import { createStore } from 'vuex'
import router from "@/router";
export default createStore({
  state: {
  },
  // getters: {
  // },
  mutations: {
  },
  actions: {
    // 安全退出
    logout(){
      window.sessionStorage.clear();
      localStorage.clear();
      // window.localStorage.clear();
      // 跳转到登录页面
      router.replace('/login')
    }
  },
  modules: {
  }
})
