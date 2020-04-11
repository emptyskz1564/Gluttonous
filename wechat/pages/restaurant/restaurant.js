var app = getApp()
var API = require('../../utils/restapi.js')

Page({

  totalPage:0,      //总页数
  currentPage:1,    //当前页数
  pageSize:10,     //页面总条数
  totalSzie:0,    //总条数
  
  /**
   * 页面的初始数据
   */
  data: {
    list:[],
    showList:[],
    inputValue:"",
    searchList:[],
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


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log('onLoad')
    var that = this
    // 使用 Mock
    API.ajax('', function (res) {
        //这里既可以获取模拟的res
        console.log(res)
        that.setData({
            list:res.data
        })
    });
    console.log(that.data.list);
    that.totalSzie=that.data.list.length;
    //计算总页数
    that.totalPage=Math.ceil(that.totalSzie/that.pageSize);
    that.getShowList();
    console.log(this.data.list)
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

  //点击餐厅图片放大预览
  handlePreviewImg(e){
    //接受传递过来的图片url
    const url=e.currentTarget.dataset.url;
    console.log("图片放大");
    wx.previewImage({
      urls:url
    })
  }
})