
import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        path: '/',
        redirect: '/hot'
    },
    {
        path: '/hot',
        name: 'Home',
        component: () => import('../public-views/Home.vue'),
    },
    {
        path: '/personalCenter',
        name: 'PersonalCenter',
        component: () => import('../personal-views/PersonalCenter.vue'),
        children:[
            {path: 'personDetail',name:'PersonDetail',component: () => import('../personal-views/PersonDetail.vue')},
            {path: 'comment',name:'Comment',component: () => import('../personal-views/Comment.vue')},
            {path: 'data',name:'Data',component: () => import('../personal-views/Data.vue')},
            {path: 'fans',name:'Fans',component: () => import('../personal-views/Fans.vue')},
            {path: 'follow',name:'Follow',component: () => import('../personal-views/Follow.vue')},
            {path: 'like',name:'Like',component: () => import('../personal-views/Like.vue')},
            {path: 'follow',name:'Follow',component: () => import('../personal-views/Follow.vue')},
            {path: 'reply',name:'Reply',component: () => import('../personal-views/Reply.vue')},
            {path: 'upload',name:'Upload',component: () => import('../personal-views/Upload.vue')},
            ]
    },
    {
        path: '/bangumi',
        name: 'Bangumi',
        component: () => import('../public-views/Bangumi.vue')
    },
    {
        path: '/mall',
        name: 'Mall',
        component: () => import('../shopping-views/Mall.vue'),
    },
    {
        path: '/mall/productDetail/:id',
        name: 'ProductDetail',
        props: true,
        component: () => import('../shopping-views/ProductDetail.vue')
    },
    {
        path: '/shopCar',
        name: 'ShopCar',
        component: () => import('../shopping-views/ShopCar.vue')
    },
    {
        path: '/video/:id', // 动态路由，用于视频播放页
        name: 'VideoPlayer',
        component: () => import('../public-views/VideoPlayer.vue')
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next)=>{
    const whiteList = ['/hot', '/bangumi', '/mall' , '/login' , '/register'];
    const token = sessionStorage.getItem("token")
    if (token){
        next();
    }else{
        if (whiteList.includes(to.path) || to.path.startsWith('/mall')){
            next();
        }else{
            next({path : '/login'});
        }
        }
});
export default router;
