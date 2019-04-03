package com.wl4g.devops.iam.authc;

import org.apache.shiro.util.Assert;
import org.hibernate.validator.constraints.NotBlank;

import com.wl4g.devops.common.bean.iam.SocialAuthorizeInfo;
import com.wl4g.devops.iam.common.authc.AbstractIamAuthenticationToken;

/**
 * Abstract SNS Oauth2 authentication token<br/>
 * 
 * <font color=red>Note: Social network login does not require login
 * account(principal)</font>
 * 
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0
 * @date 2018年11月19日
 * @since
 */
public abstract class Oauth2SnsAuthenticationToken extends AbstractIamAuthenticationToken {
	private static final long serialVersionUID = 8587329689973009598L;

	/**
	 * Social networking authorized information.
	 */
	final private SocialAuthorizeInfo social;

	/**
	 * Request client remote IP
	 */
	@NotBlank
	final private String host;

	public Oauth2SnsAuthenticationToken(String fromAppName, String redirectUrl, SocialAuthorizeInfo social, String host) {
		super(fromAppName, redirectUrl);
		Assert.notNull(social, "'social' must not be null");
		this.social = (social == null ? new SocialAuthorizeInfo() : social);
		this.host = host;
	}

	@Override
	final public Object getPrincipal() {
		return null; // Oauth2 login, no principal
	}

	@Override
	final public Object getCredentials() {
		return null; // Oauth2 login, no credentials
	}

	public SocialAuthorizeInfo getSocial() {
		return social;
	}

	@Override
	final public String getHost() {
		return host;
	}

	@Override
	public String toString() {
		return "[social=" + social + ", host=" + host + "]";
	}

}
