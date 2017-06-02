package com.sfeir.testant.tests;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.Operation;
import com.example.ws.Response;
import com.example.ws.WebserviceAPI;
import com.sfeir.testant.utils.device.MockUtils;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;

import java.util.List;

import static com.sfeir.testant.tests.MyTestFramework.checkEqual;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;

/**
 * Created by patrickvongpraseuth on 01/06/2017.
 */

public class Test2Class {

    //This test should pass
    public void mockInjectionTest() {

        Operation op = new Operation();
        assertEquals(3, op.addition());


        MockUtils.checkMockable(op.addition());


        /////////////////
        /////////////////
        //MOCK METHOD RETURN
        Operation mock = Mockito.mock(Operation.class);
        Mockito.when(mock.addition()).thenReturn(4);
        assertEquals(4, mock.addition());



    }

}
