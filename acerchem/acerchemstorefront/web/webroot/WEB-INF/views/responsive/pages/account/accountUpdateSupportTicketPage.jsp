<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="cts" tagdir="/WEB-INF/tags/responsive/user"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<c:url value="/account/support-ticket/${ticketData.id}" var="addSupportTicket"/>

<template:master pageTitle="${pageTitle}">
<div style="margin:5% 5% 10% 5%;">
<div class="back-link border">
    <a href="../support-tickets">
        <span class="glyphicon glyphicon-chevron-left"></span>
    </a>
    <span class="label"><spring:theme code="text.account.supportTicket.updateTicket.heading" text="Request Customer Support"/></span>
</div>

<div class="well well-lg well-tertiary ">
    <div class="col-sm-12 col-no-padding">
        <div class="col-sm-3 item-wrapper">
            <div class="item-group">
                <span class="item-label">
                    <spring:message code="text.account.supporttickets.createTicket.subject" text="Subject"/>
                </span>
                <span class="item-value">
                    ${fn:escapeXml(ticketData.subject)}
                </span>
            </div>
        </div>
        
        <div class="col-sm-3 item-wrapper">
            <div class="item-group">
                <span class="item-label">
                    <spring:theme code="text.account.supporttickets.dateUpdated" text="Date Updated" />
                </span>
                <span class="item-value">
                    <fmt:formatDate value="${ticketData.lastModificationDate}" pattern="dd-MM-yy hh:mm a" />
                </span>
            </div>
        </div>

        <div class="col-sm-3 item-wrapper">
            <div class="item-group">
                <span class="item-label">
                    <spring:theme code="text.account.supporttickets.dateCreated" text="Date Created" />
                </span>
                <span class="item-value">
                    <fmt:formatDate value="${ticketData.creationDate}" pattern="dd-MM-yy hh:mm a" />
                </span>
            </div>
        </div>

        <div class="col-sm-3 item-wrapper">
            <div class="item-group">
                <span class="item-label">
                    <spring:theme code="text.account.supporttickets.status" text="Status" />
                </span>
                <span class="item-value">
                    ${fn:toUpperCase(ticketData.status.id)}
                </span>
            </div>
        </div>

        <!-- Ticket Categories -->
        <c:if test="${not empty ticketData.ticketCategory }">
        	<div class="col-sm-3 item-wrapper">
	            <div class="item-group">
	                <span class="item-label">
        				<spring:theme code="text.account.supporttickets.createTicket.ticketCategory" text="Category"/>
        			</span>
        			<span class="item-value">
        				${ticketData.ticketCategory}
        			</span>
        		</div>
        	</div>
        </c:if>

		<!-- Associated Objects -->
        <c:if test="${not empty ticketData.associatedTo}">
	        <div class="col-sm-3 item-wrapper">
	            <div class="item-group">
	                <span class="item-label">
	                    <spring:theme code="text.account.supporttickets.createTicket.associatedTo" text="Associated To"/>
	                </span>
	                <span class="item-value">
	                    ${fn:substringBefore(fn:toUpperCase(ticketData.associatedTo), ';')}
	                </span>
	            </div>
	        </div>
		</c:if>
    </div>
</div>

<div style="color: #F00">
	<form:errors path="message"/>
</div>

<div class="row customer-ticketing-body">
    <div class="col-xs-12">
        <div class="account-section-header account-section-header-secondary">
        	<h2>Messages</h2>
			-Current Status : ${ticketData.status.id}
			<br/><br/>
        </div>
		<br/>
        <div class="cts-msg-history">
            <div style="display:none" id="ct-overlay-title">
                <div class="headline"><span class="headline-text"><spring:theme code="text.account.supporttickets.updateTicket.addMessage" text="Add Message" /></span></div>
            </div>

            <spring:hasBindErrors name="supportTicketForm">
				<div class="updateSupportTicketValidationErrors" ></div>
			</spring:hasBindErrors>

            <div style="display: none">
                <span id="supporttickets-tryLater"><spring:theme code="text.account.supporttickets.tryLater"/></span>
                <span id="attachment-file-max-size-exceeded-error-message"><spring:theme code="text.account.supporttickets.fileMaxSizeExceeded"/></span>
                <span id="file-too-large-message"><spring:theme code="text.account.supporttickets.file.is.large.than" arguments="${maxUploadSizeMB}"/></span>
            </div>
            <common:globalMessagesTemplates/>

			<form:form method="post" commandName="supportTicketForm" action="${addSupportTicket}">
                 <form:hidden path="subject" value="${ticketData.subject}"/>
                 <input id="currentTicketStatus" name="status" type="hidden" value="${ticketData.status.id}">
                 <form:hidden path="id" value="${ticketData.id}"/>
                 
                 <div class="has-error">
                     <div id="NotEmpty-supportTicketForm-message" class="help-block" style="display: none;"></div>
                     <div id="Size-supportTicketForm-message" class="help-block" style="display: none;"></div>
                 </div>
                <%--  <div class="form-group" style="display: none;">
                     <label for="status" class="control-label"><spring:message code="text.account.supportTicket.updateTicket.status" text="Status"/> </label>
                     <form:select path="status"  cssClass="form-control js-add-message-status">
                         <form:option value="${ticketData.status.id}">
                         	<spring:message code="${ticketData.status.id}"/> (<spring:message code="text.account.supporttickets.currentStatus" text="Current Status"/>)
                         </form:option>
                         <c:forEach items="${ticketData.availableStatusTransitions}" var="status">
                             <form:option value="${status.id}" >
                             	<spring:message code="${status.id}"/> 
                             </form:option>
                         </c:forEach>
                     </form:select> 
                 </div> --%>
                 <formElement:formTextArea idKey="message" labelKey="MESSAGE" path="message" areaCSS="form-control js-add-message" labelCSS=""/>
				 <button class="btn btn-primary btn-block" id="updateTicket" type="submit" >send</button>
            </form:form>
			
            <c:choose>
                <c:when test="${not empty ticketData.messageHistory}">
                    <div class="form-group">
                        <label class="control-label"><spring:message code="text.account.supporttickets.createTicket.message.history"/></label>
                        <p class="form-control-static">${ticketData.messageHistory}</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <cts:ticketEvents ticketData="${ticketData}" />
                </c:otherwise>
            </c:choose>
            
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
$('#updateTicket').on('click',function(){
	var message=$("#message").val().replace(/^\s+|\s+$/g,"");
	if(message==""||message==null){
		 maxalert('Please enter a message!');
		return false;
	}
})

</script> 
</template:master>