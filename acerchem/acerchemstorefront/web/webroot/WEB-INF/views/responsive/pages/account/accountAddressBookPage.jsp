<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:htmlEscape defaultHtmlEscape="true" />
<c:set var="noBorder" value=""/>
<c:if test="${not empty addressData}">
    <c:set var="noBorder" value="no-border"/>
</c:if>

<div class="title">Address Book</div>
<c:if test="${empty addressData}">
	<div class="account-section-content content-empty">No Saved Addresses Found</div>
</c:if>

<c:if test="${not empty addressData}">    
	<div class="rigcont">
		<div class="addbook">
			<c:forEach items="${addressData}" var="address">
				<ul>
					<li ${address.defaultAddress==true?'class="def"':'class="row"'}>
						<div class="row">
							<span class="row-left name">${fn:escapeXml(address.title)}&nbsp;${fn:escapeXml(address.firstName)}&nbsp;${fn:escapeXml(address.lastName)}</span>
							<a href="set-default-address/${fn:escapeXml(address.id)}">
								<span class="row-left setdef">default</span>
							</a>
						</div>
						<div class="row alone">
							<span class="text">
								<c:if test="${not empty address.line1}">
						            ${fn:escapeXml(address.line1)},
						        </c:if>
						        <c:if test="${not empty address.line2}">
						            ${fn:escapeXml(address.line2)},
						        </c:if>
								<c:if test="${not empty address.town}">
						            ${fn:escapeXml(address.town)},
						        </c:if>
						        <c:if test="${not empty address.region.name}">
						            ${fn:escapeXml(address.region.name)},
						        </c:if>
								<c:if test="${not empty address.postalCode}">
						            ${fn:escapeXml(address.postalCode)},
						        </c:if>
						        <c:if test="${not empty address.country.name}">
						            ${fn:escapeXml(address.country.name)}
						        </c:if>
							</span>
							<span class="phone">${fn:escapeXml(address.phone)}</span>
						</div>
						<div class="row operate">
							<a href="edit-address/${fn:escapeXml(address.id)}">
								<i class="icons edit-icon"></i>
							</a>
							<a href="#" class="action-links removeAddressFromBookButton" data-address-id="${fn:escapeXml(address.id)}" data-popup-title="<spring:theme code="text.address.delete.popup.title" />">
								<i class="icons del-icon"></i>
							</a>
						</div>
					</li>
				</ul>
			</c:forEach>
		</div>
		<div class="btn-set">	
	        <a href="add-address" class="btn btn-newadd">
	            New Address
	        </a>
		</div>
	</div>
</c:if>

<c:forEach items="${addressData}" var="address">
	<div class="display-none">
    	<div id="popup_confirm_address_removal_${fn:escapeXml(address.id)}" class="account-address-removal-popup">
     		<div class="text">Whether to delete this address ?</div>
     		<br/>
     		<div class="modal-actions">
            	<div class="row">
                	<ycommerce:testId code="addressRemove_delete_button">
                    	<div class="col-xs-12 col-sm-6 col-sm-push-6">
                        	<a class="btn btn-primary btn-block" data-address-id="${fn:escapeXml(address.id)}" href="remove-address/${fn:escapeXml(address.id)}">
                                <spring:theme code="text.address.delete" />
                            </a>
                        </div>
                    </ycommerce:testId>
	                <div class="col-xs-12 col-sm-6 col-sm-pull-6">
		                <a class="btn btn-default btn-block closeColorBox" data-address-id="${fn:escapeXml(address.id)}">
		                    <spring:theme code="text.button.cancel"/>
		                </a>
		             </div>
	       	  	</div>
       		</div>
     	</div>
    </div>
</c:forEach>
			
