package exception;

import parser.Token;

import java.text.ParseException;

public class UnexpectedTokenException extends ParseException {
    public UnexpectedTokenException(Token token, int position) {
        super("unexpected " + token.getType().name() + "-token '"
                + token.getValue() + "'", position);
    }
}
