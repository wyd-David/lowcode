package com.yabushan.common.bpm.controller;


import com.yabushan.common.bpm.model.DocParams;
import com.yabushan.common.bpm.model.FlowParams;
import com.yabushan.common.bpm.model.FlowResult;
import com.yabushan.common.bpm.service.DocumentService;
import com.yabushan.common.bpm.service.FlowService;
import com.yabushan.common.bpm.utils.ToolUtil;
import com.yabushan.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flow")
@Slf4j
@Api(tags = {"BPM测试"})
public class FlowController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private FlowService flowService;


    @ApiOperation(value = "创建文档", notes = "创建文档", httpMethod = "POST")
    @RequestMapping(value = "/createDoc", method = RequestMethod.POST)
    public AjaxResult createDoc(DocParams docParams)
    {
        try {
            String result = documentService.create(docParams);
            FlowResult flowResult = ToolUtil.dealWithReturn(result);
            return AjaxResult.success(flowResult);
        }catch (Exception e){
            log.error(e.toString());
            return  AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation(value = "开启流程", notes = "开启流程", httpMethod = "POST")
    @RequestMapping(value = "/startFlow", method = RequestMethod.POST)
    public AjaxResult startFlow(FlowParams dto)
    {
        try {
            String start = flowService.start(dto);
            FlowResult flowResult = ToolUtil.dealWithReturn(start);
            return AjaxResult.success(flowResult);
        }catch (Exception e){
            log.error(e.toString());
            return  AjaxResult.error(e.getMessage());
        }
    }


    @ApiOperation(value = "打开流程文档", notes = "打开流程文档", httpMethod = "POST")
    @RequestMapping(value = "/openDoc", method = RequestMethod.POST)
    public AjaxResult openDoc(DocParams docParams)
    {
        try {
            String open = documentService.open(docParams);
            FlowResult flowResult = ToolUtil.dealWithReturn(open);
            return AjaxResult.success(flowResult);
        }catch (Exception e){
            log.error(e.toString());
            return  AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation(value = "提交流程", notes = "提交流程", httpMethod = "POST")
    @RequestMapping(value = "/commitFlow", method = RequestMethod.POST)
    public AjaxResult commitFlow(FlowParams dto)
    {
        try {
            String commit = flowService.commit(dto);
            FlowResult flowResult = ToolUtil.dealWithReturn(commit);
            return AjaxResult.success(flowResult);
        }catch (Exception e){
            log.error(e.toString());
            return  AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation(value = "回退流程", notes = "回退流程", httpMethod = "POST")
    @RequestMapping(value = "/rejectFlow", method = RequestMethod.POST)
    public AjaxResult rejectFlow(FlowParams dto)
    {
        try {
            String reject = flowService.reject(dto);
            FlowResult flowResult = ToolUtil.dealWithReturn(reject);
            return AjaxResult.success(flowResult);
        }catch (Exception e){
            log.error(e.toString());
            return  AjaxResult.error(e.getMessage());
        }
    }

}
