package com.example.instagramrecovery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.instagramrecovery.presentation.view.DataActivity;

public class MyWebViewClient extends WebViewClient {

    public Context mContext;
    public String linkUrl;
    public String exchangeCode;
    public String code;
    public SharedPreferences sharedPreferences;

    public MyWebViewClient(String linkUrl, Context mContext) {
        this.linkUrl = linkUrl;
        this.mContext = mContext;
    }

    // When you click on any interlink on webview.
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.i("MyLog","Click on any interlink on webview that time you got url :-" + url);
        url = getLinkUrl();
        return super.shouldOverrideUrlLoading(view, url);
    }

    // When page loading
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.i("MyLog", "Current url when webpage loading.." + url);
    }

    // When page load finish.
    @Override
    public void onPageFinished(WebView view, String url) {
        String accessCode;
        String urlWithCode;
        sharedPreferences = mContext.getSharedPreferences("userData",Context.MODE_PRIVATE);
        Log.i("MyLog", "Your current url when webpage loading.. finish " + url);
        if(url.startsWith("https://math-dzieu.github.io/Info.github.io/?code=")){
            urlWithCode = url;
            Log.i("MyLog", "Your url with code is : " + urlWithCode);
            setCode(urlWithCode);
            accessCode = urlWithCode.substring(urlWithCode.indexOf("=") + 1, urlWithCode.indexOf("#"));
            Log.i("MyLog", "Your code in the url : " + accessCode);
            setExchangeCode(accessCode);
            saveData(accessCode);
            Log.i("MyLog", "mContext : " + mContext);
            goToDataActivity(mContext);
        }
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }


    private void saveData(String access) {
        Log.i("MyLog", "token saveData() : " + access);
        sharedPreferences
                .edit()
                .putString("code", access)
                .apply();

        Toast.makeText(mContext, "succes save", Toast.LENGTH_SHORT).show();
    }

    public void goToDataActivity(Context context){
        Intent intent = new Intent(context, DataActivity.class);
        context.startActivity(intent);
    }


    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}