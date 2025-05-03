package com.yabushan.web.controller.common;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.util.ObjectUtil;
import com.yabushan.activiti.service.ActivitiService;
import com.yabushan.activiti.util.StepCodeName;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.model.LoginUser;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.ServletUtils;
import com.yabushan.framework.web.service.TokenService;
import com.yabushan.poi.utils.EasypoiUtil;
import com.yabushan.poi.utils.ExcelUtils;
import com.yabushan.system.domain.ServiceStepBtnConf;
import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.domain.TodoInfoPush;
import com.yabushan.system.domain.vo.TodoInfoVo;
import com.yabushan.system.domain.vo.TodoVo;
import com.yabushan.system.service.IActReModelService;
import com.yabushan.system.service.ITodoInfoPushService;
import com.yabushan.system.service.ITodoInfoService;
import com.yabushan.system.service.impl.TodoInfoServiceImpl;
import com.yabushan.web.model.vo.TodoParamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * 待办处理
 *
 * @author yabushan
 */
@RestController
@RequestMapping("/todoinfo")
@Api(tags = "待办待阅接口")
public class TodoController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private ITodoInfoService todoInfoService;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private TodoInfoServiceImpl todoInfoServiceimpl;

  //  @Autowired
  //  private IWxPortalApplyInfoService wxPortalApplyInfoService;

    @Autowired
    private ITodoInfoPushService iTodoInfoPushService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private IActReModelService actReModelService;

    @Value("${iplist.s3Image-Echo}")
    private String s3ImageEcho;


    /**
     * 查询待办待阅列表
     */
    @PostMapping("/todoinfo/list")
  // @Log(title = "待办列表接口", businessType = BusinessType.OTHER)
    @ApiOperation(value = "待办列表接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "todoInfo", value = "待办实体类", dataType = "TodoInfo")
    })
    public TableDataInfo list(TodoInfo todoInfo) {
        startPage();
        if(!"admin".equals(SecurityUtils.getUsername())){
            todoInfo.setTodoHandler(SecurityUtils.getUsername());
        }
        List<TodoInfo> list = todoInfoService.selectTodoInfoListPC(todoInfo);
        return getDataTable(list);
    }

    /**
     * 查询待办待阅列表
     */
    @PostMapping("/todoinfo/listCount")
    @Log(title = "待办数量接口", businessType = BusinessType.OTHER)
    @ApiOperation(value = "待办数量接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "todoInfo", value = "待办实体类", dataType = "TodoInfo")
    })
    public AjaxResult listCount(TodoInfo todoInfo) {

        if(!"admin".equals(SecurityUtils.getUsername())){
            todoInfo.setTodoHandler(SecurityUtils.getUsername());
        }
        return AjaxResult.success(todoInfoService.selectTodoInfoListPCCount(todoInfo));
    }

    /**
     * 查询所有推送数据列表
     */
    @PostMapping("/todoPushInfo/list")
    @Log(title = "查询所有推送数据列表", businessType = BusinessType.OTHER)
    @ApiOperation(value = "查询所有推送数据列表")
    public TableDataInfo todoPushList(TodoInfoPush todoInfo) {
        startPage();

        List<TodoInfoPush> list = iTodoInfoPushService.selectTodoInfoPushList(todoInfo);
        return getDataTable(list);
    }

    /**
     * 删除推送数据列表
     */
    @ApiOperation("删除推送数据列表")
    @Log(title = "删除推送数据列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/todoPushInfo/{ids}")
    public AjaxResult removeTodoPushInfo(@PathVariable String[] ids) {
        return toAjax(iTodoInfoPushService.deleteTodoInfoPushByIds(ids));
    }

    /**
     * 获取当前登陆人去重的应用名称跟应用ID
     */
    @PostMapping("/todoinfo/selectTodoInfoGetAppName")
    @ApiOperation(value = "获取当前登陆人去重的应用名称跟应用ID接口")
    public TableDataInfo selectTodoInfoGetAppName(TodoInfoVo todoInfo) {
        List<TodoInfoVo> list = todoInfoService.selectTodoInfoGetAppName(todoInfo);
        return getDataTable(list);
    }


    /**
     * 查询待办待阅列表关联了应用表查询
     */
    @GetMapping("/todoinfoOrApplyinfo/list")
    @ApiOperation(value = "查询待办待阅列表关联了应用表查询")
    public TableDataInfo todoinfoOrApplyinfolist(TodoInfoVo todoInfo) {
        startPage();
        if(null!=todoInfo){
            todoInfo.setTodoHandler(SecurityUtils.getUsername());
        }
        if ("1".equals(todoInfo.getTodoDataType())) {
            //获取待办
            todoInfo.setTodoType("");
        } else if ("2".equals(todoInfo.getTodoDataType())) {
            //获取已办
            todoInfo.setTodoType("completed");
        } else if ("3".equals(todoInfo.getTodoDataType())) {
            //待阅
            todoInfo.setTodoType("todo");
        } else if ("4".equals(todoInfo.getTodoDataType())) {
            //已阅
            todoInfo.setTodoType("todo");
        }
        //获取应用列表
        //TODO  需要优化工作流与业务数据库的编码需转换
        List<TodoInfoVo> list = todoInfoService.selectTodoInfoOrApplyinfoList(todoInfo);
        /*WxPortalApplyInfo wxTemp = new WxPortalApplyInfo();

        final List<WxPortalApplyInfo> wxPortalApplyInfos = wxPortalApplyInfoService.selectWxPortalApplyInfoList(wxTemp);
        //封装应用Map
        Map appMap = new HashMap();
        for (int i=0;i<wxPortalApplyInfos.size();i++) {
            appMap.put(wxPortalApplyInfos.get(i).getId(),wxPortalApplyInfos.get(i).getImgPath());
        }

        for (int i = 0; i < list.size(); i++) {
            //获取应用信息
            list.get(i).setImgPath(s3ImageEcho+appMap.get(list.get(i).getApplyId()));
        //    WxPortalApplyInfo wxPortalApplyInfo = wxPortalApplyInfoService.selectWxPortalApplyInfoById(list.get(i).getApplyId());

          *//*  if(wxPortalApplyInfo!=null){
                if(StringUtils.isNotNull(wxPortalApplyInfo.getImgPath())&&!wxPortalApplyInfo.getImgPath().equals("")){
                    list.get(i).setImgPath(s3ImageEcho+wxPortalApplyInfo.getImgPath());
                }
            }*//*
        }*/
        return getDataTable(list);
    }

    /**
     * 我的申请，小程序
     */
    @GetMapping("/todoinfoOrApplyinfo/selectMyTodoInfoList")
    @ApiOperation(value = "我的申请，小程序接口")
    public TableDataInfo selectMyTodoInfoList(TodoInfoVo todoInfo) {
        String username = SecurityUtils.getUsername();
        todoInfo.setRealDealUser(username);
        todoInfo.setTodoApplicant(username);
        startPage();
        //TODO  需要优化工作流与业务数据库的编码需转换
        List<TodoInfoVo> list = todoInfoService.selectMyTodoInfoList(todoInfo);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setImgPath(s3ImageEcho + list.get(i).getImgPath());
        }
        return getDataTable(list);
    }

    /**
     * 根据处理人查询待办待阅的总数
     */
    @PostMapping("/todoinfoBytodoHandler/list")
    @ApiOperation(value = "根据处理人查询待办待阅的总数")
    public AjaxResult todoinfoBytodoHandler(@RequestBody TodoInfoVo todoInfo) {

        return AjaxResult.success(todoInfoService.selectTodoInfoCount(todoInfo.getTodoHandler()));
    }
