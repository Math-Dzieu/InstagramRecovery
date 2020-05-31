package com.example.instagramrecovery.presentation.model;

import java.util.List;

/**
 * This class permit to get the list of Data for each post of the user
 */
public class RestDataResponse {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}

