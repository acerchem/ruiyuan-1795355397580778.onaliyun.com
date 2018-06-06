package com.acerchem.core.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

public class CommonConvertTools {
	private static final Logger LOG = Logger.getLogger(CommonConvertTools.class);
	
	private static Properties props=new Properties();
	public static boolean HtmlCovertPdf(final String content, final String outFile) {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(outFile);
			ITextRenderer renderer = new ITextRenderer();
			final ITextFontResolver fontResolver = renderer.getFontResolver();
			// 处理中文 fontResolver.addFont("simhei.ttf", BaseFont.IDENTITY_H,
			// BaseFont.NOT_EMBEDDED);
			// fontResolver.addFont("C:/Windows/Fonts/SIMSUN.TTC",
			// BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			// fontResolver.addFont("C:/Windows/Fonts/arialuni.ttf",
			// BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			// fontResolver.addFont("simsun.ttc", BaseFont.IDENTITY_H,
			// BaseFont.NOT_EMBEDDED);

			final BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
			final Font fontChinese = new Font(bfChinese, 12, Font.NORMAL, Color.GREEN);

			// final String url = new
			// File(inputFile).toURI().toURL().toString();
			// renderer.setDocument(url);

			renderer.setDocumentFromString(content);
			// 解决图片的相对路径问题
			// renderer.getSharedContext().setBaseURL(imagePath);
			renderer.layout();
			renderer.createPDF(os);

			// renderer.createPDF(os, false, 1);

			// final PdfWriter writer = renderer.getWriter();
			// final HeaderFooter footer = new HeaderFooter(new Phrase("page:",
			// fontChinese), true);
			// footer.setBorder(Rectangle.NO_BORDER);
			// footer.setAlignment(Element.ALIGN_CENTER);
			// writer.setHeader(footer);

			renderer.finishPDF();
			renderer = null;
			return true;
		} catch (final Exception e) {
			LOG.error(">>>>>>>>>>>>>>html file style error for generaiting pdf file.>>>>>>>>>>>>>>");
			e.printStackTrace();
			return false;
		} finally {
			try {
				os.close();
			} catch (final IOException e) {
				LOG.error(">>>>>>>>>>>>>>OutputStream close error>>>>>>>>>>>>>>");
				e.printStackTrace();
			}
		}
	}

	public static String getPdfName(final String subject) {
		return getPdfName(subject, false);
	}

	public static String getPdfName(final String subject, final Boolean isNeedOrderNumber) {

		boolean needOrderNr = false;
		if (isNeedOrderNumber != null) {
			needOrderNr = isNeedOrderNumber.booleanValue();
		}
		final String newSubject = subject.replace("：", ":");
		String name = "";
		final int pos = newSubject.indexOf(":");
		final String orderNumber = newSubject.substring(pos + 1);
		if (StringUtils.containsIgnoreCase(newSubject, "contract")) {
			name = "Contract";
		} else if (StringUtils.containsIgnoreCase(newSubject, "proforma")) {
			name = "Proforma Invoice";
		} else if (StringUtils.containsIgnoreCase(newSubject, "invoice")) {
			name = "Invoice and Packling List";
		} else if (StringUtils.containsIgnoreCase(newSubject, "delivery")) {
			name = "Delivery Note";
		} else if (StringUtils.containsIgnoreCase(newSubject, "release")) {
			name = "Release Note";
		} else {
			name = "Unknown";
		}

		final StringBuffer buffer = new StringBuffer(name);
		if (needOrderNr) {
			// 对于没有冒号的，无订单号，直接取业务名字
			if (pos > 0) {
				buffer.append("(").append(orderNumber).append(")");
			}
		}
		return buffer.toString();
	}

	public static String getFormatHtml(final String content) {
		final String html = content.replace("\r\n", "\n").replace("\n\n", "\n");
		final String arr[] = html.split("\n");
		final StringBuffer buffer = new StringBuffer();
		if (arr.length > 0) {
			for (final String s : arr) {
				buffer.append("<div>").append(s).append("</div>").append("<br/>");
			}

			return buffer.toString();
		} else {
			return content;
		}
	}

	public static String getSpecialProperties(final String key,final String defaultStr){
		if (props != null){
			return getPropertyFile("acerchemcore/messages/email-pdfEmailBody.properties").getProperty(key, defaultStr);
		}
		return defaultStr;
	}
	public static Properties getPropertyFile(final String propertyName){  
	     
	    try {  
	        props=PropertiesLoaderUtils.loadAllProperties(propertyName);   
	    } catch (final IOException e) {
	    	LOG.error(">>>>>>>>>>>>>>Cannot find property file!>>>>>>>>>>>>>>");
	        e.printStackTrace();  
	    }  
	    return props;  
	}  
	
}
