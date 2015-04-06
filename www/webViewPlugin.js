/*global elu, module */
module.exports = (function() {
  'use strict';


  var _show = function(url, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'show', [url]);
  };

  var _hide = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, 'WebViewPlugin', 'hide', []);
  };


  return {
    Show: _show,
    Hide: _hide,
    Close: _hide
  };

})();
