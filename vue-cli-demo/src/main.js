import Vue from 'vue'//没有相对路径的话,导入node_modules中的vue中的dist中的uve.js
import App from './App.vue'//导入相对路径,查找当前路径下的,可以省略.vue
import router from './router'//导入router目录中的index.js
import '@/assets/css/commons.css'
//关闭用于环境的提示信息
Vue.config.productionTip = false

//在main.js中引入
//引入element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//使用use安装
Vue.use(ElementUI)




import axios from '@/utils/axiosutil.js'
import qs from 'qs'

Vue.prototype.$axios=axios
Vue.prototype.$qs=qs

new Vue({
  router,//注册路由
  render: h => h(App)//指定根组件模板元素
}).$mount('#app')//挂载模板
