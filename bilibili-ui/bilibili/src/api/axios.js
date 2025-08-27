
import axios from 'axios';

const server = axios.create({
    baseURL: 'http://localhost:8080', // 假设后端 API 地址前缀是 /api
    timeout: 5000
});

// 添加请求拦截器，在每个请求的header中附带token
server.interceptors.request.use(config => {
    const token = sessionStorage.getItem('token');
    if (token) {
        config.headers['token'] = token;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

server.interceptors.response.use(response => {
    return response;
}, error => {
    return Promise.reject(error);
});

export default server;
