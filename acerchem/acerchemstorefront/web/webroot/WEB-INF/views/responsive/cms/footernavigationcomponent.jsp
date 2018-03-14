<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/responsive/common/footer"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${component.visible}">
	<div class="footer maxleft">
			<div class="container">
				<div class="footermin maxleft">
					<div class="left">
						<div class="maxadd">
							<h3>Contact us</h3>
							<ul>
								<li><i class="maxadd"></i><span>Baglan Bay Innovation Centre,Baglan Energy Park,
Port Talbot,Wales,U.K.SA12 7AX.   </span></li>
								<li><i class="maxfax"></i><span>FAX:+86(0)2131358998</span></li>
								<li><i class="maxphone"></i><span>Phone: +86(0)2131358882</span></li>
								<li><i class="maxemail"></i><span>Email: service@acerchem.com</span></li>
							</ul>
						</div>
						<div class="maxfootnav">
							<h3>Customer service</h3>
							<ul>
								<li><a href="#">FAQ</a></li>
								<li><a href="#">Privacy & Security Policy</a></li>
								<li><a href="#">Shipping & Return Policy</a></li>
								<li><a href="#">Terms & Condition</a></li>
							</ul>
						</div>
						<div class="maxfootnav">
							<h3>Company</h3>
							<ul>
								<li><a href="#">About</a></li>
								<li><a href="#">Help</a></li>
								<li><a href="#">Contact</a></li>
								<li><a href="#">Terms</a></li>
								<li><a href="#">Privacy</a></li>
							</ul>
						</div>
						<div class="maxfootnav">
							<h3>Connect</h3>
							<ul>
								<li><a href="#">Animal Nutrition</a></li>
								<li><a href="#">Antioxidants</a></li>
								<li><a href="#">B-Complex</a></li>
								<li><a href="#">Carotenoids</a></li>
								<li><a href="#">Cognitive</a></li>
							</ul>
						</div>
					</div>
					<div class="right">
						<ul class="share-buttons">
						  <li><a class="linkedin" href="http://www.linkedin.com/shareArticle?mini=true&url=&title=&summary=&source=" target="_blank" title="Share on LinkedIn" onclick="window.open('http://www.linkedin.com/shareArticle?mini=true&url=' + encodeURIComponent(document.URL) + '&title=' +  encodeURIComponent(document.title)); return false;"></a></li>
						  <li><a class="tumblr" href="http://www.tumblr.com/share?v=3&u=&quote=&s=" target="_blank" title="Post to Tumblr" onclick="window.open('http://www.tumblr.com/share?v=3&u=' + encodeURIComponent(document.URL) + '&quote=' +  encodeURIComponent(document.title)); return false;"></a></li>
						  <li><a class="facebook" href="https://www.facebook.com/sharer/sharer.php?u=&quote=" title="Share on Facebook" target="_blank" onclick="window.open('https://www.facebook.com/sharer/sharer.php?u=' + encodeURIComponent(document.URL) + '&quote=' + encodeURIComponentdocument(.URL)); return false;"></a></li>
						  <li><a class="twitter" href="https://twitter.com/intent/tweet?source=&text=:%20" target="_blank" title="Tweet" onclick="window.open('https://twitter.com/intent/tweet?text=' + encodeURIComponent(document.title) + ':%20'  + encodeURIComponent(document.URL)); return false;"></a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="gotop"></div>
			<div class="footerbot maxleft">
				<div class="container">
				© 1996-2018, Acerchem.com, Inc. or its affiliates
				</div>
			</div>
		</div>
</c:if>