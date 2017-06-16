package com.sfeir.testant.tests;

import com.example.callback.APIHelper;
import com.example.callback.APIService;
import com.example.callback.CountryResponse;
import com.example.callback.MyCallBack;
import com.sfeir.testant.server.MyServer;
import com.sfeir.testant.utils.MockUtils;

import retrofit2.Call;

import static org.junit.Assert.assertEquals;

/**
 * Created by patrickvongpraseuth on 16/06/2017.
 */

public class TestAPIServiceClass {


    public void mockCallback() throws InterruptedException {

        MockUtils mockList = MyServer.mocks;
        MyCallBack<CountryResponse> myCallBack = new MyCallBack();

        APIService mock = (APIService) mockList.getMock("com.example.callback.APIService");

        APIHelper.setAPIService(mock);

        Call<CountryResponse> call = APIHelper.getAPIService().country("FRANCE");

        call.enqueue(myCallBack);

        CountryResponse response = myCallBack.getApiResponse();

        assertEquals("India", response.getRestResponse().getResult().get(0).getName());


        APIHelper.reinitService();

        call = APIHelper.getAPIService().country("FRANCE");

        call.enqueue(myCallBack);

        //waiting for previous api call finished
        Thread.sleep(3000);

        response = myCallBack.getApiResponse();

        assertEquals("France", response.getRestResponse().getResult().get(0).getName());
    }
}
