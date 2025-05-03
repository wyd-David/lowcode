package com.yabushan.ai.domain;



import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * aiboot对象 ai_chart_boot
 *
 * @author yabushan
 * @date 2024-06-06
 */

@ApiModel(value = "aiboot对象")
public class AiChartBoot extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 自增主键 */
    @ApiModelProperty("自增主键")
    private Integer Id;
    /** 内容 */
    @Excel(name = "内容")
    @ApiModelProperty("内容")
    private String chartText;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdTime;
    /** 标签1 */
    @Excel(name = "标签1")
    @ApiModelProperty("标签1")
    private String lable1;
    /** 标签2 */
    @Excel(name = "标签2")
    @ApiModelProperty("标签2")
    private String label2;
    /** 标签3 */
    @Excel(name = "标签3")
    @ApiModelProperty("标签3")
    private String label3;
    /** 标签4 */
    @Excel(name = "标签4")
    @ApiModelProperty("标签4")
    private String label4;
    /** 标签5 */
    @Excel(name = "标签5")
    @ApiModelProperty("标签5")
    private String label5;
    /** 标签6 */
    @Excel(name = "标签6")
    @ApiModelProperty("标签6")
    private String label6;
    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
    @Excel(name = "导入状态")
    private String importStatus;
    
    

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }
    /**
     * 禁用状态  0启用,1禁用
     */
    private String disableEnableState;

    public String getDisableEnableState() {
        return disableEnableState;
    }

    public void setDisableEnableState(String disableEnableState) {
        this.disableEnableState = disableEnableState;
    }
  
    public void setChartText(String chartText)
    {
        this.chartText = chartText;
    }

    public String getChartText()
    {
        return chartText;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setLable1(String lable1)
    {
        this.lable1 = lable1;
    }

    public String getLable1()
    {
        return lable1;
    }
    public void setLabel2(String label2)
    {
        this.label2 = label2;
    }

    public String getLabel2()
    {
        return label2;
    }
    public void setLabel3(String label3)
    {
        this.label3 = label3;
    }

    public String getLabel3()
    {
        return label3;
    }
    public void setLabel4(String label4)
    {
        this.label4 = label4;
    }

    public String getLabel4()
    {
        return label4;
    }
    public void setLabel5(String label5)
    {
        this.label5 = label5;
    }

    public String getLabel5()
    {
        return label5;
    }
    public void setLabel6(String label6)
    {
        this.label6 = label6;
    }

    public String getLabel6()
    {
        return label6;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("chartId", getId())
                .append("chartText", getChartText())
                .append("createdTime", getCreatedTime())
                .append("lable1", getLable1())
                .append("label2", getLabel2())
                .append("label3", getLabel3())
                .append("label4", getLabel4())
                .append("label5", getLabel5())
                .append("label6", getLabel6())
                .append("createdBy", getCreatedBy())
                .toString();
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
}
