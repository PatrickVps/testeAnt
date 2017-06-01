package com.sfeir.testant.tests;

import static com.sfeir.testant.tests.MyTestFramework.checkEqual;
import static com.sfeir.testant.tests.MyTestFramework.checkTrue;

/**
 * Created by patrickvongpraseuth on 31/05/2017.
 */

public class TestsClass {
    //This test should fail because it raises an exception
    public void divisionByZeroTest() {
        int var1 = 1;
        int var2 = 2;

        int res = 2/0;

        checkTrue(var2 > var1);
    }

    //This test should pass
    public void anotherTest() {
        int var1 = 1;
        int var2 = 2;

        checkTrue(var2 > var1);
    }

    //This test should fail
    public void anotherTest2() {
        int var1 = 1;
        int var2 = 2;

        checkTrue(var1 > var2);
    }

    //This test should pass
    public void equalityTest() {
        checkEqual(2, 2);
    }

    //This test should fail
    public void inequalityTest() {
        checkEqual(1, 2);
    }
}