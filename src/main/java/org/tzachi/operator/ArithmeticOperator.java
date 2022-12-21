package org.tzachi.operator;

import io.vavr.API;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static org.tzachi.operator.Operator.Associativity.BINARY;
import static org.tzachi.operator.Operator.Precedence.ADDING;
import static org.tzachi.operator.Operator.Precedence.MULTIPLICATION;


public abstract class ArithmeticOperator extends Operator implements BinaryEvaluator {

    public static final ArithmeticOperator ADD = new ArithmeticOperator("+", BINARY, ADDING) {

        @Override
        public int apply(int op1, int op2) {
            return op1 + op2;
        }
    };


    public static final ArithmeticOperator SUB = new ArithmeticOperator("-", BINARY, ADDING) {

        @Override
        public int apply(int op1, int op2) {
            return op1 - op2;
        }
    };


    public static final ArithmeticOperator MUL = new ArithmeticOperator("*", BINARY, MULTIPLICATION) {

        @Override
        public int apply(int op1, int op2) {
            return op1 * op2;
        }
    };

    public static final ArithmeticOperator DIV = new ArithmeticOperator("/", BINARY, MULTIPLICATION) {

        @Override
        public int apply(int op1, int op2) {
            return op1 / op2;
        }
    };

    private ArithmeticOperator(String symbol, Associativity associativity, Precedence precedence) {
        super(symbol, associativity, precedence);
    }

    public static ArithmeticOperator of(String symbol) {
        return API.Match(symbol).of(
            Case($(ADD.getSymbol()), ADD),
            Case($(SUB.getSymbol()), SUB),
            Case($(MUL.getSymbol()), MUL),
            Case($(DIV.getSymbol()), DIV),
            Case($(), o -> {
                throw new IllegalArgumentException("Failed to build token ");
            }));
    }
}
