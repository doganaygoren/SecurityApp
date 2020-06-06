package com.example.security;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LiveFeedActivity extends AppCompatActivity {

    private WebView liveFeed;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);
        liveFeed= findViewById(R.id.liveFeed);
        liveFeed.setWebViewClient(new WebViewClient());
        liveFeed.loadUrl("http://35.204.170.200/client"); //Server IP which mehmet created

        WebSettings webSettings= liveFeed.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {

        if(liveFeed.canGoBack()){
            liveFeed.goBack();
        }else {
            super.onBackPressed();
        }
    }
}
