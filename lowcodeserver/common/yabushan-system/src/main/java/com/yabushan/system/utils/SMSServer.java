/**
 * SMSServer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yabushan.system.utils;

public interface SMSServer extends javax.xml.rpc.Service {

	/**
	 * 企信通下行短信Web服务 
	 * login() 登陆系统 
	 * send() 发送短信 
	 * logout() 退出系统 
	 * logout() 强制退出系统
	 */
    public String getMeipWebServiceServerPortAddress();

    public MeipWebServiceServerPortType getMeipWebServiceServerPort() throws javax.xml.rpc.ServiceException;

    public MeipWebServiceServerPortType getMeipWebServiceServerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
