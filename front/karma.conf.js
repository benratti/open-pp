// karma.conf.js
process.env.CHROME_BIN = require('puppeteer').executablePath() // IMPORTANT!

module.exports = function(config) {
  config.set({
    browsers: ['ChromeHeadless', 'Firefox', 'Chrome'],
    plugins: [
        require('karma-jasmine'),
        require('karma-chrome-launcher'),
        require('karma-firefox-launcher')
    ]
  })
}