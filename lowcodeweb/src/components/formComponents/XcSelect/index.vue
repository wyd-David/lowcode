<template>

<div class="drop-select">
          <el-select    v-model="model"
                        :multiple="multiple"
                        :style="{ width: width }"
                        :disabled="disabled"
                        :size="size"
                        :clearable="clearable && !hasall"
                        @change="onChange"
                        :placeholder="placeholder">
            <el-option v-for="item in options"  :key="item[keys.value]"  :label="item[keys.label]" :value="item[keys.value]"/>
          </el-select>
  </div>

</template>

<script>
    export default {
        props: {
            value: {
                default: ''
            },
            field: '',
            keys: {
                type: Object,
                default: () => {
                    return {
                        label: 'label',
                        value: 'value'
                    };
                }
            },
            width: {
                default: '250px'
            },
            hasall: {
                type: Boolean,
                default: false
            },
            clearable: {
                default: false
            },
            multiple: {
                type: Boolean,
                default: false
            },
            transfer: {
                type: Boolean,
                default: false
            },
            disabled: {
                type: Boolean,
                default: false
            },
            placeholder: {
                default: '请选择'
            },
            size: {
                default: ''
            },
            
            datas: {
                type: Array,
                default: () => {
                    return  [
                        {
                            label: '启用',
                            value: 'enabled'
                        },
                        {
                            label: '停用',
                            value: 'disabled'
                        }
                    ];
                }
            }
        },
        data() {
            return {
                options: [],
                defaultOptions: {
                    status: [
                        {
                            label: '启用',
                            value: 'enabled'
                        },
                        {
                            label: '停用',
                            value: 'disabled'
                        }
                    ],
                    status2: [
                        {
                            label: '启用2',
                            value: 'enabled'
                        },
                        {
                            label: '停用2',
                            value: 'disabled'
                        }
                    ]
                }
            };
        },
        computed: {
            model: {
                get() {
                    const val = this.value && this.value.length ? this.value.split(',') : [];
                    return this.multiple ? val : this.hasall && !this.value.length ? 'all' : this.value;
                },
                set(val) {
                    if (this.multiple) {
                        const value = val.length ? val.join(',') : '';
                        this.$emit('input', value);
                    } else {
                        this.$emit('input', this.hasall && val === 'all' ? '' : val);
                    }
                }
            }
        },
        watch: {
            value() {
                if (this.multiple) {
                    const vals = this.value && this.value.length ? this.value.split(',') : [];
                    const arr = this.options.filter(item => vals.indexOf(item[this.keys.value]) > -1);
                    const temp = arr.map(item => item[this.keys.label]);
                    const label = temp.join(',');
                    this.$emit('update:label', label);
                } else {
                    const arr = this.options.filter(item => item[this.keys.value] === this.value);
                    const label = arr.length ? arr[0][this.keys.label] : '';
                    this.$emit('update:label', label);
                }
            },
            datas: {
                handler(newval) {
                    // debugger
                    let options = JSON.parse(JSON.stringify(this.datas));
                    this.unshiftAllItem(options);
                },
                deep: true
            }
        },
        created() {
            if (this.hasall) {
                let item = {};
                item[this.keys.label] = '全部';
                item[this.keys.value] = 'all';
                this.options = [item];
            }
            if (this.field) {
                let options = this.defaultOptions[this.field];
                if (options) {
                    this.unshiftAllItem(options);
                } else {
                    this.getOptions();
                }
            } else {
                let options = JSON.parse(JSON.stringify(this.datas));
                this.unshiftAllItem(options);
            }
        },
        methods: {
            // 获取字典项
            async getOptions() {
                const session = this.$Utils.getSessionItem(this.field);
                if (session) {
                    this.unshiftAllItem(session);
                } else {
                    let searchParams = {
                        code: this.field,
                        enable: 'enabled'
                    };
                    const { data } = await this.$API.getDictOptionByCode(searchParams);
                    if (data.code === 200 && data.data) {
                        let temp = data.data || [];
                        let list = [];
                        temp.forEach(item => {
                            if (item.viewFlag && parseInt(item.viewFlag) === 1) {
                                list.push({
                                    label: item.text,
                                    value: item.code
                                });
                            }
                        });
                        // this.$Utils.setSessionItem(this.field, list); // 存储到sessionStorage
                        this.unshiftAllItem(list);
                    }
                }
            },
            // 添加全选项
            unshiftAllItem(data) {
                if (this.hasall&&data) {
                    let temp = {};
                    temp[this.keys.label] = '全部';
                    temp[this.keys.value] = 'all';
                    data.unshift(temp);
                }
                this.options = data;
            },
            // 选择
            onChange(val) {
                this.$emit('change', val);
            }
        }
    };
</script>


