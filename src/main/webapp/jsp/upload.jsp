<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/upload/uploadFile.action" method="post" enctype="multipart/form-data">
	    文件：<input type="file" name="file"/><br>
	    名字：<input type="text" name="filePath"/><br>
	    <input type="submit" value="上传"/>
	</form>
</body>
</html>