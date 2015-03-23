package com.ultrapower.eoms.ultrasm.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.jdom.Element;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataSet;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;
import com.ultrapower.eoms.common.core.component.xml.XmlParser;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfg;
import com.ultrapower.eoms.ultrasm.model.ExcelSpanInfo;
import com.ultrapower.eoms.ultrasm.model.ImpFieldConfig;
import com.ultrapower.eoms.ultrasm.model.ImpTableConfig;
import com.ultrapower.eoms.ultrasm.model.TableProperties;
import com.ultrapower.eoms.ultrasm.model.TitleProperties;
import com.ultrapower.eoms.ultrasm.service.CommonExcelService;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.ultrasm.service.ExcelManagerService;
import com.ultrapower.eoms.ultrasm.service.IExcelExtend;
import com.ultrapower.eoms.ultrasm.util.ExcelStyleUtil;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;
import com.ultrapower.remedy4j.core.RemedyField;
import com.ultrapower.remedy4j.core.RemedyFieldType;
import com.ultrapower.remedy4j.core.RemedySession;

public class CommonExcelImpl implements CommonExcelService
{
	private ExcelManagerService excelManagerService;
	private DicManagerService dicManagerService;
	public Map dataMap;
	public List<String> errorInfoList;
	
	/**
	 * 根据导出配置XML的标识和导出数据集来导出EXCEL
	 * @param response
	 * @param cfgMark 导出配置标识
	 * @param dataTable 导出数据集
	 * @return List<String> 返回导出错误信息 如果List为空则说明不存在错误 即导出成功
	 */
	public List<String> exportExcelByDt(HttpServletResponse response, String cfgMark, DataTable dataTable)
	{
		errorInfoList = new ArrayList<String>();
		TableProperties tableProperties = this.getTableProperties(cfgMark);
		if(tableProperties != null)
		{
			String result = this.exportExcel(response, tableProperties, dataTable);
			if(!"".equals(result))//如果返回空 则导出成功
				errorInfoList.add(result);
		}
		else
		{
			errorInfoList.add("该配置标识没有配置详细导出信息，请与管理员联系！");
		}
		return errorInfoList;
	}
	
	/**
	 * 根据导出配置XML的标识和查询SQL缓存key值来导出EXCEL
	 * @param response
	 * @param cfgMark 导出配置标识
	 * @param cacheid 查询SQL缓存key值
	 * @return List<String> 返回导出错误信息 如果List为空则说明不存在错误 即导出成功
	 * @throws Exception
	 */
	public List<String> exportExcelByCfg(HttpServletResponse response, String cfgMark,String cacheid) throws Exception
	{
		errorInfoList = new ArrayList<String>();
		//从缓存获取SQL信息
		Object sqlObj = BaseCacheManager.get(Constants.QUERYSQL, cacheid);//从缓存取出查询SQL
		if(sqlObj!=null)
		{
			SqlResult sqlResult = (SqlResult) sqlObj;
			//获取导出标题属性
			TableProperties tableProperties = this.getTableProperties(cfgMark);
			if(tableProperties != null)
			{
				//获取导出数据
				DataTable dataTable = this.getDataTable(tableProperties, sqlResult);
				String sql = sqlResult.getSql();
				if(sql.indexOf("/*ORDERBY*/") == -1)
					tableProperties.setGroupField("");
				String result = this.exportExcel(response, tableProperties, dataTable);
				if(!"".equals(result))//如果返回空 则导出成功
					errorInfoList.add(result);
			}
			else
			{
				errorInfoList.add("该配置标识没有配置详细导出信息，请与管理员联系！");
			}
		}
		else
			errorInfoList.add("数据过期，请刷新后再导出！");
		return errorInfoList;
	}
	
