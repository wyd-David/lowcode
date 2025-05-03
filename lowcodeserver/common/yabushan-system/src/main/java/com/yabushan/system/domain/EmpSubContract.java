package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工合同子集对象 emp_sub_contract
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubContract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private String recId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 排序显示 */
    @Excel(name = "排序显示")
    private Long sortIndex;

    /** 合同期限类型 */
    @Excel(name = "合同期限类型")
    private Long deadlineType;

    /** 合同终止（解除）时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同终止", readConverterExp = "解=除")
    private Date terminateDate;

    /** 合同终止（解除）情况 */
    @Excel(name = "合同终止", readConverterExp = "解=除")
    private Long terminateCircs;

    /** 合同号 */
    @Excel(name = "合同号")
    private String contractNo;

    /** 合同类型 */
    @Excel(name = "合同类型")
    private Long contractType;

    /** 合同到期是否续签 */
    @Excel(name = "合同到期是否续签")
    private Long continueFlag;

    /** 合同终止（解除）文件依据 */
    @Excel(name = "合同终止", readConverterExp = "解=除")
    private String terminateGist;

    /** 试用期期限（月） */
    @Excel(name = "试用期期限", readConverterExp = "月=")
    private Long probationershipTerm;

    /** 合同备注 */
    @Excel(name = "合同备注")
    private String memo;

    /** 合同工作岗位 */
    @Excel(name = "合同工作岗位")
    private String contractPost;

    /** 合同甲方公司地点 */
    @Excel(name = "合同甲方公司地点")
    private String asideOfficeAddr;

    /** 合同工资 */
    @Excel(name = "合同工资")
    private String contractPay;

    /** 签订合同日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签订合同日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date contractSignedDate;

    /** 合同状态 数据字典 */
    @Excel(name = "合同状态 数据字典")
    private Long contractStatus;

    /** 合同性质 数据字典 */
    @Excel(name = "合同性质 数据字典")
    private Long contractNature;

    private String deadlineTypeName;
    private String  contractTypeName;
    private String continueFlagName;
    private String contractStatusName;
    private String contractNatureName;


    public void setRecId(String recId)
    {
        this.recId = recId;
    }

    public String getRecId()
    {
        return recId;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setSortIndex(Long sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public Long getSortIndex()
    {
        return sortIndex;
    }
    public void setDeadlineType(Long deadlineType)
    {
        this.deadlineType = deadlineType;
    }

    public Long getDeadlineType()
    {
        return deadlineType;
    }
    public void setTerminateDate(Date terminateDate)
    {
        this.terminateDate = terminateDate;
    }

    public Date getTerminateDate()
    {
        return terminateDate;
    }
    public void setTerminateCircs(Long terminateCircs)
    {
        this.terminateCircs = terminateCircs;
    }

    public Long getTerminateCircs()
    {
        return terminateCircs;
    }
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }

    public String getContractNo()
    {
        return contractNo;
    }
    public void setContractType(Long contractType)
    {
        this.contractType = contractType;
    }

    public Long getContractType()
    {
        return contractType;
    }
    public void setContinueFlag(Long continueFlag)
    {
        this.continueFlag = continueFlag;
    }

    public Long getContinueFlag()
    {
        return continueFlag;
    }
    public void setTerminateGist(String terminateGist)
    {
        this.terminateGist = terminateGist;
    }

    public String getTerminateGist()
    {
        return terminateGist;
    }
    public void setProbationershipTerm(Long probationershipTerm)
    {
        this.probationershipTerm = probationershipTerm;
    }

    public Long getProbationershipTerm()
    {
        return probationershipTerm;
    }
    public void setMemo(String memo)
    {
        this.memo = memo;
    }

    public String getMemo()
    {
        return memo;
    }
    public void setContractPost(String contractPost)
    {
        this.contractPost = contractPost;
    }

    public String getContractPost()
    {
        return contractPost;
    }
    public void setAsideOfficeAddr(String asideOfficeAddr)
    {
        this.asideOfficeAddr = asideOfficeAddr;
    }

    public String getAsideOfficeAddr()
    {
        return asideOfficeAddr;
    }
    public void setContractPay(String contractPay)
    {
        this.contractPay = contractPay;
    }

    public String getContractPay()
    {
        return contractPay;
    }
    public void setContractSignedDate(Date contractSignedDate)
    {
        this.contractSignedDate = contractSignedDate;
    }

    public Date getContractSignedDate()
    {
        return contractSignedDate;
    }
    public void setContractStatus(Long contractStatus)
    {
        this.contractStatus = contractStatus;
    }

    public Long getContractStatus()
    {
        return contractStatus;
    }
    public void setContractNature(Long contractNature)
    {
        this.contractNature = contractNature;
    }

    public Long getContractNature()
    {
        return contractNature;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("sortIndex", getSortIndex())
            .append("deadlineType", getDeadlineType())
            .append("terminateDate", getTerminateDate())
            .append("terminateCircs", getTerminateCircs())
            .append("contractNo", getContractNo())
            .append("contractType", getContractType())
            .append("continueFlag", getContinueFlag())
            .append("terminateGist", getTerminateGist())
            .append("probationershipTerm", getProbationershipTerm())
            .append("memo", getMemo())
            .append("contractPost", getContractPost())
            .append("asideOfficeAddr", getAsideOfficeAddr())
            .append("contractPay", getContractPay())
            .append("contractSignedDate", getContractSignedDate())
            .append("contractStatus", getContractStatus())
            .append("contractNature", getContractNature())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public String getDeadlineTypeName() {
        return deadlineTypeName;
    }

    public void setDeadlineTypeName(String deadlineTypeName) {
        this.deadlineTypeName = deadlineTypeName;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getContinueFlagName() {
        return continueFlagName;
    }

    public void setContinueFlagName(String continueFlagName) {
        this.continueFlagName = continueFlagName;
    }

    public String getContractStatusName() {
        return contractStatusName;
    }

    public void setContractStatusName(String contractStatusName) {
        this.contractStatusName = contractStatusName;
    }

    public String getContractNatureName() {
        return contractNatureName;
    }

    public void setContractNatureName(String contractNatureName) {
        this.contractNatureName = contractNatureName;
    }
}
