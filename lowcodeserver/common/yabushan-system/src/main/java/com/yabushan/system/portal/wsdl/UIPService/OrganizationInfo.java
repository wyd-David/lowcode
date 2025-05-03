/**
 * OrganizationInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yabushan.system.portal.wsdl.UIPService;

public class OrganizationInfo  implements java.io.Serializable {
    private com.yabushan.system.portal.wsdl.UIPService.UumOrganizationInfo[] orgInfo;

    private int queryResult;

    public OrganizationInfo() {
    }

    public OrganizationInfo(
           com.yabushan.system.portal.wsdl.UIPService.UumOrganizationInfo[] orgInfo,
           int queryResult) {
           this.orgInfo = orgInfo;
           this.queryResult = queryResult;
    }


    /**
     * Gets the orgInfo value for this OrganizationInfo.
     *
     * @return orgInfo
     */
    public com.yabushan.system.portal.wsdl.UIPService.UumOrganizationInfo[] getOrgInfo() {
        return orgInfo;
    }


    /**
     * Sets the orgInfo value for this OrganizationInfo.
     *
     * @param orgInfo
     */
    public void setOrgInfo(com.yabushan.system.portal.wsdl.UIPService.UumOrganizationInfo[] orgInfo) {
        this.orgInfo = orgInfo;
    }

    public com.yabushan.system.portal.wsdl.UIPService.UumOrganizationInfo getOrgInfo(int i) {
        return this.orgInfo[i];
    }

    public void setOrgInfo(int i, com.yabushan.system.portal.wsdl.UIPService.UumOrganizationInfo _value) {
        this.orgInfo[i] = _value;
    }


    /**
     * Gets the queryResult value for this OrganizationInfo.
     *
     * @return queryResult
     */
    public int getQueryResult() {
        return queryResult;
    }


    /**
     * Sets the queryResult value for this OrganizationInfo.
     *
     * @param queryResult
     */
    public void setQueryResult(int queryResult) {
        this.queryResult = queryResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof OrganizationInfo)) return false;
        OrganizationInfo other = (OrganizationInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.orgInfo==null && other.getOrgInfo()==null) ||
             (this.orgInfo!=null &&
              java.util.Arrays.equals(this.orgInfo, other.getOrgInfo()))) &&
            this.queryResult == other.getQueryResult();
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
        if (getOrgInfo() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOrgInfo());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getOrgInfo(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getQueryResult();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrganizationInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://portal.gmcc.net/wsdl/UIPService", "organizationInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgInfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orgInfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://portal.gmcc.net/wsdl/UIPService", "uumOrganizationInfo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
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
