package org.tzachi.tokenizer.token;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.tzachi.operator.IncrementOperator;

import static org.tzachi.tokenizer.token.Token.Type.INCREMENTAL;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IncrementalToken extends Token {

    private static final String INCREMENTAL_REGEX = "(\\w+)";
    //private static final String INCREMENTAL_REGEX = "(\\+\\+|--)|(\\w+)";
    private String value;

    private IncrementalType incrementalType;

    private IncrementOperator incrementOperator;

    VariableToken operand;

    public IncrementalToken(String value, IncrementOperator incrementOperator, IncrementalType incrementalType){
        super(INCREMENTAL);
        this.incrementOperator = incrementOperator;
        this.incrementalType = incrementalType;
        this.value = value;
        Matcher matcher = Pattern.compile(INCREMENTAL_REGEX).matcher(value);
        while (matcher.find()) {
            //String operator1 = matcher.group(1);
            String variable = matcher.group(1);
             if (variable != null) {
                operand = new VariableToken(variable);
            }
        }
    }

    @Override
    public String getValue() {
        return "";
    }

    public enum IncrementalType {
        PRE, POST
    }
}