	/**
	 * 根据配置标识获取导出标题属性
	 * @param cfgMark	配置标识
	 * @return
	 */
	private TableProperties getTableProperties(String cfgMark)
	{
		TableProperties tableProperties = null;
		if(!"".equals(StringUtils.checkNullString(cfgMark)))
		{
			try
			{
				StringBuffer sqlExcel = new StringBuffer();//导出配置SQL
				sqlExcel.append("select eecr.rownumber, eecr.datarow,");
				sqlExcel.append("       eecf.pid, eecf.fieldname, eecf.displayname, eecf.rowspan, eecf.colspan, eecf.ordernum, ");
				sqlExcel.append("       eecf.isgroup, eecf.enable, eecf.width, eecf.align, eecf.datatype, eecf.datainfo");
				sqlExcel.append("  from bs_t_sm_excelexpcfg eec, bs_t_sm_excelexpcfgrow eecr, bs_t_sm_excelexpcfgfld eecf");
				sqlExcel.append(" where eec.pid = eecr.eecid and eecr.pid = eecf.eecrid and eec.cfgmark = ? ");
				sqlExcel.append(" order by rownumber,ordernum,enable desc");
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = queryAdapter.executeQuery(sqlExcel.toString(),new Object[] {cfgMark});
				ExcelExpCfg eec = excelManagerService.getExcelCfgByMark(cfgMark);
				if(table != null && table.length() > 0 && eec != null)
				{
					//new一个表格属性对象
					tableProperties = new TableProperties();
					tableProperties.setExcelName(StringUtils.checkNullString(eec.getExcelname()));//导出EXCEL名称
					tableProperties.setTitleRow(NumberUtils.formatToInt(eec.getTitlerow()));//标题行数
					tableProperties.setTitleCol(NumberUtils.formatToInt(eec.getTitlecol()));//标题列数
					tableProperties.setSheetRow(NumberUtils.formatToInt(eec.getSheetrow()));//每页导出条数
					String groupField = "";//分组字段信息
					int titleCol = 0;//真实标题列 累加器
					String titleColStr = "";//分组字段所在列
					//获取数据存入表格属性中
					TitleProperties title = null;//创建标题属性对象
					DataTable titleTable = new DataTable("QTABLE");//用来存放标题属性对象
					DataRow titleRow = null;
					DataRow dataRow = null;
					int oldRow = -1;
					for(int i=0;i<table.length();i++)
					{
						dataRow = table.getDataRow(i);
						title = new TitleProperties();
						int newRow = dataRow.getInt("rownumber");//获取行号
						if(oldRow != newRow)//当换行时的创建新行DataRow
						{
							if(oldRow >= 0)
								titleTable.putDataRow(titleRow);
							titleRow = new DataRow();
						}
						oldRow = newRow;
						title.setFieldName(dataRow.getString("fieldname"));
						title.setDisplayName(dataRow.getString("displayname"));
						title.setRowSpan(dataRow.getInt("rowspan"));
						title.setColSpan(dataRow.getInt("colspan"));
						title.setEnable(dataRow.getString("enable"));
						title.setWidth(dataRow.getInt("width"));
						title.setAlign(dataRow.getString("align"));
						title.setDatatype(dataRow.getString("datatype"));
						title.setDatainfo(dataRow.getString("datainfo"));
						titleRow.put(dataRow.getString("pid"), title);
						String isGroup = dataRow.getString("isgroup");//是否按此字段分组标识
						String isTitleRow = dataRow.getString("datarow");//是否真实标题行
						if("1".equals(isTitleRow))
						{
							if("1".equals(isGroup))
							{
								if(!"".equals(groupField))
								{
									groupField += ",";
									titleColStr += ",";
								}
								groupField += dataRow.getString("fieldname");
								titleColStr += titleCol;
							}
							titleCol++;//真实标题列 累加器累加
						}
					}
					titleTable.putDataRow(titleRow);
					tableProperties.setTitleTable(titleTable);
					if(!"".equals(groupField))//分组分页字段 格式:  字段1,字段2;列数1,列数2 如:  c1,c2,c3;1,5,7 
						tableProperties.setGroupField(groupField+";"+titleColStr);
					else
						tableProperties.setGroupField("");
				}
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return tableProperties;
	}
	
	/**
	 * 根据表格属性对象和查询SQL 获取需导出的数据集
	 * @param tableProperties	表格属性对象
	 * @param sqlResult			查询SQL
	 * @return
	 */
	private DataTable getDataTable(final TableProperties tableProperties, final SqlResult sqlResult)
	{
		if(sqlResult == null)
			return null;
		DataTable dataTable = null;//存放查询数据的dataTable
		String sql = sqlResult.getSql();//查询SQL
		String count = "/*COUNT*/";//替换标识符 即：将两个此标识符中间的字段信息进行替换配置的字段
		int countStart = sql.indexOf(count);//第一个标识位置
		int countEnd = sql.lastIndexOf(count);//最后一个标识位置
		int countLen = count.length();//标识符长度
		if(countEnd > countStart)
		{
			DataTable titleTable = tableProperties.getTitleTable();
			DataRow titleRow = titleTable.getDataRow(tableProperties.getTitleRow()-1);//获取真实标题行
			String queryField = "";//导出查询字段
			for(int i=0;i<titleRow.length();i++)
			{
				TitleProperties title = (TitleProperties) titleRow.getObject(i);
				if(i > 0)
					queryField += ",";
				queryField += " " + title.getFieldName() + " ";
			}
			try
			{
				String newSql = sql.substring(0,countStart) + queryField + sql.substring(countEnd + countLen);//导出查询的SQL
				String groupField = tableProperties.getGroupField().split(";")[0];//获取分组分页字段
				if(!"".equals(groupField))
				{
					String orderStr = "/*ORDERBY*/";
					int orderStart = newSql.indexOf(orderStr);//第一个标识位置
					int orderEnd = newSql.lastIndexOf(orderStr);//最后一个标识位置
					int orderLen = orderStr.length();//标识符长度
					if(orderStart > 0 && orderEnd > orderStart)//如果存在order by 标识
					{
						//把分组字段拼到其他排序字段前面
						if("".equals(newSql.substring(orderEnd + orderLen))) {
							newSql = newSql.substring(0, orderStart) + "order by " + groupField;
						} else {
							newSql = newSql.substring(0, orderEnd + orderLen) + groupField + "," + newSql.substring(orderEnd + orderLen);//导出查询的SQL							
						}
					}
					else
					{
						//如果最外层没有order by 则在此SQL最后追加order by 和 此分组分页字段 (2010-08-31 暂时不追加)
						//newSql = newSql + " order by " + groupField;
					}
					/*
					//查询order by 的位置 把分组字段拼进SQL中
					String strTemp = newSql.toLowerCase();//定义临时变量 将SQL全部大写字母转成小写字母存放此变量中 目的是查询 order by 位置
					int leftBrackets = strTemp.lastIndexOf("(");//最后一个左括号位置
					int rightBrackets = strTemp.lastIndexOf(")");//最后一个右括号位置
					int lastUnion = strTemp.lastIndexOf(" union ");//最后一个 union 位置
					int nOrder = strTemp.lastIndexOf(" order ");//最后一个 order 位置
					int nBy = strTemp.lastIndexOf(" by ");//最后一个 by 位置
					//当成立下条件时，说明此SQL最外层是存在 order by 
					if(nOrder > 0 && (rightBrackets < nOrder || rightBrackets > nOrder && leftBrackets > nOrder && lastUnion < nOrder))
					{
						//把分组分页字段拼装到 order by 后面其他字段的前面以便导出时分页
						newSql = newSql.substring(0, nBy+4) + groupField + "," +newSql.substring(nBy+4);
					}
					else
					{
						//如果最外层没有order by 则在此SQL最后追加order by 和 此分组分页字段
						newSql = newSql+" order by "+groupField;
					}
					*/
				}
				QueryAdapter queryAdapter = new QueryAdapter();
				List values = sqlResult.getValues();
				//查询出此SQL对应数据信息
				if(values != null)
					dataTable = queryAdapter.executeQuery(newSql, values.toArray());
				else
					dataTable = queryAdapter.executeQuery(newSql, null);
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return dataTable;//返回导出数据
	}
	
	/**
	 * 导出EXCEL
	 * @param response
	 * @param tableProperties	标题属性对象
	 * @param dataTable			导出数据集
	 * @return
	 */
	private String exportExcel(HttpServletResponse response, TableProperties tableProperties, DataTable dataTable)
	{
		if(tableProperties == null)//如果表格属性对象为空 则返回导出失败原因
			return "请先进行配置再导出！";
		String excelName = StringUtils.checkNullString(tableProperties.getExcelName());//获取导出的EXCEL名称
		if("".equals(excelName))//如果未配置EXCEL名称则采用默认名称
			excelName = ExcelStyleUtil.defaultExcelName;
		DataTable titleTable = tableProperties.getTitleTable();//获取EXCEL标题行
		int titleLen = 0;
		if(titleTable != null)
			titleLen = titleTable.length();
		if(titleLen <= 0)//如果没有标题行则退出
			return "请先进行配置再导出！";
		DataRow realTitleRow = titleTable.getDataRow(titleLen-1);//获取真实标题行数据
		List<TitleProperties> dataTitleList = new ArrayList();//存放真实标题行数据,用于转换数据、操作数据
		int[] colWidth = new int[realTitleRow.length()];//每个sheet页列宽都相同,则先整理出来以便设置列宽时方便
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFFont font = wb.createFont();
		//设置EXCEL单元格样式
		HSSFCellStyle cellTitleStyle = ExcelStyleUtil.setStyle(wb,"title",font,"center","LIGHTGREEN");
		List<HSSFCellStyle> cellDataStyleList = new ArrayList();//创建单元格样式,以列为基准
		List<HSSFCellStyle> cellDataStyleList2 = new ArrayList();//创建单元格样式,以列为基准
		HSSFCellStyle cellDataStyle  = null;
		HSSFCellStyle cellDataStyle2  = null;
		for(int i=0;i<realTitleRow.length();i++)
		{
			int setWidth = ExcelStyleUtil.defaultWidth;//先取默认列宽
			String setAlign = ExcelStyleUtil.defaultAlign;//先取默认对齐方式
			TitleProperties title = (TitleProperties) realTitleRow.getObject(i);
			if(title != null)
			{
				setWidth = title.getWidth();
				setAlign = title.getAlign();
			}
			colWidth[i] = setWidth;
			cellDataStyle = ExcelStyleUtil.setStyle(wb,"cell",font,setAlign,"WHITE");
			cellDataStyle2 = ExcelStyleUtil.setStyle(wb,"cell",font,setAlign,"GREY");
			cellDataStyleList2.add(cellDataStyle2);
			cellDataStyleList.add(cellDataStyle);
			dataTitleList.add(title);//真实标题行字段添加
		}
		//整理标题行数据
		int rowNum = tableProperties.getTitleRow();//标题行数
		int colNum = tableProperties.getTitleCol();//标题列数
		String[][] titleName = new String[rowNum][colNum];//标题行名称
		TitleProperties title = null;//标题对象
		List<ExcelSpanInfo> cellSpanList = new ArrayList();
		ExcelSpanInfo cellSpan = null;
		for(int i=0;i<rowNum;i++)
		{
			DataRow titleRow = titleTable.getDataRow(i);
			for(int j=0;j<colNum;j++)
			{
				title = (TitleProperties) titleRow.getObject(j);//获取单元格属性对象
				int rowSpan = title.getRowSpan();
				int colSpan = title.getColSpan();
				String enable = title.getEnable();
				if("1".equals(enable) && (rowSpan > 1 || colSpan > 1))//存储需要合并的单元格 进行统一的合并
				{
					cellSpan = new ExcelSpanInfo();
					cellSpan.setStartRow(i);
					cellSpan.setEndRow(i+rowSpan-1);
					cellSpan.setStartCol(j);
					cellSpan.setEndCol(j+colSpan-1);
					cellSpanList.add(cellSpan);
				}
				titleName[i][j] = title.getDisplayName();
			}
		}
		
		//操作数据行数据替换
		TitleProperties dataTitle = null;
		dataMap = new HashMap();//替换字典类型的数据 key:字典值 value:展示数据
		for(int i=0;i<dataTitleList.size();i++)
		{
			dataTitle = dataTitleList.get(i);
			//dataType:该字段数据类型 dataInfo:数据信息,跟此数据类型紧密相关
			String fieldName = dataTitle.getFieldName();
			String dataType = dataTitle.getDatatype();
			String dataInfo = dataTitle.getDatainfo();
			if("".equals(dataInfo))
				continue;
			if("4".equals(dataType))//数据字典类型
			{
				List<DicItem> diList = dicManagerService.getDicItemByDicType(dataInfo);
				if(diList != null && diList.size() > 0)
				{
					DicItem di = null;
					for(int k=0;k<diList.size();k++)
					{
						di = diList.get(k);
						dataMap.put(fieldName + "-" + dataType + "-" + di.getDivalue(), di.getDiname());
					}
				}
			}
			else if("5".equals(dataType))//SQL配置类型
			{
				try
				{
					QueryAdapter queryAdapter = new QueryAdapter();
					DataTable dt = queryAdapter.executeQuery(dataInfo, null);
					if(dt != null && dt.length() > 0)
					{
						DataRow dr = null;
						for(int k=0;k<dt.length();k++)
						{
							dr = dt.getDataRow(k);
							dataMap.put(fieldName + "-" + dataType + "-" + dr.getString(0), dr.getString(1));
						}
					}
				}
				catch (Exception e)
				{
					RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
					e.printStackTrace();
				}
			}
			else if("6".equals(dataType))//手动配置类型
			{
				dataInfo = dataInfo.replaceAll("；", ";");//将全角的分号替换成半角的分号
				dataInfo = dataInfo.replaceAll("：", ":");//将全角的冒号替换成半角的冒号
				String[] subData = dataInfo.split(";");
				for(int k=0;k<subData.length;k++)
				{
					if(!"".equals(subData[k]))
					{
						String[] realData = subData[k].split(":");
						if(realData.length > 1)
							dataMap.put(fieldName + "-" + dataType + "-" + realData[0].replaceAll(" ", ""), realData[1]);
					}
				}
			}
		}
			
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		CellRangeAddress useRegion = null;
		String sheetName = excelName;
		String oldPageInfo = "";
		String[] groupField = tableProperties.getGroupField().split(";");
		String[] groupCol = null;//按分组的列数
		int dataRowNum = 0;//数据行数
		int nSheetName = 1;//当sheet名重复时加上数字标识
		String strPrevName = "";
		int sheetRowMax = tableProperties.getSheetRow();//每页记录条数上限
		if(groupField.length > 1)
		{
			groupCol = groupField[1].split(",");
		}
		if(dataTable != null && dataTable.length() > 0)
		{
			for(int dr=0;dr<dataTable.length();dr++)//循环创建sheet页
			{
				DataRow dataRow = dataTable.getDataRow(dr);
				String pageInfo = "";
				if(groupCol != null)
				{
					for(int i=0;i<groupCol.length;i++)
					{
						if(i>0)
							pageInfo += "_";
						int nCol = Integer.valueOf(groupCol[i]);//分组列数
						String fieldStr = dataRow.getString(nCol);//获取分组数据
						dataTitle = dataTitleList.get(nCol);//获取分组所在列标题属性对象
						String dataType = dataTitle.getDatatype();//获取此列数据类型
						String fieldName = dataTitle.getFieldName();
						if("4".equals(dataType) || "5".equals(dataType) || "6".equals(dataType))//如果是字典类型则替换
						{
							Object tmpObj = dataMap.get(fieldName + "-" + dataType + "-" + fieldStr);
							//fieldStr = tmpObj==null ? i+"" : (String) tmpObj;
							if(tmpObj != null)
								fieldStr = (String) tmpObj;
						}
						pageInfo += fieldStr;
					}
				}
				if(dr == 0 || !oldPageInfo.equals(pageInfo) || (sheetRowMax>0&&sheetRowMax<=dataRowNum))
				{
					//创建sheet页
					if(groupField.length > 1)//存在分组分sheet页
					{
						if("".equals(pageInfo))//若当配置的分组字段为空
						{
							sheetName = excelName + "_" + (sheetRowMax==0?dr:dr/sheetRowMax);
						}
						else if(strPrevName.equals(pageInfo))//当在同一分组内超过每页导出条数上限
						{
							sheetName = pageInfo + "_" + nSheetName;
							nSheetName++;
						}
						else//下一分组时
						{
							sheetName = pageInfo;
							nSheetName = 1;
						}
					}
					else if(sheetRowMax > 0 && sheetRowMax <= dataRowNum)//当未设置分组分页 而只设置了每页导出条数
					{
						sheetName = excelName + "_" + nSheetName;
						nSheetName++;
					}
					strPrevName = pageInfo;
					sheet = wb.createSheet(sheetName);
					for(int i=0;i<colWidth.length;i++)//设置列宽
						sheet.setColumnWidth(i, colWidth[i]);
					//wb.setSheetName(0, excelName);
					//创建标题行
					for(int i=0;i<rowNum;i++)
					{
						row = sheet.createRow(i);//创建行
						for(int j=0;j<colNum;j++)
						{
							//创建单元格
							cell = row.createCell(j);
							cell.setCellStyle(cellTitleStyle);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(titleName[i][j]);
						}
					}
					//合并标题单元格
					for(int i=0;i<cellSpanList.size();i++)
					{
						cellSpan = cellSpanList.get(i);
						useRegion = new CellRangeAddress(cellSpan.getStartRow(), cellSpan.getEndRow(), cellSpan.getStartCol(), cellSpan.getEndCol());
						sheet.addMergedRegion(useRegion);
					}
					dataRowNum = 0;//换sheet 将数据行重新累加
				}
				oldPageInfo = pageInfo;
				row = sheet.createRow(rowNum + dataRowNum);//创建数据行
				for(int j=0;j<tableProperties.getTitleCol();j++)
				{
					dataTitle = dataTitleList.get(j);
					String fieldName = dataTitle.getFieldName();
					String dataType = dataTitle.getDatatype();//数据类型
					String dataInfo = dataTitle.getDatainfo();//数据信息,意义建立在数据类型上 当类型不同 此数据信息代表不同意义
					String displayData = StringUtils.checkNullString(dataRow.getString(fieldName));//展示数据
					//此处和导入位置的操作方式是一样的，若修改也要将导出配置相对位置也要修改
					if("1".equals(dataType) || "".equals(displayData))
					{
						//字符串不做任何转换
					}
					else if("2".equals(dataType))//数值类型,保留小数位数
					{
						int pointSite = displayData.indexOf(".");
						if(pointSite > 0)
						{
							if(NumberUtils.isNumeric(dataInfo))
							{
								int keepLen = Integer.valueOf(dataInfo);
								if(displayData.length() > pointSite + keepLen)//当数据长度大于截取长度时才进行截取 否则不做操作
									displayData = displayData.substring(0, keepLen==0 ? pointSite : pointSite + keepLen + 1);
							}
						}
					}
					else if("3".equals(dataType))//日期类型,按照格式进行转换
					{
						long date = NumberUtils.formatToLong(displayData);
						if(date > 0)
							displayData = TimeUtils.formatIntToDateString(dataInfo, date);
					}
					else if("4".equals(dataType) || "5".equals(dataType) || "6".equals(dataType))//字典类型,查询字典替换对应数据
					{
						Object tmpObj = dataMap.get(fieldName + "-" + dataType + "-" + displayData);
						displayData = tmpObj==null ? "" : (String) tmpObj;
					}
					cell = row.createCell(j);
					//cell.setCellStyle(dataRowNum%2==0?cellDataStyleList.get(j):cellDataStyleList2.get(j));
					cell.setCellStyle(cellDataStyleList.get(j));
					if("2".equals(dataType))
					{
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(NumberUtils.formatToFloat(displayData));
					}
					else
					{
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(StringUtils.checkNullString(displayData));
					}
				}
				dataRowNum++;//当前页数据行数累加
			}
		}
		else//如果没有数据则只创建标题行
		{
			sheet = wb.createSheet(sheetName);
			for(int i=0;i<colWidth.length;i++)//设置列宽
				sheet.setColumnWidth(i, colWidth[i]);
			//wb.setSheetName(0, excelName);
			//创建标题行
			for(int i=0;i<rowNum;i++)
			{
				row = sheet.createRow(i);//创建行
				for(int j=0;j<colNum;j++)
				{
					//创建单元格
					cell = row.createCell(j);
					cell.setCellStyle(cellTitleStyle);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(titleName[i][j]);
				}
			}
			//合并标题单元格
			for(int i=0;i<cellSpanList.size();i++)
			{
				cellSpan = cellSpanList.get(i);
				useRegion = new CellRangeAddress(cellSpan.getStartRow(), cellSpan.getEndRow(), cellSpan.getStartCol(), cellSpan.getEndCol());
				sheet.addMergedRegion(useRegion);
			}
		}
		//导出EXCEL
		OutputStream os;
		try {
			os = response.getOutputStream();
			response.reset();
			response.setContentType("application/x-msdownload");
			excelName = new String(excelName.getBytes("gb2312"), "iso8859-1");
			response.setHeader("Content-disposition", "attachment; filename=" + excelName + ".xls");
			wb.write(os);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 根据XML配置和导入的EXCEL文件 将数据导入到数据库中
	 * @param file 导入的EXCEL文件
	 * @param xmlPath XML配置文件路径
	 * @return List<String> 返回导入错误信息 如果List为空则说明不存在错误 即导入成功
	 * @throws IOException
	 */
	public List<String> importExcelByCfg(File file, String xmlPath) throws IOException
	{
		errorInfoList = new ArrayList<String>();
		if(file == null)
		{
			errorInfoList.add("传入的文件file为空！");
			return errorInfoList;
		}
		List<ImpTableConfig> impTableConfigList = this.resolveXml(xmlPath);//调用私有方法 根据XML文件配置路径获取配置信息
		if(impTableConfigList != null && impTableConfigList.size() > 0 && errorInfoList.size()==0)
		{
			ImpTableConfig impTableConfig = null;
			List<ImpFieldConfig> impFieldConfigList = null;
			ImpFieldConfig impFieldConfig = null;
			List<String> excelColNameList = new ArrayList<String>();
			for(int i=0;i<impTableConfigList.size();i++)
			{
				impTableConfig = impTableConfigList.get(i);
				impFieldConfigList = impTableConfig.getImpFieldConfigList();
				

				//导入前方法
				if(impTableConfig.getExtend() != null && !impTableConfig.getExtend().equals("")){
					IExcelExtend extend = (IExcelExtend) WebApplicationManager.getBean(impTableConfig.getExtend());
					if(extend != null){
						extend.extendExpRun(errorInfoList, impTableConfig);
					}
				}
				
				
				for(int j=0;j<impFieldConfigList.size();j++)
				{
					impFieldConfig = impFieldConfigList.get(j);
					String dataType = impFieldConfig.getDatatype();
					if(!"8".equals(dataType))
						excelColNameList.add(impFieldConfig.getExcelcolName());
				}
			}
			DataTable excelDt = this.excelThansDt(file, impTableConfigList.get(0).getStartRow(), impTableConfigList.get(0).getStartCol(), excelColNameList);//调用私有方法 根据EXCEL文件获取将要导入的数据
						
			if(impTableConfig.getExtend() != null && !impTableConfig.getExtend().equals("")){
				IExcelExtend extend = (IExcelExtend) WebApplicationManager.getBean(impTableConfig.getExtend());
				if(extend != null){
					extend.extendImpRun(errorInfoList, impTableConfig, excelDt);
				}
			}
			
			//测试数据存储
			if(excelDt != null && excelDt.length() > 0)
			{
				DataSet excelDs = this.getImportData(impTableConfigList, excelDt);
				if(excelDs != null)
				{
					//测试整理的导入数据是否正确
//					DataTable dt = null;
//					DataRow dr = null;
//					String temp = "";
//					for(int i=0;i<excelDs.length();i++)
//					{
//						dt = excelDs.getTable(i);
//						for(int j=0;j<dt.length();j++)
//						{
//							dr = dt.getDataRow(j);
//							for(int k=0;k<dr.length();k++)
//							{
//								temp = temp + "!===!" + dr.getString(k);
//							}
//							System.out.println(temp);
//							temp = "";
//						}
//					}
					if(errorInfoList == null || errorInfoList.size() <= 0)
					{
						DataAdapter dataAdapter=new DataAdapter();
						int rowcount=dataAdapter.execute(excelDs);
						if(rowcount==-1)
						{
							errorInfoList.add("导入出错(database)");
						}
					}
				}
			}
			else
			{
				errorInfoList.add("导入的EXCEL无数据或配置错误导致获取不到EXCEL数据！（请检测开始导入行和开始导入列是否配置正确）");
			}
		}
		return errorInfoList;
	}

	/**
	 * 根据XML配置和导入的EXCEL文件 将数据导入到数据库中
	 * @param file 导入的EXCEL文件
	 * @param xmlPath XML配置文件路径
	 * @return List<String> 返回导入错误信息 如果List为空则说明不存在错误 即导入成功
	 * @throws IOException
	 */
	public List<String> importExcelByCfgToRemedy(File file, String xmlPath) throws IOException
	{
		errorInfoList = new ArrayList<String>();
		if(file == null)
		{
			errorInfoList.add("传入的文件file为空！");
			return errorInfoList;
		}
		List<ImpTableConfig> impTableConfigList = this.resolveXml(xmlPath);//调用私有方法 根据XML文件配置路径获取配置信息
		if(impTableConfigList != null && impTableConfigList.size() > 0 && errorInfoList.size()==0)
		{
			ImpTableConfig impTableConfig = null;
			List<ImpFieldConfig> impFieldConfigList = null;
			ImpFieldConfig impFieldConfig = null;
			List<String> excelColNameList = new ArrayList<String>();
			for(int i=0;i<impTableConfigList.size();i++)
			{
				impTableConfig = impTableConfigList.get(i);
				impFieldConfigList = impTableConfig.getImpFieldConfigList();
				for(int j=0;j<impFieldConfigList.size();j++)
				{
					impFieldConfig = impFieldConfigList.get(j);
					String dataType = impFieldConfig.getDatatype();
					if(!"8".equals(dataType))
						excelColNameList.add(impFieldConfig.getExcelcolName());
				}
			}
			DataTable excelDt = this.excelThansDt(file, impTableConfigList.get(0).getStartRow(), impTableConfigList.get(0).getStartCol(), excelColNameList);//调用私有方法 根据EXCEL文件获取将要导入的数据
			if(excelDt != null && excelDt.length() > 0)
			{
				DataSet excelDs = this.getImportData(impTableConfigList, excelDt);
				if(excelDs != null)
				{
					if(errorInfoList == null || errorInfoList.size() <= 0)
					{
						this.importIntoRemedy(impTableConfigList, excelDt);
					}
				}
			}
			else
			{
				errorInfoList.add("导入的EXCEL无数据或配置错误导致获取不到EXCEL数据！（请检测开始导入行和开始导入列是否配置正确）");
			}
		}
		return errorInfoList;
	}

	/**
	 * 根据XML文件配置路径获取配置信息
	 * @param xmlPath	XML配置文件路径
	 * @return
	 */
	private List<ImpTableConfig> resolveXml(String xmlPath)
	{
		List<ImpTableConfig> impTableConfigList = null;//导入表格配置对象List
		XmlParser xml = null;
		File file = new File(xmlPath);
		if(!file.exists())//验证文件是否存在
		{
			errorInfoList.add("创建xml文件解析器失败，请检查文件路径是否正确！");
			return null;
		}
		xml = new XmlParser(xmlPath);
		String relationship = StringUtils.checkNullString(xml.getAttributeValue("relation","relationship"));//获取配置的关联关系
		//根据固定格式解析关联关系start
		relationship = relationship.replaceAll(" ", "");
		String[] relaTables = null;//存放关联表
		Map relaTbMap = null;//存放关联关系 key:表名  value:字段
		if(!"".equals(relationship))
		{
			relaTbMap = new HashMap();
			relaTables = relationship.split(";");
			for(int i=0;i<relaTables.length;i++)
			{
				String[] tmpTb = relaTables[i].split(":");
				relaTbMap.put(tmpTb[0], tmpTb.length>1 ? tmpTb[1] : "");
			}
		}
		//根据固定格式解析关联关系end
		List<Element> elementList = xml.getElement("table");//获取所有的table表配置信息
		if(elementList != null && elementList.size() > 0)
		{
			impTableConfigList = new ArrayList();
			ImpTableConfig impTableConfig = null;
			Element element = null;
			dataMap = new HashMap();//用来存放在导入文件中涉及到的字典信息
			for(int i=0;i<elementList.size();i++)
			{
				String tableInfo = "第" + (i+1) + "个table";//此循环内公用部分提示
				element = elementList.get(i);//获取每个table配置信息
				String tableName = StringUtils.checkNullString(element.getAttributeValue("name"));//获取表名
				if("".equals(tableName))//如果配置的表名为空 则解析下一个table
				{
					errorInfoList.add(tableInfo+"的必要属性name尚未配置！");
					continue;
				}
				impTableConfig = new ImpTableConfig();//创建一个导入表格配置对象
				impTableConfig.setTableName(tableName);
				String strRow = element.getAttributeValue("startrow");
				String strCol = element.getAttributeValue("startcol");
				int startRow = 0;
				int startCol = 0;
				if(!"".equals(StringUtils.checkNullString(strRow)))
				{
					if(NumberUtils.isNumeric(strRow))//判断table配置的开始导入行是否是数字
						startRow = Integer.valueOf(strRow);
					else
						errorInfoList.add(tableInfo+"的属性startrow必须是非负数字！");
				}
				if(!"".equals(StringUtils.checkNullString(strCol)))
				{
					if(NumberUtils.isNumeric(strCol))//判断table配置的开始导入列是否是数字
						startCol = Integer.valueOf(strCol);
					else
						errorInfoList.add(tableInfo+"的属性startcol必须是非负数字！");
				}

				String impExtend = element.getAttributeValue("extend");
				impTableConfig.setExtend(impExtend);
				impTableConfig.setStartRow(startRow);
				impTableConfig.setStartCol(startCol);
				impTableConfig.setReadType(StringUtils.checkNullString(element.getAttributeValue("type")));//获取读取类型
				String relation = "";//是否被关联 relation=false则未被关联 否则该表是关联表 则存放的值为比较字段 即：当这几个字段完全相同时则会采取去重操作
				if(relaTbMap != null)//当配置了关联关系 即relation属性
				{
					Object tmpObj = relaTbMap.get(tableName);//获取该表是否是关联表 如果存在则为关联表
					relation = tmpObj!=null ? (String)tmpObj : "false";
				}
				else
				{
					relation = "false";
				}
				impTableConfig.setRelation(relation);
				impTableConfig.setCreateGuid(StringUtils.checkNullString(element.getAttributeValue("createguid")).toLowerCase());
				impTableConfig.setGuidName(StringUtils.checkNullString(element.getAttributeValue("guidname")));
				List<Element> fieldElementList = element.getChildren("field");
				if(fieldElementList != null && fieldElementList.size() > 0)
				{
					int existNum = 0;//关系字段在配置的table中存在的字段个数
					List<String> tmpFieldList = null;//存放此表配置的比较字段 用来验证配置的字段是否正确
					if(!"false".equals(relation) && !"".equals(relation))//如果配置了比较字段
						tmpFieldList = UltraSmUtil.arrayToList(relation.split(","));
					List<ImpFieldConfig> impFieldConfigList = new ArrayList();
					ImpFieldConfig impFieldConfig = null;
					Element fieldElement = null;
					for(int j=0;j<fieldElementList.size();j++)
					{
						String fieldInfo = tableInfo + "中的" + "第" + (j+1) + "个field";//此循环内公用部分提示
						boolean isImp = true;
						impFieldConfig = new ImpFieldConfig();
						fieldElement = fieldElementList.get(j);
						String fieldName = StringUtils.checkNullString(fieldElement.getChildTextTrim("fieldname"));
						String excelcolName = StringUtils.checkNullString(fieldElement.getChildTextTrim("excelcolname"));
						String dataLength = StringUtils.checkNullString(fieldElement.getChildTextTrim("datalength"));
						String dataType = StringUtils.checkNullString(fieldElement.getChildTextTrim("datatype"));
						String dataInfo = StringUtils.checkNullString(fieldElement.getChildTextTrim("datainfo"));
						if(tmpFieldList != null && tmpFieldList.indexOf(fieldName) >= 0)//当配置了比较字段 并且 此字段是配置的比较字段
							existNum++;
						if("".equals(fieldName))
						{
							errorInfoList.add(fieldInfo + "的fieldname属性为空！");
							isImp = false;
						}
						if(!"8".equals(dataType))//dataType:8 代表关联字段 则不需要配置excelcolName和dataLength
						{
							if("".equals(excelcolName))
							{
								errorInfoList.add(fieldInfo + "的excelcolname属性为空！");
								isImp = false;
							}
							if("".equals(dataLength) || !NumberUtils.isNumeric(dataLength))
							{
								errorInfoList.add(fieldInfo + "的datalength属性为空或者非数字！");
								isImp = false;
							}
						}
						if(!isImp)
							continue;
						impFieldConfig.setFieldName(fieldName);
						impFieldConfig.setExcelcolName(excelcolName);
						impFieldConfig.setIsUnique(StringUtils.checkNullString(fieldElement.getChildTextTrim("isunique")).toLowerCase());
						if("".equals(dataType))
						{
							dataType = "1";
						}
						else if("4".equals(dataType))
						{
							List<DicItem> diList = dicManagerService.getDicItemByDicType(dataInfo);
							if(diList != null && diList.size() > 0)
							{
								DicItem di = null;
								String[] dicInfo = new String[diList.size()];
								for(int k=0;k<diList.size();k++)
								{
									di = diList.get(k);
									dataMap.put(fieldName + "-" + dataType + "-" + di.getDiname(), di.getDivalue());
									dicInfo[k] = di.getDiname();
								}
								dataMap.put(fieldName + "-" + dataType, dicInfo);
							}
						}
						else if("5".equals(dataType))
						{
							try
							{
								QueryAdapter queryAdapter = new QueryAdapter();
								DataTable dt = queryAdapter.executeQuery(dataInfo, null);
								if(dt != null && dt.length() > 0)
								{
									DataRow dr = null;
									String[] dicInfo = new String[dt.length()];
									for(int k=0;k<dt.length();k++)
									{
										dr = dt.getDataRow(k);
										dataMap.put(fieldName + "-" + dataType + "-" + dr.getString(1), dr.getString(0));
										dicInfo[k] = dr.getString(2);
									}
									dataMap.put(fieldName + "-" + dataType, dicInfo);
								}
							}
							catch (Exception e)
							{
								RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
								e.printStackTrace();
							}
						}
						else if("6".equals(dataType))
						{
							dataInfo = dataInfo.replaceAll("；", ";");//将全角的分号替换成半角的分号
							dataInfo = dataInfo.replaceAll("：", ":");//将全角的冒号替换成半角的冒号
							String[] subData = dataInfo.split(";");
							if(subData.length > 0)
							{
								String[] dicInfo = new String[subData.length];
								for(int k=0;k<subData.length;k++)
								{
									if(!"".equals(subData[k]))
									{
										String[] realData = subData[k].split(":");
										if(realData.length > 1)
										{
											dataMap.put(fieldName + "-" + dataType + "-" + realData[1].trim(), realData[0]);
											dicInfo[k] = realData[1].trim();
										}
									}
								}
								if(dicInfo.length > 0)
									dataMap.put(fieldName + "-" + dataType, dicInfo);
							}
						}
						impFieldConfig.setDatatype(dataType);
						impFieldConfig.setDatainfo(dataInfo);
						impFieldConfig.setDatalength(dataLength);
						impFieldConfig.setIsnull(StringUtils.checkNullString(fieldElement.getChildTextTrim("isnull")).toLowerCase());
						impFieldConfig.setDefaultvalue(StringUtils.checkNullString(fieldElement.getChildTextTrim("defaultvalue")));
						impFieldConfigList.add(impFieldConfig);
					}
					if(tmpFieldList != null && existNum != tmpFieldList.size())//如果对于此表配置的比较字段不都在table配置中,则提示配置有误
					{
						errorInfoList.add("relation属性\""+tableName+"\"表对应的字段配置有误，请看对应的table中是否配置了这些字段！");
					}
					impTableConfig.setImpFieldConfigList(impFieldConfigList);
				}
				else
				{
					errorInfoList.add(tableInfo + "未配置field字段信息！");
				}
				impTableConfigList.add(impTableConfig);
			}
		}
		else
		{
			errorInfoList.add("在xml文件中未配置table元素！");
		}
		return impTableConfigList;
	}

	/**
	 * 根据EXCEL文件获取将要导入的数据
	 * @param file		导入的EXCEL文件
	 * @param startRow	开始行
	 * @param startCol	开始列
	 * @param excelColNameList	xml中配置的Excel列名
	 * @return DataTable
	 * @throws IOException
	 */
	private DataTable excelThansDt(File file, int startRow, int startCol, List<String> excelColNameList) throws IOException
	{
		InputStream ins = null;
		try {
			ins = (InputStream) (new FileInputStream(file));
		} catch (Exception e) {
			errorInfoList.add("读取文件失败！");
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
			return null;
		}
		// 获取工作薄
		HSSFWorkbook rwb = null;
		try {
			rwb = new HSSFWorkbook(ins);
		} catch (Exception e) {
			errorInfoList.add("获取工作簿失败！");
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
			return null;
		}
		// 开始数据导入
		// 在Excel文档中，第一张工作表的缺省索引是0
		HSSFSheet st = null;
		try {
			st = rwb.getSheetAt(0);
		} catch (Exception e) {
			errorInfoList.add("获取sheet页失败！");
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
			return null;
		}
		
		int rowNum = 0;
		int colNum = startCol;
		DataTable excelDt = null;
		DataRow excelDr = null;
		List excelTitle = new ArrayList();
		HSSFRow hssfRow = null;
		HSSFCell hssfCell = null;
		
		//EXCEL标题行数据信息
		hssfRow = st.getRow(startRow);
		if(hssfRow == null)
			return null;
		while(true)
		{
			hssfCell = hssfRow.getCell(colNum);
			if(hssfCell == null)
				break;
			String colName = StringUtils.checkNullString(hssfCell.getStringCellValue());
			if("".equals(colName.replaceAll(" ", "")))
				break;
			excelTitle.add(colName);
			colNum++;
		}
		
		boolean xml_excelCol = true;
		String colNames = "";
		for(int i=0;i<excelColNameList.size();i++)
		{
			String colName = excelColNameList.get(i);
			if(excelTitle.indexOf(colName) < 0)
			{
				if(!"".equals(colNames))
					colNames += ",";
				colNames += "\"" + colName + "\"";
				xml_excelCol = false;
			}
		}
		if(!xml_excelCol)
		{
			errorInfoList.add("在xml中配置的excelcolname属性中" + colNames + "列在Excel中不存在，请检查是否配置错误或选择错误Excel！");
			return null;
		}
		
		//EXCEL数据行信息
		excelDt = new DataTable("excelDt");
		rowNum = startRow + 1;
		while(true)
		{
			hssfRow = st.getRow(rowNum);
			if(hssfRow == null)
				break;
			excelDr = new DataRow();
			int nullNum = startCol;
			for(int k=startCol;k<colNum;k++)
			{
				hssfCell = hssfRow.getCell(k);
				Object colData = "";
				if(hssfCell != null)
					colData = StringUtils.checkNullString(readCell(hssfCell));
				if("".equals(colData))
					nullNum++;
				excelDr.put((String) excelTitle.get(k-startCol), colData);
			}
			if(nullNum < colNum)
				excelDt.putDataRow(excelDr);
			rowNum++;
		}
		ins.close();
		return excelDt;
	}
	
	/**
	 * 读取EXCEL单元格类型并对单元格数据进行处理
	 * @param hssfCell
	 * @return
	 */
	private String readCell(HSSFCell hssfCell)
	{
		DecimalFormat df = new DecimalFormat("#.########");//默认最多保留8位小数
		String objCellValue = null;
		if (hssfCell == null)
		{
			return null;
		}
		int cellType = hssfCell.getCellType();
		switch (cellType)
		{
		case HSSFCell.CELL_TYPE_NUMERIC: // Numeric
			if (HSSFDateUtil.isCellDateFormatted(hssfCell)) //日期类型
			{
				objCellValue = TimeUtils.formatLongDateToString(hssfCell.getDateCellValue());
			}
			else //数值类型
			{
				objCellValue = df.format(hssfCell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_STRING: // String
			objCellValue = hssfCell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // Formula
			objCellValue = hssfCell.getCellFormula();
			break;
		case HSSFCell.CELL_TYPE_BLANK: // Blank
			objCellValue = hssfCell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN: // boolean
			objCellValue = String.valueOf(hssfCell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR: // Error
			break;
		}
		return objCellValue.trim();
	}

	/**
	 * 获取导入数据
	 * @param impTableConfigList	导入表格配置对象List
	 * @param excelDt				导入数据集
	 * @return
	 */
	private DataSet getImportData(List<ImpTableConfig> impTableConfigList, DataTable excelDt)
	{
		//函数返回的结果集DataSet,用来存放导入的数据
		DataSet excelDs = null;
		DataTable dataTable = null;
		DataRow dataRow = null;
		if(impTableConfigList != null && impTableConfigList.size() > 0 && excelDt != null && excelDt.length() > 0)
		{
			excelDs = new DataSet();
			int impTableConfigLen = impTableConfigList.size();//配置的Table的个数
			ImpTableConfig impTableConfig = null;//创建导入表格配置对象
			List<ImpFieldConfig> impFieldConfigList = null;
			ImpFieldConfig impFieldConfig = null;//创建导入字段配置对象
			DataRow excelDr = null;//EXCEL的行数据
			QueryAdapter queryAdapter = null;//用于验证数据执行SQL的
			DataTable table = null;//用于接收queryAdapter的查询结果
			Map uniqueMap = null;//唯一标识验证用
			Map uniqueFieldMap = null;
			Map relationMap = new HashMap();
			Object uniqueFieldObj = null;
			int startRow = impTableConfigList.get(0).getStartRow();//impTableConfig.getStartRow();
			DataTable[] dts=new DataTable[impTableConfigLen];
			uniqueMap = new HashMap();
			for(int i=0;i<impTableConfigLen;i++)
			{
				impTableConfig = impTableConfigList.get(i);
				dts[i]=new DataTable(impTableConfig.getTableName());
				excelDs.put(dts[i]);
			}
			String tableName = "";
			String relation = "";
			for(int rowNum=0;rowNum<excelDt.length();rowNum++)
			{
				int realRow = startRow + rowNum + 2;//对应EXCEL中展示的行数
				String excelRowInfo = "第" + realRow + "行";
				boolean isImp = true;
				excelDr = excelDt.getDataRow(rowNum);
				String prevData = "";
				String excelData = "";
				for(int i=0;i<impTableConfigLen;i++)
				{
					impTableConfig = impTableConfigList.get(i);
					tableName = impTableConfig.getTableName();//获取表名
					impFieldConfigList = impTableConfig.getImpFieldConfigList();
					if(impFieldConfigList != null && impFieldConfigList.size() > 0)
					{
						relation = impTableConfig.getRelation();
						if(!"false".equals(relation) && rowNum > 0 && excelData.equals(prevData))//如果此表在关联关系中配置了，则执行去重操作
						{
							DataTable tempTable = dts[i];
							DataRow tempRow = tempTable.getDataRow(tempTable.length()-1);
							List<String> tmpFieldList = null;
							if(!"".equals(relation))//当配置了关联关系对于此表的比较字段
								tmpFieldList = UltraSmUtil.arrayToList(relation.split(","));
							for(int k=0;k<impFieldConfigList.size();k++)
							{
								impFieldConfig = impFieldConfigList.get(k);
								String fieldName = impFieldConfig.getFieldName();
								if(tmpFieldList!=null && tmpFieldList.indexOf(fieldName)<0)
									continue;
								String datatype = impFieldConfig.getDatatype();
								if(!"8".equals(datatype))
								{
									String datainfo = impFieldConfig.getDatainfo();
									String dataStr = excelDr.getString(impFieldConfig.getExcelcolName());
									if("1".equals(datatype))
									{
										//字符型不需要转换
									}
									else if("2".equals(datatype))
									{
										int pointSite = dataStr.indexOf(".");
										if(pointSite > 0)
										{
											if(NumberUtils.isNumeric(datainfo))
											{
												int keepLen = Integer.valueOf(datainfo);
												if(dataStr.length() > pointSite + keepLen)//当数据长度大于截取长度时才进行截取 否则不做操作
													dataStr = dataStr.substring(0, keepLen==0 ? pointSite : pointSite + keepLen + 1);
											}
										}
									}
									else if("3".equals(datatype))
									{
										dataStr = String.valueOf(TimeUtils.formatDateStringToInt(dataStr, datainfo));
									}
									else if("4".equals(datatype) || "5".equals(datatype) || "6".equals(datatype))
									{
										Object tmpObj = dataMap.get(fieldName + "-" + datatype + "-" + dataStr);
										dataStr = tmpObj==null ? "" : (String) tmpObj;
									}
									excelData += dataStr;
									prevData += tempRow.getString(impFieldConfig.getFieldName());
								}
							}
							if(!"".equals(prevData) && (excelData.equals(prevData) || excelData.equals("")))//如果两行数据相同 或者 当前表当前行数据为空则此行不加到table中
								continue;
						}
						dataRow = new DataRow();
						String createGuid = impTableConfig.getCreateGuid();
						if("true".equals(createGuid))//如果xml中配置了创建UUID属性 并且为true则自动添加UUID字段
						{
							String guidName = impTableConfig.getGuidName();
							String uuid = UUIDGenerator.getUUIDoffSpace();
							dataRow.put(guidName, uuid);
						}
						for(int j=0;j<impFieldConfigList.size();j++)
						{
							impFieldConfig = impFieldConfigList.get(j);
							String fieldName = impFieldConfig.getFieldName();
							String excelcolName = impFieldConfig.getExcelcolName();
							String isUnique = impFieldConfig.getIsUnique();
							String datatype = impFieldConfig.getDatatype();
							String datainfo = impFieldConfig.getDatainfo();
							String datalength = impFieldConfig.getDatalength();
							String isnull = impFieldConfig.getIsnull();
							String defaultvalue = impFieldConfig.getDefaultvalue();
							String dataStr = "";
							if(datatype.equals("8"))
							{
								int point = datainfo.indexOf(".");
								if(point > 0)
								{
									String getTbName = datainfo.substring(0,point);
									String getFldName = datainfo.substring(point+1);
									DataTable getDt = excelDs.getTable(getTbName);
									if(getDt != null && getDt.length() > 0)
									{
										DataRow getDr = getDt.getDataRow(getDt.length()-1);
										if(getDr != null)
										{
											dataStr = getDr.getString(getFldName);
										}
//										else
//										{
//											errorInfoList.add("在获取关联字段信息时获取不到" + k + "行数据！");
//											isImp = false;
//										}
									}
									else
									{
										Object obj = relationMap.get(tableName + "." + fieldName);
										if(obj == null)
										{
											relationMap.put(tableName + "." + fieldName, "");
											errorInfoList.add("表\"" + tableName + "\"的字段\"" + fieldName + "\"关联字段的表名尚未配置！");
										}
										isImp = false;
									}
								}
								else
								{
									Object obj = relationMap.get(tableName + "." + fieldName);
									if(obj == null)
									{
										relationMap.put(tableName + "." + fieldName, "");
										errorInfoList.add("表\"" + tableName + "\"的字段\"" + fieldName + "\"关联字段信息配置格式错误！");
									}
									isImp = false;
								}
							}
							else
							{
								dataStr = excelDr.getString(excelcolName);
								//进行验证
								String fieldInfo = "\"" + excelcolName + "\"";
								if("true".equals(isUnique))//验证唯一标识
								{
									String sql = " select count(*) from " + tableName + " where " + fieldName + " = ? ";
									queryAdapter = new QueryAdapter();
									table = queryAdapter.executeQuery(sql,new Object[] {dataStr});
									if(table != null && table.length() > 0)
									{
										int num = table.getDataRow(0).getInt(0);
										if(num > 0)
										{
											errorInfoList.add(fieldInfo + "列唯一标识验证中，" + excelRowInfo + "数据在数据库中已存在！");
											isImp = false;
										}
										else
										{
											uniqueFieldObj = uniqueMap.get(fieldName + "_" + i);
											if(uniqueFieldObj == null)
											{
												uniqueFieldMap = new HashMap();
												uniqueFieldMap.put(dataStr, realRow);
												uniqueMap.put(fieldName + "_" + i, uniqueFieldMap);
											}
											else
											{
												uniqueFieldMap = (Map) uniqueFieldObj;
												if(uniqueFieldMap.get(dataStr) != null)
												{
													errorInfoList.add(fieldInfo + "列唯一标识验证中，" + excelRowInfo + "数据在EXCEL中与第" + uniqueFieldMap.get(dataStr) + "行重复存在！");
													isImp = false;
												}
												else
												{
													uniqueFieldMap.put(dataStr, realRow);
												}
											}
										}
									}
								}
								if("false".equals(isnull))//是否可为空 若不为空进行此验证
								{
									if("".equals(dataStr))
									{
										errorInfoList.add(fieldInfo + "列非空验证中，" + excelRowInfo + "数据为空！");
										isImp = false;
									}
								}
								if(NumberUtils.isNumeric(datalength))
								{
									if(dataStr.length() > Integer.valueOf(datalength))
									{
										errorInfoList.add(fieldInfo + "列长度验证中，" + excelRowInfo + "数据长度超出配置长度！");
										isImp = false;
									}
								}
								if(!"".equals(defaultvalue))//默认数据若不为空 则将空数据赋值为默认数据
								{
									dataStr = "".equals(dataStr)?defaultvalue:dataStr;
								}
								if(!isImp)
									continue;
								//此处和导出位置的操作方式是一样的，若修改也要将导出配置相对位置也要修改
								if("1".equals(datatype))
								{
									//字符型不需要转换
								}
								else if("2".equals(datatype))
								{
									int pointSite = dataStr.indexOf(".");
									if(pointSite > 0)
									{
										if(NumberUtils.isNumeric(datainfo))
										{
											int keepLen = Integer.valueOf(datainfo);
											if(dataStr.length() > pointSite + keepLen)//当数据长度大于截取长度时才进行截取 否则不做操作
												dataStr = dataStr.substring(0, keepLen==0 ? pointSite : pointSite + keepLen + 1);
										}
									}
								}
								else if("3".equals(datatype))
								{
									dataStr = String.valueOf(TimeUtils.formatDateStringToInt(dataStr, datainfo));
								}
								else if("4".equals(datatype) || "5".equals(datatype) || "6".equals(datatype))
								{
									Object tmpObj = dataMap.get(fieldName + "-" + datatype + "-" + dataStr);
									dataStr = tmpObj==null ? "" : (String) tmpObj;
								}
							}
							dataRow.put(fieldName, dataStr);
						}
						if(isImp)
						{
							dataRow.setOptype(1);
							dts[i].putDataRow(dataRow);
						}
					}
				}
				excelDs.put(dataTable);
			}
		}
		return excelDs;
	}

	/**
	 * 导入数据到remedy
	 * @param impTableConfigList	导入表格配置对象List
	 * @param excelDt				导入数据集
	 * @return
	 */
	private DataSet importIntoRemedy(List<ImpTableConfig> impTableConfigList, DataTable excelDt)
	{
		String demoPwd = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.remedy.demopwd"));
		RemedySession remedySession = new RemedySession("Demo", demoPwd);
		DataSet excelDs = null;
		DataTable dataTable = null;
		DataRow dataRow = null;
		remedySession.login();
		if(impTableConfigList != null && impTableConfigList.size() > 0 && excelDt != null && excelDt.length() > 0)
		{
			excelDs = new DataSet();
			int impTableConfigLen = impTableConfigList.size();//配置的Table的个数
			ImpTableConfig impTableConfig = null;//创建导入表格配置对象
			List<ImpFieldConfig> impFieldConfigList = null;
			ImpFieldConfig impFieldConfig = null;//创建导入字段配置对象
			DataRow excelDr = null;//EXCEL的行数据
			QueryAdapter queryAdapter = null;//用于验证数据执行SQL的
			DataTable table = null;//用于接收queryAdapter的查询结果
			Map uniqueMap = null;//唯一标识验证用
			Map uniqueFieldMap = null;
			Map relationMap = new HashMap();
			Object uniqueFieldObj = null;
			int startRow = impTableConfigList.get(0).getStartRow();//impTableConfig.getStartRow();
			DataTable[] dts=new DataTable[impTableConfigLen];
			uniqueMap = new HashMap();
			for(int i=0;i<impTableConfigLen;i++)
			{
				impTableConfig = impTableConfigList.get(i);
				dts[i]=new DataTable(impTableConfig.getTableName());
				excelDs.put(dts[i]);
			}
			String tableName = "";
			String relation = "";
			for(int rowNum=0;rowNum<excelDt.length();rowNum++)
			{
				int realRow = startRow + rowNum + 2;//对应EXCEL中展示的行数
				String excelRowInfo = "第" + realRow + "行";
				boolean isImp = true;
				excelDr = excelDt.getDataRow(rowNum);
				String prevData = "";
				String excelData = "";
				for(int i=0;i<impTableConfigLen;i++)
				{
					impTableConfig = impTableConfigList.get(i);
					tableName = impTableConfig.getTableName();//获取表名
					impFieldConfigList = impTableConfig.getImpFieldConfigList();
					if(impFieldConfigList != null && impFieldConfigList.size() > 0)
					{
						relation = impTableConfig.getRelation();
						if(!"false".equals(relation) && rowNum > 0 && excelData.equals(prevData))//如果此表在关联关系中配置了，则执行去重操作
						{
							DataTable tempTable = dts[i];
							DataRow tempRow = tempTable.getDataRow(tempTable.length()-1);
							List<String> tmpFieldList = null;
							if(!"".equals(relation))//当配置了关联关系对于此表的比较字段
								tmpFieldList = UltraSmUtil.arrayToList(relation.split(","));
							for(int k=0;k<impFieldConfigList.size();k++)
							{
								impFieldConfig = impFieldConfigList.get(k);
								String fieldName = impFieldConfig.getFieldName();
								if(tmpFieldList!=null && tmpFieldList.indexOf(fieldName)<0)
									continue;
								String datatype = impFieldConfig.getDatatype();
								if(!"8".equals(datatype))
								{
									String datainfo = impFieldConfig.getDatainfo();
									String dataStr = excelDr.getString(impFieldConfig.getExcelcolName());
									if("1".equals(datatype))
									{
										//字符型不需要转换
									}
									else if("2".equals(datatype))
									{
										int pointSite = dataStr.indexOf(".");
										if(pointSite > 0)
										{
											if(NumberUtils.isNumeric(datainfo))
											{
												int keepLen = Integer.valueOf(datainfo);
												if(dataStr.length() > pointSite + keepLen)//当数据长度大于截取长度时才进行截取 否则不做操作
													dataStr = dataStr.substring(0, keepLen==0 ? pointSite : pointSite + keepLen + 1);
											}
										}
									}
									else if("3".equals(datatype))
									{
										dataStr = String.valueOf(TimeUtils.formatDateStringToInt(dataStr, datainfo));
									}
									else if("4".equals(datatype) || "5".equals(datatype) || "6".equals(datatype))
									{
										Object tmpObj = dataMap.get(fieldName + "-" + datatype + "-" + dataStr);
										dataStr = tmpObj==null ? "" : (String) tmpObj;
									}
									excelData += dataStr;
									prevData += tempRow.getString(impFieldConfig.getFieldName());
								}
							}
							if(!"".equals(prevData) && (excelData.equals(prevData) || excelData.equals("")))//如果两行数据相同 或者 当前表当前行数据为空则此行不加到table中
								continue;
						}
						dataRow = new DataRow();
						List<RemedyField> remedyFieldList = new ArrayList<RemedyField>();
						String createGuid = impTableConfig.getCreateGuid();
						if("true".equals(createGuid))//如果xml中配置了创建UUID属性 并且为true则自动添加UUID字段
						{
							String guidName = impTableConfig.getGuidName();
							String uuid = UUIDGenerator.getUUIDoffSpace();
							dataRow.put(guidName, uuid);
							String[] fieldNames = guidName.split("_");
							RemedyField remedyField = new RemedyField(fieldNames[0], fieldNames.length>1 ? fieldNames[1] : "", RemedyFieldType.KEYWORD, uuid);
							remedyFieldList.add(remedyField);
						}
						for(int j=0;j<impFieldConfigList.size();j++)
						{
							impFieldConfig = impFieldConfigList.get(j);
							String fieldName = impFieldConfig.getFieldName();
							String excelcolName = impFieldConfig.getExcelcolName();
							String isUnique = impFieldConfig.getIsUnique();
							String datatype = impFieldConfig.getDatatype();
							String datainfo = impFieldConfig.getDatainfo();
							String datalength = impFieldConfig.getDatalength();
							String isnull = impFieldConfig.getIsnull();
							String defaultvalue = impFieldConfig.getDefaultvalue();
							String dataStr = "";
							int remedyType = RemedyFieldType.NULL;
							if(datatype.equals("8"))
							{
								int point = datainfo.indexOf(".");
								if(point > 0)
								{
									String getTbName = datainfo.substring(0,point);
									String getFldName = datainfo.substring(point+1);
									DataTable getDt = excelDs.getTable(getTbName);
									if(getDt != null && getDt.length() > 0)
									{
										DataRow getDr = getDt.getDataRow(getDt.length()-1);
										if(getDr != null)
										{
											dataStr = getDr.getString(getFldName);
										}
//										else
//										{
//											errorInfoList.add("在获取关联字段信息时获取不到" + k + "行数据！");
//											isImp = false;
//										}
									}
									else
									{
										Object obj = relationMap.get(tableName + "." + fieldName);
										if(obj == null)
										{
											relationMap.put(tableName + "." + fieldName, "");
											errorInfoList.add("表\"" + tableName + "\"的字段\"" + fieldName + "\"关联字段的表名尚未配置！");
										}
										isImp = false;
									}
								}
								else
								{
									Object obj = relationMap.get(tableName + "." + fieldName);
									if(obj == null)
									{
										relationMap.put(tableName + "." + fieldName, "");
										errorInfoList.add("表\"" + tableName + "\"的字段\"" + fieldName + "\"关联字段信息配置格式错误！");
									}
									isImp = false;
								}
							}
							else
							{
								dataStr = excelDr.getString(excelcolName);
								//进行验证
								String fieldInfo = "\"" + excelcolName + "\"";
								if("true".equals(isUnique))//验证唯一标识
								{
									String sql = " select count(*) from " + tableName + " where " + fieldName + " = ? ";
									queryAdapter = new QueryAdapter();
									table = queryAdapter.executeQuery(sql,new Object[] {dataStr});
									if(table != null && table.length() > 0)
									{
										int num = table.getDataRow(0).getInt(0);
										if(num > 0)
										{
											errorInfoList.add(fieldInfo + "列唯一标识验证中，" + excelRowInfo + "数据在数据库中已存在！");
											isImp = false;
										}
										else
										{
											uniqueFieldObj = uniqueMap.get(fieldName + "_" + i);
											if(uniqueFieldObj == null)
											{
												uniqueFieldMap = new HashMap();
												uniqueFieldMap.put(dataStr, realRow);
												uniqueMap.put(fieldName + "_" + i, uniqueFieldMap);
											}
											else
											{
												uniqueFieldMap = (Map) uniqueFieldObj;
												if(uniqueFieldMap.get(dataStr) != null)
												{
													errorInfoList.add(fieldInfo + "列唯一标识验证中，" + excelRowInfo + "数据在EXCEL中与第" + uniqueFieldMap.get(dataStr) + "行重复存在！");
													isImp = false;
												}
												else
												{
													uniqueFieldMap.put(dataStr, realRow);
												}
											}
										}
									}
								}
								if("false".equals(isnull))//是否可为空 若不为空进行此验证
								{
									if("".equals(dataStr))
									{
										errorInfoList.add(fieldInfo + "列非空验证中，" + excelRowInfo + "数据为空！");
										isImp = false;
									}
								}
								if(NumberUtils.isNumeric(datalength))
								{
									if(dataStr.length() > Integer.valueOf(datalength))
									{
										errorInfoList.add(fieldInfo + "列长度验证中，" + excelRowInfo + "数据长度超出配置长度！");
										isImp = false;
									}
								}
								if(!"".equals(defaultvalue))//默认数据若不为空 则将空数据赋值为默认数据
								{
									dataStr = "".equals(dataStr)?defaultvalue:dataStr;
								}
								if(!isImp)
									continue;
								//此处和导出位置的操作方式是一样的，若修改也要将导出配置相对位置也要修改
								if("1".equals(datatype))
								{
									//字符型不需要转换
									remedyType = RemedyFieldType.CHAR;
								}
								else if("2".equals(datatype))
								{
									int pointSite = dataStr.indexOf(".");
									if(pointSite > 0)
									{
										if(NumberUtils.isNumeric(datainfo))
										{
											int keepLen = Integer.valueOf(datainfo);
											if(dataStr.length() > pointSite + keepLen)//当数据长度大于截取长度时才进行截取 否则不做操作
											{
												dataStr = dataStr.substring(0, keepLen==0 ? pointSite : pointSite + keepLen + 1);
											}
										}
									}
									remedyType = RemedyFieldType.INTEGER;
								}
								else if("3".equals(datatype))
								{
									dataStr = String.valueOf(TimeUtils.formatDateStringToInt(dataStr, datainfo));
									remedyType = RemedyFieldType.TIME;
								}
								else if("4".equals(datatype) || "5".equals(datatype) || "6".equals(datatype))
								{
									Object tmpObj = dataMap.get(fieldName + "-" + datatype + "-" + dataStr);
									dataStr = tmpObj==null ? "" : (String) tmpObj;
									remedyType = RemedyFieldType.CHAR;
								}
								else
								{
									remedyType = RemedyFieldType.CHAR;
								}
							}
							dataRow.put(fieldName, dataStr);
							String[] fieldNames = fieldName.split("_");
							RemedyField remedyField = new RemedyField(fieldNames[0], fieldNames.length>1 ? fieldNames[1] : "", remedyType, dataStr);
							remedyFieldList.add(remedyField);
						}
						if(isImp)
						{
							dataRow.setOptype(1);
							dts[i].putDataRow(dataRow);
							remedySession.addEntry(tableName, remedyFieldList);
						}
					}
				}
				excelDs.put(dataTable);
			}
		}
		remedySession.logout();
		return excelDs;
	}
	
	/**
	 * 下载导入模版
	 * @param response
	 * @param xmlPath XML配置文件路径
	 * @return List<String> 返回导出模版错误信息 如果List为空则说明不存在错误 即模版下载成功
	 */
	public List<String> downloadImportTemplate(HttpServletResponse response, String xmlPath)
	{
		errorInfoList = new ArrayList<String>();
		List<ImpTableConfig> impTableConfigList = this.resolveXml(xmlPath);//调用私有方法 根据XML文件配置路径获取配置信息
		int impTableConfigLen = 0;//配置的表个数
		if(impTableConfigList != null)
			impTableConfigLen = impTableConfigList.size();
		if(impTableConfigLen > 0)
		{
			ImpTableConfig impTableConfig = impTableConfigList.get(0);			

			if(impTableConfig.getExtend() != null && !impTableConfig.getExtend().equals("")){
				IExcelExtend extend = (IExcelExtend) WebApplicationManager.getBean(impTableConfig.getExtend());
				if(extend != null){
					extend.extendExpRun(errorInfoList, impTableConfig);
				}
			}
			
			List<ImpFieldConfig> impFieldConfigList = null;
			ImpFieldConfig impFieldConfig = null;
			int startRow = impTableConfig.getStartRow();//模版导出的标题开始行
			int startCol = impTableConfig.getStartCol();//模版导出的标题开始列
			int colNum = startCol;//标题导出到的列 标题从startCol列导出到colNum列
			for(int i=0;i<impTableConfigLen;i++)//此循环是计算导入到的列
			{
				impTableConfig = impTableConfigList.get(i);
				impFieldConfigList = impTableConfig.getImpFieldConfigList();
				if(impFieldConfigList != null)
					colNum += impFieldConfigList.size();
			}
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFFont font = wb.createFont();
			HSSFCellStyle cellTitleStyle = ExcelStyleUtil.setStyle(wb,"title",font,"center","LIGHTGREEN");
			HSSFCellStyle requiredTitleStyle = ExcelStyleUtil.setStyle(wb,"title",font,"center","RED");
			//HSSFCellStyle cellDataStyle = ExcelStyleUtil.setStyle(wb,"cell",font,"left","WHITE");
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			String excelName = "导入模版";
			sheet = wb.createSheet(excelName);
			for(int i=startCol;i<colNum;i++)//设置列宽
				sheet.setColumnWidth(i, ExcelStyleUtil.defaultWidth);
			colNum = startCol;
			row = sheet.createRow(startRow);//创建标题行
			for(int i=0;i<impTableConfigLen;i++)//遍历各表的字段 进行创建单元格信息
			{
				impTableConfig = impTableConfigList.get(i);
				impFieldConfigList = impTableConfig.getImpFieldConfigList();
				for(int j=0;j<impFieldConfigList.size();j++)
				{
					impFieldConfig = impFieldConfigList.get(j);
					String datatype = impFieldConfig.getDatatype();
					if("8".equals(datatype))
						continue;
					String isnull = impFieldConfig.getIsnull();
					cell = row.createCell(colNum);
					cell.setCellStyle(isnull.equals("false")?requiredTitleStyle:cellTitleStyle);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(impFieldConfig.getExcelcolName());
					colNum++;
				}
			}
			
			//给字典类型列配置下拉菜单选择
			colNum = startCol;
			//List<String> fillDirectionList = new ArrayList<String>();//用来存放填写说明信息
			for(int i=0;i<impTableConfigLen;i++)
			{
				impTableConfig = impTableConfigList.get(i);
				impFieldConfigList = impTableConfig.getImpFieldConfigList();
				for(int j=0;j<impFieldConfigList.size();j++)
				{
					impFieldConfig = impFieldConfigList.get(j);
					String datatype = impFieldConfig.getDatatype();
					if("8".equals(datatype))
						continue;
					if("4".equals(datatype) || "5".equals(datatype) || "6".equals(datatype))//当属于字典类型时 需特殊说明一下 则添加到填写说明中
					{
						String fieldName = impFieldConfig.getFieldName();
						Object tmpObj = dataMap.get(fieldName + "-" + datatype);
						if(tmpObj != null)
						{
							String[] dicInfo = (String[]) tmpObj;
							CellRangeAddressList regions = new CellRangeAddressList(startRow + 1, startRow + 1000, colNum, colNum);
							DVConstraint constraint = DVConstraint.createExplicitListConstraint(dicInfo);
							HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
							sheet.addValidationData(data_validation);
							//fillDirectionList.add("\"" + impFieldConfig.getExcelcolName() + "\"列请使用下拉选择框选择，或输入[ " + UltraSmUtil.listThanString(UltraSmUtil.arrayToList(dicInfo)) + " ]中的信息");
						}
					}
					colNum++;
				}
			}
			
//			if(fillDirectionList.size() > 0)
//			{
//				String fillDirection = "填写说明：";
//				int dicNum = 0;
//				for(dicNum=0;dicNum<fillDirectionList.size();dicNum++)
//				{
//					fillDirection += "\n" + (dicNum+1) + "、" + fillDirectionList.get(dicNum); 
//				}
//				for(int i=0;i<=dicNum;i++)//将填写说明边框画线
//				{
//					row = sheet.createRow(startRow + 17 + i);
//					for(int j=startCol;j<colNum;j++)
//					{
//						if(i==0 || i==dicNum || j==startCol || j==colNum-1)
//						{
//							cell = row.createCell(j);
//							cell.setCellStyle(cellDataStyle);
//							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//						}
//					}
//				}
//				row = sheet.getRow(startRow + 17);
//				cell = row.getCell(startCol);
//				cell.setCellStyle(cellDataStyle);
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//				cell.setCellValue(fillDirection);
//				//合并填写说明单元格
//				CellRangeAddress useRegion = new CellRangeAddress(startRow + 17, startRow + 17 + dicNum, startCol, colNum - 1);
//				sheet.addMergedRegion(useRegion);
//			}
			
			OutputStream os;
			try {
				os = response.getOutputStream();
				response.reset();
				response.setContentType("application/x-msdownload");
				excelName = new String(excelName.getBytes("gb2312"), "iso8859-1");
				response.setHeader("Content-disposition", "attachment; filename=" + excelName + ".xls");
				wb.write(os);
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return errorInfoList;
	}
	
	public void setExcelManagerService(ExcelManagerService excelManagerService) {
		this.excelManagerService = excelManagerService;
	}
	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}
}
