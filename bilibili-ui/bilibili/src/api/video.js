
import request from '../utils/request.js';

export const getVideoList = (params) => {
    return request({
        url: '/videos',
        method: 'get',
        params
    });
};

export const getVideoById = (id) => {
    return request({
        url: `/videos/${id}`,
        method: 'get'
    });
};
