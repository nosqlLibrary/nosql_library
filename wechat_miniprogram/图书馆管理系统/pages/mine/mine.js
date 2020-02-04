// pages/mine/mine.js
Page({


  /**
   * 页面的初始数据
   */
  data: {
    login_accountInput:'',
    login_passwordInput:'',
    user_or_admin:'user',
   items: 
         [
           { name: 'user', value: '用户', checked: 'true' },
           { name: 'admin', value: '管理员' },
         ]

  },
  login_accountInput: function (e) {
    this.setData({
      login_accountInput: e.detail.value
    })
    console.log('账号输入完成，账号为：', e.detail.value)
  },

  radioChange: function (e) {
    this.setData({
      user_or_admin: e.detail.value
    })
    console.log('radio发生change事件，携带value值为：', e.detail.value)
    console.log('js中，data的user_or_admin的值为：',this.data.user_or_admin)
  },


  loging: function () {

    if(this.data.user_or_admin=='user'){
     wx.navigateTo({
       url: '/pages/mineindex/mineindex',
     })
   }
   else{
     wx.navigateTo({
       url: '/pages/adminindex/adminindex',
      })
    }
  },


  sign_in: function () {
    wx.navigateTo({
      url: '/pages/change_info/change_info'
    })
  },




  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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