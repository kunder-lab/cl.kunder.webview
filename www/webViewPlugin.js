/*global cordova, module */
'use strict';

module.exports = (function() {
  var _show = function(url, successCallback, errorCallback, loading) {
    if(loading) {
      cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'show', [url, loading]);
    }
    else {
      cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'show', [url]);
    }
  };

  var _load = function(url) {
    cordova.exec(null, null, 'WebViewPlugin', 'load', [url]);
  };

  var _reload = function() {
    cordova.exec(null, null, 'WebViewPlugin', 'reload', []);
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

  var _subscribeDebugCallback = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'subscribeDebugCallback', []);
  };

  var _subscribeResumeCallback = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'subscribeResumeCallback', []);
  };

  var _subscribeUrlCallback = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'subscribeUrlCallback', []);
  };

  var _subscribeExitCallback = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'subscribeExitCallback', []);
  };

  var _exitApp = function() {
    cordova.exec(function(){},function(){}, 'WebViewPlugin', 'exitApp', []);
  };

  var _setWebViewBehavior = function() {
    cordova.exec(function(){},function(){}, 'WebViewPlugin', 'webViewAdjustmenBehavior', []);
  };

  return {
    Show: _show,
    Load: _load,
    Reload: _reload,
    Hide: _hide,
    Close: _hide,
    SubscribeCallback: _subscribeCallback,
    SubscribeDebugCallback: _subscribeDebugCallback,
    SubscribeResumeCallback: _subscribeResumeCallback,
    SubscribeUrlCallback: _subscribeUrlCallback,
    SubscribeExitCallback: _subscribeExitCallback,
    ExitApp: _exitApp,
    HideLoading: _hideLoading,
    SetWebViewBehavior: _setWebViewBehavior
  };
})();
