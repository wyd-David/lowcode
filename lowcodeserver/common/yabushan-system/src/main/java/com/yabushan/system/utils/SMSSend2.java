package com.yabushan.system.utils;


import com.yabushan.system.service.impl.WarnSmsLogServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.RemoteException;


public class SMSSend2 {

	private static final Logger log = LoggerFactory.getLogger(SMSSend2.class);

	MeipWebServiceServerPortType gmmsSmsServer;

	public static String gmmsSmsSendSessionId;
	private String sysId="1";
	private String password="2";

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	String[] sys_ids = new String[]{"honor","rrdsjdy","webmail","ywk","gdoa",
			"erp","ulearning","news","jyfxxt","glxxzczx","ldjs","mt","gis","bbss","timp",
			"yewuzhicheng","ithotline","yinqihulian","tygzpl","smis"};

	//String[] sys_ids2 = new String[]{"bbss"};//bbss

	/**
	 * 登录方法
	 * @param sysId
	 * @param password
	 * @throws Exception
	 */
	public SMSSend2(String sysId,String password) throws Exception{

		try{
			SMSServer server = new SMSServerLocator();
			gmmsSmsServer = server.getMeipWebServiceServerPort(new java.net.URL("http://10.243.22.238/SMSMeipWebService/SMSServer?wsdl"));//10.248.200.191
		}catch(MalformedURLException mue){
			log.error("new SMSServerLocator错误:"+mue.getMessage());
			mue.printStackTrace();
			throw mue;
		}catch(Exception se){
			log.error("new SMSServerLocator错误:"+se.getMessage());
			se.printStackTrace();
			throw se;
		}
		try{
			gmmsSmsSendSessionId = gmmsSmsServer.login(sysId, password);
		}catch(RemoteException re){
			log.error("login错误:"+re.getMessage());
			re.printStackTrace();
			throw re;
		}
	}


	/**
	 * 发送短信方法
	 * @param content  短信内容
	 * @param receivers  接收手机号 13812345678#13612345678#13512345678
	 * @param extendsNum  扩展号
	 * @return 如果返回null则表示未发送成功，否则发送成功
	 * @throws RemoteException
	 */
	public String send(String content,String receivers,String extendsNum) throws RemoteException{
		String sendRtn = "";
		try{
			sendRtn = gmmsSmsServer.send(gmmsSmsSendSessionId, content, receivers, extendsNum);
		}catch(RemoteException re){
			log.error("send错误:"+re.getMessage());
			re.printStackTrace();
			throw re;
		}
		return sendRtn;
	}


	
}
