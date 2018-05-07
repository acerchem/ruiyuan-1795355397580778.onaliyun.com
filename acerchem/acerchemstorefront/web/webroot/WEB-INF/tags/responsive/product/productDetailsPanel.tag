<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<div class="g-cont prod-cont">
			<!-- left -->
			<div class="g-cell">
				<!-- slide -->
				<div class="m-prodslide">
					<div class="minwrap">
						<div class="flip"> 
				    		<span class="up"></span> 
							<span class="down"></span>
					    </div>
						<div class="minimg">
						</div>
					</div>
					<div class="slidewrap">
						<!-- <div class="maxmark">
							<h3>10</h3>
			    			<p><em class="">%</em><em>OFF</em></p>
			    		</div> -->
						<ul class="slide-item">
							 <c:forEach items="${galleryImages}" var="container" varStatus="varStatus">
							<li class="item"><img src="${container.product.url}"  alt=""></li>
							
							</c:forEach>
						</ul>
					</div>						
				</div>					
				<!-- s end -->
				<!-- base -->
				<div class="prodbase">
					<div class="g-title">
						<p>${fn:escapeXml(product.name)}</p>
						<div class="min">
							<span class="mintitle">${ycommerce:sanitizeHTML(product.summary)}</span>
							<span class="sku">${fn:escapeXml(product.code)}</span>
						</div>
					</div>
					<div class="priceset">
						<span class="price"><format:fromPrice priceData="${product.price}"/></span>
						<span class="old-price"><format:fromPrice priceData="${product.promotionPrice}"/></span>
					</div>
					
					<div class="Summary">
						<c:forEach items="${product.potentialPromotions}" var="promotion"  varStatus="id"  >
						    <c:if test="${id.index==0}">	
						    <c:if test="${promotion.firedMessages ne null}"> 
							    <c:forEach items="${promotion.firedMessages}" var="messages" >
							    <c:set value="${ fn:split(messages, ',') }" var="arr"  />
							     <span>
								    <c:forEach items="${arr}" var="message" varStatus="i" >
								      <c:if test="${i.index==0}">	
								          <i>${message}% off</i>
								      </c:if>
								      
								        <c:if test="${i.index==1}">	
								      From minQuantity ${message}
								      </c:if>
								        <c:if test="${i.index==2}">	
								       to maxQuantity ${message}
								      </c:if>
								    
								    </c:forEach>
							    
							        </span>
							    </c:forEach>
							    </c:if>
							</c:if>
						</c:forEach>
					</div>
					
					<div class="specnum">
						 <div class="spec">
						 <!-- <label>
								<span class="label-title">Specifications</span>	
								<div class="selbox">
									<input type="hidden" class="required" value="" name="spec" alt="Please Select nation">
									<span class="pitch"></span>
									<ul class="select">
										<li data-val="25">25kg</li>
										<li data-val="50">50kg</li>
										<li data-val="75">75kg</li>						
									</ul>
								</div>	
							</label>
							 -->
							 
					  <select id="futureAvailableDateId" style="display: none;">
						<c:forEach items="${countrys}" var="data"  varStatus="id"  >
						<option value ="${data.storeId}">${data.futureAvailableDate}</option>
						
                        </c:forEach>
                        </select> 
                        
                        <select id="futureInventoryId" style="display: none;">
						<c:forEach items="${countrys}" var="data"  varStatus="id"  >
						<option value ="${data.storeId}">${data.futureInventory}</option>
						
                        </c:forEach>
                        </select> 
                        
							<c:forEach items="${countrys}" var="data"  varStatus="id"  >
						<c:if test="${id.index==0}">	
							<label class="futday">
								<span class="label-title">Future days</span>	
								<div class="selbox" id="selectId">
									<input type="hidden" value="" name="futday" alt="Please Select nation" id="futday">
									<span class="pitch"></span>
									<ul class="select" >
										<li data-val="${data.futureInventory}">${data.futureAvailableDate}</li>
														
									</ul>
								</div>	
							</label>	
							</c:if>	
                        </c:forEach>					
						</div> 
						
						<div class="invernum">
						
						<select id="inventoryId" style="display: none;">
						<c:forEach items="${countrys}" var="data"  varStatus="id"  >
						<option value ="${data.storeId}">${data.inventory}</option>
						
                        </c:forEach>
                        </select> 
                        
                       <c:forEach items="${countrys}" var="data"  varStatus="id"  >
						<c:if test="${id.index==0}">
							<span class="label-title inventory">Inventory:<i>${data.inventory}</i> <span class="spot">(<em>${data.inventory}</em>)</span></span>
							</c:if>	
                        </c:forEach>
							
							<label>
								<input type="checkbox" name="Keep" id="checkfutureId">
								<span class="checkbox">Display future inventory</span>
							</label>							
						</div>
					 
						<div class="delivery flex-wrap">
							<span class="label-title">Delivery From:</span>
							<div class="flex">
							
								<select id="storeMulId">
								<c:forEach items="${countrys}" var="data"  >
								<option value ="${data.storeId}">${data.storeName}</option>
								    </c:forEach>
								</select>
								
							</div>
							
							<c:forEach items="${countrys}" var="data"  varStatus="id"  >
							<c:if test="${id.index==0}">
							   <input type="hidden" value="${data.storeId}" name="storeHidId"  id="storeHidId">
					        </c:if>	
	                       </c:forEach>
							
							
						
						</div>
						
						<div class="delivery flex-wrap">
						
						<select id="countryId" style="display: none;">
						<c:forEach items="${countrys}" var="data">
						<option value ="${data.storeId}">${data.countryListString}</option>
						
                        </c:forEach>
                        </select> 
						
						<c:forEach items="${countrys}" var="data"  varStatus="id"  >
							<c:if test="${id.index==0}">
							    <c:forEach items="${data.countryDataList}" var="country"  varStatus="vs"  >
							    
							      <c:set var="myVar" value="${stat.first ? '' : myVar} ${country.name}" />
							      
							    </c:forEach>
							    
							    
					        </c:if>	
	                       </c:forEach>
                        
							<span class="label-title">Delivery area: <em>${myVar}</em></span>
						
						</div>
						

						<div class="prod-sum">
							<div class="m-setnum">
							<span class="set sub">-</span>
                              <input type="text" name="pdnum" class="set" value="1">								
								<span class="set add">+</span>
								
							</div>
							<%-- <c:forEach var="${countrys}" var="data1" begin="0" end="1"> --%>
						<c:forEach items="${countrys}" var="data"  varStatus="id"  >
						<c:if test="${id.index==0}">
							    <i class="delintro"><em>Delivery within &nbsp ${data.avaReleaseDay} &nbsp days</em></i>
						</c:if>	
                        </c:forEach>
                        <input type="hidden" value="" name="avaReleaseDay"  id="avaReleaseDay">
                        <select id="avaReleaseDayId" style="display: none;">
						<c:forEach items="${countrys}" var="data"  varStatus="id"  >
						<option value ="${data.storeId}">${data.avaReleaseDay}</option>
						
                        </c:forEach>
                        </select> 
							
 						</div>

					</div>
			
					
					<cms:pageSlot position="AddToCart" var="component" >
						<cms:component component="${component}" />
					</cms:pageSlot>
					
					<!-- <div class="btn-set">
						<button class="btn btn-submit">Check Out</button>
						<button class="btn btn-cart">Add to Cart</button>							
					</div> 
 -->
				</div>
				<!-- b end -->
			</div>
			<!-- l end -->

			<!-- right -->
			<div class="g-cell product-right">
				<div class="g-table qclist">
					<div class="g-title">
							QC Documents
					</div>
					<ul class="donlist">
					
				 <%-- <c:if test="${fn:length(product.certificates.url) > 0}"> --%>
				 <c:if test="${product.certificatess ne null}"> 
					 <c:forEach items="${product.certificatess}" var="data"  varStatus="id"  >
					 
				        <li>						
							<a href="${data.url}" >${data.altText}</a>
						</li>
						
					 </c:forEach>
						
					</c:if>
					</ul>
					<div class="btn-set line-setbtn">       
					      <a class="click_pop btn btn-showlist" href="javascript:void(0)">Message Consultation</a>
					</div> 		
				</div>
				<div class="tableshare">
					<div class="title">Share with a Friend</div>
					<ul class="share-buttons"></ul>
				</div>
			</div>
			<!-- r end -->	
		</div>
		<!-- top end -->

		<div class="m-pagecard">
			<div class="card">
				<span class="now">Product Description</span>
				<!-- <span>Revews</span> -->
			</div>
		</div>
		<!-- down -->
