package com.yabushan.web.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ApiModel(value="YmxCustomerGiftInfoVO",description = "客户信息实体类")
public class YmxCustomerGiftInfoVO implements Serializable,Cloneable {
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

    /** 订单表主键 */
    @ApiModelProperty(value = "订单表主键")
    private String orderId ;

    /** 创建人 */
    @JsonIgnore
    @ApiModelProperty(value = "创建人")
    private String createdBy ;
    /** 创建时间 */
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime ;

}
