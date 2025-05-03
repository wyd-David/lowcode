<template>
  <div class="container" ref="container">
  </div>
</template>

<script >
import * as THREE from "three";
import { ref } from "vue";

import img4_l from "@/assets/static/imgs/living/4_l.jpg";
import img4_r from "@/assets/static/imgs/living/4_r.jpg";
import img4_u from "@/assets/static/imgs/living/4_u.jpg";
import img4_d from "@/assets/static/imgs/living/4_d.jpg";
import img4_b from "@/assets/static/imgs/living/4_b.jpg";
import img4_f from "@/assets/static/imgs/living/4_f.jpg";
//添加一个轨道控制器
import { OrbitControls} from "three/examples/jsm/controls/OrbitControls";
//导入动画库
import gsap from "gsap";
//导入dat.gui
import * as dat from "dat.gui";

export default {
  name: 'App',
  data() {
    return {
      renderer: null,
      scene: null,
      camera: null,
      cube: null,
      clock: null,
      animate1: null,
      controls: null,
      gui: null,
      directionalLight: null,
      smallBall: null
    }
  },
  mounted() {
    //初始化场景
    const scene = new THREE.Scene();
    //初始化相机
    const camera = new THREE.PerspectiveCamera(
    75,
    window.innerWidth/window.innerHeight,
    0.1,
    1000);
    //设置相机位置
    camera.position.z=0.1;


    //初始化渲染器
    const renderer = new THREE.WebGLRenderer();
    renderer.setSize(window.innerWidth,window.innerHeight);

   //将webgl渲染的canvas内容添加到div
   this.$refs['container'].appendChild(renderer.domElement);

    //使用渲染器，通过相机将场景渲染进来
    renderer.render(scene,camera);

    this.renderer=renderer;//将渲染器绑定
    this.scene=scene;//将场景绑定
    this.camera=camera;//将相机绑定

    //添加立方体
    const gemetry = new THREE.BoxGeometry(10,10,10);//设置大小
    //添加材质
   // const material = new THREE.MeshBasicMaterial({color:0x00ff00});
    //生成立方体物体
    //const cube = new THREE.Mesh(gemetry,material);
    //将物体添加到场景
   // scene.add(cube);

    //添加轨道控制器
    const controls = new OrbitControls(camera,renderer.domElement);
    //设置控制器阻尼，让控制器更有真实效果，必须在动画循环调用update
    controls.enableDamping = true;
    //绑定控制器
    this.controls=controls;
    var boxMaterials = [];

   /* import img4_l from "@/assets/static/imgs/living/4_l.jpg";
    import img4_r from "@/assets/static/imgs/living/4_r.jpg";
    import img4_u from "@/assets/static/imgs/living/4_u.jpg";
    import img4_d from "@/assets/static/imgs/living/4_d.jpg";
    import img4_b from "@/assets/static/imgs/living/4_b.jpg";
    import img4_f from "@/assets/static/imgs/living/4_f.jpg"; */
     //var arr =['4_l','4_r','4_u','4_d','4_b','4_f'];
    let texture = new THREE.TextureLoader().load(img4_l);
    boxMaterials.push(new THREE.MeshBasicMaterial({map: texture}));

   let texture1 = new THREE.TextureLoader().load(img4_r);
   boxMaterials.push(new THREE.MeshBasicMaterial({map: texture1}));

   let texture2 = new THREE.TextureLoader().load(img4_u);
   texture2.rotation = Math.PI;//如果是上和下的图片，则旋转180°
   texture2.center = new THREE.Vector2(0.5,0.5);//设置旋转中心
   boxMaterials.push(new THREE.MeshBasicMaterial({map: texture2}));



   let texture3 = new THREE.TextureLoader().load(img4_d);
   texture3.rotation = Math.PI;//如果是上和下的图片，则旋转180°
   texture3.center = new THREE.Vector2(0.5,0.5);//设置旋转中心
   boxMaterials.push(new THREE.MeshBasicMaterial({map: texture3}));

   let texture5 = new THREE.TextureLoader().load(img4_b);
   boxMaterials.push(new THREE.MeshBasicMaterial({map: texture5}));

   let texture6 = new THREE.TextureLoader().load(img4_f);
   boxMaterials.push(new THREE.MeshBasicMaterial({map: texture6}));

    //添加图片

    //设置盒子的材质
   // var boxMaterials = [];
   /* arr.forEach((item)=>{
      //纹理加载
      console.log(img);
      let texture = new THREE.TextureLoader().load(img);
      //创建基础材质并贴图
      debugger
      if(item==='4_u' || item==='4_d'){
        texture.rotation = Math.PI;//如果是上和下的图片，则旋转180°
        texture.center = new THREE.Vector2(0.5,0.5);//设置旋转中心
      }
      boxMaterials.push(new THREE.MeshBasicMaterial({map: texture}));
    }); */
    //设置物体的材质
   const cube = new THREE.Mesh(gemetry,boxMaterials);

   cube.geometry.scale(1,1,-1);//调整几何体Z轴颠倒

  //将物体添加到场景
    scene.add(cube);


    this.renders();
  },
  methods: {
    renders(){
       this.renderer.render(this.scene,this.camera);
       requestAnimationFrame(this.renders.bind(this));
    },

  }
}
</script>

<style>
  *{
    margin: 0;
    padding: 0;
  }
.container{
  height: 100vh;
  width: 100vw;
  background-color: #f0f0f0;
}
</style>
