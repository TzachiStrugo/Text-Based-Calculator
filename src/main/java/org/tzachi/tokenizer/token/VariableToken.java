package org.tzachi.tokenizer.token;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import static org.tzachi.tokenizer.token.Token.Type.VARIABLE;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VariableToken extends Token {

    String value;

    public VariableToken(String value) {
        super(VARIABLE);
        this.value = value;
    }
}
