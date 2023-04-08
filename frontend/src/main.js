import Vue from 'vue'
import App from './App.vue'
import router from './router'

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import {deleteRequest, getRequest, postRequest, putRequest} from "@/utils/api";
Vue.config.productionTip = false
Vue.use(Element)

Vue.config.productionTip = false


import { Loading } from 'element-ui';

Vue.prototype.openLoading = function() {
  const loading = this.$loading({           // 声明一个loading对象
    lock: true,                             // 是否锁屏
    text: '',                         // 加载动画的文字
    spinner: 'el-icon-loading',             // 引入的loading图标
    background: 'rgba(0, 0, 0, 0.8)',       // 背景颜色
    target: ' .region',       // **需要遮罩的区域，这里写要添加loading的选择器**
    fullscreen: false,
    customClass: 'loadingclass'             // **遮罩层新增类名，如果需要修改loading的样式**
  })

  let start = new Date().getTime(); // 开始时间戳
  let interval = setInterval(() => {
    let end = new Date().getTime(); // 当前时间戳
    let duration = ((end - start) / 1000).toFixed(0); // 计算加载所用的时间差，并保留两位小数
    loading.text = `加载耗时 ${duration} 秒`; // 更新 loading 文本
  }, 1000); // 每秒钟更新一次

  setTimeout(function () {                  // 设定定时器，超时5S后自动关闭遮罩层，避免请求失败时，遮罩层一直存在的问题
    loading.close();                        // 关闭遮罩层
    clearInterval(interval); // 清除计时器
  },30000)

  return loading;
}

// todo-lrc : 插件为什么在这里
//plugins
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
