Page({

  /**
   * 页面的初始数据
   */
  data: {
    labelList:[],
    list:[],
    userId:"",
    str:{userId:2,resId:1675852983,cardContent:"123",cardTitle:"123",bestFood:"123",selfLable1:"123",selfLable2:"yahu",selfLable3:"aha",lableId1:1,lableId2:-1,lableId3:-1}
  },
//https://hailicy.xyz/wechatpro/v1/restaurants
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that=this;
    let userInfo=wx.getStorageSync('userInfo');
    that.setData({
      userId:userInfo.userId
    })
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/vuserrestaurant/'+that.data.userId,
      success:function(res){
        console.log(res);
        wx.request({
          url: 'https://hailicy.xyz/wechatpro/v1/vuserlable/'+that.data.userId,
          success:function(res){}
        })
      }
    })
    // wx.request({
    //   url: 'https://hailicy.xyz/wechatpro/v1//discusses/asc/1',
    //   success:function(res){
    //     console.log(res);
    //   }
    // })
    // wx.request({
    //   url: 'https://hailicy.xyz/wechatpro/v1//cards',
    //   success:function(res){
    //     console.log(res);
    //     that.setData({
    //       list:res.data
    //     })
    //     let list=res.data;
    //     for(let i=0;i<list.length;i++){
    //       let url=list[i].picUrl;
    //       let vurl=list[i].videoUrl;
    //       if(url!=null){
    //         let urls=url.split("@");
    //         list[i].picUrl=urls;
    //       }else{
    //         list[i].picUrl=[];
    //       }
    //       if(vurl!=null){
    //         let vurls=vurl.split("@");
    //         list[i].videoUrl=vurls;
    //       }else{
    //         list[i].videoUrl=[];
    //       }
    //     }
    //     that.setData({
    //       list:list
    //     })
    //     console.log(that.data.list);
        
    //   }
    // })
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
    
  }
})