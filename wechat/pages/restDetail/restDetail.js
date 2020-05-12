// pages/restDetail/restDetail.js
var app = getApp()
var API = require('../../utils/restDtilapi.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userid:"",
    list:{},
      // 是否收藏
      isCollect:false,

        // 自定义自己喜欢的颜色
        colorArr: ["#EE2C2C", "#ff7070", "#EEC900", "#4876FF", "#ff6100",
        "#7DC67D", "#E17572", "#7898AA", "#C35CFF", "#33BCBA", "#C28F5C",
        "#FF8533", "#6E6E6E", "#428BCA", "#5cb85c", "#FF674F", "#E9967A",
        "#66CDAA", "#00CED1", "#9F79EE", "#CD3333", "#FFC125", "#32CD32",
        "#00BFFF", "#68A2D5", "#FF69B4", "#DB7093", "#CD3278", "#607B8B"],
      // 存储随机颜色
      randomColorArr: [],

  },

  Collecting:function(){
    let that=this;
    console.log("加入收藏");
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/userrestaurant/like/'+this.data.userid+"/"+this.data.list.resId,
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
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/userrestaurant/hate/'+this.data.userid+"/"+this.data.list.resId,
      method:'POST',
      success:function(res){
        that.setData({
          isCollect:false
        })
        console.log(res);
      }
    })
  },

  getRandomColor:function(){
    let that=this,
      labLen = 4,
      colorArr = that.data.colorArr,
      colorLen = colorArr.length,
      randomColorArr = [];
    //判断执行
    do{
      let random = colorArr[Math.floor(Math.random() * colorLen)];
      randomColorArr.push(random);
      labLen--;
    } while (labLen > 0)
    that.setData({ 
      randomColorArr: randomColorArr
    });
    console.log(
      that.data.randomColorArr
    );
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that=this;
    let userInfo=wx.getStorageSync('userInfo');
    that.setData({
      userid:userInfo.userId
    })
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/restaurants/'+options.resId,
      success:function(res){
        let list=res.data;
        wx.request({
          url: 'https://hailicy.xyz/wechatpro/v1/vreslable/'+options.resId,
          success:function(res){
            let url=list.resUrl;
            let urls=url.split("@");
            list["resUrl"]=urls;
            let labels=[];
            for(let i=0;i<res.data.length;i++){
              labels.push(res.data[i].lableContent);
            }
            list["labels"]=labels;
            that.setData({
              list:list
            })
            console.log(list);
          }
        })
      }
    })
    that.getRandomColor();

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

  //点击餐厅图片放大预览
  handlePreviewImg(e){
    //接受传递过来的图片url  
    //console.log(e);
    const urls=e.target.dataset.url;
    console.log("图片放大");
    wx.previewImage({
      current:urls[0],
      urls:urls
    })
  }
})