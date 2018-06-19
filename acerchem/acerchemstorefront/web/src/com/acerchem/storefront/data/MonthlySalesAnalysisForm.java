package com.acerchem.storefront.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MonthlySalesAnalysisForm  implements Serializable{
	private String area;
	private String year;
	public String getArea() {
		return area;
	}
	public void setArea(final String area) {
		this.area = area;
	}
	public String getYear() {
		return year;
	}
	public void setYear(final String year) {
		this.year = year;
	}
	
	

}
