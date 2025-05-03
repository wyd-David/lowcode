package com.yabushan.form.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

/**
 * API日志对象 PMIS_API_LOG
 *
 * @author yabushan
 * @date 2023-07-29
 */

@ApiModel(value = "API日志对象")
@Component
public class PmisApiLog extends BaseEntity 
        {
private static final long serialVersionUID = 1L;


        /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String logId;
            /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String apiCode;
                /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String apiParam;
            /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String apiResult;
            /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String apiRowType;
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
                        public void setLogId(String logId)
            {
            this.logId = logId;
            }

    public String getLogId()
            {
            return logId;
            }
                            public void setApiCode(String apiCode)
            {
            this.apiCode = apiCode;
            }

    public String getApiCode()
            {
            return apiCode;
            }
                                public void setApiParam(String apiParam)
            {
            this.apiParam = apiParam;
            }

    public String getApiParam()
            {
            return apiParam;
            }
                            public void setApiResult(String apiResult)
            {
            this.apiResult = apiResult;
            }

    public String getApiResult()
            {
            return apiResult;
            }
                            public void setApiRowType(String apiRowType)
            {
            this.apiRowType = apiRowType;
            }

    public String getApiRowType()
            {
            return apiRowType;
            }

           

            @Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("logId", getLogId())
                                .append("apiCode", getApiCode())
                                .append("createTime", getCreateTime())
                                .append("apiParam", getApiParam())
                                .append("apiResult", getApiResult())
                                .append("apiRowType", getApiRowType())
            .toString();
        }
        }
