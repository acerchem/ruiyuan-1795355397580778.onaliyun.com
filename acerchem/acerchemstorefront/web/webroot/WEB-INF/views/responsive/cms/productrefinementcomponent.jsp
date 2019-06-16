<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav" %>

<div class="product-facet-box">
       <div class="block-title">
              <strong><span>Shop By<i class="fa fa-angle-down" aria-hidden="true"></i></span></strong>
       </div>
       <div id="product-facet" class="hidden-sm hidden-xs product__facet js-product-facet">
              <nav:facetNavAppliedFilters pageData="${searchPageData}"/>
              <nav:facetNavRefinements pageData="${searchPageData}"/>
       </div>
</div>
<script type="text/javascript">
    jQuery(".product-facet-box").children('.block-title').click(function(){
        jQuery(this).siblings("#product-facet").slideToggle( "500", function() {
        });
    });
    jQuery(".product-facet-box .facet").each(function(){
        var _this = $(this);
        $(this).children('.facet__name',_this).click(function(){
            jQuery(this).siblings(".facet__values",_this).slideToggle( "500", function() {
            });
        });
    })
</script>