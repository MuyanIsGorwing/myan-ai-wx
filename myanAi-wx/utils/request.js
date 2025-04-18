// API 基础地址
const BASE_URL = 'http://localhost:8080/' // 请替换为您的实际API地址

// 封装请求方法
const request = (url, options = {}) => {
  return new Promise((resolve, reject) => {
    wx.request({
      url: `${BASE_URL}${url}`,
      method: options.method || 'GET',
      data: options.data,
      header: {
        'Content-Type': 'application/json',
        ...options.header
      },
      success: (res) => {
        if (res.statusCode >= 200 && res.statusCode < 300) {
          resolve(res.data)
        } else {
          reject(new Error(res.data.message || '请求失败'))
        }
      },
      fail: (err) => {
        reject(new Error(err.errMsg || '网络错误'))
      }
    })
  })
}

// API 方法
const api = {
  // 发送聊天消息
  sendMessage: (content) => {
    return request('/ds/simple-questions', {
      method: 'GET',
      data: {
        content
      }
    })
  }
}

module.exports = {
  request,
  api
} 