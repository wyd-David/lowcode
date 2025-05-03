package com.yabushan.web.controller.system;

import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.common.utils.http.HttpClientUtil;
import com.yabushan.common.utils.poi.ExcelUtil;
import com.yabushan.common.utils.sign.Base64;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.system.domain.SyncUser;
import com.yabushan.system.domain.vo.MyDeptVo;
import com.yabushan.system.service.ISyncOrgService;
import com.yabushan.system.service.ISyncUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步用户信息Controller
 *
 * @author yabushan
 * @date 2021-08-24
 */
@RestController
@RequestMapping("/system/syncUser")
public class SyncUserController extends BaseController
{
    @Autowired
    private ISyncUserService syncUserService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ISyncOrgService orgService;

    // 验证码类型
    @Value("${image.url}")
    private String imageUrl;

    /**
     * 查询同步用户信息列表
     */
//    @PreAuthorize("@ss.hasPermi('system:syncUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(SyncUser syncUser)
    {
        startPage();
        List<SyncUser> list = syncUserService.selectSyncUserList(syncUser);
        for(SyncUser user : list){
            MyDeptVo deptVo = orgService.getMyDeptInfoByLoginId(user.getLoginid());
            user.setDepEnName(deptVo.getDeptEnName());
        }
        return getDataTable(list);
    }

    /**
     * 导出同步用户信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:syncUser:export')")
    @Log(title = "同步用户信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SyncUser syncUser)
    {
        List<SyncUser> list = syncUserService.selectSyncUserList(syncUser);
        ExcelUtil<SyncUser> util = new ExcelUtil<SyncUser>(SyncUser.class);
        return util.exportExcel(list, "user");
    }

    /**
     * 获取同步用户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:syncUser:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(syncUserService.selectSyncUserById(id));
    }

    /**
     * 新增同步用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:syncUser:add')")
    @Log(title = "同步用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SyncUser syncUser)
    {
        return toAjax(syncUserService.insertSyncUser(syncUser));
    }

    /**
     * 修改同步用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:syncUser:edit')")
    @Log(title = "同步用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SyncUser syncUser)
    {
        return toAjax(syncUserService.updateSyncUser(syncUser));
    }

    /**
     * 删除同步用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:syncUser:remove')")
    @Log(title = "同步用户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(syncUserService.deleteSyncUserByIds(ids));
    }

    /**
    * 数据导入数据库的下载模板
    * @return
    * @throws Exception
    */
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate()
    {
        ExcelUtil<SyncUser> util = new ExcelUtil<SyncUser>(SyncUser.class);
        return util.importTemplateExcel("同步用户信息数据");
    }
    /**
    * 数据导入数据库
    * @param file 数据列表
    * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
    * @return
    * @throws Exception
    */
    @Log(title = "同步用户信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:syncUser:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SyncUser> util = new ExcelUtil<SyncUser>(SyncUser.class);
        List<SyncUser> userList = util.importExcel(file.getInputStream());
        LoginUser loginUser =tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = syncUserService.importSyncUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }


    /**
     * 根据loginId获取图片
     * @param loginid 登录id
     * @return
     * @throws Exception
     */
    @Log(title = "根据loginId获取图片", businessType = BusinessType.IMPORT)
    @GetMapping("/getUserInfoByObjectImages")
    public   Map<String,Object>  getUserInfoByObjectImages(HttpServletRequest request){
        String loginid = request.getParameter("loginid");

        Map<String,Object> resultMap = new HashMap<>();
         String imageInfo=Base64.encode(HttpClientUtil.getNetImgByUrl( imageUrl+loginid));

         if(!"".equals(imageInfo)){
             resultMap.put("image",imageInfo);
             resultMap.put("code",200);
         }else {
             resultMap.put("image","");
             resultMap.put("code",0);
         }


        return  resultMap  ;



    }


}
