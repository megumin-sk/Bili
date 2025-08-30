import server from "../utils/axios.js";

export function addOrder(data){
    return server({
        url: '/order/addOrder',
        method: 'POST',
        data
    })
}