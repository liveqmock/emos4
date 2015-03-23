package com.ultrapower.eoms.common.core.component.tree.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.ultrapower.eoms.common.core.component.tree.model.SelectOptionBean;
import com.ultrapower.eoms.common.core.component.tree.service.SelectTreeSrcDataService;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.web.BaseAction;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 29, 2010 6:09:48 PM
 */
public class BidirectSelectTree extends BaseAction{

	private static final long serialVersionUID = 1L;
	private SelectTreeSrcDataService selectTreeSrc;
	private String sourceDataObj;//集成接口SelectTreeSrcDataService   的实现的类注册ID
	private String targetDataArr;//右侧数据的数组 eg: {"213,张三";"214,李四";"215,王五"}
	private String multiple;//单选/多选   当multiple="1" 为单选 其它多选
	private String isUser;//人/组   0：部门  1：人员 
	
	private List<SelectOptionBean> leftData;//左侧框数据
	private List<SelectOptionBean> rightData;//右侧框数据
	
	private String input_id;
	private String input_name;
	private String par;
	
	/**
	 * 自定义选择数据
	 * @return
	 */
	public String getParaSelectTree(){
		
		//左侧选择数据准备
		leftData = new ArrayList<SelectOptionBean>();
		Object obj = null;
		if(!StringUtils.checkNullString(sourceDataObj).equals("")){
			try{
				selectTreeSrc = (SelectTreeSrcDataService)WebApplicationManager.getBean(sourceDataObj);//获取左侧数据实例
				obj = selectTreeSrc.getSourceDataObj(par);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(obj instanceof HashMap){
			HashMap map = (HashMap)obj;
			Iterator<String> it = map.keySet().iterator();
			SelectOptionBean selectOptionBean;
			while (it.hasNext()) {
				String key = it.next();
				String value = (String) map.get(key);
				selectOptionBean = new SelectOptionBean();
				selectOptionBean.setKey(key);
				selectOptionBean.setValue(value);
				leftData.add(selectOptionBean);
			}
		}else if(obj instanceof List){
			List<SelectOptionBean> list = (List<SelectOptionBean>)obj;
			leftData = list;
		}else{}
		
		//右侧选择数据准备  ""
		rightData = new ArrayList<SelectOptionBean>();
		if(targetDataArr!=null&&!"".equals(targetDataArr)){
			try {
				targetDataArr = URLDecoder.decode(targetDataArr,"UTF-8");
//				if (!targetDataArr.equals(new String(targetDataArr.getBytes(),"gb2312"))) {
//					targetDataArr = new String(targetDataArr.getBytes("ISO8859-1"),"UTF8");
//				}
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String []targetDataArrData = targetDataArr.split(";");
			SelectOptionBean selectOptionBean;
			for(int i=0;i<targetDataArrData.length;i++){
				String[] optionValue = targetDataArrData[i].split(",");
				selectOptionBean = new SelectOptionBean();
				selectOptionBean.setKey(optionValue[0]);
				selectOptionBean.setValue(optionValue[1]);
				rightData.add(selectOptionBean);
			}
		}
			
			
		this.getRequest().setAttribute("isUser", isUser);
		this.getRequest().setAttribute("input_id", input_id);
		this.getRequest().setAttribute("input_name", input_name);
		//单选 & 多选
		this.getRequest().setAttribute("multiple", multiple);
		
		return "listDataTree";
	}

	/**
	 * 人员、部门树选择
	 * @return
	 */
	public String getParaSelectDepUserTree(){
		
		//右侧选择数据准备
		rightData = new ArrayList<SelectOptionBean>();
		try{
			targetDataArr = new String(targetDataArr.getBytes("ISO8859-1"),"utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(targetDataArr!=null&&!"".equals(targetDataArr)){
			try {
				targetDataArr = new String(targetDataArr.getBytes("ISO8859-1"),"UTF8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			String []targetDataArrData = targetDataArr.split(";");	
			SelectOptionBean selectOptionBean;
			for(int i=0;i<targetDataArrData.length;i++){
				String[] optionValue = targetDataArrData[i].split(",");
				selectOptionBean = new SelectOptionBean();
				selectOptionBean.setKey(optionValue[0]);
				selectOptionBean.setKey(optionValue[1]);
				rightData.add(selectOptionBean);
			}
		}
		
		this.getRequest().setAttribute("input_id", input_id);
		this.getRequest().setAttribute("input_name", input_name);
		//单选 & 多选
		this.getRequest().setAttribute("multiple", multiple);
		return "depUserDataTree";
	}

	public String getSourceDataObj() {
		return sourceDataObj;
	}

	public void setSourceDataObj(String sourceDataObj) {
		this.sourceDataObj = sourceDataObj;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getIsUser() {
		return isUser;
	}

	public void setIsUser(String isUser) {
		this.isUser = isUser;
	}

	public List<SelectOptionBean> getLeftData() {
		return leftData;
	}

	public void setLeftData(List<SelectOptionBean> leftData) {
		this.leftData = leftData;
	}

	public List<SelectOptionBean> getRightData() {
		return rightData;
	}

	public void setRightData(List<SelectOptionBean> rightData) {
		this.rightData = rightData;
	}

	public String getTargetDataArr() {
		return targetDataArr;
	}

	public void setTargetDataArr(String targetDataArr) {
		this.targetDataArr = targetDataArr;
	}


	public String getInput_id() {
		return input_id;
	}


	public void setInput_id(String input_id) {
		this.input_id = input_id;
	}


	public String getInput_name() {
		return input_name;
	}


	public void setInput_name(String input_name) {
		this.input_name = input_name;
	}

	public String getPar() {
		return par;
	}

	public void setPar(String par) {
		this.par = par;
	}
}
