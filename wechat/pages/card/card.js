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
    addLabel:[],
    showModalStatus: false,
    list:[],    //总打卡列表
    showList:[],    //显示的打卡列表
    inputValue:"",
    searchList:[],
    // tabs:[
    //   {
    //     id:0,
    //     isActive:true,
    //     value:"普通用户"
    //   },
    //   {
    //     id:1,
    //     isActive:false,
    //     value:"vip用户"
    //   }
    // ],
    cardForm:{
      userId:7,
      resId:1675852983,
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
        // 自定义自己喜欢的颜色
    colorArr: ["#EE2C2C", "#ff7070", "#EEC900", "#4876FF", "#ff6100",
        "#7DC67D", "#E17572", "#7898AA", "#C35CFF", "#33BCBA", "#C28F5C",
        "#FF8533", "#6E6E6E", "#428BCA", "#5cb85c", "#FF674F", "#E9967A",
        "#66CDAA", "#00CED1", "#9F79EE", "#CD3333", "#FFC125", "#32CD32",
        "#00BFFF", "#68A2D5", "#FF69B4", "#DB7093", "#CD3278", "#607B8B"],
      // 存储随机颜色
    randomColorArr: [],
  },

  //表单提交按钮

formSubmit: function (e) {

  console.log('form发生了submit事件，携带数据为：', e.detail.value)
  
  this.setData({
    cardForm:e.detail.value
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
    var that = this;
    var user_id = wx.getStorageSync('userid')
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
    //******************此处需要获取userid******************
    const userInfo=wx.getStorageSync('userInfo');
    let userId = userInfo["userId"];
    let str=this.data.cardForm;
    str["userId"]=userId;
    /////////////////餐厅id如何获取
    str["resId"]="1675852983";
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
      url: 'https://hailicy.xyz/wechatpro/v1/upCard1',
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
        url: 'https://hailicy.xyz/wechatpro/v1/upCard1',
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
    var that = this
    wx.request({
      url: 'https://hailicy.xyz/wechatpro/v1/cardusers',
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

  // onPageScroll: function (e) {//监听页面滚动
  //   this.setData({
  //     scrollTop: e.scrollTop
  //   })
  // },

  //点击添加标签事件
  addLabel(e){
    console.log("添加标签");
    if(this.data.labelIndex<2){
      this.setData({
        addLabel:[...this.data.addLabel,true]
      })
      this.data.labelIndex++;
    }else{
      console.log("标签达到上限");
      
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
  }
})