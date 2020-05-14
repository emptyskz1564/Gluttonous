// pages/user/user.js
const requestUtil = require('./../../utils/request.js')
const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo: null,
    showLabels: false,
    clickedIndex: 0,
    label1: {
      lableId: '-1',
      lableContent: '口味偏好'
    },
    label2: {
      lableId: '-1',
      lableContent: '口味偏好'
    },
    label3: {
      lableId: '-1',
      lableContent: '口味偏好'
    },
    labels: [],
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    hasUserInfo: false
  },
  // 获取所有的口味标签
  getAllLabels: function () {
    let that = this
    wx.request({
      url: requestUtil.apiUrl + '/userlable/first',
      method: 'GET',
      success: function (res) {
        if (res.statusCode === 200) {
          that.setData({
            labels: res.data
          })
        } else {
          requestUtil.requestExceptionHandler(res.statusCode)
        }
      }
    })
  },
  openSelection: function (e) {
    this.setData({
      showLabels: !this.data.showLabels,
      clickedIndex: e.currentTarget.dataset.index
    })
  },
  labelsChosen: function () {
    // 关闭选项
    this.setData({
      showLabels: !this.data.showLabels
    })
    // 如果什么都没选，就配第一个
    switch (this.data.clickedIndex) {
      case '1':
        if (this.data.label1.lableId === '-1') {
          this.setData({
            label1: {
              lableId: this.data.labels[0].lableId,
              lableContent: this.data.labels[0].lableContent
            }
          })
        }
        break
      case '2':
        if (this.data.label2.lableId === '-1') {
          this.setData({
            label2: {
              lableId: this.data.labels[0].lableId,
              lableContent: this.data.labels[0].lableContent
            }
          })
        }
        break
      case '3':
        if (this.data.label3.lableId === '-1') {
          this.setData({
            label3: {
              lableId: this.data.labels[0].lableId,
              lableContent: this.data.labels[0].lableContent
            }
          })
        }
        break
    }
    // 提交选项
    // ============================================
    // wx.request({
    //   url: 'url',
    // })
    // ============================================
    // 在这里完善提交口味的接口
  },
  selectItem: function (e) {
    switch (e.currentTarget.dataset.item) {
      case '1':
        this.setData({
          label1: {
            lableId: this.data.labels[e.detail.value[0]].lableId,
            lableContent: this.data.labels[e.detail.value[0]].lableContent
          }
        })
        break
      case '2':
        this.setData({
          label2: {
            lableId: this.data.labels[e.detail.value[0]].lableId,
            lableContent: this.data.labels[e.detail.value[0]].lableContent
          }
        })
        break
      case '3':
        this.setData({
          label3: {
            lableId: this.data.labels[e.detail.value[0]].lableId,
            lableContent: this.data.labels[e.detail.value[0]].lableContent
          }
        })
        break
    }
  },

  getWechatUserInfo: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        wx.setStorageSync('userInfo', res);
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          wx.setStorageSync('userInfo', res);
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  handleGetuserinfo: function (e) {
    let authStatus = e.detail.errMsg
    wx.setStorageSync('userInfo', e.detail.userInfo)
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true,
      canIUse: authStatus
    })
    if (authStatus === 'getUserInfo:ok') {
      this.getWechatUserInfo()
      wx.login({
        success: (res) => {
          wx.request({
            url: requestUtil.apiUrl + '/login/' + res.code,
            method: 'GET',
            success: (res) => {
              app.globalData.user.userId = res.data
              wx.setStorageSync('userId', res.data)
              wx.request({
                url: requestUtil.apiUrl + '/vuserlable/' + wx.getStorageSync('userId'),
                success: function (res) {
                  console.log(res);
                }
              })
            }
          })
        }
      })
    } else {
      wx.showToast({
        title: '授权后可以使用更多功能',
        icon: 'none'
      })
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getAllLabels()
    this.getWechatUserInfo()
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},
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
  toStar: function () {
    wx.navigateTo({
      url: './../star/star',
    })
  },
  toMyCard: function () {
    wx.navigateTo({
      url: './../myCard/myCard',
    })
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
    return {
      title: '饕餮食客，海吃海喝'
    }
  }
})