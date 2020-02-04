// pages/mineindex/mineindex.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    list_items: [{
      id: 1,
      unique: "已借的书"
     }, {
      id: 2,
      unique: "修改信息"
     }, {
      id: 3,
      unique: "修改密码"
     }
    ]
  },
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },

  1: function () {
    wx.navigateTo({
      url: '/pages/borrowed/borrowed',
    })
  },
  2: function () {
    wx.navigateTo({
      url: '/pages/change_info/change_info',
    })
  },
  3: function () {
    wx.navigateTo({
      url: '/pages/change_password/change_password',
    })
  },
  4: function () {
    wx.navigateTo({
      url: '/pages/logs/logs',
    })
  },
  5: function () {
    wx.navigateTo({
      url: '../logs/logs',
    })
  },

  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  }

  // /**
  //  * 生命周期函数--监听页面加载
  //  */
  // onLoad: function () {
  //   console.log('onLoad')
  //   var that = this
  //   //调用应用实例的方法获取全局数据
  //   app.getUserInfo(function (userInfo) {
  //     //更新数据
  //     that.setData({
  //       userInfo: userInfo
  //     })
  //   })

  // }

})