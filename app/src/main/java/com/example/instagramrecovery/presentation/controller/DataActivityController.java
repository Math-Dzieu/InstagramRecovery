package com.example.instagramrecovery.presentation.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.instagramrecovery.data.GetToken;
import com.example.instagramrecovery.data.GetUserData;
import com.example.instagramrecovery.Constants;
import com.example.instagramrecovery.presentation.model.Data;
import com.example.instagramrecovery.presentation.model.RestDataResponse;
import com.example.instagramrecovery.presentation.model.UserAndToken;
import com.example.instagramrecovery.presentation.view.DataActivity;
import com.example.instagramrecovery.presentation.view.InstagramPost;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class DataActivityController {
    public SharedPreferences sharedPreferences;
    public String accessToken;
    public Gson gson;
    public String userId;
    public DataActivity view;

    public DataActivityController(DataActivity dataActivity, SharedPreferences sharedPreferences, Gson gson) {
        this.view = dataActivity;
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
    }

    public void onStart(){
        sharedPreferences = view.getBaseContext().getSharedPreferences("userData", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("code", null);
        Log.i("MyLog", "accessToken " + accessToken );
        getTheAccessToken();

        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Data> dataList = getDataFromCache();
        if(dataList != null){
            view.showDataList(dataList);
        }else{
            getTheUserData();
        }
    }

    public void onItemClick(){
        getTheUserData();
        List<Data> dataList = getDataFromCache();
        view.showDataList(dataList);
    }

    public void getTheUserData() {
        String typeData = "caption,id,media_type,media_url,username,timestamp";
        String accessToken = sharedPreferences.getString("access_token", null);
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GetUserData getUserData = retrofit2.create(GetUserData.class);
        Call<RestDataResponse> call = getUserData.requestGetData(typeData, accessToken);
        Log.i("MyLog", "getUserData.get_Data(accessToken) " + getUserData.requestGetData(typeData, accessToken).request().url());
        call.enqueue(new Callback<RestDataResponse>() {
            @Override
            public void onResponse(Call<RestDataResponse> call, Response<RestDataResponse> response) {
                if(response.isSuccessful()&&response.body() != null){
                    Log.i("MyLog", "response.raw().request().url() : " + response.raw().request().url());
                    List<Data> dataList = response.body().getData();
                    saveDataList(dataList);
                    Log.i("MyLog", "saveList Successful");
                    view.showDataList(dataList);
                    Log.i("MyLog", "showList Successful");
                }
                else{
                    Log.i("MyLog", "response.raw().request().url() : " + response.raw().request().url());
                    Log.i("MyLog", "isn't Successful + response : " + response.body());
                    Log.i("MyLog", "response.code() : " + response.code());
                    Toast.makeText(view.getApplicationContext(), "Information are not correct" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestDataResponse> call, Throwable t) {
                Log.i("MyLog", "Error in callback : " + t);
                Toast.makeText(view.getApplicationContext(), "Error during connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveDataList(List<Data> dataList) {
        String jsonString = gson.toJson(dataList);
        sharedPreferences
                .edit()
                .putString("jsonDataList", jsonString)
                .apply();
        Toast.makeText(view.getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();
    }

    public List<Data> getDataFromCache() {
        String jsonData = sharedPreferences.getString("jsonDataList", null);

        if (jsonData == null){
            return null;
        }else{
            Type listType = new TypeToken<List<Data>>(){}.getType();
            return gson.fromJson(jsonData, listType);
        }
    }

    private void getTheAccessToken(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        GetToken interfaceGetToken = retrofit.create(GetToken.class);

        Log.i("MyLog", "getTheAccessToken");
        Call<UserAndToken> call = interfaceGetToken.requestGetAccessToken(Constants.app_id, Constants.app_secret, "authorization_code", Constants.redirect_uri, accessToken);
        call.enqueue(new Callback<UserAndToken>() {
            @Override
            public void onResponse(Call<UserAndToken> call, Response<UserAndToken> response) {
                if(response.isSuccessful()){
                    Log.i("MyLog", "is Successful + response : " + response.body());
                    Toast.makeText(view.getApplicationContext(), "Success ", Toast.LENGTH_SHORT).show();
                    setAccessToken(response.body().getToken());
                    setUserId(response.body().getId());
                    saveUserIdAccessToken(getAccessToken(), getUserId());
                }
                else{
                    Log.i("MyLog", "isn't Successful + response : " + response.body());
                    Toast.makeText(view.getApplicationContext(), "Information are not correct" + response.body(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserAndToken> call, Throwable t) {
                Log.i("MyLog", "Error in callback : ");
                Toast.makeText(view.getApplicationContext(), "Error during connection", Toast.LENGTH_SHORT).show();
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


    public void navigateToDetails(Data item) {
        Intent intent = new Intent(view.getApplicationContext(), InstagramPost.class);
        intent.putExtra("PicUrl", item.getMedia_url());
        intent.putExtra("Comment", item.getCaption());
        view.getApplicationContext().startActivity(intent);
    }

}
