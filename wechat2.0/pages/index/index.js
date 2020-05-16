//index.js
let requestUtil = require('./../../utils/request.js')
//获取应用实例
const app = getApp()
Page({
  data: {
    cardsfavorStatus: [],
    nearCardFavorStatus: [],
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
  // 获取位置信息
  getLocations: function () {},
  // 获得附近的打卡信息
  getNearCards: function () {
    let that = this;
    if(wx.getStorageSync('userId')){
      wx.request({
        url: 'https://hailicy.xyz/wechatpro/v1/vipcards/'+wx.getStorageSync('userId'),
        success:function(res){
          if (res.statusCode === 200) {
            // 请求成功
            let nearCardFavorStatus = [];
            for(let i = 0 ; i < res.data.length; i ++){
              nearCardFavorStatus.push(false);
            }
            that.setData({
              nearCardFavorStatus:nearCardFavorStatus,
              nearCards: res.data,
              nearCardsImageUrls: (function () {
                let imageUrls = []
                for (let i = 0; i < res.data.length; i++) {
                  imageUrls.push(
                    res.data[i].picUrl === null ? null : res.data[i].picUrl.split('@')[0]
                  )
                }
                return imageUrls
              })()
            })
            wx.setStorageSync('nearCard', that.data.nearCards)
          } else {
            that.setData({ exceptions: requestUtil.requestExceptionHandler(res.statusCode) })
          }
        }
      })
    }
  },
  // 获得打卡内容
  getCards: function () {
    let that = this
    wx.request({
      url: requestUtil.apiUrl + '/hotcardusers',
      method: 'GET',
      success: function (res) {
        if (res.statusCode === 200) {
          // 请求成功
          let cardsfavorStatus = [];
          for(let i = 0 ; i < res.data.length; i ++){
            cardsfavorStatus.push(false);
          }
          that.setData({
            cardsfavorStatus:cardsfavorStatus,
            cards: res.data,
            cardsImageUrls: (function () {
              let imageUrls = []
              for (let i = 0; i < res.data.length; i++) {
                imageUrls.push(
                  res.data[i].picUrl === null ? null : res.data[i].picUrl.split('@')[0]
                )
              }
              return imageUrls
            })()
          })
          
          wx.setStorageSync('hotCard', that.data.cards)
        } else {
          that.setData({ exceptions: requestUtil.requestExceptionHandler(res.statusCode) })
        }
      },
      // 其他错误
      fail: function (err) {
      }
    })
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
  // 点赞
  favor: function (e) {
    console.log(e);
    let that=this;
    let userId = wx.getStorageSync('userId');
    let cardId=null;
    if(e.currentTarget.dataset.sign === "hotCard"){
      cardId=wx.getStorageSync('hotCard')[e.currentTarget.dataset.cardid].cardId;
    }
    else{
      cardId=wx.getStorageSync('nearCard')[e.currentTarget.dataset.cardid].cardId;
    }
    if (userId !== '' && userId !== null) {
      wx.request({
        
        url: 'https://hailicy.xyz/wechatpro/v1/carduserlike/'+cardId+"/" + userId,
        method:'POST',
        success:function(res){
          console.log(res);
          wx.showToast({
            title: '操作成功'
          })
          if(e.currentTarget.dataset.sign === "hotCard"){
            let cardsfavorStatus = that.data.cardsfavorStatus;
            cardsfavorStatus[e.currentTarget.dataset.cardid]=true;
            that.setData({
              cardsfavorStatus:cardsfavorStatus
            })
          }
          else{
            let nearCardFavorStatus = that.data.nearCardFavorStatus;
            nearCardFavorStatus[e.currentTarget.dataset.cardid]=true;
            that.setData({
              nearCardFavorStatus:nearCardFavorStatus
            })
          }
        }
      })
    } else {
      wx.showToast({
        title: '请先登录再点赞',
        icon: 'none'
      })
    }
  },
  // 跳转到打卡详情
  toCard: function (e) {
    wx.navigateTo({
      url: '../card/card?id=' + e.currentTarget.dataset.item+'&sign='+e.currentTarget.dataset.sign,
    })
  }
})
