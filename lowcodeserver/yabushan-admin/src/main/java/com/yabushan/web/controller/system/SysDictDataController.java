package com.yabushan.web.controller.system;

import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysDictData;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.poi.ExcelUtil;
import com.yabushan.system.mapper.SysDictDataMapper;
import com.yabushan.system.service.ISysDictDataService;
import com.yabushan.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 *
 * @author ruoyi
 */
@Api(tags = "数据字典信息")
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController
{
    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @ApiOperation(value = "查询所有数据字典")
    @Log(title = "查询所有数据字典", businessType = BusinessType.OTHER)
//    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDictData dictData)
    {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @ApiOperation(value = "导出所有数据字典")
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
   // @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @GetMapping("/export")
    public AjaxResult export(SysDictData dictData)
    {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        return util.exportExcel(list, "字典数据");
    }

    /**
     * 查询字典数据详细
     */
    @ApiOperation(value = "查询字典数据详细")
   // @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictCode}")
    public AjaxResult getInfo(@PathVariable Long dictCode)
    {
        return AjaxResult.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @ApiOperation(value = "根据字典类型查询字典数据信息")
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType)
    {
        return AjaxResult.success(dictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典类型
     */
    @ApiOperation(value = "新增字典类型")
   // @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictData dict)
    {
        dict.setCreateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @ApiOperation(value = "修改保存字典类型")
  //  @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictData dict)
    {
        dict.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @ApiOperation(value = "删除字典类型")
   // @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public AjaxResult remove(@PathVariable Long[] dictCodes)
    {
        return toAjax(dictDataService.deleteDictDataByIds(dictCodes));
    }



    /**
     * 获取自定义查询条件数据
     */
    @ApiOperation(value = "查询字典数据详细")
    @GetMapping(value = "/customize/{dictCode}")
    public AjaxResult getCustomizeDataInfo(@PathVariable String dictCode)
    {
        Integer   createId=null;
        if("datasourceId".equals(dictCode)){
            //获取所有数据源
            if(SecurityUtils.getUsername().equals("admin")){

            }else{
                  createId= Math.toIntExact(SecurityUtils.getLoginUser().getUser().getUserId());
            }

            return AjaxResult.success(dictDataService.getDataSouceInfo(createId));
        }
        return AjaxResult.error("没有指定数据字典信息");
    }

    /**
     * 根据字典编号和名称判断是否已存在，去重校验
     */
    @ApiOperation(value = "根据字典编号和名称判断是否已存在")
    @GetMapping(value = "/checkDicName")
    public AjaxResult checkDicName(@RequestParam("dictType") String dictType,@RequestParam("dictLabel") String dictLabel)
    {
        int countNum = dictDataMapper.countDictDataByType(dictType,dictLabel);
        return AjaxResult.success(countNum);
    }
}
