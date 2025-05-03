package com.yabushan.datasource.utils;

import com.yabushan.datasource.service.DynamicDataSourceFactory;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.SqlParser.Config;
import org.apache.calcite.sql.parser.SqlParserImplFactory;

import java.util.Locale;
import java.util.regex.Pattern;

public class SQLValidator {


    private static final String SELECT_PATTERN = "^\\s*SELECT.*";
    private static final String UPDATE_PATTERN = "^\\s*UPDATE.*";
    private static final String DELETE_PATTERN = "^\\s*DELETE.*";
    private static final String INSERT_PATTERN = "^\\s*INSERT.*";

    private static final String SELECT_PATTERN_ = "^\\s*select.*";
    private static final String UPDATE_PATTERN_ = "^\\s*update.*";
    private static final String DELETE_PATTERN_ = "^\\s*delete.*";
    private static final String INSERT_PATTERN_ = "^\\s*insert.*";
    
    
    public static boolean validateSQL(String sql) {
        sql=sql.replace("`","").toLowerCase(Locale.ROOT);
        if (Pattern.matches(SELECT_PATTERN, sql)) {
            // 校验SELECT语句的合法性
         
        } else if (Pattern.matches(UPDATE_PATTERN, sql)) {
            // 校验UPDATE语句的合法性
          
        } else if (Pattern.matches(DELETE_PATTERN, sql)) {
            // 校验DELETE语句的合法性
           
        } else if (Pattern.matches(INSERT_PATTERN, sql)) {
            // 校验INSERT语句的合法性
           
        } if (Pattern.matches(SELECT_PATTERN_, sql)) {
            // 校验SELECT语句的合法性

        } else if (Pattern.matches(UPDATE_PATTERN_, sql)) {
            // 校验UPDATE语句的合法性

        } else if (Pattern.matches(DELETE_PATTERN_, sql)) {
            // 校验DELETE语句的合法性

        } else if (Pattern.matches(INSERT_PATTERN_, sql)) {
            // 校验INSERT语句的合法性

        }  else {
            // SQL语句格式不正确
            return false;
        }
        try {
            sql=sql.replace("#","'");
            Config config = SqlParser.configBuilder().build();
            SqlParserImplFactory factory = config.parserFactory();
            SqlParser parser = SqlParser.create(sql,config);
            SqlNode node = parser.parseStmt();
            return true;
        } catch (SqlParseException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        // String sql1 = "insert INTO TEST_01(status48624,CREATED_BY,UPDATED_BY,APPROE_STATUS,input49553) values(#status48624#,#CREATED_BY#,#UPDATED_BY#,#APPROE_STATUS#,#input49553#)";
      // String sql2 = "INSERT INTO employee(id,username,name,password,email,age,admin,dept_id,status) VALUES({! #id#},{! #username#},{! #name#},{! #password#},{! #email#},{! #age#},{! #admin#},{! #dept_id#},{! #status#})";
         String sql3 = "delete from TEST_01 where id = #id#  ";
        String s = DynamicDataSourceFactory.validateApiSql(sql3);
        System.out.println(s);
        System.out.println(validateSQL(s));
/*
        String s = DynamicDataSourceFactory.validateApiSql(sql1);
        System.out.println(s); 
        System.out.println(validateSQL(s));
        System.out.println(validateSQL(sql3));
        String s2 = DynamicDataSourceFactory.validateApiSql(sql2);
        System.out.println(s2); 
        System.out.println(validateSQL(s2)); 
    */
    }
}
