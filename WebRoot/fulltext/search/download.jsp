<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.File,java.io.InputStream,java.io.FileInputStream,java.io.OutputStream" %>
<%@ page import="java.io.BufferedInputStream,java.io.BufferedOutputStream" %>

<%
	request.setCharacterEncoding("UTF-8");
	String path = request.getParameter("addr");
	if(path!=null)
	{
		File file = new File(path);
		if(file.exists() && file.isFile())
		{
			InputStream fileInputStream = new FileInputStream(file);
			response.setContentType("application/octet-stream");
			String fileName = file.getName();
			fileName = new String(fileName.getBytes(), "iso8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			BufferedInputStream bis = new BufferedInputStream(fileInputStream);
			BufferedOutputStream bos = null;
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
			{
				bos.write(buff, 0, bytesRead);
			}
			if (bos != null)
			{
				bos.close();
			}
			if (bis != null)
			{
				bis.close();
			}
			out.clear();
			out=pageContext.pushBody();
		}
		else
		{
			out.println("<font color='red'>没有该文件："+path+"。</font>");
		}
	}
	else
	{
		out.println("<font color='red'>文件路径为nul。!</font>");
	}
	
 %>
