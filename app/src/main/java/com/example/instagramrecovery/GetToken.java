package com.example.instagramrecovery;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GetToken {
    @FormUrlEncoded
    @POST("/oauth/access_token/")
    Call<UserAndToken> requestGetAccessToken(
            @Field("client_id") String id,
            @Field("client_secret") String client_id,
            @Field("grant_type") String authorization_code,
            @Field("redirect_uri") String redirect_uri,
            @Field("code") String code);
}

