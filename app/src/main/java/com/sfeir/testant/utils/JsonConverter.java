package com.sfeir.testant.utils;

import com.google.gson.Gson;
import com.sfeir.testant.server.MyArgument;
import com.sfeir.testant.server.MyJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickvongpraseuth on 16/06/2017.
 */

public class JsonConverter {

    /**
     * Method transforming a String class to an instance class with its value
     *
     * @param list
     * @return
     * @throws JSONException
     */
    public static List<Object> convertToInstance(List<MyArgument> list) throws JSONException {

        List<Object> result = new ArrayList<>();

        for (MyArgument arg : list) {
            Object model = null;
            try {
                model = new Gson().fromJson(arg.getValue(), Class.forName(arg.getClassname()));
                result.add(model);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * Method allowing to convert a json format input to our custom object @{@link MyJson}
     *
     * @param jsonIn
     * @return
     */
    public static List<MyJson> convertJsonToObject(String jsonIn) {

        List<MyJson> mockList = new ArrayList<>();
        JSONArray jsonMock = new JSONArray();

        try {
            jsonMock = new JSONArray(jsonIn);
        } catch (JSONException e) {
            //if input is not an array, we create an array with one element
            try {
                JSONObject obj = new JSONObject(jsonIn);

                jsonMock = new JSONArray();
                jsonMock.put(obj);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        for (int i = 0; i < jsonMock.length(); i++) {
            try {
                JSONObject result = jsonMock.getJSONObject(i);

                //convert json to custom object
                MyJson model = new Gson().fromJson(result.toString(), MyJson.class);

                mockList.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mockList;
    }


    public static List<MyArgument> convertJsonToObject2(String jsonIn) {

        List<MyArgument> mockList = new ArrayList<>();
        JSONArray jsonMock = new JSONArray();

        try {
            jsonMock = new JSONArray(jsonIn);
        } catch (JSONException e) {
            //if input is not an array, we create an array with one element
            try {
                JSONObject obj = new JSONObject(jsonIn);

                jsonMock = new JSONArray();
                jsonMock.put(obj);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        for (int i = 0; i < jsonMock.length(); i++) {
            try {
                JSONObject result = jsonMock.getJSONObject(i);

                //convert json to custom object
                MyArgument model = new Gson().fromJson(result.toString(), MyArgument.class);

                mockList.add(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mockList;
    }
}
