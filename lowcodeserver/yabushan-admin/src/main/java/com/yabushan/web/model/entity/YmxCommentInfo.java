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
 * @Description YmxCommentInfo
 * @Author huihui
 * @Date 2021-03-29 17:43:20
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxCommentInfo",description = "评论表实体类")
public class YmxCommentInfo implements Serializable,Cloneable {
    private static final long serialVersionUID = 1L;

    /** 评论表主键 */
    @ApiModelProperty(value = "评论表主键")
    private String commentId;

    /** 订单表主键 */
    @ApiModelProperty(value = "订单表主键")
    @Excel(name = "订单表主键")
    private String orderId;

    /** 评论图片 */
    @ApiModelProperty(value = "评论图片")
    @Excel(name = "评论图片")
    private String commentImage;

    /** 评论星级 */
    @ApiModelProperty(value = "评论星级")
    @Excel(name = "评论星级")
    private String commentStarRating;

    /** 感谢语 */
    @ApiModelProperty(value = "感谢语")
    @Excel(name = "感谢语")
    private String commentThankYou;

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
