<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="level" value="${component.displayLevel}"/>
<c:url value="${fn:escapeXml(urlLink)}" var="encodedUrl" />

<c:if test='${level eq 1}'>
	<li class="item1">
		<a href="">
			<div class="meansale">
				<p>promo</p>
				<span>Sodium Hyaluronate 1% Solution (25kg Drum) </span>
				<label><i>15</i>%</label>
				<em><i>$58</i></em>
			</div>
			
			<img src="${media.url}"/>
		</a>
	</li>
</c:if>

<c:if test='${level eq 2}'>
<li class="item2">
		<a href="">
			<div class="text">
				<p>Essential Nutrients for sports</p>
				<span>View the details</span>
			</div>
			<img src="${media.url}"/>
		</a>
	</li>
</c:if>

<c:if test='${level eq 3}'>
<li class="item4">
	<a href="">
	<div class="textmessge">
		<p>If you have an opinion or advice </p>
		<span>Send message</span>
	</div>
	<img src="${media.url}"/>
	</a>
</li>
  </c:if>
  
  <c:if test='${level eq 4}'>
  
   <li class="item5 formtext">
		<div>
			<p>Let's contact you</p>
			<span>Let's contact you to get more information</span>
		</div>
		<form action="">
			<input type="text" placeholder="Your Emaill Address" class="text-inp">
			<input type="number" placeholder="Your Phone" class="text-inp">
		</form>
		<button class="btn">Subscribe</button>
	</li>

  </c:if>
