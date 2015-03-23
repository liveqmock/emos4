package com.ultrapower.eoms.ftrmaintain.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ftrmaintain.model.DBIndexSource;
import com.ultrapower.eoms.ftrmaintain.model.FileIndexSource;
import com.ultrapower.eoms.ftrmaintain.model.IndexCategory;
import com.ultrapower.eoms.ftrmaintain.model.IndexPath;
import com.ultrapower.eoms.ftrmaintain.model.IndexSource;
import com.ultrapower.eoms.ftrmaintain.service.IndexCategoryService;

public class CategoryManagerAction extends BaseAction{
	private IndexCategoryService indexCategoryService;
	private String pid;
	private IndexCategory indexCategory;
	private String childCategoryId;
	private String parentCategoryName;
	private List<IndexPath> indexPath;
	private List<IndexSource> indexSource;
	private Map<String,String> selectiveChildren;
	public String indexCategoryList()
	{
		return SUCCESS;
	}
	
	public String categoryLoad()
	{
		if(pid!=null)
		{
			indexCategory = indexCategoryService.getCategoryById(pid);
			if(indexCategory==null)
				return findForward("indexCategorySave");
			indexPath = indexCategoryService.getIndexPathByCategoryId(pid);
			List<DBIndexSource> dbSource = indexCategoryService.getDBSourceByCategoryId(pid);
			List<FileIndexSource> fileSource = indexCategoryService.getFileSourceByCategoryId(pid);
			indexSource = new ArrayList<IndexSource>();
			if(dbSource!=null)
			{
				for(DBIndexSource source:dbSource)
				{
					indexSource.add(new IndexSource("1",source.getSqlname(),source.getIsCustomSource()));
				}
			}
			if(fileSource!=null)
			{
				for(FileIndexSource source:fileSource)
				{
					indexSource.add(new IndexSource("2",source.getSourceinfo(),source.getDatafrom(),source.getIsCustomSource()));
				}
			}
			if(indexCategory.getParentid()!=null)
			{
				IndexCategory parent = indexCategoryService.getCategoryById(indexCategory.getParentid());
				if(parent!=null)
					parentCategoryName = parent.getDisplayname();
			}
			if(indexCategory.getIsphysical()==0L)
			{
				List<IndexCategory> children = indexCategoryService.getChildCategory(pid);
				int len =0;
				if(children!=null)
					len = children.size();
				IndexCategory temp;
				StringBuffer ids = new StringBuffer();
				for(int i=0;i<len;i++)
				{
					temp = children.get(i);
					ids.append(","+temp.getPid());
				}
				if(ids.length()>0)
					childCategoryId = ids.substring(1);
			}
		}
		selectiveChildren = indexCategoryService.getSelectiveChildren(pid);
		return findForward("indexCategorySave");
	}
	
	public String saveCategoryInfo()
	{
		boolean b = false;
		if(indexCategory!=null)
		{
			UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
			String userpid = "";
			if(userSession != null)
				userpid = userSession.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			if("".equals(StringUtils.checkNullString(indexCategory.getPid())))
			{
				indexCategory.setCreater(userpid);
				indexCategory.setCreatetime(currentTime);
			}
			indexCategory.setLastmodifier(userpid);
			indexCategory.setLastmodifytime(currentTime);
			if(indexCategory.getIsphysical()==1L)
			{
				String pathStr = StringUtils.checkNullString(this.getRequest().getParameter("_pathStr"));
				if(!"".equals(pathStr))
				{
					String[] pathArr = pathStr.split(";");
					indexPath = new ArrayList<IndexPath>();
					for(String str:pathArr)
					{
						indexPath.add(new IndexPath(str.split(",")[0],Long.valueOf(str.split(",")[1])));
					}
				}
				String sourceStr = StringUtils.checkNullString(this.getRequest().getParameter("_sourceStr"));
				List<DBIndexSource> dbsource = null;
				List<FileIndexSource> filesource = null;
				if(!"".equals(sourceStr))
				{
					String[] sourceArr = sourceStr.split(";");
					dbsource = new ArrayList<DBIndexSource>();
					filesource = new ArrayList<FileIndexSource>();
					String type;
					String value;
					Long isCustomSource;
					String fileFrom;
					for(String str:sourceArr)
					{
						type = str.split(",")[0];
						value = str.split(",")[1];
						isCustomSource = Long.valueOf(str.split(",")[2]);
						if("1".equals(type))
						{
							dbsource.add(new DBIndexSource(null,null,value,isCustomSource));
						}
						else if("2".equals(type))
						{
							fileFrom = str.split(",")[3];
							filesource.add(new FileIndexSource(null,null,Long.valueOf(fileFrom),value,isCustomSource));
						}
					}
				}
				b = indexCategoryService.saveCategoryInfo(indexCategory, indexPath, dbsource, filesource, null);
			}
			else if(indexCategory.getIsphysical()==0L)
			{
				indexCategory.setHlttextpath(null);
				indexCategory.setIsaddhlttext(0L);
				String childCategoryId = StringUtils.checkNullString(this.getRequest().getParameter("childCategoryId"));
				if(!"".equals(childCategoryId))
				{
					String[] idArr = childCategoryId.split(",");
					List<String> idlist = new ArrayList<String>();
					for(String str:idArr)
					{
						if(!"".equals(str))
						{
							idlist.add(str);
						}
					}
					List<IndexCategory> children = indexCategoryService.getCategoryByIdList(idlist);
					b = indexCategoryService.saveCategoryInfo(indexCategory, null, null, null, children);
				}
			}
		}
		if(b)
		{
			this.getRequest().setAttribute("parafresh", "true");
			this.getRequest().setAttribute("returnMessage", Internation.language("com_msg_saveSuccess")+"!");
		}
		else
		{
			this.getRequest().setAttribute("parafresh", "false");
			this.getRequest().setAttribute("returnMessage", Internation.language("com_msg_saveErr")+"!");
		}
		return "refresh";
	}
	
	public String delCategory()
	{
		String ids = StringUtils.checkNullString(this.getRequest().getParameter("var_selectvalues"));
		if(!"".equals(ids))
		{
			String[] idArr = ids.split(",");
			List<String> idlst = new ArrayList<String>();
			for(int i=0;i<idArr.length;i++)
			{
				idlst.add(idArr[i]);
			}
			if(idlst.size()>0)
				indexCategoryService.deleteCategory(idlst);
		}
		return findRedirect("indexCategoryList");
	}
	
	public void setIndexCategoryService(IndexCategoryService indexCategoryService) {
		this.indexCategoryService = indexCategoryService;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public IndexCategory getIndexCategory() {
		return indexCategory;
	}

	public void setIndexCategory(IndexCategory indexCategory) {
		this.indexCategory = indexCategory;
	}

	public String getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(String childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public String getParentCategoryName() {
		return parentCategoryName;
	}

	public List<IndexPath> getIndexPath() {
		return indexPath;
	}

	public List<IndexSource> getIndexSource() {
		return indexSource;
	}

	public Map<String, String> getSelectiveChildren() {
		return selectiveChildren;
	}

	
	
}
