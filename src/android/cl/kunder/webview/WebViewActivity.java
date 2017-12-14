package cl.kunder.webview;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.cordova.CordovaActivity;

public class WebViewActivity extends CordovaActivity {
    static Dialog dialog;
    static Activity activity2;
    private boolean hasPausedEver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Aqui debo crear el loading
        activity2 = this;
        WebViewPlugin.webViewActivity = this;
        Bundle b = getIntent().getExtras();
        String url = b.getString("url");
        Boolean shouldShowLoading = false;
        try{
            shouldShowLoading = b.getBoolean("shouldShowLoading");
        }
        catch(Exception e){

        }
        if(shouldShowLoading){
            showLoading();
        }
        loadUrl((url.matches("^(.*://|javascript:)[\\s\\S]*$")?"":"file:///android_asset/www/")+url);
        appView.getView().setOnTouchListener(new View.OnTouchListener() {
            Handler handler = new Handler();

            int numberOfTaps = 0;
            long lastTapTimeMs = 0;
            long touchDownMs = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchDownMs = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacksAndMessages(null);

                        if ((System.currentTimeMillis() - touchDownMs) > ViewConfiguration.getTapTimeout()) {
                            //it was not a tap

                            numberOfTaps = 0;
                            lastTapTimeMs = 0;
                            break;
                        }

                        if (numberOfTaps > 0
                                && (System.currentTimeMillis() - lastTapTimeMs) < ViewConfiguration.getDoubleTapTimeout()) {
                            numberOfTaps += 1;
                        } else {
                            numberOfTaps = 1;
                        }

                        lastTapTimeMs = System.currentTimeMillis();

                        if (numberOfTaps == 5) {
                            WebViewPlugin.webViewPlugin.callDebugCallback();
                        }
                }

                return false;
            }
        });
    }

    public static boolean showLoading() {
        // Loading spinner
        activity2.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog = new Dialog(activity2,android.R.style.Theme_Translucent_NoTitleBar);
                ProgressBar progressBar = new ProgressBar(activity2,null,android.R.attr.progressBarStyle);

                LinearLayout linearLayout = new LinearLayout(activity2);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                RelativeLayout layoutPrincipal = new RelativeLayout(activity2);
                layoutPrincipal.setBackgroundColor(Color.parseColor("#d9000000"));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);

                linearLayout.addView(progressBar);

                linearLayout.setLayoutParams(params);

                layoutPrincipal.addView(linearLayout);

                dialog.setContentView(layoutPrincipal);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                });
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                        if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK)
                            return true;
                        return false;
                    }
                });

                dialog.show();
            }
        });

        return true;
    }

    public static boolean hideLoading() {
        // Loading spinner
        activity2.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.hide();
            }
        });
        return true;
    }

    public String getUrl() {
        return appView.getUrl();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (hasPausedEver && WebViewPlugin.webViewPlugin != null) {
            WebViewPlugin.webViewPlugin.callResumeCallback();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hasPausedEver = true;
    }
}
