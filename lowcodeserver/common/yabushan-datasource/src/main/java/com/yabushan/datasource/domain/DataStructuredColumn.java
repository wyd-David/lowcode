package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 字段元数据对象 data_structured_column
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "字段元数据对象")
public class DataStructuredColumn extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String columnId;
    /** 数据项编码 */
    @Excel(name = "数据项编码")
    @ApiModelProperty("数据项编码")
    private String columnCode;
    /** $column.columnComment */
    @Excel(name = "数据项编码")
    @ApiModelProperty("$column.columnComment")
    private String resourceId;
    /** $column.columnComment */
    @Excel(name = "数据项编码")
    @ApiModelProperty("数据项编码")
    private String name;
    /** $column.columnComment */
    @Excel(name = "数据项编码")
    @ApiModelProperty("数据项编码")
    private String displayName;
    /** 字段类型 */
    @Excel(name = "字段类型")
    @ApiModelProperty("字段类型")
    private String typeName;
    /** 长度 */
    @Excel(name = "长度")
    @ApiModelProperty("长度")
    private Long length;
    /** 精度 */
    @Excel(name = "精度")
    @ApiModelProperty("精度")
    private String precision;
    /** $column.columnComment */
    @Excel(name = "精度")
    @ApiModelProperty("$column.columnComment")
    private String defaultValue;
    /** 是否为外键，1是，0否 */
    @Excel(name = "是否为外键，1是，0否")
    @ApiModelProperty("是否为外键，1是，0否")
    private String isForeignKey;
    /** 是否为主键，1是，0否 */
    @Excel(name = "是否为主键，1是，0否")
    @ApiModelProperty("是否为主键，1是，0否")
    private String isPrimaryKey;
    /** 是否允许为空，1是，0否 */
    @Excel(name = "是否允许为空，1是，0否")
    @ApiModelProperty("是否允许为空，1是，0否")
    private String isNullable;
    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String comment;
    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long order;
    /** 是否有效。1有效，0已删除 */
    @Excel(name = "是否有效。1有效，0已删除")
    @ApiModelProperty("是否有效。1有效，0已删除")
    private String visible;
    /** 数值类型，1百分数；2数值，3文本 */
    @Excel(name = "数值类型，1百分数；2数值，3文本")
    @ApiModelProperty("数值类型，1百分数；2数值，3文本")
    private String valueType;
    /** 默认聚合函数 */
    @Excel(name = "默认聚合函数")
    @ApiModelProperty("默认聚合函数")
    private String defaultAggFuncation;
    /** 数据分析类型：1指标，2维度 */
    @Excel(name = "数据分析类型：1指标，2维度")
    @ApiModelProperty("数据分析类型：1指标，2维度")
    private String dataAnalysisType;
    /** 单位 */
    @Excel(name = "单位")
    @ApiModelProperty("单位")
    private String unit;
    /** 是否查询条件字段，1是，0否 */
    @Excel(name = "是否查询条件字段，1是，0否")
    @ApiModelProperty("是否查询条件字段，1是，0否")
    private String isSearchCondition;
    /** 查询匹配格式：1等值匹配；2模糊匹配；3正则表达式匹配；4样本匹配 */
    @Excel(name = "查询匹配格式：1等值匹配；2模糊匹配；3正则表达式匹配；4样本匹配")
    @ApiModelProperty("查询匹配格式：1等值匹配；2模糊匹配；3正则表达式匹配；4样本匹配")
    private String searchFormat;
    /** 字段的数据分级 */
    @Excel(name = "字段的数据分级")
    @ApiModelProperty("字段的数据分级")
    private String securityGradeId;
    /** 加密类型 */
    @Excel(name = "加密类型")
    @ApiModelProperty("加密类型")
    private String encryptRuleId;
    /** 脱敏规则Id */
    @Excel(name = "脱敏规则Id")
    @ApiModelProperty("脱敏规则Id")
    private String desensitizedRuleId;
    /** 对应的标准代码ID */
    @Excel(name = "对应的标准代码ID")
    @ApiModelProperty("对应的标准代码ID")
    private String standardCodeId;
    /** 显示格式 */
    @Excel(name = "显示格式")
    @ApiModelProperty("显示格式")
    private String displayFormat;
    /** 类型分类NUMBER、STRING、DATE、BINARY */
    @Excel(name = "类型分类NUMBER、STRING、DATE、BINARY")
    @ApiModelProperty("类型分类NUMBER、STRING、DATE、BINARY")
    private String typeClassify;
    /** 同义词名称，多个用英文逗号间隔 */
    @Excel(name = "同义词名称，多个用英文逗号间隔")
    @ApiModelProperty("同义词名称，多个用英文逗号间隔")
    private String sameName;
    /** 关联的字典资源  -- 废除 */
    @Excel(name = "关联的字典资源  -- 废除")
    @ApiModelProperty("关联的字典资源  -- 废除")
    private String dictResourceId;
    /** 字典资源名称 */
    @Excel(name = "字典资源名称")
    @ApiModelProperty("字典资源名称")
    private String dictResourceName;
    /** 字典资源关系 */
    @Excel(name = "字典资源关系")
    @ApiModelProperty("字典资源关系")
    private String dictRelationId;
    /** 列类型，对应字典表column_type */
    @Excel(name = "列类型，对应字典表column_type")
    @ApiModelProperty("列类型，对应字典表column_type")
    private String columnType;
    /** 外部链接 */
    @Excel(name = "外部链接")
    @ApiModelProperty("外部链接")
    private String externalLink;
    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Long showOrder;
    /** 选择方式：1、下拉框 2、弹出框 */
    @Excel(name = "选择方式：1、下拉框 2、弹出框")
    @ApiModelProperty("选择方式：1、下拉框 2、弹出框")
    private String choiceMode;
    /** 是否自增长主键 */
    @Excel(name = "是否自增长主键")
    @ApiModelProperty("是否自增长主键")
    private String increment;
    /** 非物理字段的默认值，是一个JSON格式的简单数值或者是一个环境变量 */
    @Excel(name = "非物理字段的默认值，是一个JSON格式的简单数值或者是一个环境变量")
    @ApiModelProperty("非物理字段的默认值，是一个JSON格式的简单数值或者是一个环境变量")
    private String logicDefault;
    /** 非物理字段的固定值，是一个JSON格式的简单数值或者是一个环境变量 */
    @Excel(name = "非物理字段的固定值，是一个JSON格式的简单数值或者是一个环境变量")
    @ApiModelProperty("非物理字段的固定值，是一个JSON格式的简单数值或者是一个环境变量")
    private String logicValue;
    /** 字段是否可更新，默认为1 */
    @Excel(name = "字段是否可更新，默认为1")
    @ApiModelProperty("字段是否可更新，默认为1")
    private String updatable;
    /** 是否持久化，默认为1，设置为0表示这是一个逻辑字段 */
    @Excel(name = "是否持久化，默认为1，设置为0表示这是一个逻辑字段")
    @ApiModelProperty("是否持久化，默认为1，设置为0表示这是一个逻辑字段")
    private String persist;
    /** 字段是否不对外公开，默认为0 */
    @Excel(name = "字段是否不对外公开，默认为0")
    @ApiModelProperty("字段是否不对外公开，默认为0")
    private String internal;
    /** 字段是否可被排序，默认为0 */
    @Excel(name = "字段是否可被排序，默认为0")
    @ApiModelProperty("字段是否可被排序，默认为0")
    private String sortable;
    /** 字段是否可被作为过滤条件，默认为0 */
    @Excel(name = "字段是否可被作为过滤条件，默认为0")
    @ApiModelProperty("字段是否可被作为过滤条件，默认为0")
    private String filterable;
    /** 字段选项集名称（范围） */
    @Excel(name = "字段选项集名称", readConverterExp = "范=围")
    @ApiModelProperty("字段选项集名称（范围）")
    private String options;
    /** 字段格式 */
    @Excel(name = "字段格式")
    @ApiModelProperty("字段格式")
    private String format;
    /** 字段验证表达式 */
    @Excel(name = "字段验证表达式")
    @ApiModelProperty("字段验证表达式")
    private String validate;
    /** 字段输入类型 */
    @Excel(name = "字段输入类型")
    @ApiModelProperty("字段输入类型")
    private String input;
    /** 指定数据来源于关系实体字段，如from relation_name.field */
    @Excel(name = "指定数据来源于关系实体字段，如from relation_name.field")
    @ApiModelProperty("指定数据来源于关系实体字段，如from relation_name.field")
    private String from;
    /** 扩展属性字段，可以存储JSON，由使用方决定如何使用 */
    @Excel(name = "扩展属性字段，可以存储JSON，由使用方决定如何使用")
    @ApiModelProperty("扩展属性字段，可以存储JSON，由使用方决定如何使用")
    private String extension;
    /** 驼峰名 */
    @Excel(name = "驼峰名")
    @ApiModelProperty("驼峰名")
    private String camelName;
    /** 默认有权限 */
    @Excel(name = "默认有权限")
    @ApiModelProperty("默认有权限")
    private String defaultPermission;
    /** 密级 */
    @Excel(name = "密级")
    @ApiModelProperty("密级")
    private Long secretLevel;
    /** 当前物理字段名 */
    @Excel(name = "当前物理字段名")
    @ApiModelProperty("当前物理字段名")
    private String oldColumnName;
    /** 是否知识抽取 */
    @Excel(name = "是否知识抽取")
    @ApiModelProperty("是否知识抽取")
    private String isExtractKg;
    /** 图谱是否隐藏 */
    @Excel(name = "图谱是否隐藏")
    @ApiModelProperty("图谱是否隐藏")
    private String isGraphHidden;
    /** 状态：designing 设计中，materialization 已物化 */
    @Excel(name = "状态：designing 设计中，materialization 已物化")
    @ApiModelProperty("状态：designing 设计中，materialization 已物化")
    private String status;
    /** 映射字段ID */
    @Excel(name = "映射字段ID")
    @ApiModelProperty("映射字段ID")
    private String mappingColumnId;
    /** 字段存在类型：物理还是逻辑 */
    @Excel(name = "字段存在类型：物理还是逻辑")
    @ApiModelProperty("字段存在类型：物理还是逻辑")
    private String columnExistenceType;
    /** 业务字段还是技术字段 */
    @Excel(name = "业务字段还是技术字段")
    @ApiModelProperty("业务字段还是技术字段")
    private String columnUseType;
    /** 逻辑字段映射的物理字段 */
    @Excel(name = "逻辑字段映射的物理字段")
    @ApiModelProperty("逻辑字段映射的物理字段")
    private String physicalColumnId;
    /** 继承 */
    @Excel(name = "继承")
    @ApiModelProperty("继承")
    private String extendFrom;
    /** 所属应用id */
    @Excel(name = "所属应用id")
    @ApiModelProperty("所属应用id")
    private String appId;
    /** 所属数据分类ID */
    @Excel(name = "所属数据分类ID")
    @ApiModelProperty("所属数据分类ID")
    private String categoryId;
    /** 共享类型 */
    @Excel(name = "共享类型")
    @ApiModelProperty("共享类型")
    private String sharingType;
    /** 正向字段、逆向字段类型:0正向/1逆向 */
    @Excel(name = "正向字段、逆向字段类型:0正向/1逆向")
    @ApiModelProperty("正向字段、逆向字段类型:0正向/1逆向")
    private String fineType;
    /** 字段函数表达式 */
    @Excel(name = "字段函数表达式")
    @ApiModelProperty("字段函数表达式")
    private String functionExpression;
    /** 字段函数表达式 */
    @Excel(name = "字段函数表达式")
    @ApiModelProperty("字段函数表达式")
    private String funt;
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
    public void setColumnId(String columnId)
    {
        this.columnId = columnId;
    }

    public String getColumnId()
    {
        return columnId;
    }
    public void setColumnCode(String columnCode)
    {
        this.columnCode = columnCode;
    }

    public String getColumnCode()
    {
        return columnCode;
    }
    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }

    public String getResourceId()
    {
        return resourceId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getTypeName()
    {
        return typeName;
    }
    public void 
    setLength(Long length)
    {
        this.length = length;
    }

    public Long getLength()
    {
        return length;
    }
    public void setPrecision(String precision)
    {
        this.precision = precision;
    }

    public String getPrecision()
    {
        return precision;
    }
    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }
    public void setIsForeignKey(String isForeignKey)
    {
        this.isForeignKey = isForeignKey;
    }

    public String getIsForeignKey()
    {
        return isForeignKey;
    }
    public void setIsPrimaryKey(String isPrimaryKey)
    {
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getIsPrimaryKey()
    {
        return isPrimaryKey;
    }
    public void setIsNullable(String isNullable)
    {
        this.isNullable = isNullable;
    }

    public String getIsNullable()
    {
        return isNullable;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }
    public void setOrder(Long order)
    {
        this.order = order;
    }

    public Long getOrder()
    {
        return order;
    }
    public void setVisible(String visible)
    {
        this.visible = visible;
    }

    public String getVisible()
    {
        return visible;
    }
    public void setValueType(String valueType)
    {
        this.valueType = valueType;
    }

    public String getValueType()
    {
        return valueType;
    }
    public void setDefaultAggFuncation(String defaultAggFuncation)
    {
        this.defaultAggFuncation = defaultAggFuncation;
    }

    public String getDefaultAggFuncation()
    {
        return defaultAggFuncation;
    }
    public void setDataAnalysisType(String dataAnalysisType)
    {
        this.dataAnalysisType = dataAnalysisType;
    }

    public String getDataAnalysisType()
    {
        return dataAnalysisType;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setIsSearchCondition(String isSearchCondition)
    {
        this.isSearchCondition = isSearchCondition;
    }

    public String getIsSearchCondition()
    {
        return isSearchCondition;
    }
    public void setSearchFormat(String searchFormat)
    {
        this.searchFormat = searchFormat;
    }

    public String getSearchFormat()
    {
        return searchFormat;
    }
    public void setSecurityGradeId(String securityGradeId)
    {
        this.securityGradeId = securityGradeId;
    }

    public String getSecurityGradeId()
    {
        return securityGradeId;
    }
    public void setEncryptRuleId(String encryptRuleId)
    {
        this.encryptRuleId = encryptRuleId;
    }

    public String getEncryptRuleId()
    {
        return encryptRuleId;
    }
    public void setDesensitizedRuleId(String desensitizedRuleId)
    {
        this.desensitizedRuleId = desensitizedRuleId;
    }

    public String getDesensitizedRuleId()
    {
        return desensitizedRuleId;
    }
    public void setStandardCodeId(String standardCodeId)
    {
        this.standardCodeId = standardCodeId;
    }

    public String getStandardCodeId()
    {
        return standardCodeId;
    }
    public void setDisplayFormat(String displayFormat)
    {
        this.displayFormat = displayFormat;
    }

    public String getDisplayFormat()
    {
        return displayFormat;
    }
    public void setTypeClassify(String typeClassify)
    {
        this.typeClassify = typeClassify;
    }

    public String getTypeClassify()
    {
        return typeClassify;
    }
    public void setSameName(String sameName)
    {
        this.sameName = sameName;
    }

    public String getSameName()
    {
        return sameName;
    }
    public void setDictResourceId(String dictResourceId)
    {
        this.dictResourceId = dictResourceId;
    }

    public String getDictResourceId()
    {
        return dictResourceId;
    }
    public void setDictResourceName(String dictResourceName)
    {
        this.dictResourceName = dictResourceName;
    }

    public String getDictResourceName()
    {
        return dictResourceName;
    }
    public void setDictRelationId(String dictRelationId)
    {
        this.dictRelationId = dictRelationId;
    }

    public String getDictRelationId()
    {
        return dictRelationId;
    }
    public void setColumnType(String columnType)
    {
        this.columnType = columnType;
    }

    public String getColumnType()
    {
        return columnType;
    }
    public void setExternalLink(String externalLink)
    {
        this.externalLink = externalLink;
    }

    public String getExternalLink()
    {
        return externalLink;
    }
    public void setShowOrder(Long showOrder)
    {
        this.showOrder = showOrder;
    }

    public Long getShowOrder()
    {
        return showOrder;
    }
    public void setChoiceMode(String choiceMode)
    {
        this.choiceMode = choiceMode;
    }

    public String getChoiceMode()
    {
        return choiceMode;
    }
    public void setIncrement(String increment)
    {
        this.increment = increment;
    }

    public String getIncrement()
    {
        return increment;
    }
    public void setLogicDefault(String logicDefault)
    {
        this.logicDefault = logicDefault;
    }

    public String getLogicDefault()
    {
        return logicDefault;
    }
    public void setLogicValue(String logicValue)
    {
        this.logicValue = logicValue;
    }

    public String getLogicValue()
    {
        return logicValue;
    }
    public void setUpdatable(String updatable)
    {
        this.updatable = updatable;
    }

    public String getUpdatable()
    {
        return updatable;
    }
    public void setPersist(String persist)
    {
        this.persist = persist;
    }

    public String getPersist()
    {
        return persist;
    }
    public void setInternal(String internal)
    {
        this.internal = internal;
    }

    public String getInternal()
    {
        return internal;
    }
    public void setSortable(String sortable)
    {
        this.sortable = sortable;
    }

    public String getSortable()
    {
        return sortable;
    }
    public void setFilterable(String filterable)
    {
        this.filterable = filterable;
    }

    public String getFilterable()
    {
        return filterable;
    }
    public void setOptions(String options)
    {
        this.options = options;
    }

    public String getOptions()
    {
        return options;
    }
    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getFormat()
    {
        return format;
    }
    public void setValidate(String validate)
    {
        this.validate = validate;
    }

    public String getValidate()
    {
        return validate;
    }
    public void setInput(String input)
    {
        this.input = input;
    }

    public String getInput()
    {
        return input;
    }
    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getFrom()
    {
        return from;
    }
    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    public String getExtension()
    {
        return extension;
    }
    public void setCamelName(String camelName)
    {
        this.camelName = camelName;
    }

    public String getCamelName()
    {
        return camelName;
    }
    public void setDefaultPermission(String defaultPermission)
    {
        this.defaultPermission = defaultPermission;
    }

    public String getDefaultPermission()
    {
        return defaultPermission;
    }
    public void setSecretLevel(Long secretLevel)
    {
        this.secretLevel = secretLevel;
    }

    public Long getSecretLevel()
    {
        return secretLevel;
    }
    public void setOldColumnName(String oldColumnName)
    {
        this.oldColumnName = oldColumnName;
    }

    public String getOldColumnName()
    {
        return oldColumnName;
    }
    public void setIsExtractKg(String isExtractKg)
    {
        this.isExtractKg = isExtractKg;
    }

    public String getIsExtractKg()
    {
        return isExtractKg;
    }
    public void setIsGraphHidden(String isGraphHidden)
    {
        this.isGraphHidden = isGraphHidden;
    }

    public String getIsGraphHidden()
    {
        return isGraphHidden;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setMappingColumnId(String mappingColumnId)
    {
        this.mappingColumnId = mappingColumnId;
    }

    public String getMappingColumnId()
    {
        return mappingColumnId;
    }
    public void setColumnExistenceType(String columnExistenceType)
    {
        this.columnExistenceType = columnExistenceType;
    }

    public String getColumnExistenceType()
    {
        return columnExistenceType;
    }
    public void setColumnUseType(String columnUseType)
    {
        this.columnUseType = columnUseType;
    }

    public String getColumnUseType()
    {
        return columnUseType;
    }
    public void setPhysicalColumnId(String physicalColumnId)
    {
        this.physicalColumnId = physicalColumnId;
    }

    public String getPhysicalColumnId()
    {
        return physicalColumnId;
    }
    public void setExtendFrom(String extendFrom)
    {
        this.extendFrom = extendFrom;
    }

    public String getExtendFrom()
    {
        return extendFrom;
    }
    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppId()
    {
        return appId;
    }
    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryId()
    {
        return categoryId;
    }
    public void setSharingType(String sharingType)
    {
        this.sharingType = sharingType;
    }

    public String getSharingType()
    {
        return sharingType;
    }
    public void setFineType(String fineType)
    {
        this.fineType = fineType;
    }

    public String getFineType()
    {
        return fineType;
    }
    public void setFunctionExpression(String functionExpression)
    {
        this.functionExpression = functionExpression;
    }

    public String getFunctionExpression()
    {
        return functionExpression;
    }
    public void setFunt(String funt)
    {
        this.funt = funt;
    }

    public String getFunt()
    {
        return funt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("columnId", getColumnId())
                .append("columnCode", getColumnCode())
                .append("resourceId", getResourceId())
                .append("name", getName())
                .append("displayName", getDisplayName())
                .append("typeName", getTypeName())
                .append("length", getLength())
                .append("precision", getPrecision())
                .append("defaultValue", getDefaultValue())
                .append("isForeignKey", getIsForeignKey())
                .append("isPrimaryKey", getIsPrimaryKey())
                .append("isNullable", getIsNullable())
                .append("comment", getComment())
                .append("order", getOrder())
                .append("visible", getVisible())
                .append("valueType", getValueType())
                .append("defaultAggFuncation", getDefaultAggFuncation())
                .append("dataAnalysisType", getDataAnalysisType())
                .append("unit", getUnit())
                .append("isSearchCondition", getIsSearchCondition())
                .append("searchFormat", getSearchFormat())
                .append("securityGradeId", getSecurityGradeId())
                .append("encryptRuleId", getEncryptRuleId())
                .append("desensitizedRuleId", getDesensitizedRuleId())
                .append("standardCodeId", getStandardCodeId())
                .append("displayFormat", getDisplayFormat())
                .append("typeClassify", getTypeClassify())
                .append("sameName", getSameName())
                .append("dictResourceId", getDictResourceId())
                .append("dictResourceName", getDictResourceName())
                .append("dictRelationId", getDictRelationId())
                .append("columnType", getColumnType())
                .append("externalLink", getExternalLink())
                .append("showOrder", getShowOrder())
                .append("choiceMode", getChoiceMode())
                .append("increment", getIncrement())
                .append("logicDefault", getLogicDefault())
                .append("logicValue", getLogicValue())
                .append("updatable", getUpdatable())
                .append("persist", getPersist())
                .append("internal", getInternal())
                .append("sortable", getSortable())
                .append("filterable", getFilterable())
                .append("options", getOptions())
                .append("format", getFormat())
                .append("validate", getValidate())
                .append("input", getInput())
                .append("from", getFrom())
                .append("extension", getExtension())
                .append("camelName", getCamelName())
                .append("defaultPermission", getDefaultPermission())
                .append("secretLevel", getSecretLevel())
                .append("oldColumnName", getOldColumnName())
                .append("isExtractKg", getIsExtractKg())
                .append("isGraphHidden", getIsGraphHidden())
                .append("status", getStatus())
                .append("mappingColumnId", getMappingColumnId())
                .append("columnExistenceType", getColumnExistenceType())
                .append("columnUseType", getColumnUseType())
                .append("physicalColumnId", getPhysicalColumnId())
                .append("extendFrom", getExtendFrom())
                .append("appId", getAppId())
                .append("categoryId", getCategoryId())
                .append("sharingType", getSharingType())
                .append("fineType", getFineType())
                .append("functionExpression", getFunctionExpression())
                .append("funt", getFunt())
                .toString();
    }
}
