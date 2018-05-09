<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:htmlEscape defaultHtmlEscape="true" />

<div class="g-cell">
			<!-- product Item -->
		<div class="g-table product-table">
		<div class="g-title">
			<table>
				<tr>
					<td><div class="intro">Item <em class="min"></em></div></td>
					<td>Price</td>
					<td>Qty</td>
					<td>Total Weight</td>
					<td>Total Price</td>
					<td><div class="tot">Operate</div></td>
				</tr>
			</table>
		</div>	
		
		<c:forEach items="${cartData.rootGroups}" var="group" varStatus="loop">
	    	<cart:rootEntryGroup cartData="${cartData}" entryGroup="${group}"/>
	        <p></p>
	    </c:forEach>			
				
	</div>
	
	<cart:exportCart/>
</div>

		