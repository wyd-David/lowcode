package com.yabushan.web.controller.bpm;

import cn.hutool.json.JSONObject;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.web.service.BpmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Bpm流程接口")
@RequestMapping("/bpm")
public class BpmController extends BaseController {

    @Autowired
    private BpmService bpmService;

    @PostMapping("/draft")
    @ApiOperation(value = "BPM拟稿接口", notes = "BPM拟稿接口")
    public AjaxResult draft(String service, String user) {
        JSONObject draft = bpmService.draft(service, user);
        return AjaxResult.success("成功",draft);
    }
    @PostMapping("/start")
    @ApiOperation(value = "BPM发起流程接口", notes = "BPM发起流程接口")
    public AjaxResult start(String service, String title, String user, String nextActivityId, String nextUsers, String approveResult, String approveNote, String chain) {
        JSONObject draft = bpmService.start( service,  title,  user,  nextActivityId,  nextUsers,  approveResult,  approveNote,  chain);
        return AjaxResult.success("成功",draft);
    }
}
