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
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login =(Button)findViewById(R.id.LoginButton);
        webview =(WebView)findViewById(R.id.web_view);
        mContext = getApplicationContext();


        btn_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                goUrl();
            }
        });

    }

    private void goUrl() {
        //The webview must connect to a desired link so that it links to a link with an access code to get the instagram token.
    }

}