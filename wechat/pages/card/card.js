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
    img_url: [],
    labelIndex:0,
    addLabel:[],
    showModalStatus: false,
    list:[],    //总打卡列表
    showList:[],    //显示的打卡列表
    inputValue:"",
    searchList:[],
    tabs:[
      {
        id:0,
        isActive:true,
        value:"普通用户"
      },
      {
        id:1,
        isActive:false,
        value:"vip用户"
      }
    ],
    cardForm:{
      cardTitle:"",
      cardContent:"",
      bestFood:"",
      cardLabel1:"",
      cardLabel2:"",
      cardLabel3:"",
    }
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

   chooseimage:function(){
    var that = this;
    wx.chooseImage({
      count: 9, // 默认9  
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
      success: function (res) {
        
        if (res.tempFilePaths.length>0){
 
          //图如果满了9张，不显示加图
          if (res.tempFilePaths.length == 9){
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
    wx.showLoading({
      title: '上传中',
    })
    that.img_upload()
  },
  //图片上传
  img_upload: function () {
    //******************此处需要获取userid******************
    let that = this;
    let img_url = that.data.img_url;
    let img_url_ok = [];
    //由于图片只能一张一张地上传，所以用循环
    for (let i = 0; i < img_url.length; i++) {
      wx.uploadFile({
        //路径填你上传图片方法的地址
        url: 'http://wechat.homedoctor.com/Moments/upload_do',
        filePath: img_url[i],
        name: 'file',
        formData: {
          'user': 'test'
        },
        success: function (res) {
          console.log('上传成功');
          //把上传成功的图片的地址放入数组中
          img_url_ok.push(res.data)
          //如果全部传完，则可以将图片路径保存到数据库
          if (img_url_ok.length == img_url.length) {
            var userid = wx.getStorageSync('userid');
            var content = that.data.content;
            wx.request({
              url: 'http://wechat.homedoctor.com/Moments/adds',
              data: {
                user_id: userid,
                images: img_url_ok,
                content: content,
              },
              success: function (res) {
                if (res.data.status == 1) {
                  wx.hideLoading()
                  wx.showModal({
                    title: '提交成功',
                    showCancel: false,
                    success: function (res) {
                      if (res.confirm) {
                        wx.navigateTo({
                          url: '/pages/my_moments/my_moments',
                        })
                      }
                    }
                  })
                }
              }
            })
          }
        },
        fail: function (res) {
          console.log('上传失败')
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

  //从子组件传递过来的标题点击事件
  handleTabsItemChange:function(e){
    //获取被点击的标题索引
    const {index}=e.detail;
    //修改源数组
    let {tabs}=this.data;
    tabs.forEach((v,i)=>i===index?v.isActive=true:v.isActive=false);
    //赋值到data中
    this.setData({
      tabs:tabs
    })
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
        console.log("res");
        
        console.log(res)
        that.setData({
            list:res.data,
        })
    });
    console.log(that.data.list);
    that.totalSzie=that.data.list.length;
    //计算总页数
    that.totalPage=Math.ceil(that.totalSzie/that.pageSize);
    that.getShowList();
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
    const urls=this.data.showList.img;    //以后接口直接得到打卡图片数组
    const current=e.currentTarget.dataset.url;
    console.log("预览图片");
    wx.previewImage({
      current:current,
      urls: urls
    })
  },

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
  }

})