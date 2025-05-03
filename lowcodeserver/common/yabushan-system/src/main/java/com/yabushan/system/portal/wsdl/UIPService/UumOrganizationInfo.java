/**
 * UumOrganizationInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.yabushan.system.portal.wsdl.UIPService;

public class UumOrganizationInfo  implements java.io.Serializable {
    private String OUFullName;

    private int OUID;

    private int OULevel;

    private String OUOrder;

    private String orgDN;

    private String organizationName;

    private int parentOUID;

    public UumOrganizationInfo() {
    }

    public UumOrganizationInfo(
           String OUFullName,
           int OUID,
           int OULevel,
           String OUOrder,
           String orgDN,
           String organizationName,
           int parentOUID) {
           this.OUFullName = OUFullName;
           this.OUID = OUID;
           this.OULevel = OULevel;
           this.OUOrder = OUOrder;
           this.orgDN = orgDN;
           this.organizationName = organizationName;
           this.parentOUID = parentOUID;
    }


    /**
     * Gets the OUFullName value for this UumOrganizationInfo.
     *
     * @return OUFullName
     */
    public String getOUFullName() {
        return OUFullName;
    }


    /**
     * Sets the OUFullName value for this UumOrganizationInfo.
     *
     * @param OUFullName
     */
    public void setOUFullName(String OUFullName) {
        this.OUFullName = OUFullName;
    }


    /**
     * Gets the OUID value for this UumOrganizationInfo.
     *
     * @return OUID
     */
    public int getOUID() {
        return OUID;
    }


    /**
     * Sets the OUID value for this UumOrganizationInfo.
     *
     * @param OUID
     */
    public void setOUID(int OUID) {
        this.OUID = OUID;
    }


    /**
     * Gets the OULevel value for this UumOrganizationInfo.
     *
     * @return OULevel
     */
    public int getOULevel() {
        return OULevel;
    }


    /**
     * Sets the OULevel value for this UumOrganizationInfo.
     *
     * @param OULevel
     */
    public void setOULevel(int OULevel) {
        this.OULevel = OULevel;
    }


    /**
     * Gets the OUOrder value for this UumOrganizationInfo.
     *
     * @return OUOrder
     */
    public String getOUOrder() {
        return OUOrder;
    }


    /**
     * Sets the OUOrder value for this UumOrganizationInfo.
     *
     * @param OUOrder
     */
    public void setOUOrder(String OUOrder) {
        this.OUOrder = OUOrder;
    }


    /**
     * Gets the orgDN value for this UumOrganizationInfo.
     *
     * @return orgDN
     */
    public String getOrgDN() {
        return orgDN;
    }


    /**
     * Sets the orgDN value for this UumOrganizationInfo.
     *
     * @param orgDN
     */
    public void setOrgDN(String orgDN) {
        this.orgDN = orgDN;
    }


    /**
     * Gets the organizationName value for this UumOrganizationInfo.
     *
     * @return organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }


    /**
     * Sets the organizationName value for this UumOrganizationInfo.
     *
     * @param organizationName
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }


    /**
     * Gets the parentOUID value for this UumOrganizationInfo.
     *
     * @return parentOUID
     */
    public int getParentOUID() {
        return parentOUID;
    }


    /**
     * Sets the parentOUID value for this UumOrganizationInfo.
     *
     * @param parentOUID
     */
    public void setParentOUID(int parentOUID) {
        this.parentOUID = parentOUID;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UumOrganizationInfo)) return false;
        UumOrganizationInfo other = (UumOrganizationInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.OUFullName==null && other.getOUFullName()==null) ||
             (this.OUFullName!=null &&
              this.OUFullName.equals(other.getOUFullName()))) &&
            this.OUID == other.getOUID() &&
            this.OULevel == other.getOULevel() &&
            ((this.OUOrder==null && other.getOUOrder()==null) ||
             (this.OUOrder!=null &&
              this.OUOrder.equals(other.getOUOrder()))) &&
            ((this.orgDN==null && other.getOrgDN()==null) ||
             (this.orgDN!=null &&
              this.orgDN.equals(other.getOrgDN()))) &&
            ((this.organizationName==null && other.getOrganizationName()==null) ||
             (this.organizationName!=null &&
              this.organizationName.equals(other.getOrganizationName()))) &&
            this.parentOUID == other.getParentOUID();
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
        if (getOUFullName() != null) {
            _hashCode += getOUFullName().hashCode();
        }
        _hashCode += getOUID();
        _hashCode += getOULevel();
        if (getOUOrder() != null) {
            _hashCode += getOUOrder().hashCode();
        }
        if (getOrgDN() != null) {
            _hashCode += getOrgDN().hashCode();
        }
        if (getOrganizationName() != null) {
            _hashCode += getOrganizationName().hashCode();
        }
        _hashCode += getParentOUID();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UumOrganizationInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://portal.gmcc.net/wsdl/UIPService", "uumOrganizationInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OUFullName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OUFullName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OUID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OUID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OULevel");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OULevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OUOrder");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OUOrder"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgDN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orgDN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organizationName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organizationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentOUID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentOUID"));
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
