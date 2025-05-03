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
 * @Description YmxInformationOptionsInfo
 * @Author huihui
 * @Date 2021-s03-30 14:31:39
 * @Version 1.0
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="YmxInformationOptionsInfo",description = "个人信息选项实体类")
public class YmxInformationOptionsInfo implements Serializable,Cloneable {
    private static final long serialVersionUID = 1L;

    /** 个人信息选项表主键 */
    @ApiModelProperty(value = "个人信息选项表主键")
    private String optionsId;

    /** 选项类型 */
    @Excel(name = "选项类型")
    @ApiModelProperty(value = "选项类型")
    private String optionsType;

    /** 选项名称 */
    @Excel(name = "选项名称")
    @ApiModelProperty(value = "选项名称")
    private String optionsName;

    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty(value = "礼物表主键")
    private String createdBy;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty(value = "更新人")
    private String lastUpdatedBy;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdatedTime;

}
