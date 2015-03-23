package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.Internation;
/**
 * 树组件api
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version May 17, 2010 5:29:23 PM
 */
public class TreeManager{
	
	
	private int rRow=0;
	private int rRowD = 0;
	/**
	 * 根据父节点Id和该节点下面的子节点来拼接树菜单集合
	 * @param dtreeBeanList 多个节点的数据集合
	 * @param parentid 父节点Id
	 * @return 返回拼装的树xml节点字符串
	 */
	public String createDhtmlxtreeXml(List<DtreeBean> dtreeBeanList, String parentid){
		DtreeBean dTree = new DtreeBean();
		dTree.setParentId(parentid);
		dTree.setChildList(dtreeBeanList);
		return createDhtmlxtreeXml(dTree);
	}
	
	/**
	 * 创建一列并排的树结构,单列的数据
	 * @param dtreeBeanList
	 * @return 返回并排树的节点字符串
	 */
	public String apposeDhtmlXtreeXml(List<DtreeBean> dtreeBeanList){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buffer.append("<tree ");
		buffer.append("id=\"" + 0 + "\">");
		HashMap map;
		for (DtreeBean tree : dtreeBeanList) {
			buffer.append("<item ");
			buffer.append(" text=\"" + tree.getText() + "\"");
			buffer.append(" id=\"" + tree.getId() + "\"");
			buffer.append(" open=\""+tree.getOpen()+"\" ");
			buffer.append(" im0=\"" + tree.getIm0() + "\"");
			buffer.append(" im1=\"" + tree.getIm1() + "\"");
			buffer.append(" im2=\"" + tree.getIm2() + "\"");
			buffer.append(" child=\"" + tree.getChild() + "\"");
			buffer.append(" checked=\"" + tree.getChecked() +"\" ");
			buffer.append(" disabled=\"" + tree.getDisabled() + "\"");
			buffer.append(" nocheckbox=\""+tree.getNocheckbox()+"\" ");
			buffer.append(" >");
			
			map = tree.getUserdata();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String name = iterator.next();
				String value =com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(map.get(name));// (String) map.get(name);
				if (value == null || "".equals(value)) {
					value = "";
				}
				buffer.append("<userdata ");
				buffer.append(" name=\"" + name + "\">");
				buffer.append("<![CDATA[" + value + "]]>");
				buffer.append("</userdata>");
			}
			
			buffer.append("</item>");
		}
		buffer.append("</tree>");
		return buffer.toString();
	}
	
	/**
	 * 根据节点对象来拼接树节点信息
	 * @param dtreeBean 节点信息
	 * @return 树的xml数据
	 */
	@Deprecated
	private String createDhtmlxtreeXml(DtreeBean dtreeBean) {
		StringBuffer buffer = new StringBuffer();
		String parentid = dtreeBean.getParentId();
		List<DtreeBean> childrenTree = dtreeBean.getChildList();
		if(childrenTree == null){
			childrenTree = new ArrayList<DtreeBean>();
		}
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buffer.append("<tree ");
		parentid = (!StringUtils.hasLength(parentid)) ? "0" : parentid;
		if((childrenTree==null || childrenTree.size()<=0) && "0".equals(parentid))
		{
			buffer.append("id=\"" + parentid + "\">");
			buffer.append("<item ");
			buffer.append(" text=\""+Internation.language("com_lb_noData")+"！\"");//当没有数据的时候返回的显示字符串
			buffer.append(" id=\"nodata\"");//当没有数据的时候返回的id为nodata，可以通过该ID进行点击控制
			buffer.append(" open=\"\" ");
			buffer.append(" im0=\"\"");
			buffer.append(" im1=\"\"");
			buffer.append(" im2=\"\"");
			buffer.append(" child=\"0\"");
			buffer.append(" checked=\"\" ");
			buffer.append(" nocheckbox=\"\"");
			buffer.append(" >");
			buffer.append("</item>");
		}
		else
		{
			buffer.append("id=\"" + parentid + "\">");
		}
		HashMap map;
		for (DtreeBean tree : childrenTree) {
			buffer.append("<item ");
			buffer.append(" text=\"" + tree.getText() + "\"");
			buffer.append(" id=\"" + tree.getId() + "\"");
			buffer.append(" open=\""+tree.getOpen()+"\" ");
			buffer.append(" im0=\"" + tree.getIm0() + "\"");
			buffer.append(" im1=\"" + tree.getIm1() + "\"");
			buffer.append(" im2=\"" + tree.getIm2() + "\"");
			buffer.append(" child=\"" + tree.getChild() + "\"");
			buffer.append(" disabled=\"" + tree.getDisabled() + "\"");
			buffer.append(" checked=\"" + tree.getChecked() +"\" ");
			buffer.append(" nocheckbox=\""+ tree.getNocheckbox() + "\"");
			buffer.append(" >");
			
			map = tree.getUserdata();
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String name = iterator.next();
				String value =com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(map.get(name));// (String) map.get(name);
				if (value == null || "".equals(value)) {
					value = "";
				}
				buffer.append("<userdata ");
				buffer.append(" name=\"" + name + "\">");
				buffer.append("<![CDATA[" + value + "]]>");
				buffer.append("</userdata>");
			}

			buffer.append("</item>");
		}
		buffer.append("</tree>");
		return buffer.toString();
	}
	
	public String createDHTMLXtreeXML(DtreeBean dtreeBean){
		Document xml = DocumentHelper.createDocument();
		Element root = xml.addElement("tree");
		root.addAttribute("id", dtreeBean.getId());
		root.addAttribute("text", dtreeBean.getText());
		xml.setRootElement(root);
		addItem(dtreeBean,root);
		return xml.asXML();
	}
	
	private void addItem(DtreeBean dtreeBean,Element element){
		for(DtreeBean d : dtreeBean.getChildList()){
			Element item = element.addElement("item");
			item.addAttribute("id", d.getId());
			item.addAttribute("text", d.getText());
			
			for(Entry<String, String> entry : d.getUserdata().entrySet()){
				Element userdata = item.addElement("userdata");
				userdata.addAttribute("name",entry.getKey());
				userdata.setText(entry.getValue());
			}
			
			if(d.getChildList() != null){
				addItem(d,item);
			}
		}
	}
	
	/**
	 * 根据完整树形结构直接进行树xml参数组装
	 * @param userList 需展示的树数据 如下(部门树)：
	 * level  id       text
	 * 1      0000200  集团公司
	 * 2      0000201  重庆公司
	 * 3      0000203  网络部
	 * 4      0000210  数据部
	 * 3      0000204  信息部
	 * 4      0000211  信息中心
	 * @return 返回整个树脚本所需的xml参数 如下(部门树)：
	 * <?xml version="1.0" encoding="utf-8"?>
	 * <tree id="0">
	 *	<item id="0000200" text="集团公司">
	 *		<item id="0000201" text="重庆公司">
	 *			<item id="0000203" text="网络部">
	 *				<item id="0000210" text="数据部">
	 *			</item>
	 *		    <item id="0000204" text="信息部">
	 *			    <item id="0000211" text="信息中心"></item>
	 *		    </item>
	 *		</item>
	 *	 </item>
	 *	</tree>
	 */
	public String createDhtmlxtreeXml(List<MenuDtree> userList){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buffer.append("<tree id=\"0\">");
		int userListLen = 0;
		if(userList!=null)
			userListLen = userList.size();
		int index = 0;
		HashMap map;
		for(int i=0;i<userListLen;i++){
			MenuDtree userData = userList.get(i);
			if(index<userData.getLevel()){//open=\"1\"
				
				buffer.append("<item id=\"" + userData.getId() + "\" text=\""+ userData.getText() + "\" child=\""+userData.getChild()+"\" im0=\"" + userData.getIm0() + "\" im1=\"" + userData.getIm1() + "\" im2=\"" + userData.getIm2() + "\" >");
				map = userData.getUserdata();
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String name = iterator.next();
					String value = com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(map.get(name));//(String) map.get(name);
					if (value == null || "".equals(value)) {
						value = "";
					}
					buffer.append("<userdata ");
					buffer.append(" name=\"" + name + "\">");
					buffer.append("<![CDATA[" + value + "]]>");
					buffer.append("</userdata>");
				}
			}else if(index==userData.getLevel()){
				buffer.append("</item>");
				buffer.append("<item id=\"" + userData.getId() + "\" text=\""+ userData.getText() + "\" child=\""+userData.getChild()+"\" im0=\"" + userData.getIm0() + "\" im1=\"" + userData.getIm1() + "\" im2=\"" + userData.getIm2() + "\" >");
				map = userData.getUserdata();
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String name = iterator.next();
					String value =com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(map.get(name));// (String) map.get(name);
					if (value == null || "".equals(value)) {
						value = "";
					}
					buffer.append("<userdata ");
					buffer.append(" name=\"" + name + "\">");
					buffer.append("<![CDATA[" + value + "]]>");
					buffer.append("</userdata>");
				}
			}else{
				for (int j = index - userData.getLevel(); j >= 0; j--) {
					buffer.append("</item>");
				}
				buffer.append("<item id=\"" + userData.getId() + "\" text=\""+ userData.getText() + "\" child=\""+userData.getChild()+"\" im0=\"" + userData.getIm0() + "\" im1=\"" + userData.getIm1() + "\" im2=\"" + userData.getIm2() + "\" >");
				map = userData.getUserdata();
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String name = iterator.next();
					String value = com.ultrapower.eoms.common.core.util.StringUtils.checkNullString(map.get(name));//(String) map.get(name);
					if (value == null || "".equals(value)) {
						value = "";
					}
					buffer.append("<userdata ");
					buffer.append(" name=\"" + name + "\">");
					buffer.append("<![CDATA[" + value + "]]>");
					buffer.append("</userdata>");
				}
			}
			index = userData.getLevel();
		}
		for (int i = 0; i < index; i++) {
			buffer.append("</item>");
		}
		buffer.append("</tree>");
		return buffer.toString();
	}
	
	/**
	 * 根据完整树形结构数据直接进行树xml参数组装,并且默认打开传入的节点信息
	 * @param userList 树的信息集合
	 * @param openIdList 需要打开和默认选中的节点
	 * @return 返回组装好的树xml
	 */
	public String createDhtmlxtreeXml(List<MenuDtree> userList, List<String> openIdList){
		if(openIdList == null || openIdList.size() <= 0)
		{
			return createDhtmlxtreeXml(userList);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buffer.append("<tree id=\"0\">");
		int userListLen = 0;
		if(userList!=null)
			userListLen = userList.size();
		int index = 0;
		for(int i=0;i<userListLen;i++){
			MenuDtree userData = userList.get(i);
			String id = userData.getId();
			String open = "";
			String select = "";
			if(openIdList != null && openIdList.size() > 0)
			{
				if(openIdList.indexOf(id) >= 0)
				{
					open = "1";
				}
				if(id.equals(openIdList.get(openIdList.size()-1)))
				{
					select = "1";
				}
			}
			if(index<userData.getLevel()){//open=\"1\"
				
				buffer.append("<item id=\"" + id + "\" text=\""+ userData.getText() + "\" open=\""+open+"\" select=\""+select+"\" child=\""+userData.getChild()+"\" im0=\"" + userData.getIm0() + "\" im1=\"" + userData.getIm1() + "\" im2=\"" + userData.getIm2() + "\" >");
				HashMap map = userData.getUserdata();
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String name = iterator.next();
					String value = (String) map.get(name);
					if (value == null || "".equals(value)) {
						value = "";
					}
					buffer.append("<userdata ");
					buffer.append(" name=\"" + name + "\">");
					buffer.append("<![CDATA[" + value + "]]>");
					buffer.append("</userdata>");
				}
			}else if(index==userData.getLevel()){
				buffer.append("</item>");
				buffer.append("<item id=\"" + id + "\" text=\""+ userData.getText() + "\" open=\""+open+"\" select=\""+select+"\" child=\""+userData.getChild()+"\" im0=\"" + userData.getIm0() + "\" im1=\"" + userData.getIm1() + "\" im2=\"" + userData.getIm2() + "\" >");
				HashMap map = userData.getUserdata();
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String name = iterator.next();
					String value = (String) map.get(name);
					if (value == null || "".equals(value)) {
						value = "";
					}
					buffer.append("<userdata ");
					buffer.append(" name=\"" + name + "\">");
					buffer.append("<![CDATA[" + value + "]]>");
					buffer.append("</userdata>");
				}
			}else{
				for (int j = index - userData.getLevel(); j >= 0; j--) {
					buffer.append("</item>");
				}
				buffer.append("<item id=\"" + id + "\" text=\""+ userData.getText() + "\" open=\""+open+"\" select=\""+select+"\" child=\""+userData.getChild()+"\" im0=\"" + userData.getIm0() + "\" im1=\"" + userData.getIm1() + "\" im2=\"" + userData.getIm2() + "\" >");
				HashMap map = userData.getUserdata();
				Iterator<String> iterator = map.keySet().iterator();
				while (iterator.hasNext()) {
					String name = iterator.next();
					String value = (String) map.get(name);
					if (value == null || "".equals(value)) {
						value = "";
					}
					buffer.append("<userdata ");
					buffer.append(" name=\"" + name + "\">");
					buffer.append("<![CDATA[" + value + "]]>");
					buffer.append("</userdata>");
				}
			}
			index = userData.getLevel();
		}
		for (int i = 0; i < index; i++) {
			buffer.append("</item>");
		}
		buffer.append("</tree>");
		return buffer.toString();
	}
	
	/**
	 * 递归的方式进行有序树的xml生成
	 * @param id 根节点ID
	 * @param userList 数据集合
	 * @param byType 采用的遍历方式
	 * @param openLevel 打开级别
	 * @return 满足树格式的xml数据
	 */
	public String createtreeRecursion(String id,List<MenuDtree> userList,String byType,int openLevel){
		TreeManager treeManager = new TreeManager();
		return treeManager.createDhtmlXtreeXml(userList,byType,openLevel);
	}
	
	/**
	 * 根据完整树形结构直接进行树xml参数组装
	 * @param userList 需展示的树数据 如下(部门树)：
	 * level  id       text      parentid
	 * 1      0000200  集团公司    0 
	 * 2      0000201  重庆公司    0000200
	 * 3      0000203  网络部      0000201 
	 * 4      0000210  数据部      0000203
	 * 3      0000204  信息部      0000201
	 * 4      0000211  信息中心    0000204
	 * @param byType   参数说明：如上数据所示,如果按照level,来进行过滤组装则 赋参数值"level"; 如果按照parentid,来进行过滤组装则 赋参数值"parentid" 
	 * @param openLevel 节点打开级别标示 如果是1 则数据默认将一级下面的根节点展示
	 * @return 返回整个树脚本所需的xml参数 如下(部门树)：
	 * <?xml version="1.0" encoding="utf-8"?>
	 * <tree id="0">
	 *	<item id="0000200" text="集团公司">
	 *		<item id="0000201" text="重庆公司">
	 *			<item id="0000203" text="网络部">
	 *				<item id="0000210" text="数据部">
	 *			</item>
	 *		    <item id="0000204" text="信息部">
	 *			    <item id="0000211" text="信息中心"></item>
	 *		    </item>
	 *		</item>
	 *	 </item>
	 *	</tree>
	 */
	private String createDhtmlXtreeXml(List<MenuDtree> userList,String byType,int openLevel){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buffer.append("<tree id=\"0\">");
		if(userList!=null)
			buffer.append(recursionXml(userList,byType,openLevel));
		buffer.append("</tree>");
		return buffer.toString();
	} 
	
	/**
	 * 递归查询树的结构信息 
	 * @param userList
	 * @param byType
	 * @return str
	 */
	private String recursionXml(List<MenuDtree> userList,String byType,int openLevel){
		int lstCount=0;
		if(userList!=null)
			lstCount=userList.size();
		StringBuffer buffer = new StringBuffer();
		MenuDtree userData = userList.get(rRow);
		int level = 0;
		String pid = "";
		if(byType=="parentid")
			pid = userData.getId();
		else   
			level=userData.getLevel();
		if(userData.getLevel()==openLevel)
			buffer.append("<item id=\"" + userData.getId() + "\" text=\""+ userData.getText() + "\" child=\"1\" open=\"1\" im0=\"" + userData.getIm0() + "\" im1=\"" + userData.getIm1() + "\" im2=\"" + userData.getIm2() + "\" >");
		else
			buffer.append("<item id=\"" + userData.getId() + "\" text=\""+ userData.getText() + "\" child=\"1\" im0=\"" + userData.getIm0() + "\" im1=\"" + userData.getIm1() + "\" im2=\"" + userData.getIm2() + "\" >");
		 
		HashMap map = userData.getUserdata();
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String name = iterator.next();
			String value = (String) map.get(name);
			if (value == null || "".equals(value)) {
				value = "";
			}
			buffer.append("<userdata ");
			buffer.append(" name=\"" + name + "\">");
			buffer.append("<![CDATA[" + value + "]]>");
			buffer.append("</userdata>");
		}
		while(true)
		{
			if(lstCount>(rRow+1))
			{
				userData = userList.get(rRow+1);
				if(byType=="parentid"){
					if(pid.equals(userData.getParentid()))
					{
						rRow++;
						buffer.append(recursionXml(userList,byType,openLevel));
					}
					else
						break;
				}else{
					if(level<userData.getLevel())
					{
						rRow++;
						buffer.append(recursionXml(userList,byType,openLevel));
					}
					else
						break;
				}
			}
			else
				break;
		}
		buffer.append("</item>");
		return buffer.toString();
	}
}
