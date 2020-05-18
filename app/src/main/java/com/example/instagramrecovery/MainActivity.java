package com.example.instagramrecovery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    WebView webview;
    public String linkUrl = "https://api.instagram.com/oauth/authorize?client_id=598355621029954&redirect_uri=https://math-dzieu.github.io/Info.github.io&scope=user_profile,user_media&response_type=code";
    public Context mContext;
    public MyWebViewClient mWebViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login =(Button)findViewById(R.id.LoginButton);
        webview =(WebView)findViewById(R.id.web_view);
        mContext = getApplicationContext();
        mWebViewClient = new MyWebViewClient(linkUrl, mContext);

        btn_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                goUrl();
            }
        });
        
    }

    private void goUrl() {
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.loadUrl(mWebViewClient.getLinkUrl());
    }

}
