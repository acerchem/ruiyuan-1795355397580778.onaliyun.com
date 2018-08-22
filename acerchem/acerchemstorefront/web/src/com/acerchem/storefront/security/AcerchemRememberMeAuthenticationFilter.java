package com.acerchem.storefront.security;

import java.io.IOException;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

public class AcerchemRememberMeAuthenticationFilter extends RememberMeAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private RememberMeServices rememberMeServices;

	@Resource
	private UserService userService;

	public AcerchemRememberMeAuthenticationFilter(final AuthenticationManager authenticationManager,
			final RememberMeServices rememberMeServices) {
		super(authenticationManager, rememberMeServices);
	}

	@Override
	protected void onSuccessfulAuthentication(final HttpServletRequest httpservletrequest,
			final HttpServletResponse httpservletresponse, final Authentication authentication) {
		final UserModel user = userService.getCurrentUser();

		if (CollectionUtils.isNotEmpty(user.getGroups())) {
			final Optional optional = user.getGroups().stream().filter(group -> group.getUid().equals("reportgroup"))
					.findAny();
			if (optional.isPresent()) {

				throw new MyAuthenticationException("report user can't login via rememberMe!");
			}

		}
		super.onSuccessfulAuthentication(httpservletrequest, httpservletresponse, authentication);
	}

	public static class MyAuthenticationException extends AuthenticationException {

		public MyAuthenticationException(final String msg) {
			super(msg);
		}
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			final Authentication rememberMeAuth = rememberMeServices.autoLogin(request, response);
			if (rememberMeAuth != null) {

				final UserModel user = userService.getUserForUID(rememberMeAuth.getPrincipal().toString());
				if (CollectionUtils.isNotEmpty(user.getGroups())) {
					final Optional optional = user.getGroups().stream()
							.filter(group -> group.getUid().equals("reportgroup")).findAny();
					if (optional.isPresent()) {
						return;
					}
				}
			}

		}
		super.doFilter(req, res, chain);
	}

}
