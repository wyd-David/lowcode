<template>
  <div class="app-container">
    <div class="search-folder mb12" ref="folder" :class="{'folder-open': isOpen}">

      <div style="flex:1">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
          <el-form-item label="登录ID" prop="loginid">
            <el-input
              v-model="queryParams.loginid"
              placeholder="请输入登录ID"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="ERP号" prop="erpid">
            <el-input
              v-model="queryParams.erpid"
              placeholder="ERP号"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="用户中文名" prop="username">
            <el-input
              v-model="queryParams.username"
              placeholder="请输入用户中文名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <!-- <el-form-item label="用户所属部门id" prop="dpid">
            <el-input
              v-model="queryParams.dpid"
              placeholder="请输入用户所属部门id"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="mobile">
            <el-input
              v-model="queryParams.mobile"
              placeholder="请输入手机号码"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable size="small">
              <el-option label="请选择字典生成" value=""/>
            </el-select>
          </el-form-item> -->
          <el-form-item label="岗位名称" prop="positioncat">
            <el-input
              v-model="queryParams.positioncat"
              placeholder="请输入岗位名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-form>
      </div>

      <div class="search-folder-utils">
        <el-button type="primary" size="mini" @click="handleQuery">搜索</el-button>
        <el-button size="mini" @click="resetQuery">重置</el-button>
        <p v-if="height > 34 && isShowOpen" @click="onHandle">{{isOpen ? '收起' : '展开'}}</p>
        <span v-if="height > 34 && isShowOpen">
					<i class="el-icon-arrow-up ivu-icon" @click="onHandle" v-if="isOpen"></i>
					<i class="el-icon-arrow-down ivu-icon" @click="onHandle" v-else></i>
				 </span>
      </div>
    </div>
    <div class="container">
      <el-row :gutter="10" class="mb12 utlsBtn">
        <!--<el-col :span="1.5">-->
          <!--<el-button-->
            <!--type="primary"-->
            <!--icon="el-icon-plus"-->
            <!--size="mini"-->
            <!--@click="handleAdd"-->
            <!--v-hasPermi="['system:syncUser:add']"-->
          <!--&gt;新增-->
          <!--</el-button>-->
        <!--</el-col>-->
        <!--<el-col :span="1.5">-->
          <!--<el-button-->
            <!--type="primary"-->
            <!--icon="el-icon-edit"-->
            <!--size="mini"-->
            <!--:disabled="single"-->
            <!--@click="handleUpdate"-->
            <!--v-hasPermi="['system:syncUser:edit']"-->
          <!--&gt;修改-->
          <!--</el-button>-->
        <!--</el-col>-->
        <!--<el-col :span="1.5">-->
          <!-- <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:syncUser:remove']">删除
          </el-button> -->
        <!--</el-col>-->
        <!--<el-col :span="1.5">-->
          <!--<el-button-->
            <!--type="primary"-->
            <!--icon="el-icon-lock"-->
            <!--size="mini"-->
            <!--:disabled="multiple"-->
            <!--@click="disabledBtnClick(1)"-->
            <!--v-hasPermi="['system:syncUser:remove']"-->
          <!--&gt;禁用-->
          <!--</el-button>-->
        <!--</el-col>-->
        <!--<el-col :span="1.5">-->
          <!--<el-button-->
            <!--type="primary"-->
            <!--icon="el-icon-unlock"-->
            <!--size="mini"-->
            <!--:disabled="multiple"-->
            <!--@click="disabledBtnClick(0)"-->
            <!--v-hasPermi="['system:syncUser:remove']"-->
          <!--&gt;启用-->
          <!--</el-button>-->
        <!--</el-col>-->
        <!--<el-col :span="1.5">-->
          <!--<el-button-->
            <!--type="default"-->
            <!--icon="el-icon-upload"-->
            <!--size="mini"-->
            <!--@click="openImportTable"-->
            <!--v-hasPermi="['system:syncUser:importData']"-->
          <!--&gt;导入-->
          <!--</el-button>-->
        <!--</el-col>-->
        <el-col :span="1.5">
          <el-button
            type="default"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:syncUser:importData']"
          >导出
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" class="normalBtn" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="syncUserList" @selection-change="handleSelectionChange">
        <!-- <el-table-column type="selection" width="55" align="left"/> -->
        <el-table-column label="ID" align="center" prop="id"/>
        <el-table-column label="登录ID" width="120px" align="center" prop="loginid"/>
        <el-table-column label="ERP号" width="120px" align="center" prop="erpid"/>
        <el-table-column label="用户中文名" width="120px" align="center" prop="username"/>
        <el-table-column label="手机号码" width="120px" align="center" prop="mobile"/>
        <el-table-column label="性别" width="80px" align="center" prop="sex"/>
        <el-table-column label="是否为主部门" width="120px" align="center" prop="maindepartment"/>
        <el-table-column label="员工类别" width="120px" align="center" prop="stafftype"/>
        <el-table-column label="所在群组" width="150px" align="center" prop="dpdn"/>
        <el-table-column label="唯一名称" width="150px"  align="center" prop="dn"/>
        <el-table-column label="岗位名称" width="150px"  align="center" prop="positioncat"/>
        <el-table-column label="操作" width="120px" fixed="right" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              @click="handleQueryOption(scope.row)"
            >查看
            </el-button>
            <!-- <el-button
              size="mini"
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:syncUser:edit']">修改
            </el-button> -->
            <el-button
              size="mini"
              type="text"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:syncUser:remove']">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox">
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </div>
    </div>
    <!-- 添加或修改同步用户信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" :disabled="disabled">
        <el-form-item label="UUID" prop="userid">
          <el-input v-model="form.userid" placeholder="请输入UUID,主键"/>
        </el-form-item>
        <el-form-item label="登录ID" prop="loginid">
          <el-input v-model="form.loginid" placeholder="请输入用户的登录ID"/>
        </el-form-item>
        <el-form-item label="ERP号" prop="erpid">
          <el-input v-model="form.erpid" placeholder="ERP号"/>
        </el-form-item>
        <el-form-item label="用户中文名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户中文名"/>
        </el-form-item>
        <el-form-item label="用户中文名拼音" prop="initialcharacter">
          <el-input v-model="form.initialcharacter" placeholder="请输入用户中文名拼音"/>
        </el-form-item>
        <el-form-item label="用户所属部门id" prop="dpid">
          <el-input v-model="form.dpid" placeholder="请输入用户所属部门id"/>
        </el-form-item>
        <el-form-item label="座机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入座机号"/>
        </el-form-item>
        <el-form-item label="直线座机号" prop="directphone">
          <el-input v-model="form.directphone" placeholder="请输入直线座机号"/>
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入手机号码"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="mailbox">
          <el-input v-model="form.mailbox" placeholder="请输入邮箱"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="(在部门中的)序号" prop="serialnumber">
          <el-input v-model="form.serialnumber" placeholder="请输入(在部门中的)序号"/>
        </el-form-item>
        <el-form-item label="QQ 或 MSN" prop="qqormsn">
          <el-input v-model="form.qqormsn" placeholder="请输入QQ 或 MSN"/>
        </el-form-item>
        <el-form-item label="工作地点" prop="workingplace">
          <el-input v-model="form.workingplace" type="textarea" maxlength="200" show-word-limit placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="职位" prop="post">
          <el-input v-model="form.post" placeholder="请输入职位"/>
        </el-form-item>
        <el-form-item label="职级" prop="rank">
          <el-input v-model="form.rank" placeholder="请输入职级"/>
        </el-form-item>
        <el-form-item label="生日" prop="birthday">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.birthday"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择生日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="入职日期" prop="employeddate">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.employeddate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择入职日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="离职日期" prop="dimissiondate">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.dimissiondate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择离职日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注(在数据库中对本用户的备注)" prop="comments">
          <el-input v-model="form.comments" placeholder="请输入备注(在数据库中对本用户的备注)"/>
        </el-form-item>
        <el-form-item label="个人相片" prop="photograph">
          <el-input v-model="form.photograph" placeholder="请输入个人相片"/>
        </el-form-item>
        <el-form-item label="是否为主部门" prop="maindepartment">
          <el-input v-model="form.maindepartment" placeholder="请输入是否为主部门"/>
        </el-form-item>
        <el-form-item label="上级领导(为ERP保留的扩展)" prop="superiorleader">
          <el-input v-model="form.superiorleader" placeholder="请输入上级领导(为ERP保留的扩展)"/>
        </el-form-item>
        <el-form-item label="员工类别(正式员工,临时员工,营销人员等)" prop="stafftype">
          <el-select v-model="form.stafftype" placeholder="请选择员工类别(正式员工,临时员工,营销人员等)">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="用户的个人签名(签名留言)" prop="idiograph">
          <el-input v-model="form.idiograph" placeholder="请输入用户的个人签名(签名留言)"/>
        </el-form-item>
        <el-form-item label="个人备注(信息)" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入个人备注(信息)"/>
        </el-form-item>
        <el-form-item label="Domino Ldap中记录的UNID" prop="oaid">
          <el-input v-model="form.oaid" placeholder="请输入Domino Ldap中记录的UNID"/>
        </el-form-item>
        <el-form-item label="Domino Ldap中当前记录所在群组" prop="dpdn">
          <el-input v-model="form.dpdn" placeholder="请输入Domino Ldap中当前记录所在群组"/>
        </el-form-item>
        <el-form-item label="在Ldap中的记录显示名" prop="displayname">
          <el-input v-model="form.displayname" placeholder="请输入在Ldap中的记录显示名"/>
        </el-form-item>
        <el-form-item label="在Ldap中的记录唯一名称" prop="dn">
          <el-input v-model="form.dn" placeholder="请输入在Ldap中的记录唯一名称"/>
        </el-form-item>
        <el-form-item label="门户Tam创建标识(外部系统无需使用)" prop="tamuid">
          <el-input v-model="form.tamuid" placeholder="请输入门户Tam创建标识(外部系统无需使用)"/>
        </el-form-item>
        <el-form-item label="用户邮箱服务器所在" prop="oamailserver">
          <el-input v-model="form.oamailserver" placeholder="请输入用户邮箱服务器所在"/>
        </el-form-item>
        <el-form-item label="Domino邮件库名称" prop="oamailfile">
          <el-input v-model="form.oamailfile" placeholder="请输入Domino邮件库名称"/>
        </el-form-item>
        <el-form-item label="OA中用户的描述备注" prop="oacomment">
          <el-input v-model="form.oacomment" placeholder="请输入OA中用户的描述备注"/>
        </el-form-item>
        <el-form-item label="用户身份证号" prop="nationalid">
          <el-input v-model="form.nationalid" placeholder="请输入用户身份证号"/>
        </el-form-item>
        <el-form-item label="统一人员编码" prop="uniusercode">
          <el-input v-model="form.uniusercode" placeholder="请输入统一人员编码"/>
        </el-form-item>
        <el-form-item label="用户所在单位编号" prop="orgcodeofuser">
          <el-input v-model="form.orgcodeofuser" placeholder="请输入用户所在单位编号"/>
        </el-form-item>
        <el-form-item label="系统保留字段" prop="closeaccount">
          <el-input v-model="form.closeaccount" placeholder="请输入系统保留字段"/>
        </el-form-item>
        <el-form-item label="用户创建时间" prop="usercreatedate">
          <el-input v-model="form.usercreatedate" placeholder="请输入用户创建时间"/>
        </el-form-item>
        <el-form-item label="系统保留字段" prop="usermodifydate">
          <el-input v-model="form.usermodifydate" placeholder="请输入系统保留字段"/>
        </el-form-item>
        <el-form-item label="系统保留字段" prop="userdeletedate">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.userdeletedate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择系统保留字段">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="岗位类型代号" prop="positioncatcode">
          <el-input v-model="form.positioncatcode" placeholder="请输入岗位类型代号"/>
        </el-form-item>
        <el-form-item label="岗位名称" prop="positioncat">
          <el-input v-model="form.positioncat" placeholder="请输入岗位名称"/>
        </el-form-item>
        <el-form-item label="岗位" prop="position">
          <el-input v-model="form.position" placeholder="请输入岗位"/>
        </el-form-item>
        <el-form-item label="用户组织架构编号" prop="orgidofuser">
          <el-input v-model="form.orgidofuser" placeholder="请输入用户组织架构编号"/>
        </el-form-item>
        <el-form-item label="用户对应的部门唯一编号" prop="unideptcode">
          <el-input v-model="form.unideptcode" placeholder="请输入用户对应的部门唯一编号"/>
        </el-form-item>
        <el-form-item label="用户对应的部门唯一编号" prop="rid">
          <el-input v-model="form.rid" placeholder="请输入用户对应的部门唯一编号"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看同步用户信息对话框 -->
    <el-dialog :title="title" :visible.sync="isOpenQuery" width="600px" append-to-body="append-to-body">
      <el-form ref="queryform" :model="form" label-width="120px" :disabled="disabled">
        <el-form-item label="UUID,主键:" prop="userid">
          {{form.userid}}
        </el-form-item>
        <el-form-item label="用户的登录ID:" prop="loginid">
          {{form.loginid}}
        </el-form-item>
        <el-form-item label="旧的用户ID,从ERP数据中取过来:" prop="erpid">
          {{form.erpid}}
        </el-form-item>
        <el-form-item label="用户中文名:" prop="username">
          {{form.username}}
        </el-form-item>
        <el-form-item label="用户中文名拼音首字母如朱泰江为ztj:" prop="initialcharacter">
          {{form.initialcharacter}}
        </el-form-item>
        <el-form-item label="用户所属部门id:" prop="dpid">
          {{form.dpid}}
        </el-form-item>
        <el-form-item label="座机号:" prop="phone">
          {{form.phone}}
        </el-form-item>
        <el-form-item label="直线座机号:" prop="directphone">
          {{form.directphone}}
        </el-form-item>
        <el-form-item label="手机号码:" prop="mobile">
          {{form.mobile}}
        </el-form-item>
        <el-form-item label="邮箱:" prop="mailbox">
          {{form.mailbox}}
        </el-form-item>
        <el-form-item label="性别:" prop="sex">
          {{form.sex | FieldFilter(sexOptions)}}
        </el-form-item>
        <el-form-item label="(在部门中的)序号:" prop="serialnumber">
          {{form.serialnumber}}
        </el-form-item>
        <el-form-item label="QQ 或 MSN:" prop="qqormsn">
          {{form.qqormsn}}
        </el-form-item>
        <el-form-item label="工作地点:" prop="workingplace">
          {{form.workingplace}}
        </el-form-item>
        <el-form-item label="职位:" prop="post">
          {{form.post}}
        </el-form-item>
        <el-form-item label="职级:" prop="rank">
          {{form.rank}}
        </el-form-item>
        <el-form-item label="生日:" prop="birthday">
          {{form.birthday | filterDate}}
        </el-form-item>
        <el-form-item label="入职日期:" prop="employeddate">
          {{form.employeddate | filterDate}}
        </el-form-item>
        <el-form-item label="离职日期:" prop="dimissiondate">
          {{form.dimissiondate | filterDate}}
        </el-form-item>
        <el-form-item label="备注(在数据库中对本用户的备注):" prop="comments">
          {{form.comments}}
        </el-form-item>
        <el-form-item label="个人相片:" prop="photograph">
          {{form.photograph}}
        </el-form-item>
        <el-form-item label="是否为主部门:" prop="maindepartment">
          {{form.maindepartment}}
        </el-form-item>
        <el-form-item label="上级领导(为ERP保留的扩展):" prop="superiorleader">
          {{form.superiorleader}}
        </el-form-item>
        <el-form-item label="员工类别(正式员工,临时员工,营销人员等):" prop="stafftype">
          {{form.stafftype | FieldFilter(stafftypeOptions)}}
        </el-form-item>
        <el-form-item label="用户的个人签名(签名留言):" prop="idiograph">
          {{form.idiograph}}
        </el-form-item>
        <el-form-item label="个人备注(信息):" prop="remarks">
          {{form.remarks}}
        </el-form-item>
        <el-form-item label="Domino Ldap中记录的UNID:" prop="oaid">
          {{form.oaid}}
        </el-form-item>
        <el-form-item label="Domino Ldap中当前记录所在群组:" prop="dpdn">
          {{form.dpdn}}
        </el-form-item>
        <el-form-item label="在Ldap中的记录显示名:" prop="displayname">
          {{form.displayname}}
        </el-form-item>
        <el-form-item label="在Ldap中的记录唯一名称:" prop="dn">
          {{form.dn}}
        </el-form-item>
        <el-form-item label="门户Tam创建标识(外部系统无需使用):" prop="tamuid">
          {{form.tamuid}}
        </el-form-item>
        <el-form-item label="用户邮箱服务器所在:" prop="oamailserver">
          {{form.oamailserver}}
        </el-form-item>
        <el-form-item label="Domino邮件库名称:" prop="oamailfile">
          {{form.oamailfile}}
        </el-form-item>
        <el-form-item label="OA中用户的描述备注:" prop="oacomment">
          {{form.oacomment}}
        </el-form-item>
        <el-form-item label="用户身份证号:" prop="nationalid">
          {{form.nationalid}}
        </el-form-item>
        <el-form-item label="统一人员编码:" prop="uniusercode">
          {{form.uniusercode}}
        </el-form-item>
        <el-form-item label="用户所在单位编号:" prop="orgcodeofuser">
          {{form.orgcodeofuser}}
        </el-form-item>
        <el-form-item label="系统保留字段:" prop="closeaccount">
          {{form.closeaccount}}
        </el-form-item>
        <el-form-item label="用户创建时间:" prop="usercreatedate">
          {{form.usercreatedate}}
        </el-form-item>
        <el-form-item label="系统保留字段:" prop="usermodifydate">
          {{form.usermodifydate}}
        </el-form-item>
        <el-form-item label="系统保留字段:" prop="userdeletedate">
          {{form.userdeletedate | filterDate}}
        </el-form-item>
        <el-form-item label="岗位类型代号:" prop="positioncatcode">
          {{form.positioncatcode}}
        </el-form-item>
        <el-form-item label="岗位名称:" prop="positioncat">
          {{form.positioncat}}
        </el-form-item>
        <el-form-item label="岗位:" prop="position">
          {{form.position}}
        </el-form-item>
        <el-form-item label="用户组织架构编号:" prop="orgidofuser">
          {{form.orgidofuser}}
        </el-form-item>
        <el-form-item label="用户对应的部门唯一编号:" prop="unideptcode">
          {{form.unideptcode}}
        </el-form-item>
        <el-form-item label="用户对应的部门唯一编号:" prop="rid">
          {{form.rid}}
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isOpenQuery = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 同步用户信息导入对话框 -->
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
          <el-checkbox v-model="upload.updateSupport"/>
          是否更新已经存在的用户数据
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
  import {getToken} from "@/utils/auth";
  import {
    listSyncUser,
    getSyncUser,
    delSyncUser,
    addSyncUser,
    updateSyncUser,
    exportSyncUser,
    importTemplate,
    disableEnable
  } from "@/api/system/syncUser";


  import {formatDate} from '@/utils/index';

  export default {
    name: "SyncUser",
    data() {
      return {
        // 搜索栏展示配置
        height: null,
        disabled: false,
        isShowOpen: false,
        isOpen: true,
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
        // 同步用户信息表格数据
        syncUserList: [],
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
          headers: {Authorization: "Bearer " + getToken()},
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/system/syncUser/importData",
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          loginid: null,
          erpid: null,
          username: null,
          dpid: null,
          mobile: null,
          sex: null,
          positioncat: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {}
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
    mounted() {
      this.height = this.$refs.folder.clientHeight;
      if (this.height > 32) {
        this.isOpen = false;
      }
      // this.$nextTick(() => {
      //   this.height = this.$refs.folder.clientHeight;
      //   if (this.height > 34) {
      //     this.isOpen = false;
      //     this.isShowOpen = true;
      //   } else {
      //     this.isShowOpen = false;
      //   }
      // });
    },
    methods: {
      /** 查询同步用户信息列表 */
      getList() {
        this.loading = true;
        listSyncUser(this.queryParams).then(response => {
          this.syncUserList = response.rows || [];
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
          id: null,
          userid: null,
          loginid: null,
          erpid: null,
          username: null,
          initialcharacter: null,
          dpid: null,
          phone: null,
          directphone: null,
          mobile: null,
          mailbox: null,
          sex: null,
          serialnumber: null,
          qqormsn: null,
          workingplace: null,
          post: null,
          rank: null,
          birthday: null,
          employeddate: null,
          dimissiondate: null,
          status: "0",
          comments: null,
          photograph: null,
          maindepartment: null,
          superiorleader: null,
          stafftype: null,
          idiograph: null,
          remarks: null,
          oaid: null,
          dpdn: null,
          displayname: null,
          dn: null,
          tamuid: null,
          oamailserver: null,
          oamailfile: null,
          oacomment: null,
          nationalid: null,
          uniusercode: null,
          orgcodeofuser: null,
          closeaccount: null,
          usercreatedate: null,
          usermodifydate: null,
          userdeletedate: null,
          positioncatcode: null,
          positioncat: null,
          position: null,
          orgidofuser: null,
          unideptcode: null,
          rid: null
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
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.disabled = false;
        this.reset();
        this.open = true;
        this.title = "添加同步用户信息";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.disabled = false;
        this.reset();
        const id = row.id || this.ids
        getSyncUser(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改同步用户信息";
        });
      },
      /**详情按钮*/
      handleQueryOption(row) {
        // this.reset();
        const id = row.id || this.ids
        getSyncUser(id).then(response => {
          this.form = response.data;
          this.isOpenQuery = true;
          this.title = "查看同步用户信息";
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
            if (this.form.id != null) {
              updateSyncUser(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addSyncUser(this.form).then(response => {
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
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除同步用户信息编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delSyncUser(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      /** 禁用或启用操作*/
      disabledBtnClick(type) {
        var ids = this.ids || [];
        this.$confirm('是否确认' + (type === 0 ? "启用" : "禁用") + '?', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return disableEnable({ids: ids.toString(), disableEnableState: type});
        }).then(() => {
          this.getList();
          this.msgSuccess((type === 0 ? "启用" : "禁用") + "成功");
        }).catch(function () {
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        let queryParams = this.queryParams;
        queryParams.id = this.ids ? this.ids.toString() : '';
        this.$confirm('是否确认导出同步用户信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportSyncUser(queryParams);
        }).then(response => {
          this.download(response.msg,response.key);
        }).catch(function () {
        });
      },
      /** 导入按钮操作 */
      openImportTable() {
        this.upload.title = "同步用户信息数据导入";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        importTemplate().then((response) => {
          this.download(response.msg,response.key);
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
        this.$alert(response.msg, "导入结果", {dangerouslyUseHTMLString: true});
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      },
      // 搜索栏展开
      onHandle() {
        this.isOpen = !this.isOpen;
      }
    }
  };
</script>


<style lang="scss" scoped>
  .app-container {
    background: #E9F4FD;
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
