package org.tzachi.operator;


import io.vavr.API;
import lombok.Getter;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static org.tzachi.operator.Operator.Associativity.BINARY;
import static org.tzachi.operator.Operator.Precedence.ASSIGNMENT;

@Getter
public abstract class AssignmentOperator extends Operator {

    public static final AssignmentOperator ASSIGN = new AssignmentOperator("=", BINARY, ASSIGNMENT) {
    };

    public static final AssignmentOperator ADD_ASSIGN = new AssignmentOperator("+=", BINARY, ASSIGNMENT) {
    };

    public static final AssignmentOperator MUL_ASSIGN = new AssignmentOperator("*=", BINARY, ASSIGNMENT) {
    };

    public static final AssignmentOperator SUB_ASSIGN = new AssignmentOperator("-=", BINARY, ASSIGNMENT) {
    };

    private AssignmentOperator(String symbol, Associativity associativity, Precedence precedence) {
        super(symbol, associativity, precedence);
    }

    public static AssignmentOperator of(String symbol) {
        return API.Match(symbol).of(
            Case($(ASSIGN.getSymbol()), ASSIGN),
            Case($(ADD_ASSIGN.getSymbol()), ADD_ASSIGN),
            Case($(MUL_ASSIGN.getSymbol()), MUL_ASSIGN),
            Case($(SUB_ASSIGN.getSymbol()), SUB_ASSIGN),
            Case($(), o -> {
                throw new IllegalArgumentException("Failed to ");
            }));
    }
}
