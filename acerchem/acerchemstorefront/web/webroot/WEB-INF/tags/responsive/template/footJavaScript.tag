<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/responsive/template/cms" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>

<c:url value="/" var="siteRootUrl"/>


<c:choose>
	<c:when test="${wro4jEnabled}">
	</c:when>
	<c:otherwise>
		<%-- Custom ACC JS --%>
		<!-- shaun -->
		<%-- Custom ACC JS --%>
		<!-- shaun -->

		<script type="text/javascript" src="${commonResourcePath}/js/acc.address.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.autocomplete.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.carousel.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.cart.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.cartitem.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.checkout.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.checkoutaddress.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.checkoutsteps.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.cms.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.colorbox.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.common.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.forgottenpassword.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.global.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.hopdebug.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.imagegallery.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.langcurrencyselector.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.minicart.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.navigation.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.order.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.paginationsort.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.payment.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.paymentDetails.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.pickupinstore.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.product.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.productDetail.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.quickview.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.ratingstars.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.refinements.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.silentorderpost.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.tabs.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.termsandconditions.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.track.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.storefinder.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.futurelink.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.productorderform.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.savedcarts.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.multidgrid.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.quickorder.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/acc.quote.js"></script>

		<script type="text/javascript" src="${commonResourcePath}/js/acc.csv-import.js"></script>

		<script type="text/javascript" src="${commonResourcePath}/js/_autoload.js"></script>
		<script type="text/javascript" src="${commonResourcePath}/js/_autoload.js"></script>


	</c:otherwise>
</c:choose>

