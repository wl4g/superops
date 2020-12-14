// Generated by XCloud DevOps for Codegen, refer: http://dts.devops.wl4g.com

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

package com.wl4g.devops.doc.service.impl;

import com.github.pagehelper.PageHelper;
import com.wl4g.components.core.bean.BaseBean;
import com.wl4g.components.core.bean.model.PageModel;
import com.wl4g.devops.common.bean.doc.EnterpriseRepositoryVersion;
import com.wl4g.devops.doc.data.EnterpriseRepositoryVersionDao;
import com.wl4g.devops.doc.service.EnterpriseRepositoryVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.wl4g.components.common.lang.Assert2.notNullOf;
import static java.util.Objects.isNull;

/**
 *  service implements of {@link EnterpriseRepositoryVersion}
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
    public PageModel<EnterpriseRepositoryVersion> page(PageModel<EnterpriseRepositoryVersion> pm, EnterpriseRepositoryVersion enterpriseRepositoryVersion) {
        pm.page(PageHelper.startPage(pm.getPageNum(), pm.getPageSize(), true));
        pm.setRecords(enterpriseRepositoryVersionDao.list(enterpriseRepositoryVersion));
        return pm;
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
