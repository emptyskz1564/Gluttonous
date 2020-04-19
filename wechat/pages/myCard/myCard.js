// pages/card/card.js
var app = getApp()
var API = require('../../utils/api.js')
Page({

  totalPage:0,      //总页数
  currentPage:1,    //当前页数
  pageSize:10,     //页面总条数
  totalSzie:0,    //总条数

  /**
   * 页面的初始数据
   */
  data: {
    userLike:[],
    img_url: [],
    labelIndex:0,
    list:[],    //总打卡列表
    showList:[],    //显示的打卡列表
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
      console.log("请输入信息");
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


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('onLoad')
    let that = this
    let userInfo=wx.getStorageSync('userInfo');
    let userId=userInfo.userId;
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/cardusers/'+userId,
      success:function(res){
        console.log(res);
        that.setData({
          list:res.data
        })
        let list=res.data;
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
        for(let i=0;i<list.length;i++){
          list[i].userName=userInfo.nickName
        }
        that.setData({
          list:list
        })
        console.log(that.data.list);
        that.totalSzie=that.data.list.length;
        //计算总页数
        that.totalPage=Math.ceil(that.totalSzie/that.pageSize);
        that.getShowList();
        that.getRandomColor();
      }
    })
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
    //重置当前页面为1
    this.currentPage=1;
    //重新发起请求获取List(这里省去，获取接口后补上)
    //重新发送请求获取showList
    this.getShowList();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
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
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  // onPageScroll: function (e) {//监听页面滚动
  //   this.setData({
  //     scrollTop: e.scrollTop
  //   })
  // },
  
  //点击放大图片事件
  handlePreviewImg(e){
    //接受传递过来的图片url  
    console.log(e);
    const urls=e.target.dataset.url;
    console.log(urls);
    
    console.log("图片放大");
    wx.previewImage({
      current:urls[0],
      urls:urls
    })
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
  }


})