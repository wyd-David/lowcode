package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据源类型分类对象 metadata_datasource_type_class
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "数据源类型分类对象")
public class MetadataDatasourceTypeClass extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 分类代码 */
    @ApiModelProperty("分类代码")
    private String classCode;
            /** 分类名称 */
    @Excel(name = "分类名称")
    @ApiModelProperty("分类名称")
    private String className;
            /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long order;
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
                        public void setClassCode(String classCode)
            {
            this.classCode = classCode;
            }

    public String getClassCode()
            {
            return classCode;
            }
                            public void setClassName(String className)
            {
            this.className = className;
            }

    public String getClassName()
            {
            return className;
            }
                            public void setOrder(Long order)
            {
            this.order = order;
            }

    public Long getOrder()
            {
            return order;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("classCode", getClassCode())
                                .append("className", getClassName())
                                .append("order", getOrder())
            .toString();
        }
        }
