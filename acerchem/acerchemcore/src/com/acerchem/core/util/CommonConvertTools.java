package com.acerchem.core.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;

public class CommonConvertTools {
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

			//final String url = new File(inputFile).toURI().toURL().toString();
			//renderer.setDocument(url);

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
			e.printStackTrace();
			return false;
		} finally {
			try {
				os.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

}
