<template>
  <div class="app-container home">

<!--     
    <div class="navlist">
      <el-row :gutter="20">
        <el-col :span="24">
          <div class="navlist-item xclink">
            <div class="navlist-item-left">
              <p class="title">智慧小巡</p>
            </div>
            
          </div>
        </el-col>
        
      </el-row>
    </div> -->

    <el-row :gutter="20">
      <el-col :span="16">
        <div class="crad">
          <div class="crad-header">
            <div class="crad-title">
              <img src="~@/assets/images/xc_crad.png" alt=""/>
              <span>智慧小巡</span>
            </div>
            <div class="crad-header-content"></div>
          </div>
          <div class="crad-content" style="height:900px">
            <iframe
              :src="robotSrc"
              frameborder="no"
              style="width: 100%; height: 100%"
              scrolling="auto"
            />


          </div>
        </div>
      </el-col>


      <el-col :span="8">
        <div class="crad">
          <div class="crad-header">
            <div class="crad-title">
              <img src="~@/assets/images/fx_crad.png" alt=""/>
              <span>猜你想问</span>
            </div>
            <div class="crad-header-content"></div>
            <div class="crad-header-right">
              更多 <i class="el-icon-arrow-right"></i>
            </div>
          </div>
          <div class="crad-content">
            <div class="fx_list">
              <div class="fx_list-item">
                <div class="fx_list-item-left"></div>
                <div class="fx_list-item-center">
                  <div class="title">韶关-乳源分公司…员工及渠道</div>
                </div>
                <div class="fx_list-item-right">10分钟前</div>
              </div>
              <div class="fx_list-item">
                <div class="fx_list-item-left"></div>
                <div class="fx_list-item-center">
                  <div class="title">韶关-乳源分公司…员工及渠道</div>
                </div>
                <div class="fx_list-item-right">5小时前</div>
              </div>
              <div class="fx_list-item">
                <div class="fx_list-item-left"></div>
                <div class="fx_list-item-center">
                  <div class="title">韶关-乳源分公司…员工及渠道</div>
                </div>
                <div class="fx_list-item-right">3天前</div>
              </div>
              <div class="fx_list-item">
                <div class="fx_list-item-left"></div>
                <div class="fx_list-item-center">
                  <div class="title">韶关-乳源分公司…员工及渠道</div>
                </div>
                <div class="fx_list-item-right">2020-10-31</div>
              </div>
              <div class="fx_list-item">
                <div class="fx_list-item-left"></div>
                <div class="fx_list-item-center">
                  <div class="title">韶关-乳源分公司…员工及渠道</div>
                </div>
                <div class="fx_list-item-right">2020-10-31</div>
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-dialog title="提示" :visible.sync="todoShow" width="600px">
      <div class="dialog-context"></div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="todoShow = false">取 消</el-button>
        <el-button type="primary" @click="todoShow = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { getTodoList } from '@/api/index'

  export default {
    name: 'Index',
    data() {
      return {
        robotSrc: '',
        total: 10,
        pageNo: 1,
        pageSize: 10,
        todoShow: false,
        detailObj: {},
        activeName: '1',
        tableData: [],
        todoTypes: ['待办', '已办', '待阅', '已阅']
      }
    },
    methods: {
      handleSizeChange(e) {
        this.pageSize = e
        this.getList()
      },
      handleCurrentChange(e) {
        this.pageNo = e
        this.getList()
      },
      handleClick(row) {
        this.detailObj = row
        // this.todoShow = true
        window.open(row.todoUrl);
      },
      handleTabClick() {
        this.getList()
      },
      getList() {
        getTodoList({
          pageNum: this.pageNo,
          pageSize: this.pageSize,
          todoDataType: this.activeName
        }).then((res) => {
          this.tableData = res.rows
          this.total = res.total
        })
      }
    },
    created() {
      // CONFIG.url+"/dev-api/robot"
      // debugger
      this.robotSrc=CONFIG.url+process.env.VUE_APP_BASE_API+"/robot";
      this.getList()
    }
  }
</script>

