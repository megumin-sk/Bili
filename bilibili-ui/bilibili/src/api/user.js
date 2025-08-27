import server from "./axios.js";

export function login(data){
    return server({
        url: '/user/login',
        method: 'post',
        data:data
    })
}

export function queryUser(){
    return server({
        url: '/user/queryUserById',
        method: 'GET'
    })
}

export function updateUser(data){
    return server({
        url: '/user/updateUser',
        method: 'post',
        data:data
    })
}