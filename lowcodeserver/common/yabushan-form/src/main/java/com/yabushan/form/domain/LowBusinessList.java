package com.yabushan.form.domain;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import com.yabushan.form.vo.ListTitleVo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 业务列对象 low_business_list
 *
 * @author yabushan
 * @date 2022-10-15
 */

@ApiModel(value = "业务列对象")
public class LowBusinessList extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 列表ID */
    @ApiModelProperty("列表ID")
    private String listId;
            /** 业务列表名称 */
    @Excel(name = "业务列表名称")
    @ApiModelProperty("业务列表名称")
    private String listName;
            /** 业务列表编码 */
    @Excel(name = "业务列表编码")
    @ApiModelProperty("业务列表编码")
    private String listCode;
            /** 是否需要查询按钮;1:是，0：否 */
    @Excel(name = "是否需要查询按钮;1:是，0：否")
    @ApiModelProperty("是否需要查询按钮;1:是，0：否")
    private String needQeuryBtn;
            /** 是否需要导出按钮;1:是，0：否 */
    @Excel(name = "是否需要导出按钮;1:是，0：否")
    @ApiModelProperty("是否需要导出按钮;1:是，0：否")
    private String needExportBtn;
            /** 是否需要导入按钮;1:是，0：否 */
    @Excel(name = "是否需要导入按钮;1:是，0：否")
    @ApiModelProperty("是否需要导入按钮;1:是，0：否")
    private String needInportBtn;
            /** 是否需要修改按钮;1:是，0：否 */
    @Excel(name = "是否需要修改按钮;1:是，0：否")
    @ApiModelProperty("是否需要修改按钮;1:是，0：否")
    private String needUpdateBtn;
            /** 是否需要删除按钮;1:是，0：否 */
    @Excel(name = "是否需要删除按钮;1:是，0：否")
    @ApiModelProperty("是否需要删除按钮;1:是，0：否")
    private String needDeleteBtn;
            /** 是否需要查看按钮;1:是，0：否 */
    @Excel(name = "是否需要查看按钮;1:是，0：否")
    @ApiModelProperty("是否需要查看按钮;1:是，0：否")
    private String needViewBtn;
            /** 绑定的流程ID */
    @Excel(name = "绑定的流程ID")
    @ApiModelProperty("绑定的流程ID")
    private String listFlowProcdefId;
            /** 绑定的表单ID */
    @Excel(name = "绑定的表单ID")
    @ApiModelProperty("绑定的表单ID")
    private String formUuid;
            /** 业务列表状态 */
    @Excel(name = "业务列表状态")
    @ApiModelProperty("业务列表状态")
    private String listStatus;
            /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String listMemo;
            /** 数据源 */
    @Excel(name = "数据源")
    @ApiModelProperty("数据源")
    private String dataResourceid;
            /** 数据列表API */
    @Excel(name = "数据列表API")
    @ApiModelProperty("数据列表API")
    private String dataApi;
            /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
            /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdTime;
            /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private String updatedBy;
            /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("更新时间")
    private Date updatedTime;
            /** 租户号 */
    @Excel(name = "租户号")
    @ApiModelProperty("租户号")
    private String tenantId;
    @Excel(name = "导入状态")
    private String importStatus;
    private  String listMemoChild;

    private List<ListTitleVo> listTitleVo;
    private List<ListTitleVo> listChildTitleVo;




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
                        public void setListId(String listId)
            {
            this.listId = listId;
            }

    public String getListId()
            {
            return listId;
            }
                            public void setListName(String listName)
            {
            this.listName = listName;
            }

    public String getListName()
            {
            return listName;
            }
                            public void setListCode(String listCode)
            {
            this.listCode = listCode;
            }

    public String getListCode()
            {
            return listCode;
            }
                            public void setNeedQeuryBtn(String needQeuryBtn)
            {
            this.needQeuryBtn = needQeuryBtn;
            }

    public String getNeedQeuryBtn()
            {
            return needQeuryBtn;
            }
                            public void setNeedExportBtn(String needExportBtn)
            {
            this.needExportBtn = needExportBtn;
            }

    public String getNeedExportBtn()
            {
            return needExportBtn;
            }
                            public void setNeedInportBtn(String needInportBtn)
            {
            this.needInportBtn = needInportBtn;
            }

    public String getNeedInportBtn()
            {
            return needInportBtn;
            }
                            public void setNeedUpdateBtn(String needUpdateBtn)
            {
            this.needUpdateBtn = needUpdateBtn;
            }

    public String getNeedUpdateBtn()
            {
            return needUpdateBtn;
            }
                            public void setNeedDeleteBtn(String needDeleteBtn)
            {
            this.needDeleteBtn = needDeleteBtn;
            }

    public String getNeedDeleteBtn()
            {
            return needDeleteBtn;
            }
                            public void setNeedViewBtn(String needViewBtn)
            {
            this.needViewBtn = needViewBtn;
            }

    public String getNeedViewBtn()
            {
            return needViewBtn;
            }
                            public void setListFlowProcdefId(String listFlowProcdefId)
            {
            this.listFlowProcdefId = listFlowProcdefId;
            }

    public String getListFlowProcdefId()
            {
            return listFlowProcdefId;
            }
                            public void setFormUuid(String formUuid)
            {
            this.formUuid = formUuid;
            }

    public String getFormUuid()
            {
            return formUuid;
            }
                            public void setListStatus(String listStatus)
            {
            this.listStatus = listStatus;
            }

    public String getListStatus()
            {
            return listStatus;
            }
                            public void setListMemo(String listMemo)
            {
            this.listMemo = listMemo;
            }

    public String getListMemo()
            {
            return listMemo;
            }
                            public void setDataResourceid(String dataResourceid)
            {
            this.dataResourceid = dataResourceid;
            }

    public String getDataResourceid()
            {
            return dataResourceid;
            }
                            public void setDataApi(String dataApi)
            {
            this.dataApi = dataApi;
            }

    public String getDataApi()
            {
            return dataApi;
            }
                            public void setCreatedBy(String createdBy)
            {
            this.createdBy = createdBy;
            }

    public String getCreatedBy()
            {
            return createdBy;
            }
                            public void setCreatedTime(Date createdTime)
            {
            this.createdTime = createdTime;
            }

    public Date getCreatedTime()
            {
            return createdTime;
            }
                            public void setUpdatedBy(String updatedBy)
            {
            this.updatedBy = updatedBy;
            }

    public String getUpdatedBy()
            {
            return updatedBy;
            }
                            public void setUpdatedTime(Date updatedTime)
            {
            this.updatedTime = updatedTime;
            }

    public Date getUpdatedTime()
            {
            return updatedTime;
            }
                            public void setTenantId(String tenantId)
            {
            this.tenantId = tenantId;
            }

    public String getTenantId()
            {
            return tenantId;
            }

