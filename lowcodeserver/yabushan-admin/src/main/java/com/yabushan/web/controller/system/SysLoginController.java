package com.yabushan.web.controller.system;

import com.yabushan.common.constant.Constants;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysMenu;
import com.yabushan.common.core.domain.entity.SysRole;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.core.domain.model.LoginBody;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.framework.web.service.SysLoginService;
import com.yabushan.framework.web.service.SysPermissionService;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.domain.SyncUser;
import com.yabushan.system.domain.vo.MyDeptVo;
import com.yabushan.system.mapper.SysRoleMapper;
import com.yabushan.system.service.ISyncOrgService;
import com.yabushan.system.service.ISyncUserService;
import com.yabushan.system.service.ISysMenuService;
import com.yabushan.system.service.ISysUserService;
import com.yabushan.web.utils.AESUtil;
import com.yabushan.web.utils.ReadTxtFileNio;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@Api(tags = "登录验证")
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISyncOrgService syncOrgService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISyncUserService iSyncUserService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录方法")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
       try{
           boolean key = ReadTxtFileNio.isKey();
           if(!key){
               return AjaxResult.error("授权已过期，请联系授权（微信:yabushan）");
           }
       }catch (Exception e){
           return AjaxResult.error("授权已过期");
       }
        String password = "";
        try {

            password = AESUtil.aesDecrypt(loginBody.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), password, loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * h5登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/APPDL")
    @ApiOperation(value = "登录方法")
    public AjaxResult loginAPP(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }



    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    @ApiOperation(value = "获取用户信息")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        List<SysRole> sysRoles = sysRoleMapper.selectRolePermissionByUserId(user.getUserId());
        user.setRoles(sysRoles);
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        MyDeptVo sysDeptInfo = syncOrgService.getSysDeptInfo(user.getUserName());
        SysUser sysUser = sysUserService.selectUserByUserName(user.getUserName());

        //获取客户表数据得到erpid
        SyncUser syncUser=new SyncUser();
        syncUser.setLoginid(user.getUserName());
        List<SyncUser> syncUserList= iSyncUserService.selectSyncUserList(syncUser);
        if(sysUser != null){
            user.setUserType(sysUser.getUserType());
            for( SyncUser syncUser1:syncUserList){
                user.setErpId(syncUser1.getErpid());
            }
        }
        if(sysDeptInfo != null){
            user.setZuzhiId(user.getDeptId());
            user.setDeptId(Long.valueOf(sysDeptInfo.getDeptId()));
            if(user.getDept() != null){
                user.getDept().setZuzhiId(user.getDept().getDeptId());
                user.getDept().setZuzhiName(user.getDept().getDeptName());
                user.getDept().setDeptId(Long.valueOf(sysDeptInfo.getDeptId()));
                user.getDept().setDeptName(sysDeptInfo.getDeptName());
            }
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @ApiOperation(value = "获取路由信息")
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
//        使用三级菜单 不传默认模板 updated by ly 220706
//        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId(),"Layout");
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId(),"");
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @ApiOperation(value = "获取路由信息")
    @GetMapping("getRoutersLayout")
    public AjaxResult getRoutersLayout()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId(),"Layout2");
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
