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
package com.wl4g.devops.iam.captcha.jigsaw;

import java.io.Serializable;

/**
 * Apply jigsaw image model
 * 
 * @author Wangl.sir
 * @version v1.0 2019年8月30日
 * @since
 */
public class ApplyJigsawImgModel implements Serializable {
	private static final long serialVersionUID = 4975604164412626949L;

	private String applyUuid;
	private int y;
	private String primaryImgUrl;
	private String blockImgUrl;

	public String getApplyUuid() {
		return applyUuid;
	}

	public void setApplyUuid(String applyUuid) {
		this.applyUuid = applyUuid;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getPrimaryImgUrl() {
		return primaryImgUrl;
	}

	public void setPrimaryImgUrl(String primaryUrl) {
		this.primaryImgUrl = primaryUrl;
	}

	public String getBlockImgUrl() {
		return blockImgUrl;
	}

	public void setBlockImgUrl(String blockUrl) {
		this.blockImgUrl = blockUrl;
	}

}
