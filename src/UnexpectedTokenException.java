import java.text.ParseException;

public class UnexpectedTokenException extends ParseException {
    UnexpectedTokenException(Token token, int position) {
        super("unexpected " + token.getType().name() + "-token "
                + token.getValue(), position);
    }
}
