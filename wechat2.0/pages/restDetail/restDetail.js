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
    formShow: false,
    isCollect:false,
    paramIndex: '', //resId
    rest: null,
    restLabel:[],
    restImageUrls: [],
    img_url:[],
    cardForm:{
      userId:null,
      resId:null,
      cardContent:"",
      cardTitle:"",
      bestFood:"",
      selfLable1:"",
      LabelId1:1,
      selfLable2:"",
      LabelId2:-1,
      selfLable3:"",
      LabelId3:-1,
    },
    labelIndex:0,
    addLabel:[],
    showModalStatus: false,
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
  // 打开或关闭打卡表单
  toggleCardForm: function () {
    this.setData({
      formShow: !this.data.formShow
    })
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

  
  powerDrawer: function (e) { 
    console.log(e);
    var currentStatu = e.currentTarget.dataset.statu; 
    this.util(currentStatu) //动画处理
   }, 
   util: function(currentStatu){ 
    /* 动画部分 */
    // 第1步：创建动画实例  
    var animation = wx.createAnimation({ 
     duration: 200, //动画时长 
     timingFunction: "linear", //线性 
     delay: 0 //0则不延迟 
    }); 
      
    // 第2步：这个动画实例赋给当前的动画实例 
    this.animation = animation; 
    
    // 第3步：执行第一组动画 
    animation.opacity(0).rotateX(-100).step(); 
    
    // 第4步：导出动画对象赋给数据对象储存 
    this.setData({ 
     animationData: animation.export() 
    }) 
      
    // 第5步：设置定时器到指定时候后，执行第二组动画 
    setTimeout(function () { 
     // 执行第二组动画 
     animation.opacity(1).rotateX(0).step(); 
     // 给数据对象储存的第一组动画，更替为执行完第二组动画的动画对象 
     this.setData({ 
      animationData: animation 
     }) 
       
     //关闭 
     if (currentStatu == "close") { 
      this.setData( 
       { 
        showModalStatus: false
       } 
      ); 
     } 
    }.bind(this), 200) 
     
    // 显示 
    if (currentStatu == "open") { 
     this.setData( 
      { 
       showModalStatus: true
      } 
     ); 
    } 
   } ,

     //表单提交按钮

  formSubmit: function (e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    this.setData({
      cardForm:e.detail.value
    })
    this.send();
  },

   //选择本地图片
  chooseimage:function(){
    var that = this;
    wx.chooseImage({
      count: 9, // 默认9  
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
      success: function (res) {
        if (res.tempFilePaths.length>0){
          //图如果满了9张，不显示加图
          if (res.tempFilePaths.length === 9){
            that.setData({
              hideAdd:1
            })
          }else{
            that.setData({
              hideAdd: 0
            })
          }
 
          //把每次选择的图push进数组
          let img_url = that.data.img_url;
          for (let i = 0; i < res.tempFilePaths.length; i++) {
            img_url.push(res.tempFilePaths[i])
          }
          that.setData({
            img_url: img_url
          })
          
        }
        
      }
    })  
  },
  //发布按钮事件
  send:function(){
    let that = this;
    let user_id = wx.getStorageSync('userId');
    if(that.data.cardForm.cardTitle===""&&that.data.cardForm.cardContent===""){
      console.log("没有打卡信息上传");
      that.util("close");
    }else{
      that.img_upload()
    }
    // wx.showLoading({
    //   title: '上传中',
    // })
  },
  //图片上传
  img_upload: function () {
    let userId = wx.getStorageSync('userId');
    let str=this.data.cardForm;
    str["userId"]=userId;
    /////////////////餐厅id如何获取
    str["resId"]=this.data.paramIndex;
    if(str.LabelId2!=""){
      str.LabelId2=1
    }
    if(str.LabelId3!=""){
      str.LabelId3=1
    }
    str=JSON.stringify(str);
    let that = this;
    let img_url = that.data.img_url;
    let cardId="";
    //let img_url_ok = [];
    wx.uploadFile({
      filePath: img_url[0],
      name: 'files',
      url: 'https://hailicy.xyz/wechatpro/v1/card/first',
      formData:{
        str:str
      },
      header: {
        "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"     /*更改头部*/
      },
      success:function(res){
        cardId=res.data
        console.log(res);
        if(img_url.length===0){
          wx.showToast({
            title: '打卡成功',
            duration: 2000
          });
          that.util("close");
        }
      }
    })
    //上传剩下的图片
    for(let i=1; i< img_url.length; i++){
      wx.uploadFile({
        filePath: img_url[i],
        name: 'files',
        url: 'https://hailicy.xyz/wechatpro/v1/card/second',
        formData:{
          cardId:JSON.stringify(cardId)
        },
        header: {
          "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"     /*更改头部*/
        },
        success:function(res){
          console.log(res);
          wx.showToast({
            title: '打卡成功',
            duration: 2000
          });
          that.util("close"); 
        }
      })
    }
  } ,


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