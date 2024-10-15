import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/views/Layout/index.vue";
import Login from "@/views/Login/index.vue";
import home  from '@/views/Layout/home.vue';
import GaitLogin from '@/views/Layout/GaitLogin.vue';
import IdentifyRecognition from '@/views/Layout/IdentifyRecognition.vue';
import CameraMonitoring from '@/views/Layout/CameraMonitoring.vue';
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/layout', name: 'layout', component: Layout,
      children:[
        { path: '', name: 'home', component: home, meta: { title: '首页' } },
        { path:'/layout/gait-login', name: 'gait-login', component: GaitLogin, meta: { title: '步态录入'} },
        { path: '/layout/identify-recognition', name: 'identify-recognition', component: IdentifyRecognition, meta: { title: '步态识别'}},
        { path: '/layout/camera-monitoring', name: 'camera-monitoring', component: CameraMonitoring, meta: { title: '摄像布防' }},
      ]
    },
    { path: '/', name: 'login', component: Login },

  ]
})

export default router
