package com.yabushan.web.controller.datasource;

import com.yabushan.common.annotation.Log;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.model.LoginBody;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.datasource.Vo.*;
import com.yabushan.datasource.domain.DataStructuredColumn;
import com.yabushan.datasource.domain.DigDataResource;
import com.yabushan.datasource.service.DigDatasourceService;
import com.yabushan.datasource.service.DynamicDataSourceFactory;
import com.yabushan.datasource.utils.SQLValidator;
import com.yabushan.framework.web.service.SysLoginService;
import com.yabushan.system.domain.DataApiInfos;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "数据源服务类")
@RequestMapping("/wxdatasource")
public class WeixinDigDatasourceController extends BaseController {
    
    @Autowired
    private DigDatasourceService digDatasourceService;

    /**
     * 
     * 新增
     * @param params
     * @return
     */
    @ApiOperation("新增")
    @Log(title = "新增接口META", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public AjaxResult insert(@RequestBody  Map params) {
        try {
          return  digDatasourceService.insert(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

    }
    
    
    @ApiOperation("修改接口")
    @Log(title = "修改接口META", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResult update(@RequestBody  Map params) {
        try {
           return  digDatasourceService.update(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

    }
    

    @ApiOperation("删除接口")
    @Log(title = "删除接口META", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public AjaxResult delete(@RequestBody  Map params) {
        try {
          return  digDatasourceService.delete(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }
    
    //查询
    @ApiOperation("查询接口")
    @Log(title = "查询接口META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public AjaxResult select(@RequestBody  Map params) {
        try {
            return digDatasourceService.select(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }


    //分页查询
    @ApiOperation("分页查询接口")
    @Log(title = "分页查询接口META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/pageSelect", method = RequestMethod.POST)
    public AjaxResult pageSelect(@RequestBody  Map params) {
        try {
            return  digDatasourceService.pageSelect(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }


    


    
}
