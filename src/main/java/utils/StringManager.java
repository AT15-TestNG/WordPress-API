package utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringManager {
    public static final String ALL_WHITE_SPACES_REGEX = "\\s+";
    public static final String SPACES_BEFORE_AND_AFTER_NEW_LINE_REGEX = "\\s*\\r\\s*|\\s*\\n\\s*";
    public static final String NEW_LINE_REGEX = "\"\\\\r|\\\\n\"";
    public static final String BLANK_SPACE_REGEX = " ";
    public static final String DOUBLE_BACK_SLASH_REGEX = "\\\\\\\\";
    public static final String PARENTHESIS_REGEX = "[()]";
    public static final String ANY_CHAR_REGEX = "(.*?)";

    public static String convertStringUsingRegex(String text, String regex, String replacementText) {
        return text.replaceAll(regex, replacementText);
    }

    public static String replaceNewLineWithSpace(String text) {
        return convertStringUsingRegex(text, NEW_LINE_REGEX, "");
    }

    public static String removeWhiteSpaces(String text) {
        return convertStringUsingRegex(text, ALL_WHITE_SPACES_REGEX, "");
    }

    public static String removeParenthesis(String text) {
        return convertStringUsingRegex(text, PARENTHESIS_REGEX, "");
    }

    public static String replaceDoubleBackSlashWithSlash(String text) {
        return convertStringUsingRegex(text, DOUBLE_BACK_SLASH_REGEX, "\\\\");
    }

    public static String generateStringDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss z");

        return formatter.format(date);
    }

    public static String getTimeStamp() {
        return String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
    }
}
