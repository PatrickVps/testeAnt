package com.sfeir.testant.server;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;

import com.example.ws.Response;
import com.example.ws.WebserviceAPI;
import com.google.gson.Gson;
import com.sfeir.testant.MainActivity;
import com.sfeir.testant.tests.MyTestFramework;
import com.sfeir.testant.utils.device.BootUtils;
import com.sfeir.testant.utils.device.ConnexionUtils;
import com.sfeir.testant.utils.device.InstallUtils;
import com.sfeir.testant.utils.device.MockUtils;
import com.sfeir.testant.utils.device.PermissionsUtils;
import com.sfeir.testant.utils.device.ScreenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

        System.out.println( "\nRunning! Point your browers to http://"+ip+":8080/ \n" );
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

        for (int i = 0 ; i< teste.length(); i++) {
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

        switch (session.getUri()){

            case "/runTests":
                String[] array = {"com.sfeir.testant.tests.TestMockClass"};
                try {
                    MyTestFramework.runTest(array);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "/turnOn":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.turnOnScreen((Activity) context);
                    }
                });
                break;

            case "/turnOff":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.turnOffScreen((Activity) context);
                    }
                });
                break;

            case "/unlock":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.unlockScreen((Activity) context);
                    }
                });
                break;

            case "/lock":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.lockScreen((Activity) context);
                    }
                });
                break;
            // /parameter?name=bluetooth&active=false
            case "/parameter":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PermissionsUtils.setParameter(
                                (Activity) context,
                                session.getParms().get("name"),
                                Boolean.parseBoolean(session.getParms().get("active")));
                    }
                });
                break;

            // /internet?mode=wifi&active=false
            case "/internet":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            ConnexionUtils.handleInternet(
                                    (Activity) context,
                                    session.getParms().get("mode"),
                                    Boolean.parseBoolean(session.getParms().get("active")));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

            case "/reboot":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BootUtils.rebootPhone(
                                    (Activity) context, false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                break;

//            case "/airplane":
//                ((Activity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ConnexionUtils.airplane((Activity) context);
//                    }
//                });
//                break;

            case "/rotate":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ScreenUtils.rotateScreen((Activity) context);
                    }
                });
                break;


            case "/alarms":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BootUtils.getAlarms((Activity) context);
                    }
                });
                break;


            case "/updateApp":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InstallUtils.updateApp((Activity) context);
                    }
                });
                break;


            case "/removeApp":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InstallUtils.uninstallApp((Activity) context);
                    }
                });
                break;

            case "/launchService":
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        InstallUtils.launchService((Activity) context);
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
                                    session.getParms().containsKey("arg")?session.getParms().get("arg").split("\\+"):null,
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

////        JSONObject json = null;
////        try {
////            json = new JSONObject((String)session.getQueryParameterString());
////            JSONObject test = json.getJSONObject("out").getJSONObject("Response");
////            WebserviceAPI.convertToResponse(test.toString());
////
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//
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
                    MockUtils.mockMethodPost(json.getString("class"),json.getString("method"), args.toArray(), results);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

        String msg = "<html><body><h1>Hello server</h1>\n";
        msg += "<p>We serve " + session.getUri() + " !</p>";
        return new Response( msg + "</body></html>\n" );
    }
}