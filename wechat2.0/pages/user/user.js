// pages/user/user.js
const requestUtil = require('./../../utils/request.js')
Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo: null,
    showLabels: false,
    label1: '口味偏好',
    label2: '口味偏好',
    label3: '口味偏好',
    labels: [],
    labelValue: [0, 0, 0]
  },
  // 获取所有的口味标签
  getAllLabels: function () {
    let that = this
    wx.request({
      url: requestUtil.apiUrl + '/userlable/first',
      method: 'GET',
      success: function (res) {
        console.log(res.statusCode, res.data)
        if (res.statusCode === 200) {
          that.setData({
            labels: res.data
          })
        } else {
          requestUtil.requestExceptionHandler(res.statusCode)
        }
      },
      fail: function(err) {
        console.log(err)
      }
    })
  },
  openSelection: function () {
    this.setData({
      showLabels: !this.data.showLabels
    })
  },
  selectLabels: function (e) {
    const val = e.detail.value
    this.setData({
      label1: this.data.labels[val[0]].lableContent,
      label2: this.data.labels[val[1]].lableContent,
      label3: this.data.labels[val[2]].lableContent
    })
  },
  labelsChosen: function () {
    this.setData({
      showLabels: !this.data.showLabels
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getAllLabels()
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
    return {
      title: '饕餮食客，海吃海喝'
    }
  },

  handleGetuserinfo(e){
    let {userInfo} = e.detail;
    userInfo["userId"]=this.data.userId;
    console.log(userInfo);
    wx.setStorageSync('userInfo', userInfo);
    wx.reLaunch({
      url: '/pages/user2/user'
    })
  },

  login:function () {
    let that= this;
    wx.login({
      success:function(res){
        console.log(res.code);
        wx.request({
          url: 'https://hailicy.xyz/wechatpro/v1/login/'+res.code,
          success:function(res){
            console.log(res);
            that.setData({
              userId:res.data
            })
          }
        })
      }
    })
  }
})