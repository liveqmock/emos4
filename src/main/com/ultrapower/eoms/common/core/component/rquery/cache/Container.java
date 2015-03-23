package com.ultrapower.eoms.common.core.component.rquery.cache;

import java.io.Serializable;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;

/**
 * 用于存储查询过程中的对象，存于缓存中 利于导出excel时取sql或数据
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-7-30
 */
public class Container implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4992278700795202774L;

	private SqlResult sqlResult;//sql对象
	private DataTable dataTabe;//数据结果集对象
	public SqlResult getSqlResult() {
		return sqlResult;
	}
	public void setSqlResult(SqlResult sqlResult) {
		this.sqlResult = sqlResult;
	}
	public DataTable getDataTabe() {
		return dataTabe;
	}
	public void setDataTabe(DataTable dataTabe) {
		this.dataTabe = dataTabe;
	}
	
	
}
