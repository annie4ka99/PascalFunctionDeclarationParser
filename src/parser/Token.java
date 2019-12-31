package parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Token {
    public enum TokenType {
        TYPE_SEP,
        VAR_SEP,
        SEM,
        LPAREN,
        RPAREN,
        END,
        FUNC,
        PROC,
        VAR,
        NAME,
        TYPE
    }

    Token(String value, TokenType tokenType) {
        this.value = value;
        this.type = tokenType;
    }

    private final String value;

    private final TokenType type;

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }


    private static final String[] typeList = {
            "boolean",
            "char",
            "integer",
            "byte",
            "word",
            "short",
            "long",
            "real",
            "single",
            "double",
            "extended"
    };

    static Set<String> types = new HashSet<>(Arrays.asList(typeList));
}
