package com.yabushan.form.controller;

import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.form.domain.XunchaUserOpinionInfo;
import com.yabushan.form.service.IXunchaUserOpinionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/userController")
@Api(tags = {"用户常用联系人,常用意见接口"})
@Slf4j
public class UserController {

    @Autowired
    private IXunchaUserOpinionInfoService service;

    /**
     * 新增常用意见接口
     *
     * @param opinion 意见
     */
    @PostMapping("/addOpinion")
    @ApiOperation(value = "1.新增常用意见接口")
    @Log(title = "新增常用意见接口 ", businessType = BusinessType.INSERT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "opinion", value = "常用意见", dataType = "String", required = true),
    })
    public AjaxResult addOpinion(String opinion) {
        if (StringUtils.isEmpty(opinion)) {
            return AjaxResult.error("常用意见不能为空");
        }
        XunchaUserOpinionInfo xunchaUserOpinionInfo = new XunchaUserOpinionInfo();
        xunchaUserOpinionInfo.setOpinion(opinion);
        xunchaUserOpinionInfo.setUserId(SecurityUtils.getUsername());
        List<XunchaUserOpinionInfo> list = service.selectXunchaUserOpinionInfoList(xunchaUserOpinionInfo);
        if (null == list || list.size() == 0) {
            return AjaxResult.success(service.insertXunchaUserOpinionInfo(xunchaUserOpinionInfo));
        } else {
            return AjaxResult.error("该常用意见已存在");
        }
    }

    /**
     * 获取个人常用意见接口
     *
     * @return 个人常用意见
     */
    @PostMapping("/getOpinions")
    @ApiOperation(value = "2.获取个人常用意见接口")
    @Log(title = "获取个人常用意见接口 ", businessType = BusinessType.SELECT)
    public AjaxResult getOpinions() {
        XunchaUserOpinionInfo xunchaUserOpinionInfo = new XunchaUserOpinionInfo();
        xunchaUserOpinionInfo.setUserId(SecurityUtils.getUsername());
        return AjaxResult.success(service.selectXunchaUserOpinionInfoList(xunchaUserOpinionInfo));
    }

}
