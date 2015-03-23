package com.ultrapower.eoms.common.plugin.ecside.easydataaccess;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EasyDataAccessUtil {

	private static Log logger = LogFactory.getLog(EasyDataAccessUtil.class);
	
	
	public static String parseEasySql(String easySql){
		String newSql=StringUtils.trim(easySql);
		newSql=newSql.replaceAll(escRex(EasyDataAccessConstants.C_LESS), EasyDataAccessConstants.LESS_S);
		newSql=newSql.replaceAll(escRex(EasyDataAccessConstants.C_GREAT), EasyDataAccessConstants.GREAT_S);
		newSql=newSql.replaceAll(escRex(EasyDataAccessConstants.C_NOTEQUAL), EasyDataAccessConstants.NOTEQUAL_S);
		return newSql;
	}
	
	public static String escRex(String in) {
		return in.replaceAll("\\{", "\\\\{").replaceAll("\\}", "\\\\}");
	}
	
	public static List createSqlSnippets(String inSql ){
		
		if (inSql==null || inSql.trim().length()<1 ){
			return null; 
		}
		List sqlSnippetList=new ArrayList();
		Pattern pattern = Pattern.compile(EasyDataAccessConstants.REX_SQL);
		Matcher matcher = pattern.matcher(inSql);
		while (matcher.find()){
			SqlSnippet sqlSnippet=new SqlSnippet();
			sqlSnippet.start=matcher.start(0);
			sqlSnippet.end=inSql.indexOf(EasyDataAccessConstants.END_TAG,sqlSnippet.start)+EasyDataAccessConstants.END_TAG.length();
			sqlSnippet.condition=matcher.group(1).trim();
			sqlSnippet.snippet=inSql.substring(sqlSnippet.start, sqlSnippet.end).trim();
			sqlSnippet.content=sqlSnippet.snippet.substring(matcher.group(0).length(),sqlSnippet.snippet.length()-EasyDataAccessConstants.END_TAG.length()).trim();
			sqlSnippet.init();
			sqlSnippetList.add(sqlSnippet);
		}
		return sqlSnippetList;
	}

	
	public static PreparedStatement createPreparedStatement(Connection conn,String inSql,Map map) throws SQLException{
		List sqlSnippetList=createSqlSnippets(inSql);
		return createPreparedStatement(conn, inSql, sqlSnippetList, map);
	}
	
	public static PreparedStatement createPreparedStatement(Connection conn,String inSql,List sqlSnippetList,Map map) throws SQLException{
		List paraList=new ArrayList();
		return createPreparedStatement(conn, inSql, sqlSnippetList, map,paraList);
	}
	
	public static PreparedStatement createPreparedStatement(Connection conn,String inSql,List sqlSnippetList,Map map,List paraList) throws SQLException{

		String nsql=getDBSql(inSql,map,paraList,sqlSnippetList);
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(nsql);
			for (int i=0;i<paraList.size();i++){
				SqlParameter sqlParameter=(SqlParameter)paraList.get(i);
				pstmt.setString(sqlParameter.getIndex(), sqlParameter.getValueAsString());
			}
		} catch (SQLException e) {
//			LogHandler.errorLog(logger, e);
			if (pstmt!=null) pstmt.close();
			throw e;
		}
		return pstmt;
	}
	
	
	public static PreparedStatement createPreparedCallStatement(Connection conn,String inSql,Map map) throws SQLException{
		List sqlSnippetList=createSqlSnippets(inSql);
		return createPreparedCallStatement(conn, inSql, sqlSnippetList, map);
	}
	public static CallableStatement createPreparedCallStatement(Connection conn,String inSql,List sqlSnippetList,Map map) throws SQLException{
		List paraList=new ArrayList();
		return createPreparedCallStatement(conn, inSql, sqlSnippetList, map, paraList);
	}
	
	public static CallableStatement createPreparedCallStatement(Connection conn,String inSql,List sqlSnippetList,Map map,List paraList) throws SQLException{
		
		String nsql=getDBSql(inSql,map,paraList,sqlSnippetList);
		CallableStatement cstmt = null;
		try {
			cstmt = conn.prepareCall(nsql);
			for (int i=0;i<paraList.size();i++){
				SqlParameter sqlParameter=(SqlParameter)paraList.get(i);
				if (EasyDataAccessConstants.OUT_TYPE.equals(sqlParameter.getType())){
					cstmt.registerOutParameter(sqlParameter.getIndex(), java.sql.Types.VARCHAR);
				}else{
					cstmt.setString(sqlParameter.getIndex(), sqlParameter.getValueAsString());
				}
			}

		} catch (SQLException e) {
//			LogHandler.errorLog(logger, e);
			if (cstmt!=null) cstmt.close();
			throw e;
		}
		return cstmt;
	}
	
	
	public static String getDBSql(String inSql,Map map){
		List sqlSnippetList=createSqlSnippets(inSql);
		return getDBSql(inSql,  map, new ArrayList(),sqlSnippetList);
	}
	
	public static String getDBSql(String inSql,Map map,List paraList){
		List sqlSnippetList=createSqlSnippets(inSql);
		return getDBSql(inSql,  map, paraList,sqlSnippetList);
	}

	public static String getDBSql(String inSql,Map map,List paraList,List sqlSnippetList ){
		StringBuffer sql=new StringBuffer();
		int start=0;
		int last=0;
		if (map==null) map=new HashMap();
		int i=0;
		for(i=0;i<sqlSnippetList.size();i++){
			SqlSnippet sqlSnippet=(SqlSnippet)sqlSnippetList.get(i);
			start=last;
			last=sqlSnippet.start;
			String osql=inSql.substring(start,last);
			sql.append(osql);
			if (sqlSnippet.check(map)){
				sql.append(sqlSnippet.content);
			}
			last=sqlSnippet.end;
		}
		if (last>0 && last<inSql.length()){
			sql.append(inSql.substring(last));
		}
		if (i==0){
			sql.append(inSql);
		}
		
		Pattern pattern = Pattern.compile(EasyDataAccessConstants.REX_PARAMETER);
		Matcher matcher = pattern.matcher(sql);
		String outSql=sql.toString();
		int index=0;
		while (matcher.find()){
			index++;
			String pPara=matcher.group(0);
			String pName=matcher.group(1);
			String pType=EasyDataAccessConstants.IN_TYPE;
			if (pName.startsWith(EasyDataAccessConstants.C_SQL_OUT_PARAMETER)){
				pName=pName.substring(4);
				pType=EasyDataAccessConstants.OUT_TYPE;
				//pPara="#{"+pPara.substring(5);
			}
			String pValue=(String)map.get(pName);
			SqlParameter sqlPara=new SqlParameter(pName,pValue,pType);
			sqlPara.setIndex(index);
			paraList.add(sqlPara);
			outSql=StringUtils.replace(outSql, pPara, "?");
		}
		logger.debug(" ===== "+EasyDataAccessUtil.class.getName()+" ===== \n"+inSql+"\n"+outSql);
		return outSql;
	}
	


	public static boolean isEquals(long a,long b){
		return a==b;		
	}

	public static boolean isNotEquals(long a,long b){
		return a!=b;		
	}

	public static boolean isLessThen(long a,long b){
		return a<b;		
	}

	public static boolean isGreatThen(long a,long b){
		return a>b;		
	}

	public static boolean isLessThenE(long a,long b){
		return a<=b;		
	}

	public static boolean isGreatThenE(long a,long b){
		return a>=b;		
	}

	public static boolean isEmpty(String a){
		return a==null||a.length()<1;		
	}
	public static boolean isNotEmpty(String a){
		return !isEmpty(a);	
	}
	
	public static boolean isEquals(String a,String b){
		if (b==null){
			return a==null;
		}
		return b.equals(a);
	}

	public static boolean isNotEquals(String a,String b){
		if (b==null){
			return a!=null;
		}
		return !b.equals(a);		
	}

	public static boolean isLessThen(String a,String b){
		return a!=null&&a.compareTo(b)<0;	
	}

	public static boolean isGreatThen(String a,String b){
		return a!=null&&a.compareTo(b)>0;	
	}

	public static boolean isLessThenE(String a,String b){
		return a!=null&&(a.compareTo(b)<0||a.equals(b));		
	}

	public static boolean isGreatThenE(String a,String b){
		return a!=null&&(a.compareTo(b)>0||a.equals(b));		
	}

	
	
	
//	public static void bulidSqlSnippetList(String inSql,List sqlSnippetList ){
//		if (inSql==null || inSql.trim().length()<1 || sqlSnippetList==null ){
//			return;
//		}
//		
//		inSql=parseEasySql(inSql);
//
//
//		
//		Pattern pattern = Pattern.compile(EasyDataAccessConstants.REX_SQL);
//		Matcher matcher = pattern.matcher(inSql);
//		while (matcher.find()){
//			SqlSnippet sqlSnippet=new SqlSnippet();
//			sqlSnippet.start=matcher.start(0);
//			sqlSnippet.end=inSql.indexOf(EasyDataAccessConstants.END_TAG,sqlSnippet.start)+EasyDataAccessConstants.END_TAG.length();
//			sqlSnippet.condition=matcher.group(1).trim();
//			sqlSnippet.snippet=inSql.substring(sqlSnippet.start, sqlSnippet.end).trim();
//			sqlSnippet.content=sqlSnippet.snippet.substring(matcher.group(0).length(),sqlSnippet.snippet.length()-EasyDataAccessConstants.END_TAG.length()).trim();
//			sqlSnippet.init();
//			sqlSnippetList.add(sqlSnippet);
//		}
//	}



	
}
