<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:url value="${fn:escapeXml(urlLink)}" var="encodedUrl" />

<div class="col-fun maxlatest">
	<div class="container">
		<div class="banner">
			<div class="device">
				<div class="banner_btn">
					<a class="arrow-left" href="#"></a> <a class="arrow-right" href="#"></a>
				</div>
				<div class="swiper-container">
					<div class="swiper-wrapper">
			<c:forEach items="${medias}" var="media">

				<div class="swiper-slide">
					<img src="${media.url}" />

					<div class="maxporsit">
						<div class="container">
							<div class="maxtite_ban">
							<h2>
								the global </br> factory-direct </br> marketplace </br>
							</h2>
							<p>
								B2B E-Commerce Platform Connectiong</br> Buyers And Sellers With
								Nutritional</br> Ingredients</br>
							</p>
							<a class="button maxsales_but" href="#">SHOW NOW</a>
				</div>
				<div class="maximg_ban"></div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	<div class="pagination"></div>
			</div>
		</div>
	</div>
</div>

