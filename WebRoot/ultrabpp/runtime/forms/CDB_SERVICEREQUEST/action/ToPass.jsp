<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:CollectField name="is_audit_level_up" label="是否审批升级" type="collect" showtype="checkbox" resource="是:是" paras="" row="1" cell="1" visiable="1"  />
<ubf:HiddenField name="deal_group" label="处理组ID" cell="1" length="2000" />
<ubf:CharacterField name="view_deal_group" label="处理组" row="1" cell="1" visiable="1" length="100" />
<ubf:SelectField name="deal_person" label="处理人" type="collect" resource="清空:" paras="" cell="1" visiable="1"  />
<ubf:SelectField name="view_deal_person" label="处理人" type="collect" resource=" : " paras="" cell="1" visiable="1"  />
<ubf:AssignTreeField name="dealPerson" label="处理人" row="1" cell="1" single="1"  stepno="step05" selectType="2"  actionName="ToPass" next="1" visiable="1" />
<ubf:CharacterField name="dealInfo" label="处理说明" row="5" cell="4" visiable="1" length="1000" />
<ubf:CollectField name="notify" label="通知" type="collect" showtype="checkbox" resource="短信:短信,邮件:邮件" paras="" row="1" cell="1" visiable="1" defaultValue="短信" />
