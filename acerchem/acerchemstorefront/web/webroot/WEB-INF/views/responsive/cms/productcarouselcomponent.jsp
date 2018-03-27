<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />


<c:if test="${component.isSale}">

<div class="title">
		${fn:escapeXml(title)}
	<div class="maxpagin"></div>
</div>

<div class="swiper-container2 maxon_salesul">

	<div class="banner_btn"> 
   		<a class="arrow-left" href="#"></a> 
		<a class="arrow-right" href="#"></a>
    </div>
    
	  <ul class="swiper-wrapper">
	  <c:forEach items="${productData}" var="product">
	   <li class="swiper-slide">
	    	<div class="swiper-slide-bg">
	    		<div class="bg-box">
	    		<div class="maxmark">
	    			<h3>10</h3>
	    			<p><em class="">%</em><em>OFF</em></p>
	    		</div>
		    	<div class="maximg">
		    	
		    	<c:url value="${product.url}" var="productUrl"/>
		    	
		    		<a href="${productUrl}">
		    		<product:productPrimaryImage product="${product}" format="product"/>
			    		</a>
	    	
		    		
		    		<div class="maximgpor">
		    			<a href="">Buy Now</a><!--未登录时-->
		    		</div>
		    	</div>
		    	<div class="maxtext">
		    		<p>TwinTiger Glutathione (5kg Bag) by Jincheng</p>
		    		<!-- 登录显示 -->
		    		<!-- <span class="price">$68.00</span>
		    		<span class="old-price">$98.00</span> -->
		    		<!-- 登录显示 END-->
		    	</div>
		    	</div>
	    	</div>
	    </li>
	    </c:forEach>
	  </ul>
</div>	
</c:if>	

<c:if test="${component.isPromotion}">

<div class="title">
		${fn:escapeXml(title)}
	<div class="maxpagin"></div>
</div>

<div class="swiper-container3 maxon_salesul">

	<div class="banner_btn"> 
   		<a class="arrow-left" href="#"></a> 
		<a class="arrow-right" href="#"></a>
    </div>
    
	  <ul class="swiper-wrapper">
	  <c:forEach items="${productData}" var="product">
	   <li class="swiper-slide">
	    	<div class="swiper-slide-bg">
	    		<div class="bg-box">
	    		
		    	<div class="maximg">
		    	<product:productPrimaryImage product="${product}" format="product"/>
			    	
		    		<div class="maximgpor">
		    			<a href="">Buy Now</a><!--未登录时-->
		    		</div>
		    	</div>
		    	<div class="maxtext">
		    		<p>TwinTiger Glutathione (5kg Bag) by Jincheng</p>
		    		<!-- 登录显示 -->
		    		<!-- <span class="price">$68.00</span>
		    		<span class="old-price">$98.00</span> -->
		    		<!-- 登录显示 END-->
		    	</div>
		    	</div>
	    	</div>
	    </li>
	    </c:forEach>
	  </ul>
</div>	

</c:if>	    


<c:if test="${component.isWell}">
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
			    			<a href="">Buy Now</a><!--未登录时-->
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

</c:if>