<div class="g-cont prod-cont down-cont">
			<div class="g-proudtable">
				<div class="g-cell">					
					<!-- descr -->
					<div class="m-cardwrap descr">
						<!-- <div class="m-note">
							<div class="title">Note</div>
							<div class="text">
								These statements have not been evaluated by the Food and Drug Administration. This product is not intended to diagnose, treat, cure or prevent any disease. <br/>*All product pictures are for display purposes only, it may not reflect the exact product color or mesh size, please refer to spec sheet for details.
							</div>
						</div> -->
						<ul class="g-desclist">
					    	${product.summary}
							${product.description}
						</ul>
						<div class="g-descblock">
							<div class="title">Additional Information</div>
							<ul class="g-table">	
								<li>
									<span>Other Names</span>
									<span>${product.otherName}</span>
								</li>
								<li>
									<span>Quality Standard</span>
									<span>${product.specification}</span>
								</li>	
								<li>
									<span>CAS</span>
									<span>${product.CAS}</span>
								</li>
								<li>
									<span>Chemical Formula</span>
									<span>${product.chemicalInfo}</span>
								</li>
							</ul>
						</div>
						<div class="g-descblock">
						<div class="title">&nbsp &nbsp</div>
							
							<ul class="g-table">								
								
								
								<li>
									<span>Molecular Weight</span>
									<span>${product.formulaWeight}</span>
								</li>	
								<li>
									<span>Manufacturer</span>
									<c:if test="${not empty product.vendor}">
									<span>${product.vendor.name}</span>
									</c:if>
								</li>	
								
								<li>
									<span>Package Type</span>
									<span> ${product.netWeight}${product.unitName}/${product.packageType}</span>
								</li>	
											
							</ul>
						</div>
					</div>
					<!-- descr end -->
					<!-- revews -->
					<!-- <div class="m-cardwrap revews">
						<ul class="m-revlist">
							<li>
								<div class="party">
									<span>C ****</span>
									<span class="m-starlev" date-val="2"><i></i><em></em></span>
								</div>
								<div class="text">I appreciate the ease of use and the convenience of the documentation available and the immediate confirmation. I will continue to use this site.</div>
								<div class="date">2018/2/9</div>
							</li>

							<li>
								<div class="party">
									<span>C ****</span>
									<span class="m-starlev" date-val="0"><i></i><em></em></span>
								</div>
								<div class="text">I appreciate the ease of use and the convenience of the documentation available and the immediate confirmation. I will continue to use this site.</div>
								<div class="date">2018/2/9</div>
							</li>

							<li>
								<div class="party">
									<span>C ****</span>
									<span class="m-starlev" date-val="5"><i></i><em></em></span>
								</div>
								<div class="text">I appreciate the ease of use and the convenience of the documentation available and the immediate confirmation. I will continue to use this site.</div>
								<div class="date">2018/2/9</div>
							</li>



						</ul>
					</div> -->
					<!-- revews end -->
				</div>

				<!-- right -->
				<div class="g-cell product-right">
					<div class="g-table qclist">
					
					
					<cms:pageSlot position="SaleWellProductListSlot" var="component" >
					
						<cms:component component="${component}" />
					</cms:pageSlot>
						
					</div>
				</div>
				<!-- r edn -->
			</div>
			<!-- item -->
	<div class="maxon_salesul">
	
		
		<cms:pageSlot position="PromotionProductListSlot" var="component" >
		
			<cms:component component="${component}" />
		</cms:pageSlot>

   </div>


	<!-- item end -->
		</div>
		
		
<!-- <script type="text/javascript" src="/_ui/responsive/common/js/maxprod.js"></script> -->
<script type="text/javascript" >
    //send message
    $(document).ready(function () {
        $('.click_pop').click(function () {
        var proName="${product.name}".replace('%','%25').replace('+','%2B').replace('&','%26');
        var openUrl = ACC.config.encodedContextPath + "/account/add-support-ticket?productId=${product.code}&productName="+proName;//å¼¹åºçªå£çurl
         var iWidth=700; //å¼¹åºçªå£çå®½åº¦;
         var iHeight=700; //å¼¹åºçªå£çé«åº¦;
         var iTop = (window.screen.availHeight-30-iHeight)/2; //è·å¾çªå£çåç´ä½ç½®;
         var iLeft = (window.screen.availWidth-10-iWidth)/2; //è·å¾çªå£çæ°´å¹³ä½ç½®;
         window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+",scrollbars=yes,resizable=yes,toolbar=no,location=no, left="+iLeft); 
        });
    })
</script>
		
		

