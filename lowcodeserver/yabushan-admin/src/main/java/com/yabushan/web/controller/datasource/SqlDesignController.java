package com.yabushan.web.controller.datasource;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.datasource.service.DigDatasourceService;
import com.yabushan.form.mapper.SqlExecuteMapper;
import com.yabushan.system.service.ISysDictDataService;
import com.yabushan.web.controller.data.Extsql;
import com.yabushan.web.controller.data.Res;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "查询设计器")
@RequestMapping("/sqldesign")
public class SqlDesignController extends BaseController {

    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

    @Autowired
    private DigDatasourceService digDatasourceService;

    @Autowired
    private ISysDictDataService dictDataService;
    
    /**
     * 获取Table表数据
     * @param
     * @return
     */
    @RequestMapping(value = "/data/tables")
    public Res<?> getMessageTable(String datasourceId) {
        
        //1.校验数据源是否存在
        if(datasourceId==null || StringUtils.isEmpty(datasourceId)){
            return Res.ok("error","请输入数据库密钥");
        }

        
        String sql = "SELECT D.`resource_code` as table_name FROM `data_resource` D WHERE D.`datasource_id`='"+datasourceId+"' ORDER BY D.`resource_code` ";
        List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sql);
        return Res.ok("success",maps);
    }


    /**
     * 获取表字段数据
     * @param
     * @return
     */
    @RequestMapping(value = "/data/column")
    public Res<?> getMessageColumn(String datasourceId ,String tname ) {

        if(tname ==null || datasourceId ==null ){
            return Res.fail("类型参数错误！");
        }

        String sql = "select  F.`name` AS COLUMN_NAME FROM  `data_resource` D,  DATA_STRUCTURED_COLUMN f WHERE f.`resource_id` = d.`resource_id`   AND D.`datasource_id` = '"+datasourceId+"' AND D.`resource_code` = '"+tname+"' ORDER BY F.`name` ";
        List<Map<String, Object>> maps = sqlExecuteMapper.selectPublicItemList(sql);

        return Res.ok("success",maps);
    }


    /**
     * 执行sql语句
     * @param sql
     * @return
     */
    @RequestMapping(value = "/data/extSql")
    public Res<?> extSql(@RequestBody Extsql sql) throws Exception {
        if(sql.getSql().toUpperCase().contains("DROP ") || sql.getSql().toUpperCase().startsWith("TRUNCATE ") ){
            return Res.fail("不支持truncate和drop语句");
            
        }

        if("".equals(sql.getDatasourceId()) || null==sql.getDatasourceId() ){
            return Res.fail("数据源Id不能为空");
        }
        if("".equals(sql.getUserId()) || null==sql.getUserId() ){
            return Res.fail("用户密钥不能为空");
        }
        String sqldesign = dictDataService.selectDictLabel("sqldesign", sql.getUserId());
        if(sqldesign==null || StringUtils.isEmpty(sqldesign)){
            return Res.fail("用户密钥不正确");
        }

        AjaxResult ajaxResult = digDatasourceService.selectForDesign(sql.getDatasourceId(), sql.getSql());
        return Res.ok("success",ajaxResult);

    }
    
    
    
}
