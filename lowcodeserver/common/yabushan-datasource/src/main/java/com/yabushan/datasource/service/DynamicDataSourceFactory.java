package com.yabushan.datasource.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.datasource.Vo.Constants;
import com.yabushan.datasource.Vo.EtlResultVo;
import com.yabushan.datasource.Vo.TransactionDto;
import com.yabushan.datasource.domain.DataStructuredColumn;
import com.yabushan.datasource.domain.EtlWorkCollect;
import com.yabushan.datasource.domain.MetadataDatasourceTypeParam;
import com.yabushan.datasource.utils.DBUtil;
import com.yabushan.datasource.utils.pages.PageFinderDTO;
import com.yabushan.datasource.utils.pages.QueryPageDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class DynamicDataSourceFactory {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceFactory.class);

    private static String CONNECTION_ERROR = "数据库连接失败[database connection refused]";
    private static String TRANSFORM_ERROR = "数据对象转换失败[data transform error]";

    static Statement stat=null;

    private static Map<String, DataSource> dataSourceMap = new HashMap<>();

    public static DataSource getDataSource(String dataSourceName) {
        return dataSourceMap.get(dataSourceName);
    }

    public static void registerDataSource(String dataSourceName, DataSource dataSource) {
        dataSourceMap.put(dataSourceName, dataSource);
    }

    /**
     * *创建一个JDBC数据源
     * @param dataSourceName
     * @param url
     * @param username
     * @param password
     * @param paramList
     * @throws Exception
     */
    public static void createDataSource(String dataSourceName, String url, String username, String password, List<MetadataDatasourceTypeParam> paramList) throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        if(paramList!=null  && paramList.size()>0){
            // 其他配置项...
            //dataSource.setMaxEvictableIdleTimeMillis(10000);
            for(int i=0;i<paramList.size();i++){
                MetadataDatasourceTypeParam metadataDatasourceTypeParam = paramList.get(i);
                if("initialSize".equals(metadataDatasourceTypeParam.getParamCode())){
                    dataSource.setInitialSize(Integer.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }
                if("maxActive".equals(metadataDatasourceTypeParam.getParamCode())){
                    dataSource.setMaxActive(Integer.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }
                if("maxIdle".equals(metadataDatasourceTypeParam.getParamCode())){
                    dataSource.setMaxIdle(Integer.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }
                if("minIdle".equals(metadataDatasourceTypeParam.getParamCode())){
                    dataSource.setMinIdle(Integer.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }
                if("maxWait".equals(metadataDatasourceTypeParam.getParamCode())){
                    dataSource.setMaxWait(Integer.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }
                if("testOnBorrow".equals(metadataDatasourceTypeParam.getParamCode())){
                    //获取连接时进行测试
                    dataSource.setTestOnBorrow(Boolean.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }
                if("removeAbandoned".equals(metadataDatasourceTypeParam.getParamCode())){
                    //是否自动回收超时连接
                    dataSource.setRemoveAbandoned(Boolean.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }
                if("removeAbandonedTimeout".equals(metadataDatasourceTypeParam.getParamCode())){
                    //超时连接时间（秒）
                    dataSource.setRemoveAbandoned(Boolean.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }
                if("timeBetweenEvictionRunsMillis".equals(metadataDatasourceTypeParam.getParamCode())){
                    dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }
                if("minEvictableIdleTimeMillis".equals(metadataDatasourceTypeParam.getParamCode())){
                    dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(metadataDatasourceTypeParam.getParamValue()));
                }

            }

        }

        dataSourceMap.put(dataSourceName, dataSource);
    }


    /**
     * 获取一个数据源
     * @param datasourceId
     * @return
     * @throws Exception
     */
    public static DataSource createAndGetOneDatasource(String datasourceId,String url,String userName,String passWord,List<MetadataDatasourceTypeParam> paramList) throws Exception {
        DataSource dataSource = DynamicDataSourceFactory.getDataSource(datasourceId);
        if (dataSource == null) {
            DynamicDataSourceFactory.createDataSource(datasourceId, url, userName, passWord,paramList);
            dataSource = DynamicDataSourceFactory.getDataSource(datasourceId);
        }

        return  dataSource;
    }

    public  boolean execute(Connection conn, String sql) throws Exception {
        try{
            //获取数据库操作对象（专门执行sql语句的对象）Statement createStatement()
            //创建一个 Statement对象，用于将SQL语句发送到数据库。  (statement是专门执行sql语句的)
            stat = conn.createStatement();
            boolean execute = stat.execute(sql);
            return  execute;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            DBUtil.close(conn,stat,null);
        }
    }

    public static int executeUpdate(Connection conn, String sql) throws Exception {
        try{
            //获取数据库操作对象（专门执行sql语句的对象）Statement createStatement()
            stat = conn.createStatement();
            int execute = stat.executeUpdate(sql);
            return  execute;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            DBUtil.close(conn,stat,null);
        }
    }

    /**
     * 查询返回listMap数据
     * @param conn
     * @param sql
     * @return
     */
    public  static List<Map<String,Object>> selectListMaps(Connection conn,String sql,Map columnValues){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        PreparedStatement ps = null;    //获取PreparedStatement接口对象
        ResultSet set = null;
        try {
            sql=prepareSql(sql,columnValues);
           sql= sql.replace("&gt;",">").replace("&lt;","<");

            ps = conn.prepareStatement(sql);
            set = ps.executeQuery();    //查询返回ResultSet结果集对象
            /* 获取元数据作为字段*/
            ResultSetMetaData metaData = set.getMetaData();
            while(set.next()){
                Map<String,Object> map = new HashMap<String,Object>();  //每行记录封装为Map对象
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    java.lang.String columnName = metaData.getColumnName(i+1);
                    java.lang.String columnValue = set.getObject(i + 1)+"";
                    map.put(columnName,columnValue);
                }
                list.add(map);//将Map对象添加到List集合中
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,set);
        }
        return list;//返回封装Map对象的List接口对象
    }


    /**
     * 查询返回listMap数据
     * @param conn
     * @param sql
     * @return
     */
    public  static void validateSql(Connection conn,String sql,Map columnValues) throws Exception {
        PreparedStatement ps = null;    //获取PreparedStatement接口对象

        try {
            sql=prepareSql(sql,columnValues);

            ps = conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            DBUtil.close(conn,ps,null);
        }

    }

    /**
     * *分页获取数据
     * @param conn
     * @param sql
     * @return
     */
    public static   PageFinderDTO<Map<String,Object>> selectPageListMaps(Connection conn, String sql, Map keyValueMap, QueryPageDTO queryPageDTO){
        //获取到需要执行的sql语句
       Map getTotalMap = new HashMap<>();
       if(keyValueMap != null){
           getTotalMap.putAll(keyValueMap);
           getTotalMap.remove("pageNum");
           getTotalMap.remove("pageSize");

       }

        String totalSql=prepareSql(sql,getTotalMap);
        sql=prepareSql(sql,keyValueMap);
        PageFinderDTO<Map<String,Object>> mapPageFinderDTO = null;
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        PreparedStatement ps = null;    //获取PreparedStatement接口对象
        ResultSet set = null;//记录列表
        try {
            //获取总数
            long totalCount = getTotalCount(conn, totalSql);
            mapPageFinderDTO =new PageFinderDTO<Map<String,Object>>(queryPageDTO.getPageNum(),queryPageDTO.getPageSize(),totalCount);
            ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //设置读取的数量
            ps.setMaxRows(queryPageDTO.getPageNum()*queryPageDTO.getPageSize());
            set = ps.executeQuery();    //查询返回ResultSet结果集对象

            /**
             * 以下内容是为了在查看页面数据的时候，可以进行分页
             */
            // 查询分页数据
            /*int beginIndex = (queryPageDTO.getPageNum() - 1) * queryPageDTO.getPageSize();

            if(beginIndex > 0){
                // 设置读取数据的起始位置
                set.absolute(beginIndex);
            }*/
            /* 获取元数据作为字段*/
            ResultSetMetaData metaData = set.getMetaData();
            while(set.next()){
                Map<String,Object> map = new HashMap<String,Object>();  //每行记录封装为Map对象
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    java.lang.String columnName = metaData.getColumnName(i+1);
                    java.lang.String columnValue = set.getObject(i + 1)+"";
                    map.put(columnName,columnValue);
                }
                list.add(map);//将Map对象添加到List集合中
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,set);
        }
        mapPageFinderDTO.setData(list);
        return mapPageFinderDTO;//返回封装Map对象
    }

    /**
     * *分页获取数据
     * @param conn
     * @param sql
     * @return
     */
    public static   PageFinderDTO<Map<String,Object>> selectPageListMapsForTable(Connection conn, String sql, Map keyValueMap, QueryPageDTO queryPageDTO){
        //获取到需要执行的sql语句
        Map getTotalMap = new HashMap<>();
        if(keyValueMap != null){
            getTotalMap.putAll(keyValueMap);
            getTotalMap.remove("pageNum");
            getTotalMap.remove("pageSize");

        }


        String totalSql=prepareSql(sql,getTotalMap);
        sql=prepareSql(sql,keyValueMap);
        PageFinderDTO<Map<String,Object>> mapPageFinderDTO = null;
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        PreparedStatement ps = null;    //获取PreparedStatement接口对象
        ResultSet set = null;//记录列表
        try {
            //获取总数
            long totalCount = getTotalCount(conn, totalSql);
            mapPageFinderDTO =new PageFinderDTO<Map<String,Object>>(queryPageDTO.getPageNum(),queryPageDTO.getPageSize(),totalCount);
            ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //设置读取的数量
            ps.setMaxRows(queryPageDTO.getPageNum()*queryPageDTO.getPageSize());
            set = ps.executeQuery();    //查询返回ResultSet结果集对象

            /**
             * 以下内容是为了在查看页面数据的时候，可以进行分页
             */
            // 查询分页数据
            int beginIndex = (queryPageDTO.getPageNum() - 1) * queryPageDTO.getPageSize();

            if(beginIndex > 0){
                // 设置读取数据的起始位置
                set.absolute(beginIndex);
            }
            /* 获取元数据作为字段*/
            ResultSetMetaData metaData = set.getMetaData();
            while(set.next()){
                Map<String,Object> map = new HashMap<String,Object>();  //每行记录封装为Map对象
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    java.lang.String columnName = metaData.getColumnName(i+1);
                    java.lang.String columnValue = set.getObject(i + 1)+"";
                    map.put(columnName,columnValue);
                }
                list.add(map);//将Map对象添加到List集合中
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,set);
        }
        mapPageFinderDTO.setData(list);
        return mapPageFinderDTO;//返回封装Map对象
    }


    /**
     * * 查询总数
     * @param con
     * @param searchSql
     * @return
     * @throws Exception
     */
    public static Long getTotalCount(Connection con, String searchSql) throws Exception {
        long totalCount = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            StringBuffer countSql = new StringBuffer("select count(1) as  totalCount from ( ").append(searchSql).append(" ) a");
            pstmt = con.prepareStatement(countSql.toString());
            rs = pstmt.executeQuery();
            while(rs.next()){
                totalCount = Long.valueOf(rs.getObject("totalCount").toString());
            }
        }catch (SQLException se) {
            logger.error("数据库连接异常", se);
            throw new Exception(DynamicDataSourceFactory.CONNECTION_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据库连接异常", e);
            throw new Exception(DynamicDataSourceFactory.CONNECTION_ERROR);
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return totalCount;
    }


    /**
     * * 执行插入操作
     * @param conn
     * @param sql
     * @return
     * @throws Exception
     */
    public static int insert(Connection conn,String sql,Map columnValues) throws Exception {
        try {
            sql=prepareSql(sql,columnValues);
            int i = executeUpdate(conn, sql);
            return i;
        }catch (Exception e){
            throw new  Exception(e);
        }finally {
            DBUtil.close(conn,stat,null);
        }
    }

    /**
     * * 执行批量插入操作
     * @param conn
     * @param sqlList
     * @return
     */
    public static int insertBatch(Connection conn,List<String> sqlList,Map columnValues) throws Exception {
        int result = 0;
        try {
            stat = conn.createStatement();
            //相当于开启事务
            conn.setAutoCommit(false);
            for (String s : sqlList) {
                s=prepareSql(s,columnValues);
                stat.execute(s);
                result++;
            }
            //事务提交
            conn.commit();
        } catch (SQLException throwables) {
            //如果出错就回滚
            conn.rollback();
            throwables.printStackTrace();
            throw new Exception(throwables.getMessage());
        }finally {
            DBUtil.close(conn,stat,null);
        }
        return result;
    }

    /**
     * 执行删除操作*
     * @param conn
     * @param sql
     * @return
     * @throws Exception
     */
    public static int delete(Connection conn,String sql,Map columnValues) throws Exception {
        try {
            stat = conn.createStatement();
            sql=prepareSql(sql,columnValues);
            int i = stat.executeUpdate(sql);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            DBUtil.close(conn,stat,null);
        }

    }

    /**
     * 执行批量删除*
     * @param conn
     * @param sqlList
     * @return
     * @throws Exception
     */
    public static int deleteBatch(Connection conn,List<String> sqlList,Map columnValues) throws Exception {
        int result = 0 ;
        try {
            stat = conn.createStatement();
            //相当于开启事务
            conn.setAutoCommit(false);
            for (String s : sqlList) {
                s=prepareSql(s,columnValues);
                stat.executeUpdate(s);
                result++;
            }
            //事务提交
            conn.commit();
        } catch (SQLException throwables) {
            //如果出错就回滚
            conn.rollback();
            throwables.printStackTrace();
            throw new Exception(throwables.getMessage());
        }finally {
            DBUtil.close(conn,stat,null);
        }
        return result;
    }

    /**
     * 执行修改操作*
     * @param conn
     * @param sql
     * @return
     * @throws Exception
     */
    public  static  int update(Connection conn,String sql,Map columnValues) throws Exception {
        try {
            stat = conn.createStatement();
            sql=prepareSql(sql,columnValues);
            int i = stat.executeUpdate(sql);
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }finally {
            DBUtil.close(conn,stat,null);
        }

    }

    /**
     * 执行批量删除*
     * @param conn
     * @param sqlList
     * @return
     * @throws Exception
     */
    public  static int updateBatch(Connection conn,List<String> sqlList,Map columnValues) throws Exception {
        int result =0;
        try {
            stat = conn.createStatement();
            //相当于开启事务
            conn.setAutoCommit(false);
            for (String s : sqlList) {
                s=prepareSql(s,columnValues);
                stat.executeUpdate(s);
                result++;
            }
            //事务提交
            conn.commit();
        } catch (SQLException throwables) {
            //如果出错就回滚
            conn.rollback();
            throwables.printStackTrace();
            throw new Exception(throwables.getMessage());
        }finally {
            DBUtil.close(conn,stat,null);
        }
        return result;
    }

    /**
     * 跨库执行事务控制
     * @param connMap
     * @param transactionDtos
     * @return
     * @throws Exception
     */
    public static int transaction(Map<String ,Connection> connMap , List<TransactionDto> transactionDtos) throws Exception {
        int result =0;
        Map<String , Statement> statMap =  new HashMap<>();
        try {
            //1.遍历map数据源创建会话statment
            // while (connMap.entrySet().iterator().hasNext()) {
            for(Map.Entry<String, Connection> entry : connMap.entrySet()){
                String key = entry.getKey();
                Connection connection = entry.getValue();
                connection.setAutoCommit(false);
                //创建句柄
                Statement  stat = connection.createStatement();
                statMap.put(key,stat);
            }
            //3.执行sql语句
            for (TransactionDto transactionDto : transactionDtos) {
                String sql =  transactionDto.getDefitionSql();
                sql =prepareSql(sql,transactionDto.getParams());
                statMap.get(transactionDto.getDriverAndInstanceId()).executeUpdate(sql);
                result++;
            }
            //4.提交事务
            //  Iterator<Map.Entry<String, Connection>> iterator1 = connMap.entrySet().iterator();
            for(Map.Entry<String, Connection> entry : connMap.entrySet()){
                entry.getValue().commit();
            }

        } catch (Exception throwables) {
            //如果出错就回滚
            // Iterator<Map.Entry<String, Connection>> iterator22 = connMap.entrySet().iterator();
            for(Map.Entry<String, Connection> entry : connMap.entrySet()){
                entry.getValue().rollback();
            }
            throwables.printStackTrace();
            throw new Exception(throwables.getMessage());
        }finally {
            //Iterator<Map.Entry<String, Connection>> iterator3 = connMap.entrySet().iterator();
            for(Map.Entry<String, Connection> entry : connMap.entrySet()){
                entry.getValue().setAutoCommit(true);
                String key = entry.getKey();
                DBUtil.close(entry.getValue(),statMap.get(key),null);
            }
        }
        return result;
    }


    //联邦查询
    public static List unionSelect(Map<String ,Connection> connMap , List<TransactionDto> transactionDtos) throws Exception {
        int result =0;
        Map<String , Statement> statMap =  new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        try {
            //1.遍历map数据源创建会话statment
            // while (connMap.entrySet().iterator().hasNext()) {
            for(Map.Entry<String, Connection> entry : connMap.entrySet()){
                String key = entry.getKey();
                Connection connection = entry.getValue();
                //创建句柄
                Statement  stat = connection.createStatement();
                statMap.put(key,stat);
            }
            //3.执行sql语句
            for (TransactionDto transactionDto : transactionDtos) {
                String sql =  transactionDto.getDefitionSql();
                sql =prepareSql(sql,transactionDto.getParams());
                ResultSet set= statMap.get(transactionDto.getDriverAndInstanceId()).executeQuery(sql);
                try {
                    /* 获取元数据作为字段*/
                    ResultSetMetaData metaData = set.getMetaData();
                    while(set.next()){
                        Map<String,Object> map = new HashMap<String,Object>();  //每行记录封装为Map对象
                        for (int i = 0; i < metaData.getColumnCount(); i++) {
                            java.lang.String columnName = metaData.getColumnName(i+1);
                            java.lang.String columnValue = set.getObject(i + 1)+"";
                            map.put(columnName,columnValue);
                        }
                        list.add(map);//将Map对象添加到List集合中
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    set.close();
                }
            }


        } catch (Exception throwables) {
            throwables.printStackTrace();
            throw new Exception(throwables.getMessage());
        }finally {
            //Iterator<Map.Entry<String, Connection>> iterator3 = connMap.entrySet().iterator();
            for(Map.Entry<String, Connection> entry : connMap.entrySet()){
                String key = entry.getKey();
                DBUtil.close(entry.getValue(),statMap.get(key),null);
            }
        }
        return list;
    }


    /**
     * 数据抽取/采集
     * @param etlWorkCollect
     * @param dataSourceRead
     * @param dataSourceWriter
     * @param readSql
     * @param
     * @param type
     * @return
     * @throws Exception
     */
    public static EtlResultVo dataflow(EtlWorkCollect etlWorkCollect, DataSource dataSourceRead, DataSource dataSourceWriter, String readSql, String type, List<DataStructuredColumn> dataStructuredColumns) throws Exception {
        EtlResultVo etlResultVo = new EtlResultVo();
        Connection readConn =null;
        Connection writerConn = null;
        PreparedStatement ps =null;
        ResultSet readset =null;
        Statement writerStatement=null;
        int result = 0;

        StringBuffer buffer = new StringBuffer();
        try {
            readConn=dataSourceRead.getConnection();
            writerConn = dataSourceWriter.getConnection();
            readSql=prepareSql(readSql,null);

            //4.判断是否增量
            if("1".equals(etlWorkCollect.getCollectAdd())){
                //增量
                String totalNum=null;
                //1.获取最大值
                String maxFiledSql = "SELECT MAX("+ etlWorkCollect.getFieldName() +") as totalNum FROM ( "+readSql+" ) tbAlias " ;

                Statement statement = readConn.createStatement();
                ResultSet resultSet = statement.executeQuery(maxFiledSql);
                while(resultSet.next()){
                    totalNum = resultSet.getString("totalNum");
                }
                DBUtil.close(null,statement,resultSet);
                //2.获取最小值
                String minFiled = "0";
                if(StringUtils.isNotNull(etlWorkCollect.getFieldValue())  && StringUtils.isNotEmpty(etlWorkCollect.getFieldValue())){
                    minFiled=etlWorkCollect.getFieldValue();
                }
                //3.封装sql语句
                buffer.append("select * from ( " ).append(readSql).append(" ) tbAlias");
                buffer.append(" where ( ").append(etlWorkCollect.getFieldName());
                if(etlWorkCollect.getFieldType().contains("var") || etlWorkCollect.getFieldType().contains("date") ){
                    //字符串
                    buffer.append(" > '").append(minFiled).append("' ");
                    buffer.append(" and ").append(etlWorkCollect.getFieldName()).append(" <= '").append(totalNum).append("'");
                }else{
                    buffer.append(" > ").append(minFiled).append(" ");
                    buffer.append(" and ").append(etlWorkCollect.getFieldName()).append(" <= ").append(totalNum).append(" ");
                }
                buffer.append(" ) ").append(" or ").append(etlWorkCollect.getFieldName()).append(" is null");
                readSql =buffer.toString();

                etlResultVo.setMaxValue(totalNum);
            }
            //读取数据
            ps = readConn.prepareStatement(readSql);
            readset = ps.executeQuery();//查询返回ResultSet结果集对象
            //写入数据
            writerConn.setAutoCommit(false);
            writerStatement = writerConn.createStatement();
            /* 获取元数据作为字段*/
            ResultSetMetaData metaData = readset.getMetaData();
            int record = 0 ;//记录数量
            boolean flag=false; //是否有获取到数据

            //获取写入sql
            String insertSql  = getWriterSql(metaData,etlWorkCollect.getCollectToTableName(),dataStructuredColumns);
            //获取更新sql
            String updateSql = getUpdateSql(metaData,etlWorkCollect.getCollectToTableName(),type,dataStructuredColumns);
            //获取判断是否存在SQL
            String isExistsSql = getCountIsExistsSql(etlWorkCollect.getCollectToTableName(),dataStructuredColumns);
            while(readset.next()){
                flag=true;
                result++;
                Map<String,Object> map = new HashMap<String,Object>();  //每行记录封装为Map对象
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    java.lang.String columnName = metaData.getColumnLabel(i+1);
                    //获取到主键字段
                    if(dataStructuredColumns!=null){
                        for (DataStructuredColumn dataStructuredColumn : dataStructuredColumns) {
                            if(columnName.toUpperCase().equals(dataStructuredColumn.getColumnCode().toUpperCase())){
                                columnName=columnName.toUpperCase();break;
                            }

                        }
                    }
                    java.lang.String columnValue = readset.getObject(i + 1)+"";
                    if(StringUtils.isNull(columnValue) || StringUtils.isEmpty(columnValue)){
                        map.put(columnName,"@null");
                    }else{
                        map.put(columnName,columnValue);
                    }
                }
                //判断数据是否有主键
                isExistsSql = prepareSql(isExistsSql, map);
                boolean b = validateDataISExists(writerStatement, isExistsSql);
                String writerSqls =null;
                if(b){
                    //已存在数据
                    writerSqls = prepareSql(updateSql, map);
                }else{
                    //不存在数据
                    writerSqls = prepareSql(insertSql, map);
                }
                writerSqls=writerSqls.replace("@null","").replace("null","");
                writerStatement.addBatch(writerSqls);
                record++;
                if(record==10){
                    //1W条数据时，提交一次
                    writerStatement.executeBatch(); // 执行批处理操作
                    writerConn.commit(); // 提交事务
                    record=0;
                    writerStatement.clearBatch();
                }
            }
            if(!flag){
                return  null;
            }
            //提交剩余未提交的数据
            if(record>0 && flag){
                writerStatement.executeBatch();
                writerConn.commit(); // 提交事务
                writerConn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            DBUtil.close(readConn,ps,readset);
            DBUtil.close(writerConn,writerStatement,null);
        }
        etlResultVo.setAffectROW(result);
        return  etlResultVo;
    }



    public static EtlResultVo dataCollect(EtlWorkCollect etlWorkCollect,DataSource dataSourceRead,DataSource dataSourceWriter,String readSql,String writerSql,String type) throws Exception {
        EtlResultVo etlResultVo = new EtlResultVo();
        Connection readConn =null;
        Connection writerConn = null;
        PreparedStatement ps =null;
        ResultSet readset =null;
        Statement writerStatement=null;
        int result = 0;

        StringBuffer buffer = new StringBuffer();
        try {
            readConn=dataSourceRead.getConnection();
            writerConn = dataSourceWriter.getConnection();
            readSql=prepareSql(readSql,null);
            String totalNum=null;

            etlResultVo.setMaxValue(totalNum);
            //读取数据
            ps = readConn.prepareStatement(readSql);
            readset = ps.executeQuery();//查询返回ResultSet结果集对象
            //写入数据
            writerConn.setAutoCommit(false);
            writerStatement = writerConn.createStatement();
            /* 获取元数据作为字段*/
            ResultSetMetaData metaData = readset.getMetaData();
            //如果是采集数据，则由这个方法生成写入sql

            String createTalbeSql = getCreateTalbeSql(metaData, etlWorkCollect.getCollectToTableName(), type);
            Statement statement =null;
            try {
                writerSql= getWriterSql(metaData,etlWorkCollect.getCollectToTableName(),null);
                statement = writerConn.createStatement();
                statement.executeUpdate(createTalbeSql);
            }catch (Exception e){
                e.printStackTrace();
                throw new Exception(createTalbeSql+">>"+e.getMessage());
            }finally {
                DBUtil.close(null,statement,null);
            }

            int record = 0 ;//记录数量
            boolean flag=false; //是否有获取到数据
            while(readset.next()){
                flag=true;
                result++;
                Map<String,Object> map = new HashMap<String,Object>();  //每行记录封装为Map对象
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    java.lang.String columnName = metaData.getColumnName(i+1);
                    java.lang.String columnValue = readset.getObject(i + 1)+"";
                    if(StringUtils.isNull(columnValue) || StringUtils.isEmpty(columnValue)){
                        map.put(columnName,"@null");
                    }else{
                        map.put(columnName,columnValue);
                    }
                }

                String writerSqls = prepareSql(writerSql, map);
                // System.out.println(writerSqls);
                writerSqls=writerSqls.replace("@null","");
                // System.out.println(writerSqls);
                writerStatement.addBatch(writerSqls);
                record++;
                if(record==10){
                    //1W条数据时，提交一次
                    writerStatement.executeBatch(); // 执行批处理操作
                    writerConn.commit(); // 提交事务
                    record=0;
                    writerStatement.clearBatch();
                }
            }
            if(!flag){
                return  null;
            }

            //提交剩余未提交的数据
            if(record>0 && flag){
                writerStatement.executeBatch();
                writerConn.commit(); // 提交事务
                writerConn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            DBUtil.close(readConn,ps,readset);
            DBUtil.close(writerConn,writerStatement,null);
        }
        etlResultVo.setAffectROW(result);
        return  etlResultVo;
    }

    public   static String getCreateTalbeSql(ResultSetMetaData metaData,String tableName,String type) throws SQLException {
        StringBuffer buffer =new StringBuffer();
        buffer.append("create table ").append(tableName).append("(");
        for (int i=1; i<=metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnLabel(i);
            int dataType = metaData.getColumnType(i);
            int length = metaData.getPrecision(i);
            String fileInfo = getFileInfo(type, columnName, dataType, length);
            buffer.append(fileInfo).append(",");
        }
        String sql = buffer.toString().substring(0,buffer.toString().length()-1);
        sql=sql+")";
        return sql;
    }

    public static  String getWriterSql(ResultSetMetaData metaData,String tableName,List<DataStructuredColumn> dataStructuredColumns) throws SQLException {
        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT INTO ").append(tableName).append("(");
        for (int i=1; i<=metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnLabel(i);
            //获取到主键字段
            if(dataStructuredColumns!=null){
                for (DataStructuredColumn dataStructuredColumn : dataStructuredColumns) {
                    if(columnName.toUpperCase().equals(dataStructuredColumn.getColumnCode().toUpperCase())){
                        columnName=columnName.toUpperCase();
                        break;
                    }
                }

            }
            buffer.append(columnName).append(",");
        }
        String sql = buffer.toString().substring(0,buffer.toString().length()-1)+") values";
        StringBuffer buffer1 = new StringBuffer();
        buffer1.append( "(");
        for (int i=1; i<=metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnLabel(i);
            //获取到主键字段
            if(dataStructuredColumns!=null){
                for (DataStructuredColumn dataStructuredColumn : dataStructuredColumns) {
                    if(columnName.toUpperCase().equals(dataStructuredColumn.getColumnCode().toUpperCase())){
                        columnName=columnName.toUpperCase();
                        break;
                    }
                }

            }
            buffer1.append("{!#").append(columnName).append("#}").append(",");
        }
        String sql1 = buffer1.toString().substring(0,buffer1.toString().length()-1)+")";

        return sql+sql1;

    }


    public static boolean validateDataISExists(Statement statement,String countIsExistsSql) throws Exception {
        ResultSet resultSet = statement.executeQuery(countIsExistsSql);
        try {
            if (resultSet.next()) {
                String is_exists = resultSet.getString("IS_EXISTS");
                if(is_exists.equals("0")){
                    return false;
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw  new Exception(e.getMessage());
        }finally {
            DBUtil.close(null,null,resultSet);
        }

    }

    public static  String getUpdateSql(ResultSetMetaData metaData,String tableName,String type,List<DataStructuredColumn> dataStructuredColumns) throws SQLException {
        StringBuffer buffer = new StringBuffer();

        Map pkColums = new HashMap<String,String >();
        if(dataStructuredColumns==null || dataStructuredColumns.size()==0){
            return   getWriterSql(metaData,tableName, dataStructuredColumns);
        }
        //获取到主键字段
        for (DataStructuredColumn dataStructuredColumn : dataStructuredColumns) {
            pkColums.put(dataStructuredColumn.getColumnCode().toUpperCase(),dataStructuredColumn.getColumnCode().toUpperCase());
        }
        buffer.append("UPDATE  ").append(tableName).append(" SET ");
        for (int i=1; i<=metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnLabel(i);
            if(!pkColums.containsKey(columnName.toUpperCase())){
                //该字段不为主键
                if(Constants.MYSQL.equals(type)){
                    buffer.append("`").append(columnName).append("`");
                }else{
                    buffer.append(columnName);
                }
                buffer.append(" = ").append("{!#").append(columnName).append("#} ,");
            }

        }
        //获取到set字段信息
        String sql = buffer.toString().substring(0,buffer.toString().length()-1)+" ";
        return sql+getWherePkFiled(pkColums);
    }

    public static String getCountIsExistsSql(String tableName,List<DataStructuredColumn> dataStructuredColumns){
        StringBuffer buffer = new StringBuffer();

        Map pkColums = new HashMap<String,String >();
        //获取到主键字段
        for (DataStructuredColumn dataStructuredColumn : dataStructuredColumns) {
            pkColums.put(dataStructuredColumn.getColumnCode().toUpperCase(),dataStructuredColumn.getColumnCode().toUpperCase());
        }
        buffer.append("select count(1) as IS_EXISTS").append(" FROM ").append(tableName).append(" ");
        buffer.append(getWherePkFiled(pkColums));
        return  buffer.toString();
    }



    static String getWherePkFiled(Map params){
        //获取到过滤条件主键字段
        StringBuffer buffer1 = new StringBuffer();
        buffer1.append( " where ");
        params.forEach((key,value) ->{
            buffer1.append(" ").append( key).append(" = ").append("{!#").append(value).append("#} ");
            buffer1.append(" and");
        });

        String sql1 = buffer1.toString().substring(0,buffer1.toString().length()-3)+"";
        return  sql1;

    }

    static String  getFileInfo(String type,String columnName,int dataType,int length){
        if(Constants.MYSQL.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.VARCHAR: return "`"+columnName+"` Varchar("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` DATETIME";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }else if(Constants.POSTGRE.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.VARCHAR: return "`"+columnName+"` Varchar("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` timestamp";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }else if(Constants.CLICK_HOUSE.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.VARCHAR: return "`"+columnName+"` Varchar("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` timestamp";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }else if(Constants.ORACLE.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.NUMERIC: return "`"+columnName+"` NUMBER";
                case Types.VARCHAR: return "`"+columnName+"` Varchar2("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` timestamp";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }else if(Constants.SQLSERVER.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.VARCHAR: return "`"+columnName+"` Varchar("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` datetime";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }else if(Constants.SQLITE.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.VARCHAR: return "`"+columnName+"` Varchar("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` timestamp";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }else if(Constants.DB2.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.VARCHAR: return "`"+columnName+"` Varchar("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` timestamp";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }else if(Constants.HIVE.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.VARCHAR: return "`"+columnName+"` Varchar("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` timestamp";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }else if(Constants.SEABOX.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.VARCHAR: return "`"+columnName+"` Varchar("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` timestamp";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }else if(Constants.GREENPLUM.equals(type)){
            switch (dataType) {
                case Types.INTEGER: return "`"+columnName+"` Integer";
                case Types.FLOAT: return "`"+columnName+"` float";
                case Types.DOUBLE: return "`"+columnName+"` double";
                case Types.VARCHAR: return "`"+columnName+"` Varchar("+length+")";
                case Types.DATE: return   "`"+columnName+"` Date ";
                case Types.TIMESTAMP: return "`"+columnName+"` timestamp";
                default:  return "`"+columnName+"` Varchar("+512+")";
            }
        }

        return null;

    }


    /**
     * 预处理需要执行的sql
     * * @param sourceSql
     * @param keyValues
     * @return
     */
    public static String prepareSql(String sourceSql,Map keyValues){
        //替换环境变量 @ENV(loginId)
        try {
            String loginId ="'"+ SecurityUtils.getUsername()+"'";
            sourceSql=sourceSql.replace("@Env(loginId)",loginId);
        }catch (Exception e){
        }

        // String dateTimeNow = DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss");
        /**
         * @{YYYY} 当前年份  2024
         * @{MM} 当前月份  04、10
         * @{DD} 当前日期天    20
         * @{YYYY-MM-DD} 当前年月日，例如：2024-01-21
         * @{YYYY-MM-DD HH:MM:SS}    @{yyyy-MM-dd HH:mm:ss} 当前年月日时分秒：2024-01-21 18:12:12
         */
        String type ="yyyy-MM-dd HH:mm:ss";
        String dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        type ="yyyy-MM-dd";
        dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        type ="yyyy";
        dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        type ="MM";
        dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        type ="dd";
        dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        if(keyValues==null){return  sourceSql;}

        //1.获取到需要动态替换的占位符
        //1.1.处理必填的
        //匹配必填字符串
        String mustRegex = "\\{!(.*?)\\}";
        String kvRegex = "\\#(.*?)#";
        Pattern mustPattern = Pattern.compile(mustRegex);
        Matcher matcher = mustPattern.matcher(sourceSql);
        Pattern kvPattern = Pattern.compile(kvRegex);


        String notMustRegex = "\\{\\?(.*?)\\}";
        Pattern notMustPattern = Pattern.compile(notMustRegex);
        Matcher notMatcher = notMustPattern.matcher(sourceSql);

        //必填
        while (matcher.find()) {
            String old =matcher.group(0);//原占位符字符串
            String kv = matcher.group(1);//参数值
            String value;
            String s;
            Matcher matcher1 = kvPattern.matcher(matcher.group(1));
            while(matcher1.find()){
                value = "";
                s ="";
                if(keyValues.containsKey(matcher1.group(1))){
                    //存在该占位符，需要进行替换
                    //判断该字符串是否是数字
                    s = keyValues.get(matcher1.group(1)).toString();
                    boolean isNumber = s.matches("\\d+");
                    if(isNumber){
                        //是数字
                        value=s;
                    }else{
                        //字符串
                        //判断是否头尾有单引号
                        if(s.startsWith("'") && s.endsWith("'")){
                            //已有字符串，不需要补充单引号
                            value=s;
                        }else{
                            //头尾需要补单引号
                            value="'"+s+"'";
                        }
                    }
                    kv=kv.replace(matcher1.group(0),value);
                }else{
                  //  kv="";
                }

            }
            sourceSql = sourceSql.replace(old,kv);
        }


        //非必填
        while (notMatcher.find()) {
            String old =notMatcher.group(0);//原占位符字符串
            String kv = notMatcher.group(1);//参数值
            String value ;
            String s ;
            Matcher matcher1 = kvPattern.matcher(notMatcher.group(1));
            while(matcher1.find()){
                value = "";
                s ="";
                if(keyValues.containsKey(matcher1.group(1))){
                    //存在该占位符，需要进行替换
                    //判断该字符串是否是数字
                    s = keyValues.get(matcher1.group(1)).toString();
                    boolean isNumber = s.matches("\\d+");
                    if(isNumber){
                        //是数字
                        value=s;
                        kv=kv.replace(matcher1.group(0),value);
                    }else{
                        //字符串
                        //判断是否头尾有单引号
                        if( s.startsWith("'") && s.endsWith("'")){
                            //已有字符串，不需要补充单引号
                            value=s;
                            kv=kv.replace(matcher1.group(0),value);
                        }else if(StringUtils.isEmpty(s)){
                            kv="";
                        }else{
                            //头尾需要补单引号
                            value="'"+s+"'";
                            kv=kv.replace(matcher1.group(0),value);
                        }
                    }

                }else{
                    kv="";

                }
            }
            sourceSql = sourceSql.replace(old,kv);
        }

        return sourceSql;


    }



    public static String validateApiSql(String sourceSql){
        Map keyValues = new HashMap();
        sourceSql=sourceSql.replace("`","");
        //替换环境变量 @ENV(loginId)
        try {
            String loginId ="'"+ SecurityUtils.getUsername()+"'";
            sourceSql=sourceSql.replace("@Env(loginId)",loginId);
        }catch (Exception e){
        }

        // String dateTimeNow = DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss");
        /**
         * @{YYYY} 当前年份  2024
         * @{MM} 当前月份  04、10
         * @{DD} 当前日期天    20
         * @{YYYY-MM-DD} 当前年月日，例如：2024-01-21
         * @{YYYY-MM-DD HH:MM:SS}    @{yyyy-MM-dd HH:mm:ss} 当前年月日时分秒：2024-01-21 18:12:12
         */
        String type ="yyyy-MM-dd HH:mm:ss";
        String dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        type ="yyyy-MM-dd";
        dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        type ="yyyy";
        dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        type ="MM";
        dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        type ="dd";
        dateTimeNow = DateUtils.dateTimeNow(type);
        sourceSql=sourceSql.replace("@{"+type+"}",dateTimeNow);

        // if(keyValues==null){return  sourceSql;}

        //1.获取到需要动态替换的占位符
        //1.1.处理必填的
        //匹配必填字符串
        String mustRegex = "\\{!(.*?)\\}";
        String kvRegex = "\\#(.*?)#";
        Pattern mustPattern = Pattern.compile(mustRegex);
        Matcher matcher = mustPattern.matcher(sourceSql);
        Pattern kvPattern = Pattern.compile(kvRegex);


        String notMustRegex = "\\{\\?(.*?)\\}";
        Pattern notMustPattern = Pattern.compile(notMustRegex);
        Matcher notMatcher = notMustPattern.matcher(sourceSql);

        //必填
        while (matcher.find()) {
            String old =matcher.group(0);//原占位符字符串
            String kv = matcher.group(1);//参数值
            String value;
            String s;
            Matcher matcher1 = kvPattern.matcher(matcher.group(1));
            while(matcher1.find()){
                value = "";
                s ="";
                keyValues.put(matcher1.group(1),"a");
                if(keyValues.containsKey(matcher1.group(1))){
                    //存在该占位符，需要进行替换
                    //判断该字符串是否是数字
                    s = keyValues.get(matcher1.group(1)).toString();
                    boolean isNumber = s.matches("\\d+");
                    if(isNumber){
                        //是数字
                        value=s;
                    }else{
                        //字符串
                        //判断是否头尾有单引号
                        if(s.startsWith("'") && s.endsWith("'")){
                            //已有字符串，不需要补充单引号
                            value=s;
                        }else{
                            //头尾需要补单引号
                            value="'"+s+"'";
                        }
                    }
                    kv=kv.replace(matcher1.group(0),value);
                }else{
                    kv="";
                }

            }
            sourceSql = sourceSql.replace(old,kv);
        }


        //非必填
        while (notMatcher.find()) {
            String old =notMatcher.group(0);//原占位符字符串
            String kv = notMatcher.group(1);//参数值
            String value ;
            String s ;
            Matcher matcher1 = kvPattern.matcher(notMatcher.group(1));
            while(matcher1.find()){
                value = "";
                s ="";
                keyValues.put(matcher1.group(1),"a");
                if(keyValues.containsKey(matcher1.group(1))){
                    //存在该占位符，需要进行替换
                    //判断该字符串是否是数字
                    s = keyValues.get(matcher1.group(1)).toString();
                    boolean isNumber = s.matches("\\d+");
                    if(isNumber){
                        //是数字
                        value=s;
                        kv=kv.replace(matcher1.group(0),value);
                    }else{
                        //字符串
                        //判断是否头尾有单引号
                        if( s.startsWith("'") && s.endsWith("'")){
                            //已有字符串，不需要补充单引号
                            value=s;
                            kv=kv.replace(matcher1.group(0),value);
                        }else if(StringUtils.isEmpty(s)){
                            kv="";
                        }else{
                            //头尾需要补单引号
                            value="'"+s+"'";
                            kv=kv.replace(matcher1.group(0),value);
                        }
                    }

                }else{
                    kv="";

                }
            }
            sourceSql = sourceSql.replace(old,kv);
        }

        return sourceSql;


    }

    public static void main1(String[] args) {
      /*  String text = "select * from " +
                "sys_user u where {! u.age=#age#} " +
                "{? and u.name=#name#}" +
                "{? and u.createtime =date_formate(#data#,'yyyy-mm-dd')} limit 1";*/
        String text ="INSERT INTO TEMP_TEST1(ID,status48624,CREATED_BY,UPDATED_BY,APPROE_STATUS,radio59841) VALUES({! #ID#},{! #status48624#},{! #CREATED_BY#},{! #UPDATED_BY#},{! #APPROE_STATUS#},{! #radio59841#})";

        Map<String, Object> param = new HashMap<>();
       /* param.put("name","sxf");
        param.put("age",12);
        param.put("xiebineg",12);*/
        //param.put("data","1992-10-01");


        param.put("ID","1");
        param.put("status48624","admin");
        param.put("CREATED_BY","input898");
        param.put("UPDATED_BY","input898");
        param.put("APPROE_STATUS","input898");
        param.put("radio59841","input898");



        String s = prepareSql(text, param);
        System.out.println(s);
        /*//匹配必填字符串
        String mustRegex = "\\{!(.*?)\\}";

        //匹配非必填字符串
        String notMustRegex = "\\{\\?(.*?)\\}";

        Pattern mustPattern = Pattern.compile(mustRegex);
        Pattern notMustPattern = Pattern.compile(notMustRegex);

        Matcher matcher = mustPattern.matcher(text);
        while (matcher.find()) {
            String kvRegex = "\\#(.*?)#";
            Pattern kvPattern = Pattern.compile(kvRegex);
            Matcher matcher1 = kvPattern.matcher(matcher.group(1));
            System.out.println(matcher.group(0)+"@"+matcher.group(1)+"@");
            while(matcher1.find()){
                System.out.println(matcher1.group(0)+"@"+matcher1.group(1)+"@");  // 输出匹配的括号内的内容
            }

        }*/

        /* matcher = notMustPattern.matcher(text);
        while (matcher.find()) {
            System.out.println("@"+matcher.group(1)+"@");  // 输出匹配的括号内的内容
        }*/

    }


    public static void main(String[] args) {

        List<TransactionDto> params = new ArrayList<>();

        TransactionDto param = new TransactionDto();
        param.setSort(10);
        params.add(param);

        TransactionDto param1 = new TransactionDto();
        param1.setSort(165);
        params.add(param1);

        TransactionDto param2 = new TransactionDto();
        param2.setSort(1);
        params.add(param2);


        TransactionDto param3 = new TransactionDto();
        param3.setSort(16);
        params.add(param3);

        Collections.sort(params, new Comparator<TransactionDto>() {
            @Override
            public int compare(TransactionDto s1, TransactionDto s2) {
                return s1.getSort() - s2.getSort();
            }
        });


        for (TransactionDto transactionDto : params) {
            System.out.println(transactionDto.getSort());
        }
    }


}
