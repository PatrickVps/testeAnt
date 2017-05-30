package com.example.ws;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by patrickvongpraseuth on 29/05/2017.
 */

public interface IWebservice {

        public static final String ENDPOINT = "http://services.groupkt.com";

        @GET("/country/search")
        LinkedTreeMap country(@Query("text") String query);

        @GET("/country/get/iso2code/{alpha2_code}")
        LinkedTreeMap countryByISO2(@Path("alpha2_code") String code);

        @GET("/country/get/iso3code/{alpha3_code}")
        LinkedTreeMap countryByISO3(@Path("alpha3_code") String code);
}

