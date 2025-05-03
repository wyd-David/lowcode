package com.yabushan.system.domain;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文件 对象 sys_files
 *
 * @author yabushan
 * @date 2022-05-16
 */
@ApiModel(value = "文件 对象")
public class SysFiles extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 乐观锁 */
    @ApiModelProperty("乐观锁")
    private String fileId;
    /** 文件上传名称 */
    @Excel(name = "文件上传名称")
    @ApiModelProperty("文件上传名称")
    private String fileName;
    /** 桶名称 */
    @Excel(name = "桶名称")
    @ApiModelProperty("桶名称")
    private String bucket;
    /** 文件地址 */
    @Excel(name = "文件地址")
    @ApiModelProperty("文件地址")
    private String fileUrl;
    /** 文件类型 */
    @Excel(name = "文件类型")
    @ApiModelProperty("文件类型")
    private String fileExt;
    /** 文件大小 */
    @Excel(name = "文件大小")
    @ApiModelProperty("文件大小")
    private Long fileSize;
    /** 服务类型 */
    @Excel(name = "服务类型")
    @ApiModelProperty("服务类型")
    private String severType;
    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdTime;
    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private String updatedBy;
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("更新时间")
    private Date updatedTime;
    /** 删除标志 0存在；1删除； */
    @ApiModelProperty("删除标志 0存在；1删除；")
    private String delFlag="0";
    @Excel(name = "导入状态")
    private String importStatus;

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }
    /**
     * 禁用状态  0启用,1禁用
     */
    private String disableEnableState;

    public String getDisableEnableState() {
        return disableEnableState;
    }

    public void setDisableEnableState(String disableEnableState) {
        this.disableEnableState = disableEnableState;
    }
    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public String getFileId()
    {
        return fileId;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
    public void setBucket(String bucket)
    {
        this.bucket = bucket;
    }

    public String getBucket()
    {
        return bucket;
    }
    public void setFileUrl(String fileUrl)
    {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl()
    {
        return fileUrl;
    }
    public void setFileExt(String fileExt)
    {
        this.fileExt = fileExt;
    }

    public String getFileExt()
    {
        return fileExt;
    }
    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
    }

    public Long getFileSize()
    {
        return fileSize;
    }
    public void setSeverType(String severType)
    {
        this.severType = severType;
    }

    public String getSeverType()
    {
        return severType;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy()
    {
        return updatedBy;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("fileId", getFileId())
                .append("fileName", getFileName())
                .append("bucket", getBucket())
                .append("fileUrl", getFileUrl())
                .append("fileExt", getFileExt())
                .append("fileSize", getFileSize())
                .append("severType", getSeverType())
                .append("createdBy", getCreatedBy())
                .append("createdTime", getCreatedTime())
                .append("updatedBy", getUpdatedBy())
                .append("updatedTime", getUpdatedTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
