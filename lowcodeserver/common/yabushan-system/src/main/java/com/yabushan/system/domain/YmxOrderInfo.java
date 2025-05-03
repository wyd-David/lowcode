package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单对象 ymx_order_info
 *
 * @author yabushan
 * @date 2021-04-02
 */
public class YmxOrderInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单表主键 */
    private String orderId;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String amazonOrderId;

    /** 商户编号 */
    @Excel(name = "商户编号")
    private String merchantOrderId;

    /** 购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购买日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String orderStatus;

    /** 配送渠道 */
    @Excel(name = "配送渠道")
    private String salesChannel;

    /** 订单渠道 */
    @Excel(name = "订单渠道")
    private String orderChannel;

    /** 路径 */
    @Excel(name = "路径")
    private String url;

    /** 配送服务类型 */
    @Excel(name = "配送服务类型")
    private String shipServiceLevel;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 最小存货单位 */
    @Excel(name = "最小存货单位")
    private String sku;

    /** ASIN码 */
    @Excel(name = "ASIN码")
    private String asin;

    /** 商品状态 */
    @Excel(name = "商品状态")
    private String itemStatus;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 货币 */
    @Excel(name = "货币")
    private String currency;

    /** 商标价格 */
    @Excel(name = "商标价格")
    private Long itemPrice;

    /** 项目税 */
    @Excel(name = "项目税")
    private Long itemTax;

    /** 运费价格 */
    @Excel(name = "运费价格")
    private Long shippingPrice;

    /** 运费税 */
    @Excel(name = "运费税")
    private Long shippingTax;

    /** 礼品包装价格 */
    @Excel(name = "礼品包装价格")
    private Long giftWrapPrice;

    /** 礼品包装税 */
    @Excel(name = "礼品包装税")
    private Long giftWrapTax;

    /** 商品促销折扣 */
    @Excel(name = "商品促销折扣")
    private String itemPromotionDiscount;

    /** 运费促销折扣 */
    @Excel(name = "运费促销折扣")
    private String shipPromotionDiscount;

    /** 货主地址 */
    @Excel(name = "货主地址")
    private String shipCity;

    /** 邮递区号 */
    @Excel(name = "邮递区号")
    private String shipPostalCode;

    /** 运输状态 */
    @Excel(name = "运输状态")
    private String shipState;

    /** 运输国家 */
    @Excel(name = "运输国家")
    private String shipCountry;

    /** 促销编号 */
    @Excel(name = "促销编号")
    private String promotionIds;

    /** 业务订单 */
    @Excel(name = "业务订单")
    private String isBusinessOrder;

    /** 订购单号码 */
    @Excel(name = "订购单号码")
    private String purchaseOrderNumber;

    /** 客户信息表主键 */
    @Excel(name = "客户信息表主键")
    private String customerInformationId;

    /** 价格指定 */
    @Excel(name = "价格指定")
    private String priceDesignation;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String lastUpdatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdatedTime;

    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setAmazonOrderId(String amazonOrderId)
    {
        this.amazonOrderId = amazonOrderId;
    }

    public String getAmazonOrderId()
    {
        return amazonOrderId;
    }
    public void setMerchantOrderId(String merchantOrderId)
    {
        this.merchantOrderId = merchantOrderId;
    }

    public String getMerchantOrderId()
    {
        return merchantOrderId;
    }
    public void setPurchaseDate(Date purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate()
    {
        return purchaseDate;
    }
    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus()
    {
        return orderStatus;
    }
    public void setSalesChannel(String salesChannel)
    {
        this.salesChannel = salesChannel;
    }

    public String getSalesChannel()
    {
        return salesChannel;
    }
    public void setOrderChannel(String orderChannel)
    {
        this.orderChannel = orderChannel;
    }

    public String getOrderChannel()
    {
        return orderChannel;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setShipServiceLevel(String shipServiceLevel)
    {
        this.shipServiceLevel = shipServiceLevel;
    }

    public String getShipServiceLevel()
    {
        return shipServiceLevel;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }
    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public String getSku()
    {
        return sku;
    }
    public void setAsin(String asin)
    {
        this.asin = asin;
    }

    public String getAsin()
    {
        return asin;
    }
    public void setItemStatus(String itemStatus)
    {
        this.itemStatus = itemStatus;
    }

    public String getItemStatus()
    {
        return itemStatus;
    }
    public void setQuantity(Long quantity)
    {
        this.quantity = quantity;
    }

    public Long getQuantity()
    {
        return quantity;
    }
    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getCurrency()
    {
        return currency;
    }
    public void setItemPrice(Long itemPrice)
    {
        this.itemPrice = itemPrice;
    }

    public Long getItemPrice()
    {
        return itemPrice;
    }
    public void setItemTax(Long itemTax)
    {
        this.itemTax = itemTax;
    }

    public Long getItemTax()
    {
        return itemTax;
    }
    public void setShippingPrice(Long shippingPrice)
    {
        this.shippingPrice = shippingPrice;
    }

    public Long getShippingPrice()
    {
        return shippingPrice;
    }
    public void setShippingTax(Long shippingTax)
    {
        this.shippingTax = shippingTax;
    }

    public Long getShippingTax()
    {
        return shippingTax;
    }
    public void setGiftWrapPrice(Long giftWrapPrice)
    {
        this.giftWrapPrice = giftWrapPrice;
    }

    public Long getGiftWrapPrice()
    {
        return giftWrapPrice;
    }
    public void setGiftWrapTax(Long giftWrapTax)
    {
        this.giftWrapTax = giftWrapTax;
    }

    public Long getGiftWrapTax()
    {
        return giftWrapTax;
    }
    public void setItemPromotionDiscount(String itemPromotionDiscount)
    {
        this.itemPromotionDiscount = itemPromotionDiscount;
    }

    public String getItemPromotionDiscount()
    {
        return itemPromotionDiscount;
    }
    public void setShipPromotionDiscount(String shipPromotionDiscount)
    {
        this.shipPromotionDiscount = shipPromotionDiscount;
    }

    public String getShipPromotionDiscount()
    {
        return shipPromotionDiscount;
    }
    public void setShipCity(String shipCity)
    {
        this.shipCity = shipCity;
    }

    public String getShipCity()
    {
        return shipCity;
    }
    public void setShipPostalCode(String shipPostalCode)
    {
        this.shipPostalCode = shipPostalCode;
    }

    public String getShipPostalCode()
    {
        return shipPostalCode;
    }
    public void setShipState(String shipState)
    {
        this.shipState = shipState;
    }

    public String getShipState()
    {
        return shipState;
    }
    public void setShipCountry(String shipCountry)
    {
        this.shipCountry = shipCountry;
    }

    public String getShipCountry()
    {
        return shipCountry;
    }
    public void setPromotionIds(String promotionIds)
    {
        this.promotionIds = promotionIds;
    }

    public String getPromotionIds()
    {
        return promotionIds;
    }
    public void setIsBusinessOrder(String isBusinessOrder)
    {
        this.isBusinessOrder = isBusinessOrder;
    }

    public String getIsBusinessOrder()
    {
        return isBusinessOrder;
    }
    public void setPurchaseOrderNumber(String purchaseOrderNumber)
    {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getPurchaseOrderNumber()
    {
        return purchaseOrderNumber;
    }
    public void setCustomerInformationId(String customerInformationId)
    {
        this.customerInformationId = customerInformationId;
    }

    public String getCustomerInformationId()
    {
        return customerInformationId;
    }
    public void setPriceDesignation(String priceDesignation)
    {
        this.priceDesignation = priceDesignation;
    }

    public String getPriceDesignation()
    {
        return priceDesignation;
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
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }
    public void setLastUpdatedTime(Date lastUpdatedTime)
    {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Date getLastUpdatedTime()
    {
        return lastUpdatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("amazonOrderId", getAmazonOrderId())
            .append("merchantOrderId", getMerchantOrderId())
            .append("purchaseDate", getPurchaseDate())
            .append("orderStatus", getOrderStatus())
            .append("salesChannel", getSalesChannel())
            .append("orderChannel", getOrderChannel())
            .append("url", getUrl())
            .append("shipServiceLevel", getShipServiceLevel())
            .append("productName", getProductName())
            .append("sku", getSku())
            .append("asin", getAsin())
            .append("itemStatus", getItemStatus())
            .append("quantity", getQuantity())
            .append("currency", getCurrency())
            .append("itemPrice", getItemPrice())
            .append("itemTax", getItemTax())
            .append("shippingPrice", getShippingPrice())
            .append("shippingTax", getShippingTax())
            .append("giftWrapPrice", getGiftWrapPrice())
            .append("giftWrapTax", getGiftWrapTax())
            .append("itemPromotionDiscount", getItemPromotionDiscount())
            .append("shipPromotionDiscount", getShipPromotionDiscount())
            .append("shipCity", getShipCity())
            .append("shipPostalCode", getShipPostalCode())
            .append("shipState", getShipState())
            .append("shipCountry", getShipCountry())
            .append("promotionIds", getPromotionIds())
            .append("isBusinessOrder", getIsBusinessOrder())
            .append("purchaseOrderNumber", getPurchaseOrderNumber())
            .append("customerInformationId", getCustomerInformationId())
            .append("priceDesignation", getPriceDesignation())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("lastUpdatedBy", getLastUpdatedBy())
            .append("lastUpdatedTime", getLastUpdatedTime())
            .toString();
    }
}
