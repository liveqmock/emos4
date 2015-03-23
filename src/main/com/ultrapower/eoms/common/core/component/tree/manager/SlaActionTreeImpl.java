package com.ultrapower.eoms.common.core.component.tree.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.sla.model.SlaMailAction;
import com.ultrapower.eoms.common.core.component.sla.model.SlaSmAction;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;

public class SlaActionTreeImpl extends TreeManager {
	public static final String SHORT_MESSAGE_NOTICE = "smNotice"; //短信通知，和字典值一样
	public static final String EMAIL_NOTICE = "emailNotice"; //邮件通知，和字典值一样
	private IDao<SlaSmAction> slaSmActionDao;
	private IDao<SlaMailAction> slaMailActionDao;
	
	public SlaActionTreeImpl()
	{
		slaSmActionDao = (IDao<SlaSmAction>)WebApplicationManager.getBean("slaSmActionDao");
		slaMailActionDao = (IDao<SlaMailAction>)WebApplicationManager.getBean("slaMailActionDao");
	}
	
	/**
	 * 获得SLA动作类型树xml字符串
	 * @param parentId 父id
	 * @return SLA动作树xml字符串
	 */
	public String getActionTypeTreeXmlStr(String parentId)
	{
		if("".equals(StringUtils.checkNullString(parentId)))
		{
			return "";
		}
		List<DtreeBean> dtreeChildrenList = getActionLst();
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentId);
	}
	
	/**
	 * 根据动作ID获得业务树xml字符串
	 * @param parentId 父id
	 * @return 业务树xml字符串
	 */
	public String getTransTreeXmlStr(String parentId)
	{
		if("".equals(StringUtils.checkNullString(parentId)))
		{
			return "";
		}
		List<DtreeBean> dtreeChildrenList = null;
		String realParentId = null;
		if(parentId.endsWith("@TYPE@"))
		{
			realParentId = parentId.substring(0, parentId.indexOf("@TYPE@"));
			dtreeChildrenList = getTranslst(realParentId,"@TYPE@");
		}
		else if(parentId.endsWith("@TRANS@"))
		{
			realParentId = parentId.substring(0, parentId.indexOf("@TRANS@"));
			dtreeChildrenList = getTranslst(realParentId,"@TRANS@");
		}
		else if(parentId.endsWith("@RULE@"))
		{
			dtreeChildrenList = new ArrayList<DtreeBean>();
			realParentId = parentId.substring(0, parentId.indexOf("@RULE@"));
		}
		return this.createDhtmlxtreeXml(dtreeChildrenList, parentId);
	}
	
	/**
	 * 获得构造动作树的DtreeBean集合
	 * @return
	 */
	public List<DtreeBean> getActionLst()
	{
		List<DtreeBean> lst = new ArrayList<DtreeBean>();
		String sql = "select di.divalue divalue, di.diname diname from bs_t_sm_dicitem di where di.dtcode='SlaActionType' order by di.ordernum";
		QueryAdapter qa = new QueryAdapter();
		DataTable datatable = qa.executeQuery(sql, null, 0, 0, 2);
		int datatableLen = 0;
		if(datatable!=null)
		{
			datatableLen = datatable.length();
		}
		DataRow dataRow;
		DtreeBean menuDtree = null;
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			menuDtree = new DtreeBean();
			menuDtree.setId(dataRow.getString("divalue")+"@TYPE@");
			menuDtree.setText(dataRow.getString("diname"));
			//menuDtree.setIm0("folderClosed.gif");
			//menuDtree.setIm1("folderClosed.gif");
			//menuDtree.setIm2("folderClosed.gif");
			lst.add(menuDtree);
		}
		return lst;
	}
	
	/**
	 * 获得构造某动作对应业务树的DtreeBean集合
	 * @param parentId 父ID，也就是动作ID
	 * @param type 根据这个类型参数，选择查询不同的表构造DtreeBean集合
	 * @return
	 */
	public List<DtreeBean> getTranslst(String parentId,String type)
	{
		if(parentId==null || type==null)
		{
			return null;
		}
		List<DtreeBean> result = new ArrayList<DtreeBean>();
		StringBuffer sql = new StringBuffer();
		if("@TYPE@".equals(type))
		{
			/*sql.append("select pid, actionname, actiontype, ordernum from (");
			sql.append(" select sm.pid pid, sm.actionname actionname, sm.actiontype actiontype,sm.ordernum ordernum");
			sql.append(" from bs_t_sm_slasmaction sm");
			sql.append(" union");
			sql.append(" select mail.pid pid, mail.actionname actionname, mail.actiontype actiontype,mail.ordernum ordernum");
			sql.append(" from bs_t_sm_slamailaction mail)");
			sql.append(" where actiontype = ?");
			sql.append(" order by ordernum");*/
			if(SlaActionTreeImpl.SHORT_MESSAGE_NOTICE.equals(parentId))
			{//短信通知
				sql.append("select pid, actionname, ordernum");
				sql.append(" from bs_t_sm_slasmaction");
				sql.append(" order by ordernum");
			}
			else if(SlaActionTreeImpl.EMAIL_NOTICE.equals(parentId))
			{//邮件通知
				sql.append("select pid, actionname, ordernum");
				sql.append(" from bs_t_sm_slamailaction");
				sql.append(" order by ordernum");
			}
			QueryAdapter qa = new QueryAdapter();
			DataTable dtb = null;
			if(sql.length()>0)
			{
				dtb = qa.executeQuery(sql.toString(), null, 0, 0, 2);
			}
			if(dtb!=null && dtb.length()>0)
			{
				for(int i=0;i<dtb.length();i++)
				{
					DataRow dr = dtb.getDataRow(i);
					DtreeBean db = new DtreeBean();
					db.setId(dr.getString("pid")+"@TRANS@");
					db.setText(dr.getString("actionname"));
					result.add(db);
				}
			}
		}
		else if("@TRANS@".equals(type))
		{
			sql.append("select ru.pid, ru.rulename");
			sql.append(" from bs_t_sm_slarule ru");
			sql.append(" where ru.actionid = ?");
			sql.append(" order by ru.ordernum");
			QueryAdapter qa = new QueryAdapter();
			DataTable dtb = qa.executeQuery(sql.toString(), new Object[]{parentId}, 0, 0, 2);
			if(dtb!=null && dtb.length()>0)
			{
				SlaSmAction parent_sm = null;
				SlaMailAction parent_mail = null;
				List<SlaSmAction> parent_sm_lst = slaSmActionDao.find("from SlaSmAction where pid = ?", new Object[]{parentId});
				List<SlaMailAction> parent_mail_lst = slaMailActionDao.find("from SlaMailAction where pid = ?", new Object[]{parentId});
				for(int i=0;i<dtb.length();i++)
				{
					DataRow dr = dtb.getDataRow(i);
					DtreeBean db = new DtreeBean();
					db.setId(dr.getString("pid")+"@RULE@");
					db.setText(dr.getString("rulename"));
					if(parent_sm_lst != null && parent_sm_lst.size()>0)
					{
						parent_sm = parent_sm_lst.get(0);
						HashMap<String,String> userdata = new HashMap<String,String>();
						userdata.put("modelId", parent_sm.getRuletplid()); //规则模板ID
						userdata.put("actionType", parent_sm.getActiontype()); //动作类型
						db.setUserdata(userdata);
					}
					else if(parent_mail_lst != null && parent_mail_lst.size()>0)
					{
						parent_mail = parent_mail_lst.get(0);
						HashMap<String,String> userdata = new HashMap<String,String>();
						userdata.put("modelId", parent_mail.getRuletplid()); //规则模板ID
						userdata.put("actionType", parent_mail.getActiontype()); //动作类型
						db.setUserdata(userdata);
					}
					result.add(db);
				}
			}
		}
		return result;
	}

	public void setSlaSmActionDao(IDao<SlaSmAction> slaSmActionDao) 
	{
		this.slaSmActionDao = slaSmActionDao;
	}

	public void setSlaMailActionDao(IDao<SlaMailAction> slaMailActionDao) 
	{
		this.slaMailActionDao = slaMailActionDao;
	}
	
	
}
