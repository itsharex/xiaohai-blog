// components/functionalDomain/index.js
import {articleLike} from "../../utils/api/article";

Component({
  /**
   * 组件的属性列表
   */
  properties: {
    clickLike:{
      value:null
    },
    articleId:{
      type:Number,
      value:null
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    onShareAppMessage() {
      const promise = new Promise(resolve => {
        setTimeout(() => {
          resolve({
            title: '自定义转发标题'
          })
        }, 2000)
      })
      return {
        title: '自定义转发标题',
        path: '/page/user?id=123',
        promise
      }
    },
    // 点赞
    likeHandle(){
      articleLike({
        articleId:this.properties.articleId,
        clickLike:this.properties.clickLike == null || this.properties.clickLike == 0?1:0
      }).then(res=>{
        this.triggerEvent('refresh')
      })
    }
  }
})
