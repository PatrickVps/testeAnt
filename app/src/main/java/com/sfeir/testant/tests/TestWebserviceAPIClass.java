package com.sfeir.testant.tests;

import com.example.ws.WebserviceAPI;
import com.sfeir.testant.server.MyServer;
import com.sfeir.testant.utils.MockUtils;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

/**
 * Created by patrickvongpraseuth on 16/06/2017.
 */

public class TestWebserviceAPIClass {


    public void mockInjectionTest() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        MockUtils mockList = MyServer.mocks;

        WebserviceAPI mock = (WebserviceAPI) mockList.getMock("com.example.ws.WebserviceAPI");

        assertEquals("United States of America", mock.getCountries("FRANCE").get(0).getName());
    }

}
