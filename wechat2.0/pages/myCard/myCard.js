//index.js
let requestUtil = require('./../../utils/request.js')
//获取应用实例
const app = getApp()
Page({
  data: {
    myCardsfavorStatus: [],
    hasData:false,
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
  },

  // 获得打卡内容
  getCards: function () {
    let that = this
    wx.request({
      url: requestUtil.apiUrl + '/cardusers/'+wx.getStorageSync('userId'),
      method: 'GET',
      success: function (res) {
        if (res.statusCode === 200) {
          // 请求成功
          let myCardsfavorStatus = [];
          for(let i = 0 ; i < res.data.length; i ++){
            myCardsfavorStatus.push(false);
          }
          that.setData({
            myCardsfavorStatus:myCardsfavorStatus,
            hasData:true,
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
          wx.setStorageSync('myCard', that.data.cards)
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
    cardId=wx.getStorageSync('myCard')[e.currentTarget.dataset.cardid].cardId;
    if (userId !== '' && userId !== null) {
      wx.request({
        url: 'https://hailicy.xyz/wechatpro/v1/carduserlike/'+cardId+"/" + userId,
        method:'POST',
        success:function(res){
          console.log(res);
          wx.showToast({
            title: '操作成功'
          })
          let myCardsfavorStatus = that.data.myCardsfavorStatus;
          myCardsfavorStatus[e.currentTarget.dataset.cardid]=true;
          that.setData({
            myCardsfavorStatus:myCardsfavorStatus
          })
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
      url: '../card2/card2?id=' + e.currentTarget.dataset.item+'&sign='+e.currentTarget.dataset.sign,
    })
  }
})
