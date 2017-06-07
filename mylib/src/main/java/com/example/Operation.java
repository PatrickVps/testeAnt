package com.example;

public class Operation implements IOperation {

    private int a = 1;
    private int b = 2;

    public Operation(){
    }

    @Override
    public int addition(){
        return a + b;
    }

    @Override
    public int addition(int a, int b){
        return a+b;
    }

    @Override
    public int substraction(){
        return a-b;
    }

    @Override
    public int operation1(){
        int a = addition(); //3
        int b = substraction(); //-1

        return a + b; //2
    }
}
