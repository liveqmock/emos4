package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;
import com.ultrapower.eoms.common.core.component.rquery.datatransfer.DataTransfer;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DataPrivilege;
import com.ultrapower.eoms.ultrasm.model.ResProperty;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.PrivilegeManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class PrivilegeManagerImpl implements PrivilegeManagerService
{
	private RoleManagerService roleManagerService;
	
	public List<MenuDtree> getNavigationMenu(String userId)
	{
		List<MenuDtree> navigateList = null;
		Map privilegeMap = this.getPrivilegeByCache(userId);
		if(privilegeMap != null)//如果权限Map不为空则继续取值
		{
			Object objMenu = privilegeMap.get("menu");
			if(objMenu != null)//对象不为空则获取出菜单目录树Map
			{
				Map menuMap = (Map) objMenu;
				if(menuMap != null)//获取的菜单目录树Map若不为空则继续取值
				{
					Object objNavigate= menuMap.get("navigate");
					if(objNavigate != null)//对象不为空则获取出导航栏节点List
						navigateList = (List<MenuDtree>) objNavigate;
				}
			}
		}
		return navigateList;
	}

	public List<MenuDtree> getMenuByNavigationID(String userId, String menuId)
	{
		List<MenuDtree> treeList = null;
		Map privilegeMap = this.getPrivilegeByCache(userId);
		if(privilegeMap != null)//如果权限Map不为空则继续取值
		{
			Object objMenu = privilegeMap.get("menu");
			if(objMenu != null)//对象不为空则获取出菜单目录树Map
			{
				Map menuMap = (Map) objMenu;
				if(menuMap != null)//获取的菜单目录树Map若不为空则继续取值
				{
					Object obj= menuMap.get(menuId);
					if(obj != null)//对象不为空则或取出该导航栏下所有节点List
						treeList = (List<MenuDtree>) obj;
				}
			}
		}
		return treeList;
	}
	
	public boolean isAllow(String userId, String resId, String opId)
	{
		Map privilegeMap = this.getPrivilegeByCache(userId);
		if(privilegeMap != null)//如果权限Map不为空则继续取值
		{
			Object objRes = privilegeMap.get("res");
			if(objRes != null)//objRes对象不为空则获取出资源Map
			{
				Map resMap = (Map) objRes;
				if(resMap != null)//获取的资源Map若不为空则继续取值
				{
					Object objOp = resMap.get(resId);
					if(objOp != null)//objOp对象不为空则或取出操作Map
					{
						Map opMap = (Map) objOp;
						if(opMap != null)//获取的操作Map若不为空则判断传入的操作ID:opId是否存在于此操作Map中
						{
							List opKey = UltraSmUtil.getMapKeys(opMap);
							if(opKey != null && opKey.indexOf(opId) >= 0)
								return true;
						}
					}
				}
			}
		}
		return false;
	}

	public SqlResult getPrivilegeSql(String userId, String resId, String opId)
	{
		SqlResult sqlResult = new SqlResult();
		Map valueMap = this.getOpData(userId, resId, opId);
		if(valueMap == null)
			return sqlResult;
		Object dpObj = valueMap.get("odp");	//获取操作数据权限
		if(dpObj != null)//验证是否存在操作数据权限
		{
			List<DataPrivilege> dpList = (List<DataPrivilege>) dpObj;//取出操作数据权限
			if(dpList != null && dpList.size() > 0)
			{
				SqlResult subSql = null;	//用于每个资源属性返回的SQL条件
				DataPrivilege dp = null;
				ResProperty rp = null;
				String operator = "";
				String datas = "";
				String oldRpId = "";
				String sql = "";
				List values = new ArrayList();
				for(int i=0;i<dpList.size();i++)
				{
					dp = dpList.get(i);
					rp = dp.getRp();		//资源属性
					operator = dp.getOperator();//操作符
					datas = dp.getData();	//数据值
					if(rp != null)
					{
						String type = rp.getIndatasourtype() + "";
						if("1".equals(type))//当rp.getIndatasourtype()这个类型等于1时:是系统变量,需要转换一下
						{
							String[] dataArray = datas.split(",");
							datas = "";
							for(int k=0;k<dataArray.length;k++)
							{
								if(!"".equals(datas))
									datas += ",";
								String tempData = "#" + dataArray[k] + "#";
								datas += SqlReplace.stringReplaceAllVar(tempData, null);//转换系统变量
							}
						}
						if("1".equals(operator))//操作符转换
							operator = "=";
						else if("2".equals(operator))
							operator = ">";
						else if("3".equals(operator))
							operator = "<";
						else if("4".equals(operator))
							operator = ">=";
						else if("5".equals(operator))
							operator = "<=";
						else if("6".equals(operator))
							operator = "<>";
						else if("7".equals(operator))
							operator = "like";
						else if("8".equals(operator))
							operator = "leftlike";
						else if("9".equals(operator))
							operator = "rightlike";
						else
							operator = "=";
						subSql = DataTransfer.getFieldValues("", operator, rp.getFieldname(), datas, 4);	//获取SQL条件
						String rpId = StringUtils.checkNullString(rp.getPid());
						if(!"".equals(oldRpId) && !oldRpId.equals(rpId))
						{
							SqlResult rpSql = new SqlResult();
							rpSql.appendSql(" and ("+sql+")");
							rpSql.appendValues(values);
							sqlResult.append(rpSql);	//将相同操作符的资源属性条件链接
							sql = "";
							values = new ArrayList();
						}
						if(!"".equals(sql))//如果是同一个资源属性用or链接
							sql += " or ";
						sql += subSql.getSql();//链接SQL
						values.addAll(subSql.getValues());//链接参数
						oldRpId = rpId;
					}
				}
				if(!"".equals(oldRpId))//追加最后一个资源属性的条件控制
				{
					SqlResult rpSql = new SqlResult();
					rpSql.appendSql(" and ("+sql+")");
					rpSql.appendValues(values);
					sqlResult.append(rpSql);	//将条件链接
				}
			}
		}
		Object sqlObj = valueMap.get("sql");
		if(sqlObj != null)
		{
			String strSql = (String) sqlObj;
			if(!"".equals(StringUtils.checkNullString(strSql)))
			{
//				sqlResult.append(StringReplace.replaceAllVar(strSql, null, RConstants.DATA_VAR_PAGE));
				sqlResult.append(SqlReplace.replaceAllVar(strSql, null,null));
			}
		}
		return sqlResult;
	}
	
	public Map getPrivelegeMap(String userId, String resId, String opId)
	{
		Map dataMap = null;
		Map valueMap = this.getOpData(userId, resId, opId);
		if(valueMap == null)
			return dataMap;
		Object dpObj = valueMap.get("odp");	//获取操作数据权限
		if(dpObj != null)//验证是否存在操作数据权限
		{
			List<DataPrivilege> dpList = (List<DataPrivilege>) dpObj;//取出操作数据权限
			if(dpList != null && dpList.size() > 0)
			{
				dataMap = new HashMap<String, String>();
				DataPrivilege dp = null;
				ResProperty rp = null;
				String key;
				String datas = "";
				for(int i=0;i<dpList.size();i++)
				{
					dp = dpList.get(i);
					rp = dp.getRp();		//资源属性
					key = rp.getFieldname();//字段名
					datas = dp.getData();	//数据值
					if(rp != null)
					{
						String type = rp.getIndatasourtype() + "";
						if("1".equals(type))//当rp.getIndatasourtype()这个类型等于1时:是系统变量,需要转换一下
						{
							String[] dataArray = datas.split(",");
							datas = "";
							for(int k=0;k<dataArray.length;k++)
							{
								if(!"".equals(datas))
									datas += ",";
								String tempData = "#" + dataArray[k] + "#";
								datas += SqlReplace.stringReplaceAllVar(tempData, null);//转换系统变量
							}
						}
						Object obj = dataMap.get(key);
						if(obj != null) {
							dataMap.put(key, (String) obj + "," + datas);
						} else {
							dataMap.put(key, datas);
						}
					}
				}
			}
		}
		return dataMap;
	}
	
	public Map getOpData(String userId, String resId, String opId)
	{
		Map valueMap = null;
		Map privilegeMap = this.getPrivilegeByCache(userId);
		if(privilegeMap != null)//如果权限Map不为空则继续取值
		{
			Object objRes = privilegeMap.get("res");
			if(objRes != null)//objRes对象不为空则获取出资源Map
			{
				Map resMap = (Map) objRes;
				if(resMap != null)//获取的资源Map若不为空则继续取值
				{
					Object objOp = resMap.get(resId);
					if(objOp != null)//objOp对象不为空则或取出操作Map
					{
						Map opMap = (Map) objOp;
						if(opMap != null)//获取的操作Map若不为空则继续取值
						{
							Object objValue = opMap.get(opId);
							if(objValue != null)//objValue对象若不为空则获取出数据权限Map
								valueMap = (Map) objValue;	
						}
					}
				}
			}
		}
		return valueMap;
	}

	public void setPrivilegeToCache(String userId)
	{
		if("".equals(StringUtils.checkNullString(userId)))
			return;
		BaseCacheManager.put(Constants.PRIVILEGECACHE, userId, this.getMaxPrivilegeByUserID(userId));
	}
	
	private Map getPrivilegeByCache(String userId)
	{
		boolean isusercache = Constants.ISUSERCACHE;//是否使用缓存
		Map privilegeMap = null;//权限Map
		if(isusercache)//若用缓存则从缓存中获取数据
		{
			Object objUser = BaseCacheManager.get(Constants.PRIVILEGECACHE, userId);
			if(objUser == null)//若缓存中不存在此用户,则从数据库中查询出此用户权限,并添加到缓存中
			{
				privilegeMap = this.getMaxPrivilegeByUserID(userId);
				BaseCacheManager.put(Constants.PRIVILEGECACHE, userId, privilegeMap);
			}
			else
			{
				privilegeMap = (Map)objUser;
			}
		}
		else//不使用缓存直接访问数据库
		{
			privilegeMap = this.getMaxPrivilegeByUserID(userId);
		}
		return privilegeMap;
	}
	
	/**
	 * 根据用户ID获取此人最大权限
	 * @param userId	用户ID
	 * @return
	 */
	private Map getMaxPrivilegeByUserID(String userId)
	{
		Map privilegeMap = new HashMap();//权限Map
		Map menuMap = this.getMaxMenuByUserID(userId);//获取最大目录树权限
		Map resMap = this.getMaxResOpByUserID(userId);//获取最大资源操作以及操作的数据权限
		privilegeMap.put("menu", menuMap);
		privilegeMap.put("res", resMap);
		return privilegeMap;
	}

	private Map getMaxMenuByUserID(String userId)
	{
		if("".equals(StringUtils.checkNullString(userId)))
			return null;
		Map menuMap = new HashMap();//菜单节点Map
		List<String> roleIdList = roleManagerService.getAllRoleIdByUserID(userId);
		List<MenuDtree> treeList = roleManagerService.getMenuByRoleID(roleIdList);//获取菜单目录最大权限
		List<MenuDtree> subList = new ArrayList();//用于存放单个导航下的所有节点信息
		List<MenuDtree> navigateList = new ArrayList();//用于存放导航栏信息
		String navigateId = "";//存放导航栏ID
		if (treeList != null && treeList.size() > 0)
		{
			for (int i = 0; i < treeList.size(); i++)
			{
				if ("0".equals(treeList.get(i).getParentid()))//到节点父ID等于0时,则为导航栏节点
				{
					navigateList.add(treeList.get(i));//添加导航栏节点信息
					if (!"".equals(navigateId))
						menuMap.put(navigateId, subList);//若导航栏ID不是初始值的空,则将navigateId导航下所有的节点信息添加到menuMap中
					navigateId = treeList.get(i).getId();
					subList = new ArrayList();;//清空节点List作为存储下一个导航栏下的节点信息
				}
				else
				{
					MenuDtree mdTree = treeList.get(i);
					mdTree.setLevel(mdTree.getLevel()-1);
					subList.add(mdTree);//拼装节点信息
				}
			}
			menuMap.put(navigateId, subList);//将最后一个导航栏下的节点信息添加到Map中
		}
		menuMap.put("navigate", navigateList);//将导航栏节点信息添加到menuMap中
		return menuMap;
	}

	private Map getMaxResOpByUserID(String userId)
	{
		if("".equals(StringUtils.checkNullString(userId)))
			return null;
		Map resMap = new HashMap();
//		List roleIdList = roleManagerService.getRoleIdByUserID(userId);
		List roleIdList = roleManagerService.getAllRoleIdByUserID(userId);
		if(roleIdList != null && roleIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(roleIdList);
				//此SQL是获取角色资源操作以及数据权限所有数据
				StringBuffer sql = new StringBuffer();
				sql.append("select distinct rro.resid, rro.opid, odp.rroid, odp.operator, odp.privilegedata, rp.*");
				sql.append("  from bs_t_sm_roleresop rro,bs_t_sm_opdataprivilege odp,bs_t_sm_resproperty rp");
				sql.append(" where odp.rroid(+) = rro.pid and rp.pid(+) = odp.resproid and rro.roleid in (" + map.get("?") + ")");
				sql.append(" order by rro.resid, rro.opid, rp.pid, odp.operator");
				QueryAdapter queryAdapter = new QueryAdapter();
				DataTable table = queryAdapter.executeQuery(sql.toString(), (Object[]) map.get("obj"));
				//将执行后的结果集table进行解析
				if(table != null && table.length() > 0)
				{
					Map opMap = new HashMap();//操作Map 用来存放操作数据权限以操作ID为key,valueMap作为值
					Map valueMap = new HashMap();//权限Map 用来存放操作的数据权限的Map
					String oldResid = "";//table中前一个资源ID
					String oldOpid = "";//table中前一个操作ID
					String oldRpid = "";//table中前一个资源属性ID
					String oldOperator = "";//table中前一个操作符
					String data = "";//用于存放资源属性对应的权限值
					String operator = "";
					ResProperty rp = null;//资源属性对象
					List<DataPrivilege> dpList = new ArrayList();//数据权限List
					DataRow row = null;
					for(int i=0;i<table.length();i++)
					{
						row = table.getDataRow(i);
						String opid = row.getString("opid");//table中当前资源ID
						String resid = row.getString("resid");//table中当前操作ID
						String rroid = row.getString("rroid");//table中当前角色资源操作关系ID
						//由于多次判断以下两条件,所以用此变量
						boolean resCon = (!"".equals(oldResid) && !oldResid.equals(resid));//当资源改变时,resCon为真
						boolean opCon = (!"".equals(oldOpid) && !oldOpid.equals(opid));//当操作改变时,opCon为真
						if(!"".equals(StringUtils.checkNullString(rroid)) || resCon || opCon)//当资源或操作改变时,数据权限值也将重新记录
						{
							String rpid = StringUtils.checkNullString(row.getString("pid"));
							boolean rpCon = (!"".equals(oldRpid) && !oldRpid.equals(rpid));//当资源属性改变时,rpCon为真
							operator = StringUtils.checkNullString(row.getString("operator"));
							if(!"".equals(oldOperator) && (!oldOperator.equals(operator) || rpCon || resCon || opCon))//当资源、操作、资源属性改变时,数据权限值也将重新记录
							{//当操作符改变时,将数据存入DataPrivilege对象
								DataPrivilege dp = new DataPrivilege();
								dp.setRp(rp);
								dp.setOperator(oldOperator);
								dp.setData(data);
								dpList.add(dp);
								data = "";
								oldRpid = "";
								oldOperator = "";
							}
							if(!"".equals(StringUtils.checkNullString(rroid)))//若角色资源操作ID不为空,拼装资源属性对象和数据值
							{
								rp = new ResProperty();
								rp.setPid(rpid);
								rp.setRespid(StringUtils.checkNullString(row.getString("resid")));
								rp.setFieldname(StringUtils.checkNullString(row.getString("fieldname")));
								rp.setFielddisplayvalue(StringUtils.checkNullString(row.getString("fielddisplayvalue")));
								rp.setIntype(NumberUtils.formatToLong(row.getString("intype")));
								rp.setIndatasourtype(NumberUtils.formatToLong(row.getString("indatasourtype")));
								rp.setInvaluetype(NumberUtils.formatToLong(row.getString("invaluetype")));
								rp.setIndata(StringUtils.checkNullString(row.getString("indata")));
								rp.setOrdernum(NumberUtils.formatToLong(row.getString("ordernum")));
								rp.setCreater(StringUtils.checkNullString(row.getString("creater")));
								rp.setCreatetime(NumberUtils.formatToLong(row.getString("createtime")));
								rp.setLastmodifier(StringUtils.checkNullString(row.getString("lastmodifier")));
								rp.setLastmodifytime(NumberUtils.formatToLong(row.getString("lastmodifytime")));
								rp.setRemark(StringUtils.checkNullString(row.getString("remark")));
								oldOperator = operator;
								oldRpid = rpid;
								data = UltraSmUtil.getSimpleStr(data, StringUtils.checkNullString(row.getString("privilegedata")));
							}
						}
						if(opCon || resCon)//当操作改变时,则拼装到操作Map中,或当资源有所改变时,操作也将重新记录
						{
							valueMap.put("odp", dpList);
							opMap.put(oldOpid, valueMap);
							valueMap = new HashMap();
							dpList = new ArrayList();
						}
						oldOpid = opid;//作为下条记录使用
						if(resCon)//资源改变,则拼装到资源Map中
						{
							resMap.put(oldResid, opMap);
							opMap = new HashMap();
						}
						oldResid = resid;//作为下条记录使用
					}
					if(!"".equals(oldOperator) || !"".equals(oldRpid))//若最后一个操作的资源属性不为空则拼装数据最后一个资源属性的数据权限
					{
						DataPrivilege dp = new DataPrivilege();
						dp.setRp(rp);
						dp.setOperator(operator);
						dp.setData(data);
						dpList.add(dp);
					}
					valueMap.put("odp", dpList);//拼装最后一个数据权限Map
					opMap.put(oldOpid, valueMap);//拼装最后一个操作Map
					resMap.put(oldResid, opMap);//拼装最后一个资源Map
				}
				//测试拼装是否正确
				/*
				List resKey = UltraSmUtil.getMapKeys(resMap);
				for(int i=0;i<resKey.size();i++)
				{
					Map opMap = (Map) resMap.get((String) resKey.get(i));
					List opKey = UltraSmUtil.getMapKeys(opMap);
					for(int j=0;j<opKey.size();j++)
					{
						Map valueMap = (Map) opMap.get((String) opKey.get(j));
						List<DataPrivilege> dpList = (List<DataPrivilege>) valueMap.get("odp");
						DataPrivilege dp = null;
						if(dpList.size()<=0)
						System.out.println(resKey.get(i) + "     " + opKey.get(j));
						for(int k=0;k<dpList.size();k++)
						{
							dp = dpList.get(k);
							ResProperty rp = dp.getRp();
							System.out.println(resKey.get(i)+"     "+opKey.get(j) + "     " + dp.getOperator() + "     " + dp.getData() + "     " + rp.getFieldname());
						}
					}
				}
				*/
				sql = new StringBuffer();
				sql.append("select distinct rro.resid, rro.opid, sdp.rroid, sdp.privilegedata");
				sql.append("  from bs_t_sm_roleresop rro, bs_t_sm_sqldataprivilege sdp");
				sql.append(" where rro.pid = sdp.rroid and rro.roleid in (" + map.get("?") + ")");
				sql.append(" order by rro.resid, rro.opid");
				queryAdapter = new QueryAdapter();
				table = queryAdapter.executeQuery(sql.toString(), (Object[]) map.get("obj"));
				if(table != null && table.length() > 0)
				{
					Map opMap = null;
					Map valueMap = null;
					String oldResid = "";//table中前一个资源ID
					String oldOpid = "";//table中前一个操作ID
					String strSql = "";
					DataRow row = null;
					for(int i=0;i<table.length();i++)
					{
						row = table.getDataRow(i);
						String opid = row.getString("opid");//table中当前资源ID
						String resid = row.getString("resid");//table中当前操作ID
						boolean resCon = (!"".equals(oldResid) && !oldResid.equals(resid));//当资源改变时,resCon为真
						boolean opCon = (!"".equals(oldOpid) && !oldOpid.equals(opid));//当操作改变时,opCon为真
						if(opCon || resCon)
						{
							Object opObj = resMap.get(oldResid);
							if(opObj != null)
							{
								opMap = (Map) opObj;
								if(opMap != null)
								{
									Object valueObj = opMap.get(oldOpid);
									if(valueObj != null)
									{
										valueMap = (Map) valueObj;
										if(valueMap != null)
										{
											strSql = " and (" + strSql + ") ";
											valueMap.put("sql", strSql);
										}
									}
								}
							}
							strSql = "";
						}
						oldOpid = opid;
						oldResid = resid;
						if(!"".equals(strSql))
							strSql += " or ";
						strSql += " (" + row.getString("privilegedata") + ") ";
					}
					Object opObj = resMap.get(oldResid);
					if(opObj != null)
					{
						opMap = (Map) opObj;
						if(opMap != null)
						{
							Object valueObj = opMap.get(oldOpid);
							if(valueObj != null)
							{
								valueMap = (Map) valueObj;
								if(valueMap != null)
								{
									strSql = " and (" + strSql + ") ";
									valueMap.put("sql", strSql);
								}
							}
						}
					}
				}
			}
			catch  (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		return resMap;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
	public static void main(String[] args)
	{
		String str="";
		long dd=(long) Integer.valueOf("");
	}
}
