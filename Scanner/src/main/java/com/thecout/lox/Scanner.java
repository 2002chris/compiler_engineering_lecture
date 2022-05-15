package com.thecout.lox;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TooManyListenersException;

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
        for (int i = line.length() - 1; i >= 0; i--) {
            characterStack.push(line.charAt(i));
        }

        while (!characterStack.isEmpty()) {
            Stack<Character> readChar = new Stack<>();

            returnToken.add(checkFun(characterStack, lineNumber, readChar));
            returnToken.add(checkIf(characterStack, lineNumber, readChar));
            returnToken.add(checkPrint(characterStack, lineNumber, readChar));
            returnToken.add(checkAnd(characterStack, lineNumber, readChar));
            returnToken.add(checkElse(characterStack, lineNumber, readChar));
            returnToken.add(checkFalse(characterStack, lineNumber, readChar));
            returnToken.add(checkFor(characterStack, lineNumber, readChar));
            returnToken.add(checkNil(characterStack, lineNumber, readChar));
            returnToken.add(checkOr(characterStack, lineNumber, readChar));
            returnToken.add(checkReturn(characterStack, lineNumber, readChar));
            returnToken.add(checkTrue(characterStack, lineNumber, readChar));
            returnToken.add(checkVar(characterStack, lineNumber, readChar));
            returnToken.add(checkWhile(characterStack, lineNumber, readChar));

            returnToken.add(checkParenLeft(characterStack, lineNumber, readChar));
            returnToken.add(checkParenRight(characterStack, lineNumber, readChar));
            returnToken.add(checkBraceLeft(characterStack, lineNumber, readChar));
            returnToken.add(checkBraceRight(characterStack, lineNumber, readChar));
            returnToken.add(checkComma(characterStack, lineNumber, readChar));
            returnToken.add(checkSemicolon(characterStack, lineNumber, readChar));
            returnToken.add(checkDot(characterStack, lineNumber, readChar));

            returnToken.add(checkPlus(characterStack, lineNumber, readChar));
            returnToken.add(checkMinus(characterStack, lineNumber, readChar));
            returnToken.add(checkStar(characterStack, lineNumber, readChar));
            returnToken.add(checkSlash(characterStack, lineNumber, readChar));
            returnToken.add(checkBang(characterStack, lineNumber, readChar));
            returnToken.add(checkBangEq(characterStack, lineNumber, readChar));
            returnToken.add(checkEq(characterStack, lineNumber, readChar));
            returnToken.add(checkEqEq(characterStack, lineNumber, readChar));
            returnToken.add(checkGr(characterStack, lineNumber, readChar));
            returnToken.add(checkGrEq(characterStack, lineNumber, readChar));
            returnToken.add(checkLe(characterStack, lineNumber, readChar));
            returnToken.add(checkLeEq(characterStack, lineNumber, readChar));

            returnToken.add(checkNumber(characterStack, lineNumber, readChar));
            returnToken.add(checkString(characterStack, lineNumber, readChar));
            returnToken.add(checkIdentifier(characterStack, lineNumber, readChar));
            returnToken.add(checkComment(characterStack, lineNumber, readChar));
            removeWhitespace(characterStack, readChar);
//            if (!characterStack.isEmpty())
//                characterStack.pop();
        }
        while (returnToken.remove(null)) ;
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

    public Token checkFun(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'f' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'u' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 'n' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == ' ') {
                            readChar.clear();
                            return new Token(TokenType.FUN, "fun", "fun", lineNumber);
                        }
                    }
                }
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkIf(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'i' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'f' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == ' ') {
                        readChar.clear();
                        return new Token(TokenType.IF, "if", "if", lineNumber);
                    }
                }
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkPrint(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'p' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'r' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 'i' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == 'n' && !characterStack.isEmpty()) {
                            readChar.push(characterStack.pop());
                            if (readChar.peek() == 't' && !characterStack.isEmpty()) {
                                readChar.push(characterStack.pop());
                                if (readChar.peek() == ' ') {
                                    readChar.clear();
                                    return new Token(TokenType.PRINT, "print", "print", lineNumber);

                                }
                            }
                        }
                    }
                }
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkParenLeft(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '(') {
                readChar.clear();
                return new Token(TokenType.LEFT_PAREN, "(", "(", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkParenRight(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == ')') {
                readChar.clear();
                return new Token(TokenType.RIGHT_PAREN, ")", ")", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkBraceLeft(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '{') {
                readChar.clear();
                return new Token(TokenType.LEFT_BRACE, "{", "{", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkBraceRight(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '}') {
                readChar.clear();
                return new Token(TokenType.RIGHT_BRACE, "}", "}", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkComma(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == ',') {
                readChar.clear();
                return new Token(TokenType.COMMA, ",", ",", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkIdentifier(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            while (!characterStack.isEmpty() && isAlphabetic(characterStack.peek())) {
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
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public void removeWhitespace(Stack<Character> characterStack, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (Character.isWhitespace(readChar.peek()) || Character.isSpaceChar(readChar.peek())) {
                readChar.clear();
                return;
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
    }

    public Token checkNumber(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        while (!characterStack.isEmpty() && Character.isDigit(characterStack.peek())) {
            readChar.push(characterStack.pop());
        }
        StringBuilder number = new StringBuilder();
        if (!readChar.isEmpty() && !(characterStack.peek() == '.')) {
            for (char c :
                    readChar) {
                number.append(c);
            }
            readChar.clear();
            return new Token(TokenType.NUMBER, number.toString(), Integer.parseInt(number.toString()), lineNumber);
        }
        if (!readChar.isEmpty() && characterStack.peek() == '.') {
            readChar.push(characterStack.pop());
            while (!characterStack.isEmpty() && Character.isDigit(characterStack.peek())) {
                readChar.push(characterStack.pop());
            }
            for (char c :
                    readChar) {
                number.append(c);
            }
            readChar.clear();
            return new Token(TokenType.NUMBER, number.toString(), Double.parseDouble(number.toString()), lineNumber);
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkPlus(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '+') {
                readChar.clear();
                return new Token(TokenType.PLUS, "+", "+", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkSemicolon(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == ';') {
                readChar.clear();
                return new Token(TokenType.SEMICOLON, ";", ";", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkString(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '"') {
                while (characterStack.peek() != '"') {
                    readChar.push(characterStack.pop());
                }
                readChar.push(characterStack.pop());
                StringBuilder string = new StringBuilder();
                for (char c :
                        readChar) {
                    string.append(c);
                }
                readChar.clear();
                return new Token(TokenType.STRING, string.toString(), string.substring(1, string.length() - 1), lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkMinus(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '-') {
                readChar.clear();
                return new Token(TokenType.MINUS, "-", "-", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkStar(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '*') {
                readChar.clear();
                return new Token(TokenType.STAR, "*", "*", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkDot(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '.') {
                readChar.clear();
                return new Token(TokenType.DOT, ".", ".", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkSlash(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '/' && (characterStack.isEmpty() || characterStack.peek() != '/')) {
                readChar.clear();
                return new Token(TokenType.SLASH, "/", "/", lineNumber);
            }
        }
        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkComment(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '/') {
                readChar.push(characterStack.pop());
                if (readChar.peek() == '/') {
                    while (!characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                    }
                    StringBuilder comment = new StringBuilder();
                    for (char c :
                            readChar) {
                        comment.append(c);
                    }
                    readChar.clear();
                    return new Token(TokenType.COMMENT, comment.toString(), comment.substring(2), lineNumber);
                }
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkBang(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '!' && (characterStack.isEmpty() || characterStack.peek() != '=')) {
                readChar.clear();
                return new Token(TokenType.BANG, "!", "!", lineNumber);
            }
        }


        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkBangEq(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '!') {
                readChar.push(characterStack.pop());
                if (readChar.peek() == '=') {
                    readChar.clear();
                    return new Token(TokenType.BANG_EQUAL, "!=", "!=", lineNumber);
                }
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkEq(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '=' && (characterStack.isEmpty() || characterStack.peek() != '=')) {
                readChar.clear();
                return new Token(TokenType.EQUAL, "=", "=", lineNumber);
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkEqEq(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '=' && (!characterStack.isEmpty() && characterStack.peek() == '=')) {
                characterStack.pop();
                readChar.clear();
                return new Token(TokenType.EQUAL_EQUAL, "==", "==", lineNumber);
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkGr(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '>' && (characterStack.isEmpty() || characterStack.peek() != '=')) {
                readChar.clear();
                return new Token(TokenType.GREATER, ">", ">", lineNumber);
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkGrEq(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '>' && (!characterStack.isEmpty() && characterStack.peek() == '=')) {
                characterStack.pop();
                readChar.clear();
                return new Token(TokenType.GREATER_EQUAL, ">=", ">=", lineNumber);
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkLe(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '<' && (characterStack.isEmpty() || characterStack.peek() != '=')) {
                readChar.clear();
                return new Token(TokenType.LESS, "<", "<", lineNumber);
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkLeEq(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == '<' && (!characterStack.isEmpty() && characterStack.peek() == '=')) {
                characterStack.pop();
                readChar.clear();
                return new Token(TokenType.LESS_EQUAL, "<=", "<=", lineNumber);
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkAnd(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'a' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'n' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 'd' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == ' ') {
                            readChar.clear();
                            return new Token(TokenType.AND, "and", "and", lineNumber);
                        }
                    }
                }
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkElse(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'e' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'l' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 's' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == 'e' && !characterStack.isEmpty()) {
                            readChar.push(characterStack.pop());
                            if (readChar.peek() == ' ') {
                                readChar.clear();
                                return new Token(TokenType.ELSE, "else", "else", lineNumber);
                            }
                        }
                    }
                }
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkFalse(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'f' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'a' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 'l' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == 's' && !characterStack.isEmpty()) {
                            readChar.push(characterStack.pop());
                            if (readChar.peek() == 'e' && !characterStack.isEmpty()) {
                                readChar.push(characterStack.pop());
                                if (readChar.peek() == ' ') {
                                    readChar.clear();
                                    return new Token(TokenType.FALSE, "false", "false", lineNumber);
                                }
                            }

                        }
                    }
                }
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkFor(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'f' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'o' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 'r' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == ' ') {
                            readChar.clear();
                            return new Token(TokenType.FOR, "for", "for", lineNumber);
                        }
                    }
                }
            }
        }


        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkNil(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'n' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'i' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 'l' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == ' ') {
                            readChar.clear();
                            return new Token(TokenType.NIL, "nil", "nil", lineNumber);
                        }
                    }
                }
            }
        }


        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkOr(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'o' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'r' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == ' ') {
                        readChar.clear();
                        return new Token(TokenType.OR, "or", "or", lineNumber);
                    }

                }
            }
        }


        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkReturn(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'r' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'e' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 't' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == 'u' && !characterStack.isEmpty()) {
                            readChar.push(characterStack.pop());
                            if (readChar.peek() == 'r' && !characterStack.isEmpty()) {
                                readChar.push(characterStack.pop());
                                if (readChar.peek() == 'n' && !characterStack.isEmpty()) {
                                    readChar.push(characterStack.pop());
                                    if (readChar.peek() == ' ' && !characterStack.isEmpty()) {
                                        readChar.clear();
                                        return new Token(TokenType.RETURN, "return", "return", lineNumber);
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }


        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkTrue(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 't' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'r' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 'u' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == 'e' && !characterStack.isEmpty()) {
                            readChar.push(characterStack.pop());
                            if (readChar.peek() == ' ') {
                                readChar.clear();
                                return new Token(TokenType.TRUE, "true", "true", lineNumber);
                            }
                        }
                    }
                }
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkVar(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'v' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'a' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 'r' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == ' ') {
                            readChar.clear();
                            return new Token(TokenType.VAR, "var", "var", lineNumber);
                        }
                    }
                }
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public Token checkWhile(Stack<Character> characterStack, int lineNumber, Stack<Character> readChar) {
        if (!characterStack.isEmpty()) {
            readChar.push(characterStack.pop());
            if (readChar.peek() == 'w' && !characterStack.isEmpty()) {
                readChar.push(characterStack.pop());
                if (readChar.peek() == 'h' && !characterStack.isEmpty()) {
                    readChar.push(characterStack.pop());
                    if (readChar.peek() == 'i' && !characterStack.isEmpty()) {
                        readChar.push(characterStack.pop());
                        if (readChar.peek() == 'l' && !characterStack.isEmpty()) {
                            readChar.push(characterStack.pop());
                            if (readChar.peek() == 'e' && !characterStack.isEmpty()) {
                                readChar.push(characterStack.pop());
                                if (readChar.peek() == ' ') {
                                    readChar.clear();
                                    return new Token(TokenType.WHILE, "while", "while", lineNumber);
                                }
                            }

                        }
                    }
                }
            }
        }

        while (!readChar.empty())
            characterStack.push(readChar.pop());
        return null;
    }

    public boolean isAlphabetic(char c) {
        return c >= 65 && c <= 90 || c >= 97 && c <= 122;
    }
}
