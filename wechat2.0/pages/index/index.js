<<<<<<< HEAD
Page({

  totalPage1:0,      //总页数
  currentPage1:1,    //当前页数
  pageSize:10,     //页面总条数
  totalSzie1:0,    //总条数

  totalPage2:0,      //总页数
  currentPage2:1,    //当前页数
  totalSzie2:0,    //总条数

  /**
   * 页面的初始数据
   */
  data: {
    labelList:[],
    picUrls:[],
    searchList:[],
    userLike:[],
    img_url: [],
    labelIndex:0,
    addLabel:[],
    showModalStatus: false,
    list1:[],    //总打卡列表
    showList1:[],    //显示的打卡列表
    list2:[],    //总餐厅列表
    showList2:[],    //显示的餐厅列表
    tabs:[
      {
        id:0,
        value:"热门",
        isActive:true
      },
      {
        id:1,
        value:"推荐",
        isActive:false
      }
    ],
        // 自定义自己喜欢的颜色
        colorArr: ["#EE2C2C", "#ff7070", "#EEC900", "#4876FF", "#ff6100",
        "#7DC67D", "#E17572", "#7898AA", "#C35CFF", "#33BCBA", "#C28F5C",
        "#FF8533", "#6E6E6E", "#428BCA", "#5cb85c", "#FF674F", "#E9967A",
        "#66CDAA", "#00CED1", "#9F79EE", "#CD3333", "#FFC125", "#32CD32",
        "#00BFFF", "#68A2D5", "#FF69B4", "#DB7093", "#CD3278", "#607B8B"],
      // 存储随机颜色
    randomColorArr: [],
  },

//获取页面上显示的showList
getShowList1:function(e){
  //没有执行搜索或是没有结果
  if(this.data.searchList.length===0){
    //设置showList
    this.setData({
      showList1:this.data.list1.slice(0,this.pageSize*this.currentPage1)
    })
    this.currentPage1++;
  }else{
    //执行搜索结合的分页
    //设置showList
    this.setData({
      showList1:this.data.searchList.slice(0,this.pageSize*this.currentPage1)
    })
    this.currentPage1++;
  }
  
  wx.stopPullDownRefresh();
  
},

//获取页面上显示的showList
getShowList2:function(e){
  //没有执行搜索或是没有结果
  if(this.data.searchList.length===0){
    //设置showList
    this.setData({
      showList2:this.data.list2.slice(0,this.pageSize*this.currentPage2)
    })
    this.currentPage2++;
  }else{
    //执行搜索结合的分页
    //设置showList
    this.setData({
      showList2:this.data.searchList.slice(0,this.pageSize*this.currentPage2)
    })
    this.currentPage2++;
  }
  
  wx.stopPullDownRefresh();
  
},

getRandomColor:function(){
  let that=this,
    labLen = that.data.list1.length*4,
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
    console.log('onLoad')
    let userInfo=wx.getStorageSync('userInfo');
    let userId=userInfo.userId;
    var that = this
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/hotcardusers',
      success:function(res){
        that.setData({
          list1:res.data
        })
        let list=res.data;
        console.log(list.length);
        for(let i=0;i<list.length;i++){
          let url=list[i].picUrl;
          let vurl=list[i].videoUrl;
          if(url!=null){
            let urls=url.split("@");
            list[i].picUrl=urls;
          }else{
            list[i].picUrl=[];
          }
          if(vurl!=null){
            let vurls=vurl.split("@");
            list[i].videoUrl=vurls;
          }else{
            list[i].videoUrl=[];
          }
          let cardLabel=[];
          if(list[i].selfLable1!=null&&list[i].selfLable1!=""){
            cardLabel.push(list[i].selfLable1)
          }
          if(list[i].selfLable2!=null&&list[i].selfLable2!=""){
            cardLabel.push(list[i].selfLable2)
          }
          if(list[i].selfLable3!=null&&list[i].selfLable3!=""){
            cardLabel.push(list[i].selfLable3)
          }
          list[i]["cardLabel"]=cardLabel;
        }
        that.data.userLike.push(false);
        that.setData({
          list1:list
        })
        console.log(that.data.list1.length);
        that.totalSzie1=that.data.list1.length;
        //计算总页数
        that.totalPage1=Math.ceil(that.totalSzie1/that.pageSize);
        that.getShowList1();
        that.getRandomColor();
        console.log(that.data.showList1);
        
      }
    })
    console.log('onLoad')
    if(wx.getStorageSync('userInfo')){
      wx.request({
        url: 'https://hailicy.xyz/wechatpro/v1/vipcards/'+userId,
        success:function(res){
          console.log(res);
          
          that.setData({
            list2:res.data
          })
          let list=res.data;
          console.log(list.length);
          for(let i=0;i<list.length;i++){
            let url=list[i].picUrl;
            let vurl=list[i].videoUrl;
            if(url!=null){
              let urls=url.split("@");
              list[i].picUrl=urls;
            }else{
              list[i].picUrl=[];
            }
            if(vurl!=null){
              let vurls=vurl.split("@");
              list[i].videoUrl=vurls;
            }else{
              list[i].videoUrl=[];
            }
            let cardLabel=[];
            if(list[i].selfLable1!=null&&list[i].selfLable1!=""){
              cardLabel.push(list[i].selfLable1)
            }
            if(list[i].selfLable2!=null&&list[i].selfLable2!=""){
              cardLabel.push(list[i].selfLable2)
            }
            if(list[i].selfLable3!=null&&list[i].selfLable3!=""){
              cardLabel.push(list[i].selfLable3)
            }
            list[i]["cardLabel"]=cardLabel;
          }
          that.data.userLike.push(false);
          that.setData({
            list2:list
          })
          console.log(that.data.list2.length);
          that.totalSzie2=that.data.list2.length;
          //计算总页数
          that.totalPage2=Math.ceil(that.totalSzie1/that.pageSize);
          that.getShowList2();
          that.getRandomColor();
          console.log(that.data.showList2);
        }
      })
    }
    
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

  onReachBottom: function () {
    if(this.data.tabs[0].isActive){
      //对热门打卡的分页
      if(this.currentPage1>this.totalPage1){
        console.log("没有下页数据了");
      }else{
        this.getShowList1();
      }
    }else{
      //对推荐的分页
      if(this.currentPage2>this.totalPage2){
        console.log("没有下页数据了");
      }else{
        this.getShowList2();
      }
    }
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
    let that=this;
    let userLike=that.data.userLike;
    let userInfo=wx.getStorageSync('userInfo');
    let userId=userInfo["userId"];
    let cardId=e.target.dataset.id;
    console.log(e);
    userLike[e.target.dataset.cardid]=false;
    that.setData({
      userLike:userLike
    })
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/carduserlike/'+cardId+"/"+userId,
      method:'POST',
      success:function(res){
        console.log(res);
      }
    })
  },

  handleTabsItemChange(e){
    const {index}=e.detail;
    let {tabs}=this.data;
    tabs.forEach((v,i)=>i===index?v.isActive=true:v.isActive=false);
    this.setData({
      tabs
    })
  }
})

=======
//index.js
let requestUtil = require('./../../utils/request.js')
//获取应用实例
const app = getApp()
Page({
  data: {
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
          that.setData({
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
  // 跳转到打卡详情
  toCard: function (e) {
    wx.navigateTo({
      url: '../card/card?id=' + e.currentTarget.dataset.item,
    })
  }
})
>>>>>>> ecc746de330539875bff559ed156cec37a9e0955
