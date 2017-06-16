package com.sfeir.testant.utils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickvongpraseuth on 16/06/2017.
 */

public class JsonConverter {

    public static List<Object> convertToGeneric(JSONObject json, String in) throws JSONException {
//        {
//            "in" : [
//            {"class" : "java.lang.Integer", "value" : "1"},
//            {"class" : "java.lang.String", "value" : "test"}
//            ],
//            "out" : [{
//            "class" : "com.example.ws.Response",
//                    "value" : {
//                "name" : "India",
//                        "alpha2_code" : "IN",
//                        "alpha3_code" : "IND"
//            }
//        }]
//        }

        JSONArray jsonArray = json.getJSONArray(in);

        Object jsonElement;
        Object model = null;
        List<Object> result = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonElement = jsonArray.get(i);

            try {
                String value = ((JSONObject) jsonElement).get("value").toString();
                String classe = ((JSONObject) jsonElement).getString("class");
                model = new Gson().fromJson(value, Class.forName(classe));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            result.add(model);
        }
        return result;
    }

}
