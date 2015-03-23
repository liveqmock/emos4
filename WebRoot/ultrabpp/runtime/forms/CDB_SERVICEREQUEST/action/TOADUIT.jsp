<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:CharacterField name="dealInfo" label="处理说明" row="5" cell="4" visiable="1" length="1000" />
<ubf:AssignTreeField name="dealPerson" label="处理组(人)" row="1" cell="1" single="0"  stepno="step01" selectType="2"  actionName="TOADUIT" next="1" visiable="1" />
