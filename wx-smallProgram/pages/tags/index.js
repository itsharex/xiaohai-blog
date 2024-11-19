// pages/tags/index.js
import {getTags} from "../../utils/api/tags";

Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    tagsList:[]
  },
  pageLifetimes: {
    show: function() {
      this.getInstance()
    }
  },
  created(){
    this.getTags()
  },
  /**
   * 组件的方法列表
   */
  methods: {
    getInstance() {
      if (typeof this.getTabBar === 'function' ) {
        this.getTabBar((tabBar) => {
          tabBar.setData({
            selected: 2
          })
        })
      }
    },
    getTags(){
      getTags().then(res=>{
        this.setData({
          tagsList:res.data
        })
      })
    },
    getList(e){
      let {id,name} = {...e.currentTarget.dataset}
      wx.navigateTo({
        url:`/pages/dataList/index?id=${id}&name=${name}&type=${5}`
      })
    }
  }
})
