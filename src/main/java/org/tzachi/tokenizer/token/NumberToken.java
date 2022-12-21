package org.tzachi.tokenizer.token;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import static org.tzachi.tokenizer.token.Token.Type.NUMBER;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NumberToken extends Token {

    String value;

    public NumberToken(String value) {
        super(NUMBER);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
