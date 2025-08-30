
import axios from 'axios';

const server = axios.create({
    baseURL: 'http://localhost:8080', // 假设后端 API 地址前缀是 /api
    timeout: 5000
});

server.interceptors.request.use(config=>{
    const token = sessionStorage.getItem("token")
    if(token){
        config.headers["token"] = token
    }else {
        console.warn("session中的token为空")
    }
    return config
},error=>{
    console.warn("请求拦截器失败",error)
    return Promise.reject(error)
})

server.interceptors.response.use(resp=>{
    if (resp.data.code === 401){
        console.warn("token为空")
    }else if (resp.data.code === 402){
        console.warn("token异常")
        sessionStorage.removeItem("token")
        window.location.href = "/login"
    }
    return resp;
})

export default server;
