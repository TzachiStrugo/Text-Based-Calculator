package org.tzachi.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public abstract class Operator {

    private String symbol;
    private Associativity associativity;
    private Precedence precedence;

    @Getter
    @RequiredArgsConstructor
    public enum Associativity {
        BINARY(2),
        UNARY(1);

        @Getter
        private final int numOperand;

    }

    @Getter
    @RequiredArgsConstructor
    public enum Precedence {
        ASSIGNMENT(10),
        ADDING(20),
        MULTIPLICATION(30),
        INCREMENTAL(30);

        private final int value;

    }
}
