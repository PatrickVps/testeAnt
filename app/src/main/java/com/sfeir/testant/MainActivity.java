package com.sfeir.testant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sfeir.testant.server.MyServer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MyServer server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
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
