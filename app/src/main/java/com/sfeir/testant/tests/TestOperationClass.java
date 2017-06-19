package com.sfeir.testant.tests;

import com.example.Operation;
import com.sfeir.testant.server.MyServer;
import com.sfeir.testant.utils.MockUtils;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;

/**
 * Created by patrickvongpraseuth on 16/06/2017.
 */

public class TestOperationClass {

    public void mockInjectionTest() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        MockUtils mockList = MyServer.mocks;

        Operation mock = (Operation) mockList.getMock("com.example.Operation");
        assertEquals(6, mock.addition());

        assertEquals(1, mock.substraction());
    }

}
