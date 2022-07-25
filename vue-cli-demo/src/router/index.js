import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/home/Home.vue'
import Login from '../views/login/Login.vue'


Vue.use(VueRouter)

const router = new VueRouter({
  routes:[
    {path:'/',redirect:'login'},//定义重定向
    {path:'/login',name:'Login',component:Login},
    { 
      path:'/home',
      name:'Home',
      component:Home,
      children:[
          {
            path:'/teacher',
            name:'Teacher',
            component:()=> import('../components/teacher/Teacher.vue')  //不需要先导入,注册时导入
          },
          {
            path:'/user',
            name:'User',
            component:()=> import('../components/user/User.vue')  //不需要先导入,注册时导入
          },

      ]
    },
  ]
})
//守卫路由
router.beforeEach((to,from,next)=>{
  if(to.path === '/login'){ //如果请求的path为login 
    next(); //放行
  }
  else{ //否则，路由的path不是请求login
    const token = localStorage.getItem('strToken');  //获取token
    token ? next() : next('/login') //如果取到了，放行，没有取到转路径login
  }
})

export default router
