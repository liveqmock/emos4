package com.ultrapower.eoms.ultrabpp.cache.manager;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.ultrabpp.cache.model.ExtendFunctionConfigModel;
import com.ultrapower.eoms.ultrabpp.cache.service.ExtendFunctionCacheService;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;

public class ExtendFunctionCacheXmlImpl implements ExtendFunctionCacheService
{
	private Logger log = LoggerFactory.getLogger(ExtendFunctionCacheXmlImpl.class);
	private static final String CACHE_NAME = "bppExtendFunc";
	
	private Map<String, Class<ExtendFunction>> formServerFuncCache = new HashMap<String, Class<ExtendFunction>>();

	public String getRelateType(String baseSchema) {
		String relateType = null;
		ExtendFunctionConfigModel configModel = (ExtendFunctionConfigModel)BaseCacheManager.get(CACHE_NAME, baseSchema);
		if(configModel != null) {
			relateType = configModel.getRelateType();
		}
		return relateType;
	}
	
	public List<WfType> getRelateSchemas(String baseSchema) {
		List<WfType> relateSchemas = null;
		ExtendFunctionConfigModel configModel = (ExtendFunctionConfigModel)BaseCacheManager.get(CACHE_NAME, baseSchema);
		if(configModel != null) {
			relateSchemas = configModel.getRelateSchemas();
		}
		return relateSchemas;
	}
	
	public List<String> getClientFuncList(String baseSchema)
	{
		ExtendFunctionConfigModel configModel = (ExtendFunctionConfigModel)BaseCacheManager.get(CACHE_NAME, baseSchema);
		if(configModel != null)
		{
			return configModel.getClientFunctions();
		}
		else
		{
			return new ArrayList<String>();
		}
	}

