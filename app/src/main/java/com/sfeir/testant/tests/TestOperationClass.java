package com.sfeir.testant.tests;

import com.example.Operation;
import com.sfeir.testant.server.MyServer;
import com.sfeir.testant.utils.MockUtils;

import static org.junit.Assert.assertEquals;

/**
 * Created by patrickvongpraseuth on 16/06/2017.
 */

public class TestOperationClass {

    public void mockInjectionTest() throws Exception {

//        ////////////////////
//        //MOCK FOR THIS TEST
//        ////////////////////
//        List<PostJson> mockList2 = JsonConverter.convertJsonToObject("[{\"classname\":\"com.example.Operation\",\"method\":\"addition\",\"in\":[],\"out\":[{\"classname\":\"java.lang.Integer\",\"value\":\"6\"}]},{\"classname\":\"com.example.Operation\",\"method\":\"substraction\",\"in\":[],\"out\":[{\"classname\":\"java.lang.Integer\",\"value\":\"1\"}]}]");
//        for (PostJson json : mockList2) {
//            try {
//                List<Object> args = JsonConverter.convertToInstance(json.getIn());
//                List<Object> results = JsonConverter.convertToInstance(json.getOut());
//
//                MockUtils.setMock(MockMethodEnum.METHOD, json.getClassname(), json.getMethod(), args.toArray(), results);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        MockUtils mockList = MyServer.mocks;

        Operation mock = null;
        mock = (Operation) mockList.getMock("com.example.Operation");

        assertEquals(6, mock.addition());

        assertEquals(1, mock.substraction());

    }

}
