<template>
  <div class="app-container">
    <expand-collapse>
      <div slot="left">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="所属目录ID" prop="resourceId">
            <el-input v-model="queryParams.resourceId" placeholder="请输入数据项编码" clearable size="small"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
        <el-form-item label="数据项编码" prop="columnCode">
          <el-input v-model="queryParams.columnCode" placeholder="请输入数据项编码" clearable size="small"
            @keyup.enter.native="handleQuery" />
        </el-form-item>

        </el-form>
      </div>
      <div slot="right">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
      </div>
    </expand-collapse>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
         <!--  <el-col :span="1.5">
       <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAdd"
            v-hasPermi="['datasource:dataStructuredColumn:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
            v-hasPermi="['datasource:dataStructuredColumn:edit']">修改</el-button>
        </el-col> -->
       <!-- <el-col :span="1.5">
          <el-button type="danger" icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
            v-hasPermi="['datasource:dataStructuredColumn:remove']">删除</el-button>
        </el-col> -->
        <!-- <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-lock" size="mini" :disabled="multiple" @click="disabledBtnClick(1)"
            v-hasPermi="['datasource:dataStructuredColumn:remove']">禁用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="primary" icon="el-icon-unlock" size="mini" :disabled="multiple" @click="disabledBtnClick(0)"
            v-hasPermi="['datasource:dataStructuredColumn:remove']">启用</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-upload" size="mini" @click="openImportTable"
            v-hasPermi="['datasource:dataStructuredColumn:importData']">导入</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="default" icon="el-icon-download" size="mini" @click="handleExport"
            v-hasPermi="['datasource:dataStructuredColumn:importData']">导出</el-button>
        </el-col> -->
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="dataStructuredColumnList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="left" />

        <el-table-column label="数据项编码" align="center" prop="columnCode" />
        <el-table-column label="中文名称" align="center" prop="displayName" />
        <el-table-column label="字段类型" align="center" prop="typeName" />
          <el-table-column label="类型分类" align="center" prop="typeClassify" />
          <el-table-column label="长度" align="center" prop="length" />
          <el-table-column label="精度" align="center" prop="precision" />
          <el-table-column label="默认值" align="center" prop="defaultValue" />
        <el-table-column label="数据项ID" align="center" prop="columnId" />
        <el-table-column label="所属目录ID" align="center" prop="resourceId" />
        <!-- <el-table-column label="数据项编码" align="center" prop="name" /> -->

