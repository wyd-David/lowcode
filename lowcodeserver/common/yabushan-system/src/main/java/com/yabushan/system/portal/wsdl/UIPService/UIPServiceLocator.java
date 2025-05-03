/**
 * UIPServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yabushan.system.portal.wsdl.UIPService;

public class UIPServiceLocator extends org.apache.axis.client.Service implements com.yabushan.system.portal.wsdl.UIPService.UIPService {

    public UIPServiceLocator() {
    }


    public UIPServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UIPServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for UIPServiceIFPort
    private String UIPServiceIFPort_address = "http://eip2.gmcc.net/eipservice/uipservice";

    public String getUIPServiceIFPortAddress() {
        return UIPServiceIFPort_address;
    }

    // The WSDD service name defaults to the port name.
    private String UIPServiceIFPortWSDDServiceName = "UIPServiceIFPort";

    public String getUIPServiceIFPortWSDDServiceName() {
        return UIPServiceIFPortWSDDServiceName;
    }

    public void setUIPServiceIFPortWSDDServiceName(String name) {
        UIPServiceIFPortWSDDServiceName = name;
    }

    public com.yabushan.system.portal.wsdl.UIPService.UIPServiceIF getUIPServiceIFPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(UIPServiceIFPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUIPServiceIFPort(endpoint);
    }

    public com.yabushan.system.portal.wsdl.UIPService.UIPServiceIF getUIPServiceIFPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.yabushan.system.portal.wsdl.UIPService.UIPServiceIFBindingStub _stub = new com.yabushan.system.portal.wsdl.UIPService.UIPServiceIFBindingStub(portAddress, this);
            _stub.setPortName(getUIPServiceIFPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUIPServiceIFPortEndpointAddress(String address) {
        UIPServiceIFPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.yabushan.system.portal.wsdl.UIPService.UIPServiceIF.class.isAssignableFrom(serviceEndpointInterface)) {
                com.yabushan.system.portal.wsdl.UIPService.UIPServiceIFBindingStub _stub = new com.yabushan.system.portal.wsdl.UIPService.UIPServiceIFBindingStub(new java.net.URL(UIPServiceIFPort_address), this);
                _stub.setPortName(getUIPServiceIFPortWSDDServiceName());
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
        if ("UIPServiceIFPort".equals(inputPortName)) {
            return getUIPServiceIFPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://portal.gmcc.net/wsdl/UIPService", "UIPService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://portal.gmcc.net/wsdl/UIPService", "UIPServiceIFPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("UIPServiceIFPort".equals(portName)) {
            setUIPServiceIFPortEndpointAddress(address);
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
