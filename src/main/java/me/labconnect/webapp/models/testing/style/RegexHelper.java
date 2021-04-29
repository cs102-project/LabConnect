package me.labconnect.webapp.models.testing.style;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class serves static helper methods for regular expression checks.
 *
 * @author Berk Çakar
 * @author Berkan Şahin
 * @author Borga Haktan Bilen
 * @author Alp Ertan
 * @author Vedat Eren Arıcan
 * @version 28.04.2021
 */
public class RegexHelper {

    /**
     * Checks the method header if it complies to Java conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean methodRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;

        regex = "(public|private|protected)\\s+(static?|abstract?)\\s+(String|void|int|boolean|char|long|byte|float|double|)\\s+(\\w*)\\s*(\\()";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        if (patternMatcher.find()) {
            return true;
        }

        regex = "\\s*static\\s*void\\s*main\\s*\\(\\s*String\\s*\\[\\]\\s*[^\\)]*\\)";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks the constructor if it complies to Java conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean constructorRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(public)\\s+([A-Z]|[a-z])*\\s*(\\()";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks the method calling statement if it complies to Java conventions or not
     * .
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean methodCallRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        //regex = "(_*)(|[A-Z]+|[a-z]+|[1-9]*)(_*)\\s*(\\(\\s*\\))\\s*\\;";
        regex = "(?!\\bif\\b|\\bfor\\b|\\bwhile\\b|\\bswitch\\b|\\btry\\b|\\bcatch\\b)(\\b[\\w]+\\b)[\\s\\n\\r]*(?=\\(.*\\))";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks the method calling statement if it complies to Java conventions or not
     * . Tries to match both javadoc style and C style comments (excluding
     * {@code //} style).
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     * @apiNote Do not try to match the file line by line. Comments should be in a
     *          continous state.
     */
    public static boolean commentBlockRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks the method calling statement if it complies to Java conventions or not
     * . Tries to match both javadoc style and C style comments (excluding
     * {@code //} style).
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     * @apiNote Do not try to match the file line by line. Comments should be in a
     *          continous state.
     */
    public static boolean generalCommentRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Removes inline comments.
     *
     * @param str The string that is going to be processed.
     * @return String with inline comments removed
     */
    public static String inlineCommentRegexReplacer(String str, String replacement) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(//.*)";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.replaceAll(replacement);
    }

    /**
     * Checks if the method is named conventionally or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean methodLowerCaseRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(_*)([a-z]+|[1-9]*)(_*)\\s*(\\(\\s*\\))\\s*\\;";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the class is named conventionally or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean classRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(public?|private?)(\\s+)(class{1})\\s+(\\w+|\\d*)";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the interface is named conventionally or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean interfaceRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(public?|private?)(\\s+)(interface{1})\\s+(\\w+|\\d*)";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the constant is named conventionally or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean constantRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(final)\\s+(String|int|boolean|char|long|byte|float|double|\\w)\\s+(\\w*)\\s*(=)\\s+(\\w*|\\d*|.)";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the {@code if} statement is constructed according to conventions or
     * not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean ifRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(if)\\s*(\\()";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the {@code else if} statement is constructed according to
     * conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean elseifRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(else if)\\s*(\\()";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the {@code else} statement is constructed according to conventions
     * or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean elseRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(else)\\s*";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the {@code switch} statement is constructed according to
     * conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean switchRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(switch)\\s*(\\()";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the {@code for} statement is constructed according to conventions
     * or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean forRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(for)\\s*(\\()";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the {@code while} statement is constructed according to conventions
     * or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean whileRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(while)\\s*(\\()";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the {@code do} statement is constructed according to conventions or
     * not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to the Java
     *         conventions, {@code false} otherwise.
     */
    public static boolean doRegexMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(do)\\s*(\\()";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the line containes bitwise ampersand operator or not
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the line containes bitwise ampersand operator ,
     *         {@code false} otherwise.
     */
    public static boolean bitwiseAmpersandMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(if|else if)\\s*\\(.+[^&]&[^&]";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }

    /**
     * Checks if the line containes bitwise or operator or not
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the line containes bitwise or operator ,
     *         {@code false} otherwise.
     */
    public static boolean bitwiseOrMatcher(String str) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(if|else if)\\s*\\(.+[^\\|]\\|[^\\|]";
        pattern = Pattern.compile(regex);
        patternMatcher = pattern.matcher(str);

        return patternMatcher.find();
    }
}