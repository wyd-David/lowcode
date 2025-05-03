package com.yabushan.web.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description YmxCustomerInformationInfoVo
 * @Author huihui
 * @Date 2021-03-29 15:27:23
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxCustomerInformationInfoVo",description = "录入客户个人信息接收类")
public class YmxCustomerInformationInfoVo implements Serializable,Cloneable {

    @ApiModelProperty(value = "邮箱地址")
    private String customerEmail;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    private String customerName;
    /**
     * facebookId
     */
    @ApiModelProperty(value = "facebookId")
    private String customerFacebookId;

    /** 订单表主键 */
    @ApiModelProperty(value = "订单表主键")
    private String orderId ;
}
