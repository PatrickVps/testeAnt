package com.sfeir.testant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sfeir.testant.server.MyServer;
import com.sfeir.testant.utils.device.MockUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MyServer server;
    private static MockUtils mockUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            server = new MyServer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        /*if(server != null) {
            server.stop();
        }
        */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(server != null) {
            server.stop();
        }
    }
}
