package cl.kunder.webview;


import android.content.Intent;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;

import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.LOG;



public class WebViewPlugin extends CordovaPlugin {

  private static final String LOG_TAG = "WebViewPlugin";
  private static CallbackContext subscribeCallbackContext = null;
  private static CallbackContext subscribeExitCallbackContext = null;
  private static CallbackContext subscribeDebugCallbackContext = null;
  private static CallbackContext subscribeResumeCallbackContext = null;
  public static WebViewPlugin webViewPlugin = null;
  public static WebViewActivity webViewActivity = null;

  public WebViewPlugin() {

  }

  /**
  * Sets the context of the Command. This can then be used to do things like
  * get file paths associated with the Activity.
  *
  * @param cordova
  *            The context of the main Activity.
  * @param webView
  *            The CordovaWebView Cordova is running in.
  */
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    webViewPlugin = this;
  }

  /**
  * Executes the request and returns PluginResult.
  *
  * @param action
  *            The action to execute.
  * @param args
  *            JSONArry of arguments for the plugin.
  * @param callbackContext
  *            The callback id used when calling back into JavaScript.
  * @return True if the action was valid, false if not.
  */
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("show") && args.length() > 0) {
      LOG.d(LOG_TAG, "Show Web View");
      final String url = args.getString(0);
      Boolean shouldShowLoading = false;
      try{
        shouldShowLoading = args.getBoolean(1);
      }
      catch(Exception e){

      }
      if(!"".equals(url)) {
        showWebView(url, shouldShowLoading);
        JSONObject r = new JSONObject();
        r.put("responseCode", "ok");
        callbackContext.success(r);
      }
      else {
        callbackContext.error("Empty Parameter url");
      }

    }
    else if(action.equals("hide")) {
      LOG.d(LOG_TAG, "Hide Web View");
      hideWebView();
      JSONObject r = new JSONObject();
      r.put("responseCode", "ok");
      callbackContext.success(r);
    }
    else if(action.equals("load")) {
      LOG.d(LOG_TAG, "Web View Load Url");
      if (webViewActivity == null) {
          execute("show", args, callbackContext);
      } else {
          final String url = args.getString(0);
          webViewActivity.loadUrl(url);
      }
    }
    else if(action.equals("reload")) {
        LOG.d(LOG_TAG, "Web View Reload");
        if (webViewActivity == null) {
          LOG.d(LOG_TAG, "Web View is not initialized.");
        } else {
          webViewActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
              final String url = webViewActivity.getUrl();
              webViewActivity.loadUrl(url);
            }
          });
        }
    }

    else if(action.equals("hideLoading")) {
      LOG.d(LOG_TAG, "Hide Web View Loading");
      try{
        WebViewActivity.hideLoading();
      }
      catch(Exception e){
        LOG.e(LOG_TAG, "Error in hideLoading");
        LOG.e(LOG_TAG, e.toString());
      }
      JSONObject r = new JSONObject();
      r.put("responseCode", "ok");
      callbackContext.success(r);
    }

    else if(action.equals("subscribeCallback")){
      LOG.d(LOG_TAG, "Subscribing Cordova CallbackContext");
      subscribeCallbackContext = callbackContext;
    }

    else if(action.equals("subscribeDebugCallback")){
        LOG.d(LOG_TAG, "Subscribing Cordova CallbackContext");
        subscribeDebugCallbackContext = callbackContext;
    }

    else if(action.equals("subscribeResumeCallback")){
        LOG.d(LOG_TAG, "Subscribing Cordova CallbackContext");
        subscribeResumeCallbackContext = callbackContext;
    }

    else if(action.equals("subscribeExitCallback")){
      LOG.d(LOG_TAG, "Subscribing Cordova ExitCallbackContext");
      subscribeExitCallbackContext = callbackContext;
    }

    else if(action.equals("exitApp")){
      LOG.d(LOG_TAG, "Exiting app?");
      if(subscribeExitCallbackContext != null){
        subscribeExitCallbackContext.success();
        subscribeExitCallbackContext = null;
      }
      this.cordova.getActivity().finish();

    }

    else {
      return false;
    }

    return true;
  }

  private void showWebView(final String url, Boolean shouldShowLoading) {
    LOG.d(LOG_TAG, "Url: " + url);
    Intent i = new Intent(this.cordova.getActivity(), WebViewActivity.class);
    i.putExtra("url", url);
    i.putExtra("shouldShowLoading", shouldShowLoading);
    this.cordova.getActivity().getApplicationContext().startActivity(i);
  }

  private void hideWebView() {
    LOG.d(LOG_TAG, "hideWebView");
    WebViewPlugin.webViewActivity.finish();
    if(subscribeCallbackContext != null){
      LOG.d(LOG_TAG, "Calling subscribeCallbackContext success");
      subscribeCallbackContext.success();
      subscribeCallbackContext = null;
    }
  }

  public void callDebugCallback() {
    if(subscribeDebugCallbackContext != null){
      LOG.d(LOG_TAG, "Calling subscribeCallbackContext success");
      PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
      pluginResult.setKeepCallback(true);
      subscribeDebugCallbackContext.sendPluginResult(pluginResult);
    }
  }

  public void callResumeCallback() {
    if(subscribeResumeCallbackContext != null){
      LOG.d(LOG_TAG, "Calling subscribeCallbackContext success");
      PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
      pluginResult.setKeepCallback(true);
      subscribeResumeCallbackContext.sendPluginResult(pluginResult);
    }
  }
}
