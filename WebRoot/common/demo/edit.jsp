<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Book</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>    
    <h2>
        <s:if test="null == book">
            Add Book Spring
        </s:if>
        <s:else>
            Edit Book Spring
        </s:else>
    </h2>
    <s:form action="store.action" theme="simple">
       <tr>
          <td>ISBN:</td>
          <td><input name="book.isbn" value="${book.isbn}" readonly="true"/></td>
       </tr>
       <tr>
          <td>Title:</td>
          <td><input name="book.title" value="${book.title}"/></td>
       </tr>
       <tr>
          <td>Price:</td>
          <td><input name="book.price" value="${book.price}"/></td>
       </tr>
        <s:submit />
    </s:form>
</body>
</html>