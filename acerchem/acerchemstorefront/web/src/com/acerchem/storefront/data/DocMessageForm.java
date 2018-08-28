package com.acerchem.storefront.data;

import java.io.InputStream;
import java.util.Date;

public class DocMessageForm {

	private String title;
	private String author;
	private InputStream fileins;
	
	private String filename;
	private String mimeType;
	private Date creatTime;
	private String code;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(final String author) {
		this.author = author;
	}
	public InputStream getFileins() {
		return fileins;
	}
	public void setFileins(final InputStream fileins) {
		this.fileins = fileins;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(final String filename) {
		this.filename = filename;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(final String mimeType) {
		this.mimeType = mimeType;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(final Date creatTime) {
		this.creatTime = creatTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(final String code) {
		this.code = code;
	}
	
}
