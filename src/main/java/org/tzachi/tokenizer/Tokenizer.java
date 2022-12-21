package org.tzachi.tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.tzachi.tokenizer.token.Token;

public class Tokenizer {

    private final String expression;
    private final Scanner scanner;

    public Tokenizer(String expression) {
        this.expression = expression;
        this.scanner = new Scanner(expression);
    }

    public List<Token> parse() {

        List<Token> tokens = new ArrayList<>();
        while (scanner.hasNext()) {
            tokens.add(TokenFactory.buildToken(scanner.next()));
        }
        return tokens;
    }
}
