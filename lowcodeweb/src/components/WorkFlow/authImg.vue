<template>
    <img ref="img" />
</template>

<script>
export default {
    name: 'authImg',
    props: {
        authSrc: String
    },
    computed: {
        // 拿到token值，登录成功后由后台返回的
        token() {
            // 这里我把登录之后拿到的token放到了vuex中
            return '';
            // return this.$store.state.commonState.m_token;
        }
    },
    mounted() {
        
    },
    created(){
        this.getImgSrcByToken();
    },
    methods: {
        // 携带token请求img的src
        getImgSrcByToken() {
            debugger
            // Object.defineProperty()方法会直接在一个对象上定义一个新属性，或者修改一个对象的现有属性，并返回此对象
            Object.defineProperty(Image.prototype, 'authSrc', {
                // 可写
                writable: true,
                // 可枚举
                enumerable: true,
                // 若configurable设为false，那就不可以delete了
                configurable: true
            });
            let img = this.$refs.img;
            let request = new XMLHttpRequest();
            request.responseType = 'blob';
            request.open('get', this.authSrc, true);
            request.setRequestHeader('token', this.token);
            request.onreadystatechange(e => {
                if(request.readyState == XMLHttpRequest.DONE && request.status == 200) {
                    // URL.createObjectURL() 静态方法会创建一个 DOMString，其中包含一个表示参数中给出的对象URL，这个新的URL对象表示指定的File对象或blob对象
                    img.src = URL.createObjectURL(request.response);
                    img.onload = () => {
                        // 在每次调用 createObjectURL() 方法时，都会创建一个新的 URL 对象，即使你已经用相同的对象作为参数创建过。当不再需要这些 URL 对象时，每个对象必须通过调用 URL.revokeObjectURL() 方法来释放。
                        URL.revokeObjectURL(img.src);
                    }
                }
            });
            request.send(null);
        },
    },
}
</script>   