package com.acerchem.storefront.data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * Form for validating update field on cart page.
 */
public class AcerchemAddToCartForm
{
	@NotNull(message = "{basket.error.quantity.notNull}")
	@Min(value = 0, message = "{basket.error.quantity.invalid}")
	@Digits(fraction = 0, integer = 10, message = "{basket.error.quantity.invalid}")
	private long qty = 1L;

	private boolean isUseFutureStock;

	private String warehouseCode;

	private String storeId;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public boolean getIsUseFutureStock() {
		return isUseFutureStock;
	}

	public void setIsUseFutureStock(boolean isUseFutureStock) {
		this.isUseFutureStock = isUseFutureStock;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public void setQty(final long quantity)
	{
		this.qty = quantity;
	}

	public long getQty()
	{
		return qty;
	}
}
