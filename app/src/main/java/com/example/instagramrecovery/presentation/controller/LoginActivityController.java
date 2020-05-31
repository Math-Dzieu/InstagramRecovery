package com.example.instagramrecovery.presentation.controller;

import android.content.Intent;

import com.example.instagramrecovery.presentation.view.DataActivity;
import com.example.instagramrecovery.presentation.view.LoginActivity;

/**
 * Controller from the LoginActivity View :
 * Includes function to change screen after clicking
 */
public class LoginActivityController {

    public LoginActivity view;

    /**
     * Constructor of the LoginActivityController
     * @param loginActivity : view of the activity
     */
    public LoginActivityController(LoginActivity loginActivity) {
        this.view = loginActivity;
    }

    /**
     * After clicking on the button go to the activity with data on cache, if is the first use, nothing will be present on this activity
     */
    public void onPictureClick(){
        Intent intent = new Intent(view.getApplicationContext(), DataActivity.class);
        view.getApplicationContext().startActivity(intent);
    }
}
