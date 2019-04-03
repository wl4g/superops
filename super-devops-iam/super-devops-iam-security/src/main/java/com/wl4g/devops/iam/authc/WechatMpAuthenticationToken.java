package com.wl4g.devops.iam.authc;

import com.wl4g.devops.common.bean.iam.SocialAuthorizeInfo;

/**
 * Wechat authentication token
 * 
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0
 * @date 2018年11月19日
 * @since
 */
public class WechatMpAuthenticationToken extends Oauth2SnsAuthenticationToken {
	private static final long serialVersionUID = 8587329689973009598L;

	public WechatMpAuthenticationToken(String fromAppName, String redirectUrl, SocialAuthorizeInfo social, String host) {
		super(fromAppName, redirectUrl, social, host);
	}

}
