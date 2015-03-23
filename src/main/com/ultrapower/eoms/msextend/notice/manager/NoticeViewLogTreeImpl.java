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
import com.ultrapower.eoms.msextend.notice.service.INoticeViewLogTreeService;

/**
 * @author yxg
 * @version 创建时间：Dec 27, 2012 11:30:26 AM
 * 类说明：
 */

public class NoticeViewLogTreeImpl implements INoticeViewLogTreeService{
	
	private QueryAdapter queryAdapter = new QueryAdapter();
	
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid
	 * @return
	 */
	public String getViewLogTreeXml(String parentid, String dtcode){
		if(parentid=="")
			return "";
		List<DtreeBean> dtreeChildrenList = null;
		if("0".equals(parentid)){
			dtreeChildrenList = getChild(dtcode);
		}else if("01".equals(parentid)){
			dtreeChildrenList = getChildX(parentid);
		}else{
			dtreeChildrenList = new ArrayList<DtreeBean>();
		}
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentid);
	}
	
	/**
	 * 获取父节点下的节点
	 * @param parentid 父节点
	 * @return 该父节点下的节点集合
	 */
	private List<DtreeBean> getChild(String dtcode){
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		
		DtreeBean dtreeBean0 = new DtreeBean();
		dtreeBean0.setId("03");
		dtreeBean0.setText("未查看公告");
		dtreeBean0.setChild("0");
		dtreeList.add(dtreeBean0);
		
//		DtreeBean dtreeBean = new DtreeBean();
//		dtreeBean.setId("01");
//		dtreeBean.setText("公告发布人");
//		dtreeBean.setChild("1");
//		dtreeList.add(dtreeBean);
//		
//		DtreeBean dtreeBean2 = new DtreeBean();
//		dtreeBean2.setId("02");
//		dtreeBean2.setChild("0");
//		dtreeBean2.setText("已删除的公告");
//		dtreeList.add(dtreeBean2);
		
		DtreeBean dtreeBean2 = new DtreeBean();
		dtreeBean2.setId("04");
		dtreeBean2.setChild("0");
		dtreeBean2.setText("已查看的公告");
		dtreeList.add(dtreeBean2);
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
		sql.append("select ni.CREATOR_NAME creatorname,ni.CREATOR_ID creatorid");
		sql.append(" from bs_t_notice_info ni");
		sql.append(" where 1 = 1");
		sql.append(" group by ni.CREATOR_NAME,ni.CREATOR_ID");
		sql.append(" order by ni.CREATOR_ID");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		DtreeBean dtreeBean;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			dtreeBean = new DtreeBean();
			dtreeBean.setId(StringUtils.checkNullString(dataRow.getString("creatorid")));
			dtreeBean.setText(StringUtils.checkNullString(dataRow.getString("creatorname")));
			dtreeBean.setIm0("userImg.png");
			dtreeBean.setIm1("userImg.png");
			dtreeBean.setIm2("userImg.png");
			dtreeBean.setChild("0");
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
