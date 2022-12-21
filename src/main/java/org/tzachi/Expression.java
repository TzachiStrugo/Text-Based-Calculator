package org.tzachi;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.tzachi.operator.IncrementOperator;
import org.tzachi.parser.Parser;
import org.tzachi.tokenizer.Tokenizer;
import org.tzachi.tokenizer.token.ArithmeticToken;
import org.tzachi.tokenizer.token.IncrementalToken;
import org.tzachi.tokenizer.token.IncrementalToken.IncrementalType;
import org.tzachi.tokenizer.token.Token;

import static org.tzachi.tokenizer.token.Token.Type.INCREMENTAL;
import static org.tzachi.tokenizer.token.Token.Type.NUMBER;
import static org.tzachi.tokenizer.token.Token.Type.OPERATOR;
import static org.tzachi.tokenizer.token.Token.Type.VARIABLE;


@EqualsAndHashCode
@ToString
@Getter
final class Expression {

    private final String expression;

    private final Map<String, Integer> expressionVariablesMap;

    private final Map<String, Integer> postIncrementVariablesMap;

    private final Tokenizer tokenizer;
    private String assignedVariable;

    Expression(String expression, Map<String, Integer> expressionVariablesMap,
        Map<String, Integer> postIncrementVariablesMap) {
        this.expression = expression;
        this.tokenizer = new Tokenizer(expression);
        this.expressionVariablesMap = expressionVariablesMap;
        this.postIncrementVariablesMap = postIncrementVariablesMap;
    }

    int evaluate() {
        // parse input expression to infix tokens.
        List<Token> infixTokens = tokenizer.parse();
        assignedVariable = infixTokens.get(0).getValue();

        // convert to RPN & evaluate
        return evaluate(Parser.convertToReversePolishNotationList(infixTokens));
    }

    @SneakyThrows
    private int evaluate(List<Token> postfixTokens) {

        final Stack<Integer> stack = new Stack<>();
        for (Token token : postfixTokens) {

            if (token.getType() == NUMBER) {
                stack.push(Integer.parseInt(token.getValue()));
            }

            if (token.getType() == VARIABLE) {
                Integer value = expressionVariablesMap.get(token.getValue());
                if (value == null) {
                    throw new IllegalArgumentException("Variable '" + token.getValue() + "' not declared");
                }
                stack.push(value);
            }

            if (token.getType() == INCREMENTAL) {

                IncrementalToken incrementalToken = (IncrementalToken) token;
                IncrementOperator incrementOperator = incrementalToken.getIncrementOperator();

                String variable = incrementalToken.getOperand().getValue();
                Integer value = expressionVariablesMap.get(variable);
                if (incrementalToken.getIncrementalType() == IncrementalType.PRE) {
                    if (value == null) {
                        throw new IllegalArgumentException("Variable '" + variable + "' not declared");
                    }

                    value = updateVariableForPreFlow(incrementOperator, variable, value);
                } else {
                    value = updateVariableForPostFlow(incrementOperator, variable, value);
                }
                stack.push(value);
            }

            if (token.getType() == OPERATOR) {
                ArithmeticToken arithmeticToken = (ArithmeticToken) token;
                int numOperand = arithmeticToken.getOperator().getAssociativity().getNumOperand();
                if (stack.size() < arithmeticToken.getOperator().getAssociativity().getNumOperand()) {
                    throw new IllegalArgumentException("Invalid number of operands");
                }

                if (numOperand == 2) {
                    Integer rightOperand = stack.pop();
                    Integer leftOperand = stack.pop();
                    int result = arithmeticToken.getOperator().apply(leftOperand, rightOperand);
                    stack.push(result);
                }
            }
        }

        return stack.pop();
    }

    private Integer updateVariableForPreFlow(IncrementOperator incrementOperator, String variable, Integer value) {
        value = incrementOperator.apply(getPreVariableValueOrCurrent(variable, value));
        expressionVariablesMap.put(variable, value);
        postIncrementVariablesMap.put(variable, expressionVariablesMap.get(variable));
        return value;
    }

    private Integer updateVariableForPostFlow(IncrementOperator incrementOperator, String variable, Integer value) {
        value = getPreVariableValueOrCurrent(variable, value);
        postIncrementVariablesMap.put(variable, incrementOperator.apply(value));
        expressionVariablesMap.put(variable, postIncrementVariablesMap.get(variable));
        return value;
    }

    private Integer getPreVariableValueOrCurrent(String variable, final Integer currentValue) {
        return Optional
            .ofNullable(postIncrementVariablesMap.get(variable))
            .orElseGet(() -> currentValue);
    }
}
