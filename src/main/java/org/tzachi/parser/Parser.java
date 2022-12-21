package org.tzachi.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.tzachi.operator.ArithmeticOperator;
import org.tzachi.tokenizer.TokenizerException;
import org.tzachi.tokenizer.token.ArithmeticToken;
import org.tzachi.tokenizer.token.AssignmentToken;
import org.tzachi.tokenizer.token.Token;

import static org.tzachi.operator.AssignmentOperator.ADD_ASSIGN;
import static org.tzachi.operator.AssignmentOperator.MUL_ASSIGN;
import static org.tzachi.operator.AssignmentOperator.SUB_ASSIGN;
import static org.tzachi.operator.Operator.Associativity.BINARY;
import static org.tzachi.operator.Operator.Associativity.UNARY;
import static org.tzachi.tokenizer.token.ParenthesesToken.ParenthesesType;
import static org.tzachi.tokenizer.token.Token.Type.OPERATOR;
import static org.tzachi.tokenizer.token.Token.Type.PARENTHESES;


public class Parser {

    public static List<Token> convertToReversePolishNotationList(List<Token> tokens) {

        final Stack<Token> stack = new Stack<>();
        final List<Token> prefixTokens = new ArrayList<>(tokens.size());

        Token reassignedVar = tokens.get(0);

        for (int i = 1; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            switch (token.getType()) {
                case PARENTHESES -> extracted(stack, prefixTokens, token);
                case NUMBER, VARIABLE, INCREMENTAL -> prefixTokens.add(token);
                case ASSIGNMENT -> handleAssignmentToken((AssignmentToken) token, prefixTokens, reassignedVar, stack);
                case OPERATOR -> handleOperatorToken((ArithmeticToken) token, prefixTokens, stack);
                default -> throw new TokenizerException("Token type '"+ token.getType() +"' not supported ");
            }
        }

        while (!stack.empty()) {
            prefixTokens.add(stack.pop());
        }

        return prefixTokens;
    }

    private static void extracted(Stack<Token> stack, List<Token> prefixTokens, Token token) {
        if (ParenthesesType.OPEN.getSymbol().equals(token.getValue())) {
            stack.push(token);
        } else {
            while (stack.peek().getType() != PARENTHESES) {
                prefixTokens.add(stack.pop());
            }
        }
    }

    private static void handleAssignmentToken(AssignmentToken token,
        List<Token> prefixTokens, Token reassignedVar, Stack<Token> stack) {

        if (token.getOperator() == ADD_ASSIGN) {
            prefixTokens.add(reassignedVar);
            stack.add(new ArithmeticToken(ArithmeticOperator.ADD));
        } else if (token.getOperator() == SUB_ASSIGN) {
            prefixTokens.add(reassignedVar);
            stack.add(new ArithmeticToken(ArithmeticOperator.SUB));
        } else if (token.getOperator() == MUL_ASSIGN) {
            prefixTokens.add(reassignedVar);
            stack.add(new ArithmeticToken(ArithmeticOperator.MUL));
        }
    }

    private static void handleOperatorToken(ArithmeticToken token1,
        List<Token> prefixTokens, Stack<Token> stack) {

        ArithmeticOperator op1 = token1.getOperator();
        while (!stack.empty() && stack.peek().getType() == OPERATOR) {
            ArithmeticToken token2 = (ArithmeticToken) stack.peek();
            ArithmeticOperator op2 = token2.getOperator();
            if (op1.getAssociativity() == UNARY && op2.getAssociativity() == BINARY) {
                break;
            } else if ((op1.getAssociativity() == UNARY && op1.getPrecedence().getValue() <= op2.getPrecedence().getValue())
                || (op1.getPrecedence().getValue() < token2.getOperator().getPrecedence().getValue())) {
                prefixTokens.add(stack.pop());
            } else {
                break;
            }
        }
        stack.push(token1);
    }
}
