package com.example.ws;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patrickvongpraseuth on 15/06/2017.
 */

public class WebServiceCallBack<LinkedTreeMap> implements Callback<LinkedTreeMap> {

    //save for unit tests
    private LinkedTreeMap apiResponse;

    @Override
    public void onResponse(Call<LinkedTreeMap> call, Response<LinkedTreeMap> response) {

        apiResponse = response.body();

        LinkedTreeMap test = response.body();

        //DO STUFF WITH RESPONSE
    }

    @Override
    public void onFailure(Call call, Throwable t) {
    }

    public LinkedTreeMap getApiResponse() {
        if (apiResponse instanceof ArrayList) {
            return (LinkedTreeMap) ((ArrayList) apiResponse).get(0);
        }
        return apiResponse;
    }
}
