package com.sfeir.testant.utils;


import com.example.callback.APIService;
import com.example.callback.CallResponseMock;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patrickvongpraseuth on 01/06/2017.
 */

public class MockUtils {

    private static Map<String, Object> mocks;

    public MockUtils() {
        mocks = new HashMap<>();
    }

    public static void mockMethod(String name, String method, Object[] args, Object result) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class c = Class.forName(name);
        Constructor constructor = c.getConstructor();
        //Object testsClass = constructor.newInstance();

        Class[] classes = null;
        if (args != null) {
            classes = new Class[args.length];

            for (int i = 0; i < args.length; i++) {
                classes[i] = args[i].getClass();
//                if(args[i] instanceof String){
//                    args[i] = ((String)args[i]).replace("%20"," ");
//                }
            }
        }

        Method lMethod = c.getDeclaredMethod(method, classes);

        Object mock;

        if (mocks.containsKey(name)) {
            mock = mocks.get(name);
        } else {
            mock = Mockito.mock(c);
        }

        try {
            Mockito.when(lMethod.invoke(mock, args)).thenReturn(Integer.parseInt((String) result));
        } catch (Exception e) {
            try {
                Mockito.when(lMethod.invoke(mock, args)).thenReturn(((String) result).replace("%20", " "));
            } catch (Exception e2) {
                Mockito.when(lMethod.invoke(mock, args)).thenReturn(result);
            }
        }

        mocks.put(name, mock);
    }


    public static void mockMethodPost(String name, String method, Object[] args, Object result) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class c = Class.forName(name);
        Constructor constructor = c.getConstructor();
        //Object testsClass = constructor.newInstance();

        Class[] classes = new Class[args.length];
        if (args != null) {
            int i = 0;
            for (Object arg : args) {
                classes[i] = arg.getClass();
                i++;
            }
        }

        Method lMethod = c.getDeclaredMethod(method, classes);

        Object mock;

        if (mocks.containsKey(name)) {
            mock = mocks.get(name);
        } else {
            mock = Mockito.mock(c);
        }

        try {
            Mockito.when(lMethod.invoke(mock, args)).thenReturn(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mocks.put(name, mock);
    }


    public static Object getMock(String classe) {

        if (mocks.containsKey(classe)) {
            return mocks.get(classe);
        }

        Object realObject = null;

        try {

            Class c = Class.forName(classe);
            Constructor constructor = c.getConstructor();
            realObject = constructor.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return realObject;
    }


    public static void clearMocks() {
        mocks.clear();
    }

    public static void removeMock(String classe) {
        mocks.remove(classe);
    }


    public static void mockCallback(String name, String method, Object[] args, final Object result) throws ClassNotFoundException, NoSuchMethodException {


        Class c = Class.forName(name);
//        Constructor constructor = c.getConstructor();
        //Object testsClass = constructor.newInstance();

        Class[] classes = new Class[args.length];
        if (args != null) {
            int i = 0;
            for (Object arg : args) {
                classes[i] = arg.getClass();
                i++;
            }
        }

        Method lMethod = c.getDeclaredMethod(method, classes);

        Object mock;

        if (mocks.containsKey(name)) {
            APIService api = Mockito.mock(APIService.class);
            mock = mocks.get(name);
        } else {
            mock = Mockito.mock(c);
        }

        try {
            Mockito.when(lMethod.invoke(mock, args)).thenAnswer(new Answer<Object>() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {

                    CallResponseMock<Object> test = new CallResponseMock<Object>(result);

                    return test;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        mocks.put(name, mock);


//        APIService api = Mockito.mock(APIService.class);
//
//
//        Mockito.when(api.country("FRANCE")).thenAnswer(new Answer<Object>() {
//            @Override
//            public Object answer(InvocationOnMock invocation) throws Throwable {
//                //get the arguments passed to mock
//                Object[] args = invocation.getArguments();
//                //get the mock
//                Object mock = invocation.getMock();
//                //return the result
//
//                CountryResponse tes = new CountryResponse("INDIA", "IN", "IND");
//
//                CallResponseMock<CountryResponse> test = new CallResponseMock<CountryResponse>(tes);
//
//                return test;
//            }
//        });
//
//        APIHelper.setAPIService(api);
//
//        Call<CountryResponse> test = APIHelper.getAPIService().country("FRANCE");
//
//        test.enqueue(new MyCallBack());
//
//        APIHelper.reinitService();
//
//        test = APIHelper.getAPIService().country("FRANCE");
//
//        test.enqueue(new MyCallBack());
    }
}












