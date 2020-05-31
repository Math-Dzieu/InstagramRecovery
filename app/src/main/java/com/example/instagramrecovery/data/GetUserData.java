package com.example.instagramrecovery.data;

import com.example.instagramrecovery.presentation.model.RestDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This interface will send a GET request to the instagram api to retrieve a JSON file containing the user's information.
 */
public interface GetUserData {
    @GET("/me/media")
    Call<RestDataResponse> requestGetData(@Query("fields") String field, @Query("access_token") String accessToken);

}

