let proxyObj = {}

proxyObj['/api'] = {
//websocket
  ws: false,
//目标地址
  target: 'http://127.0.0.1:8888',
//发送请求头中host会设置成target
  changeOrigin: true,
//不重写请求地址
  pathRewrite: {
    '^/api': '/'
  }
}
module.exports = {
  devServer: {
    host: '127.0.0.1',
    port: 80,
    proxy: proxyObj
  }
}
