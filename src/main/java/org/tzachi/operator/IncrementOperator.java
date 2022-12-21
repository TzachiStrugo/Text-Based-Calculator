package org.tzachi.operator;

import static org.tzachi.operator.Operator.Associativity.UNARY;
import static org.tzachi.operator.Operator.Precedence.INCREMENTAL;

public abstract class IncrementOperator extends Operator implements UnaryEvaluator {

    public static final IncrementOperator INC = new IncrementOperator("++", UNARY, INCREMENTAL) {

        @Override
        public int apply(int op1){
           return  ++op1;
        }
    };

    public static final IncrementOperator DEC = new IncrementOperator("--", UNARY, INCREMENTAL) {

        @Override
        public int apply(int op1){
            return --op1;
        }
    };


    private IncrementOperator(String symbol, Associativity associativity, Precedence precedence) {
        super(symbol, associativity, precedence);
    }
}