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

import com.fasterxml.jackson.core.type.TypeReference;
import com.wl4g.components.common.serialize.JacksonUtils;
import com.wl4g.components.core.bean.BaseBean;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.Map;

/**
 * {@link EnterpriseApi}
 *
 * @author root
 * @version master
 * @Date 2020-11-25
 * @since v1.0
 */
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class EnterpriseApi extends BaseBean {
	private static final long serialVersionUID = 346724899682630400L;

	private String name;

	/**
	 * 
	 */
	private String apiVersion;

	/**
	 * 
	 */
	private String address;

	/**
	 * http,tcp
	 */
	private String protocolType;

	private String tags;

	/**
	 * 请求响应消息结构定义
	 */
	private String messageStruct;

	/**
	 * 
	 */
	private String remark;

	/**
	 * 组织编码
	 */
	private String organizationCode;

	public EnterpriseApi() {
	}

	public EnterpriseApi withVersionId(String apiVersion) {
		setApiVersion(apiVersion);
		return this;
	}

	public EnterpriseApi withAddress(String address) {
		setAddress(address);
		return this;
	}

	public EnterpriseApi withProtocolType(String protocolType) {
		setProtocolType(protocolType);
		return this;
	}

	public EnterpriseApi withMessageStruct(String messageStruct) {
		setMessageStruct(messageStruct);
		return this;
	}

	public EnterpriseApi withRemark(String remark) {
		setRemark(remark);
		return this;
	}

	public EnterpriseApi withOrganizationCode(String organizationCode) {
		setOrganizationCode(organizationCode);
		return this;
	}

	public static void main(String[] args) {
		Map<String, Object> stringObjectMap = JacksonUtils.parseJSON(new File("/Users/vjay/testjson"),
				new TypeReference<Map<String, Object>>() {
				});
		System.out.println(stringObjectMap);

	}
}