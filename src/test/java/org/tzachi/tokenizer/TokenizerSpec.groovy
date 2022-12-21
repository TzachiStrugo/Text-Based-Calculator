package org.tzachi.tokenizer

import org.tzachi.operator.ArithmeticOperator
import org.tzachi.operator.AssignmentOperator
import org.tzachi.tokenizer.token.*
import spock.lang.Specification

import static java.util.Arrays.asList
import static org.tzachi.operator.ArithmeticOperator.ADD
import static org.tzachi.operator.ArithmeticOperator.MUL
import static org.tzachi.operator.AssignmentOperator.ADD_ASSIGN


class TokenizerSpec extends Specification {

    def "Tokenizer should return list of tokens for each arithmetic expression"() {

        given:
        def tokenizer = new Tokenizer(expression)

        expect:
        tokenizer.parse() == tokens

        where:
        expression       || tokens
        "i = 0"          || asList(varToken("i"), assignToken(ASSIGN), numToken("0"))
        "j = ++i"        || asList(varToken("j"), assignToken(ASSIGN), incToken("++i", IncrementalToken.IncrementalType.PRE))
        "j = i++ + 5"    || asList(varToken("j"), assignToken(ASSIGN), incToken("++i", IncrementalToken.IncrementalType.POST), opToken(ADD) , numToken("5"))
        "y = 5 + 3 * 10" || asList(varToken("y"), assignToken(ASSIGN), numToken("5"), opToken(ADD)
                , numToken("3"), opToken(MUL), numToken("10"))
        "i += y"         || asList(varToken("i"), assignToken(ADD_ASSIGN), varToken("y"))
    }

    private ArithmeticToken opToken(ArithmeticOperator operator) {
        new ArithmeticToken(operator)
    }

    private AssignmentToken assignToken(AssignmentOperator operator) {
        new AssignmentToken(operator)
    }

    private NumberToken numToken(String str) {
        new NumberToken(str)
    }

    private VariableToken varToken(String str) {
        new VariableToken(str)
    }

    private IncrementalToken incToken(String str, IncrementalToken.IncrementalType type) {
        new IncrementalToken(str, type)
    }
}
