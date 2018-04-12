<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>
<%@ attribute name="entryGroup" required="true" type="de.hybris.platform.commercefacades.order.EntryGroupData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
   Represents root entry group on cart page
--%>

<spring:htmlEscape defaultHtmlEscape="true"/>

<table class="list">
    <c:choose>
        <c:when test="${not empty entryGroup.children}">
            <c:forEach items="${entryGroup.children}" var="group" varStatus="loop">
                <cart:entryGroup cartData="${cartData}" entryGroup="${group}"/>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty entryGroup.orderEntries}">
                <c:forEach items="${entryGroup.orderEntries}" var="entry">
                   
                  <cart:cartItem cartData="${cartData}" entry="${entry}" index="${entryGroup.groupNumber}"/>
                     
                </c:forEach>
            </c:if>
        </c:otherwise>
    </c:choose>
</table>