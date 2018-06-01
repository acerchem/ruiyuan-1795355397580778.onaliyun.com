<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="level" value="${component.displayLevel}"/>
<c:url value="${fn:escapeXml(urlLink)}" var="encodedUrl" />

	<c:if test='${level eq 81}'>
			<div class="swiper-slide">
				<a href="${contextPath}${urlLink}"><img src="${media.url}?x-oss-process=image/resize,m_fixed,h_490,w_1519" /></a>
			</div>
		</c:if>
		
		<c:if test='${level eq 82}'>
			<div class="swiper-slide">
				
				<a href="${contextPath}${urlLink}"><img src="${media.url}?x-oss-process=image/resize,m_fixed,h_490,w_1519" /></a>
				</div>
		</c:if>
		
		<c:if test='${level eq 83}'>
			<div class="swiper-slide">
				
				<a href="${contextPath}${urlLink}"><img src="${media.url}?x-oss-process=image/resize,m_fixed,h_490,w_1519" /></a>
				</div>
		</c:if>

		<c:if test='${level eq 1}'>
			<li class="item1">
				<a href="${contextPath}${urlLink}">
				
				<!-- <div class="meansale"> -->
					<div class="text">
						<p>promo</p>
						<span>Sodium Hyaluronate 1% Solution (25kg Drum) </span>
						<!-- <label><i>15</i>%</label>
						<em><i>$58</i></em> -->
					</div>
					
					
					<img src="${media.url}?x-oss-process=image/resize,m_fixed,h_270,w_253"/>
				</a>
			</li>
			</c:if>
			<c:if test='${level eq 2}'>
			<li class="item2">
				<a href="${contextPath}${urlLink}">
				
					<div class="text">
						<p>Essential Nutrients for sports</p>
						<span>View the details</span>
					</div>
					<img src="${media.url}?x-oss-process=image/resize,m_fixed,h_270,w_253"/>
				</a>
			</li>
			</c:if>
			<c:if test='${level eq 3}'>
			<li class="item3">
				<a href="${contextPath}${urlLink}">
				
					<div class="text">
						<p>Factory examination record</p>
						<span>View the details</span>
					</div>
					<img src="${media.url}?x-oss-process=image/resize,m_fixed,h_270,w_253"/>
				</a>
			</li>
			</c:if>
			<c:if test='${level eq 4}'>
			<li class="item4">
				<a href="${contextPath}/article">
				<div class="textmessge">
					<p>Here are some qualification documents</p>
					<span>Documents</span>
				</div>
				<img src="${media.url}?x-oss-process=image/resize,m_fixed,h_270,w_253"/>
				</a>
			</li>
			</c:if>
			
			<c:if test='${level eq 5}'>
			<li class="item5 formtext">
				<div>
					<p>Let's contact you</p>
					<span>Let's contact you to get more information</span>
				</div>
				<form action="${contextPath}/account/add-support-ticket">
					<input type="text" id="email" placeholder="Your Emaill Address" class="text-inp">
					<input type="number" id="telephone" placeholder="Your Phone" class="text-inp">
				</form>
				<button class="btn click_pop">Consultation</button>
				<script>
                    $(document).ready(function () {
                        $('.click_pop').click(function () {
                        	var email=$("#email").val();
                        	var telephone=$("#telephone").val();
                        	
                        	var openUrl = ACC.config.encodedContextPath + '/account/add-support-ticket?email='+email+'&telephone='+telephone;//å¼¹åºçªå£çurl
                        	var iWidth=700; //å¼¹åºçªå£çå®½åº¦;
                        	var iHeight=700; //å¼¹åºçªå£çé«åº¦;
                        	var iTop = (window.screen.availHeight-30-iHeight)/2; //è·å¾çªå£çåç´ä½ç½®;
                        	var iLeft = (window.screen.availWidth-10-iWidth)/2; //è·å¾çªå£çæ°´å¹³ä½ç½®;
                        	window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+",scrollbars=yes,resizable=yes,toolbar=no,location=no, left="+iLeft); 
                        });
                    })
                </script>
			</li>
			</c:if>
			

	