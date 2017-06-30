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


    public void mockCallback() throws Exception {

        Exception exception = null;

        MockUtils mockList = MyServer.mocks;
        MyCallBack<CountryResponse> myCallBack = new MyCallBack();

        APIService mock = null;

        try {
//            ////////////////////
//            //MOCK FOR THIS TEST
//            ////////////////////
//            List<PostJson> mockList2 = JsonConverter.convertJsonToObject("{\"classname\":\"com.example.callback.APIService\",\"method\":\"country\",\"in\":[{\"classname\":\"java.lang.String\",\"value\":\"FRANCE\"}],\"out\":[{\"classname\":\"com.example.callback.CountryResponse\",\"value\":\"{\\\"RestResponse\\\" : { \\\"messages\\\":[], \\\"result\\\" : [{\\\"name\\\": \\\"India\\\",\\\"alpha2_code\\\":\\\"IN\\\",\\\"alpha3_code\\\":\\\"IND\\\"}]}}\"}]}");
//            for (PostJson json : mockList2) {
//                try {
//                    List<Object> args = JsonConverter.convertToInstance(json.getIn());
//                    List<Object> results = JsonConverter.convertToInstance(json.getOut());
//
//                    MockUtils.setMock(MockMethodEnum.CALLBACK, json.getClassname(), json.getMethod(), args.toArray(), results);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

            mock = (APIService) mockList.getMock("com.example.callback.APIService");

            APIHelper.setAPIService(mock);

            Call<CountryResponse> call = APIHelper.getAPIService().country("FRANCE");

            call.enqueue(myCallBack);

            CountryResponse response = myCallBack.getApiResponse();

            assertEquals("India", response.getRestResponse().getResult().get(0).getName());

        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }

        APIHelper.reinitService();

        Call<CountryResponse> call = APIHelper.getAPIService().country("FRANCE");

        call.enqueue(myCallBack);

        //waiting for previous api call finished
        Thread.sleep(3000);

        CountryResponse response = myCallBack.getApiResponse();

        assertEquals("France", response.getRestResponse().getResult().get(0).getName());

        if (exception != null)
            throw exception;
    }
}
