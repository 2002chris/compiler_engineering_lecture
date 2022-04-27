package com.thecout.lox;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.thecout.lox.TokenType.EOF;

public class Scanner {


    private final String source;
    private final List<Token> tokens = new ArrayList<>();


    public Scanner(String source) {
        this.source = source;
    }


    public List<Token> scanLine(String line, int lineNumber) {
        List<Token> returnToken = new ArrayList<>();
        Stack<Character> characterStack = new Stack<>();
        for (int i = line.length()-1; i >=0 ; i--) {
            characterStack.push(line.charAt(i));
        }

        while(!characterStack.isEmpty()){
            Stack<Character> readChar = new Stack<>();
            returnToken.add(checkFun(characterStack, lineNumber, readChar));
            returnToken.add(checkIf(characterStack, lineNumber, readChar));
            returnToken.add(checkParenLeft(characterStack, lineNumber, readChar));
            returnToken.add(checkParenRight(characterStack, lineNumber, readChar));
            returnToken.add(checkBraceLeft(characterStack, lineNumber, readChar));
            returnToken.add(checkBraceRight(characterStack, lineNumber, readChar));
            returnToken.add(checkComma(characterStack, lineNumber, readChar));
            returnToken.add(checkPlus(characterStack, lineNumber, readChar));
            returnToken.add(checkSemicolon(characterStack, lineNumber, readChar));
            returnToken.add(checkNumber(characterStack, lineNumber, readChar));
            returnToken.add(checkString(characterStack, lineNumber, readChar));
            returnToken.add(checkIdentifier(characterStack, lineNumber, readChar));
            removeWhitespace(characterStack, readChar);
//            if (!characterStack.isEmpty())
//                characterStack.pop();
        }
        while(returnToken.remove(null));
        return returnToken;
    }

    public List<Token> scan() {
        String[] lines = source.split("\n");
        for (int i = 0; i < lines.length; i++) {
            tokens.addAll(scanLine(lines[i], i));
        }
        tokens.add(new Token(EOF, "", "", lines.length));

        return tokens;
    }

    public Token checkFun(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        readChar.push(characterStack.pop());
        if(readChar.peek() == 'f'){
            if (characterStack.peek() == 'u'){
                readChar.push(characterStack.pop());
                if (characterStack.peek() == 'n'){
                    readChar.push(characterStack.pop());
                    if (characterStack.peek() == ' '){
                        characterStack.pop();
                        readChar.clear();
                       return new Token(TokenType.FUN,"fun", "fun", lineNumber);
                    }
                }
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }
    public Token checkIf(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'i'){
                if (characterStack.peek() == 'f'){
                    readChar.push(characterStack.pop());
                    if (characterStack.peek() == ' '){
                        characterStack.pop();
                        readChar.clear();
                        return new Token(TokenType.IF, "if","if", lineNumber);
                    }
                }
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkParenLeft(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '('){
                readChar.clear();
                return new Token(TokenType.LEFT_PAREN, "(","(", lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }
    public Token checkParenRight(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == ')'){
                readChar.clear();
                return new Token(TokenType.RIGHT_PAREN, ")",")", lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }
    public Token checkBraceLeft(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '{'){
                readChar.clear();
                return new Token(TokenType.LEFT_BRACE, "{","{", lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }
    public Token checkBraceRight(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '}'){
                readChar.clear();
                return new Token(TokenType.RIGHT_BRACE, "}","}", lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkComma(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()){
            readChar.push(characterStack.pop());
            if(readChar.peek() == ','){
                readChar.clear();
                return new Token(TokenType.COMMA, ",", ",", lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkIdentifier(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if(!characterStack.isEmpty()){
            while(!characterStack.isEmpty() && isAlphabetic(characterStack.peek())){
                readChar.push(characterStack.pop());
            }
            if (!readChar.isEmpty()) {
                StringBuilder identifier = new StringBuilder();
                for (char c :
                        readChar) {
                    identifier.append(c);
                }
                readChar.clear();
                return new Token(TokenType.IDENTIFIER, identifier.toString(), identifier.toString(), lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }
    public void removeWhitespace(Stack<Character> characterStack, Stack<Character> readChar){
        if(!characterStack.isEmpty()){
            readChar.push(characterStack.pop());
            if (Character.isWhitespace(readChar.peek())||Character.isSpaceChar(readChar.peek())){
                readChar.clear();
                return;
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
    }

    public Token checkNumber(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        while(!characterStack.isEmpty() && Character.isDigit(characterStack.peek())){
            readChar.push(characterStack.pop());
        }
        StringBuilder number = new StringBuilder();
        if(!readChar.isEmpty() && !(characterStack.peek() == '.')){
            for (char c :
                    readChar) {
                number.append(c);
            }
            readChar.clear();
            return new Token(TokenType.NUMBER, number.toString(), Integer.parseInt(number.toString()), lineNumber);
        }
        if(!readChar.isEmpty() && characterStack.peek() == '.'){
            readChar.push(characterStack.pop());
            while(!characterStack.isEmpty() && Character.isDigit(characterStack.peek())){
                readChar.push(characterStack.pop());
            }
            for (char c :
                    readChar) {
                number.append(c);
            }
            readChar.clear();
            return new Token(TokenType.NUMBER, number.toString(), Double.parseDouble(number.toString()), lineNumber);
        }

        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkPlus(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()){
            readChar.push(characterStack.pop());
            if(readChar.peek() == '+'){
                readChar.clear();
                return new Token(TokenType.PLUS, "+", "+", lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkSemicolon(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()){
            readChar.push(characterStack.pop());
            if(readChar.peek() == ';'){
                readChar.clear();
                return new Token(TokenType.SEMICOLON, ";", ";", lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkString(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()){
            readChar.push(characterStack.pop());
            if (readChar.peek() == '"'){
                while(characterStack.peek() != '"'){
                    readChar.push(characterStack.pop());
                }
                readChar.push(characterStack.pop());
                StringBuilder string = new StringBuilder();
                for (char c :
                        readChar) {
                    string.append(c);
                }
                readChar.clear();
                return new Token(TokenType.STRING, string.toString(), string.substring(1,string.length()-1),lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkMinus(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar){
        if (!characterStack.isEmpty()){
            readChar.push(characterStack.pop());
            if(readChar.peek() == '-'){
                readChar.clear();
                return new Token(TokenType.MINUS, "-", "-", lineNumber);
            }
        }
        while(!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }



    public boolean isAlphabetic(char c){
        return c >= 65 && c <= 90 || c >= 97 && c <= 122;
    }
}
