package com.ultrapower.eoms.ftrmaintain.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ftrmaintain.model.DisplayModel;
import com.ultrapower.eoms.ftrmaintain.model.FieldSpanInfo;
import com.ultrapower.eoms.ftrmaintain.service.IndexDisplayModelService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class IndexDisplayModelManager implements IndexDisplayModelService {
	private IDao<DisplayModel> displayModelDao;
	private IDao<FieldSpanInfo> fieldSpanInfoDao;
	
	public DisplayModel getModelById(String id) {
		if(id==null)
			return null;
		DisplayModel model = displayModelDao.get(id);
		return model;
	}

	public String getSpanInfoByIndexId(String id) {
		if("".equals(StringUtils.checkNullString(id)))
			return "[]";
		StringBuffer sql = new StringBuffer();
		/*
			select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,lstposition
			from(
			select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,absposition - 1 lstposition
			      from (
			      select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,rownum absposition
			      from (
			      select f.pid fid,f.displayname fdisplayname,f.isdisplay fisdisplay,f.sourcetype fsourcetype,f.ordernum fordernum,i.cols icols,i.ordernum iordernum,i.displayzone idisplayzone
			      from bs_t_ftr_indexfieldcfg f
			      left outer join bs_t_ftr_fieldspaninfo i on f.pid = i.indexfieldid
			      where f.indextypeid = '004' and f.sourcetype = 1
			      order by f.ordernum))
			      where fisdisplay = 1
			union
			select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,absposition - 1 lstposition
			      from (
			      select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,rownum absposition
			      from (
			      select f.pid fid,f.displayname fdisplayname,f.isdisplay fisdisplay,f.sourcetype fsourcetype,f.ordernum fordernum,i.cols icols,i.ordernum iordernum,i.displayzone idisplayzone
			      from bs_t_ftr_indexfieldcfg f
			      left outer join bs_t_ftr_fieldspaninfo i on f.pid = i.indexfieldid
			      where f.indextypeid = '40288eef2d13056b012d1324b7d50002' and f.sourcetype = 2
			      order by f.ordernum))
			      where fisdisplay = 1
			)order by fsourcetype,fordernum
		 */
		//数据库数据源字段
		sql.append("select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,lstposition from(");
		sql.append(" select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,absposition - 1 lstposition");
		sql.append(" from (");
		sql.append(" select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,rownum absposition");
		sql.append(" from (");
		sql.append(" select f.pid fid,f.displayname fdisplayname,f.isdisplay fisdisplay,f.sourcetype fsourcetype,f.ordernum fordernum,i.cols icols,i.ordernum iordernum,i.displayzone idisplayzone");
		sql.append(" from bs_t_ftr_indexfieldcfg f");
		sql.append(" left outer join bs_t_ftr_fieldspaninfo i on f.pid = i.indexfieldid");
		sql.append(" where f.indextypeid = ? and f.sourcetype = 1");
		sql.append(" order by f.ordernum))");
		sql.append(" where fisdisplay = 1");
		sql.append(" union");
		//文件数据源字段
		sql.append(" select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,absposition - 1 lstposition");
		sql.append(" from (");
		sql.append(" select fid,fdisplayname,fisdisplay,fsourcetype,fordernum,icols,iordernum,idisplayzone,rownum absposition");
		sql.append(" from (");
		sql.append(" select f.pid fid,f.displayname fdisplayname,f.isdisplay fisdisplay,f.sourcetype fsourcetype,f.ordernum fordernum,i.cols icols,i.ordernum iordernum,i.displayzone idisplayzone");
		sql.append(" from bs_t_ftr_indexfieldcfg f");
		sql.append(" left outer join bs_t_ftr_fieldspaninfo i on f.pid = i.indexfieldid");
		sql.append(" where f.indextypeid = ? and f.sourcetype = 2");
		sql.append(" order by f.ordernum))");
		sql.append(" where fisdisplay = 1");
		sql.append(")order by fsourcetype,fordernum");
		QueryAdapter qa = new QueryAdapter();
		DataTable dt = qa.executeQuery(sql.toString(), new Object[]{id,id});
		int len = 0;
		if(dt!=null)
			len = dt.length();
		StringBuffer returnStr = new StringBuffer();
		returnStr.append("[");
		DataRow row;
		for(int i=0;i<len;i++)
		{
			row = dt.getDataRow(i);
			returnStr.append(",{");
			returnStr.append("\"fldId\":\""+row.getString("fid")+"\"");
			returnStr.append(",\"fldName\":\""+row.getString("fdisplayname")+"\"");
			returnStr.append(",\"displayZone\":\""+row.getString("idisplayZone")+"\"");
			returnStr.append(",\"cols\":\""+row.getString("icols")+"\"");
			returnStr.append(",\"ordernum\":\""+row.getString("iordernum")+"\"");
			returnStr.append(",\"fldposition\":\""+row.getLong("lstposition")+"\"");
			returnStr.append("}");
		}
		if(len>0)
			returnStr.deleteCharAt(1);
		returnStr.append("]");
		return returnStr.toString();
	}

	public void setDisplayModelDao(IDao<DisplayModel> displayModelDao) {
		this.displayModelDao = displayModelDao;
	}

	public void setFieldSpanInfoDao(IDao<FieldSpanInfo> fieldSpanInfoDao) {
		this.fieldSpanInfoDao = fieldSpanInfoDao;
	}

	public Map<String, String> getPhysicalCategoryMap() {
		Map<String,String> result = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select c.pid cpid,c.displayname,m.pid mpid");
		sql.append(" from bs_t_ftr_indexcategory c left outer join bs_t_ftr_displaymodel m");
		sql.append(" on c.pid = m.indextypeid");
		sql.append(" where c.isphysical = 1 and m.pid is null order by c.ordernum");
		QueryAdapter adapter = new QueryAdapter();
		DataTable tb = adapter.executeQuery(sql.toString());
		int len = 0;
		if(tb!=null)
		{
			len = tb.length();
			result = new HashMap<String,String>();
		}
		for(int i=0;i<len;i++)
		{
			DataRow row = tb.getDataRow(i);
			result.put(row.getString("cpid"), row.getString("displayname"));
		}
		return result;
	}

	public boolean saveModel(DisplayModel model, String spaninfo) {
		if(model==null || spaninfo==null)
			return false;
		try {
			if("".equals(StringUtils.checkNullString(model.getPid())))
				displayModelDao.save(model);
			else
			{
				displayModelDao.saveOrUpdate(model);
				fieldSpanInfoDao.executeUpdate("delete FieldSpanInfo where modelpid = ?", new Object[]{model.getPid()});
			}
			String modelid = model.getPid();
			List<FieldSpanInfo> lst = new ArrayList<FieldSpanInfo>();
			String[] infoArr = spaninfo.split(";");
			int len = infoArr.length;
			FieldSpanInfo fldspan;
			String[] tempArr;
			for(int i=0;i<len;i++)
			{
				tempArr = infoArr[i].split(",");
				if(tempArr.length==5)
				{
					fldspan = new FieldSpanInfo();
					fldspan.setModelpid(modelid);
					fldspan.setIndexfieldid(tempArr[0]);
					fldspan.setDisplayzone(Long.valueOf(tempArr[1]));
					fldspan.setCols(Long.valueOf(tempArr[2]));
					fldspan.setOrdernum(Long.valueOf(tempArr[3]));
					fldspan.setFieldposition(Long.valueOf(tempArr[4]));
					lst.add(fldspan);
				}
			}
			len = lst.size();
			for(int i=0;i<len;i++)
			{
				fieldSpanInfoDao.save(lst.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delModel(List<String> modelId) {
		if(modelId==null)
			return false;
		Map map = UltraSmUtil.getSqlParameter(modelId);
		try {
			String hql = "delete from FieldSpanInfo where modelpid in("+map.get("?")+")";
			fieldSpanInfoDao.executeUpdate(hql, (Object[])map.get("obj"));
			hql = "delete from DisplayModel where pid in("+map.get("?")+")";
			displayModelDao.executeUpdate(hql, (Object[])map.get("obj"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
