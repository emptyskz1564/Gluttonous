// pages/user2/user.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    vipLevel:0,
    selfLabels:null,
    userInfo:null,
    // 自定义自己喜欢的颜色
    colorArr: ["#EE2C2C", "#ff7070", "#EEC900", "#4876FF", "#ff6100",
      "#7DC67D", "#E17572", "#7898AA", "#C35CFF", "#33BCBA", "#C28F5C",
      "#FF8533", "#6E6E6E", "#428BCA", "#5cb85c", "#FF674F", "#E9967A",
      "#66CDAA", "#00CED1", "#9F79EE", "#CD3333", "#FFC125", "#32CD32",
      "#00BFFF", "#68A2D5", "#FF69B4", "#DB7093", "#CD3278", "#607B8B"],
    // 存储随机颜色
    randomColorArr: [],
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
    const userInfo = wx.getStorageSync('userInfo');
    if(wx.getStorageSync('userInfo')){
      that.setData({userInfo})
      let userId=userInfo.userId;
      wx.request({
        url: 'https://hailicy.xyz/wechatpro/v1/users/'+userId,
        success:function(res){
          if(res.data.vip===null){
            
          }else{
            that.setData({
              vipLevel:res.data.vip
              })
          }
          
          wx.request({
            url: 'https://hailicy.xyz/wechatpro/v1/vuserlable/'+userId,
            success:function(res){
              console.log(res);
              
              let selfLabels=[];
              for(let i=0;i<res.data.length;i++){
                selfLabels.push(res.data[i].lableContent);
              }
              that.setData({
                selfLabels:selfLabels
              })
              that.getRandomColor();
            }
          })
        }
      })
    }else{
      console.log("请先登录");
      that.getRandomColor();
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
  onShareAppMessage: function (res) {
    console.log(res);
    return {
      title: '微信小程序',
      //path: '/pages/share/share?id=123',
      //imageUrl: "/images/1.jpg",
      success: (res) => {
        console.log("转发成功", res);
      },
      fail: (res) => {
        console.log("转发失败", res);
      }
    }
  }
})