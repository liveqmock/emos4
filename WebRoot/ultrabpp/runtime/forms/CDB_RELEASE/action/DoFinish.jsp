<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:CharacterField name="view_dealGroup" label="处理组" row="3" cell="4" visiable="1" length="4000" />
<ubf:CharacterField name="dealGroup" label="处理组ID" row="1" cell="1" visiable="1" length="100" />
<ubf:SelectField name="view_dealUser" label="处理人" type="collect" resource=" " paras="" cell="1" visiable="1"  />
<ubf:CharacterField name="dealUser" label="处理人ID" row="1" cell="1" visiable="1" length="100" />
<ubf:CharacterField name="dealDesc" label="处理意见" row="3" cell="4" visiable="1" length="4000" />
<ubf:CollectField name="notify" label="通知" type="collect" showtype="checkbox" resource="短信:短信,邮件:邮件" paras="" row="1" cell="4" visiable="1" defaultValue="短信,邮件" />
