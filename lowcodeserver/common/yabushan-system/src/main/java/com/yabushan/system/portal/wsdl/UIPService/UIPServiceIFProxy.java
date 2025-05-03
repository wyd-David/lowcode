package com.yabushan.system.portal.wsdl.UIPService;

public class UIPServiceIFProxy implements com.yabushan.system.portal.wsdl.UIPService.UIPServiceIF {
  private String _endpoint = null;
  private com.yabushan.system.portal.wsdl.UIPService.UIPServiceIF uIPServiceIF = null;

  public UIPServiceIFProxy() {
    _initUIPServiceIFProxy();
  }

  public UIPServiceIFProxy(String endpoint) {
    _endpoint = endpoint;
    _initUIPServiceIFProxy();
  }

  private void _initUIPServiceIFProxy() {
    try {
      uIPServiceIF = (new com.yabushan.system.portal.wsdl.UIPService.UIPServiceLocator()).getUIPServiceIFPort();
      if (uIPServiceIF != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)uIPServiceIF)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)uIPServiceIF)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (uIPServiceIF != null)
      ((javax.xml.rpc.Stub)uIPServiceIF)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public com.yabushan.system.portal.wsdl.UIPService.UIPServiceIF getUIPServiceIF() {
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF;
  }

  public com.yabushan.system.portal.types.UIPService.AuthResult SSOLogin(String string_1, String string_2, String string_3, int int_4) throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF.SSOLogin(string_1, string_2, string_3, int_4);
  }

  public com.yabushan.system.portal.types.UIPService.AuthResult commonLogin(String string_1, String string_2, String string_3, int int_4) throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF.commonLogin(string_1, string_2, string_3, int_4);
  }

  public com.yabushan.system.portal.types.UIPService.AuthResult validateToken(String string_1, String string_2) throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF.validateToken(string_1, string_2);
  }

  public void logout(String string_1) throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    uIPServiceIF.logout(string_1);
  }

  public com.yabushan.system.portal.types.UIPService.UserInfo getUserInfoByUserID(String string_1, String string_2, String string_3) throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF.getUserInfoByUserID(string_1, string_2, string_3);
  }

  public com.yabushan.system.portal.types.UIPService.OrganizationInfo getOrgnizationInfoByOUID(int int_1, boolean boolean_2, String string_3, String string_4) throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF.getOrgnizationInfoByOUID(int_1, boolean_2, string_3, string_4);
  }

  public com.yabushan.system.portal.types.UIPService.QueryResult getUserInfoByOUID(int int_1, boolean boolean_2, String string_3, String string_4) throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF.getUserInfoByOUID(int_1, boolean_2, string_3, string_4);
  }

  public com.yabushan.system.portal.types.UIPService.AppResult applicationToDo(String string_1, String string_2, String string_3, String string_4, String string_5, int int_6, String string_7, String string_8, String string_9, String string_10, int int_11) throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF.applicationToDo(string_1, string_2, string_3, string_4, string_5, int_6, string_7, string_8, string_9, string_10, int_11);
  }

  public com.yabushan.system.portal.types.UIPService.AppResult applicationToRead(String string_1, String string_2, String string_3, String string_4, int int_5, String string_6, String string_7, String string_8, String string_9, int int_10) throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF.applicationToRead(string_1, string_2, string_3, string_4, int_5, string_6, string_7, string_8, string_9, int_10);
  }

  public String getVersion() throws java.rmi.RemoteException{
    if (uIPServiceIF == null)
      _initUIPServiceIFProxy();
    return uIPServiceIF.getVersion();
  }


}
