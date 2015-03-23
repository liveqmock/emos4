package com.ultrapower.eoms.ultrasm.util;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.ultrapower.eoms.common.core.exception.BaseException;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfgFld;
import com.ultrapower.eoms.ultrasm.model.ExcelExpCfgRow;
import com.ultrapower.eoms.ultrasm.service.ExcelManagerService;

/**
 * 该类是Excel导出配置的工具类，提供XML的解析和构造
 * @author RenChenglin
 *
 */
public class ExcelExpXmlUtil {
	
	/**
	 * 解析客户端传递的XML格式字符串数据（该数据不包含xml声明）
	 * @param xmldata XML数据字符串
	 * @param userId 用户id
	 * @return Excel导出配置表头单元格矩阵信息，保存在Map中，key为tdMatrix
	 */
	public static Map getExcelCfgTrTdData(String xmldata,String userId)
	{
		if("".equals(StringUtils.checkNullString(xmldata)))
		{
			return null;
		}
		Map datamap = new HashMap();
		StringBuffer str = new StringBuffer();
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		str.append("<root>");
		str.append(xmldata);
		str.append("</root>");
		StringReader read = new StringReader(str.toString());
	    InputSource source = new InputSource(read);
	    SAXBuilder sb = new SAXBuilder();
	    Element rootElement=null;
	    try
	    {
		    Document doc = sb.build(source);
		    rootElement = doc.getRootElement();
	    }catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	    //获取矩阵行数和列数
	    int rows = Integer.parseInt(rootElement.getChild("table").getAttributeValue("rows"));
	    int cols = Integer.parseInt(rootElement.getChild("table").getAttributeValue("cols"));
	    //构造矩阵数组用以存储单元格
	    ExcelExpCfgFld[][] fldMatrix = new ExcelExpCfgFld[rows][cols];
	    //获取所有行
	    List rowList = rootElement.getChild("table").getChildren();
	    if(rowList.size()==0 || rowList==null || rowList.size()!=rows)
	    {
	    	throw new BaseException("客户端表格XML文件错误！");
	    }
	    List rowtdList;
	    for(int i=0;i<rowList.size();i++)
	    {
	    	Element row = (Element)rowList.get(i);
	    	rowtdList = row.getChildren();
	    	for(int j=0;j<rowtdList.size();j++)
	    	{
	    		Element td = (Element)rowtdList.get(j);
	    		ExcelExpCfgFld eecfld = new ExcelExpCfgFld();
	    		eecfld.setFieldname(StringUtils.checkNullString(td.getAttributeValue("fieldname")));
	    		eecfld.setDisplayname(StringUtils.checkNullString(td.getAttributeValue("displayname")));
	    		eecfld.setRowspan(Long.valueOf(StringUtils.checkNullString(td.getAttributeValue("rowspan"))));
	    		eecfld.setColspan(Long.valueOf(StringUtils.checkNullString(td.getAttributeValue("colspan"))));
	    		eecfld.setWidth(Long.valueOf(StringUtils.checkNullString(td.getAttributeValue("width"))));
	    		eecfld.setAlign(StringUtils.checkNullString(td.getAttributeValue("align")));
	    		eecfld.setColcolor(StringUtils.checkNullString(td.getAttributeValue("colcolor")));
	    		eecfld.setDatatype(StringUtils.checkNullString(td.getAttributeValue("datatype")));
	    		eecfld.setDatalength(Long.valueOf(StringUtils.checkNullString(td.getAttributeValue("datalength"))));
	    		eecfld.setDatainfo(StringUtils.checkNullString(td.getAttributeValue("datainfo")));
	    		eecfld.setOperator(StringUtils.checkNullString(td.getAttributeValue("operator")));
	    		eecfld.setComparedata(StringUtils.checkNullString(td.getAttributeValue("comparedata")));
	    		eecfld.setDisplaycolor(StringUtils.checkNullString(td.getAttributeValue("displaycolor")));
	    		eecfld.setIsgroup(StringUtils.checkNullString(td.getAttributeValue("isgroup")));
	    		eecfld.setEnable("1");
	    		
	    		int colTag = 0;
	    		for(int k=0;k<cols;k++)
	    		{
	    			if(fldMatrix[i][k]==null)
	    			{
	    				colTag = k;
	    				fldMatrix[i][k] = eecfld;
	    				break;
	    			}
	    		}
	    		if(eecfld.getRowspan()>1 || eecfld.getColspan()>1)
	    		{
	    			for(int k=0;k<eecfld.getRowspan();k++)
	    			{
	    				for(int l=0;l<eecfld.getColspan();l++)
	    				{
	    					if(k!=0 || l!=0)
	    					{
	    						ExcelExpCfgFld temp = new ExcelExpCfgFld();
	    						temp.setFieldname(eecfld.getFieldname());
	    						temp.setDisplayname(eecfld.getDisplayname());
	    						temp.setRowspan(1L);
	    						temp.setColspan(1L);
	    						temp.setWidth(eecfld.getWidth());
	    						temp.setAlign(eecfld.getAlign());
	    						temp.setColcolor(eecfld.getColcolor());
	    						temp.setDatatype(eecfld.getDatatype());
	    						temp.setDatalength(eecfld.getDatalength());
	    						temp.setDatainfo(eecfld.getDatainfo());
	    						temp.setOperator(eecfld.getOperator());
	    						temp.setComparedata(eecfld.getComparedata());
	    						temp.setDisplaycolor(eecfld.getDisplaycolor());
	    						temp.setIsgroup(eecfld.getIsgroup());
	    						temp.setEnable("0");
	    						fldMatrix[i+k][colTag+l] = temp;
	    						
	    					}
	    				}
	    			}
	    		}
	    		
	    	}
	    }
	    datamap.put("tdMatrix", fldMatrix);
		return datamap;
	}
	
