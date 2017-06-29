package com.sfeir.testant.utils;

import com.google.gson.Gson;
import com.sfeir.testant.server.MyArgument;

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
     * Caution : Recursive method
     * <p>
     * Generic method which convert a list or object to a list or object instance
     *
     * @param args
     * @return
     * @throws JSONException
     */
    public static Object convertToInstance(Object args) throws JSONException {

        Object result = null;

        if (args instanceof List) {

            result = new ArrayList<>();

            for (Object arg : (List) args) {
                ((List) result).add(convertToInstance(arg));
            }
        } else {
            try {
                MyArgument arg = (MyArgument) args;

                result = new Gson().fromJson(arg.getValue(), Class.forName(arg.getClassname()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * Method allowing to convert a json format input to an object
     *
     * @param jsonIn
     * @return
     */
    public static Object convertJsonToObject(String jsonIn, Class classname) {
        try {
            List<Object> mockList = new ArrayList<>();
            JSONArray jsonMock = new JSONArray(jsonIn);

            for (int i = 0; i < jsonMock.length(); i++) {
                try {
                    JSONObject result = jsonMock.getJSONObject(i);

                    //convert json to custom object
                    Object model = new Gson().fromJson(result.toString(), classname);

                    mockList.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return mockList;
        } catch (JSONException e) {
            //if input is not an array, we create an object
            try {
                JSONObject obj = new JSONObject(jsonIn);

                //convert json to custom object
                Object model = new Gson().fromJson(obj.toString(), classname);

                return model;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

}
