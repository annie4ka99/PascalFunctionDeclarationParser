import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


public class LexicalAnalyzer {
    private final InputStream is;
    private int curChar;
    int curPos;
    private Token curToken;

    private Set<String> identifiers = new HashSet<>();

    public LexicalAnalyzer(InputStream is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    private boolean isBlank(int c)
    {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case '(':
                curToken = new Token(String.valueOf((char)curChar), Token.TokenType.LPAREN);
                nextChar();
                break;
            case ')':
                curToken = new Token(String.valueOf((char)curChar), Token.TokenType.RPAREN);
                nextChar();
                break;
            case ':':
                curToken = new Token(String.valueOf((char)curChar), Token.TokenType.TYPE_SEP);
                nextChar();
                break;
            case ',':
                curToken = new Token(String.valueOf((char)curChar), Token.TokenType.VAR_SEP);
                nextChar();
                break;
            case ';':
                curToken = new Token(String.valueOf((char)curChar), Token.TokenType.SEM);
                nextChar();
                break;
            case -1:
                curToken = new Token(String.valueOf((char)curChar), Token.TokenType.END);
                break;
            default:
                StringBuilder tokenSB = new StringBuilder();
                int start = curChar;
                int startPos = curPos;
                while (curChar != -1 && (Character.isLetterOrDigit(curChar) || curChar == '_' || curChar=='.')) {
                    tokenSB.append((char)curChar);
                    nextChar();
                }
                String token = tokenSB.toString();
                switch (token) {
                    case "function":
                        curToken = new Token(token, Token.TokenType.FUNC);
                        break;
                    case "procedure":
                        curToken = new Token(token, Token.TokenType.PROC);
                        break;
                    case "var":
                        curToken = new Token(token, Token.TokenType.VAR);
                        break;
                    default:
                        if (Token.types.contains(token)
                                ||Pattern.matches("^(([1-9][0-9]*)|[0])[.][.](([1-9][0-9]*)|[0])$",  token
                        )
                        ) {
                            curToken = new Token(token, Token.TokenType.TYPE);
                            break;
                        } else if (Pattern.matches("^[a-zA-Z_]\\w*$", token)) {
                            if (identifiers.contains(token))
                                throw new ParseException("Duplicate identifiers '"+ token + "'", curPos);
                            identifiers.add(token);
                            curToken = new Token(token, Token.TokenType.NAME);
                            break;
                        } else {
                            throw new ParseException("Illegal character '" + (char) start + "'", startPos);
                        }

                }
        }
    }


    Token curToken() {
        return curToken;
    }

    int curPos() {
        return curPos;
    }
}
