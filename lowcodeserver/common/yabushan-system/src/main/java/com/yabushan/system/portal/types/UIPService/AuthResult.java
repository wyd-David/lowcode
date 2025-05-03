/**
 * AuthResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yabushan.system.portal.types.UIPService;

public class AuthResult  implements java.io.Serializable {
    private String idsTokenValue;

    private String idsTokenName;

    private String account;

    private boolean authResult;

    private String authMsg;

    public AuthResult() {
    }

    public AuthResult(
           String idsTokenValue,
           String idsTokenName,
           String account,
           boolean authResult,
           String authMsg) {
           this.idsTokenValue = idsTokenValue;
           this.idsTokenName = idsTokenName;
           this.account = account;
           this.authResult = authResult;
           this.authMsg = authMsg;
    }


    /**
     * Gets the idsTokenValue value for this AuthResult.
     *
     * @return idsTokenValue
     */
    public String getIdsTokenValue() {
        return idsTokenValue;
    }


    /**
     * Sets the idsTokenValue value for this AuthResult.
     *
     * @param idsTokenValue
     */
    public void setIdsTokenValue(String idsTokenValue) {
        this.idsTokenValue = idsTokenValue;
    }


    /**
     * Gets the idsTokenName value for this AuthResult.
     *
     * @return idsTokenName
     */
    public String getIdsTokenName() {
        return idsTokenName;
    }


    /**
     * Sets the idsTokenName value for this AuthResult.
     *
     * @param idsTokenName
     */
    public void setIdsTokenName(String idsTokenName) {
        this.idsTokenName = idsTokenName;
    }


    /**
     * Gets the account value for this AuthResult.
     *
     * @return account
     */
    public String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this AuthResult.
     *
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }


    /**
     * Gets the authResult value for this AuthResult.
     *
     * @return authResult
     */
    public boolean isAuthResult() {
        return authResult;
    }


    /**
     * Sets the authResult value for this AuthResult.
     *
     * @param authResult
     */
    public void setAuthResult(boolean authResult) {
        this.authResult = authResult;
    }


    /**
     * Gets the authMsg value for this AuthResult.
     *
     * @return authMsg
     */
    public String getAuthMsg() {
        return authMsg;
    }


    /**
     * Sets the authMsg value for this AuthResult.
     *
     * @param authMsg
     */
    public void setAuthMsg(String authMsg) {
        this.authMsg = authMsg;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof AuthResult)) return false;
        AuthResult other = (AuthResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.idsTokenValue==null && other.getIdsTokenValue()==null) ||
             (this.idsTokenValue!=null &&
              this.idsTokenValue.equals(other.getIdsTokenValue()))) &&
            ((this.idsTokenName==null && other.getIdsTokenName()==null) ||
             (this.idsTokenName!=null &&
              this.idsTokenName.equals(other.getIdsTokenName()))) &&
            ((this.account==null && other.getAccount()==null) ||
             (this.account!=null &&
              this.account.equals(other.getAccount()))) &&
            this.authResult == other.isAuthResult() &&
            ((this.authMsg==null && other.getAuthMsg()==null) ||
             (this.authMsg!=null &&
              this.authMsg.equals(other.getAuthMsg())));
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
        if (getIdsTokenValue() != null) {
            _hashCode += getIdsTokenValue().hashCode();
        }
        if (getIdsTokenName() != null) {
            _hashCode += getIdsTokenName().hashCode();
        }
        if (getAccount() != null) {
            _hashCode += getAccount().hashCode();
        }
        _hashCode += (isAuthResult() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getAuthMsg() != null) {
            _hashCode += getAuthMsg().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuthResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://portal.gmcc.net/types/UIPService", "AuthResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idsTokenValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idsTokenValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idsTokenName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idsTokenName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("account");
        elemField.setXmlName(new javax.xml.namespace.QName("", "account"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authResult");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authMsg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authMsg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
