package com.sfeir.testant.server;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.google.gson.Gson;
import com.sfeir.testant.utils.MockUtils;
import com.sfeir.testant.utils.TestRunner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;

import static android.content.Context.WIFI_SERVICE;

public class MyServer extends NanoHTTPD {

    private final static int PORT = 8080;

    private Context context;

    public static MockUtils mocks;


    public MyServer(Context context) throws IOException {
        super(PORT);
        start();

        mocks = new MockUtils();

        this.context = context;

        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        System.out.println("\nServer Running at http://" + ip + ":8080/ \n");
    }


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


    @Override
    public Response serve(final IHTTPSession session) {

        super.serve(session);

        switch (session.getUri()) {

            case "/runTests":
                String[] array = {
                        "com.sfeir.testant.tests.TestAPIServiceClass",
                        "com.sfeir.testant.tests.TestWebserviceAPIClass"
                };

                try {
                    TestRunner.runTest(array);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "/mockRemove":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mocks.removeMock(session.getParms().get("class"));
                    }
                });
                break;

            case "/mockClean":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mocks.clearMocks();
                    }
                });
                break;

            // /mockMethod?class=WebserviceAPI&method=getCountry&args=FRANCE+LONDON&return=USA
            case "/mockMethod":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mocks.setMock(
                                    "get",
                                    session.getParms().get("class"),
                                    session.getParms().get("method"),
                                    session.getParms().containsKey("arg") ? session.getParms().get("arg").split("\\+") : null,
                                    session.getParms().get("result"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

            case "/mockMethodPost":
                //EXAMPLE
//                {
//                    "class" : "com.example.ws.WebserviceAPI",
//                    "method" : "getCountries",
//                    "in" : [
//                              {"class" : "java.lang.String", "value" : "FRANCE"}
//                    ],
//                    "out" : [
//                              {
//                                  "class" : "com.example.ws.Response",
//                                  "value" : {
//                                            "name" : "United States of America",
//                                            "alpha2_code" : "US",
//                                            "alpha3_code" : "USA"
//                                  }
//                              }]
//                }
                try {
                    JSONObject json = new JSONObject((String) session.getQueryParameterString());
                    List<Object> args = convertToGeneric(json, "in");
                    List<Object> results = convertToGeneric(json, "out");

                    MockUtils.setMock("post", json.getString("class"), json.getString("method"), args.toArray(), results);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case "/mockCallBack":
                try {
                    JSONObject json = new JSONObject((String) session.getQueryParameterString());
                    List<Object> args = convertToGeneric(json, "in");
                    List<Object> results = convertToGeneric(json, "out");

                    MockUtils.setMock("callback", json.getString("class"), json.getString("method"), args.toArray(), results);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        String msg = "<html><body><h1>Call succeed</h1>\n";
        msg += "<p>For " + session.getUri() + " !</p>";
        return new Response(msg + "</body></html>\n");
    }
}