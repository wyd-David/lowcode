package com.yabushan.web.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description YmxOrderInfo
 * @Author huihui
 * @Date 2021-03-29 13:42:25
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxOrderInfo",description = "订单表实体类")
public class YmxOrderInfo implements Serializable,Cloneable{
    /** 订单表主键 */
    @ApiModelProperty(value = "订单表主键")
    private String orderId ;
    /** 订单编号 */
    @ApiModelProperty(value = "订单编号")
    private String amazonOrderId ;
    /** 商户编号 */
    @ApiModelProperty(value = "商户编号")
    private String merchantOrderId ;
    /** 购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "购买日期")
    private Date purchaseDate ;
    /** 订单状态 */
    @ApiModelProperty(value = "订单状态")
    private String orderStatus ;
    /** 配送渠道 */
    @ApiModelProperty(value = "配送渠道")
    private String salesChannel ;
    /** 订单渠道 */
    @ApiModelProperty(value = "订单渠道")
    private String orderChannel ;
    /** 路径 */
    @ApiModelProperty(value = "路径")
    private String url ;
    /** 配送服务类型 */
    @ApiModelProperty(value = "配送服务类型")
    private String shipServiceLevel ;
    /** 产品名称 */
    @ApiModelProperty(value = "产品名称")
    private String productName ;
    /** 最小存货单位 */
    @ApiModelProperty(value = "最小存货单位")
    private String sku ;
    /** ASIN码 */
    @ApiModelProperty(value = "ASIN码")
    private String asin ;
    /** 商品状态 */
    @ApiModelProperty(value = "商品状态")
    private String itemStatus ;
    /** 数量 */
    @ApiModelProperty(value = "数量")
    private Integer quantity ;
    /** 货币 */
    @ApiModelProperty(value = "货币")
    private Integer currency ;
    /** 商标价格 */
    @ApiModelProperty(value = "商标价格")
    private Integer itemPrice ;
    /** 项目税 */
    @ApiModelProperty(value = "项目税")
    private Integer itemTax ;
    /** 运费价格 */
    @ApiModelProperty(value = "运费价格")
    private Integer shippingPrice ;
    /** 运费税 */
    @ApiModelProperty(value = "运费税")
    private Integer shippingTax ;
    /** 礼品包装价格 */
    @ApiModelProperty(value = "礼品包装价格")
    private Integer giftWrapPrice ;
    /** 礼品包装税 */
    @ApiModelProperty(value = "礼品包装税")
    private Integer giftWrapTax ;
    /** 商品促销折扣 */
    @ApiModelProperty(value = "商品促销折扣")
    private String itemPromotionDiscount ;
    /** 运费促销折扣 */
    @ApiModelProperty(value = "运费促销折扣")
    private String shipPromotionDiscount ;
    /** 货主地址 */
    @ApiModelProperty(value = "货主地址")
    private String shipCity ;
    /** 邮递区号 */
    @ApiModelProperty(value = "邮递区号")
    private String shipPostalCode ;
    /** 运输状态 */
    @ApiModelProperty(value = "运输状态")
    private String shipState ;
    /** 运输国家 */
    @ApiModelProperty(value = "运输国家")
    private String shipCountry ;
    /** 促销编号 */
    @ApiModelProperty(value = "促销编号")
    private String promotionIds ;
    /** 业务订单 */
    @ApiModelProperty(value = "业务订单")
    private String isBusinessOrder ;
    /** 订购单号码 */
    @ApiModelProperty(value = "订购单号码")
    private String purchaseOrderNumber ;
    /** 价格指定 */
    @ApiModelProperty(value = "价格指定")
    private String priceDesignation ;
    /** 客户信息表主键 */
    @ApiModelProperty(value = "客户信息表主键")
    private String customerInformationId ;
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private String createdBy ;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime ;
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private String lastUpdatedBy ;
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date lastUpdatedTime ;
}