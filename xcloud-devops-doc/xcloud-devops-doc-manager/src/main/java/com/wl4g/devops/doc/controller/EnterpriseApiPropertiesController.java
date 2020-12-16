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
package com.wl4g.devops.doc.controller;

import com.wl4g.components.common.web.rest.RespBase;
import com.wl4g.components.core.web.BaseController;
import com.wl4g.components.core.bean.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import static org.springframework.web.bind.annotation.RequestMethod.*;


import com.wl4g.devops.common.bean.doc.EnterpriseApiProperties;
import com.wl4g.devops.doc.service.EnterpriseApiPropertiesService;

/**
* {@link EnterpriseApiProperties}
*
* @author root
* @version 0.0.1-SNAPSHOT
* @Date 
* @since v1.0
*/
@RestController
@RequestMapping("/enterpriseapiproperties")
public class EnterpriseApiPropertiesController extends BaseController {

    @Autowired
    private EnterpriseApiPropertiesService enterpriseApiPropertiesService;

    @RequestMapping(value = "/list", method = { GET })
    public RespBase<PageModel<EnterpriseApiProperties>> list(PageModel<EnterpriseApiProperties> pm, EnterpriseApiProperties enterpriseApiProperties) {
        RespBase<PageModel<EnterpriseApiProperties>> resp = RespBase.create();
        resp.setData(enterpriseApiPropertiesService.page(pm, enterpriseApiProperties));
        return resp;
    }

    @RequestMapping(value = "/save", method = { POST, PUT })
    public RespBase<?> save(@RequestBody EnterpriseApiProperties enterpriseApiProperties) {
        RespBase<Object> resp = RespBase.create();
        enterpriseApiPropertiesService.save(enterpriseApiProperties);
        return resp;
    }

    @RequestMapping(value = "/detail", method = { GET })
    public RespBase<EnterpriseApiProperties> detail(@RequestParam(required = true) Long id) {
        RespBase<EnterpriseApiProperties> resp = RespBase.create();
        resp.setData(enterpriseApiPropertiesService.detail(id));
        return resp;
    }

    @RequestMapping(value = "/del", method = { POST, DELETE })
    public RespBase<?> del(@RequestParam(required = true) Long id) {
        RespBase<Object> resp = RespBase.create();
        enterpriseApiPropertiesService.del(id);
        return resp;
    }

}