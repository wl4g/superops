package com.wl4g.devops.iam.authc;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.hibernate.validator.constraints.NotBlank;

import com.wl4g.devops.iam.common.authc.ClientRef;
import com.wl4g.devops.iam.common.authc.IamAuthenticationToken;

/**
 * General (Username/Password) authentication token
 * 
 * @author Wangl.sir <983708408@qq.com>
 * @version v1.0
 * @date 2018年11月19日
 * @since
 */
public class GeneralAuthenticationToken extends UsernamePasswordToken
		implements IamAuthenticationToken, CaptchaAuthenticationToken {
	private static final long serialVersionUID = 8587329689973009598L;

	/**
	 * Source application name
	 */
	final private String fromAppName;

	/**
	 * Source application callback URL
	 */
	final private String redirectUrl;

	@NotBlank
	final private String captcha;

	final private ClientRef clientRef;

	public GeneralAuthenticationToken(final String fromAppName, final String redirectUrl, final String username,
			final String password, String clientRef, final String captcha) {
		super(username, password);
		this.fromAppName = fromAppName;
		this.redirectUrl = redirectUrl;
		this.clientRef = ClientRef.of(clientRef);
		this.captcha = captcha;
	}

	@Override
	public Object getCredentials() {
		return new String((char[]) super.getCredentials());
	}

	@Override
	public String getCaptcha() {
		return captcha;
	}

	public ClientRef getClientRef() {
		return clientRef;
	}

	@Override
	public String getFromAppName() {
		return fromAppName;
	}

	@Override
	public String getRedirectUrl() {
		return redirectUrl;
	}

}
