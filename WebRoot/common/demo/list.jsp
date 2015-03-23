<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/core/taglibs.jsp"%>
<%@ include file="/common/plugin/ecside/ecside.jsp"%>
<html>
	<ec:table items="books" var="book" retrieveRowsCallback="process"
		sortRowsCallback="process" action="list.action"
		style="width:98%;margin:5px auto;" cellpadding="0" cellspacing="0"
		classic="true" sortable="true" useAjax="false"
		deleteAction="remove.action" toolbarLocation="top" title="演示列表">
		<ec:extend>
			<input class="searchBt" onclick="showQuery()" type="button"
				value="搜索" />
			<input onclick="batchRemove(this, 'deleteFlag','isbn');"
				type="button" value="删除" class="removeBt" />
			<input onclick="window.location.href('edit.action');"
                type="button" value="添加" class="addBt" />
		</ec:extend>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="deleteFlag"
				value="${book.isbn}" viewsAllowed="html" style="text-align:center"
				width="5%" />
			<ec:column property="isbn" title="编号" 
				onclick="window.location.href('load.action?isbn=${book.isbn}')"
				width="20%" />
			<ec:column property="title" title="名称"
				onclick="window.location.href('load.action?isbn=${book.isbn}')"
				width="20%" />
			<ec:column property="price" title="价格"
				onclick="window.location.href('load.action?isbn=${book.isbn}')"
				width="10%" />
		</ec:row>
	</ec:table>
</html>