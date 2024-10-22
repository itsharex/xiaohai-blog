import WxRequest from 'mina-request'
const instance = new WxRequest({
  baseURL: getApp().globalData.baseURL + getApp().globalData.baseAPI,
  timeout: 15000,
  isLoading: false // 不使用默认 loading
})

// 添加请求拦截器
instance.interceptors.request = (config) => {
  const token = wx.getStorageSync('token')
  if (token) {
    config.header['token'] = token
  }
  return config
}

// 添加响应拦截器
instance.interceptors.response = (response) => {
  const { isSuccess, data } = response
  // 未设置状态码则默认成功状态
  const code = response.data.code || 200;
  // 如果 isSuccess 为 false，说明执行了 fail 回调函数
  // 这时候就说明网络异常，需要给用户提示网络异常
  console.log(response,'code')
  if (code === 401){
    wx.showToast({
      title: data.msg
    })
    return Promise.reject('无效的会话，或者会话已过期，请重新登录。')

  }else{
    return data
  }
}


export default instance
