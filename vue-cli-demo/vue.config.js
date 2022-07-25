module.exports = {
    devServer: {
      proxy: {
        "/api": { // 1
          target: 'http://127.0.0.1:8080',   // 2
          changeOrigin: true, // 3
          pathRewrite: {
            '^/api': '/' // 4
          }
        }
      }
    }
}