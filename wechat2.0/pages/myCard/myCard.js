//index.js
let requestUtil = require('./../../utils/request.js')
//获取应用实例
const app = getApp()
Page({
  data: {
    hasData: false,
    // 打卡内容
    cards: [],
    nearCards: [],
    cardsImageUrls: [],
    nearCardsImageUrls: [],
    exceptions: {
      // 请求是否失败
      isError: false,
      // 请求失败内容
      errorMessage: '',
      errorDetail: '',
    },
    warnings: {
      // 请求是否无结果
      isWarning: true,
      // 请求无结果原因
      message: '食客正在收拾餐具……',
      detail: '该功能尚未开发，请等待官方公测公告',
    },
    // 用户位置信息
    location: {
      longitude: app.globalData.location.longitude,
      latitude: app.globalData.location.latitude
    },
    // 导航栏
    tabs: [
      { id: 0, value: "热门", isActive: true },
      { id: 1, value: "推荐-VIP专享", isActive: false }
    ]
  },
  // 根据id获得打卡内容
  getCards: function () {
    
  },
  onLoad: function () {
    // 加载时立即获得打卡内容
    this.getCards()
    this.getLocations()
    this.getNearCards()
  },
  onReady: function () {},
  // 切换tab
  handleTabsItemChange(e) {
    const {index} = e.detail
    let {tabs} = this.data
    tabs.forEach((item, i) => item.isActive = i === index)
    this.setData({ tabs })
  },
  // 跳转到打卡详情
  toCard: function (e) {
    wx.navigateTo({
      url: '../card/card?id=' + e.currentTarget.dataset.item,
    })
  }
})
