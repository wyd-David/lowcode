package com.yabushan.system.utils;

public class SMSServerLocator extends org.apache.axis.client.Service implements SMSServer {

	/**
	 * 下行短信Web服务 
	 * login() 登陆系统 
	 * send() 发送短信 
	 * logout() 退出系统 
	 * logout() 强制退出系统
	 */

    public SMSServerLocator() {
    }

   
    public SMSServerLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SMSServerLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MeipWebServiceServerPort
    private String MeipWebServiceServerPort_address = "http://10.243.22.23/SMSMeipWebService/SMSServer";

    public String getMeipWebServiceServerPortAddress() {
        return MeipWebServiceServerPort_address;
    }

    // The WSDD service name defaults to the port name.
    private String MeipWebServiceServerPortWSDDServiceName = "MeipWebServiceServerPort";

    public String getMeipWebServiceServerPortWSDDServiceName() {
        return MeipWebServiceServerPortWSDDServiceName;
    }

    public void setMeipWebServiceServerPortWSDDServiceName(String name) {
        MeipWebServiceServerPortWSDDServiceName = name;
    }

    public MeipWebServiceServerPortType getMeipWebServiceServerPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MeipWebServiceServerPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMeipWebServiceServerPort(endpoint);
    }

    public MeipWebServiceServerPortType getMeipWebServiceServerPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	MeipWebServiceServerBindingStub _stub = new MeipWebServiceServerBindingStub(portAddress, this);
            _stub.setPortName(getMeipWebServiceServerPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMeipWebServiceServerPortEndpointAddress(String address) {
        MeipWebServiceServerPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (MeipWebServiceServerPortType.class.isAssignableFrom(serviceEndpointInterface)) {
            	MeipWebServiceServerBindingStub _stub = new MeipWebServiceServerBindingStub(new java.net.URL(MeipWebServiceServerPort_address), this);
                _stub.setPortName(getMeipWebServiceServerPortWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("MeipWebServiceServerPort".equals(inputPortName)) {
            return getMeipWebServiceServerPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://com/creawor/meip2/webservice/server/MeipWebServiceServer.wsdl", "SMSServer");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://com/creawor/meip2/webservice/server/MeipWebServiceServer.wsdl", "MeipWebServiceServerPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {
        
if ("MeipWebServiceServerPort".equals(portName)) {
            setMeipWebServiceServerPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
