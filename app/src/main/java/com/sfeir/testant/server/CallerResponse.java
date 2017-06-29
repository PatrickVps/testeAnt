package com.sfeir.testant.server;

import android.util.Log;

import com.sfeir.testant.utils.JsonConverter;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by patrickvongpraseuth on 28/06/2017.
 */

public class CallerResponse implements Answer {

    private String urlResponse;

    public CallerResponse(String url) {
        urlResponse = url;
    }

    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {

        try {
            URL url = new URL(urlResponse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                InputStream in = conn.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);

                StringBuilder sb = new StringBuilder();

                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    System.out.print(current);
                    sb.append(current);
                }

                List<MyArgument> results = JsonConverter.convertJsonToObject2(sb.toString());

                // Do normal input or output stream reading
                Log.e("tes", "ok");

                List<Object> test = JsonConverter.convertToInstance(results);

                return test.get(0);
            } else {
                Log.e("tes", "ko");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
