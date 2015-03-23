package com.ultrapower.eoms.ultrabpp.cache.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;

/**
 * 
 * @author fying 
 * @version 
 * @since  
 * @see TemplateDoc
 */
public interface ThemeCacheService
{
    /**
	 * 表单样式 表单名称获取表单对象
	 */
	public ThemeModel getThemeModel(String name);
	public void initThemeConfig();
	public Map<String, ThemeModel> getThemeMap();
	public List<ThemeModel> getThemeList();
}
