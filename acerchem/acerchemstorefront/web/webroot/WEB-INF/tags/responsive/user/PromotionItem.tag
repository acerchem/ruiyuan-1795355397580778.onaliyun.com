<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>

<div class="maxon_salesul">
                
	<div class="title">
		Promotion Item
		<div class="maxpagin">				
		</div>	
	</div>
	<div class="banner_btn"> 
   		<span class="arrow-left"></span> 
		<span class="arrow-right"></span>
    </div>
    
    <div class="slide-wrap">
	    <div class="slide-item both">
	    	<div class="g-table product-table">
                <c:forEach items="${promotionItem}" var="pro">
                	<c:url value="${pro.url}" var="productUrl"/>
                	<div class="item">	    		
			    		<div class="img" style="height:120px;">
			    			<a href="${productUrl}">
			    				<img src="${pro.images.get(0).url}" alt="">
			    		  	</a>
			    		</div>
			    		<div class="maxtext">
				    		<p style="height:38px;line-height:20px;">
				    			${fn:escapeXml(pro.name)} (${pro.netWeight}kg ${pro.packageType})
				    		</p>
				    		<span class="price" style="padding-top: 0px; ">${pro.price.formattedValue}</span>
				    		<span class="old-price">${pro.promotionPrice.formattedValue}</span>
				    	</div>
			    	</div>
                </c:forEach>  
	    	</div>
		</div>
    </div>
</div>
<script type="text/javascript">
maxon_salesul('.maxon_salesul .slide-wrap')
</script>