<style scoped lang="scss">
  .home {
    background: #f5f7f9;

    .xclink {
      background: linear-gradient(134deg, #fc5389 0%, #f8a16f 100%);
    }

    .ljlink {
      background: linear-gradient(140deg, #9d66f5 0%, #f26fa1 100%);
    }

    .jclink {
      background: linear-gradient(118deg, #fe7b31 0%, #fca13a 100%, #fca13a 100%);
    }

    .gdlink {
      background: #fff;

      .title {
        color: #333333 !important;
      }

      .text {
        color: #747c93 !important;
      }
    }

    .navlist {
      &-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
        height: 50px;
        border-radius: 6px;
        cursor: pointer;

        &-left {
          padding-left: 20px;

          p {
            margin: 0;
          }

          .title {
            font-size: 20px;
            font-family: "PingFangSC-Semibold, PingFang SC";
            font-weight: 600;
            color: #ffffff;
            line-height: 28px;
            margin-bottom: 8px;
          }

          .text {
            font-size: 14px;
            font-family: "PingFangSC-Regular, PingFang SC";
            font-weight: 400;
            color: #ffffff;
            line-height: 20px;
          }
        }

        &-right {
          img {
            width: 56px;
            height: 56px;
            margin: 0;
            margin-top: 40px;
            margin-right: 20px;
          }
        }
      }
    }

    ::v-deep .el-tabs__nav-wrap::after {
      display: none;
    }

    ::v-deep .el-tabs__header {
      margin: 0;
    }

    .crad {
      margin-top: 20px;
      background: #fff;
      border-radius: 2px;

      &-header {
        display: flex;
        align-items: center;
        border-radius: 2px;
        height: 48px;
        padding-right: 16px;
        padding-left: 20px;
        box-shadow: inset 0px -1px 0px 0px rgba(23, 35, 61, 0.1);

        &-content {
          flex: 1;
          display: flex;
          align-items: flex-end;
          padding-left: 100px;
        }

        &-right {
          color: rgba(23, 35, 61, 0.45);
          font-size: 14px;
          cursor: pointer;
        }

        &-right:hover {
          color: #1590ff;
        }
      }

      &-title {
        display: flex;

        img {
          width: 24px;
          height: 24px;
          margin-right: 12px;
        }

        span {
          font-family: "PingFangSC-Semibold, PingFang SC";
          font-weight: 600;
          color: rgba(23, 35, 61, 0.85);
        }
      }

      &-content {
        padding: 16px 24px;
      }
    }

    .xc_list {
      margin-top: -16px;

      &-item {
        display: flex;
        align-items: center;
        padding: 16px 0;
        box-shadow: inset 0px -1px 0px 0px rgba(23, 35, 61, 0.1);

        &-left {
          display: flex;
          align-items: center;

          img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
          }
        }

        &-right {
          padding-left: 16px;

          .header {
            margin-bottom: 8px;

            p {
              margin: 0;
            }

            display: flex;

            .title {
              font-size: 14px;
              font-family: "PingFangSC-Semibold, PingFang SC";
              font-weight: 600;
              color: rgba(23, 35, 61, 0.85);
            }

            .activeText,
            .option {
              font-size: 14px;
              font-family: "PingFangSC-Regular, PingFang SC";
              font-weight: 400;
              color: rgba(23, 35, 61, 0.75);
              margin: 0 12px;
            }

            .activeText {
              color: #2e76f9;
            }
          }

          .time {
            font-size: 12px;
            font-family: "PingFangSC-Regular, PingFang SC";
            font-weight: 400;
            color: rgba(23, 35, 61, 0.45);
          }
        }
      }
    }

    .fx_list {
      margin-top: -16px;

      &-item {
        display: flex;
        align-items: center;
        padding: 16px 0;

        &-left {
          width: 12px;
          height: 12px;
          background: linear-gradient(140deg, #f7875f 0%, #f6748a 100%);
          background-size: cover;
          outline: 6px solid rgba(247, 127, 114, 0.15);
          border-radius: 50%;
        }

        &-center {
          flex: 1;
          display: flex;
          padding: 0 12px;
          font-size: 14px;
          font-family: "PingFangSC-Regular, PingFang SC";
          font-weight: 400;
          color: rgba(23, 35, 61, 0.75);
          margin: 0;

          .title {
            text-align: left;
            cursor: pointer;
            margin: 0;
          }

          .title:hover {
            color: #1590ff;
          }
        }

        &-right {
          font-size: 14px;
          font-family: "PingFangSC-Regular, PingFang SC";
          font-weight: 400;
          color: rgba(23, 35, 61, 0.45);
          text-align: right;
        }
      }

      &-item:nth-child(2) {
        .fx_list-item-left {
          background: linear-gradient(140deg, #9d66f5 0%, #f26fa1 100%);
          outline: 6px solid rgba(202, 106, 200, 0.15);
        }
      }

      &-item:nth-child(3) {
        .fx_list-item-left {
          background: linear-gradient(315deg, #fad961 0%, #f76b1c 100%);
          outline: 6px solid rgba(249, 162, 63, 0.15);
        }
      }

      &-item:nth-child(4) {
        .fx_list-item-left {
          background: linear-gradient(312deg, #02b9fa 0%, #508dff 100%);
          outline: 6px solid rgba(52, 157, 254, 0.15);
        }
      }

      &-item:last-child {
        padding-bottom: 0;
      }
    }
  }
</style>
