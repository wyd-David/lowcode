<template>
    <div>
        <div class="form-module-title">
          <span class="line"></span><span>流程图</span>
        </div>

        <div class="module-context-row">
            <!-- <img src="http://localhost:8087/prod-api/activiti/queryProImg?businessKey=archive_process_flow_003"> -->
            <!-- <img ref='imgF' :src="iconBase64"> -->
            <authImg :authSrc="authSrc" alt=""></authImg>

        </div>
    </div>
</template>
<script>
import authImg from "./authImg";

export default {
  components:{authImg},
  data() {
    return {
      authSrc
    };
  },
  mounted() {
  },
  created(){
     let imageUrl='https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F4k%2Fs%2F02%2F2109242306111155-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656673897&t=288632d955f6094940b73910cf4db74c'

            this.authSrc=imageUrl
    // this.getImgSrcByToken();
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
           
            let img = this.$refs.imgF;
            let request = new XMLHttpRequest();
            request.responseType = 'blob';
            request.open('get', this.authSrc, true);
            request.setRequestHeader('token', this.token);
            request.onreadystatechange(e => {
                if(request.readyState == XMLHttpRequest.DONE && request.status == 200) {
                    // URL.createObjectURL() 静态方法会创建一个 DOMString，其中包含一个表示参数中给出的对象URL，这个新的URL对象表示指定的File对象或blob对象
                    debugger
                    img.src = URL.createObjectURL(request.response);
                    img.onload = () => {
                        // 在每次调用 createObjectURL() 方法时，都会创建一个新的 URL 对象，即使你已经用相同的对象作为参数创建过。当不再需要这些 URL 对象时，每个对象必须通过调用 URL.revokeObjectURL() 方法来释放。
                        URL.revokeObjectURL(img.src);
                    }
                }
            });
            request.send(null);
        },

    //异步执行
        imageUrlToBase64() {
            //一定要设置为let，不然图片不显示
            let image = new Image();
            //解决跨域问题
            image.setAttribute('crossOrigin', 'anonymous');

            let imageUrl = "http://img1.imgtn.bdimg.com/it/u=4012470373,3540768040&fm=26&gp=0.jpg";
                  imageUrl='https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F4k%2Fs%2F02%2F2109242306111155-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656673897&t=288632d955f6094940b73910cf4db74c'

image.src = imageUrl
            let that = this;
            //image.onload为异步加载
            image.onload = () => {
                var canvas = document.createElement("canvas");
                canvas.width = image.width;
                canvas.height = image.height;
                var context = canvas.getContext('2d');
                context.drawImage(image, 0, 0, image.width, image.height);              
                var quality = 0.8;
                //这里的dataurl就是base64类型
                var dataURL = canvas.toDataURL("image/jpeg", quality);//使用toDataUrl将图片转换成jpeg的格式,不要把图片压缩成png，因为压缩成png后base64的字符串可能比不转换前的长！          
            }
        },
    imageToBase64 (file) {
      debugger
      file='https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F4k%2Fs%2F02%2F2109242306111155-0-lp.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656673897&t=288632d955f6094940b73910cf4db74c'
      var reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onload = () => {
        console.log('file 转 base64结果：' + reader.result)
        this.iconBase64 = reader.result
      }
      reader.onerror = function (error) {
        console.log('Error: ', error)
      }
    }
  },
};
</script>

 
