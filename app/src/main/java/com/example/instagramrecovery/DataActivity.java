package com.example.instagramrecovery;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataActivity extends AppCompatActivity {

    private final String BASE_URL2 = "https://graph.instagram.com";

    public SharedPreferences sharedPreferences;
    public String accessToken;
    public String userId;
    public GetToken interfaceGetToken;

    public String app_id = "598355621029954";
    public String app_secret = "9e1642c8c88dcdeada90d8505e3903e2";
    public String redirect_uri = "https://math-dzieu.github.io/Info.github.io";

    public Gson gson;
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        accessToken = sharedPreferences.getString("code", null);
        Log.i("MyLog", "accessToken " + accessToken );
        getTheAccessToken();

        gson = new GsonBuilder()
                .setLenient()
                .create();
        List<Data> dataList;
        getTheUserData();
    }


    private void getTheUserData() {
        String typeData = "caption,id,media_type,media_url,username,timestamp";
        String accessToken = sharedPreferences.getString("access_token", null);
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(BASE_URL2)
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
                    showDataList(dataList);
                    Log.i("MyLog", "showList Successful");
                }
                else{
                    Log.i("MyLog", "response.raw().request().url() : " + response.raw().request().url());
                    Log.i("MyLog", "isn't Successful + response : " + response.body());
                    Log.i("MyLog", "response.code() : " + response.code());
                    Toast.makeText(getApplicationContext(), "Information are not correct" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestDataResponse> call, Throwable t) {
                Log.i("MyLog", "Error in callback : " + t);
                Toast.makeText(getApplicationContext(), "Error during connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDataList(List<Data> dataList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(dataList);
        recyclerView.setAdapter(mAdapter);
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