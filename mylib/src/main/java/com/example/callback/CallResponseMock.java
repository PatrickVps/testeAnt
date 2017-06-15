package com.example.callback;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patrickvongpraseuth on 15/06/2017.
 */

public class CallResponseMock<T> implements Call {

    private T mockObject;

    public CallResponseMock(T o) {
        mockObject = o;
    }

    @Override
    public Response execute() throws IOException {
        return null;
    }

    @Override
    public void enqueue(Callback callback) {

        Response aResponse = Response.success(mockObject);

        callback.onResponse(null, aResponse);
    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call clone() {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }
}
