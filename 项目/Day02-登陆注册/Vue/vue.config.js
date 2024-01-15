const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: '0.0.0.0',
    port: 40114,
    client: {
      webSocketURL: 'ws://0.0.0.0:40114/ws'
    },
    headers: {
      'Access-Control-Allow-Origin': '*'
    },
    historyApiFallback: true,
    allowedHosts: "all"
  }
})
