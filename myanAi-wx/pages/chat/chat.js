// 获取应用实例
const app = getApp()

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

  onInputChange: function(e) {
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

    // 模拟API请求
    this.simulateApiRequest(userMessage.content, aiMessage.id)
  },

  simulateApiRequest: function(userInput, messageId) {
    // 这里应该是实际的API请求
    // 为了演示，我们使用setTimeout模拟网络请求
    setTimeout(() => {
      // 模拟API响应
      const response = `这是对"${userInput}"的回复。在实际应用中，这里应该是从后端API获取的响应。`
      
      // 更新消息列表，将loading状态的消息替换为实际内容
      const updatedMessages = this.data.messages.map(msg => {
        if (msg.id === messageId) {
          return {
            ...msg,
            content: response,
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

      // 模拟打字效果
      this.simulateTypingEffect(messageId, response)
    }, 1500) // 模拟1.5秒的网络延迟
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