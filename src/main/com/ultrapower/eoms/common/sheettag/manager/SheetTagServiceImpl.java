package com.ultrapower.eoms.common.sheettag.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.tree.model.MenuDtree;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.sheettag.common.PageInfo;
import com.ultrapower.eoms.common.sheettag.model.SheetTag;
import com.ultrapower.eoms.common.sheettag.model.SheetType;
import com.ultrapower.eoms.common.sheettag.model.TagTypeLink;
import com.ultrapower.eoms.common.sheettag.model.pk.TagTypePK;
import com.ultrapower.eoms.common.sheettag.service.SheetTagService;

@Transactional
public class SheetTagServiceImpl implements SheetTagService {
	private IDao<SheetTag> sheetTagDao;
	private IDao<SheetType> sheetTypeDao;
	private IDao<TagTypeLink> tagTypeLinkDao;

	/**
	 * @param sheetTagDao
	 *            the sheetTagDao to set
	 */
	public void setSheetTagDao(IDao<SheetTag> sheetTagDao) {
		this.sheetTagDao = sheetTagDao;
	}

	/**
	 * @param sheetTypeDao
	 *            the sheetTypeDao to set
	 */
	public void setSheetTypeDao(IDao<SheetType> sheetTypeDao) {
		this.sheetTypeDao = sheetTypeDao;
	}

	/**
	 * @param tagTypeLinkDao
	 *            the tagTypeLinkDao to set
	 */
	public void setTagTypeLinkDao(IDao<TagTypeLink> tagTypeLinkDao) {
		this.tagTypeLinkDao = tagTypeLinkDao;
	}

	public List<SheetTag> listTag(PageInfo page) {
		PageLimit pageLimit = PageLimit.getInstance();
		// TODO Auto-generated method stub
		String hql = "from SheetTag t order by t.taxis";
		return sheetTagDao.pagedQuery(hql, pageLimit,null);
				
	}

	public SheetTag getTagById(String id) {
		// TODO Auto-generated method stub
		return sheetTagDao.get(id);
	}

	public void removeTagById(String id) {
		// TODO Auto-generated method stub
		// 先删除关联
		removeTagTypeLink(id);
		sheetTagDao.removeById(id);

	}

	public void saveTag(SheetTag o) {
		// TODO Auto-generated method stub
		sheetTagDao.saveOrUpdate(o);

	}

	public List<SheetType> listType(PageInfo page) {
		PageLimit pageLimit = PageLimit.getInstance();
		String hql = "from SheetType t order by t.taxis";
		return sheetTagDao.pagedQuery(hql, pageLimit, null);
	}
	
	public List<SheetType> listPageType(PageInfo page) {
		PageLimit pageLimit = PageLimit.getInstance();
		String hql = "from SheetType t order by t.taxis";
		return sheetTagDao.createQuery(hql)
				.setFirstResult(page.getFirstItemPos())
				.setMaxResults(page.getPageSize()).list();
	}

	public SheetType getTypeById(String id) {
		// TODO Auto-generated method stub
		return sheetTypeDao.get(id);
	}

	public void removeTypeById(String id) {
		// TODO Auto-generated method stub
		// 先删除关联
		String hql = "delete from TagTypeLink t where t.id.sheettypeid = ? ";
		tagTypeLinkDao.executeUpdate(hql, new Object[] { id });
		sheetTypeDao.removeById(id);

	}

	public void saveType(SheetType o) {
		// TODO Auto-generated method stub
		sheetTypeDao.saveOrUpdate(o);

	}

	public Long getTagCount() {
		// TODO Auto-generated method stub
		String hql = "select count(*) from  SheetTag ";
		return (Long) sheetTagDao.createQuery(hql.toString()).list().get(0);
	}

	public Long getTypeCount() {
		String hql = "select count(*) from  SheetType ";
		return (Long) sheetTypeDao.createQuery(hql.toString()).list().get(0);
	}

	public List<SheetType> listTypeByTag(String id) {
		String hql = "select s from SheetType s , TagTypeLink t where s.id = t.id.sheettypeid and t.id.sheettagid = ? order by to_number(s.taxis)";
		return sheetTypeDao.createQuery(hql, new Object[] { id }).list();
	}

	public void saveTagTypeLink(String id, String[] typeIds) {
		// TODO Auto-generated method stub
		// 先删除关联
		removeTagTypeLink(id);
		TagTypeLink o = null;
		TagTypePK pk = null;
		for (String typeId : typeIds) {
			if (null != typeId && !"".equals(typeId)) {
				o = new TagTypeLink();
				pk = new TagTypePK(id, typeId);
				o.setId(pk);
				tagTypeLinkDao.saveOrUpdate(o);
			}
		}
	}

