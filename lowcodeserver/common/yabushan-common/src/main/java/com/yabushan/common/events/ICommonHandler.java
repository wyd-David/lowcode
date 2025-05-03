package com.yabushan.common.events;

import java.util.Map;

/**
 * description
 *
 * @author yl
 * @date 2022/8/16 16:55
 */
public interface ICommonHandler {

    /**
     * 处理事件
     * @param eventType
     * @param data
     */
    public void handle(String eventType,Object data);

}
