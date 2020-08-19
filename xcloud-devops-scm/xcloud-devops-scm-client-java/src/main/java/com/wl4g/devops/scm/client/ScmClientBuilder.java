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
package com.wl4g.devops.scm.client;

import static java.util.Objects.nonNull;

import com.wl4g.devops.scm.client.config.ConfCommType;
import com.wl4g.devops.scm.client.config.ScmClientProperties;
import com.wl4g.devops.scm.client.event.ScmEventListener;

/**
 * {@link ScmClientBuilder}
 *
 * @author Wangl.sir <wanglsir@gmail.com, 983708408@qq.com>
 * @version v1.0 2020-08-19
 * @since
 */
@SuppressWarnings("serial")
public class ScmClientBuilder extends ScmClientProperties<ScmClientBuilder> {

	/** Configuration refreshing internal comm protocol type. */
	private ConfCommType commType = ConfCommType.HLP;

	/** Configuration refreshing {@link ScmEventListener} */
	private ScmEventListener[] listeners;

	/**
	 * New create {@link ScmClientBuilder} instance
	 * 
	 * @return
	 */
	public static ScmClientBuilder newBuilder() {
		return new ScmClientBuilder();
	}

	public ScmClientBuilder withCommType(ConfCommType commType) {
		if (nonNull(commType)) {
			this.commType = commType;
		}
		return this;
	}

	public ScmClientBuilder withListeners(ScmEventListener... listeners) {
		if (nonNull(listeners)) {
			this.listeners = listeners;
		}
		return this;
	}

	/**
	 * Build {@link ScmClient} with builder configuration.
	 * 
	 * @return
	 */
	public ScmClient build() {
		switch (commType) {
		case HLP:
			return new HlpScmClient(this, listeners);
		case RPC:
			return new RpcScmClient(this, listeners);
		default:
			throw new Error("shouldn't be here");
		}
	}

}