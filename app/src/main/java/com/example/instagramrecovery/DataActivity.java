package com.example.instagramrecovery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    public String accessToken;
    public String userId;
    public GetToken interfaceGetToken;

    public String app_id = "598355621029954";
    public String app_secret = "9e1642c8c88dcdeada90d8505e3903e2";
    public String redirect_uri = "https://math-dzieu.github.io/Info.github.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        accessToken = sharedPreferences.getString("code", null);
        Log.i("MyLog", "accessToken " + accessToken );
        getTheAccessToken();

    }


    private void getTheAccessToken(){
        Log.i("MyLog", "getTheAccessToken");
        Call<UserAndToken> call = interfaceGetToken.requestGetAccessToken(app_id, app_secret, "authorization_code", redirect_uri, accessToken);
        call.enqueue(new Callback<UserAndToken>() {
            @Override
            public void onResponse(Call<UserAndToken> call, Response<UserAndToken> response) {
                if(response.isSuccessful()){
                    Log.i("MyLog", "is Successful + response : " + response.body());
                    Toast.makeText(getApplicationContext(), "Success ", Toast.LENGTH_SHORT).show();
                    setAccessToken(response.body().getToken());
                    setUserId(response.body().getId());
                    saveUserIdAccessToken(getAccessToken(), getUserId());
                }
                else{
                    Log.i("MyLog", "isn't Successful + response : " + response.body());
                    Toast.makeText(getApplicationContext(), "Information are not correct" + response.body(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserAndToken> call, Throwable t) {
                Log.i("MyLog", "Error in callback : ");
                Toast.makeText(getApplicationContext(), "Error during connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveUserIdAccessToken(String token, String id){
        Log.i("MyLog", "token : " + token);
        Log.i("MyLog", "id : " + id);
        sharedPreferences
                .edit()
                .putString("access_token", token)
                .putString("user_id", id)
                .apply();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}