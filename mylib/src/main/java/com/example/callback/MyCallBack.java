package com.example.callback;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patrickvongpraseuth on 15/06/2017.
 */

public class MyCallBack<CountryResponse> implements Callback<CountryResponse> {

    //save for unit tests
    private CountryResponse apiResponse;

    @Override
    public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {

        apiResponse = response.body();

        CountryResponse test = response.body();

        //DO STUFF WITH RESPONSE
    }

    @Override
    public void onFailure(Call call, Throwable t) {
    }

    public CountryResponse getApiResponse() {
        if (apiResponse instanceof ArrayList) {
            return (CountryResponse) ((ArrayList) apiResponse).get(0);
        }
        return apiResponse;
    }
}
