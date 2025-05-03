package com.yabushan.datasource.service;

import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.datasource.Vo.*;
import com.yabushan.datasource.domain.*;
import com.yabushan.datasource.mapper.AppUnitInfoMapper;
import com.yabushan.datasource.mapper.DataStructuredColumnMapper;
import com.yabushan.datasource.mapper.DigDataResourceMapper;
import com.yabushan.datasource.service.impl.*;
import com.yabushan.datasource.utils.pages.PageFinderDTO;
import com.yabushan.datasource.utils.pages.QueryPageDTO;
import com.yabushan.system.domain.DataApiInfos;
import com.yabushan.system.service.IDataApiInfosService;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

@Service
public class DigDatasourceService {



    @Autowired
    private MySqlDatasourceMethodImpl mySqlDatasourceMethodService;

    @Autowired
    private OracleSqlDatasourceMethodImpl oracleDatasourceService;
    @Autowired
    private PostGreSqlDatasourceMethodImpl postgreDataService;
    @Autowired
    private ClickhouseSqlDatasourceMethodImpl clickhouseSqlService;
    @Autowired
    private SqlServerSqlDatasourceMethodImpl sqlServerSqlService;
    @Autowired
    private SqliteSqlDatasourceMethodImpl sqliteService;
    @Autowired
    private Db2SqlDatasourceMethodImpl db2Service;
    @Autowired
    private HiveSqlDatasourceMethodImpl hiveService;
    @Autowired
    private SeaboxSqlDatasourceMethodImpl seaboxService;

    @Autowired
    private IDataApiInfosService dataApiInfosService;

    @Autowired
    private IAppUnitInstanceService appUnitInstanceService;

    @Autowired
    private GreenplumSqlDatasourceMethodImpl greenplumService;

    @Autowired
    private IEtlWorkCollectService etlWorkCollectService;

    @Autowired
    private DigDataResourceMapper digDataResourceMapper;
    @Autowired
    private DataStructuredColumnMapper dataStructuredColumnMapper;

