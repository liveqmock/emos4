<%@ page pageEncoding="UTF-8" contentType="application/json; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${param.id == '01'}">
{
	"result":[
		{"id":"0101","name":"文件夹1","type":"dir","date":"2010/10/12"},
		{"id":"0102","name":"文件夹2","type":"dir","date":"2010/10/12"},
		{"id":"text1","name":"文本1","type":"txt","size":"21000","date":"2010/10/12","author":"admin"},
		{"id":"text2","name":"文本2","type":"txt","size":"451","date":"2010/10/12","author":"admin"},
		{"id":"jpg1","name":"图片1","type":"img","size":"23560","date":"2010/10/12","author":"王云"}
	]
}
</c:if>
<c:if test="${param.id == '0101'}">
{
	"prevent":{"ctx-delete":true},
	"result":[
		{"id":"text3","name":"text文件3","type":"txt","size":"21000","date":"2010/10/12","author":"王云"}
		,{"id":"text4","name":"text文件4","type":"txt","size":"451","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"gif1","name":"test图片2","type":"img","size":"23560","date":"2010/5/4","author":"李庚"}
	]
}
</c:if>
<c:if test="${param.id == '0102'}">
{
	"prevent":{"ctx-download":true,"ctx-publish":true},
	"result":[
		{"id":"text3","name":"text文件3","type":"txt","size":"21000","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"text4","name":"text文件4","type":"txt","size":"451","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"gif1","name":"test图片2","type":"img","size":"23560","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"text4ss","name":"text文件4","type":"txt","size":"451","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"gif1s","name":"test图片2","type":"img","size":"23560","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"text4s","name":"text文件4","type":"txt","size":"451","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"gif1f","name":"test图片2","type":"img","size":"23560","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"text4f","name":"text文件4","type":"txt","size":"451","date":"2010/5/4","author":"李庚"}
		,{"id":"gif1f","name":"test图片2","type":"img","size":"23560","date":"2010/5/4","author":"李庚"}
		,{"id":"text4aa","name":"text文件4","type":"txt","size":"451","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"gif1a","name":"test图片2","type":"img","size":"23560","date":"2010/5/4","author":"李庚"}
		,{"id":"texta","name":"text文件4","type":"txt","size":"451","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"gif1b","name":"test图片2","type":"img","size":"23560","date":"2010/5/4","author":"李庚"}
		,{"id":"text4b","name":"text文件4","type":"txt","size":"451","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"gif1e","name":"test图片2","type":"img","size":"23560","date":"2010/10/12","author":"刘莉莉"}
		,{"id":"text4e","name":"text文件4","type":"txt","size":"451","date":"2010/5/4","author":"李庚"}
		,{"id":"gif1e","name":"test图片2","type":"img","size":"23560","date":"2010/10/12","author":"刘莉莉"}
	]
}
</c:if>