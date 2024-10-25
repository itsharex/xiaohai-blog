import instance from '../../utils/request'

// 登录
export function loginApi(data){
    return instance.request({
        url: '/login',
        method: 'post',
        data
    })
}
export function getInfo(data){
    return instance.request({
        url: '/system/user/info',
        method: 'get',
        data
    })
}
