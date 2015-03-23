<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:HiddenField name="deal_group" label="处理组ID" cell="1" length="2000" />
<ubf:CharacterField name="view_deal_group" label="处理组" row="1" cell="1" visiable="1" length="128" />
<ubf:SelectField name="view_deal_person" label="处理人" type="collect" resource=" : " paras="" cell="1" visiable="1"  />
<ubf:CharacterField name="deal_person" label="处理人" row="1" cell="1" visiable="1" length="128" />
<ubf:CharacterField name="Description" label="处理意见" row="3" cell="4" visiable="1" length="2000" />
<ubf:CollectField name="notify" label="通知" type="collect" showtype="checkbox" resource="短信:短信,邮件:邮件" paras="" row="1" cell="4" visiable="1" defaultValue="短信" />
