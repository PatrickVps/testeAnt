package com.example.ws;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by patrickvongpraseuth on 30/05/2017.
 */

public class WebserviceAPI {

    private IWebservice githubService;

    public WebserviceAPI() {

        githubService = new Retrofit.Builder()
                .baseUrl(IWebservice.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IWebservice.class);
    }


    public String getCountryName(String country) {

        List<Response> list = getCountries(country);

        list = convertToResponse(list);

        return list.get(0).getName();
    }


    public List<Response> getCountries(String country) {

        Call<LinkedTreeMap> test = githubService.country(country);

//        List teste = ((List<Object>) ((LinkedTreeMap) test.get("RestResponse")).get("result"));

        WebServiceCallBack callBack = new WebServiceCallBack();
        test.enqueue(callBack);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List teste = ((List<Object>) ((LinkedTreeMap) (((LinkedTreeMap) callBack.getApiResponse()).get("RestResponse"))).get("result"));

        return convertToResponse(teste);
    }


    public List<Response> getCountryFromISO2(String country) {

        Call<LinkedTreeMap> test = githubService.countryByISO2(country);

        WebServiceCallBack callBack = new WebServiceCallBack();
        test.enqueue(callBack);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object result = ((LinkedTreeMap) (((LinkedTreeMap) callBack.getApiResponse()).get("RestResponse"))).get("result");

        List list = new ArrayList();
        list.add(result);
        return convertToResponse(list);
    }


    public List<Response> getCountryFromISO3(String country) {

        Call<LinkedTreeMap> test = githubService.countryByISO3(country);

//        Object result = ((LinkedTreeMap) test.get("RestResponse")).get("result");

        WebServiceCallBack callBack = new WebServiceCallBack();
        test.enqueue(callBack);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Object result = ((LinkedTreeMap) (((LinkedTreeMap) callBack.getApiResponse()).get("RestResponse"))).get("result");

        List list = new ArrayList();
        list.add(result);

        return convertToResponse(list);
    }


    private List<Response> convertToResponse(List inputList) {

        List<Response> list = new ArrayList<>();

        for (Object result : inputList) {
            String json = new Gson().toJson(result);
            Response model = new Gson().fromJson(json, Response.class);
            list.add(model);
        }
        return list;
    }


    public static Response convertToResponse(String result) {

        Response model = new Gson().fromJson(result, Response.class);

        return model;
    }


}
