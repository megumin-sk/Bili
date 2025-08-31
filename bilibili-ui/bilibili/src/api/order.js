import server from "../utils/axios.js";

export function addOrder(data){
    return server({
        url: '/order/addOrder',
        method: 'POST',
        data
    })
}

export function getOrder(){
    return server({
        url: '/order/queryOrder',
        method: 'GET',
    })
}