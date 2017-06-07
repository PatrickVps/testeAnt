package com.example.ws;

import com.fasterxml.jackson.databind.util.ClassUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import retrofit.RestAdapter;


/**
 * Created by patrickvongpraseuth on 30/05/2017.
 */

public class WebserviceAPI {

    private IWebservice githubService;

    public WebserviceAPI(){

        githubService = new RestAdapter.Builder()
                .setEndpoint(IWebservice.ENDPOINT)
                .build()
                .create(IWebservice.class);
    }


    public String getCountryName(String country){

        List<Response> list = getCountries(country);

        list = convertToResponse(list);

        return list.get(0).getName();
    }


    public List<Response> getCountries(String country){

        LinkedTreeMap test = githubService.country(country);

        List teste = ((List<Object>) ((LinkedTreeMap)test.get("RestResponse")).get("result"));

        return convertToResponse(teste);
    }


    public List<Response> getCountryFromISO2(String country){

        LinkedTreeMap test = githubService.countryByISO2(country);

        Object result = ((LinkedTreeMap)test.get("RestResponse")).get("result");

        List list = new ArrayList();
        list.add(result);
        return convertToResponse(list);
    }


    public List<Response> getCountryFromISO3(String country){

        LinkedTreeMap test = githubService.countryByISO3(country);

        Object result = ((LinkedTreeMap)test.get("RestResponse")).get("result");

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
