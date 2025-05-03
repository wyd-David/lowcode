<template>
  <!-- 展开收起 -->
  <div
    class="expandCollapse"
    :style="`height: ${isExpand ? height : stepHeight}px;`"
  >
    <div class="box" ref="boxEl">
      <div
        class="box-left"
        :style="`width: calc(100% - ${
          isShow ? 200 : 200
        }px);height: auto;line-height:${stepHeight}px; `"
      >
        <slot name="left"></slot>
      </div>
      <div
        class="box-right"
        :style="`height: ${stepHeight}px;line-height:${stepHeight}px; width:${
          isShow ? 200 : 130
        }px;`"
      >
        <slot name="right"></slot>
        <div class="textBox" v-if="isShow" @click="onExpand">
          <div class="textBox" v-if="!isExpand">
            展开<i class="el-icon-arrow-down"></i>
          </div>
          <div class="textBox" v-else>收起<i class="el-icon-arrow-up"></i></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      stepHeight: 60,
      height: 0,
      isShow: false, // 是否显示张开收起
      isExpand: false, // 是否展开
    };
  },
  created() {
    // 注册监听事件
    window.onresize = () => {
      this.init();
    };
  },
  mounted() {
    this.init();
  },
  activated() {
    // 注册监听事件
    window.onresize = () => {
      this.init();
    };
  },
  methods: {
    onExpand() {
      this.init();
      this.isExpand = !this.isExpand;
    },
    init() {
      this.$nextTick(() => {
        let el = this.$refs.boxEl;
        if (el) {
          this.height = el.offsetHeight || 0;
        }
        this.isShow = this.height > this.stepHeight ? true : false;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
$colorText: #1590ff;

.expandCollapse {
  overflow: hidden;
  transition: all 0.5s;
  background: #fff;
}

.heightAuto {
  height: auto !important;
}

.box {
  // font-size: 0;
  overflow: inherit;
  & > div {
    // display: inline-block;
    float: left;
    vertical-align: top;
  }

  &-left {
    max-width: calc(100% - 200px);
    .el-form-item {
      margin-bottom: 0;
    }
  }

  &-right {
    width: 200px;
    display: flex;
  }

  .textBox {
    color: $colorText;
    display: inline-block;
    margin-left: 10px;
    cursor: pointer;

    i {
      margin-left: 5px;
    }
  }
}
</style>
