/*global cordova, module */
'use strict';
module.exports = (function() {


  var _show = function(url, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'show', [url]);
  };

  var _hide = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'hide', []);
  };

  var _subscribeCallback = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'subscribeCallback', []);
  };


  return {
    Show: _show,
    Hide: _hide,
    Close: _hide,
    SubscribeCallback: _subscribeCallback
  };

})();