	@Deprecated
	public static Map createHtmlData(String cfgid,ExcelManagerService excelManagerService)
	{
		if("".equals(StringUtils.checkNullString(cfgid)))
		{
			return null;
		}
		List<ExcelExpCfgRow> eecrList;
		StringBuffer tabSb = new StringBuffer(); //表格innerHTML
//		StringBuffer rosCosTag = new StringBuffer(); //记录表的每个单元的rolspan*colspan，用逗号隔开，每行用分号隔开
		eecrList = excelManagerService.getExcelExpRowDao().find("from ExcelExpCfgRow where eecid=? order by rownumber asc", new Object[]{cfgid});
		tabSb.append("<table class=\"tableborder\" id=\"cfgTb\">");
		for(int i=0;i<eecrList.size();i++)
		{
			StringBuffer sb_temp = new StringBuffer();
			sb_temp.append("<tr align=\"center\" style=\"cursor:hand;\">");
			List<ExcelExpCfgFld> eecfldList;
			eecfldList = excelManagerService.getExcelExpFldDao().find("from ExcelExpCfgFld where eecrid=? order by ordernum asc", new Object[]{eecrList.get(i).getPid()});
			boolean tag = false;
			for(int j=0;j<eecfldList.size();j++)
			{
				if("1".equals(StringUtils.checkNullString(eecfldList.get(j).getEnable())))
				{
					ExcelExpCfgFld temp = eecfldList.get(j);
					
					String fieldname = StringUtils.checkNullString(temp.getFieldname());
					String displayname = StringUtils.checkNullString(temp.getDisplayname());
					long rowspan = temp.getRowspan();
					long colspan = temp.getColspan();
					long width = temp.getWidth();
					String align = StringUtils.checkNullString(temp.getAlign());
					String colcolor = StringUtils.checkNullString(temp.getColcolor());
					String datatype = StringUtils.checkNullString(temp.getDatatype());
					long datalength = temp.getDatalength();
					String datainfo = StringUtils.checkNullString(temp.getDatainfo());
					String operator = StringUtils.checkNullString(temp.getOperator());
					String comparedata = StringUtils.checkNullString(temp.getComparedata());
					String displaycolor = StringUtils.checkNullString(temp.getDisplaycolor());
					String isgroup = StringUtils.checkNullString(temp.getIsgroup());
					
					sb_temp.append("<td oncontextmenu=\"rightClick(event);\" onclick=\"attSet(event);\"");
					sb_temp.append(" fieldname=\""+fieldname+"\" displayname=\""+displayname+"\" rowspan=\""+rowspan+"\" colspan=\""+colspan+"\"");
					sb_temp.append(" width=\""+width+"\" align=\""+align+"\" colcolor=\""+colcolor+"\" datatype=\""+datatype+"\"");
					sb_temp.append(" datalength=\""+datalength+"\" datainfo=\""+datainfo+"\" operator=\""+operator+"\"");
					sb_temp.append(" comparedata=\""+comparedata+"\" displaycolor=\""+displaycolor+"\" isgroup=\""+isgroup+"\"");
					
					if(StringUtils.checkNullString(temp.getIsgroup()).equals("1"))
					{
						sb_temp.append(" style=\"background:#84C1FF\"");
					}
					sb_temp.append(">");
					sb_temp.append("<span onclick=\"this.parentNode.click();\">"+displayname+"</span>");
					sb_temp.append("</td>");
					tag = true;
//					rosCosTag.append(temp.getRowspan()+"*"+temp.getColspan()+",");
				}
			}
			sb_temp.append("</tr>");
			if(tag)
			{
				tabSb.append(sb_temp.toString());
//				rosCosTag.append(";");
			}
		}
		tabSb.append("</table>");
//		while(rosCosTag.indexOf(",;")!=-1)
//		{
//			rosCosTag.deleteCharAt(rosCosTag.indexOf(",;"));
//		}
//		rosCosTag.deleteCharAt(rosCosTag.length()-1);
		Map map = new HashMap();
		map.put("tbdata", tabSb.toString());
//		map.put("framedata", rosCosTag.toString());
		return map;
	}
	
