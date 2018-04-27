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
						<div class="maxmark">
							<h3>10</h3>
			    			<p><em class="">%</em><em>OFF</em></p>
			    		</div>
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
						<span><i>FREE</i>Buy 2 pieces for FREE Shipping;</span>
						<span><i>10% off</i>All goods Sales to 10% Off; </span>
						<span><i>40%</i>Buy 400 items  sales to 40% Off;</span>
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
					
				 <c:if test="${fn:length(product.certificates.url) > 0}">
						<li>						
							<a href="${product.certificates.url}" >${product.certificates.altText}</a>
						</li>
						<li>						
							<a href="${product.certificates.url}" >${product.certificates.altText}</a>
						</li>
						<li>						
							<a href="${product.certificates.url}" >${product.certificates.altText}</a>
						</li>
						<li>						
							<a href="${product.certificates.url}" >${product.certificates.altText}</a>
						</li>
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
				<span>Revews</span>
			</div>
		</div>
		<!-- down -->
<div class="g-cont prod-cont down-cont">
			<div class="g-proudtable">
				<div class="g-cell">					
					<!-- descr -->
					<div class="m-cardwrap descr">
						<div class="m-note">
							<div class="title">Note</div>
							<div class="text">
								These statements have not been evaluated by the Food and Drug Administration. This product is not intended to diagnose, treat, cure or prevent any disease. <br/>*All product pictures are for display purposes only, it may not reflect the exact product color or mesh size, please refer to spec sheet for details.
							</div>
						</div>
						<ul class="g-desclist">
							<li>50% protein, as know, protein contributes to the growth and maintenance of muscle mass</li>
							<li>Hemp protein is considered complete plant-based protein, including all the 21 amino acids, as well as all the 8 essential amino acids</li>
							<li>Easy digestible, duo to the content of albumin and edestin</li>
							<li>High in vitamins (complex B) & minerals (Potassium, Phosphorus, Magnesium, Iron, Zinc, Copper, Manganese). High in Omega 3 fatty acids</li>
							<li>High in vitamins (complex B) & minerals (Potassium, Phosphorus, Magnesium, Iron, Zinc, Copper, Manganese). High in Omega 3 fatty acids</li>
						</ul>
						<div class="g-descblock">
							<div class="title">Specs</div>
							<ul class="g-table">	
														
								<li>
									<span>Vendor</span>
									<c:if test="${not empty product.vendor}">
									<span>${product.vendor.name}</span>
									</c:if>
								</li>
								<li>
									<span>Certifications</span>
									<span>Organic,Kosher, ISO 22000</span>
								</li>	
								<li>
									<span>Annual Export Amount</span>
									<span>EURO 4 million</span>
								</li>
								
								<li>
									<span>Major Ingredients</span>
									<span>Hulled/Shelled hemp seeds ,Hemp seed protein powder ,Cold pressed hempseed oil</span>
								</li>	
								<li>
									<span>Plant Part Used</span>
									<span> Seed</span>
								</li>	
								<li>
									<span>Processing Method</span>
									<span>Drying</span>
								</li>			
							</ul>
						</div>
						<div class="g-descblock">
							<div class="title">Additional Information</div>
							<ul class="g-table">								
								<li>
									<span>SKU</span>
									<span>97801-20KG-BAG-CANAH</span>
								</li>
								<li>
									<span>CAS</span>
									<span>N/A</span>
								</li>	
								<li>
									<span>Chemical Formula</span>
									<span>N/A</span>
								</li>	
								<li>
									<span>Solubility</span>
									<span>Insoluble in water</span>
								</li>
								<li>
									<span>Package Type</span>
									<span>110 : 20 kg Bag</span>
								</li>	
								<li>
									<span>Country Of Origin	</span>
									<span> Romania</span>
								</li>	
											
							</ul>
						</div>
					</div>
					<!-- descr end -->
					<!-- revews -->
					<div class="m-cardwrap revews">
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
					</div>
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
        var openUrl = ACC.config.encodedContextPath + "/account/add-support-ticket?productId=${product.code}&productName="+proName;//弹出窗口的url
         var iWidth=700; //弹出窗口的宽度;
         var iHeight=700; //弹出窗口的高度;
         var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
         var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
         window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+",scrollbars=yes,resizable=yes,toolbar=no,location=no, left="+iLeft); 
        });
    })
</script>
		
		

