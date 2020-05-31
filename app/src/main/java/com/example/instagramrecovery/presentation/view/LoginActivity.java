package com.example.instagramrecovery.presentation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.instagramrecovery.MyWebViewClient;
import com.example.instagramrecovery.R;
import com.example.instagramrecovery.presentation.controller.LoginActivityController;

/**
 * This activity is the loging activity, it's permit to connect at instagram and be redirect on other link with the access token and user id to get all data from the user
 */
public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_picture;
    WebView webview;
    public String linkUrl = "https://api.instagram.com/oauth/authorize?client_id=598355621029954&redirect_uri=https://math-dzieu.github.io/Info.github.io&scope=user_profile,user_media&response_type=code";
    public Context mContext;
    public MyWebViewClient mWebViewClient;
    public LoginActivityController loginActivityController;

    /**
     * When the activity start, defined all element int the activity and run function from the controller
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login =(Button)findViewById(R.id.LoginButton);
        btn_picture =(Button)findViewById(R.id.PicButton);
        webview =(WebView)findViewById(R.id.web_view);
        mContext = getApplicationContext();

        mWebViewClient = new MyWebViewClient(linkUrl, mContext);
        webview.setWebViewClient(mWebViewClient);

        loginActivityController = new LoginActivityController(this);

        btn_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                goUrl();
            }
        });

        btn_picture.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                loginActivityController.onPictureClick();
            }
        });

    }

    /**
     * setting the webview and load the url
     */
    private void goUrl() {
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.loadUrl(mWebViewClient.getLinkUrl());
    }
}