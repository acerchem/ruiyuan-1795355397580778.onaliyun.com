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
				<!-- 
					<div class="oper">
						<i class="icons del-icon"></i>
					</div>
					
					 -->
					 
					  <div class="item__menu">
                <c:if test="${entry.updateable}" >
                    <div class="btn-group">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" id="editEntry_${entryNumber}">
                            <span class="glyphicon glyphicon-option-vertical"></span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-right">
                            <c:if test="${not empty cartData.quoteData}">
                                <c:choose>
                                    <c:when test="${not entry.product.multidimensional}">
                                        <li>
                                            <a href="#entryCommentDiv_${entry.entryNumber}" data-toggle="collapse" >
                                                <spring:theme code="basket.page.comment.menu"/>
                                            </a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                            <a href="#entryCommentDiv_${entry.entries.get(0).entryNumber}" data-toggle="collapse" >
                                                <spring:theme code="basket.page.comment.menu"/>
                                            </a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <form:form id="cartEntryActionForm" action="" method="post" />
                             <%-- Build entry numbers string for execute action -- Start --%>
                            <c:choose>
					            <c:when test="${entry.entryNumber eq -1}"> <%-- for multid entry --%>
					                <c:forEach items="${entry.entries}" var="subEntry" varStatus="stat">
						    			<c:set var="actionFormEntryNumbers" value="${stat.first ? '' : actionFormEntryNumbers.concat(';')}${subEntry.entryNumber}" />
						    		</c:forEach>
					            </c:when>
					            <c:otherwise>
					                <c:set var="actionFormEntryNumbers" value="${entry.entryNumber}" />
					            </c:otherwise>
					        </c:choose>
					        <%-- Build entry numbers string for execute action -- End --%>
                            <c:forEach var="entryAction" items="${entry.supportedActions}">
                                <c:url value="/cart/entry/execute/${entryAction}" var="entryActionUrl"/>
                                <li class="js-execute-entry-action-button" id="actionEntry_${fn:escapeXml(entryNumber)}"
                                    data-entry-action-url="${entryActionUrl}"
                                    data-entry-action="${fn:escapeXml(entryAction)}"
                                    data-entry-product-code="${fn:escapeXml(entry.product.code)}"
                                    data-entry-initial-quantity="${entry.quantity}"
                                    data-action-entry-numbers="${actionFormEntryNumbers}">
                                    <a href="#"><spring:theme code="basket.page.entry.action.${entryAction}"/></a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
            </div>
				</td>
			</tr>
 </c:if>
