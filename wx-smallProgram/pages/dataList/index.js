// pages/dataList/index.js
import {reqSwiperData} from "../../utils/api/homePage";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    id:null,
    loadStatus:0,// 数据状态
    paramsData:{
      pageNum:1,
      pageSize:10,
      total:0,
      allPageNum:1,// 总页数
      type:5,
    },
    contentList:[],
    name:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      id:options.id,
      name:options.name,
    },()=>{
      this.getContentList()
    })
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
    this.setData({
      "paramsData.pageNum":1
    },()=>{
      this.getContentList(false)
    })
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {
    if (this.data.paramsData.pageNum < this.data.paramsData.allPageNum){
      this.setData({
        "paramsData.pageNum":this.data.paramsData.pageNum + 1
      },()=>{
        this.getContentList()
      })
    }else if (this.data.paramsData.pageNum == this.data.paramsData.allPageNum){
      this.setData({
        loadStatus:2
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  getContentList(status = true){
    if (!status){
      wx.pageScrollTo({
        scrollTop: 0,
        duration: 300
      })
    }
    this.setData({
      loadStatus:1
    })
    reqSwiperData({
      pageNum:this.data.paramsData.pageNum,
      pageSize:this.data.paramsData.pageSize,
      type:this.data.paramsData.type,
      id:this.data.id,
    }).then(res=>{
      this.setData({
        contentList:status?this.data.contentList.concat(res.data.records):res.data.records,
        "paramsData.allPageNum":Math.ceil(res.data.total / res.data.size),
        loadStatus:0
      })
    }).catch(err=>{
      this.setData({
        loadStatus:3
      })
    })
  },

})
