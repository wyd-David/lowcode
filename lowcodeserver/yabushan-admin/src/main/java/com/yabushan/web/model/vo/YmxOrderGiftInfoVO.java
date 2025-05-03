package com.yabushan.web.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description YmxOrderGiftInfoVO
 * @Author huihui
 * @Date 2021-03-29 16:29:38
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxOrderGiftInfoVO",description = "订单礼物查询接收类")
public class YmxOrderGiftInfoVO implements Serializable,Cloneable {
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
}