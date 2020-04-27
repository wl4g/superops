/*
 * Copyright 2017 ~ 2025 the original author or authors. <wanglsir@gmail.com, 983708408@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wl4g.devops.iam.common.security.xsrf.repository;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.util.WebUtils;

import com.wl4g.devops.iam.common.config.XsrfProperties;
import static com.wl4g.devops.iam.common.config.XsrfProperties.setHttpOnlyMethod;

/**
 * A {@link XsrfTokenRepository} that persists the CSRF token in a cookie named
 * "XSRF-TOKEN" and reads from the header "X-XSRF-TOKEN" following the
 * conventions of AngularJS. When using with AngularJS be sure to use
 * {@link #withHttpOnlyFalse()}.
 *
 * @author Wangl.sir <wanglsir@gmail.com, 983708408@qq.com>
 * @author Rob Winch
 * @version v1.0 2020年4月27日
 * @since
 */
public final class CookieXsrfTokenRepository implements XsrfTokenRepository {

	/**
	 * XsrfProperties
	 */
	@Autowired
	protected XsrfProperties xconfig;

	@Override
	public XsrfToken generateXToken(HttpServletRequest request) {
		return new DefaultXsrfToken(xconfig.getXsrfHeaderName(), xconfig.getXsrfParamName(), generateXsrfToken());
	}

	@Override
	public void saveXToken(XsrfToken xtoken, HttpServletRequest request, HttpServletResponse response) {
		String xtokenValue = xtoken == null ? "" : xtoken.getToken();
		Cookie cookie = new Cookie(xconfig.getXsrfCookieName(), xtokenValue);
		cookie.setSecure(request.isSecure());
		if (!isNull(xconfig.getCookiePath()) && !isBlank(xconfig.getCookiePath())) {
			cookie.setPath(xconfig.getCookiePath());
		} else {
			cookie.setPath(getRequestContext(request));
		}
		if (isNull(xtoken)) {
			cookie.setMaxAge(0);
		} else {
			cookie.setMaxAge(-1);
		}
		if (xconfig.isCookieHttpOnly() && setHttpOnlyMethod != null) {
			ReflectionUtils.invokeMethod(setHttpOnlyMethod, cookie, Boolean.TRUE);
		}

		response.addCookie(cookie);
	}

	@Override
	public XsrfToken getXToken(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, xconfig.getXsrfCookieName());
		if (isNull(cookie)) {
			return null;
		}
		String xtoken = cookie.getValue();
		if (isBlank(xtoken)) {
			return null;
		}
		return new DefaultXsrfToken(xconfig.getXsrfHeaderName(), xconfig.getXsrfParamName(), xtoken);
	}

	/**
	 * Generate xsrfToken
	 * 
	 * @return
	 */
	private String generateXsrfToken() {
		return UUID.randomUUID().toString();
	}

	private String getRequestContext(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		return contextPath.length() > 0 ? contextPath : "/";
	}

}