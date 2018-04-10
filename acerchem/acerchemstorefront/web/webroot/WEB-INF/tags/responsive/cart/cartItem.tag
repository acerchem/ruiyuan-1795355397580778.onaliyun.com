<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="entry" required="true" type="de.hybris.platform.commercefacades.order.data.OrderEntryData" %>
<%@ attribute name="index" required="false" type="java.lang.Integer"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/responsive/order" %>


<%--
    Represents single cart item on cart page
 --%>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="entryNumber" value="${entry.entryNumber}"/>
<c:if test="${empty index}">
    <c:set property="index" value="${entryNumber}"/>
</c:if>

<c:if test="${not empty entry}">

        <c:set var="showEditableGridClass" value=""/>
        <c:url value="${entry.product.url}" var="productUrl"/>
        
      	<tr>
				<td>
					<div class="intro">
						<input type="hidden" name="">
						<span class="minflex pict">	
						
                              <a href="${productUrl}"><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></a>
          						
						</span>
						<span class="minflex text">
							<span class="in-title">${fn:escapeXml(entry.product.name)}</span>
							<span class="spec">Specifications:<i>50kg</i></span>	
							<span class="old-price">price:<i><format:price priceData="${entry.basePrice}" displayFreeForZero="true"/></i></span>
						</span>								
					</div>
				</td>
				<td>
				<format:price priceData="${entry.basePrice}" displayFreeForZero="true"/>
				</td>
				
				
				<td>
					<div class="m-setnum">
					<!-- 
						<span class="set sub">-</span>
						<input type="text" name="pdnum" class="set" value="${entry.quantity}">
						<span class="set add">+</span>
						 -->
						 ${entry.quantity}
					</div>
				</td>
				<td>
					<div class="tot">
						<em>
						<format:price priceData="${entry.totalPrice}" displayFreeForZero="true"/>
						</em>
					</div>
				</td>
				<td>
			
					<div class="oper">
						 <c:forEach var="entryAction" items="${entry.supportedActions}">
                                <c:url value="/cart/entry/execute/${entryAction}" var="entryActionUrl"/>
                                
                                 <form:form id="removeCartForm${fn:escapeXml(entry.product.code)}" action="${entryActionUrl}" method="post" >
                                 
                                   <input type="hidden" name="entryNumbers" value="${entry.entryNumber}"/>
                                   <input type="hidden" name="productCode" value="${fn:escapeXml(entry.product.code)}"/>
                                    <input type="hidden" name="initialQuantity" value="${entry.quantity}"/>
					             <button id="removeCartBtn" type="submit" class="btn btn-cart" >
								Remove
							</button> 
                                 </form:form>
                                
                          </c:forEach>
					</div>
					
				
				</td>
			</tr>
 </c:if>
 
