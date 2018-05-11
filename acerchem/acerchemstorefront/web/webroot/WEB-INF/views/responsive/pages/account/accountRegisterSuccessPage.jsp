<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>

<c:url value="/" var="homeUrl"/>
<template:page pageTitle="${pageTitle}">
<div class="gen-content maxsucce">
	<div class="g-cont">
		<div class="maxstatu">
			<img src="${themeResourcePath}/css/succ1.png" alt="acerchem"/>
			<h3>Successful registration!</h3>
			<p>Please wait patiently for the audit, after which you can log on to the platform to purchase</p>
			<ul class="tipslist">
				<li>If you have any questions, please contact the customer service <b>service@acerchem.com</b>, we will reply within 24 hours. </li>
				<li>call <b>0592-8263908</b></li>
			</ul>
			<div class="btn-set">		
				<a class="btn" href="${homeUrl}">back home</a>
			</div>
		</div>
	</div>	
</div>
</template:page>