<!--        <el-table-column label="是否为外键(1是)" align="center" prop="isForeignKey" /> -->
        <el-table-column label="主键(1是)" align="center" prop="isPrimaryKey" />
        <el-table-column label="允许为空(1是)" align="center" prop="isNullable" />

       <!-- <el-table-column label="备注" align="center" prop="comment" />
        <el-table-column label="排序" align="center" prop="order" />
        <el-table-column label="是否有效。1有效，0已删除" align="center" prop="visible" />
        <el-table-column label="数值类型，1百分数；2数值，3文本" align="center" prop="valueType" />
        <el-table-column label="默认聚合函数" align="center" prop="defaultAggFuncation" />
        <el-table-column label="数据分析类型：1指标，2维度" align="center" prop="dataAnalysisType" />
        <el-table-column label="单位" align="center" prop="unit" />
        <el-table-column label="是否查询条件字段，1是，0否" align="center" prop="isSearchCondition" />
        <el-table-column label="查询匹配格式：1等值匹配；2模糊匹配；3正则表达式匹配；4样本匹配" align="center" prop="searchFormat" />
        <el-table-column label="字段的数据分级" align="center" prop="securityGradeId" />
        <el-table-column label="加密类型" align="center" prop="encryptRuleId" />
        <el-table-column label="脱敏规则Id" align="center" prop="desensitizedRuleId" />
        <el-table-column label="对应的标准代码ID" align="center" prop="standardCodeId" />
        <el-table-column label="显示格式" align="center" prop="displayFormat" />
        <el-table-column label="类型分类NUMBER、STRING、DATE、BINARY" align="center" prop="typeClassify" />
        <el-table-column label="同义词名称，多个用英文逗号间隔" align="center" prop="sameName" />
        <el-table-column label="关联的字典资源  -- 废除" align="center" prop="dictResourceId" />
        <el-table-column label="字典资源名称" align="center" prop="dictResourceName" />
        <el-table-column label="字典资源关系" align="center" prop="dictRelationId" />
        <el-table-column label="列类型，对应字典表column_type" align="center" prop="columnType" />
        <el-table-column label="外部链接" align="center" prop="externalLink" />
        <el-table-column label="显示顺序" align="center" prop="showOrder" />
        <el-table-column label="选择方式：1、下拉框 2、弹出框" align="center" prop="choiceMode" />
        <el-table-column label="是否自增长主键" align="center" prop="increment" />
        <el-table-column label="非物理字段的默认值，是一个JSON格式的简单数值或者是一个环境变量" align="center" prop="logicDefault" />
        <el-table-column label="非物理字段的固定值，是一个JSON格式的简单数值或者是一个环境变量" align="center" prop="logicValue" />
        <el-table-column label="字段是否可更新，默认为1" align="center" prop="updatable" />
        <el-table-column label="是否持久化，默认为1，设置为0表示这是一个逻辑字段" align="center" prop="persist" />
        <el-table-column label="字段是否不对外公开，默认为0" align="center" prop="internal" />
        <el-table-column label="字段是否可被排序，默认为0" align="center" prop="sortable" />
        <el-table-column label="字段是否可被作为过滤条件，默认为0" align="center" prop="filterable" />
        <el-table-column label="字段选项集名称" align="center" prop="options" />
        <el-table-column label="字段格式" align="center" prop="format" />
        <el-table-column label="字段验证表达式" align="center" prop="validate" />
        <el-table-column label="字段输入类型" align="center" prop="input" />
        <el-table-column label="指定数据来源于关系实体字段，如from relation_name.field" align="center" prop="from" />
        <el-table-column label="扩展属性字段，可以存储JSON，由使用方决定如何使用" align="center" prop="extension" />
        <el-table-column label="驼峰名" align="center" prop="camelName" />
        <el-table-column label="默认有权限" align="center" prop="defaultPermission" />
        <el-table-column label="密级" align="center" prop="secretLevel" />
        <el-table-column label="当前物理字段名" align="center" prop="oldColumnName" />
        <el-table-column label="是否知识抽取" align="center" prop="isExtractKg" />
        <el-table-column label="图谱是否隐藏" align="center" prop="isGraphHidden" />
        <el-table-column label="状态：designing 设计中，materialization 已物化" align="center" prop="status" />
        <el-table-column label="映射字段ID" align="center" prop="mappingColumnId" />
        <el-table-column label="字段存在类型：物理还是逻辑" align="center" prop="columnExistenceType" />
        <el-table-column label="业务字段还是技术字段" align="center" prop="columnUseType" />
        <el-table-column label="逻辑字段映射的物理字段" align="center" prop="physicalColumnId" />
        <el-table-column label="继承" align="center" prop="extendFrom" />
        <el-table-column label="所属应用id" align="center" prop="appId" />
        <el-table-column label="所属数据分类ID" align="center" prop="categoryId" />
        <el-table-column label="共享类型" align="center" prop="sharingType" />
        <el-table-column label="正向字段、逆向字段类型:0正向/1逆向" align="center" prop="fineType" />
        <el-table-column label="字段函数表达式" align="center" prop="functionExpression" />
        <el-table-column label="字段函数表达式" align="center" prop="funt" /> -->
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
       <!--     <el-button size="mini" type="text" @click="handleQueryOption(scope.row)">查看</el-button>
            <el-button size="mini" type="text" @click="handleUpdate(scope.row)"
              v-hasPermi="['datasource:dataStructuredColumn:edit']">修改</el-button> -->
            <el-button size="mini" type="text" @click="handleDelete(scope.row)"
              v-hasPermi="['datasource:dataStructuredColumn:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />
      </div>
    </div>
    <!-- 添加或修改字段元数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="数据项编码" prop="columnCode">
          <el-input v-model="form.columnCode" placeholder="请输入数据项编码" />
        </el-form-item>
        <el-form-item label="数据项编码" prop="resourceId">
          <el-input v-model="form.resourceId" placeholder="请输入数据项编码" />
        </el-form-item>
        <el-form-item label="数据项编码" prop="name">
          <el-input v-model="form.name" placeholder="请输入数据项编码" />
        </el-form-item>
        <el-form-item label="数据项编码" prop="displayName">
          <el-input v-model="form.displayName" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="字段类型" prop="typeName">
          <el-input v-model="form.typeName" placeholder="请输入字段类型" />
        </el-form-item>
        <el-form-item label="长度" prop="length">
          <el-input v-model="form.length" placeholder="请输入长度" />
        </el-form-item>
        <el-form-item label="精度" prop="precision">
          <el-input v-model="form.precision" placeholder="请输入精度" />
        </el-form-item>
        <el-form-item label="精度" prop="defaultValue">
          <el-input v-model="form.defaultValue" placeholder="请输入精度" />
        </el-form-item>
        <el-form-item label="是否为外键，1是，0否" prop="isForeignKey">
          <el-input v-model="form.isForeignKey" placeholder="请输入是否为外键，1是，0否" />
        </el-form-item>
        <el-form-item label="是否为主键，1是，0否" prop="isPrimaryKey">
          <el-input v-model="form.isPrimaryKey" placeholder="请输入是否为主键，1是，0否" />
        </el-form-item>
        <el-form-item label="是否允许为空，1是，0否" prop="isNullable">
          <el-input v-model="form.isNullable" placeholder="请输入是否允许为空，1是，0否" />
        </el-form-item>
        <el-form-item label="备注" prop="comment">
          <el-input v-model="form.comment" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="排序" prop="order">
          <el-input v-model="form.order" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="是否有效。1有效，0已删除" prop="visible">
          <el-input v-model="form.visible" placeholder="请输入是否有效。1有效，0已删除" />
        </el-form-item>
        <el-form-item label="数值类型，1百分数；2数值，3文本" prop="valueType">
          <el-select v-model="form.valueType" placeholder="请选择数值类型，1百分数；2数值，3文本">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="默认聚合函数" prop="defaultAggFuncation">
          <el-input v-model="form.defaultAggFuncation" placeholder="请输入默认聚合函数" />
        </el-form-item>
        <el-form-item label="数据分析类型：1指标，2维度" prop="dataAnalysisType">
          <el-select v-model="form.dataAnalysisType" placeholder="请选择数据分析类型：1指标，2维度">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="是否查询条件字段，1是，0否" prop="isSearchCondition">
          <el-input v-model="form.isSearchCondition" placeholder="请输入是否查询条件字段，1是，0否" />
        </el-form-item>
        <el-form-item label="查询匹配格式：1等值匹配；2模糊匹配；3正则表达式匹配；4样本匹配" prop="searchFormat">
          <el-input v-model="form.searchFormat" placeholder="请输入查询匹配格式：1等值匹配；2模糊匹配；3正则表达式匹配；4样本匹配" />
        </el-form-item>
        <el-form-item label="字段的数据分级" prop="securityGradeId">
          <el-input v-model="form.securityGradeId" placeholder="请输入字段的数据分级" />
        </el-form-item>
        <el-form-item label="加密类型" prop="encryptRuleId">
          <el-input v-model="form.encryptRuleId" placeholder="请输入加密类型" />
        </el-form-item>
        <el-form-item label="脱敏规则Id" prop="desensitizedRuleId">
          <el-input v-model="form.desensitizedRuleId" placeholder="请输入脱敏规则Id" />
        </el-form-item>
        <el-form-item label="对应的标准代码ID" prop="standardCodeId">
          <el-input v-model="form.standardCodeId" placeholder="请输入对应的标准代码ID" />
        </el-form-item>
        <el-form-item label="显示格式" prop="displayFormat">
          <el-input v-model="form.displayFormat" placeholder="请输入显示格式" />
        </el-form-item>
        <el-form-item label="类型分类NUMBER、STRING、DATE、BINARY" prop="typeClassify">
          <el-input v-model="form.typeClassify" placeholder="请输入类型分类NUMBER、STRING、DATE、BINARY" />
        </el-form-item>
        <el-form-item label="同义词名称，多个用英文逗号间隔" prop="sameName">
          <el-input v-model="form.sameName" placeholder="请输入同义词名称，多个用英文逗号间隔" />
        </el-form-item>
        <el-form-item label="关联的字典资源  -- 废除" prop="dictResourceId">
          <el-input v-model="form.dictResourceId" placeholder="请输入关联的字典资源  -- 废除" />
        </el-form-item>
        <el-form-item label="字典资源名称" prop="dictResourceName">
          <el-input v-model="form.dictResourceName" placeholder="请输入字典资源名称" />
        </el-form-item>
        <el-form-item label="字典资源关系" prop="dictRelationId">
          <el-input v-model="form.dictRelationId" placeholder="请输入字典资源关系" />
        </el-form-item>
        <el-form-item label="列类型，对应字典表column_type" prop="columnType">
          <el-select v-model="form.columnType" placeholder="请选择列类型，对应字典表column_type">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="外部链接" prop="externalLink">
          <el-input v-model="form.externalLink" placeholder="请输入外部链接" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="showOrder">
          <el-input v-model="form.showOrder" placeholder="请输入显示顺序" />
        </el-form-item>
        <el-form-item label="选择方式：1、下拉框 2、弹出框" prop="choiceMode">
          <el-input v-model="form.choiceMode" placeholder="请输入选择方式：1、下拉框 2、弹出框" />
        </el-form-item>
        <el-form-item label="是否自增长主键" prop="increment">
          <el-input v-model="form.increment" placeholder="请输入是否自增长主键" />
        </el-form-item>
        <el-form-item label="非物理字段的默认值，是一个JSON格式的简单数值或者是一个环境变量" prop="logicDefault">
          <el-input v-model="form.logicDefault" placeholder="请输入非物理字段的默认值，是一个JSON格式的简单数值或者是一个环境变量" />
        </el-form-item>
        <el-form-item label="非物理字段的固定值，是一个JSON格式的简单数值或者是一个环境变量" prop="logicValue">
          <el-input v-model="form.logicValue" placeholder="请输入非物理字段的固定值，是一个JSON格式的简单数值或者是一个环境变量" />
        </el-form-item>
        <el-form-item label="字段是否可更新，默认为1" prop="updatable">
          <el-input v-model="form.updatable" placeholder="请输入字段是否可更新，默认为1" />
        </el-form-item>
        <el-form-item label="是否持久化，默认为1，设置为0表示这是一个逻辑字段" prop="persist">
          <el-input v-model="form.persist" placeholder="请输入是否持久化，默认为1，设置为0表示这是一个逻辑字段" />
        </el-form-item>
        <el-form-item label="字段是否不对外公开，默认为0" prop="internal">
          <el-input v-model="form.internal" placeholder="请输入字段是否不对外公开，默认为0" />
        </el-form-item>
        <el-form-item label="字段是否可被排序，默认为0" prop="sortable">
          <el-input v-model="form.sortable" placeholder="请输入字段是否可被排序，默认为0" />
        </el-form-item>
        <el-form-item label="字段是否可被作为过滤条件，默认为0" prop="filterable">
          <el-input v-model="form.filterable" placeholder="请输入字段是否可被作为过滤条件，默认为0" />
        </el-form-item>
        <el-form-item label="字段选项集名称" prop="options">
          <el-input v-model="form.options" placeholder="请输入字段选项集名称" />
        </el-form-item>
        <el-form-item label="字段格式" prop="format">
          <el-input v-model="form.format" placeholder="请输入字段格式" />
        </el-form-item>
        <el-form-item label="字段验证表达式" prop="validate">
          <el-input v-model="form.validate" placeholder="请输入字段验证表达式" />
        </el-form-item>
        <el-form-item label="字段输入类型" prop="input">
          <el-input v-model="form.input" placeholder="请输入字段输入类型" />
        </el-form-item>
        <el-form-item label="指定数据来源于关系实体字段，如from relation_name.field" prop="from">
          <el-input v-model="form.from" placeholder="请输入指定数据来源于关系实体字段，如from relation_name.field" />
        </el-form-item>
        <el-form-item label="扩展属性字段，可以存储JSON，由使用方决定如何使用" prop="extension">
          <el-input v-model="form.extension" placeholder="请输入扩展属性字段，可以存储JSON，由使用方决定如何使用" />
        </el-form-item>
        <el-form-item label="驼峰名" prop="camelName">
          <el-input v-model="form.camelName" placeholder="请输入驼峰名" />
        </el-form-item>
        <el-form-item label="默认有权限" prop="defaultPermission">
          <el-input v-model="form.defaultPermission" placeholder="请输入默认有权限" />
        </el-form-item>
        <el-form-item label="密级" prop="secretLevel">
          <el-input v-model="form.secretLevel" placeholder="请输入密级" />
        </el-form-item>
        <el-form-item label="当前物理字段名" prop="oldColumnName">
          <el-input v-model="form.oldColumnName" placeholder="请输入当前物理字段名" />
        </el-form-item>
        <el-form-item label="是否知识抽取" prop="isExtractKg">
          <el-input v-model="form.isExtractKg" placeholder="请输入是否知识抽取" />
        </el-form-item>
        <el-form-item label="图谱是否隐藏" prop="isGraphHidden">
          <el-input v-model="form.isGraphHidden" placeholder="请输入图谱是否隐藏" />
        </el-form-item>
        <el-form-item label="状态：designing 设计中，materialization 已物化">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="映射字段ID" prop="mappingColumnId">
          <el-input v-model="form.mappingColumnId" placeholder="请输入映射字段ID" />
        </el-form-item>
        <el-form-item label="字段存在类型：物理还是逻辑" prop="columnExistenceType">
          <el-select v-model="form.columnExistenceType" placeholder="请选择字段存在类型：物理还是逻辑">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="业务字段还是技术字段" prop="columnUseType">
          <el-select v-model="form.columnUseType" placeholder="请选择业务字段还是技术字段">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="逻辑字段映射的物理字段" prop="physicalColumnId">
          <el-input v-model="form.physicalColumnId" placeholder="请输入逻辑字段映射的物理字段" />
        </el-form-item>
        <el-form-item label="继承" prop="extendFrom">
          <el-input v-model="form.extendFrom" placeholder="请输入继承" />
        </el-form-item>
        <el-form-item label="所属应用id" prop="appId">
          <el-input v-model="form.appId" placeholder="请输入所属应用id" />
        </el-form-item>
        <el-form-item label="所属数据分类ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入所属数据分类ID" />
        </el-form-item>
        <el-form-item label="共享类型" prop="sharingType">
          <el-select v-model="form.sharingType" placeholder="请选择共享类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="正向字段、逆向字段类型:0正向/1逆向" prop="fineType">
          <el-select v-model="form.fineType" placeholder="请选择正向字段、逆向字段类型:0正向/1逆向">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="字段函数表达式" prop="functionExpression">
          <el-input v-model="form.functionExpression" placeholder="请输入字段函数表达式" />
        </el-form-item>
        <el-form-item label="字段函数表达式" prop="funt">
          <el-input v-model="form.funt" placeholder="请输入字段函数表达式" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看字段元数据对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
        <el-form-item label="数据项编码:" prop="columnCode">
          {{form.columnCode}}
        </el-form-item>
        <el-form-item label="数据项编码:" prop="resourceId">
          {{form.resourceId}}
        </el-form-item>
        <el-form-item label="数据项编码:" prop="name">
          {{form.name}}
        </el-form-item>
        <el-form-item label="数据项编码:" prop="displayName">
          {{form.displayName}}
        </el-form-item>
        <el-form-item label="字段类型:" prop="typeName">
          {{form.typeName}}
        </el-form-item>
        <el-form-item label="长度:" prop="length">
          {{form.length}}
        </el-form-item>
        <el-form-item label="精度:" prop="precision">
          {{form.precision}}
        </el-form-item>
        <el-form-item label="精度:" prop="defaultValue">
          {{form.defaultValue}}
        </el-form-item>
        <el-form-item label="是否为外键，1是，0否:" prop="isForeignKey">
          {{form.isForeignKey}}
        </el-form-item>
        <el-form-item label="是否为主键，1是，0否:" prop="isPrimaryKey">
          {{form.isPrimaryKey}}
        </el-form-item>
        <el-form-item label="是否允许为空，1是，0否:" prop="isNullable">
          {{form.isNullable}}
        </el-form-item>
        <el-form-item label="备注:" prop="comment">
          {{form.comment}}
        </el-form-item>
        <el-form-item label="排序:" prop="order">
          {{form.order}}
        </el-form-item>
        <el-form-item label="是否有效。1有效，0已删除:" prop="visible">
          {{form.visible}}
        </el-form-item>
        <el-form-item label="数值类型，1百分数；2数值，3文本:" prop="valueType">
          {{form.valueType | FieldFilter(valueTypeOptions)}}
        </el-form-item>
        <el-form-item label="默认聚合函数:" prop="defaultAggFuncation">
          {{form.defaultAggFuncation}}
        </el-form-item>
        <el-form-item label="数据分析类型：1指标，2维度:" prop="dataAnalysisType">
          {{form.dataAnalysisType | FieldFilter(dataAnalysisTypeOptions)}}
        </el-form-item>
        <el-form-item label="单位:" prop="unit">
          {{form.unit}}
        </el-form-item>
        <el-form-item label="是否查询条件字段，1是，0否:" prop="isSearchCondition">
          {{form.isSearchCondition}}
        </el-form-item>
        <el-form-item label="查询匹配格式：1等值匹配；2模糊匹配；3正则表达式匹配；4样本匹配:" prop="searchFormat">
          {{form.searchFormat}}
        </el-form-item>
        <el-form-item label="字段的数据分级:" prop="securityGradeId">
          {{form.securityGradeId}}
        </el-form-item>
        <el-form-item label="加密类型:" prop="encryptRuleId">
          {{form.encryptRuleId}}
        </el-form-item>
        <el-form-item label="脱敏规则Id:" prop="desensitizedRuleId">
          {{form.desensitizedRuleId}}
        </el-form-item>
        <el-form-item label="对应的标准代码ID:" prop="standardCodeId">
          {{form.standardCodeId}}
        </el-form-item>
        <el-form-item label="显示格式:" prop="displayFormat">
          {{form.displayFormat}}
        </el-form-item>
        <el-form-item label="类型分类NUMBER、STRING、DATE、BINARY:" prop="typeClassify">
          {{form.typeClassify}}
        </el-form-item>
        <el-form-item label="同义词名称，多个用英文逗号间隔:" prop="sameName">
          {{form.sameName}}
        </el-form-item>
        <el-form-item label="关联的字典资源  -- 废除:" prop="dictResourceId">
          {{form.dictResourceId}}
        </el-form-item>
        <el-form-item label="字典资源名称:" prop="dictResourceName">
          {{form.dictResourceName}}
        </el-form-item>
        <el-form-item label="字典资源关系:" prop="dictRelationId">
          {{form.dictRelationId}}
        </el-form-item>
        <el-form-item label="列类型，对应字典表column_type:" prop="columnType">
          {{form.columnType | FieldFilter(columnTypeOptions)}}
        </el-form-item>
        <el-form-item label="外部链接:" prop="externalLink">
          {{form.externalLink}}
        </el-form-item>
        <el-form-item label="显示顺序:" prop="showOrder">
          {{form.showOrder}}
        </el-form-item>
        <el-form-item label="选择方式：1、下拉框 2、弹出框:" prop="choiceMode">
          {{form.choiceMode}}
        </el-form-item>
        <el-form-item label="是否自增长主键:" prop="increment">
          {{form.increment}}
        </el-form-item>
        <el-form-item label="非物理字段的默认值，是一个JSON格式的简单数值或者是一个环境变量:" prop="logicDefault">
          {{form.logicDefault}}
        </el-form-item>
        <el-form-item label="非物理字段的固定值，是一个JSON格式的简单数值或者是一个环境变量:" prop="logicValue">
          {{form.logicValue}}
        </el-form-item>
        <el-form-item label="字段是否可更新，默认为1:" prop="updatable">
          {{form.updatable}}
        </el-form-item>
        <el-form-item label="是否持久化，默认为1，设置为0表示这是一个逻辑字段:" prop="persist">
          {{form.persist}}
        </el-form-item>
        <el-form-item label="字段是否不对外公开，默认为0:" prop="internal">
          {{form.internal}}
        </el-form-item>
        <el-form-item label="字段是否可被排序，默认为0:" prop="sortable">
          {{form.sortable}}
        </el-form-item>
        <el-form-item label="字段是否可被作为过滤条件，默认为0:" prop="filterable">
          {{form.filterable}}
        </el-form-item>
        <el-form-item label="字段选项集名称:" prop="options">
          {{form.options}}
        </el-form-item>
        <el-form-item label="字段格式:" prop="format">
          {{form.format}}
        </el-form-item>
        <el-form-item label="字段验证表达式:" prop="validate">
          {{form.validate}}
        </el-form-item>
        <el-form-item label="字段输入类型:" prop="input">
          {{form.input}}
        </el-form-item>
        <el-form-item label="指定数据来源于关系实体字段，如from relation_name.field:" prop="from">
          {{form.from}}
        </el-form-item>
        <el-form-item label="扩展属性字段，可以存储JSON，由使用方决定如何使用:" prop="extension">
          {{form.extension}}
        </el-form-item>
        <el-form-item label="驼峰名:" prop="camelName">
          {{form.camelName}}
        </el-form-item>
        <el-form-item label="默认有权限:" prop="defaultPermission">
          {{form.defaultPermission}}
        </el-form-item>
        <el-form-item label="密级:" prop="secretLevel">
          {{form.secretLevel}}
        </el-form-item>
        <el-form-item label="当前物理字段名:" prop="oldColumnName">
          {{form.oldColumnName}}
        </el-form-item>
        <el-form-item label="是否知识抽取:" prop="isExtractKg">
          {{form.isExtractKg}}
        </el-form-item>
        <el-form-item label="图谱是否隐藏:" prop="isGraphHidden">
          {{form.isGraphHidden}}
        </el-form-item>
        <el-form-item label="映射字段ID:" prop="mappingColumnId">
          {{form.mappingColumnId}}
        </el-form-item>
        <el-form-item label="字段存在类型：物理还是逻辑:" prop="columnExistenceType">
          {{form.columnExistenceType | FieldFilter(columnExistenceTypeOptions)}}
        </el-form-item>
        <el-form-item label="业务字段还是技术字段:" prop="columnUseType">
          {{form.columnUseType | FieldFilter(columnUseTypeOptions)}}
        </el-form-item>
        <el-form-item label="逻辑字段映射的物理字段:" prop="physicalColumnId">
          {{form.physicalColumnId}}
        </el-form-item>
        <el-form-item label="继承:" prop="extendFrom">
          {{form.extendFrom}}
        </el-form-item>
        <el-form-item label="所属应用id:" prop="appId">
          {{form.appId}}
        </el-form-item>
        <el-form-item label="所属数据分类ID:" prop="categoryId">
          {{form.categoryId}}
        </el-form-item>
        <el-form-item label="共享类型:" prop="sharingType">
          {{form.sharingType | FieldFilter(sharingTypeOptions)}}
        </el-form-item>
        <el-form-item label="正向字段、逆向字段类型:0正向/1逆向:" prop="fineType">
          {{form.fineType | FieldFilter(fineTypeOptions)}}
        </el-form-item>
        <el-form-item label="字段函数表达式:" prop="functionExpression">
          {{form.functionExpression}}
        </el-form-item>
        <el-form-item label="字段函数表达式:" prop="funt">
          {{form.funt}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 字段元数据导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
        <div class="el-upload__tip" style="color:red" slot="tip">提示：仅允许导入“xls”或“xlsx”格式文件！</div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    getToken
  } from "@/utils/auth";
  import {
    listDataStructuredColumn,
    getDataStructuredColumn,
    delDataStructuredColumn,
    addDataStructuredColumn,
    updateDataStructuredColumn,
    exportDataStructuredColumn,
    importTemplate,
    disableEnable
  } from "@/api/datasource/dataStructuredColumn";


  import ExpandCollapse from "@/components/ExpandCollapse/index";
  import {
    formatDate
  } from '@/utils/index';
  export default {
    name: "DataStructuredColumn",
    components: {
      ExpandCollapse
    },
    data() {
      return {
        disabled: false,
        // 遮罩层
        loading: true,
        // 是否显示查看弹出层
        isOpenQuery: false,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 字段元数据表格数据
        dataStructuredColumnList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 用户导入参数
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: "",
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的用户数据
          updateSupport: 0,
          // 设置上传的请求头部
          headers: {
            Authorization: "Bearer " + getToken()
          },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/datasource/dataStructuredColumn/importData",
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          columnCode: null,
          resourceId: null,
          name: null,
          displayName: null,
          typeName: null,
          length: null,
          precision: null,
          defaultValue: null,
          isForeignKey: null,
          isPrimaryKey: null,
          isNullable: null,
          comment: null,
          order: null,
          visible: null,
          valueType: null,
          defaultAggFuncation: null,
          dataAnalysisType: null,
          unit: null,
          isSearchCondition: null,
          searchFormat: null,
          securityGradeId: null,
          encryptRuleId: null,
          desensitizedRuleId: null,
          standardCodeId: null,
          displayFormat: null,
          typeClassify: null,
          sameName: null,
          dictResourceId: null,
          dictResourceName: null,
          dictRelationId: null,
          columnType: null,
          externalLink: null,
          showOrder: null,
          choiceMode: null,
          increment: null,
          logicDefault: null,
          logicValue: null,
          updatable: null,
          persist: null,
          internal: null,
          sortable: null,
          filterable: null,
          options: null,
          format: null,
          validate: null,
          input: null,
          from: null,
          extension: null,
          camelName: null,
          defaultPermission: null,
          secretLevel: null,
          oldColumnName: null,
          isExtractKg: null,
          isGraphHidden: null,
          status: null,
          mappingColumnId: null,
          columnExistenceType: null,
          columnUseType: null,
          physicalColumnId: null,
          extendFrom: null,
          appId: null,
          categoryId: null,
          sharingType: null,
          fineType: null,
          functionExpression: null,
          funt: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          resourceId: [{
            required: true,
            message: "字段函数表达式不能为空",
            trigger: "blur"
          }],
          status: [{
            required: true,
            message: "状态：designing 设计中，materialization 已物化不能为空",
            trigger: "blur"
          }],
        }
      };
    },
    filters: {
      filterDate(val) {
        return formatDate(val);
      },
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询字段元数据列表 */
      getList() {
        this.loading = true;
        listDataStructuredColumn(this.queryParams).then(response => {
          this.dataStructuredColumnList = response.rows || [];
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          columnId: null,
          columnCode: null,
          resourceId: null,
          name: null,
          displayName: null,
          typeName: null,
          length: null,
          precision: null,
          defaultValue: null,
          isForeignKey: null,
          isPrimaryKey: null,
          isNullable: null,
          comment: null,
          order: null,
          visible: null,
          valueType: null,
          defaultAggFuncation: null,
          dataAnalysisType: null,
          unit: null,
          isSearchCondition: null,
          searchFormat: null,
          securityGradeId: null,
          encryptRuleId: null,
          desensitizedRuleId: null,
          standardCodeId: null,
          displayFormat: null,
          typeClassify: null,
          sameName: null,
          dictResourceId: null,
          dictResourceName: null,
          dictRelationId: null,
          columnType: null,
          externalLink: null,
          showOrder: null,
          choiceMode: null,
          increment: null,
          logicDefault: null,
          logicValue: null,
          updatable: null,
          persist: null,
          internal: null,
          sortable: null,
          filterable: null,
          options: null,
          format: null,
          validate: null,
          input: null,
          from: null,
          extension: null,
          camelName: null,
          defaultPermission: null,
          secretLevel: null,
          oldColumnName: null,
          isExtractKg: null,
          isGraphHidden: null,
          status: "0",
          mappingColumnId: null,
          columnExistenceType: null,
          columnUseType: null,
          physicalColumnId: null,
          extendFrom: null,
          appId: null,
          categoryId: null,
          sharingType: null,
          fineType: null,
          functionExpression: null,
          funt: null
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.columnId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加字段元数据";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const columnId = row.columnId || this.ids
        getDataStructuredColumn(columnId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改字段元数据";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const columnId = row.columnId || this.ids
        getDataStructuredColumn(columnId).then(response => {
          this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看字段元数据";
        });
      },
      /** 提交按钮 */
      submitForm() {
        if (this.disabled) {
          this.open = false;
          return
        }
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.columnId != null) {
              updateDataStructuredColumn(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addDataStructuredColumn(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const columnIds = row.columnId || this.ids;
        this.$confirm('是否确认删除字段元数据编号为"' + columnIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDataStructuredColumn(columnIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 禁用或启用操作*/
      disabledBtnClick(type) {
        var ids = this.ids || [];
        this.$confirm('是否确认' + (type === 0 ? "启用" : "禁用") + '?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return disableEnable({
            ids: ids.toString(),
            disableEnableState: type
          });
        }).then(() => {
          this.getList();
          this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有字段元数据数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDataStructuredColumn(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "字段元数据数据导入";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then((response) => {
          this.download(response.msg);
        });
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert(response.msg, "导入结果", {
          dangerouslyUseHTMLString: true
        });
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      },
    }
  };
</script>



<style lang="scss" scoped>
  .app-container {
    background: #F5F7F9;
  }

  .search-folder {
    // margin: 0 -16px;
    // padding: 0 16px;
    padding: 15px;
    position: relative;
    height: 60px;
    overflow-y: hidden;
    display: flex;
    background: #fff;
    border-radius: 2px;

    .ivu-icon {
      // position: absolute;
      left: 2px;
      top: 6px;
      color: #3a89da;
      z-index: 10;
      font-size: 18px;
      cursor: pointer;

      &:hover {
        opacity: 0.7;
      }
    }

    &.folder-open {
      height: auto;
    }

    &-utils {
      display: flex;
      height: 32px;
      align-items: center;
      margin-left: 10px;

      p {
        cursor: pointer;
        width: 32px;
        margin-left: 10px;
        font-size: 14px;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: #3a89da;
      }
    }
  }

  .utlsBtn {
    /deep/ .el-button--mini {
      padding: 9px 15px;
    }

    .normalBtn {
      /deep/ .el-button--mini {
        padding: 8px;
      }
    }
  }

  .container {
    padding: 12px;
    background: #fff;
    border-radius: 2px;

    /deep/ .el-table__header-wrapper {
      th {
        background: rgba(45, 140, 240, 0.04) !important;
        font-size: 14px;
        font-family: PingFangSC-Semibold, PingFang SC;
        font-weight: 600;
        color: rgba(23, 35, 61, 0.75);
      }
    }

    .pageBox {
      height: 32px;

      .pagination-container {
        padding: 0 !important;
        margin: 0;
        height: 32px;
        margin-top: 12px;

        /deep/ .number,
        /deep/ .btn-prev,
        /deep/ .btn-next {
          width: 32px;
          height: 32px;
          line-height: 31px;
          background: #fff;
          border-radius: 4px;
          font-size: 14px;
          font-family: HelveticaNeue;
          color: rgba(0, 0, 0, 0.65);
          border: 1px solid rgba(23, 35, 61, 0.15);
          font-weight: normal;
        }

        /deep/ .el-input__inner {
          height: 32px;
          line-height: 32px;
        }
      }

      /deep/ .el-pagination__total {
        line-height: 32px;
      }
    }
  }

  /deep/ .el-form-item__label {
    font-size: 14px;
    font-family: PingFangSC-Regular, PingFang SC;
    font-weight: 400;
    color: rgba(23, 35, 61, 0.75);
  }
</style>
