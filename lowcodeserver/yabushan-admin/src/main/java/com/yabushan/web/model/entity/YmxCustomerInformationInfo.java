package com.yabushan.web.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yabushan.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description YmxCustomerInformationInfo
 * @Author huihui
 * @Date 2021-03-29 15:21:17
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxCustomerInformationInfo",description = "客户信息实体类")
public class YmxCustomerInformationInfo implements Serializable,Cloneable {
    /**
     * 客户信息表主键
     */
    @ApiModelProperty(value = "客户信息表主键")
    @Excel(name = "客户信息表主键", cellType = Excel.ColumnType.STRING, prompt = "客户信息表主键")
    private String customerInformationId;
    /**
     * 邮箱地址
     */
    @ApiModelProperty(value = "邮箱地址")
    @Excel(name = "邮箱地址", cellType = Excel.ColumnType.STRING, prompt = "邮箱地址")
    private String customerEmail;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Excel(name = "客户名称", cellType = Excel.ColumnType.STRING, prompt = "客户名称")
    private String customerName;
    /**
     * facebookId
     */
    @ApiModelProperty(value = "facebookId")
    @Excel(name = "facebookId", cellType = Excel.ColumnType.STRING, prompt = "facebookId")
    private String customerFacebookId;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createdBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String lastUpdatedBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdatedTime;
}
