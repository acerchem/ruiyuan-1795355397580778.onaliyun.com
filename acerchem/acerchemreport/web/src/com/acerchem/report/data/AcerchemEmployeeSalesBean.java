package com.acerchem.report.data;

import java.util.Map;

public class AcerchemEmployeeSalesBean {

	String queryMonth;
	Map<String,Double> employeeSales;
	public String getQueryMonth() {
		return queryMonth;
	}
	public void setQueryMonth(final String queryMonth) {
		this.queryMonth = queryMonth;
	}
	public Map<String, Double> getEmployeeSales() {
		return employeeSales;
	}
	public void setEmployeeSales(final Map<String, Double> employeeSales) {
		this.employeeSales = employeeSales;
	}
	
}
