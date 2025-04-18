// app.js
App({
  onLaunch() {
    // 初始化
  },
  onShow() {
    // 小程序显示
  },
  onHide() {
    // 小程序隐藏
  },
  onError(error) {
    // 错误监控
    console.error('App Error:', error)
  },
  onPageNotFound(res) {
    // 页面不存在监控
    console.error('Page Not Found:', res)
    wx.redirectTo({
      url: 'pages/index/index'
    })
  },
  globalData: {
    userInfo: null
  }
})
