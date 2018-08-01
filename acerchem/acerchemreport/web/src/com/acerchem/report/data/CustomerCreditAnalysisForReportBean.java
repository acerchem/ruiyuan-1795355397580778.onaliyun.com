package com.acerchem.report.data;

public class CustomerCreditAnalysisForReportBean {

	private String customerName;
	private Double lineOfCredit;
	private Double lineOfUsedCredit;
	private Double lineOfResedueCredit;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(final String customerName) {
		this.customerName = customerName;
	}
	public Double getLineOfCredit() {
		return lineOfCredit;
	}
	public void setLineOfCredit(final Double lineOfCredit) {
		this.lineOfCredit = lineOfCredit;
	}
	public Double getLineOfUsedCredit() {
		return lineOfUsedCredit;
	}
	public void setLineOfUsedCredit(final Double lineOfUsedCredit) {
		this.lineOfUsedCredit = lineOfUsedCredit;
	}
	public Double getLineOfResedueCredit() {
		return lineOfResedueCredit;
	}
	public void setLineOfResedueCredit(final Double lineOfResedueCredit) {
		this.lineOfResedueCredit = lineOfResedueCredit;
	}
	@Override
	public int hashCode() {
//		final String lineOfCreditStr = String.valueOf(lineOfCredit);
//		final String hc = customerName + "-" +lineOfCreditStr;
		return customerName.hashCode();
	}
	@Override
	public boolean equals(final Object obj) {
		final CustomerCreditAnalysisForReportBean bean = (CustomerCreditAnalysisForReportBean)obj;
		return customerName.equals(bean.getCustomerName()) ;
	}
	
	
	
}
