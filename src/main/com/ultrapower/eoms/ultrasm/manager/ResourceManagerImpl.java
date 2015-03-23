package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.ultrasm.model.ResProperty;
import com.ultrapower.eoms.ultrasm.model.Resource;
import com.ultrapower.eoms.ultrasm.service.ResourceManagerService;
import com.ultrapower.eoms.ultrasm.service.ResourceUrlManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 8, 2010 6:23:11 PM
 * @descibe 
 */
@Transactional
public class ResourceManagerImpl implements ResourceManagerService{

	private IDao<Resource> resourceDao;
	private IDao<ResProperty> resourcePropertyDao;
	private RoleManagerService roleManagerService;
	private ResourceUrlManagerService resourceUrlManagerService;

	public List<Resource> getResource() {
		PageLimit pageLimit = PageLimit.getInstance();
		List<Resource> resList = null;
		try{
			resList = resourceDao.pagedQuery("from Resource", pageLimit, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resList;
	}

	public List<Resource> getResource(String name, String parentid,
			String systemtype, String defineType) {
		PageLimit pageLimit = PageLimit.getInstance();
		StringBuffer sql = new StringBuffer();
		sql.append("from Resource where 1=1 ");
		if(name!="" && name!=null && !name.equals(""))
			sql.append(" and resname like '"+name+"'");
		if(parentid!="" && parentid!=null && !parentid.equals(""))
			sql.append(" and parentid like '"+parentid+"'");
		if(systemtype!="" && systemtype!=null && !systemtype.equals(""))
			sql.append(" and systemtype like '"+systemtype+"'");
		if(defineType!="" && defineType!=null && !defineType.equals(""))
			sql.append(" and definetype like '"+defineType+"'");
		List<Resource> resList = null;
		try{
			resList = resourceDao.pagedQuery(sql.toString(), pageLimit, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resList;
	}
	
	
	public void addResource(Resource resource) {
		try{
			resourceDao.save(resource);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addResource(Resource resource, List<ResProperty> resProperyList) {
		try{
			resourceDao.save(resource);
		}catch(Exception e){
			e.printStackTrace();
		}
		int resProperyListLen = 0;
		if(resProperyList!=null)
		{
			resProperyListLen = resProperyList.size();
		}
		for(int i=0;i<resProperyListLen;i++){
			ResProperty resProperty = resProperyList.get(i);
			resProperty.setRespid(resource.getPid());
			resProperty.setCreater(resource.getCreater());
			resProperty.setCreatetime(resource.getCreatetime());
			resProperty.setLastmodifier(resource.getLastmodifier());
			resProperty.setLastmodifytime(resource.getLastmodifytime());
			this.addResProperty(resProperty);
		}
	}
	
	public void updateResource(Resource resource,
			List<ResProperty> addResPropertyList,
			List<ResProperty> modResPropertyList, List delResPropertyId) {
		//进行资源修改
		try{
			this.updateResource(resource);
		}catch(Exception e){
			e.printStackTrace();
		}
		//进行资源属性添加
		int addResPropertyListLen = 0;
		if(addResPropertyList!=null){
			addResPropertyListLen = addResPropertyList.size();
		}
		for(int add=0;add<addResPropertyListLen;add++){
			ResProperty resProperty = addResPropertyList.get(add);
			resProperty.setRespid(resource.getPid());
			resProperty.setCreater(resource.getCreater());
			resProperty.setCreatetime(resource.getCreatetime());
			resProperty.setLastmodifier(resource.getLastmodifier());
			resProperty.setLastmodifytime(resource.getLastmodifytime());
			this.addResProperty(resProperty);
		}
		//进行资源属性修改
		int modResPropertyListLen = 0;
		if(modResPropertyList!=null){
			modResPropertyListLen = modResPropertyList.size();
		}
		for(int mod=0;mod<modResPropertyListLen;mod++){
			ResProperty resProperty = modResPropertyList.get(mod);
			resProperty.setRespid(resource.getPid());
			resProperty.setCreater(resource.getCreater());
			resProperty.setCreatetime(resource.getCreatetime());
			resProperty.setLastmodifier(resource.getLastmodifier());
			resProperty.setLastmodifytime(resource.getLastmodifytime());
			this.updateResProperty(resProperty);
		}
		//资源属性删除
		int delResPropertyIdLen = 0;
		if(delResPropertyId!=null){
			delResPropertyIdLen = delResPropertyId.size();
		}
		for(int del=0;del<delResPropertyIdLen;del++){
			String pid = (String) delResPropertyId.get(del);
			this.deleteResProperty(pid);
		}
		//操作数据权限删除
		roleManagerService.deleteOdpByRpId(delResPropertyId);
	}
	
	public List<ResProperty> getResProperty(String resourcePid) {
		List<ResProperty> resPropertyList = new ArrayList();
		
		if(resourcePid!="" && !resourcePid.equals(""))
		{
			resPropertyList = resourcePropertyDao.find("from ResProperty where respid = ?", new Object[]{resourcePid});
		}
		return resPropertyList;
	}
	

	
	public void updateResource(Resource resource) {
		try{
			resourceDao.saveOrUpdate(resource);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setResourceStatus(String pids) {
		//删除资源
		try{
			 resourceDao.executeUpdate("delete Resource where pid in ("+pids+")", null);
		}catch(Exception e){
			e.printStackTrace();
		}
		//删除资源有拥有的属性配置
		try{
			resourcePropertyDao.executeUpdate("delete ResProperty where respid in ("+pids+")", null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean deleteResourceById(List resIdList)
	{
		boolean result = false;
		resIdList = UltraSmUtil.removeNullData(resIdList);
		if(resIdList != null && resIdList.size() > 0)
		{
			Map map = UltraSmUtil.getSqlParameter(resIdList);
			//删除资源
			try{
				roleManagerService.clearRroByResId(resIdList);
				resourceUrlManagerService.delResUrlByResId(resIdList);
				resourceDao.executeUpdate("delete Resource where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				resourcePropertyDao.executeUpdate("delete ResProperty where respid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				result = true;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}
	
	public ResProperty getResPropertyInfo(String resourcePid){
		return resourcePropertyDao.get(resourcePid);
	}
	
	private void addResProperty(ResProperty resProperty){
		try{
			resourcePropertyDao.save(resProperty);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void updateResProperty(ResProperty resProperty){
		try{
			resourcePropertyDao.saveOrUpdate(resProperty);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void deleteResProperty(String pid){
		try{
			resourcePropertyDao.removeById(pid);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Resource getResource(String pid) {
		return resourceDao.get(pid);
	}
	
	public void setResourceDao(IDao<Resource> resourceDao) {
		this.resourceDao = resourceDao;
	}

	public void setResourcePropertyDao(IDao<ResProperty> resourcePropertyDao) {
		this.resourcePropertyDao = resourcePropertyDao;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}

	public void setResourceUrlManagerService(ResourceUrlManagerService resourceUrlManagerService) {
		this.resourceUrlManagerService = resourceUrlManagerService;
	}
}
