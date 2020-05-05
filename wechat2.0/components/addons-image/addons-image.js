// components/addons-image/addons-image.js
Component({
  options: {
    multipleSlots: true
  },
  /**
   * 组件的属性列表
   */
  properties: {
    imageUrl: {
      type: String,
      value: 'http://47.111.252.78/images/exceptions/nope.png'
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    hidden: true,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    toggleAddonsImage: function () {
      let that = this
      this.setData({
        hidden: !that.data.hidden
      })
    }
  }
})
