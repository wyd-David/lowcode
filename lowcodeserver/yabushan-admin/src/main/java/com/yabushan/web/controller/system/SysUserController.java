package com.yabushan.web.controller.system;

import java.util.List;
import java.util.stream.Collectors;

import com.yabushan.common.core.domain.model.SysUserRoleVo;
import com.yabushan.system.domain.SysPost;
import com.yabushan.system.domain.ViewEmpInfo;
import com.yabushan.system.domain.vo.DepUserVo;
import com.yabushan.system.domain.vo.SysUserVo;
import com.yabushan.system.service.IViewEmpInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.constant.UserConstants;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysRole;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.poi.ExcelUtil;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.service.ISysPostService;
import com.yabushan.system.service.ISysRoleService;
import com.yabushan.system.service.ISysUserService;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/user")
@Api(tags = "用户信息")
public class SysUserController extends BaseController {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IViewEmpInfoService viewEmpInfoService;

    /**
     * 获取用户列表
     */
    @ApiOperation(value = "获取用户列表")
    //@Log(title = "获取用户列表", businessType = BusinessType.OTHER)
   // @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }


    /**
     * 获取用户列表
     */
    @ApiOperation(value = "获取用户列表")
    //@Log(title = "获取用户列表", businessType = BusinessType.OTHER)
    @GetMapping("/userList")
    public TableDataInfo userList(SysUserVo user) {
        startPage();
        String username = SecurityUtils.getUsername();
        if(!"admin".equals(username)){
            boolean flag=true;
            List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
            if(null!=roles && roles.size()>0){
                for(SysRole role:roles){
                    //巡察办主管也能看所有
                    if("XUNCHABAN_MAINTAINER".equals(role.getRoleKey())){
                        flag=false;
                        break;
                    }
                }
            }
            if(flag) {
                user.setCompanyId(SecurityUtils.getLoginUser().getUser().getCompanyId());
            }
        }
        List<SysUser> list = userService.selectUserListByRoleId(user);
        return getDataTable(list);
    }

    //@Log(title = "用户管理", businessType = BusinessType.EXPORT)
  //  @PreAuthorize("@ss.hasPermi('system:user:export')")
    @GetMapping("/export")
    @ApiOperation(value = "导出用户列表")
    public AjaxResult export(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
   // @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    @ApiOperation(value = "导入用户列表")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @GetMapping("/importTemplate")
    @ApiOperation(value = "导出用户列表")
    public AjaxResult importTemplate() {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @ApiOperation(value = "根据用户编号获取详细信息")
   // @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        List<SysPost> sysPosts = postService.selectPostAll();
        for(int i=0;i<sysPosts.size();i++){
            if(!sysPosts.get(i).getCreateBy().equals(SecurityUtils.getUsername())){
                sysPosts.remove(sysPosts.get(i));
            }
        }
        ajax.put("posts",sysPosts);
        if (StringUtils.isNotNull(userId)) {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @ApiOperation(value = "新增用户")
   // @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @ApiOperation(value = "修改用户")
   // @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        /*if (UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }*/
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @ApiOperation(value = "删除用户")
   // @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @ApiOperation(value = "重置密码")
   // @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @ApiOperation(value = "状态修改")
   // @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 获取用户
     */
    @ApiOperation(value = "根据条件获取用户列表")
    /*@PreAuthorize("@ss.hasPermi('system:user:getUserList')")
    @Log(title = "根据条件获取用户列表", businessType = BusinessType.UPDATE)*/
    @PostMapping("/getUserList")
    public TableDataInfo getUserList(DepUserVo depUserVo) {
        List<SysUserVo> list = userService.getUserList(depUserVo);
        return getDataTable(list);
    }

    /**
     * 获取用户
     */
    @ApiOperation(value = "根据角色key获取用户列表")
    /*@PreAuthorize("@ss.hasPermi('system:user:getUserList')")*/
    @Log(title = "根据角色key获取用户列表", businessType = BusinessType.OTHER)
    @PostMapping("/getUserListByRoleKey")
    public AjaxResult getUserByRoleKey(@RequestBody SysRole sysRole) {
        if (StringUtils.isEmpty(sysRole.getRoleKey())) {
            return AjaxResult.error("角色权限key不可为空！");
        }
        List<SysUser> list = userService.getUserByRoleKey(sysRole.getRoleKey());
        return AjaxResult.success("查询成功", list);
    }


    /**
     * 获取用户详细列表
     */
    @ApiOperation(value = "获取用户详细列表")
    @GetMapping("/viewEmpInfoList")
    public TableDataInfo viewEmpInfoList(ViewEmpInfo viewEmpInfo) {
        startPage();
        List<ViewEmpInfo> list = viewEmpInfoService.selectViewEmpInfoList(viewEmpInfo);
        return getDataTable(list);
    }


    /**
     * 注册用户
     */
    @ApiOperation(value = "注册用户")
    @PostMapping("/registerUser")
    public AjaxResult registerUser(@RequestParam(value = "loginId", required = false) String loginId,
                                   @RequestParam(value = "nickName", required = false) String nickName,
                                   @RequestParam(value = "password", required = false) String password,
                                   @RequestParam(value = "phonenumber", required = false) String phonenumber,
                                   @RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "uuid", required = false) String uuid) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(loginId))) {
            return AjaxResult.error("账号已存在！");
        }
        int i = userService.registerUser(new SysUser(loginId, nickName, password, phonenumber), code, uuid);
        return AjaxResult.success("注册成功", i);
    }

    /**
     * 新增用户
     */
    @ApiOperation(value = "新增用户角色")
    @Log(title = "新增用户角色", businessType = BusinessType.INSERT)
    @PostMapping("/addUserRole")
    public AjaxResult addUserRole(@RequestBody SysUserRoleVo sysUserRoleVo) {
        if(StringUtils.isEmpty(sysUserRoleVo.getRoleId())){
            return AjaxResult.error("角色不能为空");
        }
        if(StringUtils.isEmpty(sysUserRoleVo.getUserName())){
            return AjaxResult.error("用户不能为空");
        }
        if("1".equals(sysUserRoleVo.getRoleId())){
            return AjaxResult.error("不能为超级管理员添加人员");
        }
        int count = roleService.selectRoleByUserId(sysUserRoleVo.getUserName(), sysUserRoleVo.getRoleId());
        if(count>0){
            return AjaxResult.error("用户已存在");
        }else{
            SysUser sysUser = userService.selectUserByUserName(sysUserRoleVo.getUserName());
            if(sysUser!=null){
                roleService.insertUserRole(sysUser.getUserId(),sysUserRoleVo.getRoleId());
            }

        }
        return toAjax(1);
    }

    /**
     * 新增用户
     */
    @ApiOperation(value = "新增用户角色")
    @Log(title = "新增用户角色", businessType = BusinessType.INSERT)
    @PostMapping("/delUserRole")
    public AjaxResult delUserRole(@RequestBody SysUserRoleVo sysUserRoleVo) {
        if(StringUtils.isEmpty(sysUserRoleVo.getRoleId())){
            return AjaxResult.error("角色不能为空");
        }
        if(StringUtils.isEmpty(sysUserRoleVo.getUserName())){
            return AjaxResult.error("用户不能为空");
        }
        roleService.deleteUserRole(Long.valueOf(sysUserRoleVo.getRoleId()),sysUserRoleVo.getUserName().split(","));
        return toAjax(1);
    }
}
