package com.example.ecobeeloginapp.data.api;

import com.example.ecobeeloginapp.data.model.CatApiResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/***
 * The APIService Interface to connect to the API
 */
public interface ApiServiceInterface {
    @GET(ApiConstants.CAT_API_URL)
    Single<List<CatApiResponse>> fetchCatImage(@Query("api_key") String apiKey);
}
