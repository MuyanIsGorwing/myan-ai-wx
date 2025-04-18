// index.js
Page({
    data: {
        message: 'hello'
    },
    navigateToChat: function() {
        wx.navigateTo({
            url: '/pages/chat/chat'
        })
    }
})
