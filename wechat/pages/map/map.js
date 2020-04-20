Page({
  data: {
    Height: 0,
    scale: 15,
    latitude: "",
    longitude: "",
    markers: [],
    circles: []

  },

  onLoad: function () {
    var _this = this;

    wx.getSystemInfo({
      success: function (res) {
        //设置map高度，根据当前设备宽高满屏显示
        _this.setData({
          view: {
            Height: res.windowHeight
          }
        })
      }
    })

    wx.getLocation({
      type: 'wgs84', // 默认为 wgs84 返回 gps 坐标，gcj02 返回可用于 wx.openLocation 的坐标
      success: function (res) {

        _this.setData({
          latitude: res.latitude,
          longitude: res.longitude,
          markers: [{
            id: "1",
            latitude: res.latitude,
            longitude: res.longitude,
            width: 50,
            height: 50,
            iconPath: "/pages/icons/marker_red.png",
            title: "你在这里"

          }],
          circles: [{
            latitude: res.latitude,
            longitude: res.longitude,
            color: '#FF0000DD',
            fillColor: '#7cb5ec88',
            radius: 3000,
            strokeWidth: 1
          }]

        })
      }

    })

  },

  //滑动地图事件
  regionchange(e) {
    console.log("regiοnchange===" + e.type)
  },

  //点击merkers
  markertap(e) {
    console.log(e.markerId)

    wx.showActionSheet({
      itemList: ["A"],
      success: function (res) {
        console.log(res)
      },
      fail: function (res) {
        console.log(res.errMsg)
      }
    })
  },

  //点击缩放按钮动态请求数据
  controltap(e) {
    console.log(e);
    
    var that = this;
    console.log("scale===" + this.data.scale)
    if (e.target.dataset.controlid === "1") {
      that.setData({
        scale: --this.data.scale
      })
    } 
    if (e.target.dataset.controlid === "2") {
      that.setData({
        scale: ++this.data.scale
      })
    }


  },
  /**
    * 移动到自己位置
    */
    moveToLocation: function() {
      let mpCtx = wx.createMapContext("map");
       mpCtx.moveToLocation();
    },


})