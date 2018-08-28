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
<c:url value="/reports/docDel" var="delUrl" />	
<body class="index-body">
	<!-- top -->
	<jsp:include page="reportMenu.jsp" >
    	<jsp:param name="section"  value="${isDocMenu}" />
    </jsp:include>
	<!-- content -->
	<div class="index-container">

		<div class="content">
			<table class="grid" width="100%" align="center">
				<tr align="center">

					<th width="55%">文章名称</th>
					<th width="20%">作者</th>
					<th width="15%">创建时间</th>
					<th>操作</th>
				</tr>
		        <c:if test="${not empty docList}">
				   <c:forEach items="${docList}" var="detail">
						<tr>
		
							<td class="odd">${detail.title}</td>
							<td class="even">${detail.author}</td>
							<td class="odd"><fmt:formatDate value="${detail.creationtime}"
									timeStyle="short" type="both" /></td>
							<td class="even" align="center"><a
										href="javascript:if(confirm('确实要删除么？')) document.location='${delUrl}?delCode=${detail.articleCode}';">删除</a></td>
						</tr>
			   		</c:forEach>
				</c:if>

			</table>
		</div>

	</div>
</body>
</html>