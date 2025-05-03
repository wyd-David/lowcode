package com.yabushan.common.exception;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.yabushan.common.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonErrorCode {
	
	private static Logger logger = LogManager.getLogger(CommonErrorCode.class);

	
    public static Map<Integer, String> errorMsgMap = new HashMap<Integer, String>();
    public static Map<Integer, String> logMsgMap = new HashMap<Integer, String>();

    /**
     * 1-100 系统错误区
     */
    public static Integer SYSTEM_ERROR = 1;
    public static String SYSTEM_ERROR_MSG = "系统忙碌!";

    static {
        errorMsgMap.put(SYSTEM_ERROR, SYSTEM_ERROR_MSG);
    }

    public static Integer INVALID_PARAM = 2;
    public static String INVALID_PARAM_MSG = "参数错误!";
    
    public static Integer LACK_OF_PARAM = 3;
    public static String LACK_OF_PARAM_MSG = "缺少必要参数[{p}]!";
    
    public static Integer PERMISSION_DENY = 4;
    public static String PERMISSION_DENY_MSG = "无权操作此功能!";
    
    public static Integer LOGIN_INVALID = 5;
    public static String LOGIN_INVALID_MSG = "用户名或密码不正确!";
    
    /**
     * 101-200 外部接口错误区
     */
    
    static {
    	String end_fix = "_MSG";
    	Field[] fields = CommonErrorCode.class.getFields();
    	Map<String,String> msgMap = new HashMap<String,String>();
    	Map<String,Integer> codeMap = new HashMap<String,Integer>();
        for( Field field : fields ){
        	try{
        		String name = field.getName();
        		if(name.endsWith("Map") || "logger".equals(name)){
        			continue;
        		}
        		if(name.endsWith(end_fix)){
        			msgMap.put(name, field.get(CommonErrorCode.class).toString());
        		}else{
        			codeMap.put(name, Integer.valueOf(field.get(CommonErrorCode.class).toString()));
        		}
        	}catch(Exception e){
        		logger.error(e.getMessage(),e);
        	}
        }
        
        for(Map.Entry<String,Integer> entry : codeMap.entrySet()){
        	Integer code = entry.getValue();
        	String codeName = entry.getKey();
        	String msgName = codeName+end_fix;
        	String msg = msgMap.get(msgName);
        	if(StringUtils.isNotBlank(msg)){
        		errorMsgMap.put(code, msg);
        	}
        }
    }

}
