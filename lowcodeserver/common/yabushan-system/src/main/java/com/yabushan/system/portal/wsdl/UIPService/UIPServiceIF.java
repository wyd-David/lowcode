/**
 * UIPServiceIF.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yabushan.system.portal.wsdl.UIPService;
public interface UIPServiceIF extends java.rmi.Remote {
    public com.yabushan.system.portal.types.UIPService.AuthResult SSOLogin(String string_1, String string_2, String string_3, int int_4) throws java.rmi.RemoteException;
    public com.yabushan.system.portal.types.UIPService.AuthResult commonLogin(String string_1, String string_2, String string_3, int int_4) throws java.rmi.RemoteException;
    public com.yabushan.system.portal.types.UIPService.AuthResult validateToken(String string_1, String string_2) throws java.rmi.RemoteException;
    public void logout(String string_1) throws java.rmi.RemoteException;
    public com.yabushan.system.portal.types.UIPService.UserInfo getUserInfoByUserID(String string_1, String string_2, String string_3) throws java.rmi.RemoteException;
    public com.yabushan.system.portal.types.UIPService.OrganizationInfo getOrgnizationInfoByOUID(int int_1, boolean boolean_2, String string_3, String string_4) throws java.rmi.RemoteException;
    public com.yabushan.system.portal.types.UIPService.QueryResult getUserInfoByOUID(int int_1, boolean boolean_2, String string_3, String string_4) throws java.rmi.RemoteException;
    public com.yabushan.system.portal.types.UIPService.AppResult applicationToDo(String string_1, String string_2, String string_3, String string_4, String string_5, int int_6, String string_7, String string_8, String string_9, String string_10, int int_11) throws java.rmi.RemoteException;
    public com.yabushan.system.portal.types.UIPService.AppResult applicationToRead(String string_1, String string_2, String string_3, String string_4, int int_5, String string_6, String string_7, String string_8, String string_9, int int_10) throws java.rmi.RemoteException;
    public String getVersion() throws java.rmi.RemoteException;
}
