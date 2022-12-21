package org.tzachi.tokenizer.token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

import static org.tzachi.tokenizer.token.Token.Type.PARENTHESES;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ParenthesesToken extends Token {

    ParenthesesType parenthesesType;

    ParenthesesToken(ParenthesesType type) {
        super(PARENTHESES);
        this.parenthesesType = type;
    }

    @Override
    public String getValue() {
        return parenthesesType.symbol;
    }

    @Getter
    @RequiredArgsConstructor
    public enum ParenthesesType {
        OPEN ("("),
        CLOSED(")");

        private final String symbol;
    }
}
