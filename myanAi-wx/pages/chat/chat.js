// 获取应用实例
const app = getApp()
const { api } = require('../../utils/request.js')

Page({
  data: {
    messages: [],
    inputValue: '',
    isLoading: false,
    scrollToMessage: '',
    messageId: 0,
    statusBarHeight: 0,
    navBarHeight: 0
  },

  onLoad: function() {
    const systemInfo = wx.getSystemInfoSync()
    this.setData({
      statusBarHeight: systemInfo.statusBarHeight,
      navBarHeight: 44
    })
  },

  goBack: function() {
    wx.navigateBack({
      delta: 1
    })
  },

  onInput: function(e) {
    this.setData({
      inputValue: e.detail.value
    })
  },

  onScrollToUpper: function() {
    console.log('已滚动到顶部')
    // 这里可以添加加载更多历史消息的逻辑
  },

  scrollToBottom: function() {
    if (this.data.messages.length > 0) {
      this.setData({
        scrollToMessage: 'scroll-bottom'
      })
    }
  },

  sendMessage: function() {
    if (!this.data.inputValue.trim() || this.data.isLoading) {
      return
    }

    const userMessage = {
      id: this.data.messageId++,
      type: 'user',
      content: this.data.inputValue
    }

    const aiMessage = {
      id: this.data.messageId++,
      type: 'ai',
      content: '',
      loading: true
    }

    this.setData({
      messages: [...this.data.messages, userMessage, aiMessage],
      inputValue: '',
      isLoading: true
    }, () => {
      this.scrollToBottom()
    })

    // 发送API请求
    wx.request({
      url: 'http://localhost:8080/ds/simple-questions',
      method: 'GET',
      data: {
        content: userMessage.content
      },
      success: (res) => {
        if (res.statusCode === 200 && res.data.code === 200) {
          const aiReply = res.data.data.choices[0].message.content
          
          // 更新消息列表中的AI回复
          const updatedMessages = this.data.messages.map(msg => {
            if (msg.id === aiMessage.id) {
              return {
                ...msg,
                content: aiReply,
                loading: false
              }
            }
            return msg
          })

          this.setData({
            messages: updatedMessages,
            isLoading: false
          }, () => {
            this.scrollToBottom()
          })
        } else {
          // 处理服务器返回的错误
          this.handleRequestError(aiMessage.id)
        }
      },
      fail: (error) => {
        // 处理网络错误
        this.handleRequestError(aiMessage.id)
      }
    })
  },

  // 处理请求错误的统一方法
  handleRequestError: function(messageId) {
    // 更新消息列表中的错误提示
    const updatedMessages = this.data.messages.map(msg => {
      if (msg.id === messageId) {
        return {
          ...msg,
          content: '网络错误，请稍后再试',
          loading: false,
          isError: true
        }
      }
      return msg
    })

    this.setData({
      messages: updatedMessages,
      isLoading: false
    }, () => {
      this.scrollToBottom()
    })

    // 显示错误提示
    // wx.showToast({
    //   title: '网络错误，请稍后再试',
    //   icon: 'none',
    //   duration: 2000
    // })
  },

  simulateTypingEffect: function(messageId, fullText) {
    // 找到对应的消息
    const messageIndex = this.data.messages.findIndex(msg => msg.id === messageId)
    if (messageIndex === -1) return

    let currentText = ''
    const typingSpeed = 50 // 每个字符的延迟时间（毫秒）
    
    // 逐字显示文本
    const typeNextChar = () => {
      if (currentText.length < fullText.length) {
        currentText += fullText[currentText.length]
        
        // 更新消息内容
        const updatedMessages = [...this.data.messages]
        updatedMessages[messageIndex] = {
          ...updatedMessages[messageIndex],
          content: currentText
        }
        
        this.setData({
          messages: updatedMessages
        }, () => {
          this.scrollToBottom()
        })
        
        // 继续打字效果
        setTimeout(typeNextChar, typingSpeed)
      }
    }
    
    // 开始打字效果
    typeNextChar()
  }
}) 