package com.sfeir.testant.server;

import java.util.ArrayList;

/**
 * Created by patrickvongpraseuth on 16/06/2017.
 */

//EXAMPLE JSON INPUT
//                {
//                    "classname" : "com.example.ws.WebserviceAPI",
//                    "method" : "getCountries",
//                    "in" : [
//                              {"class" : "java.lang.String", "value" : "FRANCE"}
//                    ],
//                    "out" :
//                              {
//                                  "classname" : "com.example.ws.Response",
//                                  "value" : "{
//                                            \"name\" : \"United States of America\",
//                                            \"alpha2_code\" : \"US\",
//                                            \"alpha3_code\" : \"USA\"
//                                  }"
//                              }
//                }

public class MyJson {

    private String classname;
    private String method;
    private ArrayList<MyArgument> in;
    private MyArgument out;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public ArrayList<MyArgument> getIn() {
        return in;
    }

    public void setIn(ArrayList<MyArgument> in) {
        this.in = in;
    }

    public MyArgument getOut() {
        return out;
    }

    public void setOut(MyArgument out) {
        this.out = out;
    }

}


