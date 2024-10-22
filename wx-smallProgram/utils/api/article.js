import instance from '../../utils/request'

// 文章详情
export function articleDetails(id){
	return instance.request({
		url: `/home/show/article/${id}`,
		method: 'get',
	})
}
// 评论详情
export function getComment(id){
	return instance.request({
		url: `/home/show/article/comment/${id}`,
		method: 'get',
	})
}
// 文章点赞
export function articleLike(data){
	return instance.request({
		url: `/note/article-like`,
		method: 'post',
		data
	})
}
