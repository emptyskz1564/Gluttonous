// pages/cardDetail/cardDetail.js
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
    userLike:[],
    resList:{},
    list:[],
    disList:[],
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

  getRandomColor:function(){
    let that=this,
      labLen = 3,
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
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/vcarduserdiscuss/card/'+options.cardId,
      success:function(res){
        that.setData({
          list:res.data
        })
        let list=res.data;
        
          let url=list[0].picUrl;
          let vurl=list[0].videoUrl;
          if(url!=null){
            let urls=url.split("@");
            list[0].picUrl=urls;
          }else{
            list[0].picUrl=[];
          }
          if(vurl!=null){
            let vurls=vurl.split("@");
            list[0].videoUrl=vurls;
          }else{
            list[0].videoUrl=[];
          }
          let cardLabel=[];
          if(list[0].selfLable1!=null&&list[0].selfLable1!=""){
            cardLabel.push(list[0].selfLable1)
          }
          if(list[0].selfLable2!=null&&list[0].selfLable2!=""){
            cardLabel.push(list[0].selfLable2)
          }
          if(list[0].selfLable3!=null&&list[0].selfLable3!=""){
            cardLabel.push(list[0].selfLable3)
          }
          list[0]["cardLabel"]=cardLabel;
        that.setData({
          list:list,
          resList:list[0]
        })
        console.log(that.data.resList);
        wx.request({
          url: 'https://hailicy.xyz/wechatpro/v1//discusses/asc/'+list[0].cardId,
          success:function(res){
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
            that.getRandomColor();
            console.log(res);
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
    let userId=userInfo["userId"];
    let disId=e.target.dataset.disid;
    let that=this;
    let card=that.data.disList
    console.log(card);
    let userLike=that.data.userLike;
    console.log(e);
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

  handlePreviewImg(e){
    //接受传递过来的图片url  
    console.log(e);
    const urls=e.target.dataset.url;
    const index=e.target.dataset.index;
    console.log(urls);

    console.log("图片放大");
    wx.previewImage({
      current:urls[index],
      urls:urls
    })
  },

  setInput(e){
    console.log(e);
    
    this.setData({
      parentId:e.target.dataset.parentid,
      inputShowed:true,
      parentThread:e.target.dataset.parentthread
    })
  },

  huifu(e){
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
      for(let i=0;i<that.data.disList.length;i++){
        if(re.test(that.data.disList[i].disThread)){
          count++;
        }
      }
      parentId=head[0];
      disThread=head[0]+"."+count+"/";
    }

    let userInfo=wx.getStorageSync('userInfo');
    let userId=userInfo.userId;
    
    let str={
          cardId:that.data.disList[0].cardId,
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
        
      }
    })
  }
})