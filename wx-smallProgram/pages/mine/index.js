// pages/homePage/index.js
import {loginApi} from "../../utils/api/mine";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    iconList:[
      {url:'../../images/mine/gitee.svg'},
      {url:'../../images/mine/github.svg'},
      {url:'../../images/mine/qq.svg'},
      {url:'../../images/mine/wechat.svg'},
    ],
    exhibitionlist:[
      {title:'文章',num:'78'},
      {title:'分类',num:'5'},
      {title:'标签',num:'39'},
      {title:'评论',num:'0'},
    ],
    show:false,
    navMenu:[
      {name:'登录',key:'1'},
      {name:'个人信息',key:'1'},
      {name:'登录',key:'1'},
      {name:'登录',key:'1'},
    ],
    loginShow:false,
    username:'',
    password:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.getInstance()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  getInstance() {
    if (typeof this.getTabBar === 'function' ) {
      this.getTabBar((tabBar) => {
        tabBar.setData({
          selected: 2
        })
      })
    }
  },
  openNav(){
    this.setData({ show: true });
  },
  onClose() {
    this.setData({ show: false });
  },
  loginHandle(){
    this.setData({ loginShow: true });
  },
  onCloseLogin(){
    this.setData({ loginShow: false });
  },
  login(){
    loginApi({
      username:this.data.username,
      password:this.data.password,
    }).then(res=>{
      wx.setStorageSync('token',res.data)
      this.onCloseLogin()
    })
  }
})
