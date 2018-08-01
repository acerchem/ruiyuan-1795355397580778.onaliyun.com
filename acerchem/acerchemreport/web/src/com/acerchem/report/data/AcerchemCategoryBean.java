package com.acerchem.report.data;

public class AcerchemCategoryBean {

	private String code;
	private String name;
	public String getCode() {
		return code;
	}
	public void setCode(final String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	
	
	@Override
	public int hashCode() {
		final String in = code + name;
		return in.hashCode();
	}
	@Override
	public boolean equals(final Object obj) {
		
		final AcerchemCategoryBean cate = (AcerchemCategoryBean)obj;
		return code.equals(cate.getCode()) && name.equals(cate.getName());
	}
	
	
	
	
	
}
