package com.ultrapower.eoms.msextend.notice.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.msextend.notice.service.INoticeLevelTreeService;

/**
 * @author yxg
 * @version 创建时间：Dec 27, 2012 11:30:26 AM
 * 类说明：
 */

public class NoticeLevelTreeImpl implements INoticeLevelTreeService{
	
	private QueryAdapter queryAdapter = new QueryAdapter();
	
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid
	 * @return
	 */
	public String getLevelTreeXml(String parentid, String dtcode){
		if(parentid=="")
			return "";
		List<DtreeBean> dtreeChildrenList = null;
		if("0".equals(parentid))
			dtreeChildrenList = getChild(dtcode);
		else
			dtreeChildrenList = getChildX(parentid);
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	/**
	 * 获取父节点下的节点
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChild(String dtcode){
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid, dtname, dtcode");
		sql.append(" from bs_t_sm_dictype dt");
		sql.append(" where 1 = 1");
		if(!"".equals(StringUtils.checkNullString(dtcode)) && !"0".equals(StringUtils.checkNullString(dtcode)))
			sql.append(" and dtcode = '" + dtcode + "'");
		sql.append(" order by nlssort(dtname, 'NLS_SORT=SCHINESE_PINYIN_M')");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("pid"))+":"+StringUtils.checkNullString(dataRow.getString("dtcode")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("dtname")));
			HashMap map = new HashMap();
			map.put("type", "1");
			dtreeBean.setUserdata(map);
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
	/**
	 * 获取父节点下的节点
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChildX(String parentid){
		if(parentid=="")
			return null;
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pid, diname, parentid,divalue");
		sql.append(" from bs_t_sm_dicitem di");
		sql.append(" where 1 = 1 and di.DIVALUE <> '0'");
		if(parentid.indexOf(":") > 0)
		{
			String[] dt = parentid.split(":");
			sql.append(" and parentid = '0' and dtid = '"+dt[0]+"'");
		}
		else
			sql.append(" and parentid = '"+parentid+"'");
		sql.append(" order by ordernum");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("pid")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("diname")));
			HashMap map = new HashMap();
			map.put("divalue", StringUtils.checkNullString(dataRow.getString("divalue")));
			dtreeBean.setUserdata(map);
			dtreeList.add(dtreeBean);
		}
		return dtreeList;
	}
	
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
	 * 根据节点对象来拼接树节点信息
	 * @param dtreeBean 节点信息
	 * @return 树的xml数据
	 */
	private String createDhtmlxtreeXml(DtreeBean dtreeBean) {
		StringBuffer buffer = new StringBuffer();
		String parentid = dtreeBean.getParentId();
		List<DtreeBean> childrenTree = dtreeBean.getChildList();
		buffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		buffer.append("<tree ");
		parentid = (!org.springframework.util.StringUtils.hasLength(parentid)) ? "0" : parentid;
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
	
}
