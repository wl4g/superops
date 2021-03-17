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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wl4g.component.core.bean.BaseBean;
import com.wl4g.component.core.page.PageHolder;
import com.wl4g.dopaas.common.bean.udm.EnterpriseTeam;
import com.wl4g.dopaas.udm.data.EnterpriseTeamDao;
import com.wl4g.dopaas.udm.service.EnterpriseTeamService;
import com.wl4g.dopaas.udm.service.dto.EnterpriseTeamPageRequest;

/**
 * service implements of {@link EnterpriseTeam}
 *
 * @author root
 * @version 0.0.1-SNAPSHOT
 * @Date
 * @since v1.0
 */
@Service
public class EnterpriseTeamServiceImpl implements EnterpriseTeamService {

	@Autowired
	private EnterpriseTeamDao enterpriseTeamDao;

	@Override
	public PageHolder<EnterpriseTeam> page(EnterpriseTeamPageRequest enterpriseTeamPageRequest) {
		PageHolder<EnterpriseTeam> pm = enterpriseTeamPageRequest.getPm();
		pm.useCount().bindPage();
		EnterpriseTeam enterpriseTeam = new EnterpriseTeam();
		BeanUtils.copyProperties(enterpriseTeamPageRequest, enterpriseTeam);
		pm.setRecords(enterpriseTeamDao.list(enterpriseTeam));
		return pm;
	}

	@Override
	public int save(EnterpriseTeam enterpriseTeam) {
		if (isNull(enterpriseTeam.getId())) {
			enterpriseTeam.preInsert();
			return enterpriseTeamDao.insertSelective(enterpriseTeam);
		} else {
			enterpriseTeam.preUpdate();
			return enterpriseTeamDao.updateByPrimaryKeySelective(enterpriseTeam);
		}
	}

	@Override
	public EnterpriseTeam detail(Long id) {
		notNullOf(id, "id");
		return enterpriseTeamDao.selectByPrimaryKey(id);
	}

	@Override
	public int del(Long id) {
		notNullOf(id, "id");
		EnterpriseTeam enterpriseTeam = new EnterpriseTeam();
		enterpriseTeam.setId(id);
		enterpriseTeam.setDelFlag(BaseBean.DEL_FLAG_DELETE);
		return enterpriseTeamDao.updateByPrimaryKeySelective(enterpriseTeam);
	}

}
