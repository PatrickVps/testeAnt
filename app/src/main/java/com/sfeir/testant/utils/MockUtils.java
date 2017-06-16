package com.sfeir.testant.utils;


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

    public static void setMock(String type, String name, String method, Object[] args, Object result) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class c = Class.forName(name);
        Class[] classes = new Class[0];

        if (args != null) {
            classes = new Class[args.length];

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

        if ("callback".equals(type)) {
            mockCallBack(lMethod, mock, args, result);
        } else if ("get".equals(type)) {
            mockGet(lMethod, mock, args, result);
        } else if ("post".equals(type)) {
            mockPost(lMethod, mock, args, result);
        }

        mocks.put(name, mock);
    }

    private static void mockGet(Method lMethod, Object mock, Object[] args, Object result) throws InvocationTargetException, IllegalAccessException {
        try {
            Mockito.when(lMethod.invoke(mock, args)).thenReturn(Integer.parseInt((String) result));
        } catch (Exception e) {
            try {
                Mockito.when(lMethod.invoke(mock, args)).thenReturn(((String) result).replace("%20", " "));
            } catch (Exception e2) {
                Mockito.when(lMethod.invoke(mock, args)).thenReturn(result);
            }
        }
    }

    private static void mockPost(Method lMethod, Object mock, Object[] args, Object result) {
        try {
            Mockito.when(lMethod.invoke(mock, args)).thenReturn(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void mockCallBack(Method lMethod, Object mock, Object[] args, final Object result) {

        try {
            Mockito.when(lMethod.invoke(mock, args)).thenAnswer(new Answer<Object>() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {

                    CallResponseMock<Object> call = new CallResponseMock<Object>(result);

                    return call;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
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

}














