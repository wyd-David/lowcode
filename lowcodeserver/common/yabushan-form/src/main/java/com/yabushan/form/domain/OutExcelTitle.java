package com.yabushan.form.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "数据模板对象")
@Data
public class OutExcelTitle {
    @Excel(name = "字段1")
    private String string1;

    @Excel(name = "字段2")
    private String string2;

    @Excel(name = "字段3")
    private String string3;

    @Excel(name = "字段4")
    private String string4;

    @Excel(name = "字段5")
    private String string5;

    @Excel(name = "字段6")
    private String string6;

}
