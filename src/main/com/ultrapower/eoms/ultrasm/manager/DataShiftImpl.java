package com.ultrapower.eoms.ultrasm.manager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.PinYinUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DataShiftService;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.DnsManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;
import com.ultrapower.remedy4j.common.IRemedyUserManager;

@Transactional
public class DataShiftImpl implements DataShiftService {

	private IRemedyUserManager remedyUserManager;
	private DepManagerService depManagerService;
	private UserManagerService userManagerService;
	private DnsManagerService dnsManagerService;
	private IDao<Map> depJdbcDao;
	private IDao<UserInfo> userManagerDao;
	public static String poolName="";
	
	public List<String> organizationShift()
	{
		List<String> shiftInfoList = new ArrayList<String>();
		//处理人员表相关信息
		StringBuffer sqlUser = new StringBuffer();//查人员SQL
		sqlUser.append("select loginname, fullname, pwd, depid, groupid");
		sqlUser.append("  from bs_t_sm_user");
		sqlUser.append(" where length(pid) < 30 ");
		QueryAdapter queryUser = new QueryAdapter();
		DataTable tableUser = null;
		DataRow rowUser = null;
		int p_curpage = 1;//查询页数
		int p_pageSize = 500;//每页条数
		CryptUtils crypt = CryptUtils.getInstance();
		try
		{
			while(true)
			{
				tableUser = queryUser.executeQuery(sqlUser.toString(), null, p_curpage, p_pageSize, 0);
				int userLen = 0;
				if(tableUser != null)
				{
					userLen = tableUser.length();
				}
				if(userLen <= 0)
					break;
				for(int i=0;i<userLen;i++)
				{
					rowUser = tableUser.getDataRow(i);
					String loginname = rowUser.getString("loginname");
					String fullname = rowUser.getString("fullname");
					String pwd = rowUser.getString("pwd");
					String depid = rowUser.getString("depid");
					String groupid = rowUser.getString("groupid");
//					if(!remedyUserManager.addUser(loginname, pwd, fullname))//同步到AR
//					{
//						shiftInfoList.add("用户登录名：" + loginname + "在同步到AR时失败！");
//					}
					rowUser.put("pwd", crypt.encode(pwd));//密码加密
					if(!"".equals(depid))
					{
						//根据ID将行政部门名称查出来并更新到人员表中
						rowUser.put("depname", StringUtils.checkNullString(depManagerService.getDepNameByID(depid)));
					}
					if(!"".equals(groupid))
					{
						//根据所属组ID将所属组名称查出来并更新到人员表中
						String depStr = depManagerService.getDepNamesByIDs(UltraSmUtil.arrayToList(groupid.split(";")));
						if(!"".equals(StringUtils.checkNullString(depStr)))
						{
							rowUser.put("groupid", depStr.split(";")[0]);
							rowUser.put("groupname", depStr.split(";")[1]);
						}
					}
					rowUser.put("lastlogintime", "0");
				}
				if(tableUser != null)
				{
					//执行批量更新
					String[] primaryKey={"loginname"};
					tableUser.setTableName("bs_t_sm_user");
					tableUser.setPrimaryKey(primaryKey);
					DataAdapter dataAdapter = new DataAdapter();
					if(dataAdapter.executeUpdate(tableUser) < 0)
						shiftInfoList.add("在修改第( " + (p_curpage-1)*p_pageSize+1 + "-" + p_curpage*p_pageSize + " )人员信息时失败！");
				}
				p_curpage++;
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		
		//处理部门表相关信息
		StringBuffer sqlDep = new StringBuffer();//查部门SQL
		sqlDep.append("select level,pid");
		sqlDep.append("  from bs_t_sm_dep");
		sqlDep.append(" start with parentid = '0'");
		sqlDep.append("connect by prior pid = parentid");
		QueryAdapter queryDep = new QueryAdapter();
		DataTable tableDep = queryDep.executeQuery(sqlDep.toString(), null);
		DataRow rowDep = null;
		DataTable newDtDep = new DataTable("bs_t_sm_dep");
		DataRow newDrDep = null;
		int depLen = 0;
		if(tableDep != null)
			depLen = tableDep.length();
		else
			shiftInfoList.add("请检测部门的查询SQL是否有问题！");
		if(depLen == 0)
			return shiftInfoList;
		int[] level = new int[depLen];
		Map map = new HashMap();
		String dns = "001";
		String dn = "001";
		for(int i=0;i<depLen;i++)
		{
			rowDep = tableDep.getDataRow(i);
			level[i] = rowDep.getInt("level");
			if(i != 0)
			{
				if(level[i] > level[i-1])
				{
					dn = "001";
					dns = (String) map.get(level[i-1]) + "." + dn;
				}
				else if(level[i] == level[i-1])
				{
					dns = (String) map.get(level[i-1]);
					if(dns.indexOf(".") > 0)
					{
						dn = String.format("%1$03d", (Integer.valueOf(dns.substring(dns.lastIndexOf(".")+1))+1));
						dns = dns.substring(0,dns.lastIndexOf(".")) + "." + dn;
					}
					else
					{
						dn = String.format("%1$03d", (Integer.valueOf(dns)+1));
						dns = dn;
					}
				}
				else
				{
					dns = (String) map.get(level[i]);
					if(dns.indexOf(".") > 0)
					{
						dn = String.format("%1$03d", (Integer.valueOf(dns.substring(dns.lastIndexOf(".")+1))+1));
						dns = dns.substring(0,dns.lastIndexOf(".")) + "." + dn;
					}
					else
					{
						dn = String.format("%1$03d", (Integer.valueOf(dns)+1));
						dns = dn;
					}
				}
			}
			newDrDep = new DataRow();
			newDrDep.put("pid", rowDep.getString("pid"));
			newDrDep.put("depdns", dns);
			newDrDep.put("depdn", dn);
			newDtDep.putDataRow(newDrDep);
			map.put(level[i], dns);
		}
		if(newDtDep != null)
		{
			//执行批量更新
			String[] primaryKey={"pid"};
			newDtDep.setPrimaryKey(primaryKey);
			DataAdapter dataAdapter=new DataAdapter();
			if(dataAdapter.executeUpdate(newDtDep) < 0)
				shiftInfoList.add("在修改部门DN和DNS时修改失败！");
		}
		return shiftInfoList;
	}
	
	public List<String> createDepDns()
	{
		List<String> shiftInfoList = new ArrayList<String>();
		//处理部门表相关信息
		StringBuffer sqlDep = new StringBuffer();//查部门SQL
		sqlDep.append("select level,pid");
		sqlDep.append("  from bs_t_sm_dep");
		sqlDep.append(" start with parentid = '0'");
		sqlDep.append("connect by prior pid = parentid");
		sqlDep.append(" order siblings by ordernum");
		QueryAdapter queryDep = new QueryAdapter();
		DataTable tableDep = queryDep.executeQuery(sqlDep.toString(), null);
		DataRow rowDep = null;
		DataTable newDtDep = new DataTable("bs_t_sm_dep");
		DataRow newDrDep = null;
		int depLen = 0;
		if(tableDep != null)
			depLen = tableDep.length();
		else
			shiftInfoList.add("请检测部门的查询SQL是否有问题！");
		if(depLen == 0)
			return shiftInfoList;
		int[] level = new int[depLen];
		Map map = new HashMap();
		String dns = "001";
		String dn = "001";
		for(int i=0;i<depLen;i++)
		{
			rowDep = tableDep.getDataRow(i);
			level[i] = rowDep.getInt("level");
			if(i != 0)
			{
				if(level[i] > level[i-1])
				{
					dn = "001";
					dns = (String) map.get(level[i-1]) + "." + dn;
				}
				else if(level[i] == level[i-1])
				{
					dns = (String) map.get(level[i-1]);
					if(dns.indexOf(".") > 0)
					{
						dn = String.format("%1$03d", (Integer.valueOf(dns.substring(dns.lastIndexOf(".")+1))+1));
						dns = dns.substring(0,dns.lastIndexOf(".")) + "." + dn;
					}
					else
					{
						dn = String.format("%1$03d", (Integer.valueOf(dns)+1));
						dns = dn;
					}
				}
				else
				{
					dns = (String) map.get(level[i]);
					if(dns.indexOf(".") > 0)
					{
						dn = String.format("%1$03d", (Integer.valueOf(dns.substring(dns.lastIndexOf(".")+1))+1));
						dns = dns.substring(0,dns.lastIndexOf(".")) + "." + dn;
					}
					else
					{
						dn = String.format("%1$03d", (Integer.valueOf(dns)+1));
						dns = dn;
					}
				}
			}
			newDrDep = new DataRow();
			newDrDep.put("pid", rowDep.getString("pid"));
			newDrDep.put("depdns", dns);
			newDrDep.put("depdn", dn);
			newDtDep.putDataRow(newDrDep);
			map.put(level[i], dns);
		}
		if(newDtDep != null)
		{
			//执行批量更新
			String[] primaryKey={"pid"};
			newDtDep.setPrimaryKey(primaryKey);
			DataAdapter dataAdapter=new DataAdapter();
			if(dataAdapter.executeUpdate(newDtDep) < 0)
				shiftInfoList.add("在修改部门DN和DNS时修改失败！");
		}
		return shiftInfoList;
	}
		
	public boolean exportInitData(String downLoadPath, List<String> tbNameList)
	{
		boolean result = false;
		if(tbNameList != null && tbNameList.size() > 0)
		{
			try
			{
				FileOutputStream out = new FileOutputStream(downLoadPath);
				PrintStream p = new PrintStream(out);
				p.println("prompt PL/SQL Developer import file");
				p.println("set feedback off");
				p.println("set define off");
				if(tbNameList.indexOf("bs_t_sm_user") >= 0)
				{
					//人员表 Demo用户 和 admin用户
					p.println("prompt Loading bs_t_sm_user...");
					p.println("insert into bs_t_sm_user (pid, loginname,pwd, fullname, status, ordernum, creater, createtime, lastmodifier, lastmodifytime, lastlogintime) values ('297e39d1298150e8012981703b700002','Demo','BjjDFuU0ges=','系统管理员',1,1,'297e39d1298150e8012981703b700002',0,'297e39d1298150e8012981703b700002',0,0);");
					p.println("insert into bs_t_sm_user (pid, loginname,pwd, fullname, status, ordernum, creater, createtime, lastmodifier, lastmodifytime, lastlogintime) values ('4028e5852b50bc8a012b51abcb2f0003','admin','2YC9HMVleTo=','管理员',1,2,'297e39d1298150e8012981703b700002',0,'297e39d1298150e8012981703b700002',0,0);");
					p.println("commit;");
					p.println("prompt 2 records loaded");
					tbNameList.remove("bs_t_sm_user");
				}
				if(tbNameList.indexOf("bs_t_sm_role") >= 0)
				{
					//角色表 系统管理员角色
					p.println("prompt Loading bs_t_sm_role...");
					p.println("insert into bs_t_sm_role (pid, rolename, parentid, roledns, roledn, definetype, creater, createtime, lastmodifier, lastmodifytime) values ('297e39d1298150e8012981714a5a0004','系统管理员','0','001','001','系统管理','297e39d1298150e8012981703b700002',0,'297e39d1298150e8012981703b700002',0);");
					p.println("commit;");
					p.println("prompt 1 records loaded");
					tbNameList.remove("bs_t_sm_role");
				}
				if(tbNameList.indexOf("bs_t_sm_roleorg") >= 0)
				{
					//角色人员关系
					p.println("prompt Loading bs_t_sm_roleorg...");
					p.println("insert into bs_t_sm_roleorg (pid, roleid, orgid, orgtype, creater, createtime, lastmodifier, lastmodifytime) values('297e39d1298353f60129835861a50001','297e39d1298150e8012981714a5a0004','297e39d1298150e8012981703b700002',1,'297e39d1298150e8012981703b700002',0,'297e39d1298150e8012981703b700002',0);");
					p.println("insert into bs_t_sm_roleorg (pid, roleid, orgid, orgtype, creater, createtime, lastmodifier, lastmodifytime) values('4028e5852b50bc8a012b51abcb2f0005','297e39d1298150e8012981714a5a0004','4028e5852b50bc8a012b51abcb2f0003',1,'297e39d1298150e8012981703b700002',0,'297e39d1298150e8012981703b700002',0);");
					p.println("commit;");
					p.println("prompt 2 records loaded");
					tbNameList.remove("bs_t_sm_roleorg");
				}
				String sql = "";
	            QueryAdapter queryAdapter = new QueryAdapter();
	    		DataTable table = null;
	    		DataRow row = null;
	    		StringBuffer insertSql = null;
	            for(int i=0;i<tbNameList.size();i++)
	            {
	            	String tbName = tbNameList.get(i);
	            	String condition = "";
	            	if(tbName.indexOf(",") > 0)
	            	{
	            		String[] tmp = tbName.split(",");
	            		tbName = tmp[0];
	            		condition = tmp[1];
	            	}
	            	sql = " select * from " + tbName;
	            	if(!"".equals(condition))
	            		sql = sql + " where " + condition;
	            	table = queryAdapter.executeQuery(sql, null);
	            	p.println("prompt Loading "+ tbName + "...");
	            	if(table != null && table.length() > 0)
	            	{
	            		int tableLen = table.length();
	            		int[] columnType = table.getColumnType();
	            		for(int rowNum=0;rowNum<tableLen;rowNum++)
	            		{
	            			if(rowNum % 100 == 0 && rowNum > 0)
	            			{
	            				p.println("commit;");
	            				p.println("prompt " + rowNum + " records loaded");
	            			}
		            		StringBuffer fields = new StringBuffer();
		            		StringBuffer values = new StringBuffer();
	            			row = table.getDataRow(rowNum);
	            			values.append("'");
	            			for(int colNum=0;colNum<row.length();colNum++)
	            			{
	            				if(colNum>0)
	            				{
	            					fields.append(",");
	            					values.append("','");
	            				}
	            				fields.append(row.getKey(colNum));
	            				String value = row.getString(colNum);
	            				value = value.replaceAll("\r\n", "' || chr(13) || '' || chr(10) || '");
	            				if(columnType[colNum] == java.sql.Types.NUMERIC && "".equals(value))
	            					value = "0";
	            				values.append(value);
	            			}
	            			values.append("'");
	            			insertSql = new StringBuffer();
	            			insertSql.append("insert into ");
	            			insertSql.append(tbName);
	            			insertSql.append(" (");
	            			insertSql.append(fields);
	            			insertSql.append(") ");
	            			insertSql.append("values");
	            			insertSql.append(" (");
	            			insertSql.append(values);
	            			insertSql.append(");");
	            			p.println(insertSql.toString());
	            		}
	            		p.println("commit;");
	            		p.println("prompt " + tableLen + " records loaded");
	            	}
	            	else
	            	{
	            		p.println("prompt Table is empty");
	            	}
	            }
	            p.println("set feedback on");
	            p.println("set define on");
	            p.println("prompt Done.");
	            try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            result = true;
	        }
			catch (FileNotFoundException e)
	        {
	        	RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
	            e.printStackTrace();
	        }
		}
		return result;
	}
	
	/**
	 * 此方法是updateByMidTable的前一版 由于使用中间表解决双数据库数据同步问题 所以此方法暂时不提供
	 */
	public void tempShift()
	{
		List<String> shiftInfoList = new ArrayList<String>();
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = null;
		DataRow row = null;
		//更新人员信息表
		String userSql = "select * from bs_t_sm_user where 1=1 and (image = '1' or image = '2' or image = '3')";
		try
		{
			table = queryAdapter.executeQuery(userSql, null);
			if(table != null && table.length() > 0)
			{
				DataTable newDt = new DataTable("bs_t_sm_user");
				String[] primaryKey={"loginname"};
				newDt.setPrimaryKey(primaryKey);
				DataAdapter dataAdapter = new DataAdapter();
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					String flag = row.getString("image");
					if("1".equals(flag) || "2".equals(flag))
					{
						String loginname = row.getString("loginname");
						String fullname = row.getString("fullname");
						String pwd = row.getString("pwd");
						String depid = row.getString("depid");
						String groupid = row.getString("groupid");
						CryptUtils crypt = CryptUtils.getInstance();
//						if(!remedyUserManager.addUser(loginname, pwd, fullname))//同步到AR
//						{
//							shiftInfoList.add("用户登录名：" + loginname + "在同步到AR时失败！");
//						}
						row.put("pwd", crypt.encode(pwd));//密码加密
						if(!"".equals(depid))
						{
							//根据ID将行政部门名称查出来并更新到人员表中
							row.put("depname", StringUtils.checkNullString(depManagerService.getDepNameByID(depid)));
						}
						if(!"".equals(groupid))
						{
							//根据所属组ID将所属组名称查出来并更新到人员表中
							String depStr = depManagerService.getDepNamesByIDs(UltraSmUtil.arrayToList(groupid.split(";")));
							if(!"".equals(StringUtils.checkNullString(depStr)))
							{
								row.put("groupid", depStr.split(";")[0]);
								row.put("groupname", depStr.split(";")[1]);
							}
						}
						row.put("lastlogintime", "0");
						row.put("image", "");
						newDt.putDataRow(row);
					}
					else
					{
						String userid = row.getString("pid");
						userManagerService.deleteUserByID(userid);
					}
				}
				dataAdapter.executeUpdate(newDt);
			}
		}
		catch (Exception e)
        {
        	RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
            e.printStackTrace();
        }
		//更新部门信息表
		String depSql = "select * from bs_t_sm_dep_temp where 1=1 and (depimage = '1' or depimage = '2' or depimage = '3')";
		try
		{
			table = queryAdapter.executeQuery(depSql, null);
			if(table != null && table.length() > 0)
			{
				DataAdapter dataAdapter = new DataAdapter();
				DataRow tmpDr = null;
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					String flag = row.getString("depimage");
					row.put("depimage", "");
					if("1".equals(flag) || "2".equals(flag))
					{
						//修改中间表中标识字段
						String[] primaryKey={"pid"};
						tmpDr = new DataRow();
						tmpDr.put("pid",row.getString("pid"));
						tmpDr.put("depimage", "");
						DataTable tmpDt = new DataTable("bs_t_sm_dep_temp");
						tmpDt.putDataRow(tmpDr);
						tmpDt.setPrimaryKey(primaryKey);
						dataAdapter.executeUpdate(tmpDt);
					}
					if("1".equals(flag))
					{
						String parentid = row.getString("parentid");
						row.put("depdn", dnsManagerService.getCurrentDn("bs_t_sm_dep", "depdn", parentid));
						row.put("depdns", dnsManagerService.getCurrentDns("bs_t_sm_dep", "depdns", "depdn", parentid));
						dataAdapter.putDataRow("bs_t_sm_dep", row, null, null);
					}
					else if("2".equals(flag))
					{
						DepInfo dep = new DepInfo();
						String depId = row.getString("pid");
						dep.setPid(depId);
						dep.setDepname(row.getString("depname"));
						dep.setDepfullname(row.getString("depfullname"));
						String parentid = row.getString("parentid");
						dep.setParentid(parentid);
						dep.setDeptype(row.getString("deptype"));
						dep.setStatus(row.getLong("status"));
						dep.setDepphone(row.getString("depphone"));
						dep.setDepfax(row.getString("depfax"));
						dep.setCreater(row.getString("creater"));
						dep.setCreatetime(row.getLong("createtime"));
						dep.setLastmodifier(row.getString("lastmodifier"));
						dep.setLastmodifytime(row.getLong("lastmodifytime"));
						dep.setDepimage("");
						List mapList = depJdbcDao.find(" select depdn ,depdns,depfullname from bs_t_sm_dep where pid = ?", new Object[] {depId});
						String oldDn = "";
						String oldDns = "";
						if(mapList != null && mapList.size() > 0)
						{
							Map depMap = (Map)mapList.get(0);
							oldDn = StringUtils.checkNullString(depMap.get("parentid"));
							oldDns = StringUtils.checkNullString(depMap.get("depdns"));
						}
						dep.setDepdn(oldDn);
						dep.setDepdns(oldDns);
						depManagerService.updateDepInfo(dep);
					}
					else
					{
						String depid = row.getString("pid");
						if(depManagerService.deleteDepByID(depid))
						{
							String delSql = " delete bs_t_sm_dep_temp where pid = ?";
							dataAdapter.execute(delSql, new Object[] {depid});
						}
					}
				}
			}
		}
		catch (Exception e)
        {
        	RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
            e.printStackTrace();
        }
		//更新人员部门关系表
		try
		{
			DataAdapter dataAdapter = new DataAdapter();
			StringBuffer udSql = new StringBuffer();
			udSql.append("update bs_t_sm_userdep ud");
			udSql.append("   set ud.relatetype = 1,");
			udSql.append("       ud.loginname  = (select loginname from bs_t_sm_user u where u.pid = ud.userid)");
			udSql.append(" where 1=1 and (ud.relatetype is null or ud.relatetype = '')");
			dataAdapter.execute(udSql.toString(), null);
		}
		catch (Exception e)
        {
        	RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
            e.printStackTrace();
        }
	}
	
