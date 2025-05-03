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
 * 客户礼物对象 ymx_customer_gift_info
 *
 * @author yabushan
 * @date 2021-03-29
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxCustomerGiftInfo",description = "客户礼物实体类")
public class YmxCustomerGiftInfo implements Serializable,Cloneable {
    private static final long serialVersionUID = 1L;

    /** 客户礼物表主键 */
    @ApiModelProperty(value = "客户礼物表主键")
    private String customerGiftId;

    /** 客户表主键 */
    @ApiModelProperty(value = "客户表主键")
    @Excel(name = "客户表主键")
    private String customerInformationId;

    /** 礼物表主键 */
    @ApiModelProperty(value = "礼物表主键")
    @Excel(name = "礼物表主键")
    private String giftId;

    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    @Excel(name = "更新人")
    private String lastUpdatedBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdatedTime;


}
