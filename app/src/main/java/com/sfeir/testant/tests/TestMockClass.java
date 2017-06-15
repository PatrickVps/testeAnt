package com.sfeir.testant.tests;

import com.example.Operation;
import com.example.callback.APIHelper;
import com.example.callback.APIService;
import com.example.callback.CountryResponse;
import com.example.callback.MyCallBack;
import com.example.ws.WebserviceAPI;
import com.sfeir.testant.server.MyServer;
import com.sfeir.testant.utils.MockUtils;

import java.util.ArrayList;

import retrofit2.Call;

import static org.junit.Assert.assertEquals;


/**
 * Created by patrickvongpraseuth on 01/06/2017.
 */

public class TestMockClass {

    //This test should pass
    public void mockInjectionTest() {

        MockUtils m = MyServer.mocks;

        Operation op = (Operation) m.getMock("com.example.Operation");
        assertEquals(6, op.addition());

        assertEquals(1, op.substraction());

        WebserviceAPI ws = (WebserviceAPI) m.getMock("com.example.ws.WebserviceAPI");

        assertEquals("United States of America", ws.getCountries("FRANCE").get(0).getName());
    }


    public void mockCallback() {

        MockUtils m = MyServer.mocks;
        MyCallBack myCallBack = new MyCallBack();

        APIService mock = (APIService) m.getMock("com.example.callback.APIService");

        APIHelper.setAPIService(mock);

        Call<CountryResponse> test = APIHelper.getAPIService().country("FRANCE");

        test.enqueue(myCallBack);

        CountryResponse response = (CountryResponse) ((ArrayList) myCallBack.getApiResponse().body()).get(0);

        assertEquals("India", response.getRestResponse().getResult().get(0).getName());


        APIHelper.reinitService();

        test = APIHelper.getAPIService().country("FRANCE");

        test.enqueue(myCallBack);

        response = (CountryResponse) (myCallBack.getApiResponse().body());

        assertEquals("France", response.getRestResponse().getResult().get(0).getName());

    }

}