	public void updateByMidTable()
	{
		DataAdapter dataAdapter = new DataAdapter();
		DataAdapter dataAdapterOld = new DataAdapter(poolName);
		QueryAdapter queryAdapter = new QueryAdapter(poolName);
		DataTable table = null;
		DataRow row = null;
		DataTable newDt = null;
		DataTable tmpDt = null;
		DataRow tmpDr = null;

		//人员操作信息扫描 用中间表的image作为标识 验证增、删、改
		String userSql = "select * from bs_t_sm_user_temp where 1=1 and (image = '1' or image = '2' or image = '3')";
		try
		{
			table = queryAdapter.executeQuery(userSql, null, 1, 100, 0);
		}
		catch (Exception e)
        {
        	RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
            e.printStackTrace();
		}
		int userLen = 0;
		if(table != null)
			userLen = table.length();
		if(userLen > 0)
		{
			newDt = new DataTable("bs_t_sm_user");
			String[] primaryKey={"loginname"};
			newDt.setPrimaryKey(primaryKey);
		}
		for(int i=0;i<userLen;i++)
		{
			row = table.getDataRow(i);
			String flag = row.getString("image");//获取标识字段
			row.put("image", "");
			//将中间表的标识清空
			if("1".equals(flag) || "2".equals(flag))
			{
				String[] primaryKey={"pid"};
				tmpDr = new DataRow();
				tmpDr.put("pid",row.getString("pid"));
				tmpDr.put("image", "");
				tmpDt = new DataTable("bs_t_sm_user_temp");
				tmpDt.putDataRow(tmpDr);
				tmpDt.setPrimaryKey(primaryKey);
				dataAdapterOld.executeUpdate(tmpDt);
			}
			
			if("1".equals(flag))//标识等于1 增加操作
			{
				String pwd = row.getString("pwd");
				String depid = row.getString("depid");
				String groupid = row.getString("groupid");
				
				//密码加密
				CryptUtils crypt = CryptUtils.getInstance();
				row.put("pwd", crypt.encode(pwd));
				
				//根据ID将行政部门名称查出来并更新到人员表中
				if(!"".equals(depid))
					row.put("depname", StringUtils.checkNullString(depManagerService.getDepNameByID(depid)));
				
				//根据所属组ID将所属组名称查出来并更新到人员表中
				if(!"".equals(groupid))
				{
					String depStr = depManagerService.getDepNamesByIDs(UltraSmUtil.arrayToList(groupid.split(";")));
					if(!"".equals(StringUtils.checkNullString(depStr)))
					{
						row.put("groupid", depStr.split(";")[0]);
						row.put("groupname", depStr.split(";")[1]);
					}
				}
				row.put("lastlogintime", "0");
				dataAdapter.putDataRow("bs_t_sm_user", row, null, null);
			}
			else if("2".equals(flag))//标识等于2 修改操作
			{
				String pwd = row.getString("pwd");
				String depid = row.getString("depid");
				String groupid = row.getString("groupid");
				
				//密码加密
				CryptUtils crypt = CryptUtils.getInstance();
				row.put("pwd", crypt.encode(pwd));
				
				//根据ID将行政部门名称查出来并更新到人员表中
				if(!"".equals(depid))
					row.put("depname", StringUtils.checkNullString(depManagerService.getDepNameByID(depid)));
				
				//根据所属组ID将所属组名称查出来并更新到人员表中
				if(!"".equals(groupid))
				{
					String depStr = depManagerService.getDepNamesByIDs(UltraSmUtil.arrayToList(groupid.split(";")));
					if(!"".equals(StringUtils.checkNullString(depStr)))
					{
						row.put("groupid", depStr.split(";")[0]);
						row.put("groupname", depStr.split(";")[1]);
					}
				}
				newDt.putDataRow(row);
			}
			else if("3".equals(flag))//标识等于3 删除操作
			{
				String userid = row.getString("pid");
				if(userManagerService.deleteUserByID(userid))
				{
					String delSql = " delete bs_t_sm_user_temp where pid = ?";
					dataAdapterOld.execute(delSql, new Object[] {userid});
				}
			}
			if(newDt != null && newDt.length() > 0)
				dataAdapter.executeUpdate(newDt);
		}
		
		//部门操作信息扫描 用中间表的depimage作为标识 验证增、删、改
		String depSql = "select * from bs_t_sm_dep_temp where 1=1 and (depimage = '1' or depimage = '2' or depimage = '3')";
		try
		{
			table = queryAdapter.executeQuery(depSql, null);
		}
		catch (Exception e)
        {
        	RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
            e.printStackTrace();
		}
		int depLen = 0;
		if(table != null)
			depLen = table.length();
		
		for(int i=0;i<depLen;i++)
		{
			row = table.getDataRow(i);
			String flag = row.getString("depimage");
			row.put("depimage", "");
			//将中间表的标识清空
			if("1".equals(flag) || "2".equals(flag))
			{
				String[] primaryKey={"pid"};
				tmpDr = new DataRow();
				tmpDr.put("pid",row.getString("pid"));
				tmpDr.put("depimage", "");
				tmpDt = new DataTable("bs_t_sm_dep_temp");
				tmpDt.putDataRow(tmpDr);
				tmpDt.setPrimaryKey(primaryKey);
				dataAdapterOld.executeUpdate(tmpDt);
			}
			
			if("1".equals(flag))//标识等于1 添加操作
			{
				String parentid = row.getString("parentid");
				//添加部门信息时需要创建dn和dns
				row.put("depdn", dnsManagerService.getCurrentDn("bs_t_sm_dep", "depdn", parentid));
				row.put("depdns", dnsManagerService.getCurrentDns("bs_t_sm_dep", "depdns", "depdn", parentid));
				dataAdapter.putDataRow("bs_t_sm_dep", row, null, null);//将此条数据添加到对应表中(第一个参数)
			}
			else if("2".equals(flag))//标识等于2 修改操作
			{
				//修改部门信息时 由于涉及一些关系信息 所以在此调用部门管理服务的修改方法
				DepInfo dep = new DepInfo();
				String depId = row.getString("pid");
				dep.setPid(depId);
				dep.setDepname(row.getString("depname"));
				dep.setDepfullname(row.getString("depfullname"));
				String parentid = row.getString("parentid");
				dep.setParentid(parentid);
				dep.setDeptype(row.getString("deptype"));
				dep.setStatus(row.getLong("status"));
				dep.setDepphone(row.getString("depphone"));
				dep.setDepfax(row.getString("depfax"));
				dep.setCreater(row.getString("creater"));
				dep.setCreatetime(row.getLong("createtime"));
				dep.setLastmodifier(row.getString("lastmodifier"));
				dep.setLastmodifytime(row.getLong("lastmodifytime"));
				dep.setDepimage("");
				List mapList = depJdbcDao.find(" select depdn ,depdns,depfullname from bs_t_sm_dep where pid = ?", new Object[] {depId});
				String oldDn = "";
				String oldDns = "";
				if(mapList != null && mapList.size() > 0)
				{
					Map depMap = (Map)mapList.get(0);
					oldDn = StringUtils.checkNullString(depMap.get("depdn"));
					oldDns = StringUtils.checkNullString(depMap.get("depdns"));
				}
				dep.setDepdn(oldDn);
				dep.setDepdns(oldDns);
				depManagerService.updateDepInfo(dep);
			}
			else if("3".equals(flag))//标识等于3 删除操作
			{
				String depid = row.getString("pid");
				if(depManagerService.deleteDepByID(depid))
				{
					String delSql = " delete bs_t_sm_dep_temp where pid = ?";
					dataAdapterOld.execute(delSql, new Object[] {depid});
				}
			}
		}
		
		//关系表操作信息扫描 用中间表的relatetype作为标识 验证增、删、改
		String udSql = "select * from bs_t_sm_userdep_temp where 1=1 and (relatetype = '1' or relatetype = '2' or relatetype = '3')";
		try
		{
			table = queryAdapter.executeQuery(udSql, null);
		}
		catch (Exception e)
        {
        	RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
            e.printStackTrace();
		}
		int udLen = 0;
		if(table != null)
			udLen = table.length();
		for(int i=0;i<udLen;i++)
		{
			row = table.getDataRow(i);
			String flag = row.getString("relatetype");
			//将中间表的标识清空
			if("1".equals(flag) || "2".equals(flag))
			{
				String[] primaryKey={"pid"};
				tmpDr = new DataRow();
				tmpDr.put("pid",row.getString("pid"));
				tmpDr.put("relatetype", "");
				tmpDt = new DataTable("bs_t_sm_userdep_temp");
				tmpDt.putDataRow(tmpDr);
				tmpDt.setPrimaryKey(primaryKey);
				dataAdapterOld.executeUpdate(tmpDt);
			}
			if("1".equals(flag))//标识等于1 添加操作
			{
				row.put("relatetype", "1");
				dataAdapter.putDataRow("bs_t_sm_userdep", row, null, null);
			}
			else if("2".equals(flag))//标识等于2 修改操作
			{
				
			}
			else if("3".equals(flag))//标识等于3 删除操作
			{
				String udid = row.getString("pid");
				String delUdSql = "delete bs_t_sm_userdep where pid = ?";//删除新系统管理的关系信息
				String delUdtSql = "delete bs_t_sm_userdep_temp where pid = ?";//删除中间表的关系信息
				try
				{
					dataAdapter.execute(delUdSql, new Object[] {udid});
					dataAdapterOld.execute(delUdtSql, new Object[] {udid});
				}
				catch (Exception e)
		        {
		        	RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
		            e.printStackTrace();
				}
			}
		}
	}
	
