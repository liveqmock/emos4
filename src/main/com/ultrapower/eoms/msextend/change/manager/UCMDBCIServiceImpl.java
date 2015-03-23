package com.ultrapower.eoms.msextend.change.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hp.ucmdb.api.UcmdbService;
import com.hp.ucmdb.api.classmodel.Attribute;
import com.hp.ucmdb.api.classmodel.ClassDefinition;
import com.hp.ucmdb.api.classmodel.ClassModelService;
import com.hp.ucmdb.api.topology.QueryDefinition;
import com.hp.ucmdb.api.topology.QueryNode;
import com.hp.ucmdb.api.topology.Topology;
import com.hp.ucmdb.api.topology.TopologyQueryFactory;
import com.hp.ucmdb.api.topology.TopologyQueryService;
import com.hp.ucmdb.api.types.TopologyCI;
import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.msextend.change.model.CIClass;
import com.ultrapower.eoms.msextend.change.model.CIRelevance;
import com.ultrapower.eoms.msextend.change.service.UCMDBCIService;
@Deprecated
/**
 * 改为使用WebService与UCMDB交互
 */
public class UCMDBCIServiceImpl implements UCMDBCIService{
	
	public UCMDBCIServiceImpl(){
		
	}
	
	@Override
	public Map<String,String> getAttributesByTypeName(String typeName) {
		UcmdbService ucmdbService;
		try{
			ucmdbService = CreateUCMDBConnection.createSDKConnection();
		}catch (Exception e) {
			throw new RuntimeException("UcmdbService 创建失败",e);
		}
		ClassModelService classModel = ucmdbService.getClassModelService();
		ClassDefinition definition = classModel.getClassDefinition(typeName);
		Map<String,Attribute> attributes = definition.getAllAttributes();
		Map<String,String> attributesMap = new HashMap<String,String>();
		for(String attributeName : attributes.keySet()){
			attributesMap.put(attributeName, attributes.get(attributeName).getDisplayLabel());
		}
		return attributesMap;
	}

	@Override
	public Map<String, String> getAttributeMapByCIGID(String typeName,String id) {
		UcmdbService ucmdbService;
		TopologyQueryFactory queryFactory;
		TopologyQueryService queryService;
		try{
			ucmdbService = CreateUCMDBConnection.createSDKConnection();
			queryService = ucmdbService.getTopologyQueryService();
			queryFactory = queryService.getFactory();
		}catch (Exception e) {
			throw new RuntimeException("UcmdbService 创建失败",e);
		}
		Map<String,String> attributeMap = new HashMap<String, String>();
		String cisNodeName = "nodes";
	    QueryDefinition queryDefinition = queryFactory.createQueryDefinition("query ci by id");
		QueryNode queryNode = queryDefinition.addNode(cisNodeName).ofType(typeName);
		queryNode.property("global_id").isEqualTo(id);
		Map<String,String> attributes = getAttributesByTypeName(typeName);
		queryNode.queryProperties(attributes.keySet().toArray(new String[attributes.size()]));
		
		Topology topology = queryService.executeQuery(queryDefinition);
		Collection<TopologyCI> cis = topology.getCIsByName(cisNodeName);
		TopologyCI ci = null;
		if(cis.size()>0){
			ci = cis.iterator().next();
			for(String attName : attributes.keySet()){
				attributeMap.put(attributes.get(attName), StringUtils.checkNullString(ci.getPropertyValue(attName)));
			}
		}
		
		return attributeMap;
	}

	@Override
	public List<CIRelevance> getCIByTypeAndName(String cit, String name) {
		UcmdbService ucmdbService;
		TopologyQueryFactory queryFactory;
		TopologyQueryService queryService;
		try{
			ucmdbService = CreateUCMDBConnection.createSDKConnection();
			queryService = ucmdbService.getTopologyQueryService();
			queryFactory = queryService.getFactory();
		}catch (Exception e) {
			throw new RuntimeException("UcmdbService 创建失败",e);
		}
		List<CIRelevance> listCis = new ArrayList<CIRelevance>();
		String cisNodeName = "nodes";
	    QueryDefinition queryDefinition = queryFactory.createQueryDefinition("Get CI by type and name");
	    //Define the CIT name you want to Search, default include sub cit.
		QueryNode queryNode = queryDefinition.addNode(cisNodeName).ofType(cit);
		queryNode.property("name").like("%"+name+'%');
		//Define the CIT Attribute you want to Search, default search all. 
		queryNode.queryProperties("display_label","name","root_class","global_id");
		Topology topology = queryService.executeQuery(queryDefinition);

	    Collection<TopologyCI> cis = topology.getCIsByName(cisNodeName);
	    
	    for (TopologyCI tci : cis) {
	    	CIRelevance ci = new CIRelevance();
	    	ci.setCiId(tci.getPropertyValue("global_id").toString());
	    	ci.setDisplayLabel(tci.getPropertyValue("display_label").toString());
//	    	ci.setClassType(tci.getPropertyValue("root_class").toString());
	    	ci.setCiClass((CIClass) BaseCacheManager.get(Constants.CICLASSESCACHE,tci.getPropertyValue("root_class")));
	    	ci.setName(tci.getPropertyValue("name").toString());
	    	listCis.add(ci);
	    }
		return listCis;
	}

	@Override
	public List<CIClass> getAllClasses() {
		UcmdbService ucmdbService;
		try{
			ucmdbService = CreateUCMDBConnection.createSDKConnection();
		}catch (Exception e) {
			throw new RuntimeException("UcmdbService 创建失败",e);
		}
		ClassModelService classModelService = ucmdbService.getClassModelService();
		Collection<ClassDefinition> classDefinitions = classModelService.getAllClasses();
		System.out.println(classDefinitions.size());
		for(ClassDefinition classDefinition : classDefinitions){
			ClassDefinition parentClassDefinition = classDefinition.getParentClass();
			
			System.out.print(classDefinition.getName()+"\t"+classDefinition.getDisplayName());
			if(parentClassDefinition!=null){
				System.out.print("\t"+parentClassDefinition.getName()+"\t"+parentClassDefinition.getDisplayName());
			}
			System.out.println();
		}
		return null;
	}
	
}
