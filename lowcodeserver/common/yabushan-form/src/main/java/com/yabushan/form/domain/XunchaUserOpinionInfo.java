package com.yabushan.form.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 常用意见对象 xuncha_user_opinion_info
 *
 * @author CJH
 * @date 2022-08-04
 */

@ApiModel(value = "常用意见对象")
public class XunchaUserOpinionInfo {


    /**
     * 用户id
     */
    @Excel(name = "用户id")
    @ApiModelProperty("用户id")
    private String userId;
    /**
     * 用户姓名
     */
    @Excel(name = "用户姓名")
    @ApiModelProperty("用户姓名")
    private String userName;
    /**
     * 意见
     */
    @Excel(name = "意见")
    @ApiModelProperty("意见")
    private String opinion;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
