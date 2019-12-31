import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class Parser {
    private LexicalAnalyzer lex;

    private Token consume(Token.TokenType expectedType) throws ParseException {
        Token token = lex.curToken();
        if (token.getType() != expectedType) {
            throw new ParseException("expected '" + expectedType.name() + "', but found '"
                    + token.getType().name() + "'", lex.curPos);
        }
        lex.nextToken();
        return token;
    }

    private Tree S() throws ParseException {
        Token curToken = lex.curToken();
        switch (curToken.getType()) {
            case FUNC:
                lex.nextToken();
                Tree f = F();
                Tree t = T();
                consume(Token.TokenType.SEM);
                consume(Token.TokenType.END);
                return new Tree("S", new Tree("function"), f, t, new Tree(";"));
            case PROC:
                lex.nextToken();
                Tree f2 = F();
                consume(Token.TokenType.SEM);
                consume(Token.TokenType.END);
                return new Tree("S", new Tree("procedure"), f2, new Tree(";"));
            default:
                throw new UnexpectedTokenException(curToken, lex.curPos());
        }
    }

    private Tree T() throws ParseException {
        Token curToken = lex.curToken();
        if (curToken.getType() == Token.TokenType.TYPE_SEP) {
            lex.nextToken();
            Token type = consume(Token.TokenType.TYPE);
            return new Tree("T", new Tree(":"),
                    new Tree("type: " + type.getValue()));
        } else {
            throw new UnexpectedTokenException(curToken, lex.curPos());
        }
    }

    private Tree F() throws ParseException {
        Token curToken = lex.curToken();
        if (curToken.getType() == Token.TokenType.NAME) {
            lex.nextToken();
            consume(Token.TokenType.LPAREN);
            Tree a = A();
            consume(Token.TokenType.RPAREN);
            return new Tree("F", new Tree("function's name: " + curToken.getValue()), new Tree("("), a, new Tree(")"));
        } else {
            throw new UnexpectedTokenException(curToken, lex.curPos());
        }
    }

    private Tree A() throws ParseException {
        Token curToken = lex.curToken();
        switch (curToken.getType()) {
            case NAME:
            case VAR:
                Tree p = P();
                Tree c = C();
                return new Tree("A", p, c);
            case RPAREN:
                return new Tree("A");
            default:
                throw new UnexpectedTokenException(curToken, lex.curPos());
        }
    }

    private Tree C() throws ParseException {
        Token curToken = lex.curToken();
        switch (curToken.getType()) {
            case SEM:
                lex.nextToken();
                Tree p = P();
                Tree c = C();
                return new Tree("C", new Tree(";"), p, c);
            case RPAREN:
                return new Tree("C");
            default:
                throw new UnexpectedTokenException(curToken, lex.curPos());

        }
    }

    private Tree P() throws ParseException {
        Token curToken = lex.curToken();
        switch (curToken.getType()) {
            case VAR:
                lex.nextToken();
                Tree v = V();
                return new Tree("P", new Tree("var"), v);
            case NAME:
                Tree v2 = V();
                return new Tree("P", v2);
            default:
                throw new UnexpectedTokenException(curToken, lex.curPos());
        }
    }


    private Tree V() throws ParseException {
        Token curToken = lex.curToken();
        if (curToken.getType() == Token.TokenType.NAME) {
            lex.nextToken();
            Tree n = N();
            Tree t = T();
            return new Tree("V", new Tree("variable name: " + curToken.getValue()), n, t);
        } else {
            throw new UnexpectedTokenException(curToken, lex.curPos());
        }
    }

    private Tree N() throws ParseException {
        Token curToken = lex.curToken();
        switch (curToken.getType()) {
            case VAR_SEP:
                lex.nextToken();
                Token name = consume(Token.TokenType.NAME);
                Tree n = N();
                return new Tree("N", new Tree(","), new Tree("name: " + name.getValue()), n);
            case TYPE_SEP:
                return new Tree("N");
            default:
                throw new UnexpectedTokenException(curToken, lex.curPos());
        }
    }

    public Tree parse(String input) throws ParseException {
        InputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        return S();
    }

    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            Tree result = parser.parse("function square(var a, b : integer):integer;");
            result.show();
            System.out.println("that's all");
        } catch (ParseException ex) {
            System.out.println(ex.getMessage() + ex.getErrorOffset());
        }
    }


}


