package com.thecout.lox;

public class TestScanner {

    public static void main(String[] args) {
        String programm = """
            fun printSum(a,b) {
            print a/b;
                }
            print 25/60;
            """;

        Scanner sc = new Scanner(programm);
        for (Token token :
                sc.scan()) {
            System.out.println(token);
        }

    }


}
