package com.acerchem.storefront.interceptors.beforecontroller;

import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;

import de.hybris.platform.acceleratorstorefrontcommons.interceptors.BeforeControllerHandler;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

public class ReportUserCheckBeforeControllerHandler implements BeforeControllerHandler {
	private static final Logger LOG = Logger.getLogger(ReportUserCheckBeforeControllerHandler.class);

	@Resource(name = "userService")
	private UserService userService;

	@Override
	public boolean beforeController(final HttpServletRequest request, final HttpServletResponse response,
			final HandlerMethod handler) throws Exception {
		// final Authentication authentication =
		// SecurityContextHolder.getContext().getAuthentication();
		// final UserModel principal =
		// userService.getUserForUID(authentication.getPrincipal().toString());
		if (request.isSecure()) {
			final UserModel user = userService.getCurrentUser();
			if (CollectionUtils.isNotEmpty(user.getGroups())) {
				final Optional optional = user.getGroups().stream()
						.filter(group -> group.getUid().equals("reportgroup")).findAny();
				if (optional.isPresent()) {
					if (!request.getServletPath().contains("/login")) {
						if (!request.getServletPath().contains("/report")) {
							LOG.info("requestUrl:" + request.getServletPath());
							gotoLogout(request, response);
							return false;
						}
					}
				}

			}
		}

		return true;
	}

	private void gotoLogout(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		// Invalidate session and redirect to the root page logout
		request.getSession().invalidate();

		final String encodedRedirectUrl = response.encodeRedirectURL(request.getContextPath() + "/logout");
		response.sendRedirect(encodedRedirectUrl);
	}
}
