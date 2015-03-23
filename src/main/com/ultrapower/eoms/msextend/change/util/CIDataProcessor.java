package com.ultrapower.eoms.msextend.change.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.plugin.easyui.model.EasyuiTree;
import com.ultrapower.eoms.msextend.change.model.CIClass;
import com.ultrapower.eoms.msextend.change.model.CIRelevance;
import com.ultrapower.eoms.ultrasm.model.UserInfo;

public class CIDataProcessor{
	/**
	 * 根据CI分类获取用于easyui展示下拉树的json
	 */
	public static String getAllCICLassesJson4Easyui(List<CIClass> ciClasses){
		EasyuiTree root = new EasyuiTree();
		generateEasyuiTree(root,ciClasses);
		
//		return JSONArray.fromObject(easyuiTrees).toString();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			
			@Override
			public boolean apply(Object source, String name, Object value) {
				if(name.equals("children") && value == null) return true;
				return false;
			}
		});
		return JSONArray.fromObject(root.getChildren(),jsonConfig).toString();
	}
	
	private static void generateEasyuiTree(EasyuiTree parent,List<CIClass> children){
		List<EasyuiTree> treeChildren = new ArrayList<EasyuiTree>(); 
		for(CIClass ciClass : children){
			EasyuiTree treeChild = new EasyuiTree();
			treeChild.setId(ciClass.getName());
			treeChild.setText(ciClass.getDisplayName());
			if(ciClass.getChildren()!=null && ciClass.getChildren().size() != 0){
				treeChild.setState("closed");
				generateEasyuiTree(treeChild, ciClass.getChildren());
			}
			treeChildren.add(treeChild);
		}
		parent.setChildren(treeChildren);
	}

	public static List<CIRelevance> ciInfoToCIrelevance(String[] ciInfo,
			String baseschema, String baseid) throws ParseException {
		List<CIRelevance> ciRelevances = new ArrayList<CIRelevance>();
		JSONParser jsonParser = new JSONParser();
		for(String ci : ciInfo){
			org.json.simple.JSONObject cijson = (org.json.simple.JSONObject) jsonParser.parse(ci);
			CIRelevance ciRelevance = new CIRelevance();
			ciRelevance.setBaseid(baseid);
			ciRelevance.setBaseschema(baseschema);
			CIClass ciClass = new CIClass();
			ciClass.setName((String) cijson.get("ciclass"));
			ciRelevance.setCiClass(ciClass);
			ciRelevance.setCiId((String) cijson.get("ciId"));
			ciRelevance.setCreatetime(Long.toString(TimeUtils.getCurrentTime()));
			UserInfo userInfo = new UserInfo();
			userInfo.setPid(WebApplicationManager.getUserSession().getPid());
			ciRelevance.setCreator(userInfo);
			ciRelevance.setDisplayLabel((String) cijson.get("displayLabel"));
			ciRelevance.setName((String) cijson.get("name"));
			ciRelevances.add(ciRelevance);
		}
		
		return ciRelevances;
	}
}
