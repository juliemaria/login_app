package com.example.ecobeeloginapp.data.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CatApiResponseWrapper {



    @Expose
    private List<CatApiResponse> catApiResponseList;

    public List<CatApiResponse> getCatApiResponseList() {
        return catApiResponseList;
    }

    public void setCatApiResponseList(List<CatApiResponse> catApiResponseList) {
        this.catApiResponseList = catApiResponseList;
    }




}
