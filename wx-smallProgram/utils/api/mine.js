import instance from '../../utils/request'

// 首页内容
export function loginApi(data){
    return instance.request({
        url: '/login',
        method: 'post',
        data
    })
}
