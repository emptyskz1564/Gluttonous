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

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that=this;
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/restaurants',
      success:function(res){
        console.log(res);
        that.setData({
          list:res.data
        })
        wx.request({
          url: 'https://hailicy.xyz/wechatpro/v1/vreslables',
          success:function(res){
            //console.log(res);
            that.setData({
              labelList:res.data
            })
            let list=that.data.list;
            let labelList=that.data.labelList;
            //console.log(labelList);
            let labels=[];
            for(let i=0;i<list.length;i++){
              for(let j=0;j<labelList.length;j++){
                if(labelList[j].resId===list[i].resId){
                  labels.push(labelList[j].lableContent)
                }
              }
              list[i]["labels"]=labels;
              //console.log(labels);
              labels=[];
            }
            that.setData({
              list:list
            })
            console.log(that.data.list);
          }
        }) 
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