    @Autowired
    private AppUnitInfoMapper appUnitInfoMapper;
    public AjaxResult validataDatasource(DatabaseDto databaseDto) throws Exception {
        boolean b =true;
        if(Constants.MYSQL.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("com.mysql.jdbc.Driver");
            b= mySqlDatasourceMethodService.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }else if(Constants.ORACLE.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("oracle.jdbc.driver.OracleDriver");
            b= oracleDatasourceService.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }else if(Constants.POSTGRE.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("org.postgresql.Driver");
            b= postgreDataService.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }else if(Constants.CLICK_HOUSE.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("com.clickhouse.jdbc.ClickHouseDriver");
            b= clickhouseSqlService.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }else if(Constants.SQLSERVER.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            b= sqlServerSqlService.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }else if(Constants.SQLITE.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("org.sqlite.JDBC");
            b= sqliteService.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }else if(Constants.DB2.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("com.ibm.db2.jcc.DB2Driver");
            b= db2Service.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }else if(Constants.HIVE.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("org.apache.hive.jdbc.HiveDriver");
            b= hiveService.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }else if(Constants.SEABOX.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("org.postgresql.Driver");
            b= seaboxService.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }else if(Constants.GREENPLUM.equals(databaseDto.getDriverClass())){
            databaseDto.setDriverClass("org.postgresql.Driver");
            b= greenplumService.validataDatasource(databaseDto.getDriverClass(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getDatabase(), databaseDto.getUserName(), databaseDto.getPassword());
        }
        if(b){
            return AjaxResult.success("校验成功");
        }


        return AjaxResult.error("校验失败！");

    }
    @SneakyThrows
    public AjaxResult registerDatasource(DataSourceDto databaseDto) {

        boolean b =true;
        if(Constants.MYSQL.equals(databaseDto.getDriverClass())){
            b = mySqlDatasourceMethodService.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }else if(Constants.POSTGRE.equals(databaseDto.getDriverClass())){
            b = postgreDataService.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }else if(Constants.ORACLE.equals(databaseDto.getDriverClass())){
            b = oracleDatasourceService.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }else if(Constants.CLICK_HOUSE.equals(databaseDto.getDriverClass())){
            b = clickhouseSqlService.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }else if(Constants.SQLSERVER.equals(databaseDto.getDriverClass())){
            b = sqlServerSqlService.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }else if(Constants.SQLITE.equals(databaseDto.getDriverClass())){
            b = sqliteService.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }else if(Constants.DB2.equals(databaseDto.getDriverClass())){
            b = db2Service.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }else if(Constants.HIVE.equals(databaseDto.getDriverClass())){
            b = hiveService.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }else if(Constants.SEABOX.equals(databaseDto.getDriverClass())){
            b = seaboxService.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }else if(Constants.GREENPLUM.equals(databaseDto.getDriverClass())){
            b = greenplumService.registerDatasource(databaseDto.getUserName(), databaseDto.getPassword(),
                    databaseDto.getDatabase(), databaseDto.getVersionCode(), databaseDto.getIp(), databaseDto.getPort(), databaseDto.getInstanceDisplyName(),databaseDto.getSchema(),databaseDto.getInstanceId());
        }

        if(b){
            return AjaxResult.success("数据源注册成功");
        }

        return AjaxResult.error("数据源注册失败！");

    }

    public AjaxResult getDataSourceMetaData( GetMetaDataDto getMetaDataDto) throws Exception {
        boolean b =true;
        if(Constants.MYSQL.equals(getMetaDataDto.getDriverClass())){
            b = mySqlDatasourceMethodService.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }else if(Constants.POSTGRE.equals(getMetaDataDto.getDriverClass())){
            b = postgreDataService.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }else if(Constants.ORACLE.equals(getMetaDataDto.getDriverClass())){
            b = oracleDatasourceService.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }else if(Constants.CLICK_HOUSE.equals(getMetaDataDto.getDriverClass())){
            b = clickhouseSqlService.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }else if(Constants.SQLSERVER.equals(getMetaDataDto.getDriverClass())){
            b = sqlServerSqlService.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }else if(Constants.SQLITE.equals(getMetaDataDto.getDriverClass())){
            b = sqliteService.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }else if(Constants.DB2.equals(getMetaDataDto.getDriverClass())){
            b = db2Service.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }else if(Constants.HIVE.equals(getMetaDataDto.getDriverClass())){
            b = hiveService.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }else if(Constants.SEABOX.equals(getMetaDataDto.getDriverClass())){
            b = seaboxService.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }else if(Constants.GREENPLUM.equals(getMetaDataDto.getDriverClass())){
            b = greenplumService.getDataSourceMetaData(getMetaDataDto.getDatasourceId(), getMetaDataDto.getTableNameList());
        }
        if(b){
            return AjaxResult.success("抓取元数据成功");
        }

        return AjaxResult.error("抓取元数据失败！");

    }
    public List<DigDataResource> queryDataTable( GetMetaDataDto  unitInstanceId) {
        List<DigDataResource> digDataResources = mySqlDatasourceMethodService.queryDataTable(unitInstanceId.getDatasourceId(),unitInstanceId.getTableName());
        return digDataResources;
    }

    public  List<DataStructuredColumn> queryDataStructuredColumn( GetMetaDataDto  resourceId) {
        List<DataStructuredColumn> dataStructuredColumns = mySqlDatasourceMethodService.queryDataStructuredColumn(resourceId.getResourceId());
        return dataStructuredColumns;
    }


    /**
     *
     * 新增
     * @param params
     * @return
     */
    public AjaxResult insert( Map params) throws Exception {
        if(params==null || !params.containsKey("ApiCode")){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode");
        }
        DataApiInfos dataApiInfos = dataApiInfosService.selectDataApiInfosCode(params.get("ApiCode").toString());
        if(dataApiInfos==null){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode是否有效");
        }
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(dataApiInfos.getDatasourceId());
        if(appUnitInstance==null ){
            return AjaxResult.error("请确认是否有注册数据源");
        }
        //权限APi权限
        AjaxResult ajaxResult = validateMap(dataApiInfos);
        if(ajaxResult!=null){
            return  ajaxResult;
        }

        DataSource dataSource=getDataSource(appUnitInstance,dataApiInfos);
        Connection connection = dataSource.getConnection();
        int insert = DynamicDataSourceFactory.insert(connection, dataApiInfos.getApiDefination(),params);
        return AjaxResult.success(insert);


    }


    public AjaxResult update( Map params) throws Exception {
        if(params==null || !params.containsKey("ApiCode")){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode");
        }
        DataApiInfos dataApiInfos = dataApiInfosService.selectDataApiInfosCode(params.get("ApiCode").toString());
        if(dataApiInfos==null){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode是否有效");
        }
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(dataApiInfos.getDatasourceId());
        if(appUnitInstance==null ){
            return AjaxResult.error("请确认是否有注册数据源");
        }

        //权限APi权限
        AjaxResult ajaxResult = validateMap(dataApiInfos);
        if(ajaxResult!=null){
            return  ajaxResult;
        }


        DataSource dataSource=getDataSource(appUnitInstance,dataApiInfos);
        Connection connection = dataSource.getConnection();
        int insert = DynamicDataSourceFactory.update(connection, dataApiInfos.getApiDefination(),params);
        return AjaxResult.success(insert);


    }


    @ApiOperation("删除接口")
    @Log(title = "删除接口META", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public AjaxResult delete( Map params) throws Exception {
        if(params==null || !params.containsKey("ApiCode")){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode");
        }
        DataApiInfos dataApiInfos = dataApiInfosService.selectDataApiInfosCode(params.get("ApiCode").toString());
        if(dataApiInfos==null){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode是否有效");
        }
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(dataApiInfos.getDatasourceId());
        if(appUnitInstance==null ){
            return AjaxResult.error("请确认是否有注册数据源");
        }

        //权限APi权限
        AjaxResult ajaxResult = validateMap(dataApiInfos);
        if(ajaxResult!=null){
            return  ajaxResult;
        }
        DataSource dataSource=getDataSource(appUnitInstance,dataApiInfos);
        Connection connection = dataSource.getConnection();
        int insert = DynamicDataSourceFactory.delete(connection, dataApiInfos.getApiDefination(),params);
        return AjaxResult.success(insert);

    }

    //查询
    public AjaxResult select(Map params) throws Exception {
        if(params==null || !params.containsKey("ApiCode")){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode");
        }
        DataApiInfos dataApiInfos = dataApiInfosService.selectDataApiInfosCode(params.get("ApiCode").toString());
        if(dataApiInfos==null){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode是否有效");
        }
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(dataApiInfos.getDatasourceId());
        if(appUnitInstance==null ){
            return AjaxResult.error("请确认是否有注册数据源");
        }

        //权限APi权限
        AjaxResult ajaxResult = validateMap(dataApiInfos);
        if(ajaxResult!=null){
            return  ajaxResult;
        }

        DataSource dataSource=getDataSource(appUnitInstance,dataApiInfos);
        Connection connection = dataSource.getConnection();
        List<Map<String, Object>> maps = DynamicDataSourceFactory.selectListMaps(connection, dataApiInfos.getApiDefination(), params);
        return AjaxResult.success(maps);

    }


    /**
     * * 查询设计器
     * @param datasourceId
     * @param sql
     * @return
     * @throws Exception
     */
    public AjaxResult selectForDesign(String datasourceId,String sql) throws Exception {

        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(datasourceId);
        if(appUnitInstance==null ){
            return AjaxResult.error("请确认是否有注册数据源");
        }
        DataSource dataSource=getDataSource(appUnitInstance.getAppClassCode(),datasourceId);
        Connection connection = dataSource.getConnection();
        if(sql.trim().toUpperCase().startsWith("SELECT")){
            //查询语句
            List<Map<String, Object>> maps = DynamicDataSourceFactory.selectListMaps(connection, sql, null);
            return AjaxResult.success(maps);
        }else{
            //非查询语句
            int update = DynamicDataSourceFactory.update(connection, sql, null);
            return AjaxResult.success(update);
        }


    }

    public void validateApiSQLIsOK(String datasourceId,String apiSql) throws Exception {
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(datasourceId);
        if(appUnitInstance==null){
            throw new Exception("数据引擎不存在！");

        }
        DataSource dataSource=getDataSource(appUnitInstance.getAppClassCode(),datasourceId);
        Connection connection = dataSource.getConnection();
       DynamicDataSourceFactory.validateSql(connection, apiSql, null);

    }

    //查询表字段信息
    public AjaxResult tableDataselect(Map params) throws Exception {
        if(params==null || !params.containsKey("resourceId")){
            return AjaxResult.error("请确认是否有传入resourceId");
        }
        DigDataResource resource = digDataResourceMapper.selectDataResourceById(params.get("resourceId").toString());
        if(resource==null){
            return AjaxResult.error("请确认是否有传入resourceId是否有效");
        }
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(resource.getDatasourceId());
        if(appUnitInstance==null ){
            return AjaxResult.error("请确认是否有注册数据源");
        }

        DataSource dataSource=getDataSource(appUnitInstance.getAppClassCode(),appUnitInstance.getInstanceId());//getDataSource(appUnitInstance,dataApiInfos);
        Connection connection = dataSource.getConnection();
        List<Map<String, Object>> maps = DynamicDataSourceFactory.selectListMaps(connection, "select * from "+resource.getResourceCode(), params);
        return AjaxResult.success(maps);

    }
    //分页查询、根据API
    public AjaxResult pageSelect(  Map params) throws Exception {
        if(params==null || !params.containsKey("ApiCode")){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode");
        }
        DataApiInfos dataApiInfos = dataApiInfosService.selectDataApiInfosCode(params.get("ApiCode").toString());
        if(dataApiInfos==null){
            return AjaxResult.error("请确认是否有传入数据服务编码ApiCode是否有效");
        }
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(dataApiInfos.getDatasourceId());
        if(appUnitInstance==null ){
            return AjaxResult.error("请确认是否有注册数据源");
        }

        QueryPageDTO queryPageDTO = null;
        if(params.containsKey("pageNum") && params.containsKey("pageSize")){
            Integer pageNum = (Integer) params.get("pageNum");
            Integer pageSize = (Integer) params.get("pageSize");
            queryPageDTO = new QueryPageDTO(pageNum,pageSize);
        }else{
            queryPageDTO = new QueryPageDTO();
        }

        //权限APi权限
        AjaxResult ajaxResult = validateMap(dataApiInfos);
        if(ajaxResult!=null){
            return  ajaxResult;
        }

        DataSource dataSource=getDataSource(appUnitInstance,dataApiInfos);
        Connection connection = dataSource.getConnection();
        PageFinderDTO<Map<String, Object>> mapPageFinderDTO = DynamicDataSourceFactory.selectPageListMaps(connection, dataApiInfos.getApiDefination(), params, queryPageDTO);
        return AjaxResult.success(mapPageFinderDTO);

    }


    //根据表ID进行 分页查询
    public AjaxResult pageSelectByResourceId( String resourceId,String param,int pageNum,int pageSize ) throws Exception {

        DigDataResource digDataResource = digDataResourceMapper.selectDataResourceById(resourceId);
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(digDataResource.getDatasourceId());
        if(appUnitInstance==null ){
            return AjaxResult.error("请确认是否有注册数据源");
        }
        QueryPageDTO queryPageDTO = new QueryPageDTO();
        queryPageDTO.setPageNum(pageNum);
        queryPageDTO.setPageSize(pageSize);
        String sql  = "select * from "+digDataResource.getResourceCode()+" where 1=1 ";

        //获取到该表的所有字段
        DataStructuredColumn dataStructuredColumn = new DataStructuredColumn();
        dataStructuredColumn.setResourceId(digDataResource.getResourceId());
        List<DataStructuredColumn> dataStructuredColumns = dataStructuredColumnMapper.selectDataStructuredColumnList(dataStructuredColumn);
        Map paramKeyvalue = null;
        StringBuffer buffer = new StringBuffer();
        //组装sql
        if(param!=null && StringUtils.isNotEmpty(param) && StringUtils.isNotNull(param)){
            paramKeyvalue= new HashMap();
            buffer.append(" AND ( ");
            for (DataStructuredColumn structuredColumn : dataStructuredColumns) {

                buffer.append("{!  ").append(structuredColumn.getColumnCode()).append(" like  ").append("'%").append(param).append("%'").append(" }").append(" or ");
                paramKeyvalue.put(structuredColumn.getColumnCode(),param);
            }
            String substring = buffer.toString().substring(0, buffer.toString().length() - 3)+" ) ";
            sql =sql+substring;
        }
        //封装请求头
        List<TableHeader> Header = new ArrayList();
        TableHeader tableHeader =null;
        for (DataStructuredColumn structuredColumn : dataStructuredColumns) {
            tableHeader  = new TableHeader();
            tableHeader.setLabel(structuredColumn.getDisplayName()==null?structuredColumn.getColumnCode():structuredColumn.getDisplayName());
            tableHeader.setProp(structuredColumn.getColumnCode());
            Header.add(tableHeader);
        }

        //封装数据
        DataSource dataSource = getDataSource(appUnitInstance.getAppClassCode(),appUnitInstance.getInstanceId());
        Connection connection = dataSource.getConnection();
        PageFinderDTO<Map<String, Object>> mapPageFinderDTO = DynamicDataSourceFactory.selectPageListMapsForTable(connection, sql, paramKeyvalue, queryPageDTO);
        DatasourceTableVo datasourceTableVo = new DatasourceTableVo();
        datasourceTableVo.setData(mapPageFinderDTO);
        datasourceTableVo.setTableHeaderList(Header);
        return AjaxResult.success(datasourceTableVo);

    }




    public AjaxResult transaction(  List<TransactionDto> params,boolean isSelect) throws Exception {
        DataSource dataSource=null;
        Map<String,Connection> connectionMap = new HashMap<>();

        for (TransactionDto param : params) {
            if(params==null || StringUtils.isEmpty(param.getApiCode())){
                return AjaxResult.error("请确认是否有传入数据服务编码ApiCode"+param.getApiCode());
            }else{
                DataApiInfos dataApiInfos = dataApiInfosService.selectDataApiInfosCode(param.getApiCode());
                if(dataApiInfos==null){
                    return AjaxResult.error("请确认是否有传入数据服务编码ApiCode是否有效"+param.getApiCode());
                }
                AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(dataApiInfos.getDatasourceId());
                if(appUnitInstance==null ){
                    return AjaxResult.error("请确认是否有注册数据源"+dataApiInfos.getDatasourceId());
                }
                AjaxResult ajaxResult = validateMap(dataApiInfos);
                if(ajaxResult!=null){return  ajaxResult;}
            }
        }

        for (TransactionDto param : params) {
            if(params==null || StringUtils.isEmpty(param.getApiCode())){
                return AjaxResult.error("请确认是否有传入数据服务编码ApiCode"+param.getApiCode());
            }else{
                DataApiInfos dataApiInfos = dataApiInfosService.selectDataApiInfosCode(param.getApiCode());
                param.setDefitionSql(dataApiInfos.getApiDefination());
                AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(dataApiInfos.getDatasourceId());
                param.setDriverType(appUnitInstance.getAppClassCode());
                param.setDriverAndInstanceId(appUnitInstance.getAppClassCode()+"_"+dataApiInfos.getDatasourceId());
                if(connectionMap.containsKey(param.getDriverAndInstanceId())){
                    continue;
                }
                //封装需要用到的数据源
                dataSource=getDataSource(appUnitInstance,dataApiInfos);
                //封装用到的数据源
                if(!connectionMap.containsKey(param.getDriverAndInstanceId())){
                    Connection connection = dataSource.getConnection();
                    connectionMap.put(param.getDriverAndInstanceId(),connection);
                }
            }

        }
        //将需要执行的语句按从小到大的顺序排序执行
        Collections.sort(params, new Comparator<TransactionDto>() {
            @Override
            public int compare(TransactionDto s1, TransactionDto s2) {
                return s1.getSort() - s2.getSort();
            }
        });
        if(isSelect){
            //联邦查询
            List list = DynamicDataSourceFactory.unionSelect(connectionMap, params);
            return AjaxResult.success(list);
        }else{
            //执行服务编排
            int transaction = DynamicDataSourceFactory.transaction(connectionMap, params);
            return AjaxResult.success(transaction);

        }


    }


    public String validateDatasourceIsAble(String datasourceId){
        AppUnitInfo appUnitInfo = appUnitInfoMapper.selectAppUnitInfoById(datasourceId);
        if(  !"启用".equals(appUnitInfo.getIsDelete())  || !"正常".equals(appUnitInfo.getAppStatus())  ){
            return "已禁用数据引擎"+appUnitInfo.getInstancedisplyname();

        }
        return null;

    }

    public AjaxResult validateMap(DataApiInfos  dataApiInfos){
        String result = validateDatasourceIsAble(dataApiInfos.getDatasourceId());
        if(result!=null){
            return AjaxResult.error("数据引擎已禁用，请联系管理员调整！");
        }


        String apiStatus = dataApiInfos.getApiStatus();
        if("N".equals(apiStatus)){
            return AjaxResult.error("API已停用，请联系管理员调整！");
        }else if("G".equals(apiStatus)){
            //内部使用
            //判断是否有权限使用,同一个公司的人才能使用
            String username = SecurityUtils.getUsername();
            String createBy = dataApiInfos.getCreateBy();
            Long companyIdByUserName = dataApiInfosService.getCompanyIdByUserName(username);
            Long companyIdByUserName1 = dataApiInfosService.getCompanyIdByUserName(createBy);
            if(companyIdByUserName==null && companyIdByUserName==null){
                return null;
            }
            if(!companyIdByUserName1.equals(companyIdByUserName)){
                return AjaxResult.error("API只允许内部使用，请联系管理员调整！");
            }
        }
        return null;
    }


    /**
     * 数据抽取
     *  @param etlWorkId
     * @throws Exception
     */
    public void dataFlow(String etlWorkId,String type) {
        //最后运行状态0:初始，1:运行中,2:成功,3:失败，4:等待运行,5:依赖条件未发生
        EtlWorkCollect etlWorkCollect=null;
        Integer dataflow=null;

        AppUnitInstance appUnitInstanccWriter=null;
        DataSource writer =null;
        EtlResultVo etlResultVo = new EtlResultVo();
        try {
            //0.获取作业信息
            etlWorkCollect = etlWorkCollectService.selectEtlWorkCollectById(etlWorkId);
            etlWorkCollect.setLastStatus("1");//执行中
            etlWorkCollect.setLastUpdateTime(new Date());
            etlWorkCollectService.updateEtlWorkCollect(etlWorkCollect);
            if("dataFlow".equals(type)){
                //1.获取读取的服务
                DataApiInfos dataApiRead = dataApiInfosService.selectDataApiInfosCode(etlWorkCollect.getApiCodeIn());
                AppUnitInstance appUnitInstanceRead =appUnitInstanceService.selectAppUnitInstanceById(dataApiRead.getDatasourceId());
                DataSource read = getDataSource(appUnitInstanceRead,dataApiRead);

                //2.获取写入数据源
                appUnitInstanccWriter =appUnitInstanceService.selectAppUnitInstanceById(etlWorkCollect.getCollectToDatasourceid());
                writer = getDataSource(appUnitInstanccWriter.getAppClassCode(),etlWorkCollect.getCollectToDatasourceid());

                //3.获取主键字段
                DigDataResource digDataResource = new DigDataResource();
                digDataResource.setResourceTableName(etlWorkCollect.getCollectToTableName());
                digDataResource.setDatasourceId(etlWorkCollect.getCollectToDatasourceid());
                List<DigDataResource> digDataResources = digDataResourceMapper.selectDataResourceList(digDataResource);
                String resourceId = digDataResources.get(0).getResourceId();
                DataStructuredColumn dataStructuredColumn = new DataStructuredColumn();
                dataStructuredColumn.setResourceId(resourceId);
                dataStructuredColumn.setIsPrimaryKey("1");
                List<DataStructuredColumn> dataStructuredColumns = dataStructuredColumnMapper.selectDataStructuredColumnList(dataStructuredColumn);

                //4.开始抽取
                    //抽取前执行脚本
                if(StringUtils.isNotNull(etlWorkCollect.getBeforeSql()) && StringUtils.isNotEmpty(etlWorkCollect.getBeforeSql())){
                    DynamicDataSourceFactory.update(writer.getConnection(),etlWorkCollect.getBeforeSql(),null);
                }
                    etlResultVo = DynamicDataSourceFactory.dataflow(etlWorkCollect, read, writer, dataApiRead.getApiDefination(), appUnitInstanccWriter.getAppClassCode(),dataStructuredColumns);
                    //抽取后执行脚本
                if(StringUtils.isNotNull(etlWorkCollect.getAfterSql()) && StringUtils.isNotEmpty(etlWorkCollect.getAfterSql())){
                    DynamicDataSourceFactory.update(writer.getConnection(),etlWorkCollect.getAfterSql(),null);
                }

            }else if("dataCollect".equals(type)){
                //1.获取读取的服务
                AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(etlWorkCollect.getCollectResouceId());
                String readSql ="SELECT * FROM "+etlWorkCollect.getCollectTableName();
                DataSource read = getDataSource(appUnitInstance.getAppClassCode(),etlWorkCollect.getCollectResouceId());

                //2.获取写入服务
                appUnitInstanccWriter =appUnitInstanceService.selectAppUnitInstanceById(etlWorkCollect.getCollectToDatasourceid());
                writer = getDataSource(appUnitInstanccWriter.getAppClassCode(),etlWorkCollect.getCollectToDatasourceid());

                //3.开始采集数据
                     //抽取前执行脚本
                if(StringUtils.isNotNull(etlWorkCollect.getBeforeSql()) && StringUtils.isNotEmpty(etlWorkCollect.getBeforeSql())){
                    DynamicDataSourceFactory.update(writer.getConnection(),etlWorkCollect.getBeforeSql(),null);
                }
                etlResultVo = DynamicDataSourceFactory.dataCollect(etlWorkCollect, read, writer, readSql, null,appUnitInstanccWriter.getAppClassCode());
                     //抽取后执行脚本
                if(StringUtils.isNotNull(etlWorkCollect.getAfterSql()) && StringUtils.isNotEmpty(etlWorkCollect.getAfterSql())){
                    DynamicDataSourceFactory.update(writer.getConnection(),etlWorkCollect.getAfterSql(),null);
                }

            }
            if(etlResultVo==null){
                etlWorkCollect.setLastStatus("6");
                etlWorkCollect.setCollectResult(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss")+"依赖条件未发生"+"\r\n<br/>"+etlWorkCollect.getCollectResult());

            }else{
                if(etlResultVo.getAffectROW()==null){
                    etlWorkCollect.setLastStatus("5");
                }else{
                    etlWorkCollect.setFieldValue(etlResultVo.getMaxValue());
                    etlWorkCollect.setCollectResult(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss")+"执行成功："+etlResultVo.getAffectROW()+"条数据入库,最大值为:"+etlResultVo.getMaxValue()+"   \r\n<br/>"+etlWorkCollect.getCollectResult());
                    etlWorkCollect.setLastStatus("2");
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            etlWorkCollect.setCollectResult(DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss")+"执行失败："+"\n"+e.getMessage()+etlWorkCollect.getCollectResult());
            etlWorkCollect.setLastStatus("3");
        }finally {
            etlWorkCollect.setLastUpdateTime(new Date());
            etlWorkCollectService.updateEtlWorkCollect(etlWorkCollect);
        }

    }


    //根据数据源注册主键动态查询查询
    public AjaxResult Autoselect(Map params) throws Exception {
        if(params==null || !params.containsKey("datasource_id")){
            return AjaxResult.error("请确认是否有传入主键ID：datasource_id");
        }

        if(params==null || !params.containsKey("sql")){
            return AjaxResult.error("请确认是否有传入sql");
        }
        AppUnitInstance appUnitInstance = appUnitInstanceService.selectAppUnitInstanceById(params.get("datasource_id").toString());
        if(appUnitInstance==null ){
            return AjaxResult.error("请确认是否有注册数据源");
        }

        DataApiInfos dataApiInfos = new DataApiInfos();

        dataApiInfos.setDatasourceId(appUnitInstance.getInstanceId());


        DataSource dataSource=getDataSource(appUnitInstance,dataApiInfos);
        Connection connection = dataSource.getConnection();
        List<Map<String, Object>> maps = DynamicDataSourceFactory.selectListMaps(connection, params.get("sql").toString(), null);
        return AjaxResult.success(maps);

    }


    private DataSource getDataSource(AppUnitInstance appUnitInstance,DataApiInfos dataApiInfos) throws Exception {
        DataSource dataSource=null;
        if(Constants.MYSQL.equals(appUnitInstance.getAppClassCode())){
            dataSource =  mySqlDatasourceMethodService.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }else if(Constants.POSTGRE.equals(appUnitInstance.getAppClassCode())){
            dataSource =  postgreDataService.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }else if(Constants.CLICK_HOUSE.equals(appUnitInstance.getAppClassCode())){
            dataSource =  clickhouseSqlService.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }else if(Constants.ORACLE.equals(appUnitInstance.getAppClassCode())){
            dataSource =  oracleDatasourceService.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }else if(Constants.SQLSERVER.equals(appUnitInstance.getAppClassCode())){
            dataSource =  sqlServerSqlService.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }else if(Constants.SQLITE.equals(appUnitInstance.getAppClassCode())){
            dataSource =  sqliteService.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }else if(Constants.DB2.equals(appUnitInstance.getAppClassCode())){
            dataSource =  db2Service.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }else if(Constants.HIVE.equals(appUnitInstance.getAppClassCode())){
            dataSource =  hiveService.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }else if(Constants.SEABOX.equals(appUnitInstance.getAppClassCode())){
            dataSource =  seaboxService.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }else if(Constants.GREENPLUM.equals(appUnitInstance.getAppClassCode())){
            dataSource =  greenplumService.getOneDatasourceFromPool(dataApiInfos.getDatasourceId());
        }
        return  dataSource;
    }


    private DataSource getDataSource(String  appClassCode,String  datasourceId) throws Exception {
        DataSource dataSource=null;
        if(Constants.MYSQL.equals(appClassCode)){
            dataSource =  mySqlDatasourceMethodService.getOneDatasourceFromPool(datasourceId);
        }else if(Constants.POSTGRE.equals(appClassCode)){
            dataSource =  postgreDataService.getOneDatasourceFromPool(datasourceId);
        }else if(Constants.CLICK_HOUSE.equals(appClassCode)){
            dataSource =  clickhouseSqlService.getOneDatasourceFromPool(datasourceId);
        }else if(Constants.ORACLE.equals(appClassCode)){
            dataSource =  oracleDatasourceService.getOneDatasourceFromPool(datasourceId);
        }else if(Constants.SQLSERVER.equals(appClassCode)){
            dataSource =  sqlServerSqlService.getOneDatasourceFromPool(datasourceId);
        }else if(Constants.SQLITE.equals(appClassCode)){
            dataSource =  sqliteService.getOneDatasourceFromPool(datasourceId);
        }else if(Constants.DB2.equals(appClassCode)){
            dataSource =  db2Service.getOneDatasourceFromPool(datasourceId);
        }else if(Constants.HIVE.equals(appClassCode)){
            dataSource =  hiveService.getOneDatasourceFromPool(datasourceId);
        }else if(Constants.SEABOX.equals(appClassCode)){
            dataSource =  seaboxService.getOneDatasourceFromPool(datasourceId);
        }else if(Constants.GREENPLUM.equals(appClassCode)){
            dataSource =  greenplumService.getOneDatasourceFromPool(datasourceId);
        }
        return  dataSource;
    }
}