	public void removeTagTypeLink(String typeId) {
		// TODO Auto-generated method stub
		String hql = "delete from TagTypeLink t where t.id.sheettagid = ? ";
		tagTypeLinkDao.executeUpdate(hql, new Object[] { typeId });
	}

	public List<SheetTag> listAllTag() {
		// TODO Auto-generated method stub
		return sheetTagDao.getAll();
	}

	public List<SheetTag> getListAllTagByLevel(int taglevel) {
		// TODO Auto-generated method stub
		String hql = "from SheetTag where taglevel = ?";
		return sheetTagDao.find(hql,taglevel);
	}

	public List<SheetTag> getListAllTagByParentId(String parentid) {
		String hql = "from SheetTag where parentid = ?";
		return sheetTagDao.find(hql,parentid);
	}
	
	public List<SheetTag> getListTagBySheetTypeId(String baseschema) {
		// TODO Auto-generated method stub
		String hql = "from SheetTag st where st.id in (select ttl.id.sheettagid from TagTypeLink ttl,SheetType sty where ttl.id.sheettypeid = sty.id and  sty.baseschema = ?)";
		return sheetTagDao.find(hql,baseschema);
	}
	
	public SheetType getSheetTypeByBaseSchema(String baseschema) {
		SheetType sheetType = null;
		String hql = "from SheetType t where t.baseschema = ? order by id";
		List<SheetType> sheetTypeList = sheetTagDao.find(hql, baseschema);
		if(sheetTypeList != null && !sheetTypeList.isEmpty()){
			sheetType = sheetTypeList.get(0);
		}
		return sheetType;
	}
	
	public void removeTagTypeLinkBySheetTypeId(String sheetTypeId) {
		// TODO Auto-generated method stub
		String hql = "delete from TagTypeLink t where t.id.sheettypeid = ? ";
		tagTypeLinkDao.executeUpdate(hql, new Object[] { sheetTypeId });
	}
	
	public List<SheetTag> listNavigationTag(String partSQL, String upepSQL) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct t from SheetTag t,TagTypeLink l,SheetType st");
		hql.append(" where t.id=l.id.sheettagid and l.id.sheettypeid=st.id");
		hql.append(" and (");
		hql.append("( (st.bstype='1' or st.bstype='0') and ").append(partSQL).append(" )");
		hql.append(" or (st.bstype='0' and st.baseschema is null and st.url not like '/ultrabpp/view.action?mode=NEW&baseSchema=UPEP_REQ%' ) ");
		hql.append(" or ").append(upepSQL);
		hql.append(" )");
		hql.append(" order by to_number(t.taxis)");
		return sheetTagDao.find(hql.toString());
	}
	
	public List<SheetTag> listServiceTag(String partSQL,String stbSQL) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct t from SheetTag t,TagTypeLink l,SheetType st");
		hql.append(" where t.id=l.id.sheettagid and l.id.sheettypeid=st.id");
		hql.append(" and ((st.bstype='1' and st.sheettype='1' and ").append(partSQL);
		hql.append(") or (st.bstype='0' and st.baseschema is null and st.url not like '/ultrabpp/view.action?mode=NEW&baseSchema=UPEP_REQ%') ");
		hql.append(" or ").append(stbSQL);
		hql.append(") and st.sheettype='1'");
		hql.append(" order by to_number(t.taxis)");
		return sheetTagDao.find(hql.toString());
	}
	
	/**
	 * 验证工单标签名称
	 * @param editTagName
	 * @return
	 */
	public String editvalidate(String editTagName){
		String hql = "from SheetTag t where t.name = ? ";
		 List<SheetTag> listValue = sheetTagDao.find(hql,editTagName);
		if(listValue.isEmpty()){
			return "false";
		}
		    return "true";
	}
	
	
	/**
	 * 保存归属关系
	 * @param tagId
	 * @param typeId
	 */
	public void saveTagTypeLink(String tagId, String typeId) {
		TagTypeLink o = null;
		TagTypePK pk = null;
		if (null != typeId && !"".equals(typeId)) {
			o = new TagTypeLink();
			pk = new TagTypePK(tagId, typeId);
			o.setId(pk);
			tagTypeLinkDao.saveOrUpdate(o);
		}
	}

}
