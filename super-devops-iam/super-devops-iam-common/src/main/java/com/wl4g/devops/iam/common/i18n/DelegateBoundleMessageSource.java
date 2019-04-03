/*
 * Copyright 2015 the original author or authors.
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
package com.wl4g.devops.iam.common.i18n;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.Assert;

import static com.wl4g.devops.common.constants.IAMDevOpsConstants.KEY_USE_LOCALE;

import java.util.Locale;

import com.wl4g.devops.iam.common.utils.SessionBindings;

/**
 * Delegate resource boundle message source.
 * 
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0 2019年3月24日
 * @since
 */
public class DelegateBoundleMessageSource extends ResourceBundleMessageSource {

	/**
	 * Message source accessor delegate.
	 */
	private MessageSourceAccessor accessor;

	public DelegateBoundleMessageSource() {
		this(DelegateBoundleMessageSource.class);
	}

	public DelegateBoundleMessageSource(Class<?> withClassPath) {
		this(getBasename(withClassPath));
	}

	public DelegateBoundleMessageSource(String... basenames) {
		Assert.isTrue((basenames != null && basenames.length > 0), "'basenames' cannot not be empty");
		super.setBasenames(basenames);
	}

	/**
	 * Get locale message
	 * 
	 * @param code
	 * @return
	 */
	public String getMessage(String code, Object... args) {
		Locale locale = (Locale) SessionBindings.getBindValue(KEY_USE_LOCALE);
		locale = locale == null ? Locale.getDefault() : locale;
		return getSource().getMessage(code, args, locale);
	}

	/**
	 * Get actual message source
	 * 
	 * @return
	 */
	public MessageSourceAccessor getSource() {
		if (accessor != null) {
			return accessor;
		}
		return (accessor = new MessageSourceAccessor(this));
	}

	/**
	 * Defualt i18n message base classpath prefix
	 * 
	 * @return
	 */
	private static String getBasename(Class<?> clazz) {
		Assert.notNull(clazz, "'clazz' cannot not be null");
		String path = clazz.getName();
		return path.substring(0, path.lastIndexOf(".")) + ".messages";
	}

}