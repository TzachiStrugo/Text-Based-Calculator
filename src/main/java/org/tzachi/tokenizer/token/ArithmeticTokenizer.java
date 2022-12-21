package org.tzachi.tokenizer.token;

//import static org.tzachi.operator.Operator.Precedence.ASSIGNMENT;
//import static org.tzachi.operator.Operator.Precedence.UNARY;

/**
 * The arithmetic tokenizer is responsible to break a string into tokens [Number, Operator, Variable].
 */
public final class ArithmeticTokenizer {

    //private static final Set<Operator.Precedence> SEQUENTIAL_OPERATOR_TOKEN_RULES = Set.of(ASSIGNMENT, UNARY);
    //
    //private static final Set<Character> OPEN_PARENTHESES = Set.of( '(', '{' ,'[');
    //
    //private static final Set<Character> CLOSED_PARENTHESES = Set.of( ')', '}' ,']');
    //
    //private final char[] expression;
    //
    //private int pos = 0;
    //
    //private Token lastToken;
    //
    //public ArithmeticTokenizer(String expression) {
    //    this.expression = expression.toCharArray();
    //}
    //
    //public boolean hasNext() {
    //    return expression.length > pos;
    //}
    //
    //public Token nextToken() {
    //    while (Character.isWhitespace(expression[pos])) {
    //        ++pos;
    //    }
    //    return parseToken();
    //}
    //
    //
    //private Token parseToken() {
    //
    //    // try to find variables.
    //    if (Character.isLetter(expression[pos]) || expression[pos] == '_') {
    //        int offset = pos;
    //        while (hasNext() && isVariable(expression[pos])) {
    //            ++pos;
    //        }
    //        String variable = String.valueOf(expression, offset, pos - offset);
    //        if (lastToken != null && lastToken.getType() != OPERATOR) {
    //            throw new TokenizerException("Unable to parse characters '" + variable + "'. Expression not valid.");
    //        } else {
    //            lastToken = new VariableToken(variable);
    //            return lastToken;
    //        }
    //    }
    //
    //    // try to find numbers.
    //    if (Character.isDigit(expression[pos])) {
    //        int offset = pos;
    //        while (hasNext() && isNumber(expression[pos])) {
    //            ++pos;
    //        }
    //        String number = new String(expression, offset, pos - offset);
    //        if (lastToken != null && lastToken.getType() != OPERATOR) {
    //            throw new TokenizerException("Unable to parse characters '" + number + "'. Expression not valid.");
    //        } else {
    //            lastToken = new NumberToken(number);
    //            return lastToken;
    //        }
    //    }
    //
    //    //TODO TS: Add Queue to valid a mismatched parentheses
    //
    //    //// try to find open parentheses operators.
    //    //if (OPEN_PARENTHESES.contains(expression[pos])) {
    //    //    lastToken = new ParenthesesToken(OPEN);
    //    //    return lastToken;
    //    //}
    //
    //    //// try to find closed parentheses operators.
    //    //if (CLOSED_PARENTHESES.contains(expression[pos])) {
    //    //    lastToken = new ParenthesesToken(CLOSED);
    //    //    return lastToken;
    //    //}
    //    //
    //    //
    //    //// try to increment
    //    //if (CLOSED_PARENTHESES.contains(expression[pos])) {
    //    //    lastToken = new ParenthesesToken(CLOSED);
    //    //    return lastToken;
    //    //}
    //
    //    // try to find operators.
    //    // First try look for operator with 2 characters
    //    if (expression.length > pos + 1) {
    //        Optional<Operator> operatorOp = Operator.getBySymbol(String.valueOf(expression, pos, 2));
    //        if (operatorOp.isPresent()) {
    //            Operator operator = operatorOp.get();
    //            if (lastToken != null && isPreviousOperatorTokenAllowIt()) {
    //                throw new TokenizerException("Unable to parse characters '" + operator.getSymbol() + "'. " +
    //                    "Expression not valid.");
    //            }
    //
    //            lastToken = new ArithmeticToken(operator);
    //            pos += 2;
    //            return lastToken;
    //        }
    //    }
    //
    //    // Second try look for operators with 1 characters
    //    ArithmeticToken operator = Operator.getBySymbol(String.valueOf(expression[pos]))
    //        .map(ArithmeticToken::new)
    //        .orElseThrow(() -> new TokenizerException("Unable to parse characters '" + expression[pos] + "'. " +
    //            "Expression not valid"));
    //
    //    if (lastToken != null && isPreviousOperatorTokenAllowIt()) {
    //        throw new TokenizerException("Unable to parse characters '" + operator.getValue() + "'. Expression " +
    //            "not valid.");
    //    }
    //    lastToken = operator;
    //    ++pos;
    //    return lastToken;
    //}
    //
    //private boolean isPreviousOperatorTokenAllowIt() {
    //    if (lastToken.getType() == OPERATOR) {
    //        Operator.Precedence prevPrecedence = ((ArithmeticToken) lastToken).getOperator().getPrecedence();
    //        return !SEQUENTIAL_OPERATOR_TOKEN_RULES.contains(prevPrecedence);
    //    } else {
    //        return false;
    //    }
    //}
    //
    //
    //private static boolean isNumber(char ch) {
    //    return Character.isDigit(ch) || ch == '.';
    //}
    //
    //
    //private static boolean isVariable(char ch) {
    //    return Character.isLetter(ch) ||
    //        ch == '_' ||
    //        Character.isDigit(ch);
    //}
}
