package org.tzachi.tokenizer.token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.tzachi.operator.ArithmeticOperator;

import static org.tzachi.tokenizer.token.Token.Type.OPERATOR;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ArithmeticToken extends Token {

    private final ArithmeticOperator operator;

    public ArithmeticToken(ArithmeticOperator operator) {
        super(OPERATOR);
        this.operator = operator;
    }

    public String getValue() {
        return operator.getSymbol();
    }
}
