package com.sfeir.testant.utils;


/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    private static List<String> errors = new ArrayList<String>();

    public static void runTest(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        for (String arg : args) {

            errors.clear();

            Class c = Class.forName(arg);
            Constructor constructor = c.getConstructor();
            Object testsClass = constructor.newInstance();
            Method[] m = c.getDeclaredMethods();

            for (Method test : m) {
                try {
                    test.invoke(testsClass);
                } catch (Exception e) {
                    if (test != null && e != null && e.getCause() != null && e.getCause().getStackTrace() != null) {
                        fail(test.getName() + " : \n" + e.getCause().getMessage() + "\n" + printArray(e.getCause().getStackTrace()));
                    }
                }
            }
            System.out.println("-----");

            if (errors.size() > 0) {
                System.err.println(errors.size() + " test(s) in error on " + (m.length - 1) + " tests run\n");

                for (String failingTest : errors) {
                    System.err.println(failingTest);
                }
            } else {
                System.out.println("All the tests pass.");
            }
        }
    }

    private static void fail(String message) {
        errors.add(message);
    }

    private static String printArray(StackTraceElement[] stackTraceElement) {
        String result = "";

        for (StackTraceElement line : stackTraceElement) {
            result += line + "\n";
        }

        return result;
    }

    public static List<String> getErrors() {
        return errors;
    }
}
