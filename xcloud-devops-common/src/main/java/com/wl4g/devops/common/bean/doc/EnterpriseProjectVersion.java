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
package com.wl4g.devops.common.bean.doc;

import com.wl4g.components.core.bean.BaseBean;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * {@link EnterpriseProjectVersion}
 *
 * @author root
 * @version master
 * @Date 2020-11-25
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EnterpriseProjectVersion extends BaseBean {
	private static final long serialVersionUID = 502750392682994816L;

	/**
	 * 
	 */
	private Long projectId;

	/**
	 * 
	 */
	private String version;

	/**
	 * 组织编码
	 */
	private String organizationCode;

	public EnterpriseProjectVersion() {
	}

	public EnterpriseProjectVersion withProjectId(Long projectId) {
		setProjectId(projectId);
		return this;
	}

	public EnterpriseProjectVersion withVersion(String version) {
		setVersion(version);
		return this;
	}

	public EnterpriseProjectVersion withOrganizationCode(String organizationCode) {
		setOrganizationCode(organizationCode);
		return this;
	}
}