package com.thecout.lox;

public class TestScanner {

    public static void main(String[] args) {
        String programm = """
            print and else false for nil or return true var while   } fu
            fun printSum(a,b) {
            print a/b;
            
                if\040
                print 25/60; //adbawiofwgofa
            """;

        Scanner sc = new Scanner(programm);
        for (Token token :
                sc.scan()) {
            System.out.println(token);
        }

    }


}
