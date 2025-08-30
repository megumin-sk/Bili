import server from "../utils/axios.js";

export function queryAllProduct(){
    return server({
        url: 'product/queryAll',
        method: 'GET'
    })
}

export function queryById(id){
    return server({
        url: `product/queryById/${id}`,
        method: 'GET'
    })
}