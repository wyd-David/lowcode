package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文件上传记录对象 import_file_info
 *
 * @author 姜森焱
 * @date 2021-09-08
 */

@ApiModel(value = "文件上传记录对象")
public class ImportFileInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private Long id;
    /**
     * 文件名
     */
    @Excel(name = "文件名")
    @ApiModelProperty("文件名")
    private String fileName;
    /**
     * 文件key值
     */
    @Excel(name = "文件key值")
    @ApiModelProperty("文件key值")
    private String fileKey;
    /**
     * 业务id
     */
    @Excel(name = "业务id")
    @ApiModelProperty("业务id")
    private String businessKey;
    /**
     * 所属模块
     */
    @Excel(name = "所属模块")
    @ApiModelProperty("所属模块")
    private String applyId;
    /**
     * 上传人
     */
    @Excel(name = "上传人")
    @ApiModelProperty("上传人")
    private String uploader;
    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    @ApiModelProperty("文件大小")
    private String fileSize;
    /**
     * 文件状态,关联字典0删除1启用2未启用
     */
    @Excel(name = "文件状态,关联字典0删除1启用2未启用")
    @ApiModelProperty("文件状态,关联字典0删除1启用2未启用")
    private String fileState;
    @Excel(name = "导入状态")
    private String importStatus;
    /**
     * 压缩文件的图片key
     */
    private String compressFileKey;

    public String getCompressFileKey() {
        return compressFileKey;
    }

    public void setCompressFileKey(String compressFileKey) {
        this.compressFileKey = compressFileKey;
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUploader() {
        return uploader;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileState(String fileState) {
        this.fileState = fileState;
    }

    public String getFileState() {
        return fileState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("fileName", getFileName())
                .append("fileKey", getFileKey())
                .append("businessKey", getBusinessKey())
                .append("applyId", getApplyId())
                .append("uploader", getUploader())
                .append("createTime", getCreateTime())
                .append("fileSize", getFileSize())
                .append("fileState", getFileState())
                .toString();
    }
}
