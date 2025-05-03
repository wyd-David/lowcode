package com.yabushan.form.controller;

import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.system.domain.vo.SysUserVo;
import com.yabushan.system.service.ISysUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/websocket/local")
@Slf4j
public class LocalThreadController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

    //保存表单数据
    @ApiOperation("保存任务数据")
    @PostMapping("/data")
    public AjaxResult saveFormInfo(@RequestBody Map paramMap){

        //获取用户账号
        if(!paramMap.containsKey("dataUid")){
            return AjaxResult.error("参数未上传");
        }
        if(!paramMap.containsKey("dataKey")){
            return AjaxResult.error("参数未上传1");
        }
        if(!paramMap.containsKey("dataType")){
            return AjaxResult.error("参数未上传2");
        }
        if(!paramMap.containsKey("data")){
            return AjaxResult.error("参数未上传3");

        }
        //获取用户key
        String userName = paramMap.get("dataUid").toString();
        String userKey = paramMap.get("dataKey").toString();
        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.setUserName(userName);
        sysUserVo.setPhonenumber(userKey);
        SysUser sysUser = sysUserService.selectUserByUserName(userName);
        if(sysUser==null ){
            return  AjaxResult.error("用户无效");
        }

        String data = paramMap.get("data").toString();
        String sql = "insert into prod_task_info(TASK_ID,CREATED_BY,TASK_NAME) VALUES(";

        String[] split = data.replace("[", "").replace("]", "").split(",");
        for(Integer i=0;i<split.length;i++){
            String value=null;
            value = sql+"'"+StringUtils.getUUID()+"',"+"'"+userName+"',"+"'"+split[i].trim()+"'"+")";
            System.out.println(value);
           try{
               sqlExecuteMapper.execute(value);
           }catch (Exception e){
               log.error(e.toString());
           }

        }


        return  AjaxResult.success("成功！");

    }

}
