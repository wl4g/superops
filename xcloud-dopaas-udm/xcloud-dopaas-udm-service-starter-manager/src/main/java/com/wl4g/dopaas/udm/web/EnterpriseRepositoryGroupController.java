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
package com.wl4g.dopaas.udm.web;

import com.wl4g.component.common.web.rest.RespBase;
import com.wl4g.component.core.page.PageHolder;
import com.wl4g.component.core.web.BaseController;
import com.wl4g.dopaas.common.bean.udm.EnterpriseRepositoryGroup;
import com.wl4g.dopaas.udm.service.EnterpriseRepositoryGroupService;
import com.wl4g.dopaas.udm.service.dto.EnterpriseRepositoryGroupPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
* {@link EnterpriseRepositoryGroup}
*
* @author root
* @version 0.0.1-SNAPSHOT
* @Date 
* @since v1.0
*/
@RestController
@RequestMapping("/enterpriserepositorygroup")
public class EnterpriseRepositoryGroupController extends BaseController {

    @Autowired
    private EnterpriseRepositoryGroupService enterpriseRepositoryGroupService;

    @RequestMapping(value = "/list", method = { GET })
    public RespBase<PageHolder<EnterpriseRepositoryGroup>> list(EnterpriseRepositoryGroupPageRequest enterpriseRepositoryGroupPageRequest,PageHolder<EnterpriseRepositoryGroup> pm) {
        RespBase<PageHolder<EnterpriseRepositoryGroup>> resp = RespBase.create();
        enterpriseRepositoryGroupPageRequest.setPm(pm);
        resp.setData(enterpriseRepositoryGroupService.page(enterpriseRepositoryGroupPageRequest));
        return resp;
    }

    @RequestMapping(value = "/save", method = { POST, PUT })
    public RespBase<?> save(@RequestBody EnterpriseRepositoryGroup enterpriseRepositoryGroup) {
        RespBase<Object> resp = RespBase.create();
        enterpriseRepositoryGroupService.save(enterpriseRepositoryGroup);
        return resp;
    }

    @RequestMapping(value = "/detail", method = { GET })
    public RespBase<EnterpriseRepositoryGroup> detail(@RequestParam(required = true) Long id) {
        RespBase<EnterpriseRepositoryGroup> resp = RespBase.create();
        resp.setData(enterpriseRepositoryGroupService.detail(id));
        return resp;
    }

    @RequestMapping(value = "/del", method = { POST, DELETE })
    public RespBase<?> del(@RequestParam(required = true) Long id) {
        RespBase<Object> resp = RespBase.create();
        enterpriseRepositoryGroupService.del(id);
        return resp;
    }

}
