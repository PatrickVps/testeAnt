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


    @Override
    public Response serve(final IHTTPSession session) {

        super.serve(session);

        TestRunner.clearErrors();

        String response = "OK";

        switch (session.getUri()) {

            case "/runTest":
                String[] arrayTmp = {
                        session.getParms().get("testClass"),
                };

                try {
                    TestRunner.runTest(arrayTmp);
                } catch (Exception e) {
                    e.printStackTrace();
                    response = e.toString();
                }
                break;

            case "/runTests":
                String[] array = {
                        "com.sfeir.testant.tests.TestAPIServiceClass",
                        "com.sfeir.testant.tests.TestWebserviceAPIClass",
                        "com.sfeir.testant.tests.TestOperationClass"
                };

                try {
                    TestRunner.runTest(array);
                } catch (Exception e) {
                    e.printStackTrace();
                    response = e.toString();
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
            case "/mockCallBack":

                MockMethodEnum currentEnum = MockMethodEnum.METHOD;

                if ("/mockCallBack".equals(session.getUri())) {
                    currentEnum = MockMethodEnum.CALLBACK;
                }

                List<Object> mockList = new ArrayList<>();

                Object mockConverted = JsonConverter.convertJsonToObject(session.getQueryParameterString(), PostJson.class);

                if (mockConverted != null && mockConverted instanceof List) {
                    mockList = (List<Object>) mockConverted;
                } else {
                    mockList.add(mockConverted);
                }
                for (Object obj : mockList) {
                    PostJson json = (PostJson) obj;
                    try {
                        Object args = JsonConverter.convertToInstance(json.getIn());
                        Object results = JsonConverter.convertToInstance((json.getOut() == null ? json.getListOut() : json.getOut()));

                        MockUtils.setMock(currentEnum, json.getClassname(), json.getMethod(), args, results);

                    } catch (Exception e) {
                        e.printStackTrace();
                        response = e.toString();
                    }
                }
                break;

            default:
                Response r = new Response("<html><body><h1>Cannot execute " + session.getUri() + "</h1></body></html>");
                r.setStatus(Response.Status.METHOD_NOT_ALLOWED);
                return r;
        }

        Response.Status status = Response.Status.OK;

        StringBuilder msg = new StringBuilder();
        msg.append("<html><body><h1>Call succeed</h1>\n");
        msg.append("<p>For " + session.getUri() + " !</p>");

        List<String> errors = TestRunner.getErrors();
        if (!errors.isEmpty()) {
            for (String s : errors) {
                msg.append("<p>" + s + "</p>");
            }
            status = Response.Status.BAD_REQUEST;
        } else {
            msg.append("<p>" + response + "</p>");
        }
        msg.append("</body></html>\n");

        Response responseHTTP = new Response(msg.toString());
        responseHTTP.setStatus(status);

        return responseHTTP;
    }


}