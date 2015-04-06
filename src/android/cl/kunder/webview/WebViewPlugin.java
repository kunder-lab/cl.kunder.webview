package cl.kunder.webview;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.LinearLayoutSoftKeyboardDetect;
import org.apache.cordova.PluginEntry;
import org.apache.cordova.Whitelist;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.LOG;

public class WebViewPlugin extends CordovaPlugin {

  private static final String LOG_TAG = "WebViewPlugin";

  private static Dialog webViewDialog;

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

        CordovaWebView otherWebView = new CordovaWebView(context);
        CordovaWebViewClient client = otherWebView.makeWebViewClient((CordovaActivity)activity);
        CordovaChromeClient chrome = otherWebView.makeWebChromeClient((CordovaActivity)activity);

        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse((CordovaActivity)activity);

        Whitelist internalWhitelist = parser.getInternalWhitelist();
        Whitelist externalWhitelist = parser.getExternalWhitelist();
        ArrayList<PluginEntry> pluginEntries = parser.getPluginEntries();

        otherWebView.init(
        (CordovaActivity)activity,
        client,
        chrome,
        pluginEntries,
        internalWhitelist,
        externalWhitelist,
        preferences);


        otherWebView.loadUrl("file:///android_asset/www/" + url);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);;
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;

        LinearLayout root = new LinearLayoutSoftKeyboardDetect((CordovaActivity)activity, width, height);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT, 0.0F));

        otherWebView.setId(101);
        otherWebView.setLayoutParams(new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT,
        1.0F));

        root.addView((View) otherWebView);

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
          plugin.webView.destroy();
        }
      }
    });
  }
}
