// components/commentsSection/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    commentList:{
      type:Array,
      value:[]
    },
    child:{
      type:Boolean,
      value: false
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    fileUrl:getApp().globalData.fileUrl,
  },
created() {
},
  /**
   * 组件的方法列表
   */
  methods: {

  }
})
