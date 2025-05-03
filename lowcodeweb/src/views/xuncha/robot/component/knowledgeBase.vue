<template>
  <div class="robot_knowledge_base">
    <div class="list-title" style="margin-top: 0">
      <span class="line"></span><span>知识库({{ total }})</span>
      <div class="operation">
        <!-- <el-button @click="toUrl" type="text">
          <span style="display: flex"
            ><img
              style="margin-right: 2px"
              src="../../../../assets/images/robot/copy.png"
              width="14px"
            />
            OA制度库</span
          >
        </el-button> -->
        <el-button @click="downloadFiles" type="primary" size="mini"
          ><span style="display: flex">
            <img
              style="margin-right: 2px"
              src="../../../../assets/images/robot/download@2x.png"
              width="14px"
            />批量下载
          </span></el-button
        >
      </div>
    </div>
    <div class="robot-list" @scroll="handleScroll">
      <el-checkbox-group
        v-model="checkedCities"
        @change="handleCheckedCitiesChange"
      >
        <div class="item" v-for="(item, i) in list" :key="item.id + i">
          <el-checkbox :label="item"
            ><div style="line-height: 26px">
              <el-popover
                :open-delay="500"
                placement="top"
                width="400"
                trigger="hover"
              >
                <div
                  class="hover-dialog"
                  v-html="item.fileName_H ? item.fileName_H[0] : item.fileName"
                ></div>
                <div
                  slot="reference"
                  class="item-title"
                  @click="handlePreview(item)"
                >
                  标题：
                  <div
                    class="item-title-context"
                    style="width: auto"
                    v-html="
                      item.fileName_H ? item.fileName_H[0] : item.fileName
                    "
                  ></div>
                  <span class="checkBox-item">{{
                    [
                      "",
                      "案例库",
                      "巡察制度流程库",
                      "过程资料库",
                      "报告报表库",
                    ][item.refType]
                  }}</span>
                </div>
              </el-popover>

              <el-popover
                :open-delay="500"
                placement="top"
                width="400"
                trigger="hover"
              >
                <div
                  class="hover-dialog"
                  v-html="item.content_H ? item.content_H[0] : item.content"
                ></div>
                <div slot="reference" class="item-content">
                  内容：
                  <div
                    class="item-content-context"
                    v-html="item.content_H ? item.content_H[0] : item.content"
                  ></div>
                </div>
              </el-popover></div
          ></el-checkbox>
        </div>
      </el-checkbox-group>
      <div class="load-more" v-if="loading">
        <i class="el-icon-loading"></i>
      </div>
    </div>
  </div>
</template>

<script>
import { downloadZipFile, previewFile } from "@/utils/uploadOperate";
import { getCardListApi } from "@/api/xuncha/robot";
export default {
  props: {
    obj: [Object],
  },
  data() {
    return {
      loading: false,
      checkAll: false,
      checkedCities: [],
      isIndeterminate: true,
      pageNum: 1,
      pageSize: 10,
      isNextPage: true,
      st: null,
      list: [],
      total: 0,
    };
  },
  created() {
    if (this.obj.contentJson) {
      this.obj.contentJson.list.forEach((res) => {
        if (res.content == "null") {
          res.content = "";
        }
      });
      this.list = this.obj.contentJson.list;
      this.total = this.obj.contentJson.total;
    }
  },
  methods: {
    handlePreview(row) {
      previewFile(row.fileId);
    },
    handleScroll(e) {
      if (!this.isNextPage) {
        return;
      }
      let height = e.target.scrollHeight - e.target.clientHeight;
      if (e.target.scrollTop + 1 >= height) {
        this.loading = true;
        clearTimeout(this.st);
        this.st = setTimeout(() => {
          this.pageNum++;
          this.getCardList();
        }, 1000);
      }
    },
    getCardList() {
      getCardListApi({
        searchKeyWord: this.obj.searchKeyword,
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        repoType: this.obj.repoType,
      }).then((res) => {
        if (!res.data.list.length) {
          this.isNextPage = false;
        } else {
          this.isNextPage = true;
          this.list = this.list.concat(res.data.list);
        }
        this.loading = false;
      });
    },
    toUrl() {
      window.open("https://www.baidu.com");
    },
    downloadFiles() {
      if (!this.checkedCities.length) {
        this.msgError("请选择文件!");
        return;
      }
      downloadZipFile(this.checkedCities.map((item) => item.fileId).join(","));
    },
    handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.obj.contentJson.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.obj.contentJson.length;
    },
  },
};
</script>
<style lang="scss" scoped>
.robot_knowledge_base {
  position: relative;
  width: 80%;
  height: 337px;
  background: #ffffff;
  margin-left: 20px;
  box-shadow: 0px 0px 6px 0px rgba(133, 133, 133, 0.18);
  &::before {
    width: 0;
    height: 0;
    border-style: solid;
    border-width: 8px 8px 8px 0;
    border-color: transparent rgba(133, 133, 133, 0.18) transparent transparent;
    content: "\A";
    position: absolute;
    left: -8px;
    top: 8px;
    border-right-color: rgba(133, 133, 133, 0.18);
  }
  &::after {
    width: 0;
    height: 0;
    border-style: solid;
    border-width: 8px 8px 8px 0;
    border-color: transparent #fff transparent transparent;
    content: "\A";
    position: absolute;
    left: -7px;
    top: 8px;
    border-right-color: #fff;
  }
  .robot-list {
    padding: 0px 14px;
    height: 281px;
    overflow-y: scroll;
    overflow-x: hidden;
    .item {
      padding: 14px 0px;
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: rgba(23, 35, 61, 0.75);
    }

    /deep/.el-checkbox {
      display: flex;
      align-items: center;
    }
  }
  .load-more {
    text-align: center;
    position: absolute;
    bottom: 0px;
    left: 0px;
    right: 0px;
  }
  .list-title {
    position: relative;
    .operation {
      position: absolute;
      right: 20px;
      top: 10px;
      font-size: 14px;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #1590ff;
      margin-bottom: 0px;
    }
  }

  .item-title {
    display: flex;
  }
  .item-title-context {
    width: 700px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    position: relative;
  }
  .item-content {
    display: flex;
  }
  .item-content-context {
    width: 700px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}
.hover-dialog {
  max-height: 300px;
  overflow: auto;
  padding: 10px;
}
/*css主要部分的样式*/
/*定义滚动条宽高及背景，宽高分别对应横竖滚动条的尺寸*/
::-webkit-scrollbar {
  width: 6px; /*对垂直流动条有效*/
  height: 6px; /*对水平流动条有效*/
}

/*定义滚动条的轨道颜色、内阴影及圆角*/
::-webkit-scrollbar-track {
  border-radius: 4px;
  /* -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3); */
  /* background-color: rosybrown; */
}

/*定义滑块颜色、内阴影及圆角*/
::-webkit-scrollbar-thumb {
  border-radius: 8px;
  background-color: #dddee0;
  /* -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3); */
}

/*定义滑块悬停变化颜色、内阴影及圆角*/
::-webkit-scrollbar-thumb:hover {
  background-color: #c7c9cc;
}

/*定义两端按钮的样式*/
::-webkit-scrollbar-button {
  /* background-color: cyan; */
}

/*定义右下角汇合处的样式*/
::-webkit-scrollbar-corner {
  /* background: khaki; */
}

.checkBox-item {
  display: inline-block;
  padding: 0px 5px;
  font-size: 12px;
  border-radius: 2px;
  background-color: #fef0f0;
  border: 1px solid #fde2e2;
  color: #f56c6c;
  position: absolute;
  right: 0;
  line-height: 22px;
}
</style>