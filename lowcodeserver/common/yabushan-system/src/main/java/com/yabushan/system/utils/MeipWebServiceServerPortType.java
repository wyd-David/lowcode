/**
 * MeipWebServiceServerPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yabushan.system.utils;

public interface MeipWebServiceServerPortType extends java.rmi.Remote {
    public String login(String uid, String password) throws java.rmi.RemoteException;
    public String send(String sessionId, String content, String receivers, String extendsNum) throws java.rmi.RemoteException;
    public String sendWapPush(String sessionId, String content, String wapUrl, String receivers, String extendsNum) throws java.rmi.RemoteException;
    public String sendSms(String sessionId, String content, String receivers, String extendsNum, String smsType) throws java.rmi.RemoteException;
    public int logout(String sessionId) throws java.rmi.RemoteException;
    public int forceLogout(String uid, String password) throws java.rmi.RemoteException;
}
