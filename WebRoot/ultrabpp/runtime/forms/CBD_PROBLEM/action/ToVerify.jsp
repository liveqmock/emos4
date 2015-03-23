<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="CreatorFullName" label="建单人" row="1" cell="1" single="1"  stepno="step02" selectType="2"  actionName="ToVerify" next="1" visiable="1" />
<ubf:CharacterField name="ProSuggest" label="处理意见" row="3" cell="4" visiable="1" length="4000" />
<ubf:CollectField name="notify" label="通知" type="collect" showtype="checkbox" resource="短信:短信,邮件:邮件" paras="" row="1" cell="4" visiable="1" defaultValue="短信" />
