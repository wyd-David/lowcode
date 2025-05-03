package com.yabushan.web.controller.system;

import java.util.List;

import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.domain.SysSyncDep;
import com.yabushan.system.domain.vo.DepUserVo;
import com.yabushan.system.domain.vo.MyDeptVo;
import com.yabushan.system.domain.vo.OrgRoleUserVo;
import com.yabushan.system.domain.vo.RoleEmpInfoVo;
import com.yabushan.system.service.ISysSyncDepService;
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
import com.yabushan.system.domain.SyncOrg;
import com.yabushan.system.service.ISyncOrgService;
import com.yabushan.common.utils.poi.ExcelUtil;
import com.yabushan.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 同步组织信息Controller
 *
 * @author yabushan
 * @date 2021-08-24
 */
@RestController
@RequestMapping("/system/syncOrg")
public class SyncOrgController extends BaseController
{
    @Autowired
    private ISyncOrgService syncOrgService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ISysSyncDepService sysSyncDepService;

    /**
     * 查询同步组织信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SyncOrg syncOrg)
    {
        startPage();
        List<SyncOrg> list = syncOrgService.selectSyncOrgList(syncOrg);
        return getDataTable(list);
    }

    /**
     * 导出同步组织信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:syncOrg:export')")
    @Log(title = "同步组织信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SyncOrg syncOrg)
    {
        List<SyncOrg> list = syncOrgService.selectSyncOrgList(syncOrg);
        ExcelUtil<SyncOrg> util = new ExcelUtil<SyncOrg>(SyncOrg.class);
        return util.exportExcel(list, "org");
    }

    /**
     * 获取同步组织信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:syncOrg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(syncOrgService.selectSyncOrgById(id));
    }

    /**
     * 新增同步组织信息
     */
    @PreAuthorize("@ss.hasPermi('system:syncOrg:add')")
    @Log(title = "同步组织信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SyncOrg syncOrg)
    {
        return toAjax(syncOrgService.insertSyncOrg(syncOrg));
    }

    /**
     * 修改同步组织信息
     */
    @PreAuthorize("@ss.hasPermi('system:syncOrg:edit')")
    @Log(title = "同步组织信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit( SyncOrg syncOrg)
    {
        return toAjax(syncOrgService.updateSyncOrg(syncOrg));
    }

    /**
     * 删除同步组织信息@RequestBody
     */
    @PreAuthorize("@ss.hasPermi('system:syncOrg:remove')")
    @Log(title = "同步组织信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(syncOrgService.deleteSyncOrgByIds(ids));
    }


    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<SyncOrg> util = new ExcelUtil<SyncOrg>(SyncOrg.class);
        return util.importTemplateExcel("同步组织信息数据");
    }
    /**
    * 数据导入数据库
    * @param file 数据列表
    * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
    * @return
    * @throws Exception
    */
    @Log(title = "同步组织信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:syncOrg:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SyncOrg> util = new ExcelUtil<SyncOrg>(SyncOrg.class);
        List<SyncOrg> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser =tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = syncOrgService.importSyncOrg(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 根据部门获取子级部门信息及该部门下的用户信息
     * @return
     * @throws Exception
     */
    @PostMapping("/initOrgUserList")
    public AjaxResult initOrgUserList(@RequestBody DepUserVo vo){
        DepUserVo depUserVo = syncOrgService.initOrgUserList(vo.getDeptId(),vo.getNickName());
        return AjaxResult.success(depUserVo);
    }

    /**
     * 查询同步组织信息列表树形结构
     */
    @GetMapping("/listTreeselect")
    public TableDataInfo listTreeselect()
    {
        List<SyncOrg> list = syncOrgService.selectSysSyncDepAllTree();
        return getDataTable(list);
    }

    /**
     * 查询同步组织信息列表
     */
    @Log(title = "部门中英文查询", businessType = BusinessType.IMPORT)
    @GetMapping("/listChineseAndEnglish")
    public TableDataInfo listChineseAndEnglish(SyncOrg syncOrg)
    {
        startPage();
        List<SyncOrg> list = syncOrgService.selectSysSyncDepAll(syncOrg);
        return getDataTable(list);
    }


    /**
     * 获取自己的部门及部门英文信息
     * @return
     */
    @RequestMapping("/getMyDeptInfo")
    public AjaxResult getMyDeptInfo(){
        MyDeptVo myDeptVo = syncOrgService.getMyDeptInfo();
        return AjaxResult.success(myDeptVo);
    }


    /**
     * 获取所有部门信息
     * @return
     */
    @RequestMapping("/getAlldeptInfo")
    public AjaxResult getAllDeptInfo(){
        SyncOrg syncOrg = new SyncOrg();
        syncOrg.setIsdept("1");
        List<SyncOrg> syncOrgs = syncOrgService.selectSyncOrgList(syncOrg);
        return AjaxResult.success(syncOrgs);
    }

    /**
     * 根据组织、角色名称/流程角色名称  获取该角色人员信息
     * @param orgRoleUserVo
     * @return
     */
    @PostMapping("/getOrgRoleUserInfo")
    public AjaxResult getOrgRoleUserInfo(@RequestBody OrgRoleUserVo orgRoleUserVo){
        List<RoleEmpInfoVo> lcjsEmpInfo=null;
        if("1".equals(orgRoleUserVo.getRoleType())){
            //角色
        }else if("2".equals(orgRoleUserVo.getRoleType())){
            //流程角色
        }else{
            return AjaxResult.error("请输入正确的角色类型");
        }
        lcjsEmpInfo = syncOrgService.getLcjsEmpInfo(orgRoleUserVo);
        return AjaxResult.success(lcjsEmpInfo);

    }

    /**
     * 部门中英文修改
     */
    @Log(title = "部门中英文修改", businessType = BusinessType.UPDATE)
    @PostMapping("/updateChineseAndEnglish")
    public AjaxResult updateChineseAndEnglish(@RequestBody SysSyncDep syssyncdep)
    {
        syssyncdep.setId(Long.parseLong(syssyncdep.getSyscsdepid()));
        return toAjax(sysSyncDepService.updateSysSyncDep(syssyncdep));
    }


}
