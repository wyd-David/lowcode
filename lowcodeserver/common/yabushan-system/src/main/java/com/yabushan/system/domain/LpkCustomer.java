package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 外卖对象 lpk_customer
 *
 * @author yabushan
 * @date 2021-06-05
 */
public class LpkCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long keyId;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String goodsName;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobileNum;

    /** 接受礼品方式,1:银行卡，2：微信，3：支付宝 */
    @Excel(name = "接受礼品方式,1:银行卡，2：微信，3：支付宝")
    private Long payWay;

    /** 收款码图片 */
    @Excel(name = "收款码图片")
    private String payImg;

    /** 五星好评截图 */
    @Excel(name = "五星好评截图")
    private String goodsImg;

    /** 宝贵意见 */
    @Excel(name = "宝贵意见")
    private String customAdvice;

    /** 是否已返现,0:否，1：是 */
    @Excel(name = "是否已返现,0:否，1：是")
    private Long isPay;

    /** 返现时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "返现时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /** 返现金额 */
    @Excel(name = "返现金额")
    private Long payMoney;

    /** 所属商户 */
    private String telantId;

    /** 星级数 */
    @Excel(name = "星级数")
    private Long starNum;

    public void setKeyId(Long keyId)
    {
        this.keyId = keyId;
    }

    public Long getKeyId()
    {
        return keyId;
    }
    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName()
    {
        return goodsName;
    }
    public void setMobileNum(String mobileNum)
    {
        this.mobileNum = mobileNum;
    }

    public String getMobileNum()
    {
        return mobileNum;
    }
    public void setPayWay(Long payWay)
    {
        this.payWay = payWay;
    }

    public Long getPayWay()
    {
        return payWay;
    }
    public void setPayImg(String payImg)
    {
        this.payImg = payImg;
    }

    public String getPayImg()
    {
        return payImg;
    }
    public void setGoodsImg(String goodsImg)
    {
        this.goodsImg = goodsImg;
    }

    public String getGoodsImg()
    {
        return goodsImg;
    }
    public void setCustomAdvice(String customAdvice)
    {
        this.customAdvice = customAdvice;
    }

    public String getCustomAdvice()
    {
        return customAdvice;
    }
    public void setIsPay(Long isPay)
    {
        this.isPay = isPay;
    }

    public Long getIsPay()
    {
        return isPay;
    }
    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }

    public Date getPayTime()
    {
        return payTime;
    }
    public void setPayMoney(Long payMoney)
    {
        this.payMoney = payMoney;
    }

    public Long getPayMoney()
    {
        return payMoney;
    }
    public void setTelantId(String telantId)
    {
        this.telantId = telantId;
    }

    public String getTelantId()
    {
        return telantId;
    }
    public void setStarNum(Long starNum)
    {
        this.starNum = starNum;
    }

    public Long getStarNum()
    {
        return starNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("keyId", getKeyId())
            .append("goodsName", getGoodsName())
            .append("mobileNum", getMobileNum())
            .append("payWay", getPayWay())
            .append("payImg", getPayImg())
            .append("goodsImg", getGoodsImg())
            .append("customAdvice", getCustomAdvice())
            .append("isPay", getIsPay())
            .append("payTime", getPayTime())
            .append("createTime", getCreateTime())
            .append("payMoney", getPayMoney())
            .append("telantId", getTelantId())
            .append("starNum", getStarNum())
            .toString();
    }
}
