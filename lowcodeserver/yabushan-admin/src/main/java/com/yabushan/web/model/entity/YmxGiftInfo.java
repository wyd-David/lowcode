package com.yabushan.web.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description YmxGiftInfo
 * @Author huihui
 * @Date 2021-03-29 16:22:52
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxGiftInfo",description = "礼物实体类")
public class YmxGiftInfo implements Serializable,Cloneable {
    /**
     * 礼物表主键
     */
    @ApiModelProperty(value = "礼物表主键")
    private String giftId;
    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String giftName;
    /**
     * 产品图片
     */
    @ApiModelProperty(value = "产品图片")
    private String giftImage;
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