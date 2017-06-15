package com.example.callback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickvongpraseuth on 15/06/2017.
 */

public class CountryResponse {

    @SerializedName("RestResponse")
    @Expose
    RestResponse restResponse;

    public CountryResponse(String name, String alph2, String alph3) {
        restResponse = new RestResponse();
        restResponse.setMessages(new ArrayList<String>());

        restResponse.addResult(name, alph2, alph3);
    }

    public RestResponse getRestResponse() {
        return restResponse;
    }

    public void setRestResponse(RestResponse restResponse) {
        this.restResponse = restResponse;
    }

    public class RestResponse {
        @SerializedName("messages")
        @Expose
        List<String> messages;

        @SerializedName("result")
        @Expose
        List<Result> result;

        public RestResponse() {
            result = new ArrayList<>();
        }

        public void addResult(String name, String alph2, String alph3) {
            result.add(new Result(name, alph2, alph3));
        }

        public class Result {
            @SerializedName("name")
            @Expose
            String name;
            @SerializedName("alpha2_code")
            @Expose
            String alph2;
            @SerializedName("alpha3_code")
            @Expose
            String alph3;

            public Result(String name, String alph2, String alph3) {
                this.name = name;
                this.alph2 = alph2;
                this.alph3 = alph3;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAlph2() {
                return alph2;
            }

            public void setAlph2(String alph2) {
                this.alph2 = alph2;
            }

            public String getAlph3() {
                return alph3;
            }

            public void setAlph3(String alph3) {
                this.alph3 = alph3;
            }
        }

        public List<String> getMessages() {
            return messages;
        }

        public void setMessages(List<String> messages) {
            this.messages = messages;
        }

        public List<Result> getResult() {
            return result;
        }

        public void setResult(List<Result> result) {
            this.result = result;
        }
    }
}
