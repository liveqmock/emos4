<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="DistributePerson" label="派发人" row="1" cell="1" single="1"  stepno="step03" selectType="2"  actionName="RenextReview" next="1" visiable="1" />
<ubf:CollectField name="notify" label="通知" type="collect" showtype="checkbox" resource="短信:短信,邮件:邮件" paras="" row="1" cell="4" visiable="1" defaultValue="短信" />
<ubf:CharacterField name="Description" label="处理意见" row="3" cell="4" visiable="1" length="2000" />
