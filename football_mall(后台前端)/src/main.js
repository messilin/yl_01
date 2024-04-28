import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import store from './store'
import '././assets/styles/border.css'
import '././assets/styles/reset.css'
import SvgIcon from '@/icons'
import '@/router/permission.js'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css';

const app = createApp(App);
SvgIcon(app);
app.component('QuillEditor', QuillEditor)
app.use(store).use(router).use(ElementPlus).use(SvgIcon).mount('#app')

// 解决ResizeObserver loop completed with undelivered notifications爆报错
const debounce = (fn, delay) => {
    let timer = null;
    return function () {
        let context = this;
        let args = arguments;
        clearTimeout(timer);
        timer = setTimeout(function () {
            fn.apply(context, args);
        }, delay);
    }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver{
    constructor(callback) {
        callback = debounce(callback, 16);
        super(callback);
    }
}
