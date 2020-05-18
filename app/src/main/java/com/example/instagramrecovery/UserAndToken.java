package com.example.instagramrecovery;

public class UserAndToken {
    private String access_token;
    private String user_id;

    public String getToken() {
        return access_token;
    }

    public void setToken(String token) {
        this.access_token = token;
    }

    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }
}