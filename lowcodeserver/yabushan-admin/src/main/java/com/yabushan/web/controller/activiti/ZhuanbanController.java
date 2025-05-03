package com.yabushan.web.controller.activiti;

import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.ViewEmpInfo;
import com.yabushan.system.service.IViewEmpInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/xuncha/XunchaLiaisonsManage")
@Api(tags = {"联络员维护 "})
@Slf4j
public class ZhuanbanController {
    @Autowired
    private IViewEmpInfoService viewEmpInfoService;
    /**
     * 选择联络员下拉框
     */
    @ApiOperation("选择联络员下拉框")
    @GetMapping("/selectLiaisonsData")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名，模糊查询", dataType = "String", required = false),
            @ApiImplicitParam(name = "type", value = "0 根据当前用户部门查询；1 根据当前用户单位查询", dataType = "0 or 1", required = false),
    })
    public AjaxResult selectLiaisonsData(String username, String type) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        List<Map<String, Object>> result = viewEmpInfoService.selectLiaisonsData(map);
        return AjaxResult.success(result);
    }
}
