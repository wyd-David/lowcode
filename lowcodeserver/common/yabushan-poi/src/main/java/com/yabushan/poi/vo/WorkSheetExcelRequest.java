package com.yabushan.poi.vo;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @Description 工单批量导入参数
 *   IExcelModel, IExcelDataModel 是为了实现返回校验失败的信息
 * @Author GX
 * @Date 2020/05/12 14:51
 * @Version V1.0
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkSheetExcelRequest implements IExcelModel, IExcelDataModel {

    @Excel(name = "第几行")
    private int rowNum;
    @Excel(name = "错误信息")
    private String errorMsg;


    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(int rowNum) {

    }

//    @Override
//    public void setRowNum(Integer integer) {
//
//    }



    @Excel(name = "工单名称（必填）")
    @NotBlank(message = "工单名称不能为空")
    private String workSheetName;

    /**
     * 工单类型:   对应代码表
     */
    @Excel(name = "工单类型（必填）", type = 10)
    @NotNull(message = "工单类型不能为空")
    private Integer workSheetType;

    /**
     * 泵房id
     */
    private Long houseId;

    @Excel(name = "泵房名称")
    private String houseName;

    /**
     * 单元id
     */
    private Long unitId;

    @Excel(name = "单元编号")
    private String unitSn;

    @Excel(name = "工单内容（说明）")
    private String workSheetExplain;

    @Excel(name = "部位")
    private String position;

    @Excel(name = "计划开始时间（必填）")
    @NotNull(message = "计划开始时间不能为空")
    @Pattern(regexp = "^((?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$", message = "开始时间格式不正确")
    private String planStartDateString;
    private Long planStartDate;

    @Excel(name = "计划结束时间（必填）")
    @NotNull(message = "计划结束时间不能为空")
    @Pattern(regexp = "^((?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$", message = "结束时间格式不正确")
    private String planEndDateString;
    private Long planEndDate;

    @Excel(name = "计划小时", type = 10)
    @DecimalMin(value = "0", message = "计划小时不能小于0")
    private BigDecimal planHour;

    @Excel(name = "计划人数", type = 10)
    @DecimalMin(value = "1", message = "计划人数不能小于1")
    private Integer planUserNumber;

    @Excel(name = "计划费用: 单位（元）", type = 10)
    @DecimalMin(value = "0", message = "计划费用不能小于0")
    private BigDecimal planOutlay;

    @Excel(name = "备注", width = 15, replace = { "正常_0", "报警_1"})
    private String remarks;

}
