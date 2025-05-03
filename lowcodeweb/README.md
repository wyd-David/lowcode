# inspection_system_front 
前端工程




# 前端公共组件使用：

## 下拉组件

### 数据字典下拉：

```

数据字典下拉：
引入：
  import XcSelect from '@/components/formComponents/XcSelect/index'
<xc-select hasall v-model="queryForm.selectTest" :label.sync="queryForm.selectTestName" width="100px"  :field="$dicCode.status"> </XcSelect>
说明：
field：业务字典的编号，统一在src/utis/code.js维护
v-model:绑定key
:label.sync：绑定值value
hasall:全部选项
width:宽度


```



### 自定义下拉：

```

自定义下拉：
引入：
  import XcSelect from '@/components/formComponents/XcSelect/index'
<xc-select hasall v-model="queryForm.selectTest" :label.sync="queryForm.selectTestName" width="100px" keys="{value:'id',label='name'}"  datas="{"id":"1",name:"fd"}"> </XcSelect>
说明：
v-model:绑定key
:label.sync：绑定值value
keys:键值的配置
datas:数据信息
hasall:全部选项
width:宽度
```

### 现成对接下拉：轮次、批次、公司、任务

```

现成对接下拉：轮次、批次、公司、任务，
代码位置：src/components/XunCha
引入：
  import Lunci from '@/components/XunCha/LunCi/index'
   <lun-ci v-model="queryForm.selectTest" :label.sync="queryForm.selectTestName"></lun-ci>
说明：
v-model:绑定key
:label.sync：绑定值value
hasall:全部选项
width:宽度
```



## 工作流组件规划

说明：流程分两块，流程未发起和已发起的情况

1、未发起：暂存和提交，自己保存数据以及走流程

2、发起后：简单可以用现成的操作按钮，不满足可以定制化按钮，

比如复制按钮排插组件，自己定制：src/components/WorkFlow/deepBtn.vue

### 流程基本信息说明

```
 process={
  //业务id
    businessKey: '',
    //待办id
    todoId: '',
  //0无权限 1可查看 2可处理
    authorizeFlag: '-1',
    //流程实例id
    processInstanceId: null,
    //流程定义key
    processDefinitionId: null,
    //任务定义key
    taskDefinitionKey: null,
    //当前节点用户
    curStepUser:'',
    //当前节点名称
    curStepName:'',
    //任务id
    curtaskId:'',
    //下一个节点
    nextStepAndUser:[],
    //退回节点
    backNextStepAndUser:[],
    //流程轨迹
    allFlowStep:[],
    //按钮信息
    hasBtnPerms:[],
};

获取方式：
import { fetchWorkFlowInfo } from "@/utils/workflow";
fetchWorkFlowInfo(this.businessKey,this.todoId,((e)=>{
          if(e.authorizeFlag==0){
             that.$router.push('/noPermission')
          }
          that.process=e
          that.hasBtnPerms = that.process.hasBtnPerms;
       }));
       
参数说明：
businessKey：必填
todoId：选填，代码打开的场景传入

```



### 按钮排插组件

```
组件说明：主要用于统一的流程按钮，包括线程按钮：暂存、转办、退回、审批
使用及参数说明：
businessKey：必填
todoId：选填，代码打开的场景传入
@saveDraftData：暂存事件回调
@close：关闭事件回调


引用及案例：src/views/xuncha/archives/case/caseMethodForm.vue
 import deepBtn from "@/components/WorkFlow/deepBtn";
    <div slot="footer" class="dialog-footer">
      <deep-btn ref="deepbtnRef"
                :businessKey="caseId"
                @close="handleClose"
                @saveDraftData="handleSaveFromData"
      >
        <!-- 发起默认按钮 -->
        <el-button type="primary"  @click="saveBtn">暂存</el-button>
        <el-button type="primary"  @click="submitBtn">提交</el-button>
      </deep-btn>
```

### 选人弹窗组件

```
组件说明：主要用于统一的流程按钮，包括线程按钮：转办、退回、审批
使用及参数说明：已弹框的触发方式
process：必填


引用及案例：src/components/WorkFlow/deepBtn.vue
import CompleteProcess from "@/components/WorkFlow/completeProcess";
import TurnbackProcess from "@/components/WorkFlow/turnbackProcess";
import transferProcess from "@/components/WorkFlow/transferProcess";

 <complete-process
        ref="completeRef"
        :dialogVisible="applyShow"
        @close="applyShow = false"
      ></complete-process>
      
//打开弹窗
    openDialog(show,btnRef){
       if(!this.process.nextStepAndUser&&this.process.nextStepAndUser.length==0){
          this.$message.error('流程人员异常');
          return
      }
        this[show]=true;
        this.$refs[btnRef].init(this.process);
    },
```

### 流程轨迹组件

```
组件说明：主要显示流程轨迹
使用及参数说明：
allFlowStep：必填

  引用及案例：src/components/WorkFlow/deepBtn.vue
 import AllFlowStep from "@/components/WorkFlow/AllFlowStep";
 <all-flow-step ref="allFlowStepRef" v-if="businessKey" :allFlowStep="process.allFlowStep" ></all-flow-step>


```

