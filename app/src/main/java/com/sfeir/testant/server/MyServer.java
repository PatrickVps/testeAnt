package com.sfeir.testant.server;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import com.sfeir.testant.enumeration.MockMethodEnum;
import com.sfeir.testant.utils.JsonConverter;
import com.sfeir.testant.utils.MockUtils;
import com.sfeir.testant.utils.TestRunner;

import org.json.JSONObject;

import java.io.IOException;
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


    @Override
    public Response serve(final IHTTPSession session) {

        super.serve(session);

        switch (session.getUri()) {

            case "/runTests":
                String[] array = {
//                        "com.sfeir.testant.tests.TestAPIServiceClass",
                        "com.sfeir.testant.tests.TestWebserviceAPIClass",
                        "com.sfeir.testant.tests.TestOperationClass"
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

            case "/mockMethod":
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
                    List<Object> args = JsonConverter.convertToGeneric(json, "in");
                    List<Object> results = JsonConverter.convertToGeneric(json, "out");

                    MockUtils.setMock(MockMethodEnum.METHOD, json.getString("class"), json.getString("method"), args.toArray(), results);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case "/mockCallBack":
                try {
                    JSONObject json = new JSONObject((String) session.getQueryParameterString());
                    List<Object> args = JsonConverter.convertToGeneric(json, "in");
                    List<Object> results = JsonConverter.convertToGeneric(json, "out");

                    MockUtils.setMock(MockMethodEnum.CALLBACK, json.getString("class"), json.getString("method"), args.toArray(), results);

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