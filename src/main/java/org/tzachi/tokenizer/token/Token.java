package org.tzachi.tokenizer.token;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public abstract class Token {

    private final Type type;

    public abstract String getValue();

    public enum Type {
        OPERATOR,
        NUMBER,
        VARIABLE,
        PARENTHESES,
        ASSIGNMENT,
        INCREMENTAL
    }
}
