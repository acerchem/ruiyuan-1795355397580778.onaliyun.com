<%@ page trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>
<spring:htmlEscape defaultHtmlEscape="true" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="UTF-8">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>message</title>

<link rel="stylesheet" type="text/css"
	href="${commonResourcePath}/acerchem/images/style.css">

</head>
<c:url value="/reports/docAdd" var="addUrl" />
<body class="index-body">
	<!-- top -->
	<jsp:include page="reportMenu.jsp" />
	<!-- content -->
	<div class="index-container">


		<form class="formfont" action="${addUrl}"
			enctype="multipart/form-data" method="post">
			<table class="grid">
				<tr height="30px">
					<td>文章标题:<input type="text" name="title" maxlength="100"
						size="100">
					
					</td>
				</tr>
				<tr height="30px">
					<td>文章作者:<input type="text" name="author">
					</td>
				</tr>
				<tr height="30px">
					<td>上传文件：<input type="file" name="file">
				
					</td>
				</tr >
				<tr height="30px">
					<td><input type="submit" value="上传文件确定">
				</tr>
			</table>
		</form>


	</div>
</body>
</html>