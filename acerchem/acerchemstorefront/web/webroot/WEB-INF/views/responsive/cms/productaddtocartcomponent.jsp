<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<spring:htmlEscape defaultHtmlEscape="true" />


<div class="specnum">
<div class="spec">
	<label>
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
	<label class="futday">
		<span class="label-title">Future days</span>	
		<div class="selbox">
			<input type="hidden" value="" name="futday" alt="Please Select nation" id="futday">
			<span class="pitch"></span>
			<ul class="select">
				<c:forEach items="${product.stock.futureStock}" var="data" varStatus="vs">
	 	
			<li data-val="${data.futureStockLevel}">${data.releaseDay}</li>
 				 
		</c:forEach>

			</ul>
		</div>	
	</label>						
</div>
<div class="invernum">
	<span class="label-title inventory">Inventory:<i>${product.stock.stockLevel}</i> <span class="spot">(<em>${product.stock.stockLevel}</em>)</span></span>	

		<label>
			<input type="checkbox" name="Keep">
			<span class="checkbox">Display future inventory</span>
		</label>

		
	</div>
	
	<div class="delivery flex-wrap">
		<span class="label-title">Delivery to &nbsp &nbsp &nbsp &nbsp Warehouse</span>
		<div class="flex">
			<select>
			 <c:forEach items="${countrys}" var="data" varStatus="vs">
			  <option value ="${data.countryData.isocode}">${data.countryData.name}</option>
			 </c:forEach>
			</select>
		</div>
		 <div class="flex">
			<select>
			  <option value ="NY">New York</option>
			  <option value ="LA">Los Angeles</option>
			  <option value="CI">Chicago</option>
			</select>
		</div>
	</div>

	<div class="prod-sum">
		<div class="m-setnum">
			<span class="set sub">-</span>
			<input type="text" class="" name="pdpAddtoCartInput" class="set" value="1"  id="pdpAddtoCartInput">
			<span class="set add">+</span>
		</div>
		<i class="delintro">Delivery<em>30</em>days</i>
	</div>
	
	 <%-- <div class="actions">
        <c:if test="${multiDimensionalProduct}" >
                <c:url value="${product.url}/orderForm" var="productOrderFormUrl"/>
                <a href="${productOrderFormUrl}" class="btn btn-default btn-block btn-icon js-add-to-cart glyphicon-list-alt">
                    <spring:theme code="order.form" />
                </a>
        </c:if>
        <action:actions element="div"  parentComponent="${component}"/>
    </div>
	 --%>

</div>

