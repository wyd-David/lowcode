package com.yabushan.system.domain;

import java.io.Serializable;

public class ViewDeptInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String companyName;

    private String companyId;

    private String deptName;

    private String deptId;

    private String ouOrder;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getOuOrder() {
        return ouOrder;
    }

    public void setOuOrder(String ouOrder) {
        this.ouOrder = ouOrder;
    }
}
