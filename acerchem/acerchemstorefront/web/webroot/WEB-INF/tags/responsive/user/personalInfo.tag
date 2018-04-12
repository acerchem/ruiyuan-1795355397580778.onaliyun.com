<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url value="/my-account/orders" var="ordersUrl"/>
<spring:url value="/my-account/update-profile" var="infoUrl"/>
<spring:url value="/my-account/address-book" var="addressBookUrl"/>
<spring:url value="/my-account/update-password" var="updatePasswordUrl"/>
<%-- <spring:url value="/my-account/payment-details" var="paymentDetailsUrl"/> --%>
<spring:url value="/my-account/credit" var="creditUrl"/>

<div class="g-left">
	<div class="base">
		<div class="top both">
			<span><img src="${themeResourcePath}/css/vip.png"></span>
			<span>Regular<br/>Member====need modify</span>
		</div>
		<div class="name">Hello,${user.name}</div>
		<a href="/acerchemstorefront/electronics/en/logout"><i class="icons out-icon"></i></a>
		
	</div>

	<div class="memberlist">
		<ul>
			<li class="${nowPage=='orders'?'now':''}"><a href="${ordersUrl}" class="icons order-icons">Order History <i>${searchPageData.pagination.totalNumberOfResults}</i></a></li>
			<li class="${nowPage=='update-profile'?'now':''}"><a href="${infoUrl}" class="icons personal-icons">Personal Information</a></li>
			<li class="${nowPage=='address-book'?'now':''}"><a href="${addressBookUrl}" class="icons address-icons">Address Book</a></li>
			<li class="${nowPage=='update-password'?'now':''}"><a href="${updatePasswordUrl}" class="icons modify-icons">Modify The Password</a></li>
			<%-- <li class="${nowPage=='payment-details'?'now':''}"><a href="${paymentDetailsUrl}" class="icons modify-icons">Payment Details</a></li> --%>
			<li class="${nowPage=='credit'?'now':''}"><a href="${creditUrl}" class="icons modify-icons">Credit Account</a></li>
		</ul>
	</div>

</div>