package com.thecout.lox;

public class TestScanner {

    public static void main(String[] args) {
        String programm = """
            for(var i = 0; a < d; i = i+1){
                    a = a+1;
                }
            
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
