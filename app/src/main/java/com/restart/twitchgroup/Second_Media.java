package com.restart.twitchgroup;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Second_Media extends Activity {
    private ProgressDialog dialog;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        dialog = ProgressDialog.show(Second_Media.this, "", "Loading. Please wait...", true);
        webView = (WebView) findViewById(R.id.webView3);
        webView.setVisibility(View.INVISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewController() {
            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
                webView.setVisibility(View.VISIBLE);
            }
        });

        webView.loadUrl("https://www.twitter.com/");
    }

    public class WebViewController extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}