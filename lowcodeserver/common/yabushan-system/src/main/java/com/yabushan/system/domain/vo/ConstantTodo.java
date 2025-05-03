package com.yabushan.system.domain.vo;

import java.util.HashMap;
import java.util.Map;

public  class ConstantTodo {

    /**
     * 定义待办的url
     */
    public  static final Map<String, String> APPLICATION_TODO_URL = new HashMap<String, String>() {
        {
            /**
             * 1.物资申领
             */
            put("1", "http://www.baidu.com");
            /**
             * 餐厅管理
             */
            put("2", "");
        }
    };


    /**
     * 定义待阅的URL
     */
    public  static final Map<String, String> APPLICATION_TO_READ_URL = new HashMap<String, String>() {
        {
            /**
             * 1.物资申领
             */
            put("1", "http://www.baidu.com");
            /**
             * 餐厅管理
             */
            put("2", "");
        }
    };

    /**
     * 定义已办的url
     */
    public  static final Map<String, String> APPLICATION_HAD_DONE_URL = new HashMap<String, String>() {
        {
            /**
             * 1.物资申领
             */
            put("1", "http://www.baidu.com");
            /**
             * 餐厅管理
             */
            put("2", "");
        }
    };


    /**
     * 定义已阅的url
     */
    public  static final Map<String, String> APPLICATION_HAD_READ_URL = new HashMap<String, String>() {
        {
            /**
             * 1.物资申领
             */
            put("1", "http://www.baidu.com");
            /**
             * 餐厅管理
             */
            put("2", "");
        }
    };

    /**
     * 定义工作流名称
     */
    public  static final Map<String, String> WORKFLOW_NAME = new HashMap<String, String>() {
        {
            /**
             * 1.物资申领
             */
            put("1", "物资申领");
            /**
             * 餐厅管理
             */
            put("2", "");
        }
    };

}
