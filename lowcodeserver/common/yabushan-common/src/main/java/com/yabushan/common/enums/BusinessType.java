package com.yabushan.common.enums;

/**
 * 业务操作类型
 *
 * @author ruoyi
 */
public enum BusinessType
{
    /**
     * 其它
     */
    OTHER,

    /**
     * 新增
     */
    INSERT,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 授权
     */
    GRANT,

    /**
     * 导出
     */
    EXPORT,

    /**
     * 导入
     */
    IMPORT,

    /**
     * 强退
     */
    FORCE,

    /**
     * 生成代码
     */
    GENCODE,

    /**
     * 清空数据
     */
    CLEAN,
    /**
     * 查询数据
     */
    SELECT,
    /**
     * 数据源校验 
     */
    VALIDATE,
    /**
     * 数据源注册 
     */
    REGISTE,

    /**
     * 抓取元数据 
     */
    GETMETA,
}
