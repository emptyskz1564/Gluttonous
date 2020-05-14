// pages/card/card.js
let requestUtil = require('./../../utils/request.js')
//获取应用实例
const app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    // 回复框状态
    inputShowed: false,
    //回复内容
    inputValue:"",
    // 回复框值
    inputVal: "",
    //将要回复的评论id,默认为回复打卡
    disId:0,
    parentThread:"",
    parentId:null,
    disList:[],     //评论列表
    userLike:[],      //用来记录每个评论的点赞状态
    paramIndex: '',
    card: null,
    cardImageUrls: [],
    cardVideoUrls: [],
    exceptions: {
      // 请求是否失败
      isError: false,
      // 请求失败内容
      errorMessage: '',
      errorDetail: '',
    },
    favorStatus: false,
    cardLike:0,
    imageUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
  },

  
  inputBind: function(event) {
    this.setData({
        inputValue: event.detail.value
    })
    console.log(this.data.inputValue)
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    let index = options.id;
    this.setData({ paramIndex: options.id })
    this.setData({
      card:wx.getStorageSync('hotCard')[index],
      cardImageUrls: (function() {
        return wx.getStorageSync('hotCard')[index].picUrl === null ? null : (function () {
          let urls = wx.getStorageSync('hotCard')[index].picUrl.split('@')
          urls.pop()
          return urls
        })()
      })()
    })
    console.log(this.data.card);
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1//discusses/asc/' + wx.getStorageSync('hotCard')[index].cardId,
      success:function(res){
        console.log(res);
        that.setData({
          disList:res.data
        })
        let userLike=[];
        for(let i=0;i<that.data.disList.length;i++){
          userLike.push(true);
        }
        that.setData({
          userLike:userLike
        })
        console.log(res);
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.previewUserImage = this.selectComponent('#previewUserImage') // 获得组件
    this.previewAddonsImage = this.selectComponent('#previewAddonsImage') // 获得组件
  },
  // 完善组件事件
  toggleUserImage: function () {
    this.previewUserImage.toggleUserImage()
  },
  toggleAddonsImage: function (e) {
    let that = this
    that.setData({
      imageUrl: e.currentTarget.dataset.url
    })
    that.previewAddonsImage.toggleAddonsImage()
  },
  // 点赞
  favor: function () {
    let that=this;
    let userId = wx.getStorageSync('userId');
    let hotCard=wx.getStorageSync('hotCard');
    hotCard[that.data.paramIndex]["cardLike"] += 1;
    wx.setStorageSync('hotCard', hotCard);
    if (userId !== '' && userId !== null) {
      wx.request({
        url: 'https://hailicy.xyz/wechatpro/v1/carduserlike/'+that.data.card.cardId+"/" + userId,
        method:'POST',
        success:function(res){
          console.log(res);
          wx.showToast({
            title: '操作成功'
          })
        }
      })
      this.setData({
        favorStatus: !this.data.favorStatus,
        cardLike: this.data.cardLike + (this.data.favorStatus ? -1 : 1)
      })
    } else {
      wx.showToast({
        title: '请先登录再点赞',
        icon: 'none'
      })
    }
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

  handleLike(e){
    let that=this;
    let userLike=that.data.userLike;
    console.log(e);
    userLike[e.target.dataset.cardid]=true;
    that.setData({
      userLike:userLike
    })
  },
  
  handleDisLike(e){
    let userInfo=wx.getStorageSync('userInfo');
    let userId=wx.getStorageSync("userId");
    let disId=e.target.dataset.disid;
    let that=this;
    let card=that.data.disList
    let userLike=that.data.userLike;
    userLike[e.target.dataset.cardid]=false;
    that.setData({
      userLike:userLike
    })
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/discussuserlike/'+disId+"/"+userId+"/"+card[0].cardId,
      method:'POST',
      success:function(res){
        console.log(res);
      }
    })
  },

  
  setInput:function (e) {
    console.log(e);
    this.setData({
      parentId:e.target.dataset.parentid,
      inputShowed:true,
      parentThread:e.target.dataset.parentthread
    })
  },

  reply:function (e) {
    console.log(e);
    let that=this;
    let parentThread=that.data.parentThread;
    let disId=that.data.disId;
    let parentId=that.data.parentId;
    console.log("parentId:"+parentId);
    let disThread="";
    if(parentId===null){
      let count=1;
      //自动生成一个父评论的disThread
      for(let i=0;i<that.data.disList.length;i++){
        if(that.data.disList[i].parentId===0)
          count++;
      }
      disThread=count+"/";
      parentId=0;
    }else{
      let count=0;
      //自动生成一个子评论的disThread
      //先分割父评论
      let head=parentThread.split("/");
      let re =null;
      if(head[0]==="1"){re=/^1.*$/;}  
      if(head[0]==="2"){re=/^2.*$/;}  
      if(head[0]==="3"){re=/^3.*$/;}  
      if(head[0]==="4"){re=/^4.*$/;}  
      if(head[0]==="5"){re=/^5.*$/;}  
      if(head[0]==="6"){re=/^6.*$/;} 
      if(head[0]==="7"){re=/^7.*$/;}
      if(head[0]==="8"){re=/^8.*$/;}  
      if(head[0]==="9"){re=/^9.*$/;}  
      if(head[0]==="10"){re=/^10.*$/;}  
      for(let i=0;i<that.data.disList.length;i++){
        if(re.test(that.data.disList[i].disThread)){
          count++;
        }
      }
      parentId=head[0];
      disThread=head[0]+"."+count+"/";
    }

    let userInfo=wx.getStorageSync('userInfo');
    let userId=wx.getStorageSync("userId");
    
    let str={
          cardId:that.data.card.cardId,
          disContent:that.data.inputValue,
          parentId:parentId,
          parentThread:disThread,
          disuserId:userId
        };
    console.log(str);
    
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/discuss',
      data:{
        str:JSON.stringify(str)
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"     /*更改头部*/
      },
      method:'POST',
      success:function(res){
        console.log(res);
        wx.redirectTo({
          url: '../card/card?id='+that.data.paramIndex,
        })
      }
    })
  }
})