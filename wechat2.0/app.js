//app.js
App({
  onLaunch: function () {

  },
  globalData: {
    userInfo: null,
    user: {
      userId: '',
      userName: '',
      wxId: '',
      vip: 0,
      userUrl: '',
      userOpenId: ''
    },
    location: {
      longitude: 115.394633,
      latitude: 31.795101
    },
    themes: [
      // pink
      ['#ddd', '#fab7b7', '#f5a8a8', '#e19999'],
      // light-cold & deep-warm
      ['#f8f3eb', '#c3edea', '#fc7e2f', '#f40552'],
      // light
      ['#63b7af', '#abf0e9', '#d4f3ef', '#ee8572'],
      // dark
      ['#ffd8a6', '#fc8210', '#ff427f', '#007892']
    ]
  }
})