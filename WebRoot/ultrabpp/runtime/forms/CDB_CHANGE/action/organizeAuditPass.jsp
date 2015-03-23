<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../theme/taglibs.jsp" %>
<ubf:CharacterField name="view_dealGroup" label="处理组" row="1" cell="1" visiable="1" length="1000" />
<ubf:CharacterField name="dealGroup" label="处理组ID" row="1" cell="1" visiable="1" length="100" />
<ubf:SelectField name="view_dealUser" label="处理人" type="collect" resource=" " paras="" cell="1" visiable="1"  />
<ubf:CharacterField name="dealUser" label="处理人ID" row="1" cell="1" visiable="0" length="100" />
<ubf:TimeField name="review_time" label="评审时间" row="1" cell="1" visiable="1" format="yyyy-MM-dd HH:mm:ss"  />
<ubf:CharacterField name="audit_huican_person" label="评审会参会人" row="1" cell="1" visiable="1" length="2000" />
<ubf:CharacterField name="other_canhui_person" label="其它参会人" row="1" cell="1" visiable="1" length="2000" />
<ubf:HiddenField name="audit_huican_person_id" label="评审会参会人id" cell="1" length="2000" />
<ubf:CharacterField name="dealDesc" label="处理意见" row="5" cell="4" visiable="1" length="1000" />
<ubf:CollectField name="notify" label="通知" type="collect" showtype="checkbox" resource="短信:短信,邮件:邮件" paras="" row="1" cell="4" visiable="1" defaultValue="短信" />
