import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '报送平台',
    props:true,
    component: () => import('@/views/Home'),
    children: [
      {
        path: '/Grep',
        name: '查询文本',
        props:true,
        component: () => import('@/components/Grep')
      },
      {
        path: '/Find',
        name: '查询文件',
        props:true,
        component: () => import('@/components/Find.vue')
      }
    ]
  }
]

const router = new VueRouter({
  mode: "history",
  // mode: 'hash',
  routes
})

export default router
