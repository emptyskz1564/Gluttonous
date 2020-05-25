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
    img_url:[],
    cardFormResId:null,
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
    lightStar: false,
    search: '',
    color: {
      page: '#ffe0ac',
      card: '#f9f9f9',
      label: '#82c4c3'
    }
  },
  powerDrawer: function (e) { 
    console.log(e);
    var currentStatu = e.currentTarget.dataset.statu;
    if(currentStatu === "open"){
      this.setData({
        cardFormResId:e.currentTarget.dataset.resid
      })
    } 
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
    str["resId"]=this.data.cardFormResId;
    if(str.LabelId2!=""){
      str.LabelId2=1
    }
    if(str.LabelId3!=""){
      str.LabelId3=1
    }
    console.log("上传的打卡信息：");
    console.log(str);
    
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
  //获取收藏餐厅
  getStarRestaurant: function () {
    let that = this;
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/vuserrestaurant/'+wx.getStorageSync('userId'),
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
                  true // 暂时规定全部为false，后期会修正
                )
              }
              return status
            })()
          })
          
          let restaurantList = [];
          let tmp = that.data.restaurantList[0];
          restaurantList.push(that.data.restaurantList[0]);
          restaurantList[0]["lableContent1"] = that.data.restaurantList[0].lableContent;
          let labelIndex = 2;
          let resIndex = 0;
          for(let i = 1;i<that.data.restaurantList.length;i++){
            if(tmp.resId === that.data.restaurantList[i].resId){
              restaurantList[resIndex]["lableContent"+labelIndex] = that.data.restaurantList[i].lableContent;
              labelIndex++;
            }else{
              for(let j = 0; j < (3-labelIndex+1); ){
                restaurantList[resIndex]["lableContent"+labelIndex] = null;
                labelIndex++;
              }
              restaurantList.push(that.data.restaurantList[i]);
              resIndex++;
              restaurantList[resIndex]["lableContent1"] = that.data.restaurantList[i].lableContent;
              labelIndex = 2;
            }
          }
          for(let j = 0; j < (3-labelIndex+1); ){
            restaurantList[resIndex]["lableContent"+labelIndex] = null;
            labelIndex++;
          }
          that.setData({
            restaurantList:restaurantList
          })
          console.log(that.data.restaurantList);
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
    this.getStarRestaurant()
    console.log(this.data.restaurantList);
    
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

  // 跳转到餐厅详情
  toRest: function (e) {
    wx.navigateTo({
      url: '../restDetail/restDetail?id=' + e.currentTarget.dataset.item,
    })
  }

})