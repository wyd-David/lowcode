import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken, setToken, getCookieValue } from "@/utils/auth";
import { autoH5Login } from "@/api/login";
import { isRelogin } from '@/utils/request'
import { title } from 'process'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/auth-redirect', '/bind', '/register','/lowcodeForm','/ThreeDform','/ThreeDformDemo','/ai/chartNow']

router.beforeEach((to, from, next) => {
  NProgress.start()
  let url = encodeURI(location.href);
  let iPlanetDirectoryPro = getCookieValue("iPlanetDirectoryPro") || "";
  let fd = new FormData();
  if (iPlanetDirectoryPro && !getToken()) {
    // alert('自动登录')
    autoH5Login({ iPlanetDirectoryPro: iPlanetDirectoryPro, flag: "pc" })
      .then((res) => {
        setToken(res.token);
        // 判断当前用户是否已拉取完user_info信息
        store
          .dispatch("GetInfo")
          .then((res) => {
            // 拉取user_info
            const roles = res.roles;
            store
              .dispatch("GenerateRoutes", { roles })
              .then((accessRoutes) => {
                // 测试 默认静态页面
                // debugger
                // store.dispatch('permission/generateRoutes', { roles }).then(accessRoutes => {
                // 根据roles权限生成可访问的路由表
                router.addRoutes(accessRoutes); // 动态添加可访问路由表
                console.log(accessRoutes, "完成路由加载了");
                next({ ...to, replace: true }); // hack方法 确保addRoutes已完成
              })
              .catch((err) => {
                next({ path: "/" });
              });
          })
          .catch((err) => {
            store.dispatch("FedLogOut").then(() => {
              Message.error(err);
              next({ path: "/" });
            });
          });
        // next()
        // location.href = location.origin + '/#'+ to.matched[0].path + '?id=' + queryData.todoId
      })
      .catch(() => {
        next();
      });
    return;
  }
  if (getToken()) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/

    // 待办拦截 hideNavbar为约定好的标识
    if (location.search.indexOf('hideNavbar') !== -1) {
      store.dispatch('settings/setNavBarStatus', true).then(() => {
        next()
      })
      return
    } else {
      store.dispatch('settings/setNavBarStatus', false)
    }

    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        isRelogin.show = true
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetInfo').then(() => {
          isRelogin.show = false
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            // 根据roles权限生成可访问的路由表
            accessRoutes.forEach(oitem => {
              if (oitem.children) { 
                oitem.meta.currId = oitem.children[0].parentId;
                oitem.children.forEach((titem) => {
                  if (titem.threeChildren&&titem.threeChildren.length) {
                    titem.meta.currId = titem.threeChildren[0].parentId;
                    titem.meta.parentId = titem.parentId;
                    titem.meta.gId = oitem.meta.currId;//祖id
                  }
                  //有三级菜单,赋值父级id
                  if (titem.children) {
                    titem.meta.parentId = titem.children[0].parentId;
                    titem.children.forEach((fitem) => {
                      if (fitem.threeChildren && fitem.threeChildren.length) {
                        fitem.meta.currId = fitem.threeChildren[0].parentId;
                        fitem.meta.parentId = fitem.parentId;
                      }
                    });
                  }
                });
              }
            })
            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
          })
        }).catch(err => {
            store.dispatch('LogOut').then(() => {
              Message.error(err)
              next({ path: '/' })
            })
          })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
