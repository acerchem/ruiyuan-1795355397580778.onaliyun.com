<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />


<c:if test="${component.isSale}">

<div class="g-title">
		Everyone is Buying
	</div>
<ul class="pdlist">
 <c:forEach items="${productData}" var="product">
 <c:url value="${product.url}" var="productUrl"/>
	<li class="both">
		<div class="img">
   			<a href="${productUrl}"><product:productPrimaryImage product="${product}" format="product"/></a>
   		</div>
   		<div class="maxtext">
    		<p class="in-title">Potassium Sorbate Granular (25kg Carton) by Gaojiang</p>
    		<div class="spset">
    			<span class="price">$68.00</span>
    		</div>
    	</div>
	</li>
	</c:forEach>
</ul>	
</c:if>	

<c:if test="${component.isPromotion}">

	<div class="title">
			Promotion Item
			<div class="maxpagin">				
			</div>	
		</div>
		<div class="banner_btn"> 
    		<span class="arrow-left"></span> 
			<span class="arrow-right"></span>
	    </div>
	    <div class="slide-wrap">
		    <div class="slide-item both">
		     <c:forEach items="${productData}" var="product">
		     <c:url value="${product.url}" var="productUrl"/>
		    	<div class="item">	    		
		    		<div class="img">
		    			<a href="${productUrl}"><product:productPrimaryImage product="${product}" format="product"/></a>
		    		</div>
		    		<div class="maxtext">
			    		<p>TwinTiger Glutathione (5kg Bag) by Jincheng</p>
			    		<!-- 登录显示 -->
			    		<span class="price"><i>$68.00</i></span>
			    		<span class="old-price"><i>$98.00</i></span>
			    		<!-- 登录显示 END-->
			    	</div>
		    	</div>
		    	</c:forEach>
		    	</div>
		    	
		    	</div>

</c:if>	    


<!--<c:if test="${component.isWell}">
	<div class="title">
		${fn:escapeXml(title)}
	<div class="maxmore_tit">
		<a href="#" id="maxcenbtn1">more</a>
	</div>	
	</div>
	
	<div class="maxon_salesul maxon_salesul_list">
		 <ul>
		  <c:forEach items="${productData}" var="product">
		 	 <li>
		    	<div class="swiper-slide-bg">
		    		<div class="bg-box">
			    	<div class="maximg">
			    		<product:productPrimaryImage product="${product}" format="product"/>
			    		<div class="maximgpor">
			    			<a href="">Buy Now</a><!--未登录时
			    		</div>
			    		
			    	</div>
			    	<div class="maxtext">
			    		<p>TwinTiger Glutathione (5kg Bag) by Jincheng</p>
			    	</div>
			    	</div>
		    	</div>
		    </li>
		    </c:forEach>
		 </ul>
	</div>

</c:if>-->

