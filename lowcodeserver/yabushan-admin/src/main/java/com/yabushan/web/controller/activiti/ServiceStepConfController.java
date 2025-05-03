package com.yabushan.web.controller.activiti;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.system.domain.ServiceStepConf;
import com.yabushan.system.service.IServiceStepConfService;
import com.yabushan.common.utils.poi.ExcelUtil;
import com.yabushan.common.core.page.TableDataInfo;

/**
 * stepController
 *
 * @author yabushan
 * @date 2021-04-21
 */
@RestController
@RequestMapping("/workflow/conf")
public class ServiceStepConfController extends BaseController
{
    @Autowired
    private IServiceStepConfService serviceStepConfService;

    /**
     * 查询step列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:conf:list')")
    @GetMapping("/list")
    public TableDataInfo list(ServiceStepConf serviceStepConf)
    {
        startPage();
        List<ServiceStepConf> list = serviceStepConfService.selectServiceStepConfList(serviceStepConf);
        return getDataTable(list);
    }

    /**
     * 导出step列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:conf:export')")
    @Log(title = "step", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ServiceStepConf serviceStepConf)
    {
        List<ServiceStepConf> list = serviceStepConfService.selectServiceStepConfList(serviceStepConf);
        ExcelUtil<ServiceStepConf> util = new ExcelUtil<ServiceStepConf>(ServiceStepConf.class);
        return util.exportExcel(list, "conf");
    }

    /**
     * 获取step详细信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:conf:query')")
    @GetMapping(value = "/{keyId}")
    public AjaxResult getInfo(@PathVariable("keyId") Long keyId)
    {
        return AjaxResult.success(serviceStepConfService.selectServiceStepConfById(keyId));
    }

    /**
     * 新增step
     */
    @PreAuthorize("@ss.hasPermi('workflow:conf:add')")
    @Log(title = "step", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ServiceStepConf serviceStepConf)
    {
        return toAjax(serviceStepConfService.insertServiceStepConf(serviceStepConf));
    }

    /**
     * 修改step
     */
    @PreAuthorize("@ss.hasPermi('workflow:conf:edit')")
    @Log(title = "step", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ServiceStepConf serviceStepConf)
    {
        return toAjax(serviceStepConfService.updateServiceStepConf(serviceStepConf));
    }

    /**
     * 删除step
     */
    @PreAuthorize("@ss.hasPermi('workflow:conf:remove')")
    @Log(title = "step", businessType = BusinessType.DELETE)
	@DeleteMapping("/{keyIds}")
    public AjaxResult remove(@PathVariable Long[] keyIds)
    {
        return toAjax(serviceStepConfService.deleteServiceStepConfByIds(keyIds));
    }
}
