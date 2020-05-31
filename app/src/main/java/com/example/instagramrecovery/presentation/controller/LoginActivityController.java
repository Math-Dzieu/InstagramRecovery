package com.example.instagramrecovery.presentation.controller;

import android.content.Intent;

import com.example.instagramrecovery.presentation.view.DataActivity;
import com.example.instagramrecovery.presentation.view.LoginActivity;

public class LoginActivityController {

    public LoginActivity view;

    public LoginActivityController(LoginActivity loginActivity) {
        this.view = loginActivity;
    }

    public void onPictureClick(){
        Intent intent = new Intent(view.getApplicationContext(), DataActivity.class);
        view.getApplicationContext().startActivity(intent);
    }
}
