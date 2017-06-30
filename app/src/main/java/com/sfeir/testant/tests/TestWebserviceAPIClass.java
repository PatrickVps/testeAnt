package com.sfeir.testant.tests;

import com.example.ws.WebserviceAPI;
import com.sfeir.testant.server.MyServer;
import com.sfeir.testant.utils.MockUtils;

import static org.junit.Assert.assertEquals;

/**
 * Created by patrickvongpraseuth on 16/06/2017.
 */

public class TestWebserviceAPIClass {


    public void mockInjectionTest() throws Exception {

//        ////////////////////
//        //MOCK FOR THIS TEST
//        ////////////////////
//        List<PostJson> mockList2 = JsonConverter.convertJsonToObject("{\"classname\":\"com.example.ws.WebserviceAPI\",\"method\":\"getCountries\",\"in\":[{\"classname\":\"java.lang.String\",\"value\":\"FRANCE\"}],\"out\":[{\"classname\":\"com.example.ws.Response\",\"value\":\"{\\\"name\\\": \\\"United States of America\\\", \\\"alpha2_code\\\":\\\"US\\\",\\\"alpha3_code\\\":\\\"USA\\\"}\"}]}");
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

        WebserviceAPI mock = (WebserviceAPI) mockList.getMock("com.example.ws.WebserviceAPI");

        assertEquals("United States of America", mock.getCountries("FRANCE").get(0).getName());
    }

}
