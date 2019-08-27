<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="showDeliveryAddress" required="true" type="java.lang.Boolean" %>
<%@ attribute name="showPotentialPromotions" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/responsive/grid" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<spring:htmlEscape defaultHtmlEscape="true" />
<spring:url value="/checkout/multi/summary/placeOrder" var="placeOrderUrl"/>
<spring:url value="/quote/create" var="quoteOrderUrl"/>


<c:set var="hasShippedItems" value="${cartData.deliveryItemsQuantity > 0}" />
<c:set var="deliveryAddress" value="${cartData.deliveryAddress}"/>


<div class="cart-total check-total">					
<div class="g-table">	
	<div class="g-title"><span>Order Summary</span></div>
	<ul class="check-item">
<c:forEach items="${cartData.entries}" var="entry" varStatus="loop">
	<c:url value="${entry.product.url}" var="productUrl"/>
	<li class="item both">	    		
   		<div class="img">
   			<a href="${productUrl}">
   		  		<product:productPrimaryImage product="${entry.product}" format="thumbnail"/>
   		  	</a>
   		</div>
   		<div class="maxtext">
    		<p class="in-title">${fn:escapeXml(entry.product.name)}</p>
    		<p class="spec">package:<i>${entry.product.netWeight}${entry.product.unitName} &nbsp ${entry.product.packageType}</i></p>
    		<div class="spset">
    			<span class="price">
    			   <%--  <format:price priceData="${entry.basePrice}" displayFreeForZero="true" /> --%>
    			   <c:if test="${not empty entry.product.promotionPrice}">
						    <format:price priceData="${entry.product.promotionPrice}" displayFreeForZero="true" />
						</c:if>
						
						<c:if test="${empty entry.product.promotionPrice}">
					    	 <format:price priceData="${entry.product.price}" displayFreeForZero="true" />
						</c:if>
    			</span>
    			<span class="num">${entry.quantity*entry.product.netWeight}${entry.product.unitName}</span>
    		</div>
    	</div>
   	</li>
	
	
</c:forEach>

</ul>


<div class="list">
	<div class="item">
		<div class="item-row">
			<span>Subtotal</span>
			<span>
				<format:price priceData="${cartData.subTotal}"/>
			</span>
		</div>
		
		<div class="item-row">
			<span>Discount Amount</span>
			
            <span>-<format:price priceData="${cartData.totalDiscounts}"/></span>
		</div>

<c:if test="${not empty cartData.deliveryMode}">
<c:if test="${not empty cartData.deliveryCost}">
		<div class="item-row">
			<span>Delivery Cost</span>
			<span ><format:price priceData="${cartData.deliveryCost}"/></span>
		</div>
		</c:if>
</c:if>
		

		<!-- <div class="item-row">
			<span>Discount Amount</span>
			<span class="row">-$12.34</span>
		</div> -->
		
			<div class="item-row">
				<span>Release Cost</span>
				
	              <span><format:price priceData="${cartData.storageCost}"/></span>
	               
			</div>
			
			<div class="item-row">
				<span>Handling Charge</span>
				
	              <span><format:price priceData="${cartData.operateCost}"/></span>
	               
			</div>
<c:if test="${not empty cartData.deliveryMode}">
		<div class="item-row">
			<span>Order Total</span>
			<span >
			<format:price priceData="${cartData.totalPrice}"/>
			</span>
		</div>
		</c:if>
	</div>					
</div>		

<form:form action="${placeOrderUrl}" id="placeOrderForm1" commandName="placeOrderForm">

<input type="hidden" name="paymentCode" id="paymentCode" value=""/>

 <label>
 <input type="checkbox" checked="checked" name="termsCheck" id="termsCheck">
 	<span class="checkbox" >
	<a href="javascript:openText()" style="color: red" >Sales agreement</a>
	</span>
	</label>



<div class="btn-set">
  <button id="placeOrder" type="submit" class="btn btn-submit">
      Place Order
  </button>
  </div>

</form:form>

<form:form action="${quoteOrderUrl}" id="quoteForm1" method="get" commandName="quoteForm">
	<div class="btn-set">
		<button id="placeQuote" type="button" class="btn btn-submit">
			<spring:theme code="checkout.summary.placeQuote" />
		</button>
	</div>
</form:form>
</div>

</div>

<div class="quote-popup" style="display: none;">
	<div>
	<div class="quote-popup-html g-cartable maxfixed maxfixed-bg" style="display: block;">
		<div class="indwrap">
			<div class="cart-total">
				<div class="title">Quote</div>
				<form action="">
					<div class="maxfixed-over  product-table">
						<div class="quote_popup_msg">
							StoreId is different with others.</div>
					</div>
					<div class="btn-set">
						<a class="btn btn-close" href="javascript:void(0);">Close</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	</div>
