ACC.track = {
	trackAddToCart: function (productCode, quantity, cartData)
	{
		if(window.mediator!=null) {
            window.mediator.publish('trackAddToCart', {
                productCode: productCode,
                quantity: quantity,
                cartData: cartData
            });
        }
	},
	trackRemoveFromCart: function(productCode, initialCartQuantity)
	{
        if(window.mediator!=null) {
            window.mediator.publish('trackRemoveFromCart', {
                productCode: productCode,
                initialCartQuantity: initialCartQuantity
            });
        }
	},

	trackUpdateCart: function(productCode, initialCartQuantity, newCartQuantity)
	{
        if(window.mediator!=null) {
            window.mediator.publish('trackUpdateCart', {
                productCode: productCode,
                initialCartQuantity: initialCartQuantity,
                newCartQuantity: newCartQuantity
            });
        }
	}
	

};