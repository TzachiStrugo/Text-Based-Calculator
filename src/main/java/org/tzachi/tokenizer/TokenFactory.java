package org.tzachi.tokenizer;

import io.vavr.API;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.tzachi.operator.ArithmeticOperator;
import org.tzachi.operator.AssignmentOperator;
import org.tzachi.tokenizer.token.ArithmeticToken;
import org.tzachi.tokenizer.token.AssignmentToken;
import org.tzachi.tokenizer.token.IncrementalToken;
import org.tzachi.tokenizer.token.NumberToken;
import org.tzachi.tokenizer.token.Token;
import org.tzachi.tokenizer.token.VariableToken;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static org.tzachi.operator.IncrementOperator.DEC;
import static org.tzachi.operator.IncrementOperator.INC;
import static org.tzachi.tokenizer.token.IncrementalToken.IncrementalType.POST;
import static org.tzachi.tokenizer.token.IncrementalToken.IncrementalType.PRE;

public class TokenFactory {

    private static final String VARIABLE_REGEX = "[a-z]+";
    private static final String INCREMENT_REGEX = "\\+\\+";
    private static final String DECREMENT_REGEX = "\\-\\-";

    //TODO TS: Support variable token with '_' and '[0-9]'
    public static Token buildToken(String token) {

        return API.Match(token).of(
            Case($(isMatch(".*(\\+|-|\\*|)=.*")),  () -> new AssignmentToken(AssignmentOperator.of(token))),
            Case($(isMatch("[-+/\\*]")),    () -> new ArithmeticToken(ArithmeticOperator.of(token))),
            Case($(isMatch("[0-9]+")),      () -> new NumberToken(token)),
            Case($(isMatch(VARIABLE_REGEX)),      () -> new VariableToken(token)),
            Case($(isMatch(VARIABLE_REGEX + INCREMENT_REGEX)), () -> new IncrementalToken(token, INC, POST)),
            Case($(isMatch(VARIABLE_REGEX + DECREMENT_REGEX)), () -> new IncrementalToken(token, DEC, POST)),
            Case($(isMatch(INCREMENT_REGEX + VARIABLE_REGEX)), () -> new IncrementalToken(token, INC, PRE)),
            Case($(isMatch(DECREMENT_REGEX + VARIABLE_REGEX)), () -> new IncrementalToken(token, DEC, PRE)),
            Case($(), o -> {
                throw new IllegalArgumentException("Failed to find match for token: '" + token +("'. please verify " +
                    "your expression (Validations section is Limited. )"));
            }));
    }

    private static Predicate<String> isMatch(String regex) {
        return str -> Pattern.matches(regex, str);
    }
}
