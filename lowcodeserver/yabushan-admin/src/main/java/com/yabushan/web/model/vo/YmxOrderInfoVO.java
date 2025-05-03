package com.yabushan.web.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description YmxOrderInfoVO
 * @Author huihui
 * @Date 2021-03-29 13:43:31
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxOrderInfoVO",description = "订单匹配接收类")
public class YmxOrderInfoVO implements Serializable,Cloneable{
    /** 订单表主键 */
    @ApiModelProperty(value = "订单表主键")
    private String orderId ;

    @ApiModelProperty(value = "客户信息表主键")
    private String customerInformationId ;

    /** 订单编号 */
    @ApiModelProperty(value = "订单编号")
    private String amazonOrderId ;

    /** 更新人 */
    @JsonIgnore
    @ApiModelProperty(value = "更新人")
    private String lastUpdatedBy ;
    /** 更新时间 */
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdatedTime ;

}
