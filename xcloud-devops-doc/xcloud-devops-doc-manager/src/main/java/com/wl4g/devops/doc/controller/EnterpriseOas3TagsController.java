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
import com.wl4g.components.core.bean.model.PageModel;
import com.wl4g.components.core.web.BaseController;
import com.wl4g.devops.common.bean.doc.EnterpriseOas3Tags;
import com.wl4g.devops.doc.service.EnterpriseOas3TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
* {@link EnterpriseOas3Tags}
*
* @author root
* @version 0.0.1-SNAPSHOT
* @Date 
* @since v1.0
*/
@RestController
@RequestMapping("/enterpriseoas3tags")
public class EnterpriseOas3TagsController extends BaseController {

    @Autowired
    private EnterpriseOas3TagsService enterpriseOas3TagsService;

    @RequestMapping(value = "/list", method = { GET })
    public RespBase<PageModel<EnterpriseOas3Tags>> list(PageModel<EnterpriseOas3Tags> pm, EnterpriseOas3Tags enterpriseOas3Tags) {
        RespBase<PageModel<EnterpriseOas3Tags>> resp = RespBase.create();
        resp.setData(enterpriseOas3TagsService.page(pm, enterpriseOas3Tags));
        return resp;
    }

    @RequestMapping(value = "/save", method = { POST, PUT })
    public RespBase<?> save(@RequestBody EnterpriseOas3Tags enterpriseOas3Tags) {
        RespBase<Object> resp = RespBase.create();
        enterpriseOas3TagsService.save(enterpriseOas3Tags);
        return resp;
    }

    @RequestMapping(value = "/detail", method = { GET })
    public RespBase<EnterpriseOas3Tags> detail(@RequestParam(required = true) Long id) {
        RespBase<EnterpriseOas3Tags> resp = RespBase.create();
        resp.setData(enterpriseOas3TagsService.detail(id));
        return resp;
    }

    @RequestMapping(value = "/del", method = { POST, DELETE })
    public RespBase<?> del(@RequestParam(required = true) Long id) {
        RespBase<Object> resp = RespBase.create();
        enterpriseOas3TagsService.del(id);
        return resp;
    }

}
