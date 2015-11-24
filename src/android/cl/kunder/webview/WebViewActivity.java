package cl.kunder.webview;

import android.os.Bundle;

import org.apache.cordova.CordovaActivity;

public class WebViewActivity extends CordovaActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String url = b.getString("url");
        loadUrl("file:///android_asset/www/"+url);
    }
}
