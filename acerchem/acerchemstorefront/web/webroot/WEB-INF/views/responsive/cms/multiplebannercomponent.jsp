<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="level" value="${component.displayLevel}"/>
<c:url value="${fn:escapeXml(urlLink)}" var="encodedUrl" />

<c:if test='${level eq 2}'>
<li class="${classfication}">
	<a href="">
		<img src="${media.url}"/><p>Factory-Direct </br> Pricing</p>
	</a>
</li>
</c:if>

<c:if test='${level eq 5}'>
<li>
<img src="${media.url}"/>
	<div class="bg-box2">
   		<div class="maxmark">
   			<div class="maxmark-con">
    			<p><em class="">%</em><em>OFF</em></p>
    			<h3>
   				<span>sales</span>15</h3>
   			</div>
   		</div>
   		<div class="maximgp">
   			<p>Sodium Hyaluronate 1% Solution</p>
   			<p> (25kg Drum) </p>
   		</div>
    	<a class="button maxsales_but" href="">SHOW NOW</a>
   	</div>
   	</li>
</c:if>

<c:if test='${level eq 7}'>
<li class="swiper-slide">
 <div class="maxporsit">
   	<div class="maximg_ban">
   		<img src="${media.url}"/>
   	</div>
   	<div class="maxtite_ban">
   		<div class="maxmark-con">
			<h4>
				<span>sales</span>
				<span>up to</span>
			</h4>
			<h3>50</h3>
			<p><em class="">%</em><em>OFF</em></p>
		</div>
    	<p>Chondroitin Sulfate USP 90% (O.D.B) </p>
    	<span>By CPC And 72% (As Is) / By E-HPLC (25kg Drum) / By Greentech</span>
    	<a class="button maxsales_but" href="">SHOW NOW</a>
   	</div>
  </div>
  </li>
  </c:if>
  
  <c:if test='${level eq 9}'>
  
    <li><a href="">
		<img class="maxhid" src="${media.url}"/><img class="maxhov" src="${media.url}"/>
		</a>
	</li>
  
  </c:if>
