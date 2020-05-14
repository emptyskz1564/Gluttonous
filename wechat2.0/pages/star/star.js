// pages/restaurant/restaurant.js
//index.js
let requestUtil = require('./../../utils/request.js')
//获取应用实例
const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    hasData: false,
    restaurants: [],
    resImageUrls:[],
  },
  //获取收藏餐厅
  getStarRestaurant: function () {
    let that=this;
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/vuserrestaurant/'+wx.getStorageSync('userId'),
      success:function(res){
        that.setData({
          hasData:true,
          restaurants:res.data,
          resImageUrls: (function () {
            let imageUrls = []
            for (let i = 0; i < res.data.length; i++) {
              imageUrls.push(
                res.data[i].picUrl === null ? null : res.data[i].resUrl.split('@')[0]
              )
            }
            return imageUrls
          })()
        })
      }
    })
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getStarRestaurant()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  // 跳转到餐厅详情
  toRest: function (e) {
    wx.navigateTo({
      url: '../restDetail/restDetail?id=' + e.currentTarget.dataset.item,
    })
  }

})