</div>

<div id="light" class="white_content" >
	<table class="font" style="font-family:arial;font-size: 12px;border-color: #000000">
	<tbody>
		<tr>
			<td style="text-align: center;">
			<strong>			
					THIS SALES AGREEMENT (the  "Agreement") is BETWEEN:
			</strong>
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">
			<strong>			
					Ingredients4u (the "SELLER")
			</strong>
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">
			<strong>			
					And
			</strong>
			</td>
		</tr>
		<tr>
			<td style="text-align: center;">
			<strong>			
					Customer (the "BUYER")
			</strong>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			IN CONSIDERATION OF THE COVENANTS and agreement’s contained in this Sales Agreement the parties to this Agreement agree as follows:
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			1.	General Terms:
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			1) The legal relationship between the Seller and the Buyer is governed by the latest United Nations Conventions on contract for the international sale of goods(CISG). Price terms apply to INCOTERMS 2010.

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			2) The ownership of the goods transfers from the seller to the buyer after the seller receives the full payment of the goods.

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			3) Buyer can’t claim any exclusive right under this contract nor any compensation for any costs they may occur while selling. Seller has right to sell its products to any entity they choose at any price except if the exclusive contract signed between the seller and the buyer.

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			2.	Insurance
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			Covering all risks for 110% of invoice (with war risk) value to be affected by the buyer with price terms of FOB&CFR, effected by seller in the other price terms.

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			3.  Quality and quantity discrepancy:

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			In the case of quality of discrepancy, claim should be filed by the buyer within 30 days after receiving the goods, the buyer shall supply the inspection report by 3rd party. The buyer will lose the claim rights after 30 days of receiving the goods. While for quantity discrepancy, claim should be filed by the buyer within 15 days after receiving the goods. Otherwise the buyer will give up the rights of quality and quantity discrepancy complaint. If the quality and quantity discrepancy is occurred by the seller, the seller will make the compensation within the value of the invoice.

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			4.	Delivery
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			Delivery Date is upon buyer’s selection on the platform, for inventory quantity the longest time that we can keep stock for an order is 30 days. Extension of delivery date is allowed only within 15 days after order placed and could be extended 15 days maximum. If buyer wants to extend more than 15 days, seller reserves the right to charge 1% stock management fee for every 30 more days.

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			5.  Force majeure

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			The seller shall not be held for failure or delay the delivery of the entire lot or a portion of the goods under the sales Agreement in consequence of any force majeure incidents which might occur. Force majeure as referred to in the Agreement means unforeseeable, unavoidable and insurmountable objective conditions such as natural disasters, coup, wars, strikes and so on. The seller or buyer should advise the other party on time of occurrence.

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			6.  Arbitration
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			Any dispute arising from or in connection with the sales Agreement shall be settled through friendly negotiation. In case no settlement can be reached, the dispute shall then be submitted to the Economic and Trade Arbitration Commission in Seller's address. The arbitral award is final and binding upon both parties.

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			7.	Cancellation
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			The Seller reserves the right to cancel placed order:
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			a.	if the Buyer fails to pay for any shipment when due;
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			b.	in the event of the Buyer’s insolvency or bankruptcy; or
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			c.	if the Seller deems that its prospect of payment is impaired. 
			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			The buyer reserves the right to cancel order at least 7 days prior to delivery date free of charge, if buyer wants to cancel the order within 7 days prior to the delivery date, seller reserves to charge any loss caused and 1% stock management fee upon order amount.

			</p>
			</td>
		</tr>
		<tr>
			<td>
			<p>
			8.  This Agreement constitutes the entire agreement between the parties and ther are no further items or provisions, either oral or otherwise. The Buyer acknowledges that is has not replied upon any representations of the Seller as to prospective performance of the Goods, but has relied upon its own inspection and investigation of the subject matter. 

			</p>
			</td>
		</tr>
		</tbody>
	</table>
	<button onclick = "document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'">close</button>
	
</div>
<div id="fade" class="black_overlay"></div>
<style> 
    .black_overlay{ 
        display: none; 
        position: absolute; 
        top: 0%; 
        left: 0%; 
        width: 100%; 
        height: 100%; 
        background-color: black; 
        z-index:1001; 
        -moz-opacity: 0.8; 
        opacity:.80; 
        filter: alpha(opacity=88); 
    } 
    .white_content { 
        display: none; 
        position: absolute; 
        top: 5%; 
        left: 25%; 
        width: 55%; 
        height: 80%; 
        padding: 20px; 
        border: 10px solid orange; 
        background-color: white; 
        z-index:1002; 
        overflow: auto; 
    } 
</style> 