@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("listId", getListId())
                                .append("listName", getListName())
                                .append("listCode", getListCode())
                                .append("needQeuryBtn", getNeedQeuryBtn())
                                .append("needExportBtn", getNeedExportBtn())
                                .append("needInportBtn", getNeedInportBtn())
                                .append("needUpdateBtn", getNeedUpdateBtn())
                                .append("needDeleteBtn", getNeedDeleteBtn())
                                .append("needViewBtn", getNeedViewBtn())
                                .append("listFlowProcdefId", getListFlowProcdefId())
                                .append("formUuid", getFormUuid())
                                .append("listStatus", getListStatus())
                                .append("listMemo", getListMemo())
                                .append("dataResourceid", getDataResourceid())
                                .append("dataApi", getDataApi())
                                .append("createdBy", getCreatedBy())
                                .append("createdTime", getCreatedTime())
                                .append("updatedBy", getUpdatedBy())
                                .append("updatedTime", getUpdatedTime())
                                .append("tenantId", getTenantId())
            .toString();
        }

            public List<ListTitleVo> getListTitleVo() {
                return listTitleVo;
            }

            public void setListTitleVo(List<ListTitleVo> listTitleVo) {
                this.listTitleVo = listTitleVo;
            }

            public String getListMemoChild() {
                return listMemoChild;
            }

            public void setListMemoChild(String listMemoChild) {
                this.listMemoChild = listMemoChild;
            }

            public List<ListTitleVo> getListChildTitleVo() {
                return listChildTitleVo;
            }

            public void setListChildTitleVo(List<ListTitleVo> listChildTitleVo) {
                this.listChildTitleVo = listChildTitleVo;
            }
        }
