package com.ultrapower.eoms.workflow.sheet.export.manager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.jdom.Element;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.plugin.datagrid.grid.Limit;
import com.ultrapower.eoms.common.plugin.datagrid.util.RequestUtils;
import com.ultrapower.eoms.common.plugin.excelutils.ExcelWriter;
import com.ultrapower.eoms.workflow.sheet.export.service.IsheetExportService;

public class SheetExportServiceImpl implements IsheetExportService {
	private static Map<String, ExportConfig> exportXmlMap = new HashMap<String, ExportConfig>();

	public String exportExcel(String sqlName, HttpServletRequest request, String isAll, String wfTypeStr, String baseSchema) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Limit limit = RequestUtils.getPageLimit(request);
		String orderby = SqlReplace.getOrderby(limit.getSortField(), limit.getSortType());
		if (!"".equals(orderby)) {
			hashMap.put("orderby", orderby);
		}
		if(null!=wfTypeStr && !"".equals(wfTypeStr)){
			hashMap.put("wfTypeStr", wfTypeStr);
		}
		if(null!=baseSchema && !"".equals(baseSchema)){
			hashMap.put("baseSchema", baseSchema);
		}
		
		ExportConfig config = getExportConfig(sqlName);
		RQuery rQuery = new RQuery(sqlName, hashMap);
		if(null!=isAll && "all".equals(isAll)){
			rQuery.setPage(1);
			rQuery.setPageSize(50000);
		}else{
			rQuery.setPage(limit.getPage() <= 0 ? 0 : limit.getPage());
			rQuery.setPageSize(limit.getPageSize());
		}
		rQuery.setIsCount(2);
		ExcelWriter ew = new ExcelWriter();
		String folderPath = StringUtils.checkNullString(PropertiesUtils.getProperty("eoms.export.excel.path"));//存储文件夹路径
		if (!folderPath.endsWith(File.separator))
			folderPath += File.separator;
		ew.setExcelFilePath(folderPath + getTimeStr() + File.separator);
		ew.setExcelName(UUID.randomUUID() + ".xls");
		ew.addTitle(config.getFieldNames());
		
		DataTable dt = rQuery.getDataTable();
		List<List<String[]>> sheet = formatDataTable(dt, config);
		ew.addDatas(sheet);
//		while (true) {
//			DataTable dt = rQuery.getDataTable();
//			List<List<String[]>> sheet = formatDataTable(dt, config);
//			ew.addDatas(sheet);
//			if (rQuery.getPage() == rQuery.getPageCount())
//				break;
//			rQuery.setPage(rQuery.getPage() + 1);
//		}
		String filePath = ew.createExcel();
		return filePath;
	}

	private List<List<String[]>> formatDataTable(DataTable dt, ExportConfig config) {
		String[] index = config.getFields();
		String[] types = config.getTypes();
		List<List<String[]>> l = new ArrayList<List<String[]>>();
		for (int i = 0; i < dt.length(); i++) {
			DataRow row = dt.getDataRow(i);
			List<String[]> lr = new ArrayList<String[]>();
			for (int j = 0; j < index.length; j++) {
				String value = row.getString(index[j]);
				if (types[j].equals("date")) {
					if(org.apache.commons.lang3.StringUtils.isEmpty(value)){
						value = "0";
					}else{
						value = TimeUtils.formatIntToDateString(Long.parseLong(value));
					}
				}
				lr.add(new String[] { String.valueOf(j), value });
			}
			l.add(lr);
		}
		return l;
	}

	/**
	 * @param filePath 文件路径
	 * @param response
	 * @param isOnLine 是否支持在线打开
	 */
	public void downLoad(String filePath, HttpServletResponse response,
			boolean isOnLine) throws IOException {
		File f = new File(filePath);
		if (!f.exists()) {
			response.sendError(404, "File not found!");
			return;
		}
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
		byte[] buf = new byte[1024];
		int len = 0;

		response.reset(); // 非常重要
		if (isOnLine) { // 在线打开方式
			URL u = new URL("file:///" + filePath);
			response.setContentType(u.openConnection().getContentType());
			response.setHeader("Content-Disposition",
					"inline; filename=" + f.getName());
			// 文件名应该编码成UTF-8
		} else { // 纯下载方式
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
		}
		OutputStream out = response.getOutputStream();
		while ((len = br.read(buf)) > 0)
			out.write(buf, 0, len);
		br.close();
		out.close();
	}
	
	public void reflushExportConfig(String name) {
		Element sqlquery = (Element) StartUp.sqlmapElement.get(name);
		Element export = sqlquery.getChild("export");
		List<Element> fields = export.getChildren("field");
		ExportConfig config = new ExportConfig();
		for (Element field : fields) {
			String colname = field.getAttribute("colname").getValue();
			String coltype = field.getAttribute("type").getValue();
			String title = field.getAttribute("title").getValue();
			FieldConfig fieldConfig = new FieldConfig(colname, Internation.language(title), coltype);
			config.add(fieldConfig);
		}
		exportXmlMap.put(name, config);
	}
	
	private ExportConfig getExportConfig(String name) {
		if (exportXmlMap.size() == 0 || !exportXmlMap.containsKey(name)) {
			reflushExportConfig(name);
		}
		return exportXmlMap.get(name);
	}
	
	private String getTimeStr(){
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		String str = cal.get(Calendar.YEAR) + "-"
				+ (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID());
	}
}

class ExportConfig {
	private ArrayList<FieldConfig> fields = new ArrayList<FieldConfig>();
	private int size = 0;

	public void add(FieldConfig fieldConfig) {
		fields.add(fieldConfig);
		size++;
	}

	public String[] getFields() {
		String[] fields = new String[size];
		for (int i = 0; i < size; i++) {
			fields[i] = this.fields.get(i).getField();
		}
		return fields;
	}

	public String[] getFieldNames() {
		String[] fields = new String[size];
		for (int i = 0; i < size; i++) {
			fields[i] = this.fields.get(i).getFieldName();
		}
		return fields;
	}
	public String[] getTypes() {
		String[] types = new String[size];
		for (int i = 0; i < size; i++) {
			types[i] = this.fields.get(i).getType();
		}
		return types;
	}
	
	public int size(){
		return size;
	}
}

class FieldConfig {
	private String field;
	private String fieldName;
	private String type;

	public FieldConfig() {

	}

	public FieldConfig(String field, String fieldName, String type) {
		this.field = field;
		this.fieldName = fieldName;
		this.type = type;
	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}