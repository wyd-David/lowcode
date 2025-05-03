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
import com.yabushan.system.domain.ActHiTaskinst;
import com.yabushan.system.service.IActHiTaskinstService;
import com.yabushan.common.utils.poi.ExcelUtil;
import com.yabushan.common.core.page.TableDataInfo;

/**
 * 流程列表Controller
 *
 * @author yabushan
 * @date 2021-04-25
 */
@RestController
@RequestMapping("/system/taskinst")
public class ActHiTaskinstController extends BaseController
{
    @Autowired
    private IActHiTaskinstService actHiTaskinstService;

    /**
     * 查询流程列表列表
     */
    @PreAuthorize("@ss.hasPermi('system:taskinst:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActHiTaskinst actHiTaskinst)
    {
        startPage();
        List<ActHiTaskinst> list = actHiTaskinstService.selectActHiTaskinstList(actHiTaskinst);
        return getDataTable(list);
    }

    /**
     * 导出流程列表列表
     */
    @PreAuthorize("@ss.hasPermi('system:taskinst:export')")
    @Log(title = "流程列表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ActHiTaskinst actHiTaskinst)
    {
        List<ActHiTaskinst> list = actHiTaskinstService.selectActHiTaskinstList(actHiTaskinst);
        ExcelUtil<ActHiTaskinst> util = new ExcelUtil<ActHiTaskinst>(ActHiTaskinst.class);
        return util.exportExcel(list, "taskinst");
    }

    /**
     * 获取流程列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:taskinst:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(actHiTaskinstService.selectActHiTaskinstById(id));
    }

    /**
     * 新增流程列表
     */
    @PreAuthorize("@ss.hasPermi('system:taskinst:add')")
    @Log(title = "流程列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActHiTaskinst actHiTaskinst)
    {
        return toAjax(actHiTaskinstService.insertActHiTaskinst(actHiTaskinst));
    }

    /**
     * 修改流程列表
     */
    @PreAuthorize("@ss.hasPermi('system:taskinst:edit')")
    @Log(title = "流程列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActHiTaskinst actHiTaskinst)
    {
        return toAjax(actHiTaskinstService.updateActHiTaskinst(actHiTaskinst));
    }

    /**
     * 删除流程列表
     */
    @PreAuthorize("@ss.hasPermi('system:taskinst:remove')")
    @Log(title = "流程列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(actHiTaskinstService.deleteActHiTaskinstByIds(ids));
    }
}
