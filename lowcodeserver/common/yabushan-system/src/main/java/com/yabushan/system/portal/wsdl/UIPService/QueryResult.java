/**
 * QueryResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yabushan.system.portal.wsdl.UIPService;

public class QueryResult  implements java.io.Serializable {
    private int queryResult;

    private com.yabushan.system.portal.wsdl.UIPService.UumUserInfo[] userInfo;

    public QueryResult() {
    }

    public QueryResult(
           int queryResult,
           com.yabushan.system.portal.wsdl.UIPService.UumUserInfo[] userInfo) {
           this.queryResult = queryResult;
           this.userInfo = userInfo;
    }


    /**
     * Gets the queryResult value for this QueryResult.
     *
     * @return queryResult
     */
    public int getQueryResult() {
        return queryResult;
    }


    /**
     * Sets the queryResult value for this QueryResult.
     *
     * @param queryResult
     */
    public void setQueryResult(int queryResult) {
        this.queryResult = queryResult;
    }


    /**
     * Gets the userInfo value for this QueryResult.
     *
     * @return userInfo
     */
    public com.yabushan.system.portal.wsdl.UIPService.UumUserInfo[] getUserInfo() {
        return userInfo;
    }


    /**
     * Sets the userInfo value for this QueryResult.
     *
     * @param userInfo
     */
    public void setUserInfo(com.yabushan.system.portal.wsdl.UIPService.UumUserInfo[] userInfo) {
        this.userInfo = userInfo;
    }

    public com.yabushan.system.portal.wsdl.UIPService.UumUserInfo getUserInfo(int i) {
        return this.userInfo[i];
    }

    public void setUserInfo(int i, com.yabushan.system.portal.wsdl.UIPService.UumUserInfo _value) {
        this.userInfo[i] = _value;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof QueryResult)) return false;
        QueryResult other = (QueryResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            this.queryResult == other.getQueryResult() &&
            ((this.userInfo==null && other.getUserInfo()==null) ||
             (this.userInfo!=null &&
              java.util.Arrays.equals(this.userInfo, other.getUserInfo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getQueryResult();
        if (getUserInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUserInfo());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getUserInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://portal.gmcc.net/wsdl/UIPService", "queryResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://portal.gmcc.net/wsdl/UIPService", "uumUserInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
