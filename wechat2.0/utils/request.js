let apiUrl = 'https://hailicy.xyz/wechatpro/v1'

let requestExceptionHandler = function (code) {
  if (code === 404) {
    return {
      isError: true,
      errorMessage: '内容被饕餮吃掉了！',
      errorDetail: '请确保网络通畅后重试'
    }
  }
}
module.exports = {
  apiUrl: apiUrl,
  requestExceptionHandler: requestExceptionHandler
}