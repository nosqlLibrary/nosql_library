//index.js
//获取应用实例


Page({
  data: {
    library:"Nathan的图书馆",
    BookArray:[
      {
        name:'三体安徽嘎嘎哈了刚',
        douban:'9.0',
        image:'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      },
      {
        name: '三体',
        douban: '9.0',
        image: 'http://img1.gtimg.com/cul/pics/hv1/29/47/1919/124794989.jpg'
      }
    ]
  },

 
  bookinfo: function () {
    wx.navigateTo({
      url: '/pages/logs/logs'
    })
  },
 
  switch_library: function () {
    wx.navigateTo({
      url: '/pages/library_search/library_search'
    })
  }

})
