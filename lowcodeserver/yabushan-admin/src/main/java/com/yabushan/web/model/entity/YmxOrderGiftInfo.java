package com.yabushan.web.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description class
 * @Author huihui
 * @Date 2021-03-29 16:28:42
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxOrderGiftInfo",description = "订单礼物表实体类")
public class YmxOrderGiftInfo implements Serializable,Cloneable {
    /**
     * 订单礼物表主键
     */
    @ApiModelProperty(value = "订单礼物表主键")
    private String orderGiftId;
    /**
     * 订单表主键
     */
    @ApiModelProperty(value = "订单表主键")
    private String orderId;
    /**
     * 礼物表主键
     */
    @ApiModelProperty(value = "礼物表主键")
    private String giftId;
    /**
     * ASIN码
     */
    @ApiModelProperty(value = "ASIN码")
    private String asin;
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