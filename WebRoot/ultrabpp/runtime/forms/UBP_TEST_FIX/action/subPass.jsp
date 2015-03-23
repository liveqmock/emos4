<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="account" label="经费管理人" row="1" cell="4" single="0" action="ASSIGN" stepno="step3" selectType="2"  actionName="subPass" next="1" visiable="1" />
<ubf:AssignTreeField name="reCheckPerson" label="复核人" row="1" cell="4" single="1"  stepno="step3" selectType="2"  actionName="subPass" next="0" visiable="1" />
<ubf:CharacterField name="approvalComment" label="审批意见" row="2" cell="4" visiable="1" length="1000" />
