import axios from 'axios'
import { Message } from 'iview';

const instance = axios.create({
  // baseURL: '/api',
  baseURL: 'http://localhost:8062/gateway',
  timeout: 1000000
})

instance.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'

// 添加响应拦截器
instance.interceptors.response.use((res) => {
  return res
}, (error) => {
  if (error.response) {
    switch (error.response.status) {
      case 400:
        Message.error(error.response.data.msg || '请求参数异常')
        break
      case 401:
        // window.location.href = '/401';
        Message.error(error.response.data.msg || '密码错误或账号不存在！')
        break
      case 403:
        Message.error(error.response.data.msg || '无访问权限，请联系企业管理员')
        break
      default:
        Message.error(error.response.data.msg || '服务端异常，请联系技术支持')
    }
  }
  return Promise.reject(error)
})

export default instance
