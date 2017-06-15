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

        System.out.println("\nRunning! Point your browers to http://" + ip + ":8080/ \n");
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

        JSONArray teste = json.getJSONArray(in);

        Object te;
        Object modele = null;
        List<Object> result = new ArrayList<>();

        for (int i = 0; i < teste.length(); i++) {
            te = teste.get(i);

            try {
                String value = ((JSONObject) te).get("value").toString();
                String classe = ((JSONObject) te).getString("class");
                modele = new Gson().fromJson(value, Class.forName(classe));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            result.add(modele);
        }
        return result;
    }


    @Override
    public Response serve(final IHTTPSession session) {

        super.serve(session);

        switch (session.getUri()) {

            case "/runTests":
                String[] array = {"com.sfeir.testant.tests.TestMockClass"};
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
                            mocks.mockMethod(
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
                JSONObject json = null;
                List<Object> args = null;
                List<Object> results = null;
                try {
                    json = new JSONObject((String) session.getQueryParameterString());
                    args = convertToGeneric(json, "in");
                    results = convertToGeneric(json, "out");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    MockUtils.mockMethodPost(json.getString("class"), json.getString("method"), args.toArray(), results);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case "/mockCallBack":

                json = null;
                args = null;
                results = null;
                try {
                    json = new JSONObject((String) session.getQueryParameterString());
                    args = convertToGeneric(json, "in");
                    results = convertToGeneric(json, "out");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    MockUtils.mockCallback(json.getString("class"), json.getString("method"), args.toArray(), results);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        String msg = "<html><body><h1>Hello server</h1>\n";
        msg += "<p>We serve " + session.getUri() + " !</p>";
        return new Response(msg + "</body></html>\n");
    }
}