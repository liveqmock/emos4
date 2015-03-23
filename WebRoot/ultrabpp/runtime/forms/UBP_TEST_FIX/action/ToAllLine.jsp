<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:AssignTreeField name="reCheckPerson" label="复核人" row="1" cell="4" single="1"  stepno="step03" selectType="2"  actionName="ToAllLine" next="0" visiable="1" />
<ubf:AssignTreeField name="APerson" label="处理组A成员" row="1" cell="1" single="1"  stepno="step03" selectType="2"  actionName="ToAllLine" next="1" visiable="1" />
<ubf:AssignTreeField name="BPerson" label="处理组B成员" row="1" cell="1" single="0" action="ASSIGN" stepno="step03" selectType="2"  actionName="ToAllLine" next="1" visiable="1" />
<ubf:AssignTreeField name="CPerson" label="处理组C成员" row="1" cell="1" single="0" action="ASSIGN" stepno="step03" selectType="2"  actionName="ToAllLine" next="1" visiable="1" />
