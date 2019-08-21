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
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>


<%--
    Represents single cart item on cart page
 --%>

<spring:htmlEscape defaultHtmlEscape="true" />

<c:set var="entryNumber" value="${entry.entryNumber}"/>
<c:if test="${empty index}">
    <c:set property="index" value="${entryNumber}"/>
</c:if>


<c:set var ="a" value="${entry.product.netWeight}"/>
<c:set var="b" value="${entry.quantity}"/>
<c:set var ="TotalWeight" value="${a*b}"/>


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
							<span class="spec">package:<i>${entry.product.netWeight}${entry.product.unitName}/${entry.product.packageType}</i></span>	
							<span class="old-price">price:<i><format:price priceData="${entry.basePrice}" displayFreeForZero="true"/></i></span>
							<span class="old-price">Delivery From:<i>${entry.deliveryPointOfService.name}</i></span>
						</span>
					</div>
				</td>
				<td>
				<%-- <c:choose>
				     <c:when test="${entry.promotionBasePrice ne entry.basePrice}">
						<em>
						<format:price priceData="${entry.promotionBasePrice}" displayFreeForZero="true"/>
						</em>
						<i><format:price priceData="${entry.basePrice}" displayFreeForZero="true"/></i>
					</c:when>
					  <c:otherwise>
					      <em> --%>
						<format:price priceData="${entry.basePrice}" displayFreeForZero="true"/>
						<%-- </em>
					  </c:otherwise>
				</c:choose>	 --%>
				</td>

				<td>
					<div class="m-setnum">
						<span class="set add">
							<c:url value="/cart/minusOne" var="cartMinusOneUrl"/>
                            <form:form id="minusCartForm${entry.product.code}${entry.deliveryPointOfService.name}" action="${cartMinusOneUrl}" method="post" >
								<input type="hidden" name="productCodePost" value="${fn:escapeXml(entry.product.code)}"/>
								<input type="hidden" name="storeId" value="${entry.deliveryPointOfService.name}"/>
								<%--<input type="hidden" name="stockLevel" value="${entry.product.}"/>--%>
								<input type="hidden" name="entryNumber" value="${entry.entryNumber}"/>
								<input type="hidden" name="qty" value="${entry.quantity - 1}"/>
								<input type="hidden" name="stockLevel" value="${entry.product.stock.stockLevel}"/>
								<input type="hidden" name="stockThreshold" value="${entry.product.minOrderQuantity}"/>
								<button type="button" onclick="minusQuantity('minusCartForm', '${entry.product.code}', '${entry.deliveryPointOfService.name}')">-</button>
							</form:form>
						</span>
						<input type="text" id="pdnum${entry.product.code}${entry.deliveryPointOfService.name}" name="pdnum${entry.product.code}${entry.deliveryPointOfService.name}" class="set" value="${entry.quantity}" onblur="updateQuantity('minusCartForm', '${entry.product.code}', '${entry.deliveryPointOfService.name}')">
						<span class="set add">
                            <c:url value="/cart/addOne" var="cartAddOneUrl"/>
                            <form:form id="addCartForm${entry.product.code}${entry.deliveryPointOfService.name}" action="${cartAddOneUrl}" method="post" >
                                <input type="hidden" name="productCodePost" value="${fn:escapeXml(entry.product.code)}"/>
                                <input type="hidden" name="storeId" value="${entry.deliveryPointOfService.name}"/>
								<input type="hidden" name="qty" value="${entry.quantity}"/>
								<input type="hidden" name="stockLevelCode" value="${entry.product.stock.stockLevel}"/>
								<input type="hidden" name="stockThreshold" value="${entry.product.minOrderQuantity}"/>
								<button type="button" onclick="updateQuantity('addCartForm', '${entry.product.code}', '${entry.deliveryPointOfService.name}')">+</button>
                            </form:form>
                        </span>
					</div>
				</td>
				
				<td>
					<div class="m-setnum" id="totalWeight">
					<!-- 
						<span class="set sub">-</span>
						<input type="text" name="pdnum" class="set" value="${entry.quantity}">
						<span class="set add">+</span>
						 -->
						 ${TotalWeight}${entry.product.unitName}
					</div>
				</td>
				
				<td>
					<div class="tot" id="totalPrice">
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

<script type="text/javascript" >
	function minusQuantity(formName, productCode, storeId) {
		let cartForm = document.getElementById(formName + productCode + storeId);
		let avl = cartForm.qty ? cartForm.qty.value : '0';
		let stockThreshold = cartForm.stockThreshold ? cartForm.stockThreshold.value : '0';
		if(parseInt(avl)<parseInt(stockThreshold)){
			maxalert("Cannot be less than the stock threshold!");
			return;
		}
		cartForm.qty.value = avl;
		cartForm.submit();
	}
	function updateQuantity(formName, productCode, storeId)
	{
		let cartForm = document.getElementById(formName + productCode + storeId);
		let avl = document.getElementById("pdnum" + productCode + storeId).value ? document.getElementById("pdnum" + productCode + storeId).value : '0';
		let stockThreshold = cartForm.stockThreshold ? cartForm.stockThreshold.value : '0' ;
		let stockLevel = cartForm.stockLevel ? cartForm.stockLevel.value : '0' ;
		if(parseInt(avl)<parseInt(stockThreshold)){
			maxalert("Cannot be less than the stock threshold!");
			document.getElementById("pdnum" + productCode + storeId).value = stockThreshold;
			cartForm.qty.value = stockThreshold;
			return;
		}
		// if (parseInt(avl) > parseInt(stockLevel)) {
		// 	maxalert("Cannot be larger than the stock level!");
		// 	document.getElementById("pdnum" + productCode).value = stockThreshold;
		// 	cartForm.qty.value = stockThreshold;
		// 	return;
		// }
		cartForm.qty.value = avl;
		cartForm.submit();
	}

</script>
