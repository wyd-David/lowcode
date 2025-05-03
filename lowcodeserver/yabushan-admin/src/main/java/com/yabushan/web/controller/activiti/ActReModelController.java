package com.yabushan.web.controller.activiti;

import cn.hutool.core.util.ObjectUtil;
import com.yabushan.activiti.service.ActivitiService;
import com.yabushan.activiti.service.workflowUtilService;
import com.yabushan.activiti.util.StepCodeName;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.poi.ExcelUtil;
import com.yabushan.system.domain.ActReModel;
import com.yabushan.system.domain.ServiceStepBtnConf;
import com.yabushan.system.domain.ServiceStepConf;
import com.yabushan.system.service.IActReModelService;
import com.yabushan.system.service.IServiceStepConfService;
import com.yabushan.system.service.ITodoInfoPushService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作流模型Controller
 *
 * @author yabushan
 * @date 2021-04-21
 */
@RestController
@RequestMapping("/workflow/models")
public class ActReModelController extends BaseController {
    @Autowired
    private IActReModelService actReModelService;

    @Autowired
    private ActivitiService activitiService;

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private workflowUtilService workflowUtilService;

    @Autowired
    private IServiceStepConfService serviceStepConfService;

    @Autowired
    private ITodoInfoPushService iTodoInfoPushService;

    /**
     * 保存节点按钮信息
     */
//    @PreAuthorize("@ss.hasPermi('workflow:models:list')")
    @PostMapping("/saveProcessBnts")
    public AjaxResult saveProcessBnts(@RequestBody List<ServiceStepBtnConf> list) {
        if(list==null||list.size()==0){
            return AjaxResult.success("参数错误");
        }
        int res = actReModelService.saveProcessBnts(list);
        return AjaxResult.success(res);
    }

    /**
     * 查询节点按钮信息
     */
//    @PreAuthorize("@ss.hasPermi('workflow:models:list')")
    @PostMapping("/getProcessBnts")
    public AjaxResult getProcessBnts(@RequestBody ServiceStepBtnConf param) {
//        List<ServiceStepBtnConf> list = actReModelService.getProcessBnts(param);
        List<ServiceStepBtnConf> list = syncGetProcessBnts(param);
        return AjaxResult.success(list);
    }


    /**
     * 获取同时同步按钮信息
     * @param serviceStepBtnConf
     * @return
     */
    private List<ServiceStepBtnConf>  syncGetProcessBnts(ServiceStepBtnConf serviceStepBtnConf){
        List<ServiceStepBtnConf> list=actReModelService.getProcessBnts(serviceStepBtnConf);
        if(ObjectUtil.isEmpty(list)){
            List<ServiceStepBtnConf> procdef_list= actReModelService.getProcessBntsByProdefId(serviceStepBtnConf);
            if(!ObjectUtil.isEmpty(procdef_list)){
                return null;
            }
            String procdefId=serviceStepBtnConf.getProcdefId();
            //当前流程节点信息
            List<StepCodeName> nodelist = activitiService.getAllFlowNode(procdefId);
            if(ObjectUtil.isNotEmpty(nodelist)){
                Map nodeMap=new HashMap();
                for(StepCodeName item:nodelist){
                    nodeMap.put(item.getStepCode(),item.getStepCode());
                }
                String procdefKey=procdefId.split(":")[0];
                actReModelService.syncProcessBnts(procdefId,procdefKey,nodeMap);
                list=actReModelService.getProcessBnts(serviceStepBtnConf);
            }
        }
        return list;
    }


    /**
     * 查询工作流模型列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:models:list')")
    @GetMapping("/list")
    public TableDataInfo list(ActReModel actReModel) {
        startPage();
        List<ActReModel> list = actReModelService.selectActReModelList(actReModel);
        return getDataTable(list);
    }

    /**
     * 导出工作流模型列表
     */
    @PreAuthorize("@ss.hasPermi('workflow:models:export')")
    @Log(title = "工作流模型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ActReModel actReModel) {
        List<ActReModel> list = actReModelService.selectActReModelList(actReModel);
        ExcelUtil<ActReModel> util = new ExcelUtil<ActReModel>(ActReModel.class);
        return util.exportExcel(list, "models");
    }

    /**
     * 生成工作流审批节点信息
     */
    @PreAuthorize("@ss.hasPermi('workflow:models:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {

        if (id.indexOf(",") > 0) {
            return AjaxResult.error("只能单个生成");
        }
        //获取模型信息
        //获取所有步骤信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(id).singleResult();
        List<Map<String, String>> result = workflowUtilService.getJbpmNodeMap(processDefinition.getId());
        if (result.size() > 0) {
            //生成节点信息
            ServiceStepConf serviceStepConf = null;
            for (Map<String, String> map : result) {
                serviceStepConf = new ServiceStepConf();
                serviceStepConf.setProcdefId(processDefinition.getId());
                serviceStepConf.setStepCode(map.get("code"));
                serviceStepConf.setStepName(map.get("name"));
                List<ServiceStepConf> serviceStepConfs = serviceStepConfService.selectServiceStepConfList(serviceStepConf);
                if (serviceStepConfs.size() > 0) {
                    continue;
                }
                serviceStepConf.setStepId(StringUtils.getUUID().toString());
                serviceStepConfService.insertServiceStepConf(serviceStepConf);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 新增工作流模型
     */
    @PreAuthorize("@ss.hasPermi('workflow:models:add')")
    @Log(title = "工作流模型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ActReModel actReModel) {
        return toAjax(actReModelService.insertActReModel(actReModel));
    }

    /**
     * 修改工作流模型
     */
    @PreAuthorize("@ss.hasPermi('workflow:models:edit')")
    @Log(title = "工作流模型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ActReModel actReModel) {
        return toAjax(actReModelService.updateActReModel(actReModel));
    }

    /**
     * 删除工作流模型
     */
    @PreAuthorize("@ss.hasPermi('workflow:models:remove')")
    @Log(title = "工作流模型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(actReModelService.deleteActReModelByIds(ids));
    }
}
