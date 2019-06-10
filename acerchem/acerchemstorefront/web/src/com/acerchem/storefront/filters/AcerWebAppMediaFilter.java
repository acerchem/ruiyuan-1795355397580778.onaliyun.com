package com.acerchem.storefront.filters;

import de.hybris.platform.servicelayer.web.WebAppMediaFilter;

import javax.servlet.http.HttpServletRequest;


public class AcerWebAppMediaFilter extends WebAppMediaFilter
{
	@Override
	protected Iterable<String> getMediaContext(HttpServletRequest httpRequest) {
		String encodedMediaCtx = this.getLocalMediaWebUrlContextParam(httpRequest);
		encodedMediaCtx = encodedMediaCtx.replaceAll("\\?.*","");
		return this.isLegacyPrettyUrlSupport() ? this.createLegacyLocalMediaWebUrlContext(httpRequest) : this.createLocalMediawebUrlContext(encodedMediaCtx);
	}
}
