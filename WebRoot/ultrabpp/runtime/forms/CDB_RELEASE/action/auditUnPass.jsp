<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:SelectField name="step_sug" label="审批意见" type="collect" resource="修订方案:修订方案,否决:否决" paras="" cell="1" visiable="1"  />
<ubf:SelectField name="step_cmdbupload" label="通知CMDB更新" type="collect" resource="是:是,否:否" paras="" cell="1" visiable="1"  />
<ubf:SelectField name="step_baseresult" label="基本结论" type="collect" resource="成功:成功,部分成功:部分成功,失败:失败,取消:取消" paras="" cell="1" visiable="1" defaultValue="取消" />
<ubf:CharacterField name="dealDesc" label="处理意见" row="3" cell="4" visiable="1" length="4000" />
