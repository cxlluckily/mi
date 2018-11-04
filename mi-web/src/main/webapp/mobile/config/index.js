'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')
// 修改开始
var glob = require('glob');
var pages = getEntry('*.html');
var str = '';

var conf = {};
for (var pathname in pages) {
  // 配置生成的html文件，定义路径等
  var name_html = pathname.substr(0, pathname.indexOf('.'));

  conf[name_html] = path.resolve(__dirname, '../dist/' + pathname)
}

// 修改结束

module.exports = {
  dev: {
    // Paths
    assetsSubDirectory: 'static',
    assetsPublicPath: '/',
    proxyTable: {
      '/api': {
        // target: 'mitest.shankephone.com', // 测试服务接口
        // target: 'http://localhost:8999/mi', // 赵亮服务接口
        // target: 'http://192.168.6.11:8080/mi', // 司徒彬本地服务接口
        // target: 'http://192.168.6.200:8080/mi', // 冯 本地服务接口
        target: 'http://192.168.1.109:8082/mi', // 李砚龙本地服务接口
        // target: 'http://192.168.1.138:8082/mi', // 郝伟洲本地服务接口
        changeOrigin: true, // 是否解决跨域
        secure: false, //https://github.com/vuejs-templates/webpack/issues/244
        pathRewrite: {
          '^/api': '/'
        }
      }
    },
    // Various Dev Server settings
    host: 'localhost', // can be overwritten by process.env.HOST
    port: 8080, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
    autoOpenBrowser: false,
    errorOverlay: true,
    notifyOnErrors: true,
    poll: false, // https://webpack.js.org/configuration/dev-server/#devserver-watchoptions-


    /**
     * Source Maps
     */

    // https://webpack.js.org/configuration/devtool/#development
    devtool: 'cheap-module-eval-source-map',

    // If you have problems debugging vue-files in devtools,
    // set this to false - it *may* help
    // https://vue-loader.vuejs.org/en/options.html#cachebusting
    cacheBusting: true,

    cssSourceMap: true
  },

  // build: {
  //   // Template for index.html
  //   index: path.resolve(__dirname, '../dist/index.html'),
  //   pc: path.resolve(__dirname, '../dist/pc.html'),
  //   // Paths
  //   assetsRoot: path.resolve(__dirname, '../dist'),
  //   assetsSubDirectory: 'static',
  //   assetsPublicPath: '/',
  //   /**
  //    * Source Maps
  //    */

  //   productionSourceMap: true,
  //   // https://webpack.js.org/configuration/devtool/#production
  //   devtool: '#source-map',

  //   // Gzip off by default as many popular static hosts such as
  //   // Surge or Netlify already gzip all static assets for you.
  //   // Before setting to `true`, make sure to:
  //   // npm install --save-dev compression-webpack-plugin
  //   productionGzip: false,
  //   productionGzipExtensions: ['js', 'css'],

  //   // Run the build command with an extra argument to
  //   // View the bundle analyzer report after build finishes:
  //   // `npm run build --report`
  //   // Set to `true` or `false` to always turn it on or off
  //   bundleAnalyzerReport: process.env.npm_config_report
  // }

  build: Object.assign({}, conf, {
    // Template for index.html
    // index: path.resolve(__dirname, '../dist/index.html'),
    // pc: path.resolve(__dirname, '../dist/pc.html'),
    // Paths
    assetsRoot: path.resolve(__dirname, '../dist'),
    assetsSubDirectory: 'static',
    assetsPublicPath: './',
    /**
     * Source Maps
     */

    // productionSourceMap: true, // 打包后可调式
    productionSourceMap: false, // 打包后不可调式
    // https://webpack.js.org/configuration/devtool/#production
    devtool: '#source-map',

    // Gzip off by default as many popular static hosts such as
    // Surge or Netlify already gzip all static assets for you.
    // Before setting to `true`, make sure to:
    // npm install --save-dev compression-webpack-plugin
    productionGzip: false,
    productionGzipExtensions: ['js', 'css'],

    // Run the build command with an extra argument to
    // View the bundle analyzer report after build finishes:
    // `npm run build --report`
    // Set to `true` or `false` to always turn it on or off
    bundleAnalyzerReport: process.env.npm_config_report
  })
}


// 修改开始
function getEntry(globPath) {
  var entries = {},
    basename, tmp, pathname;

  glob.sync(globPath).forEach(function (entry) {
    basename = path.basename(entry, path.extname(entry));
    // tmp = entry.split('/').splice(-3);
    tmp = entry.split('/').splice(-2);
    // pathname = tmp.splice(0, 1) + '/' + basename; // 正确输出js和html的路径
    pathname = tmp[0];
    entries[pathname] = entry;
  });

  return entries;
}
// 修改结束
