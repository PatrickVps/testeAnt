package com.sfeir.testant.server;

import java.util.ArrayList;
import java.util.List;

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

public class PostJson {

    private String classname;
    private String method;
    private ArrayList<PostObject> in;
    private PostObject out;
    private List<PostObject> listOut;

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

    public ArrayList<PostObject> getIn() {
        return in;
    }

    public void setIn(ArrayList<PostObject> in) {
        this.in = in;
    }

    public PostObject getOut() {
        return out;
    }

    public void setOut(PostObject out) {
        this.out = out;
    }

    public List<PostObject> getListOut() {
        return listOut;
    }

    public void setListOut(List<PostObject> listOut) {
        this.listOut = listOut;
    }
}


