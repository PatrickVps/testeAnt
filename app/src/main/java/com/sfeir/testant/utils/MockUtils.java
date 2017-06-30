package com.sfeir.testant.utils;


import com.example.callback.CallResponseMock;
import com.sfeir.testant.enumeration.MockMethodEnum;

import org.mockito.Mockito;
import org.mockito.exceptions.misusing.WrongTypeOfReturnValue;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrickvongpraseuth on 01/06/2017.
 */

public class MockUtils {

    private static Map<String, Object> mocks;

    public MockUtils() {
        mocks = new HashMap<>();
    }


    public static Object getMock(String classe) throws Exception {

        if (mocks.containsKey(classe)) {
            return mocks.get(classe);
        }

        throw new Exception("No mock for " + classe);
    }

    public static void clearMocks() {
        mocks.clear();
    }

    public static void removeMock(String classe) {
        mocks.remove(classe);
    }


    public static void setMock(MockMethodEnum type, String name, String method, Object argsParam, Object result) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class c = Class.forName(name);
        Class[] classes = new Class[0];

        Object[] args = null;
        if (argsParam != null) {
            if (argsParam instanceof List) {
                args = ((List) argsParam).toArray();
                classes = new Class[args.length];

                int i = 0;
                for (Object arg : args) {
                    classes[i] = arg.getClass();
                    i++;
                }
            } else {
                args = new Object[1];
                args[0] = argsParam;

                classes = new Class[1];
                classes[0] = argsParam.getClass();
            }
        }

        Method lMethod = c.getDeclaredMethod(method, classes);

        Object mock;

        if (mocks.containsKey(name)) {
            mock = mocks.get(name);
        } else {
            //use default method when not mocked
            try {
                mock = Mockito.spy(c.newInstance());
            } catch (InstantiationException e) {
                mock = Mockito.mock(c); // in case we want to instantiate an API Service interface restfull
            }
        }

        if (type == MockMethodEnum.CALLBACK) {
            mockCallBack(lMethod, mock, args, result);
        } else if (type == MockMethodEnum.METHOD) {
            mockMethod(lMethod, mock, args, result);
        }

        mocks.put(name, mock);
    }

    private static void mockMethod(Method lMethod, Object mock, Object[] args, Object result) throws InvocationTargetException, IllegalAccessException {
        try {
            Mockito.when(lMethod.invoke(mock, args)).thenReturn(result); // utilisable que pour spy

        } catch (WrongTypeOfReturnValue e) {
            // exception due to the fact that method only want one object (not a list)
            if (result instanceof ArrayList) {
                try {
                    Mockito.when(lMethod.invoke(mock, args)).thenReturn(((ArrayList) result).get(0));
                } catch (Exception e1) {
                    throw e1;
                }
            }
        } catch (Exception e) {
            throw e;
        }

    }

    private static void mockCallBack(Method lMethod, Object mock, Object[] args, final Object result) throws InvocationTargetException, IllegalAccessException {

        if (result instanceof Answer) {
            Mockito.when(lMethod.invoke(mock, args)).thenAnswer((Answer) result);

        } else {

            Mockito.when(lMethod.invoke(mock, args)).thenAnswer(new Answer<Object>() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {

                    CallResponseMock<Object> call = new CallResponseMock<Object>(result);

                    return call;
                }
            });
        }
    }

}














