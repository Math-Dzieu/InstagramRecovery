package com.example.instagramrecovery.data;

import com.example.instagramrecovery.presentation.model.UserAndToken;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * The purpose of this interface is to create a POST request to the instagram api in order to be redirected to a desired link with an access token.
 * This token will then be used for the rest of the requests.
 */
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

