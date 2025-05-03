package com.yabushan.system.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 同步组织信息对象 sync_org
 *
 * @author yabushan
 * @date 2021-08-24
 */
public class SyncOrg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** UUID,主键 */
    @Excel(name = "UUID,主键")
    private String dpid;

    /** 全路径部门ID,由"."(点号)分隔 */
    @Excel(name = "全路径部门ID,由(点号)分隔")
    private String dpcode;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String dpname;

    /** 部门全称 */
    @Excel(name = "部门全称")
    private String dpfullname;

    /** 部门层级 */
    @Excel(name = "部门层级")
    private Long dplv;

    /** 部门序号 */
    @Excel(name = "部门序号")
    private Long serialnumber;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String comments;

    /** 记录创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 记录修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifieddate;

    /** 上级部门ID */
    @Excel(name = "上级部门ID")
    private String parentdpid;

    /** 操作代码 */
    @Excel(name = "操作代码")
    private String operationcode;

    /** 部门编号 */
    @Excel(name = "部门编号")
    private String newno;

    /** 部门类型代号 */
    @Excel(name = "部门类型代号")
    private String depttype;

    /** 父部门部门编号 */
    @Excel(name = "父部门部门编号")
    private String parentno;

    /** 部门在DominoLdap中记录数字标识 */
    @Excel(name = "部门在DominoLdap中记录数字标识")
    private String ldapdpid;

    /** 在Ldap中的记录唯一名称 */
    @Excel(name = "在Ldap中的记录唯一名称")
    private String dn;

    /** 是否显示于部门导航中 */
    @Excel(name = "是否显示于部门导航中")
    private String oashow;

    /** 部门名称的拼音全拼及简拼 */
    @Excel(name = "部门名称的拼音全拼及简拼")
    private String pinyin;

    /** 统一机构编码 */
    @Excel(name = "统一机构编码")
    private String unideptcode;

    /** 统一机构名称 */
    @Excel(name = "统一机构名称")
    private String unideptname;

    /** 是否部门/群组 */
    @Excel(name = "是否部门/群组")
    private String isdept;

    /** ERP中的机构ID */
    @Excel(name = "ERP中的机构ID")
    private String orgid;

    /** ERP中机构代码 */
    @Excel(name = "ERP中机构代码")
    private String orgcode;

    /** ERP中的机构名称 */
    @Excel(name = "ERP中的机构名称")
    private String orgnameinerp;

    /** 系统保留字段 */
    @Excel(name = "系统保留字段")
    private String deptdeletedate;


    @Excel(name = "上级统一机构编码")
    private String upuniteorgcode;

    /** 三大项目是否需要（1是0否）,默认值为1 */
    @Excel(name = "三大项目是否需要", readConverterExp = "1=是0否")
    private Long iscrm;

    /** 部门类型 */
    @Excel(name = "部门类型")
    private String depttype2;

    /** ERP中的公司段值 */
    @Excel(name = "ERP中的公司段值")
    private String segment1;

    /** ERP中的成本中心 */
    @Excel(name = "ERP中的成本中心")
    private String segment2;

    /** $column.columnComment */
    @Excel(name = "ERP中的成本中心")
    private Long rid;
   //部门集合
    private List<SyncOrg> chilNode;
    //部门英文
    private String depEnglishName;
    //部门账套
    private String depAcset;
    //组织id
    private String syscsdepid;

    /**
     * 系统部门id
     */


    @ApiModelProperty("系统部门id")
    private Long sysDepId;

    /**
     * 对接部门id
     */


    @ApiModelProperty("对接部门id")
    private String syncDepId;
    public SyncOrg(){}

    public SyncOrg(String parentdpid,String dpname){
        this.parentdpid = parentdpid;
        this.dpname = dpname;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setDpid(String dpid)
    {
        this.dpid = dpid;
    }

    public String getDpid()
    {
        return dpid;
    }
    public void setDpcode(String dpcode)
    {
        this.dpcode = dpcode;
    }

    public String getDpcode()
    {
        return dpcode;
    }
    public void setDpname(String dpname)
    {
        this.dpname = dpname;
    }

    public String getDpname()
    {
        return dpname;
    }
    public void setDpfullname(String dpfullname)
    {
        this.dpfullname = dpfullname;
    }

    public String getDpfullname()
    {
        return dpfullname;
    }
    public void setDplv(Long dplv)
    {
        this.dplv = dplv;
    }

    public Long getDplv()
    {
        return dplv;
    }
    public void setSerialnumber(Long serialnumber)
    {
        this.serialnumber = serialnumber;
    }

    public Long getSerialnumber()
    {
        return serialnumber;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String getComments()
    {
        return comments;
    }
    public void setCreatedate(Date createdate)
    {
        this.createdate = createdate;
    }

    public Date getCreatedate()
    {
        return createdate;
    }
    public void setModifieddate(Date modifieddate)
    {
        this.modifieddate = modifieddate;
    }

    public Date getModifieddate()
    {
        return modifieddate;
    }
    public void setParentdpid(String parentdpid)
    {
        this.parentdpid = parentdpid;
    }

    public String getParentdpid()
    {
        return parentdpid;
    }
    public void setOperationcode(String operationcode)
    {
        this.operationcode = operationcode;
    }

    public String getOperationcode()
    {
        return operationcode;
    }
    public void setNewno(String newno)
    {
        this.newno = newno;
    }

    public String getNewno()
    {
        return newno;
    }
    public void setDepttype(String depttype)
    {
        this.depttype = depttype;
    }

    public String getDepttype()
    {
        return depttype;
    }
    public void setParentno(String parentno)
    {
        this.parentno = parentno;
    }

    public String getParentno()
    {
        return parentno;
    }
    public void setLdapdpid(String ldapdpid)
    {
        this.ldapdpid = ldapdpid;
    }

    public String getLdapdpid()
    {
        return ldapdpid;
    }
    public void setDn(String dn)
    {
        this.dn = dn;
    }

    public String getDn()
    {
        return dn;
    }
    public void setOashow(String oashow)
    {
        this.oashow = oashow;
    }

    public String getOashow()
    {
        return oashow;
    }
    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }

    public String getPinyin()
    {
        return pinyin;
    }
    public void setUnideptcode(String unideptcode)
    {
        this.unideptcode = unideptcode;
    }

    public String getUnideptcode()
    {
        return unideptcode;
    }
    public void setUnideptname(String unideptname)
    {
        this.unideptname = unideptname;
    }

    public String getUnideptname()
    {
        return unideptname;
    }
    public void setIsdept(String isdept)
    {
        this.isdept = isdept;
    }

    public String getIsdept()
    {
        return isdept;
    }
    public void setOrgid(String orgid)
    {
        this.orgid = orgid;
    }

    public String getOrgid()
    {
        return orgid;
    }
    public void setOrgcode(String orgcode)
    {
        this.orgcode = orgcode;
    }

    public String getOrgcode()
    {
        return orgcode;
    }
    public void setOrgnameinerp(String orgnameinerp)
    {
        this.orgnameinerp = orgnameinerp;
    }

    public String getOrgnameinerp()
    {
        return orgnameinerp;
    }
    public void setDeptdeletedate(String deptdeletedate)
    {
        this.deptdeletedate = deptdeletedate;
    }

    public String getDeptdeletedate()
    {
        return deptdeletedate;
    }
    public void setUpuniteorgcode(String upuniteorgcode)
    {
        this.upuniteorgcode = upuniteorgcode;
    }

    public String getUpuniteorgcode()
    {
        return upuniteorgcode;
    }
    public void setIscrm(Long iscrm)
    {
        this.iscrm = iscrm;
    }

    public Long getIscrm()
    {
        return iscrm;
    }
    public void setDepttype2(String depttype2)
    {
        this.depttype2 = depttype2;
    }

    public String getDepttype2()
    {
        return depttype2;
    }
    public void setSegment1(String segment1)
    {
        this.segment1 = segment1;
    }

    public String getSegment1()
    {
        return segment1;
    }
    public void setSegment2(String segment2)
    {
        this.segment2 = segment2;
    }

    public String getSegment2()
    {
        return segment2;
    }
    public void setRid(Long rid)
    {
        this.rid = rid;
    }

    public Long getRid()
    {
        return rid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dpid", getDpid())
            .append("dpcode", getDpcode())
            .append("dpname", getDpname())
            .append("dpfullname", getDpfullname())
            .append("dplv", getDplv())
            .append("serialnumber", getSerialnumber())
            .append("status", getStatus())
            .append("comments", getComments())
            .append("createdate", getCreatedate())
            .append("modifieddate", getModifieddate())
            .append("parentdpid", getParentdpid())
            .append("operationcode", getOperationcode())
            .append("newno", getNewno())
            .append("depttype", getDepttype())
            .append("parentno", getParentno())
            .append("ldapdpid", getLdapdpid())
            .append("dn", getDn())
            .append("oashow", getOashow())
            .append("pinyin", getPinyin())
            .append("unideptcode", getUnideptcode())
            .append("unideptname", getUnideptname())
            .append("isdept", getIsdept())
            .append("orgid", getOrgid())
            .append("orgcode", getOrgcode())
            .append("orgnameinerp", getOrgnameinerp())
            .append("deptdeletedate", getDeptdeletedate())
            .append("upuniteorgcode", getUpuniteorgcode())
            .append("iscrm", getIscrm())
            .append("depttype2", getDepttype2())
            .append("segment1", getSegment1())
            .append("segment2", getSegment2())
            .append("rid", getRid())
            .toString();
    }

    public List<SyncOrg> getChilNode() {
        return chilNode;
    }

    public void setChilNode(List<SyncOrg> chilNode) {
        this.chilNode = chilNode;
    }





    public String getDepAcset() {
        return depAcset;
    }

    public void setDepAcset(String depAcset) {
        this.depAcset = depAcset;
    }

    public String getDepEnglishName() {
        return depEnglishName;
    }

    public void setDepEnglishName(String depEnglishName) {
        this.depEnglishName = depEnglishName;
    }

    public String getSyscsdepid() {
        return syscsdepid;
    }

    public void setSyscsdepid(String syscsdepid) {
        this.syscsdepid = syscsdepid;
    }

    public Long getSysDepId() {
        return sysDepId;
    }

    public void setSysDepId(Long sysDepId) {
        this.sysDepId = sysDepId;
    }

    public String getSyncDepId() {
        return syncDepId;
    }

    public void setSyncDepId(String syncDepId) {
        this.syncDepId = syncDepId;
    }
}
