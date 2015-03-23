package com.ultrapower.eoms.ultrabpp.cache.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.cache.service.ThemeCacheService;
import com.ultrapower.workflow.utils.WfEngineUtils;

/**
 * 
 * @author fying 
 * @version 
 * @since  
 * @see TemplateDoc
 */
public class ThemeCacheXmlImpl implements ThemeCacheService
{
    private final String THEME_CACHEKEY = "bppThemeInfoMap";
    public void initThemeConfig()
    {
		Map<String, ThemeModel> map = new HashMap<String, ThemeModel>(); // = (Map<String, ThemeModel>) BaseCacheManager.get(THEME_CACHEKEY, THEME_CACHEKEY);
		if (map == null)
		{
		    map = new HashMap<String, ThemeModel>();
		}
	
		String path = "/wfengine/configs/formtheme-config.xml";
		SAXReader reader = new SAXReader();
		Document doc;
		try
		{
		    doc = reader.read(WfEngineUtils.class.getResourceAsStream(path));
	
		    List nodes = doc.selectNodes("//themes/theme");
		    if (CollectionUtils.isNotEmpty(nodes))
		    {
				for (int i = 0; i < nodes.size(); i++)
				{
				    	ThemeModel themeModel = new ThemeModel();
				    	Node node = (Node) nodes.get(i);
				    	String name = node.valueOf("@name");
					String type = node.valueOf("@type");
					String forder = node.valueOf("@forder");
					String cell = node.valueOf("@cell");
					String labelWidth=node.valueOf("@labelWidth");
					String mainWidth=node.valueOf("@mainWidth");
					themeModel.setName(name);
					themeModel.setType(type);
					themeModel.setFolder(forder);
					themeModel.setCell(Integer.parseInt(cell));
					themeModel.setLabelWidth(Integer.parseInt(labelWidth));
					themeModel.setMainWidth(Integer.parseInt(mainWidth));
					map.put(name, themeModel);
				}
		    }
		} catch (DocumentException e)
		{
		    e.printStackTrace();
		}
		BaseCacheManager.put(THEME_CACHEKEY, THEME_CACHEKEY, map);
    }
    
    public ThemeModel getThemeModel(String name){
		Map<String, ThemeModel> map = (Map<String, ThemeModel>) BaseCacheManager.get(THEME_CACHEKEY, THEME_CACHEKEY);
		if(map!=null&&map.size()>0){
		    return map.get(name);
		}
		return null;
    }
    
    public Map<String, ThemeModel> getThemeMap()
    {
    	Map<String, ThemeModel> map = (Map<String, ThemeModel>) BaseCacheManager.get(THEME_CACHEKEY, THEME_CACHEKEY);
    	return map;
    }
    
    public List<ThemeModel> getThemeList()
    {
    	Map<String, ThemeModel> map = (Map<String, ThemeModel>) BaseCacheManager.get(THEME_CACHEKEY, THEME_CACHEKEY);
    	if(map == null)
    	{
    		map = new HashMap<String, ThemeModel>();
    	}
    	
    	List<ThemeModel> themeList = new ArrayList<ThemeModel>();
    	
    	for(ThemeModel model : map.values())
    	{
    		themeList.add(model);
    	}
    	
    	return themeList;
    }
}
