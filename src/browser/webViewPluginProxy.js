/* global module, require */
'use strict';
module.exports = {
  show: function(success, error, opts) {
    if(!opts.length){
      error('url cant be empty');
      return;
    }
    localStorage.webViewUrl = window.location.pathname;
    window.location.assign(opts[0]);
    success();
  },
  hide: function(success, error) {
    var url = localStorage.webViewUrl;
    if(!url){
      error('no url set to come back');
      return;
    }
    delete localStorage.webViewUrl;
    window.location.assign(url);
    success();
  },
  subscribeCallback: function(){}
};

require("cordova/exec/proxy").add("WebViewPlugin",module.exports);