	public boolean updateNodepath()
	{
		boolean result = false;
		StringBuffer sb = new StringBuffer();
		sb.append("  select level, pid, nodename");
		sb.append("    from bs_t_sm_menutree m");
		sb.append("   start with parentid = '0'");
		sb.append(" connect by parentid = prior pid");
		sb.append("   order by level");
		QueryAdapter queryAdapter = new QueryAdapter();
		try
		{
			DataTable table = queryAdapter.executeQuery(sb.toString(), null);
			int tableLen = 0;
			if(table != null)
				tableLen = table.length();
			DataRow row;
			Map map = new HashMap();
			List<String> menuIdList = new ArrayList();
			int oldLevel = 0;
			for(int i=0;i<tableLen;i++)
			{
				row = table.getDataRow(i);
				String menuId = row.getString("pid");
				String menuName = row.getString("nodename");
				int level = row.getInt("level");
				if(level != oldLevel)
				{
					if(oldLevel > 0)
						map.put(oldLevel+"", menuIdList);
					oldLevel = level;
					menuIdList = new ArrayList();
				}
				menuIdList.add(menuId);
			}
			map.put(oldLevel+"", menuIdList);
			for(int i=2;i<=oldLevel;i++)
			{
				menuIdList = (List<String>) map.get(i+"");
				if(menuIdList == null || menuIdList.size() <= 0)
					continue;
				Map conMap = UltraSmUtil.getSqlParameter(menuIdList);
				String updateSql = "update bs_t_sm_menutree m1 set nodepath = (select nodepath || '>>' || m1.nodename from bs_t_sm_menutree m2 where m2.pid = m1.parentid ) where pid in (" + conMap.get("?") + ")";
				if(i == 2)
					updateSql = "update bs_t_sm_menutree set nodepath = '当前位置：' || nodename where pid in (" + conMap.get("?") + ")";
				DataAdapter dataAdapter = new DataAdapter();
				int n = dataAdapter.execute(updateSql, (Object[]) conMap.get("obj"));
				System.out.println("第" + i + "级");
				System.out.println("节点共：" + menuIdList.size() + "条");
				System.out.println("修改了：" + n + "条");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateUserPyName()
	{
		boolean result = false;
		try
		{
			List<UserInfo> userList = userManagerDao.find("from UserInfo", null);
			int userLen = 0;
			if(userList != null)
				userLen = userList.size();
			UserInfo user;
			for(int i=0;i<userLen;i++)
			{
				user = userList.get(i);
				user.setPyname(PinYinUtils.getPinYin(user.getFullname(), "3"));
				userManagerDao.saveOrUpdate(user);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public void setRemedyUserManager(IRemedyUserManager remedyUserManager) {
		this.remedyUserManager = remedyUserManager;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setDnsManagerService(DnsManagerService dnsManagerService) {
		this.dnsManagerService = dnsManagerService;
	}
	public void setDepJdbcDao(IDao<Map> depJdbcDao) {
		this.depJdbcDao = depJdbcDao;
	}
	public void setUserManagerDao(IDao<UserInfo> userManagerDao) {
		this.userManagerDao = userManagerDao;
	}
}
