package com.sfeir.testant.tests;

import android.util.Log;

import com.example.Operation;
import com.example.ws.Response;
import com.example.ws.WebserviceAPI;
import com.sfeir.testant.MainActivity;
import com.sfeir.testant.server.MyServer;
import com.sfeir.testant.utils.device.MockUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;

/**
 * Created by patrickvongpraseuth on 01/06/2017.
 */

public class TestMockClass {

    //This test should pass
    public void mockInjectionTest() {


        MockUtils m = MyServer.mocks;

//        try {
//            m.mockMethod("com.example.Operation","addition",null, 6);
//        } catch (Exception e) {
//            Log.e("tes","nul");
//        }

        Operation op = (Operation) m.getMock("com.example.Operation");
        assertEquals(6, op.addition());

//        assertEquals(0, op.substraction());

//        try {
//            m.mockMethod("com.example.Operation","substraction",null, 1);
//        } catch (Exception e) {
//            Log.e("tes","nul");
//        }
        assertEquals(1, op.substraction());



//        Response r = new Response();
//        r.setName("United States of America");
//        List<Response> listresponse = new ArrayList<>();
//        listresponse.add(r);
//
//        String[] args = new String[]{"FRANCE"};
//        try {
//            m.mockMethod("com.example.ws.WebserviceAPI","getCountry",args, listresponse);
//        } catch (Exception e) {
//            Log.e("tes","nulle");
//        }
        WebserviceAPI ws = (WebserviceAPI) m.getMock("com.example.ws.WebserviceAPI");

        assertEquals("United States of America", ws.getCountries("FRANCE").get(0).getName());

    }

}
