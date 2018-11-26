package com.ais.mnc.view.system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.ais.mnc.R;

public class WebPageActivity extends AppCompatActivity {
    private static final String TAG = "WebPageActivity >>> ";

    WebView webView;
    FrameLayout frameLayout;

    String str_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnc_webpage);

        frameLayout = findViewById(R.id.web_frame);
        str_url = getIntent().getExtras().getString("para");

        initWebPage();
    }

    private void initWebPage() {
        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG,"browsing url: " + url);
                view.loadUrl(url);
                return true;
            }
        });
        frameLayout.addView(webView);
        webView.loadUrl(str_url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.setTag(null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}
