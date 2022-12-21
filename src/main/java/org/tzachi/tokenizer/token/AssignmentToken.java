package org.tzachi.tokenizer.token;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.tzachi.operator.AssignmentOperator;

import static org.tzachi.tokenizer.token.Token.Type.ASSIGNMENT;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AssignmentToken extends Token {

    AssignmentOperator operator;

    public AssignmentToken(AssignmentOperator operator) {
        super(ASSIGNMENT);
        this.operator = operator;
    }
    @Override
    public String getValue() {
        return  operator.getSymbol();
    }
}
