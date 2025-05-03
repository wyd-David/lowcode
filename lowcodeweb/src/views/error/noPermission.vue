<template>
  <div class="errPage-container">
    <!-- <el-button icon="arrow-left" class="pan-back-btn" @click="back">
      返回
    </el-button> -->
    <el-row>
      <el-col :span="12">
        <h1 class="text-jumbo text-ginormous">
          401错误!
        </h1>
        <h2>对不起，您没有访问权限！</h2>
        <!-- <h6>对不起，您没有访问权限，请不要进行非法操作！您可以返回主页面</h6> -->
        <!-- <el-button @click="closeWindow" size="small">关闭页面</el-button> -->
      </el-col>
      <el-col :span="12">
        <img :src="errGif" width="313" height="428" alt="Girl has dropped her ice cream.">
      </el-col>
    </el-row>
  </div>
</template>

<script>
import errGif from '@/assets/401_images/401.gif'

export default {
  name: 'Page401',
  data() {
    return {
      errGif: errGif + '?' + +new Date()
    }
  },
  methods: {
    back() {
      if (this.$route.query.noGoBack) {
        this.$router.push({ path: '/' })
      } else {
        this.$router.go(-1)
      }
    },
    
closeWindow(){
	console.log("关闭浏览器");
  var userAgent = navigator.userAgent;
  if (userAgent.indexOf("MSIE") > 0) {
    if (userAgent.indexOf("MSIE 6.0") > 0) {
      window.opener = null;
      window.close();
    } else {
      window.open("", "_top");
      window.top.close();
    }
  } else if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Chrome") != -1) {
    window.location.href = "about:blank "; //火狐默认状态非window.open的页面window.close是无效的
    //window.history.go(-2);
  } else {
    window.opener = null;
    window.open("about:blank", "_self");
    window.close();
  }

},

  }
}
</script>

<style lang="scss" scoped>
  .errPage-container {
    width: 800px;
    max-width: 100%;
    margin: 100px auto;
    .pan-back-btn {
      background: #008489;
      color: #fff;
      border: none!important;
    }
    .pan-gif {
      margin: 0 auto;
      display: block;
    }
    .pan-img {
      display: block;
      margin: 0 auto;
      width: 100%;
    }
    .text-jumbo {
      font-size: 60px;
      font-weight: 700;
      color: #484848;
    }
    .list-unstyled {
      font-size: 14px;
      li {
        padding-bottom: 5px;
      }
      a {
        color: #008489;
        text-decoration: none;
        &:hover {
          text-decoration: underline;
        }
      }
    }
  }
</style>
