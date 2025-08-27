
import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/',
        redirect: '/hot'
    },
    {
        path: '/hot',
        name: 'Home',
        component: () => import('../views/Home.vue'),
    },
    {
        path: '/personalCenter',
        name: 'PersonalCenter',
        component: () => import('../views/PersonalCenter.vue'),
        children:[
            {path: 'personHome',name:'PersonHome',component: () => import('../views/PersonHome.vue')}
        ]
    },
    {
        path: '/bangumi',
        name: 'Bangumi',
        component: () => import('../views/Bangumi.vue')
    },
    {
        path: '/mall',
        name: 'Mall',
        component: () => import('../views/Mall.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/video/:id', // 动态路由，用于视频播放页
        name: 'VideoPlayer',
        component: () => import('../views/VideoPlayer.vue')
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to,from,next)=>{
    //定义白名单
const whiteList = ['/login','/register','/mall','/bangumi','/hot'];

const token = sessionStorage.getItem("token")
    if (token){
        next()
    }else {
        //如果没有token
        if (whiteList.includes(to.path)){
            next()
        }else {
            next('/login')
        }
    }
})
export default router;
