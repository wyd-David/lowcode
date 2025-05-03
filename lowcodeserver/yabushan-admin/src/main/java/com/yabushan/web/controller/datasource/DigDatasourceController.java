package com.yabushan.web.controller.datasource;

import com.yabushan.common.annotation.Log;
import com.yabushan.common.constant.Constants;
import com.yabushan.common.constant.UserConstants;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.core.domain.model.LoginBody;
import com.yabushan.common.core.page.TableDataInfo;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.uuid.IdUtils;
import com.yabushan.datasource.Vo.*;
import com.yabushan.datasource.domain.DataStructuredColumn;
import com.yabushan.datasource.domain.DigDataResource;
import com.yabushan.datasource.service.DigDatasourceService;
import com.yabushan.datasource.service.DynamicDataSourceFactory;
import com.yabushan.datasource.utils.SQLValidator;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.framework.web.service.SysLoginService;
import com.yabushan.system.domain.DataApiInfos;
import com.yabushan.system.service.ISysUserService;
import com.yabushan.web.utils.ReadTxtFileNio;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.htrace.fasterxml.jackson.databind.JsonNode;
import org.apache.htrace.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Api(tags = "数据源服务类")
@RequestMapping("/datasource")
public class DigDatasourceController extends BaseController {

    @Autowired
    private DigDatasourceService digDatasourceService;

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysUserService userService;

    @Value("${XCX.APP_ID}")
    private String APP_ID;
    @Value("${XCX.APP_SECRET}")
    private String APP_SECRET;

    @Value("${XCX.INIT_PASSWORD}")
    private String initPassword;

    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";


    @PostMapping("/wx/login")
    @ApiOperation(value = "登录方法")
    public AjaxResult wxlogin(@RequestBody LoginBody loginBody)
    {
        String url = WX_LOGIN_URL + "?appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + loginBody.getCode() + "&grant_type=authorization_code";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(""));
            httpPost.setHeader("Content-type", "application/json");

            try (CloseableHttpResponse responseEntity = httpClient.execute(httpPost)) {
                String result = EntityUtils.toString(responseEntity.getEntity(), "UTF-8");

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(result);

                String openId = jsonNode.get("openid").asText();
                loginBody.setUsername(openId);

                loginBody.setPassword(initPassword);
                if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(loginBody.getUsername()))) {
                    //账号已存在
                }else{
                    SysUser user = new SysUser();
                    user.setDeptId(Long.valueOf("31726"));
                    user.setUserType("11");
                    user.setNickName("小程序_"+IdUtils.fastSimpleUUID());
                    user.setUserName(openId);
                    user.setPassword(SecurityUtils.encryptPassword(loginBody.getPassword()));
                    user.setSyncUserId(user.getUserName());
                    user.setMaindepartment("true");
                    userService.insertUser(user);
                }
                AjaxResult ajax = AjaxResult.success();

