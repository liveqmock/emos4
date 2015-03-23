<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<%@include file="../taglibs.jsp" %>
	<%@include file="../header.jsp" %>
	<%@include file="style.jsp" %>
	<body>
		<div id="bpp_FormContainer">
			<div id="bpp_ActPanel" style="display:block">
				<div style="height:5px;"></div>
				<div id="bpp_ActBody">
					<div id="bpp_ActComment"></div>
					<div id="bpp_ActFields">
						<ubf:CharacterField name="dealInfo" label="处理说明" row="5" cell="4" visiable="1" length="1000" />
						<ubf:SelectField name="deal_person" label="处理人" type="collect" resource=""<清空>":""" paras="" cell="1" visiable="1"  />
						<ubf:AssignTreeField name="dealPerson" label="处理组(人)" row="1" cell="1" single="0"  stepno="step01" selectType="2"  actionName="ToRequest" next="1" visiable="1" />
					</div>
					<div class="bpp_White_Block"></div>
				</div>
			</div>
		</div>
	</body>
</html>
<ubf:Preview/>