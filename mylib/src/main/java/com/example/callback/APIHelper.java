package com.example.callback;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by patrickvongpraseuth on 30/05/2017.
 */

public class APIHelper {

    private static APIService apiService;

    public APIHelper() {
        init();
    }

    private static void init() {
        apiService = new Retrofit.Builder()
                .baseUrl(APIService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService.class);
    }

    public static APIService getAPIService() {
        if (apiService == null) {
            init();
        }
        return apiService;
    }

    public static void setAPIService(APIService service) {
        apiService = service;
    }

    public static void reinitService() {
        init();
    }
}
