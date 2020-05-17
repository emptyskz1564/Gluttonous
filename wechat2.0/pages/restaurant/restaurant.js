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
    imageUrl: '',
    showAddons: false,
    restaurants: [],
    resImageUrls: [],
    restaurantList: [],
    resImageUrlList: [],
    stars: [],
    // 用户位置信息
    location: {
      longitude: app.globalData.location.longitude,
      latitude: app.globalData.location.latitude
    },
    lightStar: false,
    search: '',
    color: {
      page: '#ffe0ac',
      card: '#f9f9f9',
      label: '#82c4c3'
    }
  },
  // 图片预览
  togglePreviewRestImage: function (e) {
    this.setData({
      showAddons: !this.data.showAddons,
      imageUrl: e.currentTarget.dataset.url
    })
    this.previewRestImage.toggleAddonsImage()
  },
  // 查找餐厅
  searchRestaurant: function (e) {
    this.setData({
      search: e.detail.value
    })
    let that = this
    this.setData({
      restaurantList: (function () {
        // 创建新的数组，新数组中的元素是通过检查指定数组中符合条件的所有元素。
        return that.data.restaurants.filter(data => {
          // 如果有一个元素满足条件，则表达式返回true , 剩余的元素不会再执行检测;
          // 如果没有满足条件的元素，则返回false。
          return Object.keys(data).some(key => {
            // indexOf() 返回某个指定的字符在某个字符串中首次出现的位置，如果没有找到就返回-1
            return String(data[key]).toLowerCase().indexOf(that.data.search) > -1
          })
        })
      })()
    })
  },
  // 解析url
  decodeUrl: function (raw) {
    let urls = []
    for (let i = 0; i < raw.length; i++) {
      if (raw[i].resUrl) {
        let items = raw[i].resUrl.split('@')
        items.pop()
        urls.push(items)
      } else {
        urls.push(['http://47.111.252.78/images/exceptions/404.png'])
      }
    }
    return urls
  },
  //获取附近餐厅
  getNearRestaurant: function () {
    let that = this;
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/nearrestaurants/location/115.394633/31.795101',
      success: function (res) {
        if (res.statusCode === 200) {
          that.setData({
            restaurants: res.data,
            restaurantList: res.data,
            resImageUrls: that.decodeUrl(res.data),
            resImageUrlList: that.decodeUrl(res.data),
            stars: (function () {
              let status = []
              for (let i = 0; i < res.data.length; i++) {
                status.push(
                  false // 暂时规定全部为false，后期会修正
                )
              }
              return status
            })()
          })
        }
      }
    })
  },
  // 点击收藏/取消收藏
  star: function (e) {
    let name = 'stars[' + e.currentTarget.dataset.index + ']'
    this.setData({
      [name]: !this.data.stars[e.currentTarget.dataset.index]
    })
  },
  // 点击跳转地图
  toMap: function (e) {
    wx.navigateTo({
      url: './../map/map?resAdress=' + e.currentTarget.dataset.item,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.previewRestImage = this.selectComponent('#previewRestImage');
    this.getNearRestaurant();
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

  }
})