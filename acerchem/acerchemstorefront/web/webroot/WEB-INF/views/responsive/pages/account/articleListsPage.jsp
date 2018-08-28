<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>

<c:url value="/" var="homeUrl"/>
<template:page pageTitle="${pageTitle}">


<div class="gen-content maxsucce">
	<div class="g-cont">
		<div class="maxstatu">
			<table>
				<tr>
					<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;text-align:center;">article Title</th>
					<th style="text-transform: capitalize;color: #333;font-size: 16px;background: #f3f3f3;">Time created</th>
				</tr>
				<tr><td style="padding:0px 0px;"></td></tr>
				<c:forEach items="${articles}" var="article">
					<tr class="responsive-table-item">
						<td class="responsive-table-cell" style="padding:10px 10px;font-size:14px;">
							<a href="${article.url}" target="_blank">
								${article.title}
							</a>
						</td>
						<td class="responsive-table-cell" style="padding:10px 10px;font-size:14px;">
							${article.creationtime}
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>	
</div>
</template:page>
