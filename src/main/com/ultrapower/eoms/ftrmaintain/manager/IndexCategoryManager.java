package com.ultrapower.eoms.ftrmaintain.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ftrmaintain.model.DBIndexSource;
import com.ultrapower.eoms.ftrmaintain.model.DisplayModel;
import com.ultrapower.eoms.ftrmaintain.model.FieldSpanInfo;
import com.ultrapower.eoms.ftrmaintain.model.FileIndexSource;
import com.ultrapower.eoms.ftrmaintain.model.IndexCategory;
import com.ultrapower.eoms.ftrmaintain.model.IndexFieldCfg;
import com.ultrapower.eoms.ftrmaintain.model.IndexPath;
import com.ultrapower.eoms.ftrmaintain.service.IndexCategoryService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class IndexCategoryManager implements IndexCategoryService {

	private IDao<IndexCategory> indexCategoryDao;
	private IDao<IndexPath> indexPathDao;
	private IDao<DBIndexSource> dbSourceDao;
	private IDao<FileIndexSource> fileSourceDao;
	private IDao<IndexFieldCfg> indexFieldDao;
	private IDao<DisplayModel> displayModelDao;
	private IDao<FieldSpanInfo> fieldSpanInfoDao;

	public boolean saveCategoryInfo(IndexCategory category,
			List<IndexPath> indexPath, List<DBIndexSource> dbSource,
			List<FileIndexSource> fileSource,List<IndexCategory> children) {
		if(category==null)
		{
			return false;
		}
		try
		{
			//如果是修改逻辑索引类别，则先修改其原来子索引类别的parentId为空
			if(category.getPid()!=null && category.getIsphysical()==0L)
			{
				String hql = "update IndexCategory set parentid = ? where parentid = ?";
				indexCategoryDao.executeUpdate(hql, new Object[]{null,category.getPid()});
			}
			//存储索引类别基本信息
			String categoryId = saveCategory(category,null);
			if(categoryId==null)
				return false;
			//如果为物理索引类别，保存索引路径信息
			if(indexPath!=null && category.getIsphysical()==1L)
			{
				this.saveIndexPath(indexPath,categoryId);
			}
			//如果为物理索引类别，保存数据库数据源信息
			if(dbSource!=null && category.getIsphysical()==1L)
			{
				this.saveDBSource(dbSource,categoryId);
			}
			//如果为物理索引类别，保存文件数据源信息
			if(fileSource!=null && category.getIsphysical()==1L)
			{
				this.saveFileSource(fileSource,categoryId);
			}
			//如果为逻辑索引类别，保存子索引类别信息
			if(category.getIsphysical()==0L && children!=null)
			{
				for(IndexCategory ca:children)
				{
					saveCategory(ca,categoryId);
				}
			}
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public String saveCategory(IndexCategory category,String parentId) {
		if(category==null)
			return null;
		if(parentId!=null)
			category.setParentid(parentId);
		String categoryId = category.getPid();
		if("".equals(StringUtils.checkNullString(categoryId)))
		{
			indexCategoryDao.save(category);
			return category.getPid();
		}
		else
		{
			indexCategoryDao.saveOrUpdate(category);
			return category.getPid();
		}
	}
	
	public boolean saveDBSource(List<DBIndexSource> dbsource,String categoryId) {
		if(dbsource==null || categoryId==null)
			return false;
		dbSourceDao.executeUpdate("delete DBIndexSource where indextypeid = ?", new Object[]{categoryId});
		int len = dbsource.size();
		DBIndexSource source;
		for(int i=0;i<len;i++)
		{
			source = dbsource.get(i);
			source.setIndextypeid(categoryId);
			dbSourceDao.save(source);
		}
		return true;
	}

	public boolean saveFileSource(List<FileIndexSource> filesource,String categoryId) {
		if(filesource==null || categoryId==null)
			return false;
		fileSourceDao.executeUpdate("delete FileIndexSource where indextypeid = ?", new Object[]{categoryId});
		int len = filesource.size();
		FileIndexSource source;
		for(int i=0;i<len;i++)
		{
			source = filesource.get(i);
			source.setIndextypeid(categoryId);
			fileSourceDao.save(source);
		}
		return true;
	}

	public boolean saveIndexPath(List<IndexPath> indexPath,String categoryId) {
		if(indexPath==null || categoryId==null)
			return false;
		indexPathDao.executeUpdate("delete IndexPath where indextypeid = ?", new Object[]{categoryId});
		int len = indexPath.size();
		IndexPath path;
		for(int i=0;i<len;i++)
		{
			path = indexPath.get(i);
			path.setIndextypeid(categoryId);
			indexPathDao.save(path);
		}
		return true;
	}
	
	public List<IndexCategory> getCategoryByIdList(List<String> idList)
	{
		if(idList==null)
			return null;
		Map map = UltraSmUtil.getSqlParameter(idList);
		String hql = "from IndexCategory where pid in("+map.get("?")+")";
		List<IndexCategory> indexCategory = indexCategoryDao.find(hql, (Object[])map.get("obj"));
		return indexCategory;
	}
	
	public boolean deleteCategory(List<String> categoryId) {
		if(categoryId==null || categoryId.size()==0)
			return false;
		Map map = UltraSmUtil.getSqlParameter(categoryId);
		try {
			String hql = "delete from IndexFieldCfg where indextypeid in("+map.get("?")+")";
			indexFieldDao.executeUpdate(hql, (Object[])map.get("obj"));
			hql = "delete from IndexPath where indextypeid in("+map.get("?")+")";
			indexPathDao.executeUpdate(hql, (Object[])map.get("obj"));
			hql = "delete from DBIndexSource where indextypeid in("+map.get("?")+")";
			dbSourceDao.executeUpdate(hql, (Object[])map.get("obj"));
			hql = "delete from FileIndexSource where indextypeid in("+map.get("?")+")";
			fileSourceDao.executeUpdate(hql, (Object[])map.get("obj"));
			indexCategoryDao.executeUpdate("update IndexCategory set parentid=null where parentid in("+map.get("?")+")", (Object[])map.get("obj"));
			indexCategoryDao.executeUpdate("delete from IndexCategory where pid in("+map.get("?")+")", (Object[])map.get("obj"));
			hql = "from DisplayModel where indextypeid in("+map.get("?")+")";
			List<DisplayModel> dmlst = displayModelDao.find(hql, (Object[])map.get("obj"));
			List<String> dmids = null;
			int len = 0;
			if(dmlst!=null)
			{
				len = dmlst.size();
				dmids = new ArrayList<String>();
			}
			for(int i=0;i<len;i++)
			{
				dmids.add(dmlst.get(i).getPid());
			}
			Map map2 = UltraSmUtil.getSqlParameter(dmids);
			hql = "delete from FieldSpanInfo where modelpid in("+map2.get("?")+")";
			fieldSpanInfoDao.executeUpdate(hql, (Object[])map2.get("obj"));
			hql = "delete from DisplayModel where indextypeid in ("+map.get("?")+")";
			displayModelDao.executeUpdate(hql, (Object[])map.get("obj"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public IndexCategory getCategoryById(String id) {
		if(id==null)
			return null;
		IndexCategory category = indexCategoryDao.get(id);
		return category;
	}
	
	public List<IndexCategory> getChildCategory(String parentId)
	{
		if(parentId==null)
			return null;
		List<IndexCategory> indexCategory;
		String hql = "from IndexCategory where parentid = ?";
		indexCategory = indexCategoryDao.find(hql, new Object[]{parentId});
		return indexCategory;
	}
	
	public void setIndexCategoryDao(IDao<IndexCategory> indexCategoryDao) {
		this.indexCategoryDao = indexCategoryDao;
	}

	public void setIndexPathDao(IDao<IndexPath> indexPathDao) {
		this.indexPathDao = indexPathDao;
	}

	public void setDbSourceDao(IDao<DBIndexSource> dbSourceDao) {
		this.dbSourceDao = dbSourceDao;
	}

	public void setFileSourceDao(IDao<FileIndexSource> fileSourceDao) {
		this.fileSourceDao = fileSourceDao;
	}

	public void setDisplayModelDao(IDao<DisplayModel> displayModelDao) {
		this.displayModelDao = displayModelDao;
	}

	public List<DBIndexSource> getDBSourceByCategoryId(String categoryId) {
		if(categoryId==null)
			return null;
		List<DBIndexSource> source = dbSourceDao.find("from DBIndexSource where indextypeid = ?", new Object[]{categoryId});
		return source;
	}

	public List<FileIndexSource> getFileSourceByCategoryId(String categoryId) {
		if(categoryId==null)
			return null;
		List<FileIndexSource> source = fileSourceDao.find("from FileIndexSource where indextypeid = ?", new Object[]{categoryId});
		return source;
	}

	public List<IndexPath> getIndexPathByCategoryId(String categoryId) {
		if(categoryId==null)
			return null;
		List<IndexPath> path = indexPathDao.find("from IndexPath where indextypeid = ?", new Object[]{categoryId});
		return path;
	}

	public void setIndexFieldDao(IDao<IndexFieldCfg> indexFieldDao) {
		this.indexFieldDao = indexFieldDao;
	}

	public Map<String, String> getSelectiveChildren(String categoryId) {
		String sql1 = "select c.pid,c.displayname from bs_t_ftr_indexcategory c where ( parentid is null or parentid = ? ) and pid <> ?";
		String sql2 = "select c.pid,c.displayname from bs_t_ftr_indexcategory c where parentid is null";
		QueryAdapter adapter = new QueryAdapter();
		DataTable tb = null;
		if(categoryId==null)
			tb = adapter.executeQuery(sql2);
		else
			tb = adapter.executeQuery(sql1,new Object[]{categoryId,categoryId});
		int len = 0;
		if(tb!=null)
			len = tb.length();
		Map<String,String> results = new HashMap<String,String>();
		DataRow row;
		for(int i=0;i<len;i++)
		{
			row = tb.getDataRow(i);
			results.put(row.getString("pid"), row.getString("displayname"));
		}
		return results;
	}

	public Map<String, String> getPhysicalCategoryMap() {
		Map<String,String> result = null;
		String sql = "select pid,displayname from bs_t_ftr_indexcategory where isphysical = 1 order by ordernum";
		QueryAdapter adapter = new QueryAdapter();
		DataTable tb = adapter.executeQuery(sql);
		int len = 0;
		if(tb!=null)
		{
			len = tb.length();
			result = new HashMap<String,String>();
		}
		for(int i=0;i<len;i++)
		{
			DataRow row = tb.getDataRow(i);
			result.put(row.getString("pid"), row.getString("displayname"));
		}
		return result;
	}
}