//    /**
//     * 导出待办待阅列表
//     */
//    @ApiOperation("导出待办待阅列表")
//    @PreAuthorize("@ss.hasPermi('todoinfo:todoinfo:export')")
//    @Log(title = "待办待阅", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(TodoInfo todoInfo, HttpServletResponse response) throws IOException {
//        List<TodoInfo> list = todoInfoService.selectTodoInfoList(todoInfo);
//        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "待办待阅", "待办待阅", TodoInfo.class, "待办待阅.xlsx", response);
//        return ajaxResult;
//    }

    /**
     * 获取待办待阅详细信息
     */
    @ApiOperation("获取待办待阅详细信息")
    @PreAuthorize("@ss.hasPermi('todoinfo:todoinfo:query')")
    @GetMapping(value = "/{todoId}")
    public AjaxResult getInfo(@PathVariable("todoId") String todoId) {
        return AjaxResult.success(todoInfoService.selectTodoInfoById(todoId));
    }

    /**
     * 新增待办待阅
     */
    @ApiOperation("新增待办待阅")
    @PreAuthorize("@ss.hasPermi('todoinfo:todoinfo:add')")
    @Log(title = "待办待阅", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TodoInfo todoInfo) {
        return toAjax(todoInfoService.insertTodoInfo(todoInfo));
    }

    /**
     * 修改待办待阅
     */
    @ApiOperation("修改待办待阅")
    @PreAuthorize("@ss.hasPermi('todoinfo:todoinfo:edit')")
    @Log(title = "待办待阅", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TodoInfo todoInfo) {
        return toAjax(todoInfoService.updateTodoInfo(todoInfo));
    }

    /**
     * 删除待办待阅
     */
    @ApiOperation("删除待办待阅")
    @PreAuthorize("@ss.hasPermi('todoinfo:todoinfo:remove')")
    @Log(title = "待办待阅", businessType = BusinessType.DELETE)
    @DeleteMapping("/{todoIds}")
    public AjaxResult remove(@PathVariable String[] todoIds) {
        return toAjax(todoInfoService.deleteTodoInfoByIds(todoIds));
    }

    /**
     * 待办保存
     */
    @PostMapping("/todoinfo/todo")
    @ApiOperation(value = "待办保存接口")
    public AjaxResult sendTodo(@RequestBody TodoVo todoVo) {
        try {
            List<TodoVo> todoList = new ArrayList<>();
            todoList.add(todoVo);
            todoInfoService.batchSaveTodoInfo(todoList);
            return AjaxResult.success("成功！");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 数据导入数据库的下载模板
     *
     * @return
     * @throws Exception
     */
    @ApiOperation("数据导入数据库的下载模板")
    @GetMapping("/importTemplate")
    public AjaxResult importTemplate(HttpServletResponse response) throws IOException {
        List<TodoInfo> list = new ArrayList<>();
        AjaxResult ajaxResult = ExcelUtils.exportExcel(list, "待办待阅模板", "待办待阅", TodoInfo.class, "待办待阅导入模板.xlsx", response);
        return ajaxResult;
    }

    /**
     * 数据导入数据库
     *
     * @param file          数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return
     * @throws Exception
     */
    @ApiOperation("数据导入数据库")
    @Log(title = "待办待阅", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('todoinfo:todoinfo:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport, HttpServletResponse response) throws Exception {
        InputStream inputStream = file.getInputStream();
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        //标题
        importParams.setTitleRows(1);
        // 是否需要验证
        importParams.setNeedVerfiy(true);
        //
        ExcelImportResult<TodoInfo> requestList = ExcelImportUtil.importExcelMore(inputStream, TodoInfo.class, importParams);
        //导入的相应数据
        List<TodoInfo> list = requestList.getList();
        //调用getImgName方法获取图片名，这里是根据你配置的地址来存放的，也可以在实体类里面配置存放地址，需要可以开放
        //List<WxPortalApplyInfo> infoList = getImgName(list);
        //判断是否有错误
        if (requestList.isVerfiyFail()) {
            //getFailList()里面的就是所有校验失败的excel数据
            List<TodoInfo> failList = requestList.getFailList();
            ExportParams params = new ExportParams();
            params.setSheetName("待办待阅错误数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, TodoInfo.class, failList);
            return EasypoiUtil.downLoadExcel("待办待阅错误数据.xlsx", response, workbook);
        } else {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();
            List<TodoInfo> todoInfos = todoInfoService.importTodoInfo(list, false, operName);
            //清理掉里面缓存的数据
            todoInfoServiceimpl.cleartodoInfoInfo();
            ExportParams params = new ExportParams();
            params.setSheetName("待办待阅导入数据");
            Workbook workbook = ExcelExportUtil.exportExcel(params, TodoInfo.class, todoInfos);
            return EasypoiUtil.downLoadExcel("待办待阅导入数据.xlsx", response, workbook);
        }
    }

    /**
     * 待阅保存
     */
    @PostMapping("/todoinfo/toRead")
    @ApiOperation(value = "待阅保存接口")
    public AjaxResult sendToRead(@RequestBody TodoVo todoVo) {
        try {
            List<TodoVo> todoList = new ArrayList<>();
            todoList.add(todoVo);
            todoInfoService.batchSaveToReadInfo(todoList);
            return AjaxResult.success("成功！");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 已办
     */
    @PostMapping("/todoinfo/hadDo")
    @ApiOperation(value = "已办保存接口")
    public AjaxResult SendhadDo(@RequestBody TodoVo todoVo) {
        try {
            List<TodoVo> todoList = new ArrayList<>();
            todoList.add(todoVo);
            todoInfoService.batchSaveHadDone(todoList);
            return AjaxResult.success("成功！");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 已阅保存接口
     */
    @PostMapping("/todoinfo/hadRead")
    @ApiOperation(value = "已阅保存接口")
    public AjaxResult SendhadRead(@RequestBody TodoVo todoVo) {
        try {
            List<TodoVo> todoList = new ArrayList<>();
            todoList.add(todoVo);
            todoInfoService.batchSaveHadRead(todoList);
            return AjaxResult.success("成功！");
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 查看待办详情
     */
    @PostMapping("/todoinfo/viewTodoInfo")
    @ApiOperation(value = "查看待办详情")
    public AjaxResult viewTodoInfo(String todoId) {

        try {
            //根据待办id获取待办任务信息
            TodoInfo todoInfo = todoInfoService.selectTodoInfoById(todoId);
            if ("1".equals(todoInfo.getApplyId())) {
                //查询物资详情信息

            } else if ("2".equals(todoInfo.getApplyId())) {
                //查询餐厅详情信息
            }

            return AjaxResult.success("成功", todoInfo);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 根据处理人查询待办待阅的总数
     */
    @GetMapping("/todoinfo/getToDoInfoCount")
    @ApiOperation(value = "根据处理人查询待办待阅的总数")
    public AjaxResult getToDoInfoCount(@RequestParam("appId") String appId, @RequestParam("todoHandler") String todoHandler, @RequestParam("applyType") String applyType) {
        return AjaxResult.success("操作成功", todoInfoService.getToDoInfoCount(appId, todoHandler, applyType));
    }


    /**
     * //通过parentId获取某一类应用的待办
     * by CJH
     */
    @PostMapping("/todoinfo/getToDoList")
    @ApiOperation(value = "待办列表接口")
    public TableDataInfo getToDoList(TodoInfo todoInfo) {
        startPage();

        List<TodoInfo> list = todoInfoService.getToDoList(todoInfo);
        return getDataTable(list);
    }


    /**
     * 获取节点按钮信息
     * @param serviceStepBtnConf
     * @return
     */
    @PostMapping("/todoinfo/getProcessBnts")
    @ApiOperation(value = "获取节点按钮信息")
    public AjaxResult getProcessBnts(@RequestBody ServiceStepBtnConf serviceStepBtnConf) {
        if(StringUtils.isEmpty(serviceStepBtnConf.getProcdefId())){
            return AjaxResult.error("参数错误");
        }
//        serviceStepBtnConf.setProcdefId("xuncha_case_flow:15:2310213");

        List<ServiceStepBtnConf>  list=syncGetProcessBnts(serviceStepBtnConf);
        return AjaxResult.success(list);
    }


    /**
     * 同步节点按钮信息
     * @param serviceStepBtnConf
     * @return
     */
    @PostMapping("/todoinfo/syncProcessBnts")
    @ApiOperation(value = "同步节点按钮信息")
    public AjaxResult syncProcessBnts(@RequestBody ServiceStepBtnConf serviceStepBtnConf) {
        if(StringUtils.isEmpty(serviceStepBtnConf.getProcdefId())){
            return AjaxResult.error("参数错误");
        }

        List<ServiceStepBtnConf>  list=syncGetProcessBnts(serviceStepBtnConf);
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
     * 获取节点按钮信息
     * @param serviceStepBtnConf
     * @return
     */
    @PostMapping("/todoinfo/getWorkflowStepBtns")
    @ApiOperation(value = "获取节点按钮信息")
    public AjaxResult getWorkflowStepBtns(@RequestBody ServiceStepBtnConf serviceStepBtnConf) {
        if(StringUtils.isEmpty(serviceStepBtnConf.getProcdefId())){
            return AjaxResult.error("参数错误");
        }
        List<ServiceStepBtnConf> list=actReModelService.getProcessBnts(serviceStepBtnConf);
        Map result=new HashMap();
        for (ServiceStepBtnConf item:list){
            Map btn=new HashMap();
            btn.put("name",item.getBtnName());
            if(StringUtils.isNotEmpty(item.getBtnCode())) {
                result.put(item.getBtnCode(), btn);
            }
        }
        return AjaxResult.success(result);
    }


    /**
     * 校验授权
     *
     * @param todoInfoParam 0：没有权限，1：可以查看，2可以处理
     * @return
     */
    @PostMapping("/todoinfo/validateAuthorize")
    public AjaxResult validateAuthorize(@RequestBody TodoInfo todoInfoParam) {
        if(StringUtils.isEmpty(todoInfoParam.getBusinessKey())){
            return AjaxResult.error("缺少参数！");
        }
        Map result=new HashMap<>();
        String flag = "0";
        String todoId = "";
        TodoInfo todoInfo=null;
        if(StringUtils.isEmpty(todoInfoParam.getTodoId())){
            //校验是否有权限处理待办
//            todoInfoParam.setTodoHandler(SecurityUtils.getUsername());
//            todoInfoParam.setTodoDataType("1");
            Map param=new HashMap();
            param.put("todoHandler",SecurityUtils.getUsername());
            param.put("businessKey",todoInfoParam.getBusinessKey());
            List<TodoInfo> todoList = todoInfoService.getAuthorizeToDoList(param);
            if(todoList!=null&&todoList.size()!=0){
                todoInfo = todoList.get(0);
                flag = "1";
            }
        }else{
             todoInfo = todoInfoService.selectTodoInfoById(todoInfoParam.getTodoId());
        }
        if (todoInfo != null) {
            if (SecurityUtils.getUsername().equals(todoInfo.getTodoHandler())) {
                //是当前人的待办
                flag = "1";
                if (todoInfo.getTodoType().equals("0") && todoInfo.getTodoDataType().equals("1")) {
                    //待办未处理
                    flag = "2";
                    todoId=todoInfo.getTodoId();
                }else if(todoInfo.getTodoType().equals("0") && todoInfo.getTodoDataType().equals("3")){
                    //待阅，需要将待阅置为已阅
                    todoInfo.setModifyTime(new Date());
                    todoInfo.setTodoType("1");
                    todoInfo.setTodoDataType("4");
                    todoInfoService.updateTodoInfo(todoInfo);
                    iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfo, false, todoInfo.getTaskId());
                }
                //自己的工单
                if("0".equals(flag)){
                    if(SecurityUtils.getUsername().equals(todoInfo.getTodoApplicant())){
                        flag = "1";
                    }
                }
            }
        }
        result.put("flag",flag);
        result.put("businessKey",todoInfoParam.getBusinessKey());
        if(StringUtils.isNotEmpty(todoId)){
            result.put("todoId",todoId);
        }else{
            result.put("todoId",todoInfoParam.getTodoId());
        }

        return AjaxResult.success(result);


    }


    /**
     * 判断待办是否当前人的，是否在当前环节
     *
     * @param todoInfoId 0：没有权限，1：可以查看，2可以处理
     * @return
     */

    @PostMapping("/todoinfo/validateTodoInfoIsMy")
    public AjaxResult validateTodoInfoIsMy(@RequestBody TodoParamVo todoInfoId) {
        String flag = "0";
        TodoInfo todoInfo = todoInfoService.selectTodoInfoById(todoInfoId.getTodoInfoId());
        if (todoInfo != null) {
            if (SecurityUtils.getUsername().equals(todoInfo.getTodoHandler())) {
                //是当前人的待办
                flag = "1";
                if (todoInfo.getTodoType().equals("0") && todoInfo.getTodoDataType().equals("1")) {
                    //待办未处理
                    flag = "2";
                }else if(todoInfo.getTodoType().equals("0") && todoInfo.getTodoDataType().equals("3")){
                    //待阅，需要将待阅置为已阅
                    todoInfo.setModifyTime(new Date());
                    todoInfo.setTodoType("1");
                    todoInfo.setTodoDataType("4");
                    todoInfoService.updateTodoInfo(todoInfo);
                    iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfo, false, todoInfo.getTaskId());
                }
            }
        }

        return AjaxResult.success(flag);


    }

    /**
     * 重推待办
     */
    @PostMapping("/todoinfo/againPush")
    @ApiOperation(value = "重推待办")
    public AjaxResult againPush(@RequestParam("todoId") String todoId) {
        //根据待办id获取待办任务信息
        TodoInfo todoInfo = todoInfoService.selectTodoInfoById(todoId);
        iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfo, false, todoInfo.getTaskId());
        return AjaxResult.success("重推待办成功");
    }

    /**
     * 意外处理
     */
    @PostMapping("/todoinfo/accidentDeal")
    @ApiOperation(value = "意外处理")
    public AjaxResult accidentDeal(@RequestParam("todoId") String todoId, @RequestParam("dealUser") String dealUser) {
        activitiService.accidentDeal(todoId, dealUser);
        return AjaxResult.success("处理成功");
    }

    /**
     * 回收流程
     */
    @PostMapping("/todoinfo/recycleProcess")
    @ApiOperation(value = "回收流程")
    public AjaxResult recycleProcess(@RequestParam("todoId") String todoId) {
        activitiService.recycleProcess(todoId);
        return AjaxResult.success("处理成功");
    }

    /**
     * 修改待办待阅
     */
    @ApiOperation("修改推送待办管理")
    @Log(title = "修改推送待办管理", businessType = BusinessType.UPDATE)
    @PostMapping("/updateTodoPushInfo")
    public AjaxResult updateTodoPushInfo(@RequestBody TodoInfoPush todoInfo) {
        return toAjax(iTodoInfoPushService.updateTodoInfoPush(todoInfo));
    }

    /**
     * 获取待办待阅详细信息
     */
    @ApiOperation("获取推送待办管理详细信息")
    @GetMapping(value = "/getTodoPushInfo/{todoId}")
    public AjaxResult getTodoPushInfo(@PathVariable("todoId") String todoId) {
        return AjaxResult.success(iTodoInfoPushService.selectTodoInfoPushById(todoId));
    }

}
