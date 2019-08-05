<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/responsive/user"%>

<c:url value="/" var="homeUrl"/>
<c:url value="/login" var="loginUrl"/>
<c:url value="/login/register" var="registerUrl"/>

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
				<c:if test="${product.discontinued}">
					<div class="prodbase">
						<div class="g-title">
							<p>${fn:escapeXml(product.name)}</p>
							<div class="min">
								<span class="sku">${fn:escapeXml(product.code)}</span>
								<span class="mintitle">Manufacturer:${product.manufacturer}</span>
							</div>
						</div>
						<div class="priceset">
							<br/>
						    Due to market change, we are unable to offer this product temporarily. <br/>
							If there is any urgent demand from you, please kindly contact our customer service and we will revert to you shortly.  <br/>
							Thank you for your understanding.<br/>
						</div>
					</div>
				</c:if>
				<c:if test="${!product.discontinued}">
					<div class="prodbase">
						<div class="g-title">
							<p>${fn:escapeXml(product.name)}</p>
							<div class="min">
								<span class="sku">${fn:escapeXml(product.code)}</span>
								<span class="mintitle">Manufacturer:${product.manufacturer}</span>
							</div>
						</div>
						<div class="priceset">
						    <c:if test="${not empty product.promotionPrice}">
							    <span class="price"><format:fromPrice priceData="${product.promotionPrice}"/></span>
							    <span class="old-price"><format:fromPrice priceData="${product.price}"/></span>
							</c:if>

							<c:if test="${empty product.promotionPrice}">
						    	<span class="price"><format:fromPrice priceData="${product.price}"/></span>
							</c:if>
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
									      order more than ${message}${product.packageType}s
									      </c:if>
									        <%-- <c:if test="${i.index==2}">
									       to maxQuantity ${message}
									      </c:if> --%>

									    </c:forEach>

								        </span>
								    </c:forEach>
								    </c:if>
								</c:if>
							</c:forEach>
						</div>
						<c:if test="${user.name ne 'Anonymous'}">
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
							<c:set var ="a" value="${data.futureInventory}"/>
							<c:set var="b" value="${product.netWeight}"/>
							<c:set var ="Total" value="${a*b}"/>
							<option value ="${data.storeId}">${data.futureInventory}&nbsp${product.packageType}${data.futureInventory>1?"s":""}&nbsp/&nbsp${Total}${product.unitName}${Total>1?"s":""}</option>

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
							<c:set var ="a" value="${data.inventory}"/>
							<c:set var="b" value="${product.netWeight}"/>
							<c:set var ="Total" value="${a*b}"/>
							<option value ="${data.storeId}">${data.inventory}&nbsp${product.packageType}${data.inventory>1?"s":""}&nbsp/&nbsp${Total}${product.unitName}${Total>1?"s":""}</option>

	                        </c:forEach>
	                        </select>

	                       <c:forEach items="${countrys}" var="data"  varStatus="id"  >
							<c:if test="${id.index==0}">
							<c:set var ="a" value="${data.inventory}"/>
							<c:set var="b" value="${product.netWeight}"/>
							<c:set var ="Total" value="${a*b}"/>
								<span class="label-title inventory">Inventory:<i id="inventory">${data.inventory}</i>&nbsp${product.packageType}${data.inventory>1?"s":""}&nbsp/&nbsp${Total}${product.unitName}${Total>1?"s":""} <span class="spot">(<em>${data.inventory}&nbsp${product.packageType}</em>)</span></span>
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
	                              <input type="text" id="pdnum" name="pdnum" class="set" value="${product.minOrderQuantity}" onblur="addNum()" <c:if test="${!product.purchasable}">readonly="readonly"</c:if>>
									<span class="set add">+</span>

								</div>
								<%-- <c:forEach var="${countrys}" var="data1" begin="0" end="1"> --%>
							<c:forEach items="${countrys}" var="data"  varStatus="id"  >
							<c:if test="${id.index==0}">

									<input disabled="disabled" hidden="hidden" id="myNetWeight" value="${product.netWeight}">

								    <i class="delint"><em>${product.packageType}&nbsp/&nbsp<span id="allmyNetWeight">${product.netWeight}</span>${product.unitName}</em></i>
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
							<!-- Minimum: ${product.minOrderQuantity} ${product.packageType} ${product.minOrderQuantity>1?"s":""} -->
							MoQ: <i id="minInventory">${product.minOrderQuantity}</i>&nbsp;${product.packageType} ${product.minOrderQuantity>1?"s":""}


							<cms:pageSlot position="AddToCart" var="component" >
								<cms:component component="${component}" />
							</cms:pageSlot>



							<!-- <div class="btn-set">
							<button class="btn btn-submit">Check Out</button>
							<button class="btn btn-cart">Add to Cart</button>
							</div>
							-->
						</c:if>
						<c:if test="${user.name eq 'Anonymous'}">
							<div style="background-color: #f3f3f3;margin-top: -60px;padding-top: 55px;padding-left: 40px; padding-bottom: 55px;">
								<h4 style="font-weight: bold"> LOG IN TO VIEW DETAIL.</h4><br/>
								Enjoy 24/7 service to all in-stock products.<br/>
								View competitive price and inventory.<br/>
								Looking for QC documents and quotation sheet.<br/>

								<div class="register__section">
									<a href="${loginUrl}" style="font-weight: normal;">
										<div style="display: inline-block;border-width: 1px;margin-top: 10px;padding: 10px;width: 150px!important;background-color: #0d4170!important;text-align: center;cursor: pointer;">
											<span style="color: #fff; font-size: 14px;">LOG IN</span>
										</div>
									</a>
									<a href="${registerUrl}" style="font-weight: normal;">
										<div style="display: inline-block;border-width: 1px;margin-top: 10px;padding: 10px;width: 150px!important;background-color: #0d4170!important;text-align: center;cursor: pointer;">
											<span style="color: #fff; font-size: 14px;">REGISTER</span>
										</div>
									</a>
								</div>
							</div>
						</c:if>

					</div>
					<!-- b end -->
				</c:if>
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
				 <c:if test="${product.certificatess ne null && user.name!='Anonymous'}">
					 <c:forEach items="${product.certificatess}" var="data"  varStatus="id"  >
					 
				        <li>						
							<a href="${data.url}" target="_blank" style="font-size: smaller;">${data.altText}</a>
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
										
				<!-- frank gu --> 
				<div class="supplier-info">
					<div class="s-info">														
								<ul class="s-table">								
									<li>
										<span>${product.supplierInfo00}</span>
										<span>${product.supplierInfo01}</span>
									</li>	
									<li>
										<span>${product.supplierInfo02}</span>
										<span>${product.supplierInfo03}</span>
									</li>										
									<li>
										<span>${product.supplierInfo04}</span>
										<span>${product.supplierInfo05}</span>
									</li>	
									<li>
										<span>${product.supplierInfo06}</span>
										<span>${product.supplierInfo07}</span>
									</li>	
									<li>
										<span>${product.supplierInfo08}</span>
										<span>${product.supplierInfo09}</span>
									</li>	
									<li>
										<span>${product.supplierInfo10}</span>
										<span>${product.supplierInfo11}</span>
									</li>	
									<li>
										<span>${product.supplierInfo12}</span>
										<span>${product.supplierInfo13}</span>
									</li>	
									<li>
										<span>${product.supplierInfo14}</span>
										<span>${product.supplierInfo15}</span>
									</li>	
									<li>
										<span>${product.supplierInfo16}</span>
										<span>${product.supplierInfo17}</span>
									</li>	
									<li>
										<span>${product.supplierInfo18}</span>
										<span>${product.supplierInfo19}</span>
									</li>	
										
								</ul>
							</div>
							</div>	
							<!-- end --> 				 
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
							${product.description}
						</ul>
						<div class="g-deswrap">	
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
									
									<li>
										<span>Molecular Weight</span>
										<span>${product.formulaWeight}</span>
									</li>	
									<li>
										<span>Manufacturer</span>
										<%-- <c:if test="${not empty product.vendor}">
										<span>${product.vendor.name}</span>
										</c:if> --%>
										<span>${product.manufacturer}</span>
									</li>	
									
									<li>
										<span>Package Type</span>
										<span> ${product.netWeight}${product.unitName}/${product.packageType}</span>
										
									</li>
									
								</ul>
							</div>
							
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
         var openUrl = ACC.config.encodedContextPath + "/account/add-support-ticket?productId=${product.code}&productName="+proName;
         var iWidth=700; 
         var iHeight=700; 
         var iTop = (window.screen.availHeight-30-iHeight)/2; 
         var iLeft = (window.screen.availWidth-10-iWidth)/2; 
         window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+",scrollbars=yes,resizable=yes,toolbar=no,location=no, left="+iLeft); 
        });
    })
    
function addNum()
{
    if(document.getElementById("inventory")==null){
    	//no inventory
    	return;
    }	
	var maxnum = document.getElementById("inventory").innerHTML;
	if(maxnum==null){
		maxnum=100000;
	}
	var minnum = document.getElementById("minInventory").innerHTML;
	if(minnum==null){
		minnum=1;
	}
	var avl = parseInt(document.getElementById('pdnum').value);
	if(avl>maxnum){
		maxalert("Cannot be larger than largest inventory!");
		$('#pdnum').val(maxnum);
		$('#qty').val(maxnum); 
	}else if(avl<minnum){
		maxalert("Cannot be less than less inventory!");
		$('#pdnum').val(minnum);
		$('#qty').val(minnum); 
	}
	else{
		$('#qty').val(avl); 
	}
}
</script>
		
		

