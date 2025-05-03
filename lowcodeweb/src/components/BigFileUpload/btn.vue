<template>
  <label class="uploader-btn primary small" ref="btn" v-show="support">
    <slot></slot>
  </label>
</template>

<script>
  import { uploaderMixin, supportMixin } from './mixins'

  const COMPONENT_NAME = 'uploader-btn'

  export default {
    name: COMPONENT_NAME,
    mixins: [uploaderMixin, supportMixin],
    props: {
      directory: {
        type: Boolean,
        default: false
      },
      single: {
        type: Boolean,
        default: false
      },
      attrs: {
        type: Object,
        default () {
          return {}
        }
      }
    },
    mounted () {
      this.$nextTick(() => {
        this.uploader.uploader.assignBrowse(this.$refs.btn, this.directory, this.single, this.attrs)
      })
    }
  }
</script>

<style>
  .uploader-btn {
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    cursor: pointer;
    background: #FFFFFF;
    border: 1px solid #DCDFE6;
    border-color: #DCDFE6;
    color: #606266;
    -webkit-appearance: none;
    text-align: center;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    outline: none;
    margin: 0;
    -webkit-transition: .1s;
    transition: .1s;
    font-weight: 400;
    -moz-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    padding: 12px 20px;
    font-size: 14px;
    border-radius: 4px;

    margin-right: 10px;
  }
  .primary{
    color: #FFFFFF;
    background-color: #1590ff;
    border-color: #1590ff;
  }
  .small{
    padding: 9px 15px;
    font-size: 12px;
    border-radius: 3px;
  }
  .uploader-btn:hover {
    background: #e14d3b;
    border-color: #e14d3b;
    color: #FFFFFF;
  }
</style>
