package com.yabushan.system.domain.vo;

public class MyDeptVo {

    private String DeptName;
    private String DeptEnName;
    private String MyPosition;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    private String deptId;


    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getDeptEnName() {
        return DeptEnName;
    }

    public void setDeptEnName(String deptEnName) {
        DeptEnName = deptEnName;
    }

    public String getMyPosition() {
        return MyPosition;
    }

    public void setMyPosition(String myPosition) {
        MyPosition = myPosition;
    }
}
