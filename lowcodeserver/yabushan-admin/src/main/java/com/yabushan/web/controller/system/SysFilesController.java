package com.yabushan.web.controller.system;

import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.system.service.ISysFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件操作接口")
@RestController
@RequestMapping("/file")
public class SysFilesController {

    @Autowired
    private ISysFilesService sysFilesService;

    @ApiOperation("上传一个文件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult upload(@RequestParam MultipartFile file){
        return sysFilesService.uploadFile(file);
    }
}
