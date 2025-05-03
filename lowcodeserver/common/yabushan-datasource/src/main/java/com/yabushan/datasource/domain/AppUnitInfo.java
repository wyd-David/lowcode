package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 动态数据源管理对象 app_unit_info
 *
 * @author yabushan
 * @date 2024-01-13
 */

@ApiModel(value = "动态数据源管理对象")
public class AppUnitInfo extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 驱动类型 */
    @Excel(name = "驱动类型")
    @ApiModelProperty("驱动类型")
    private String driverclass;
            /** ip地址 */
    @Excel(name = "ip地址")
    @ApiModelProperty("ip地址")
    private String ip;
            /** 端口 */
    @Excel(name = "端口")
    @ApiModelProperty("端口")
    private String port;
            /** 数据库 */
    @Excel(name = "数据库")
    @ApiModelProperty("数据库")
    private String database;
            /** 数据库schema */
    @Excel(name = "数据库schema")
    @ApiModelProperty("数据库schema")
    private String schema;
            /** 实例名称 */
    @Excel(name = "实例名称")
    @ApiModelProperty("实例名称")
    private String instancedisplyname;
            /** 用户名 */
    @Excel(name = "用户名")
    @ApiModelProperty("用户名")
    private String username;
            /** 密码 */
    @Excel(name = "密码")
    @ApiModelProperty("密码")
    private String password;
            /** 版本号 */
    @Excel(name = "版本号")
    @ApiModelProperty("版本号")
    private String versioncode;
            /** 数据源实例ID */
    @ApiModelProperty("数据源实例ID")
    private String datasourceid;
            /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String infos;
            /** 是否删除，1：是 */
    @Excel(name = "是否删除，1：是")
    @ApiModelProperty("是否删除，1：是")
    private String isDelete;
            /** 状态，1：异常 */
    @Excel(name = "状态，1：异常")
    @ApiModelProperty("状态，1：异常")
    private String appStatus;
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
                        public void setDriverclass(String driverclass)
            {
            this.driverclass = driverclass;
            }

    public String getDriverclass()
            {
            return driverclass;
            }
                            public void setIp(String ip)
            {
            this.ip = ip;
            }

    public String getIp()
            {
            return ip;
            }
                            public void setPort(String port)
            {
            this.port = port;
            }

    public String getPort()
            {
            return port;
            }
                            public void setDatabase(String database)
            {
            this.database = database;
            }

    public String getDatabase()
            {
            return database;
            }
                            public void setSchema(String schema)
            {
            this.schema = schema;
            }

    public String getSchema()
            {
            return schema;
            }
                            public void setInstancedisplyname(String instancedisplyname)
            {
            this.instancedisplyname = instancedisplyname;
            }

    public String getInstancedisplyname()
            {
            return instancedisplyname;
            }
                            public void setUsername(String username)
            {
            this.username = username;
            }

    public String getUsername()
            {
            return username;
            }
                            public void setPassword(String password)
            {
            this.password = password;
            }

    public String getPassword()
            {
            return password;
            }
                            public void setVersioncode(String versioncode)
            {
            this.versioncode = versioncode;
            }

    public String getVersioncode()
            {
            return versioncode;
            }
                            public void setDatasourceid(String datasourceid)
            {
            this.datasourceid = datasourceid;
            }

    public String getDatasourceid()
            {
            return datasourceid;
            }
                            public void setInfos(String infos)
            {
            this.infos = infos;
            }

    public String getInfos()
            {
            return infos;
            }
                            public void setIsDelete(String isDelete)
            {
            this.isDelete = isDelete;
            }

    public String getIsDelete()
            {
            return isDelete;
            }
                            public void setAppStatus(String appStatus)
            {
            this.appStatus = appStatus;
            }

    public String getAppStatus()
            {
            return appStatus;
            }
            
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("driverclass", getDriverclass())
                                .append("ip", getIp())
                                .append("port", getPort())
                                .append("database", getDatabase())
                                .append("schema", getSchema())
                                .append("instancedisplyname", getInstancedisplyname())
                                .append("username", getUsername())
                                .append("password", getPassword())
                                .append("versioncode", getVersioncode())
                                .append("datasourceid", getDatasourceid())
                                .append("infos", getInfos())
                                .append("isDelete", getIsDelete())
                                .append("appStatus", getAppStatus())
                                .append("createTime", getCreateTime())
                                .append("createBy", getCreateBy())
            .toString();
        }
        }
