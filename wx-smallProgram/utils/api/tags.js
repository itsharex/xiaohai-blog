import instance from "../request";

export function getTags(){
    return instance.request({
        url: '/home/show/tags',
        method: 'get',
    })
}
