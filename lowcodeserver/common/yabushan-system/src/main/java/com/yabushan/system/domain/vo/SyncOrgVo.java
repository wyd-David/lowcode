package com.yabushan.system.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;

/**
 * 同步组织信息对象 sync_org
 *
 * @author yabushan
 * @date 2021-08-24
 */
public class SyncOrgVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** UUID,主键 */
    //@Excel(name = "UUID,主键")
    private String dpid;

    /** 全路径部门ID,由"."(点号)分隔 */
    //@Excel(name = "全路径部门ID,由(点号)分隔")
    private String dpcode;

    /** 部门名称 */
    @Excel(name = "部门名称",width = 35)
    private String dpname;

    //部门英文
    @Excel(name = "部门名称英译",width = 35)
    private String depEnglishName;


    //部门账套
    @Excel(name = "部门账套",width = 20)
    private String depAcset;

    public String getDepEnglishName() {
        return depEnglishName;
    }

    public void setDepEnglishName(String depEnglishName) {
        this.depEnglishName = depEnglishName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDpid() {
        return dpid;
    }

    public void setDpid(String dpid) {
        this.dpid = dpid;
    }

    public String getDpcode() {
        return dpcode;
    }

    public void setDpcode(String dpcode) {
        this.dpcode = dpcode;
    }

    public String getDpname() {
        return dpname;
    }

    public void setDpname(String dpname) {
        this.dpname = dpname;
    }

    public String getDepAcset() {
        return depAcset;
    }

    public void setDepAcset(String depAcset) {
        this.depAcset = depAcset;
    }
}
