import instance from '../../utils/request'

// 分类
export function getCategory(data){
    return instance.request({
        url: '/home/show/category',
        method: 'get',
        data
    })
}
