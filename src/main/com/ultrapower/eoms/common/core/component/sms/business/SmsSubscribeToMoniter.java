package com.ultrapower.eoms.common.core.component.sms.business;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.sms.service.InsideSmsService;
import com.ultrapower.eoms.common.core.component.sms.service.SmsSubscribeToMoniterService;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.remedy4j.core.RemedySession;

/**
 * 工单短信订阅
 * 根据用户订阅的条件将满足条件的信息插入到短信表(bs_t_sm_smsmonitor)中
 * @author Administrator
 *
 */
public class SmsSubscribeToMoniter implements SmsSubscribeToMoniterService
{
 
	private InsideSmsService insidesm;
	private HashMap<String,String> baseItem=new HashMap<String, String>();
	private LinkedList<String> baseItemKey=new LinkedList<String>();
	QueryAdapter query=new QueryAdapter();
	DataAdapter dataAdapter=new DataAdapter();
	
	public void call()
	{
		
		baseItem=new HashMap<String, String>();
		baseItemKey=new LinkedList<String>();
		try{
			
			insidesm=(InsideSmsService)WebApplicationManager.getBean("insidesm");
			
			RQuery query=new RQuery("SQL_SM_SmsSubscribeToMoniter.person",null);
			DataTable dt=query.getDataTable();
			this.subscribe(dt);
			
			query=new RQuery("SQL_SM_SmsSubscribeToMoniter.group",null);
			dt=query.getDataTable();
			this.subscribe(dt);

			query=new RQuery("SQL_SM_SmsSubscribeToMoniter.role",null);
			dt=query.getDataTable();
			this.subscribe(dt);
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param dt
	 * @throws Exception
	 */
	private   void subscribe(DataTable dt) throws Exception
	{
		
		String baseid;
		String baseschema;
		String loginname;
		String starttime;
		String endtime;
		String content;
		String goal;
		String taskid;
		int dtLen=0;
		if(dt!=null)
		{
			dtLen=dt.length();
		}
		DataRow dr;
		String upSql="update bs_t_wf_notice set issent=? where noticeid=? ";
		boolean blnitem;
		boolean blntime;
		boolean opflag;
		for(int i=0;i<dtLen;i++)
		{
			dr=dt.getDataRow(i);
			baseid=dr.getString("baseid");
			baseschema=dr.getString("baseschema");
			loginname=dr.getString("loginname");
			starttime=dr.getString("starttime");
			endtime=dr.getString("endtime");
			content=dr.getString("content");
			goal=dr.getString("mobile");
			taskid=dr.getString("taskid");
			blnitem=checkBaseitem(loginname,baseid,baseschema);
			blntime=checkTime(starttime,endtime);
			if(blnitem && blntime)
			{
				//专业和时间都匹配则发短信并更新状态
				opflag=insidesm.sendsm(goal, content, "workflow",taskid,0);
				if(opflag)
				{
					dataAdapter.execute(upSql, new Object[]{"1",dr.getString("noticeid")});
				}
				else
				{
					dataAdapter.execute(upSql, new Object[]{"2",dr.getString("noticeid")});
				}
			}
			else if(blntime)
			{
				//专业不匹配 时间匹配则更新状态 下次就不用再扫描
				dataAdapter.execute(upSql, new Object[]{"2",dr.getString("noticeid")});
			}
			
		}
	}
	
	/**
	 *根据工单查询工单专业，检查是否订阅了改专业的短信 
	 * @param loginname 处理人登录名
	 * @param baseid 工单id
	 * @param baseschema 工单类别
	 * @return 返回true则为检查通过
	 */
	private boolean checkBaseitem(String loginname,String baseid,String baseschema)
	{
		String baseitems=this.getBaseitem(baseschema, loginname);
		String item;
		if(baseitems!=null) 
		{
			String tblName=RemedySession.UtilInfor.getTableName(baseschema);
			StringBuffer str=new StringBuffer();
			str.append("select c700000014 baseitem from ");
			str.append(tblName);
			str.append(" where ");
			str.append(" c1=? ");
			DataTable dt=query.executeQuery(str.toString(), new Object[]{baseid});
			DataRow dr=dt.getDataRow(0);
			if(dt!=null)
			{
				item=dr.getString("baseitem");
				//如果未找到
				if("".equals(item) || baseitems.indexOf(";"+item+";")<0)
				{
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 检查时间条件是否满足发送的逻辑
	 * @param loginname
	 * @param baseid
	 * @param baseschema
	 * @param starttime
	 * @param endtime
	 * @return 满足则返回true
	 */
	private boolean checkTime(String starttime,String endtime)
	{
		boolean result=true;
		String[] ary=starttime.split(":");
		int sthour=0;
		int stminute=0;
		if(ary.length>1)
		{
			sthour=NumberUtils.formatToInt(ary[0]);
			stminute=NumberUtils.formatToInt(ary[1]);
		}
		ary=endtime.split(":");
		int edhour=0;
		int edminute=0;
		if(ary.length>1)
		{
			edhour=NumberUtils.formatToInt(ary[0]);
			edminute=NumberUtils.formatToInt(ary[1]);
		}
		
		Date date=TimeUtils.getCurrentDate();
		Calendar c = java.util.Calendar.getInstance();
		c.setTime( date );
		int curhour =c.get( java.util.Calendar.HOUR_OF_DAY );
		int curminute=c.get( java.util.Calendar.MINUTE );
		
		if(sthour>0 )
		{
			if(curhour<sthour)
			{
				return false;
			}
			else if(curhour==sthour)
			{
				if(curminute<stminute)
					return false;
			}
		}
		if(edhour>0)
		{
			if(curhour>edhour)
			{
				return false;
			}
			else if(curhour==edhour)
			{
				if(curminute>edminute)
					return false;
			}
		}
		
		return result;
	}	
	
	/**
	 * 查询某人订阅的专业
	 * @param baseschema
	 * @param loginname
	 * @return 返回对象的null则为未订阅 否则返回专业的字符串 以分号（;)前后分割
	 */
	private String getBaseitem(String baseschema,String loginname)
	{
		String result=null;
		String pkey=baseschema+loginname;
		result=baseItem.get(pkey);
		if(result==null && !baseItem.containsKey(pkey))
		{
			StringBuffer str=new StringBuffer();
			str.append("select baseschema,loginname,BASEITEM from BS_T_SM_SMBASEITEM where baseschema='");
			str.append(baseschema);
			str.append("' and loginname='");
			str.append(loginname);
			str.append("'");
			DataTable dt=query.executeQuery(str.toString(), null);
			int rowLen=0;
			if(dt!=null)
			{
				rowLen=dt.length();
			}
			DataRow row;
			
			str=new StringBuffer();
			for(int i=0;i<rowLen;i++)
			{
				row=dt.getDataRow(i);
				str.append(";");
				str.append(row.getString("BASEITEM"));
				str.append(";");
			}
			if(baseItemKey.size()>30)
			{
				//超过30则溢出第一个
				String key=StringUtils.checkNullString(baseItemKey.poll());
				baseItem.remove(key);
			}
			
			baseItemKey.add(pkey);
			if(rowLen>0)
			{
				baseItem.put(pkey, str.toString());
				result=str.toString();
			}
			else
			{
				baseItem.put(pkey,null);
			}
		}
		return result;
	}
	
	/**
	 * 根据订阅人查询满足可以发送短信的数据
	 * @return
	 */
	private DataTable queryNotice()
	{
		return null;
	}

/*	public InsideSmsService getInsidesm() {
		return insidesm;
	}

	public void setInsidesm(InsideSmsService insidesm) {
		this.insidesm = insidesm;
	}*/

	
}
