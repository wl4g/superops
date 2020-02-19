package com.wl4g.devops.doc.service.impl;

import com.github.pagehelper.PageHelper;
import com.wl4g.devops.common.bean.doc.FileChanges;
import com.wl4g.devops.common.bean.doc.Share;
import com.wl4g.devops.common.constants.DocDevOpsConstants;
import com.wl4g.devops.common.web.RespBase;
import com.wl4g.devops.dao.doc.FileChangesDao;
import com.wl4g.devops.dao.doc.ShareDao;
import com.wl4g.devops.doc.config.DocProperties;
import com.wl4g.devops.doc.service.ShareService;
import com.wl4g.devops.page.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.wl4g.devops.common.web.RespBase.RetCode.NOT_FOUND_ERR;
import static com.wl4g.devops.common.web.RespBase.RetCode.UNAUTHC;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

/**
 * @author vjay
 * @date 2020-02-19 16:23:00
 */
@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private ShareDao shareDao;

    @Autowired
    private FileChangesDao fileChangesDao;

    @Autowired
    private DocProperties docProperties;

    @Override
    public PageModel list(PageModel pm) {
        pm.page(PageHelper.startPage(pm.getPageNum(), pm.getPageSize(), true));
        pm.setRecords(shareDao.list());
        return pm;
    }

    @Override
    public void cancelShare(Integer id) {
        Share share = new Share();
        share.setId(id);
        share.setDelFlag(1);
        shareDao.updateByPrimaryKeySelective(share);
    }

    @Override
    public RespBase<?> rendering(String code, String passwd) {
        RespBase<Object> resp = RespBase.create();

        //for external
        Share share = shareDao.selectByShareCode(code);
        if(nonNull(share)) {
            if(System.currentTimeMillis()>=share.getExpireTime().getTime()){
                resp.setCode(NOT_FOUND_ERR);
                return resp;
            }
            if(nonNull(share.getShareType()) && share.getShareType()==1 && !equalsIgnoreCase(share.getPasswd(), passwd)){// need password but not match
                resp.setCode(UNAUTHC);
                return resp;
            }
            FileChanges lastByFileCode = fileChangesDao.selectLastByDocCode(share.getDocCode());
            resp.setData(parse(lastByFileCode.getContent()));
            return resp;
        }

        //for manager
        FileChanges lastByFileCode = fileChangesDao.selectLastByDocCode(code);
        if(nonNull(lastByFileCode)){
            resp.setData(parse(lastByFileCode.getContent()));
            return resp;
        }else{
            resp.setCode(NOT_FOUND_ERR);
            return resp;
        }

    }


    /**
     * parse base url
     * @param content
     * @return
     */
    private String parse(String content){
        content = content.replaceAll(DocDevOpsConstants.SHARE_BASE_URL,docProperties.getShareBaseUrl());
        content = content.replaceAll(DocDevOpsConstants.SHARE_BASE_URL_TRAN,docProperties.getShareBaseUrl());
        content = content.replaceAll(DocDevOpsConstants.DOC_BASE_URL,docProperties.getDocBaseUrl());
        content = content.replaceAll(DocDevOpsConstants.DOC_BASE_URL_TRAN,docProperties.getDocBaseUrl());
        return content;
    }


}
