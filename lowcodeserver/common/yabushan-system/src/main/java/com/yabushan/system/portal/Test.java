package com.yabushan.system.portal;


import com.yabushan.system.portal.types.UIPService.AuthResult;
import com.yabushan.system.portal.types.UIPService.UserInfo;
import com.yabushan.system.portal.wsdl.UIPService.UIPServiceIFProxy;

import java.rmi.RemoteException;

/**
 * 单点登陆使用的是webservice服务，wsdl地址为http://eip2.gmcc.net:80/uipservice2/uipservice?WSDL
 */
public class Test {
	/** 下面是接入portal时分配的系统信息，请根据情况修改（重要，不能乱填）*/
	/** 系统ID */
	public final static String SYSTEMID = "GMCCYJXT_ACCESSRIGHT";
	/** 系统账号 */
	public final static String SYSTEMACCOUNT = "xtsgsyjxt";
	/** 系统密码 */
	public final static String SYSTEMPWD = "Axtyjxt!123";

	public final static String SOAPURL = "http://eip2.gmcc.net/eipservice/uipservice?WSDL";

	public static void main(String[] args) throws Exception {
		String cmd = args.length > 0 ? args[0] : "1";
		Test this_ = new Test();
		if("1".equals(cmd)){
			//单点登陆，token请从浏览器中域为.gmcc.net的cookie中iPlanetDirectoryPro获取
			this_.ssoLoginTest("AQIC5wM2LY4Sfcy7UB5CvbckB7TqgDF22YB3Mad+9Vclp+k=@AAJTSQACMDU=#");
		}else if("2".equals(cmd)){
			//公共登陆，portal账号/portal密码
			//this_.commonLoginTest("dwzhangsan", "AaAa1234");
		}
	}

	/**
	 * 单点登陆示例
	 */
	public void ssoLoginTest(String token) {
		UIPServiceIFProxy proxy = new UIPServiceIFProxy();
		try {
			//参数1 当前登录PORTAL用户的令牌,该值可以从request.getCookie()上遍历到，该值的名字为：iPlanetDirectoryPro
			//参数2 为portal分配的系统ID，该值需要提接入申请单给PORTAL进行备案
			AuthResult result = proxy.getUIPServiceIF().validateToken(token, SYSTEMID);//验证portal令牌
			if(result.isAuthResult()){//令牌验证成功
				String queryUser = result.getAccount();
				//获取用户详细信息
				UserInfo info = proxy.getUIPServiceIF().getUserInfoByUserID(queryUser, SYSTEMACCOUNT, SYSTEMPWD);
				System.out.println("token校验成功，用户账号为：" + queryUser + "，手机号：" + info.getMobilePhone());
			}else{
				System.out.println("令牌验证失败，原因是：" + result.getAuthMsg());
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 公共登陆示例
	 * @throws Exception
	 *//*
	public void commonLoginTest(String userid, String userpwd) throws Exception{
		CommonLoginSender sender = new CommonLoginSender(SOAPURL, userid, userpwd, SYSTEMID);
		WebserviceRequestHolder client = new WebserviceRequestHolder();
		client.setConnectionTimeout(10 * 1000);
		client.setRequestTimeout(10 * 1000);
		@SuppressWarnings("unchecked")
		Map<String, String> result = (Map<String, String>)client.execute(sender,
				new PrepareHttpPost() {
			public void prepare(HttpPost post) {
				post.addHeader("SOAPAction", "");
			}
		});
		System.out.println(result);
	}*/

}
