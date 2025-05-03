package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 同步用户信息对象 sync_user
 *
 * @author yabushan
 * @date 2021-08-24
 */
public class SyncUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** UUID,主键 */
    @Excel(name = "UUID,主键")
    private String userid;

    /** 用户的登录ID */
    @Excel(name = "用户的登录ID")
    private String loginid;

    /** 旧的用户ID,从ERP数据中取过来 */
    @Excel(name = "旧的用户ID,从ERP数据中取过来")
    private String erpid;

    /** 用户中文名 */
    @Excel(name = "用户中文名")
    private String username;

    /** 用户中文名拼音首字母如朱泰江为ztj */
    @Excel(name = "用户中文名拼音首字母如朱泰江为ztj")
    private String initialcharacter;

    /** 用户所属部门id */
    @Excel(name = "用户所属部门id")
    private String dpid;

    /** 座机号 */
    @Excel(name = "座机号")
    private String phone;

    /** 直线座机号 */
    @Excel(name = "直线座机号")
    private String directphone;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String mobile;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String mailbox;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** (在部门中的)序号 */
    @Excel(name = "(在部门中的)序号")
    private Long serialnumber;

    /** QQ 或 MSN */
    @Excel(name = "QQ 或 MSN")
    private String qqormsn;

    /** 工作地点 */
    @Excel(name = "工作地点")
    private String workingplace;

    /** 职位 */
    @Excel(name = "职位")
    private String post;

    /** 职级 */
    @Excel(name = "职级")
    private String rank;

    /** 职级 */
    @Excel(name = "职级(关键字替换)")
    private String rankLv;

    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 入职日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date employeddate;

    /** 离职日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dimissiondate;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 备注(在数据库中对本用户的备注) */
    @Excel(name = "备注(在数据库中对本用户的备注)")
    private String comments;

    /** 个人相片 */
    @Excel(name = "个人相片")
    private String photograph;

    /** 是否为主部门 */
    @Excel(name = "是否为主部门")
    private String maindepartment;

    /** 上级领导(为ERP保留的扩展) */
    @Excel(name = "上级领导(为ERP保留的扩展)")
    private String superiorleader;

    /** 员工类别(正式员工,临时员工,营销人员等) */
    @Excel(name = "员工类别(正式员工,临时员工,营销人员等)")
    private String stafftype;

    /** 用户的个人签名(签名留言) */
    @Excel(name = "用户的个人签名(签名留言)")
    private String idiograph;

    /** 个人备注(信息) */
    @Excel(name = "个人备注(信息)")
    private String remarks;

    /** Domino Ldap中记录的UNID */
    @Excel(name = "Domino Ldap中记录的UNID")
    private String oaid;

    /** Domino Ldap中当前记录所在群组 */
    @Excel(name = "Domino Ldap中当前记录所在群组")
    private String dpdn;

    /** 在Ldap中的记录显示名 */
    @Excel(name = "在Ldap中的记录显示名")
    private String displayname;

    /** 在Ldap中的记录唯一名称 */
    @Excel(name = "在Ldap中的记录唯一名称")
    private String dn;

    /** 门户Tam创建标识(外部系统无需使用) */
    @Excel(name = "门户Tam创建标识(外部系统无需使用)")
    private String tamuid;

    /** 用户邮箱服务器所在 */
    @Excel(name = "用户邮箱服务器所在")
    private String oamailserver;

    /** Domino邮件库名称 */
    @Excel(name = "Domino邮件库名称")
    private String oamailfile;

    /** OA中用户的描述备注 */
    @Excel(name = "OA中用户的描述备注")
    private String oacomment;

    /** 用户身份证号 */
    @Excel(name = "用户身份证号")
    private String nationalid;

    /** 统一人员编码 */
    @Excel(name = "统一人员编码")
    private String uniusercode;

    /** 用户所在单位编号 */
    @Excel(name = "用户所在单位编号")
    private String orgcodeofuser;

    /** 系统保留字段 */
    @Excel(name = "系统保留字段")
    private String closeaccount;

    /** 用户创建时间 */
    @Excel(name = "用户创建时间")
    private String usercreatedate;

    /** 系统保留字段 */
    @Excel(name = "系统保留字段")
    private String usermodifydate;

    /** 系统保留字段 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "系统保留字段", width = 30, dateFormat = "yyyy-MM-dd")
    private Date userdeletedate;

    /** 岗位类型代号 */
    @Excel(name = "岗位类型代号")
    private String positioncatcode;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String positioncat;

    /** 岗位 */
    @Excel(name = "岗位")
    private String position;

    /** 用户组织架构编号 */
    @Excel(name = "用户组织架构编号")
    private String orgidofuser;

    /** 用户对应的部门唯一编号 */
    @Excel(name = "用户对应的部门唯一编号")
    private String unideptcode;

    /** $column.columnComment */
    @Excel(name = "用户对应的部门唯一编号")
    private Long rid;

    //部门英文名
    private String depEnName;

    public SyncUser(){}

    public SyncUser(String dpid){
        this.dpid = dpid;
    }

    public SyncUser(String userid,String loginid,String erpid,String username){
        this.userid = userid;
        this.loginid = loginid;
        this.erpid = erpid;
        this.username = username;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUserid(String userid)
    {
        this.userid = userid;
    }

    public String getUserid()
    {
        return userid;
    }
    public void setLoginid(String loginid)
    {
        this.loginid = loginid;
    }

    public String getLoginid()
    {
        return loginid;
    }
    public void setErpid(String erpid)
    {
        this.erpid = erpid;
    }

    public String getErpid()
    {
        return erpid;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
    public void setInitialcharacter(String initialcharacter)
    {
        this.initialcharacter = initialcharacter;
    }

    public String getInitialcharacter()
    {
        return initialcharacter;
    }
    public void setDpid(String dpid)
    {
        this.dpid = dpid;
    }

    public String getDpid()
    {
        return dpid;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setDirectphone(String directphone)
    {
        this.directphone = directphone;
    }

    public String getDirectphone()
    {
        return directphone;
    }
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getMobile()
    {
        return mobile;
    }
    public void setMailbox(String mailbox)
    {
        this.mailbox = mailbox;
    }

    public String getMailbox()
    {
        return mailbox;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setSerialnumber(Long serialnumber)
    {
        this.serialnumber = serialnumber;
    }

    public Long getSerialnumber()
    {
        return serialnumber;
    }
    public void setQqormsn(String qqormsn)
    {
        this.qqormsn = qqormsn;
    }

    public String getQqormsn()
    {
        return qqormsn;
    }
    public void setWorkingplace(String workingplace)
    {
        this.workingplace = workingplace;
    }

    public String getWorkingplace()
    {
        return workingplace;
    }
    public void setPost(String post)
    {
        this.post = post;
    }

    public String getPost()
    {
        return post;
    }
    public void setRank(String rank)
    {
        this.rank = rank;
    }

    public String getRank()
    {
        return rank;
    }
    public void setRankLv()
    {
        this.rankLv = this.rank;
    }

    public String getRankLv()
    {
        return rankLv;
    }
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }

    public Date getBirthday()
    {
        return birthday;
    }
    public void setEmployeddate(Date employeddate)
    {
        this.employeddate = employeddate;
    }

    public Date getEmployeddate()
    {
        return employeddate;
    }
    public void setDimissiondate(Date dimissiondate)
    {
        this.dimissiondate = dimissiondate;
    }

    public Date getDimissiondate()
    {
        return dimissiondate;
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
    public void setPhotograph(String photograph)
    {
        this.photograph = photograph;
    }

    public String getPhotograph()
    {
        return photograph;
    }
    public void setMaindepartment(String maindepartment)
    {
        this.maindepartment = maindepartment;
    }

    public String getMaindepartment()
    {
        return maindepartment;
    }
    public void setSuperiorleader(String superiorleader)
    {
        this.superiorleader = superiorleader;
    }

    public String getSuperiorleader()
    {
        return superiorleader;
    }
    public void setStafftype(String stafftype)
    {
        this.stafftype = stafftype;
    }

    public String getStafftype()
    {
        return stafftype;
    }
    public void setIdiograph(String idiograph)
    {
        this.idiograph = idiograph;
    }

    public String getIdiograph()
    {
        return idiograph;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }
    public void setOaid(String oaid)
    {
        this.oaid = oaid;
    }

    public String getOaid()
    {
        return oaid;
    }
    public void setDpdn(String dpdn)
    {
        this.dpdn = dpdn;
    }

    public String getDpdn()
    {
        return dpdn;
    }
    public void setDisplayname(String displayname)
    {
        this.displayname = displayname;
    }

    public String getDisplayname()
    {
        return displayname;
    }
    public void setDn(String dn)
    {
        this.dn = dn;
    }

    public String getDn()
    {
        return dn;
    }
    public void setTamuid(String tamuid)
    {
        this.tamuid = tamuid;
    }

    public String getTamuid()
    {
        return tamuid;
    }
    public void setOamailserver(String oamailserver)
    {
        this.oamailserver = oamailserver;
    }

    public String getOamailserver()
    {
        return oamailserver;
    }
    public void setOamailfile(String oamailfile)
    {
        this.oamailfile = oamailfile;
    }

    public String getOamailfile()
    {
        return oamailfile;
    }
    public void setOacomment(String oacomment)
    {
        this.oacomment = oacomment;
    }

    public String getOacomment()
    {
        return oacomment;
    }
    public void setNationalid(String nationalid)
    {
        this.nationalid = nationalid;
    }

    public String getNationalid()
    {
        return nationalid;
    }
    public void setUniusercode(String uniusercode)
    {
        this.uniusercode = uniusercode;
    }

    public String getUniusercode()
    {
        return uniusercode;
    }
    public void setOrgcodeofuser(String orgcodeofuser)
    {
        this.orgcodeofuser = orgcodeofuser;
    }

    public String getOrgcodeofuser()
    {
        return orgcodeofuser;
    }
    public void setCloseaccount(String closeaccount)
    {
        this.closeaccount = closeaccount;
    }

    public String getCloseaccount()
    {
        return closeaccount;
    }
    public void setUsercreatedate(String usercreatedate)
    {
        this.usercreatedate = usercreatedate;
    }

    public String getUsercreatedate()
    {
        return usercreatedate;
    }
    public void setUsermodifydate(String usermodifydate)
    {
        this.usermodifydate = usermodifydate;
    }

    public String getUsermodifydate()
    {
        return usermodifydate;
    }
    public void setUserdeletedate(Date userdeletedate)
    {
        this.userdeletedate = userdeletedate;
    }

    public Date getUserdeletedate()
    {
        return userdeletedate;
    }
    public void setPositioncatcode(String positioncatcode)
    {
        this.positioncatcode = positioncatcode;
    }

    public String getPositioncatcode()
    {
        return positioncatcode;
    }
    public void setPositioncat(String positioncat)
    {
        this.positioncat = positioncat;
    }

    public String getPositioncat()
    {
        return positioncat;
    }
    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getPosition()
    {
        return position;
    }
    public void setOrgidofuser(String orgidofuser)
    {
        this.orgidofuser = orgidofuser;
    }

    public String getOrgidofuser()
    {
        return orgidofuser;
    }
    public void setUnideptcode(String unideptcode)
    {
        this.unideptcode = unideptcode;
    }

    public String getUnideptcode()
    {
        return unideptcode;
    }
    public void setRid(Long rid)
    {
        this.rid = rid;
    }

    public Long getRid()
    {
        return rid;
    }

    public String getDepEnName() {
        return depEnName;
    }

    public void setDepEnName(String depEnName) {
        this.depEnName = depEnName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userid", getUserid())
            .append("loginid", getLoginid())
            .append("erpid", getErpid())
            .append("username", getUsername())
            .append("initialcharacter", getInitialcharacter())
            .append("dpid", getDpid())
            .append("phone", getPhone())
            .append("directphone", getDirectphone())
            .append("mobile", getMobile())
            .append("mailbox", getMailbox())
            .append("sex", getSex())
            .append("serialnumber", getSerialnumber())
            .append("qqormsn", getQqormsn())
            .append("workingplace", getWorkingplace())
            .append("post", getPost())
            .append("rank", getRank())
            .append("birthday", getBirthday())
            .append("employeddate", getEmployeddate())
            .append("dimissiondate", getDimissiondate())
            .append("status", getStatus())
            .append("comments", getComments())
            .append("photograph", getPhotograph())
            .append("maindepartment", getMaindepartment())
            .append("superiorleader", getSuperiorleader())
            .append("stafftype", getStafftype())
            .append("idiograph", getIdiograph())
            .append("remarks", getRemarks())
            .append("oaid", getOaid())
            .append("dpdn", getDpdn())
            .append("displayname", getDisplayname())
            .append("dn", getDn())
            .append("tamuid", getTamuid())
            .append("oamailserver", getOamailserver())
            .append("oamailfile", getOamailfile())
            .append("oacomment", getOacomment())
            .append("nationalid", getNationalid())
            .append("uniusercode", getUniusercode())
            .append("orgcodeofuser", getOrgcodeofuser())
            .append("closeaccount", getCloseaccount())
            .append("usercreatedate", getUsercreatedate())
            .append("usermodifydate", getUsermodifydate())
            .append("userdeletedate", getUserdeletedate())
            .append("positioncatcode", getPositioncatcode())
            .append("positioncat", getPositioncat())
            .append("position", getPosition())
            .append("orgidofuser", getOrgidofuser())
            .append("unideptcode", getUnideptcode())
            .append("rid", getRid())
            .toString();
    }
}
