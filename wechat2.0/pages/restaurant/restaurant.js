<<<<<<< HEAD
var app = getApp()
var API = require('../../utils/restapi.js')

Page({

  totalPage:0,      //总页数
  currentPage:1,    //当前页数
  pageSize:10,     //页面总条数
  totalSzie:0,    //总条数
  
=======
// pages/restaurant/restaurant.js
//index.js
let requestUtil = require('./../../utils/request.js')
//获取应用实例
const app = getApp()
Page({
>>>>>>> ecc746de330539875bff559ed156cec37a9e0955
  /**
   * 页面的初始数据
   */
  data: {
<<<<<<< HEAD
    picUrls:[],
    list:[],
    labelList:[],
    showList:[],
    inputValue:"",
    searchList:[],
    // 自定义自己喜欢的颜色
    colorArr: ["#EE2C2C", "#ff7070", "#EEC900", "#4876FF", "#ff6100",
      "#7DC67D", "#E17572", "#7898AA", "#C35CFF", "#33BCBA", "#C28F5C",
      "#FF8533", "#6E6E6E", "#428BCA", "#5cb85c", "#FF674F", "#E9967A",
      "#66CDAA", "#00CED1", "#9F79EE", "#CD3333", "#FFC125", "#32CD32",
      "#00BFFF", "#68A2D5", "#FF69B4", "#DB7093", "#CD3278", "#607B8B"],
    // 存储随机颜色
    randomColorArr: [],
  },
  inputBind: function(event) {
    this.setData({
        inputValue: event.detail.value
    })
    console.log(this.data.inputValue)

  },

  query:function(e){
    console.log("调用函数");
    console.log(this.data.inputValue);
    const search=this.data.inputValue.toLowerCase();
    console.log(search);
    if(this.data.inputValue===0){
      console.log("请输入关键字");
    }
    if(search.length>0){
      const searchList=this.data.list.filter(data=>{
        return Object.keys(data).some(key=>{
          return String(data[key]).toLowerCase().indexOf(search)>-1
        })
      })
      this.setData({
        searchList:searchList,
        showList:searchList
      })
      //重新设置页数
      this.totalPage=Math.ceil(this.data.searchList.length/this.pageSize);
      this.currentPage = 1;
    }else{
      console.log("没有相关信息");
    }
  },

  //清除搜索
  clearSearch:function(){
    let that=this;
    console.log("清除搜索");
    //searchList置空,显示搜索按钮
    that.setData({
      searchList:[],
      inputValue:""
    })
    //重新设置页数
    that.totalPage=Math.ceil(that.totalSzie/that.pageSize);
    that.currentPage = 1;
    //console.log(this.data.showList);
    //执行一次getShowList即可
    that.getShowList();
  },

    //获取页面上显示的showList
    getShowList:function(e){
      //没有执行搜索或是没有结果
      if(this.data.searchList.length===0){
        //设置showList
        this.setData({
          showList:this.data.list.slice(0,this.pageSize*this.currentPage)
        })
        this.currentPage++;
      }else{
        //执行搜索结合的分页
        //设置showList
        this.setData({
          showList:this.data.searchList.slice(0,this.pageSize*this.currentPage)
        })
        this.currentPage++;
      }
      
      wx.stopPullDownRefresh();
      
    },

    getRandomColor:function(){
      let that=this,
        labLen = that.data.list.length*4,
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

=======
    restaurants: [],
    resImageUrls:[],
    // 用户位置信息
    location: {
      longitude: app.globalData.location.longitude,
      latitude: app.globalData.location.latitude
    },
  },

  //https://hailicy.xyz/wechatpro/v1/nearrestaurants/location/'+that.data.location.longitude+'/'+that.data.location.latitude
  //获取附近餐厅
  getNearRestaurant: function () {
    let that = this;
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/nearrestaurants/location/115.394633/31.795101',
      success:function(res){
        if(res.statusCode === 200 ){
          that.setData({
            restaurants: res.data,
            resImageUrls: (function () {
              let imageUrls = []
              for (let i = 0; i < res.data.length; i++) {
                imageUrls.push(
                  res.data[i].picUrl === null ? null : res.data[i].resUrl.split('@')[0]
                )
              }
              return imageUrls
            })()
          })
        }
      }
    })
  },

>>>>>>> ecc746de330539875bff559ed156cec37a9e0955

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
<<<<<<< HEAD
    console.log('onLoad')
    let that = this
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/restaurants',
      success:function(res){
        that.setData({
          list:res.data
        })
        wx.request({
          url: 'https://hailicy.xyz/wechatpro/v1/vreslables',
          success:function(res){
            that.setData({
              labelList:res.data
            })
            //console.log(res);
            let list=that.data.list;
            let labelList=that.data.labelList;
            let labels=[];
            for(let i=0;i<list.length;i++){
              for(let j=0;j<labelList.length;j++){
                if(labelList[j].resId===list[i].resId){
                  labels.push(labelList[j].lableContent)
                }
              }
              let url=list[i].resUrl;
              let urls=url.split("@");
              list[i]["resUrl"]=urls;
              list[i]["labels"]=labels;
              labels=[];
            }
            that.setData({
              list:list,
            }),
            console.log(that.data.list);    
            that.totalSzie=that.data.list.length;
            //计算总页数
            that.totalPage=Math.ceil(that.totalSzie/that.pageSize);
            that.getShowList();
            that.getRandomColor();
          }
        }) 
      }
    })

    
=======
    let that = this;
    that.getNearRestaurant();
>>>>>>> ecc746de330539875bff559ed156cec37a9e0955
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
<<<<<<< HEAD
    
=======

>>>>>>> ecc746de330539875bff559ed156cec37a9e0955
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
<<<<<<< HEAD
    
=======

>>>>>>> ecc746de330539875bff559ed156cec37a9e0955
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
<<<<<<< HEAD
    
=======

>>>>>>> ecc746de330539875bff559ed156cec37a9e0955
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
<<<<<<< HEAD
    
=======

>>>>>>> ecc746de330539875bff559ed156cec37a9e0955
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
<<<<<<< HEAD
        //重置当前页面为1
        this.currentPage=1;
        //重新发起请求获取List(这里省去，获取接口后补上)
        //重新发送请求获取showList
        this.getShowList();
  },

  // onPageScroll: function (e) {//监听页面滚动
  //   this.setData({
  //     scrollTop: e.scrollTop
  //   })
  // },
=======

  },
>>>>>>> ecc746de330539875bff559ed156cec37a9e0955

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
<<<<<<< HEAD
    //没有进行搜索的分页
    if(this.data.searchList.length===0){
      if(this.currentPage>this.totalPage){
        console.log("没有下页数据了");
      }else{
        this.getShowList();
      }
    }
    //搜索结果的分页
    else{
      if(this.currentPage>this.totalPage){
        console.log("没有下页数据了");
      }else{
        this.getShowList();
      }
    }
    
=======

>>>>>>> ecc746de330539875bff559ed156cec37a9e0955
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
<<<<<<< HEAD
    
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
=======

  },

  // 跳转到餐厅详情
  toRest: function (e) {
    wx.navigateTo({
      url: '../restDetail/restDetail?id=' + e.currentTarget.dataset.item,
    })
  }

>>>>>>> ecc746de330539875bff559ed156cec37a9e0955
})