/*global cordova, module */
'use strict';
module.exports = (function() {


  var _show = function(url, successCallback, errorCallback, loading) {
    if(loading){
      cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'show', [url, loading]);
    }
    else{
      cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'show', [url]);
    }
  };

  var _hide = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'hide', []);
  };

  var _hideLoading = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'hideLoading', []);
  };

  var _subscribeCallback = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'subscribeCallback', []);
  };

  var _subscribeExitCallback = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'subscribeExitCallback', []);
  };

  var _exitApp = function() {
    cordova.exec(function(){},function(){}, 'WebViewPlugin', 'exitApp', []);
  };


  return {
    Show: _show,
    Hide: _hide,
    Close: _hide,
    SubscribeCallback: _subscribeCallback,
    SubscribeExitCallback: _subscribeExitCallback,
    ExitApp: _exitApp,
    HideLoading: _hideLoading
  };

})();
