package cl.kunder.webview;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.cordova.CordovaActivity;

public class WebViewActivity extends CordovaActivity {
    static Dialog dialog;
    static Activity activity2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Aqui debo crear el loading
        activity2 = this;
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
        
        loadUrl((url.matches("^(.*://|javascript:)[\\s\\S]*$") ? "" : "file:///android_asset/www/" + (isPluginCryptFileActive() ? "+++/" : "")) + url);
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

    /**
     * Revisa si existe el plugin cordova-plugin-crypt-file
     * @return boolean
     */
    private boolean isPluginCryptFileActive() {
        try {
            Class.forName("com.tkyaji.cordova.DecryptResource");
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
