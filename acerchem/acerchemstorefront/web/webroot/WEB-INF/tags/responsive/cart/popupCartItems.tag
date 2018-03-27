<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ attribute name="quantity" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:htmlEscape defaultHtmlEscape="true" />
	<c:url value="${product.url}" var="entryProductUrl"/>
	<!--shaun  -->
	<ul>
		<li class="item">
			<!--image  -->	
    		<div class="img">
    			<a href="${entryProductUrl}">
    		  		<product:productPrimaryImage product="${entry.product}" format="cartIcon"/>
    		  	</a>
    		</div>
    		
    		<div class="maxtext">
    		  <!--name  -->
	    		<p><a class="name" href="${entryProductUrl}">${fn:escapeXml(product.name)}</a></p>
	    	  <!-- qty -->
	    	  <div class="qty">
		    	<p class="maxtext-note">
		    		<span><spring:theme code="popup.cart.quantity.added"/></span>&nbsp;<em>1</em>
		    	</p>
		      </div>
		    	
			    	<div class="maxdel"></div> 
			    	<div class="pt-num-but">
				    	<span class="price"><format:price priceData="${entry.basePrice}"/></span>
	    				<span class="old-price"><format:price priceData="${entry.basePrice}"/></span>
	    				
					    	<div class="m-setnum">
						    	<span class="set sub">-</span>
						    	<input type="text" name="pdnum" class="set" value="1">
						    	<span class="set add">+</span>
					    	</div>
				   </div>
		   </div>
    	</li>
	</ul>








