// Generated by XCloud DoPaaS for Codegen, refer: http://dts.devops.wl4g.com

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

package com.wl4g.dopaas.udm.service.impl;

import static com.wl4g.component.common.lang.Assert2.notNullOf;
import static java.util.Objects.isNull;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wl4g.component.core.bean.BaseBean;
import com.wl4g.component.core.page.PageHolder;
import com.wl4g.dopaas.common.bean.udm.EnterpriseRepositoryVersion;
import com.wl4g.dopaas.udm.data.EnterpriseRepositoryVersionDao;
import com.wl4g.dopaas.udm.service.EnterpriseRepositoryVersionService;
import com.wl4g.dopaas.udm.service.dto.EnterpriseRepositoryVersionPageRequest;

/**
 * service implements of {@link EnterpriseRepositoryVersion}
 *
 * @author root
 * @version 0.0.1-SNAPSHOT
 * @Date
 * @since v1.0
 */
@Service
public class EnterpriseRepositoryVersionServiceImpl implements EnterpriseRepositoryVersionService {

	@Autowired
	private EnterpriseRepositoryVersionDao enterpriseRepositoryVersionDao;

	@Override
	public PageHolder<EnterpriseRepositoryVersion> page(
			EnterpriseRepositoryVersionPageRequest enterpriseRepositoryVersionPageRequest) {
		PageHolder<EnterpriseRepositoryVersion> pm = enterpriseRepositoryVersionPageRequest.getPm();
		pm.useCount().bindPage();
		EnterpriseRepositoryVersion enterpriseRepositoryVersion = new EnterpriseRepositoryVersion();
		BeanUtils.copyProperties(enterpriseRepositoryVersionPageRequest, enterpriseRepositoryVersion);
		pm.setRecords(enterpriseRepositoryVersionDao.list(enterpriseRepositoryVersion));
		return pm;
	}

	@Override
	public List<EnterpriseRepositoryVersion> getVersionsByRepositoryId(Long repositoryId) {
		return enterpriseRepositoryVersionDao.getVersionsByRepositoryId(repositoryId);
	}

	@Override
	public int save(EnterpriseRepositoryVersion enterpriseRepositoryVersion) {
		if (isNull(enterpriseRepositoryVersion.getId())) {
			enterpriseRepositoryVersion.preInsert();
			return enterpriseRepositoryVersionDao.insertSelective(enterpriseRepositoryVersion);
		} else {
			enterpriseRepositoryVersion.preUpdate();
			return enterpriseRepositoryVersionDao.updateByPrimaryKeySelective(enterpriseRepositoryVersion);
		}
	}

	@Override
	public EnterpriseRepositoryVersion detail(Long id) {
		notNullOf(id, "id");
		return enterpriseRepositoryVersionDao.selectByPrimaryKey(id);
	}

	@Override
	public int del(Long id) {
		notNullOf(id, "id");
		EnterpriseRepositoryVersion enterpriseRepositoryVersion = new EnterpriseRepositoryVersion();
		enterpriseRepositoryVersion.setId(id);
		enterpriseRepositoryVersion.setDelFlag(BaseBean.DEL_FLAG_DELETE);
		return enterpriseRepositoryVersionDao.updateByPrimaryKeySelective(enterpriseRepositoryVersion);
	}

}