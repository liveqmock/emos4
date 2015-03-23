<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:SelectField name="close_code" label="基本结论" type="collect" resource="1:已解决,2:临时解决,3:审批不通过,4:处理取消" paras="" cell="1" visiable="1"  />
<ubf:SelectField name="satisfaction" label="满意度" type="collect" resource="非常满意:非常满意,不满意:不满意" paras="" cell="1" visiable="1"  />
<ubf:CharacterField name="dealInfo" label="处理说明" row="5" cell="4" visiable="1" length="1000" />