	/**
	 * 根据EXCEL导出配置ID和导出配置服务构建XML数据到客户端JS解释
	 * @param cfgid 导出配置ID
	 * @param excelManagerService 导出配置服务对象
	 * @return 导出配置表头单元格数据字符串信息，存储在Map中，key为tbdata
	 */
	public static Map createXmlData(String cfgid,ExcelManagerService excelManagerService)
	{
		if("".equals(StringUtils.checkNullString(cfgid)))
		{
			return null;
		}
		List<ExcelExpCfgRow> eecrList;
		StringBuffer tabSb = new StringBuffer();
		eecrList = excelManagerService.getExcelExpRowDao().find("from ExcelExpCfgRow where eecid=? order by rownumber asc", new Object[]{cfgid});
		tabSb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		tabSb.append("<table>\n");
		for(int i=0;i<eecrList.size();i++)
		{
			tabSb.append("<tr id=\"\">\n");
			List<ExcelExpCfgFld> eecfldList;
			eecfldList = excelManagerService.getExcelExpFldDao().find("from ExcelExpCfgFld where eecrid=? order by ordernum asc", new Object[]{eecrList.get(i).getPid()});
			boolean tag = false;
			for(int j=0;j<eecfldList.size();j++)
			{
				ExcelExpCfgFld temp = eecfldList.get(j);
				
				String fieldname = StringUtils.checkNullString(temp.getFieldname());
				String displayname = StringUtils.checkNullString(temp.getDisplayname());
				long rowspan = temp.getRowspan();
				long colspan = temp.getColspan();
				long width = temp.getWidth();
				String align = StringUtils.checkNullString(temp.getAlign());
				String colcolor = StringUtils.checkNullString(temp.getColcolor());
				String datatype = StringUtils.checkNullString(temp.getDatatype());
				long datalength = temp.getDatalength();
				String datainfo = StringUtils.checkNullString(temp.getDatainfo());
				String operator = StringUtils.checkNullString(temp.getOperator());
				String comparedata = StringUtils.checkNullString(temp.getComparedata());
				String displaycolor = StringUtils.checkNullString(temp.getDisplaycolor());
				String isgroup = StringUtils.checkNullString(temp.getIsgroup());
				long ordernum = temp.getOrdernum();
				String enable = temp.getEnable();
				tabSb.append("<td id=\"\" fieldname=\""+fieldname+"\" displayname=\""+displayname+"\" rowspan=\""+rowspan+"\" colspan=\""+colspan
						  +"\" width=\""+width+"\" align=\""+align+"\" colcolor=\""+colcolor+"\" datatype=\""+datatype
						  +"\" datalength=\""+datalength+"\" datainfo=\""+datainfo+"\" operator=\""+operator+"\" comparedata=\""+comparedata
						  +"\" displaycolor=\""+displaycolor+"\" isgroup=\""+isgroup+"\" ordernum=\""+ordernum+"\" enable=\""+enable+"\">");
				tabSb.append("</td>\n");
			}
			tabSb.append("</tr>\n");
		}
		tabSb.append("</table>");
		Map map = new HashMap();
		map.put("tbdata", tabSb.toString());
		return map;
	}
}