	public List<ExtendFunction> getFormCommitFuncList(String baseSchema)
	{
		List<ExtendFunction> efList = new ArrayList<ExtendFunction>();
		ExtendFunctionConfigModel configModel = (ExtendFunctionConfigModel)BaseCacheManager.get(CACHE_NAME, baseSchema);
		if(configModel != null)
		{
			for(String funcStr : configModel.getCommitFunctions())
			{
				try
				{
					ExtendFunction func = formServerFuncCache.get(funcStr).newInstance();
					efList.add(func);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return efList;
	}
	public List<String> getAttributeList(String baseSchema)
	{
		ExtendFunctionConfigModel configModel = (ExtendFunctionConfigModel)BaseCacheManager.get(CACHE_NAME, baseSchema);
		if(configModel != null)
		{
			return configModel.getAttrbutes();
		}
		else
		{
			return new ArrayList<String>();
		}
	}

	public List<ExtendFunction> getFormOpenFuncList(String baseSchema)
	{
		List<ExtendFunction> efList = new ArrayList<ExtendFunction>();
		ExtendFunctionConfigModel configModel = (ExtendFunctionConfigModel)BaseCacheManager.get(CACHE_NAME, baseSchema);
		if(configModel != null)
		{
			for(String funcStr : configModel.getOpenFunctions())
			{
				try
				{
					ExtendFunction func = formServerFuncCache.get(funcStr).newInstance();
					efList.add(func);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return efList;
	}

	public void reflush(String baseSchema)
	{		
		String path = "/wfengine/configs/workflow-extend.xml";
		reflushCache(path,baseSchema);
		//fanying 2013-6-20 增加文件夹，存放不同工单的客户化配置文件，便于开发人员维护和更新
		String extendFolder = "/wfengine/configs/schemaextends";
		File fileFolder=new File(ExtendFunctionCacheXmlImpl.class.getResource("/").getPath() + extendFolder); 
		//如果文件夹不存在，则不进行遍历加载
		if(fileFolder.exists()){
			File[] files = fileFolder.listFiles();
			for(File file:files){
				String fileName = file.getName();
				if(fileName.lastIndexOf(".xml")!=-1){
					reflushCache(extendFolder+"/"+fileName,baseSchema);
				}
			}
		}
	}
	public void reflushCache(String path,String baseSchema)
	{
		log.info("开始加载客户化配置文件。path=" + path+" schema="+baseSchema); 
		SAXReader reader = new SAXReader();
		Document doc;
		try
		{
			doc = reader.read(ExtendFunctionCacheXmlImpl.class.getResourceAsStream(path));

			List<Node> nodes = null;
			if(baseSchema == null)
			{
				nodes = doc.selectNodes("/workflow-configs/schema");
			}
			else
			{
				nodes = doc.selectNodes("/workflow-configs/schema[id='" + baseSchema + "']");
			}
			
			if (CollectionUtils.isNotEmpty(nodes))
			{
				for(Node node : nodes)
				{
					ExtendFunctionConfigModel configModel = new ExtendFunctionConfigModel();
					String schema = node.valueOf("@id");
					configModel.setBaseSchema(schema);
					List<Node> extOpenList = node.selectNodes("extendopts-open/operate");
					if(CollectionUtils.isNotEmpty(extOpenList))
					{
						for (Node extOpenFunc : extOpenList)
						{
							String extClassName = extOpenFunc.valueOf("@src");
							configModel.getOpenFunctions().add(extClassName);
							setFuncClassCache(extClassName);
						}
					}
					List<Node> extCommitList = node.selectNodes("extendopts-commit/operate");
					if(CollectionUtils.isNotEmpty(extCommitList))
					{
						for (Node extCommitFunc : extCommitList)
						{
							String extClassName = extCommitFunc.valueOf("@src");
							configModel.getCommitFunctions().add(extClassName);
							setFuncClassCache(extClassName);
						}
					}
					List<Node> extClientList = node.selectNodes("extendopts-client/operate");
					if(CollectionUtils.isNotEmpty(extClientList))
					{
						for (Node extClientFunc : extClientList)
						{
							String extClassName = extClientFunc.valueOf("@src");
							configModel.getClientFunctions().add(extClassName);
						}
					}
					List<Node> extAttrList = node.selectNodes("entryattributes/attr");
					if(CollectionUtils.isNotEmpty(extAttrList))
					{
						for (Node extAttrFunc : extAttrList)
						{
							String attrName = extAttrFunc.valueOf("@name");
							configModel.getAttrbutes().add(attrName);
						}
					}
					
					List<Node> relateSchemas = node.selectNodes("relation-schema");
					if (CollectionUtils.isNotEmpty(relateSchemas)) {
						Node relateSchemasNode = relateSchemas.get(0);
						String relateType = relateSchemasNode.valueOf("@relatype");
						configModel.setRelateType(relateType);
					}
					List<String> tmpRelate = new ArrayList<String>();
					List<Node> includeSchema = node.selectNodes("relation-schema/include");
					if(CollectionUtils.isNotEmpty(includeSchema))
					{
						for (Node relateSchema : includeSchema)
						{
							String attrName = relateSchema.valueOf("@schema");
							tmpRelate.add(attrName);
						}
					}
					if (CollectionUtils.isNotEmpty(tmpRelate)) {
						for (int i = 0; i < tmpRelate.size(); i++) {
							String s = tmpRelate.get(i);
							 IWfSortManager sortService = WorkFlowServiceClient.clientInstance().getSortService();
							 try {
								WfType wfType = sortService.getWfTypeByCode(s);
								if (wfType != null) {
									configModel.getRelateSchemas().add(wfType);
								}
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
					}
					BaseCacheManager.put(CACHE_NAME, schema, configModel);
				}
			}
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}
	private void setFuncClassCache(String extClassName)
	{
		if(!formServerFuncCache.containsKey(extClassName))
		{
			try
			{
				Class<ExtendFunction> c = (Class<ExtendFunction>)Class.forName(extClassName);
				formServerFuncCache.put(extClassName, c);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}

}