                // 生成令牌
                String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                        "Dig");
                ajax.put(Constants.TOKEN, token);
                return ajax;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }




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
        if(StringUtils.isNull(loginBody.getUsername()) || StringUtils.isNull(loginBody.getPassword())){
            return AjaxResult.error("用户名或密码不能为空");
        }
        try{
            boolean key = ReadTxtFileNio.isKey();
            if(!key){
                return AjaxResult.error("授权已过期，请联系授权（微信:yabushan）");
            }
        }catch (Exception e){
            return AjaxResult.error("授权已过期");
        }
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                "Dig");
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    @ApiOperation("数据源校验")
    @Log(title = "数据源校验META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/validataDatasource", method = RequestMethod.POST)
    public AjaxResult validataDatasource(@RequestBody DatabaseDto databaseDto) {
        try {
         return   digDatasourceService.validataDatasource(databaseDto);
        } catch (Exception e) {
           logger.error(e.getMessage());
           return AjaxResult.error(e.getMessage());
        }
    }
    @ApiOperation( "数据源注册")
    @Log(title = "数据源注册META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/registerDatasource", method = RequestMethod.POST)
    public AjaxResult registerDatasource(@RequestBody DataSourceDto databaseDto) {

        try {
           return digDatasourceService.registerDatasource(databaseDto);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

    }

    @ApiOperation("抓取元数据")
    @Log(title = "抓取元数据META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/getDataSourceMetaData", method = RequestMethod.POST)
    public AjaxResult getDataSourceMetaData(@RequestBody GetMetaDataDto getMetaDataDto) {
        try {
          return digDatasourceService.getDataSourceMetaData(getMetaDataDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }

    }
    @ApiOperation("查询数据库表")
    @Log(title = "查询数据库表META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/queryDataTable", method = RequestMethod.POST)
    public TableDataInfo queryDataTable(@RequestBody GetMetaDataDto  unitInstanceId) {
        startPage();
        List<DigDataResource> digDataResources = digDatasourceService.queryDataTable(unitInstanceId);
        return getDataTable(digDataResources);
    }

    @ApiOperation("查询数据库字段")
    @Log(title = "查询数据库字段META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/queryDataStructuredColumn", method = RequestMethod.POST)
    public TableDataInfo queryDataStructuredColumn(@RequestBody GetMetaDataDto  resourceId) {
        startPage();
        List<DataStructuredColumn> dataStructuredColumns = digDatasourceService.queryDataStructuredColumn(resourceId);
        return getDataTable(dataStructuredColumns);
    }


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

    @ApiOperation("查询接口")
    @Log(title = "动态查询sql查询接口META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/autoselect", method = RequestMethod.POST)
    public AjaxResult Autoselect(@RequestBody  Map params) {
        try {
            return digDatasourceService.Autoselect(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation("查询表数据接口")
    @Log(title = "查询表数据接口", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/tableDataselect", method = RequestMethod.POST)
    public AjaxResult tableDataselect(@RequestBody  Map params) {
        try {
            return digDatasourceService.tableDataselect(params);
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



    //服务编排接口
    @ApiOperation("服务编排接口")
    @Log(title = "服务编排接口META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public AjaxResult transaction(@RequestBody  List<TransactionDto> params) {
        try {
           return  digDatasourceService.transaction(params,false);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }


    //联邦查询接口
    @ApiOperation("联邦查询接口")
    @Log(title = "服务编排接口META", businessType = BusinessType.SELECT)
    @RequestMapping(value = "/unionSelect", method = RequestMethod.POST)
    public AjaxResult unionSelect(@RequestBody  List<TransactionDto> params) {
        try {
            return  digDatasourceService.transaction(params,true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return AjaxResult.error(e.getMessage());
        }
    }



    //获取表头部信息
    @ApiOperation("获取表头部信息")
    @RequestMapping(value = "/getTableInfo", method = RequestMethod.POST)
    public AjaxResult getTableInfo(TableAutoVo tableAutoVo) throws Exception {
        if(StringUtils.isEmpty(tableAutoVo.getResourceId()) || StringUtils.isNull(tableAutoVo.getResourceId())){
            return AjaxResult.error("资源ID未传入");
        }
        if(tableAutoVo.getPageSize() ==0){
            tableAutoVo.setPageSize(10);
        }
        if(tableAutoVo.getPageNum()==0){
            tableAutoVo.setPageNum(1);
        }
        AjaxResult ajaxResult = digDatasourceService.pageSelectByResourceId(tableAutoVo.getResourceId(), tableAutoVo.getParam(), tableAutoVo.getPageNum(), tableAutoVo.getPageSize());
        return ajaxResult;

    }

    @ApiOperation("获取SQL")
    @RequestMapping(value = "/validateSql", method = RequestMethod.POST)
    public AjaxResult validateSql(@RequestBody DataApiInfos dataApiInfo){
        boolean b=true;
        String s = null;
        if(!"1".equals(dataApiInfo.getDatasourceId())){
             s = DynamicDataSourceFactory.validateApiSql(dataApiInfo.getApiDefination());
             b = SQLValidator.validateSQL(s);
        }
        if(b){
            return AjaxResult.success("校验通过");
        }else{
            return AjaxResult.error("校验失败!sql为:"+s);
        }



    }




}
