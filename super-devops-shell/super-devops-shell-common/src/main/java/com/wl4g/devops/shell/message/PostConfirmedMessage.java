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
package com.wl4g.devops.shell.message;

import static com.wl4g.devops.tool.common.lang.Assert2.notNullOf;

/**
 * Post confirmed message
 * 
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0 2020年1月4日
 * @since
 */
public class PostConfirmedMessage extends Message {
	private static final long serialVersionUID = -8574315246731906685L;

	/**
	 * Current confirmed state.
	 */
	final private Boolean confirmed;

	public PostConfirmedMessage(Boolean confirmed) {
		notNullOf(confirmed, "confirmed");
		this.confirmed = confirmed;
	}

	public Boolean getConfirmed() {
		return confirmed;
	}

	@Override
	public String toString() {
		return "PostConfirmedMessage [confirmed=" + confirmed + "]";
	}

}