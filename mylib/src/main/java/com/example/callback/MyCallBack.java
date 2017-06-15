package com.example.callback;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patrickvongpraseuth on 15/06/2017.
 */

public class MyCallBack<CountryResponse> implements Callback<CountryResponse> {

    //save for unit tests
    private Response<CountryResponse> apiResponse;

    @Override
    public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {

        apiResponse = response;

        CountryResponse teste = response.body();

        //DO STUFF WITH RESPONSE
    }

    @Override
    public void onFailure(Call call, Throwable t) {
    }

    public Response<CountryResponse> getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(Response<CountryResponse> apiResponse) {
        this.apiResponse = apiResponse;
    }

}
