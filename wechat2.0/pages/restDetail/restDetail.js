// pages/restDetail/restDetail.js
// pages/card/card.js
let requestUtil = require('./../../utils/request.js')
//获取应用实例
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isCollect:false,
    paramIndex: '',
    rest: null,
    restLabel:[],
    restImageUrls: [],
    // 用户位置信息
    location: {
      longitude: app.globalData.location.longitude,
      latitude: app.globalData.location.latitude
    },
    exceptions: {
      // 请求是否失败
      isError: false,
      // 请求失败内容
      errorMessage: '',
      errorDetail: '',
    },
    imageUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this;
    this.setData({ paramIndex: options.id })
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/restaurants/'+that.data.paramIndex,
      method:'GET',
      success:function(res){
        console.log(res);
        // 请求成功
        if (res.statusCode === 200) {
          that.setData({
            rest: res.data,
            restImageUrls: (function() {
              return res.data.resUrl === null ? null : (function () {
                let urls = res.data.resUrl.split('@')
                urls.pop()
                return urls
              })()
            })()
          })
          wx.request({
            url: 'https://hailicy.xyz/wechatpro/v1/vreslable/'+that.data.rest.resId,
            method:'GET',
            success:function(res){
              that.setData({
                restLabel:res.data
              }) 
            }            
          })
        } else { // 其他失败情况
          that.setData({
            exceptions: requestUtil.requestExceptionHandler(res.statusCode)
          })
        }
        
      }
    })
    
  },


  Collecting:function(){
    let that=this;
    console.log("加入收藏");
    let userInfo=wx.getStorageSync('userInfo');
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/userrestaurant/like/'+userInfo["userId"]+"/"+that.data.rest.resId,
      method:'POST',
      success:function(res){
        that.setData({
          isCollect:true
        })
        console.log(res);
      }
    })
  },

  CancelCollecting:function(){
    console.log("取消收藏");
    let that=this;
    let userInfo=wx.getStorageSync('userInfo');
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/userrestaurant/hate/'+userInfo["userId"]+"/"+that.data.rest.resId,
      method:'POST',
      success:function(res){
        that.setData({
          isCollect:false
        })
        console.log(res);
      }
    })
  },

  collection:function(){

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.previewAddonsImageR = this.selectComponent('#previewAddonsImageR')
  },
  toggleAddonsImage: function (e) {
    let that = this
    that.setData({
      imageUrl: e.currentTarget.dataset.url
    })
    that.previewAddonsImageR.toggleAddonsImage()
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
  toMap:function(e) {
    wx.navigateTo({
    url: '../map/map?resAdress=' + e.currentTarget.dataset.item,
  })
  }
  
})