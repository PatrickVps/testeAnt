package com.example;

public class Operation {

    private int a = 1;
    private int b = 2;

    public Operation(){
    }

    public int addition(){
        return a + b;
    }

    public int addition(int a, int b){
        return a+b;
    }

    public int substraction(){
        return a-b;
    }

    public int operation1(){
        int a = addition(); //3
        int b = substraction(); //-1

        return a + b; //2
    }
}
