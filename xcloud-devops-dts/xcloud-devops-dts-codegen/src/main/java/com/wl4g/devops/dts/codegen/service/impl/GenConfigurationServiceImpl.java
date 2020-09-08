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
package com.wl4g.devops.dts.codegen.service.impl;

import com.wl4g.devops.dts.codegen.bean.GenTable;
import com.wl4g.devops.dts.codegen.core.GenerateManager;
import com.wl4g.devops.dts.codegen.service.GenConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link GenConfigurationServiceImpl}
 *
 * @author Wangl.sir <wanglsir@gmail.com, 983708408@qq.com>
 * @version v1.0 2020-09-07
 * @since
 */
public class GenConfigurationServiceImpl implements GenConfigurationService {

    @Autowired
    private GenerateManager generateManager;

    @Override
    public GenTable loadMetadata(Integer databaseId, String tableName) {
        return null;
    }

    @Override
    public GenTable detail(Integer tableId) {
        return null;
    }

    @Override
    public void saveGenConfig(GenTable genTable) {

    }

    @Override
    public void delete(Integer tableId) {

    }

    @Override
    public void generate(Integer tableId) {
        //TODO find table config from db

        /*AbstractParameter abstractParameter = new AbstractParameter();
        generateManager.execute(abstractParameter);*/

    }
}
