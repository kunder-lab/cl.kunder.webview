package cl.kunder.webview;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;

import android.widget.LinearLayout;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaInterfaceImpl;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;

import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.PluginEntry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.LOG;

import org.apache.cordova.engine.*;



public class WebViewPlugin extends CordovaPlugin {

  private static final String LOG_TAG = "WebViewPlugin";

  private static Dialog webViewDialog;

  static SystemWebView webView;

  private static CallbackContext subscribeCallbackContext = null;

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

      if(!"".equals(url)) {
        showWebView(url);
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

    else if(action.equals("subscribeCallback")){
      LOG.d(LOG_TAG, "Subscribing Cordova CallbackContext");
      subscribeCallbackContext = callbackContext;
    }

    else {
      return false;
    }

    return true;
  }

  private void showWebView(final String url) {
    LOG.d(LOG_TAG, "Url: " + url);

    final CordovaPlugin plugin = this;

    plugin.cordova.getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Activity activity = plugin.cordova.getActivity();
        Context context = activity.getApplicationContext();
        //esta es la webView que cargaría la URL
        webView = new SystemWebView(context);
        CordovaWebView webInterface = new CordovaWebViewImpl(new SystemWebViewEngine(webView));
        CordovaInterface systemInterface = new CordovaInterfaceImpl(activity);
        CordovaPreferences cordovaPreferences = new CordovaPreferences();


        SystemWebViewEngine systemWebViewEngine = new SystemWebViewEngine(webView);

        //        CordovaWebView otherWebView = new CordovaWebView(context);
        //        CordovaWebViewClient client = otherWebView.makeWebViewClient((CordovaActivity)activity);
        //        CordovaChromeClient chrome = otherWebView.makeWebChromeClient((CordovaActivity)activity);

        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(activity);

        //        Whitelist internalWhitelist = parser.getInternalWhitelist();
        //        Whitelist externalWhitelist = parser.getExternalWhitelist();
        ArrayList<PluginEntry> pluginEntries = parser.getPluginEntries();

        webInterface.init(systemInterface,pluginEntries, parser.getPreferences());

        //        otherWebView.init(
        //        (CordovaActivity)activity,
        //        client,
        //        chrome,
        //        pluginEntries,
        //        internalWhitelist,
        //        externalWhitelist,
        //        preferences);

        webView.loadUrl("file:///android_asset/www/" + url);
        //otherWebView.loadUrl("file:///android_asset/www/" + url);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);;
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
        LinearLayout root = new LinearLayout(activity);
        //LinearLayout root = new LinearLayoutSoftKeyboardDetect((CordovaActivity)activity, width, height);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT, 0.0F));

        //webView.setId(101);

        webView.setLayoutParams(new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            1.0F));
        //otherWebView.setId(101);
        //        otherWebView.setLayoutParams(new LinearLayout.LayoutParams(
        //        ViewGroup.LayoutParams.MATCH_PARENT,
        //        ViewGroup.LayoutParams.MATCH_PARENT,
        //        1.0F));

        root.addView((View) webView);
        //root.addView((View) otherWebView);

        webViewDialog = new Dialog(activity, android.R.style.Theme_NoTitleBar);
        webViewDialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        webViewDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        webViewDialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(webViewDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        webViewDialog.setContentView(root);
        webViewDialog.show();
        webViewDialog.getWindow().setAttributes(lp);
      }
    });

  }

  private void hideWebView() {
    // Aquí estamos parados en cordova.webview
    // otherWebView es el objeto webview mirado desde la webview original
    final CordovaPlugin plugin = this;

    plugin.cordova.getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        if(webViewDialog != null && webViewDialog.isShowing()) {
          webViewDialog.dismiss();
          webView.destroy();
          if(subscribeCallbackContext != null){
            LOG.d(LOG_TAG, "Calling subscribeCallbackContext success");
            subscribeCallbackContext.success();
            subscribeCallbackContext = null;
          }
        }
      }
    });
  }
}
