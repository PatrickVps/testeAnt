package com.example.callback;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by patrickvongpraseuth on 29/05/2017.
 */

public interface APIService {

    public static final String ENDPOINT = "http://services.groupkt.com";

    @GET("/country/search")
    Call<CountryResponse> country(@Query("text") String query);
}

