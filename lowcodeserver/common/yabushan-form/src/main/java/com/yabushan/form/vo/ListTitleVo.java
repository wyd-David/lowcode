package com.yabushan.form.vo;

import lombok.Data;

import java.util.List;

@Data
public class ListTitleVo {
    private String key;
    private String label;
    private String prop;
    private String width;
    private String headerAlign;
    private String align;
    /**
     * formfiledtype【表单字段类型】
     *  'input'     输入框
     * 'select'    下拉选择框
     * 'formselect'   弹出表单选择* 
     */
    private String formfiledtype;

    /**
     * * formdatasourcetype【表单数据来源类型】
     * 'table'   数据库表
     * 'apicode'   数据服务API编码
     */
    private String formdatasourcetype;
    private String pk;//是否作为关联子表的主键，1:是，0：否
    /**
     * formdatasourceId【表单数据源ID】
     * 具体的表名称、或者API编码名称* 
     */
    private String formdatasourceId;
    /**
     * display【是否显示】
     * true  显示
     * flase  不显示
     */
    private boolean display;
    private boolean scope;
    private boolean sortable;
    private boolean disabled;//是否可编辑
    private List<ListTitleVo> propChildren;


}
