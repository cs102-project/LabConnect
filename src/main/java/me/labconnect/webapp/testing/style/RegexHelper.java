package me.labconnect.webapp.testing.style;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class serves static helper methods for regular
 * expression checks.
 *
 * @author Berk Çakar
 * @author Berkan Şahin
 * @author Borga Haktan Bilen
 * @author Alp Ertan
 * @version 22.04.2021
 */
public class RegexHelper {

    /**
     * Checks the method header if it complies to Java
     * conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean methodRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;

        regex = "(public|private|protected)\\s+(static?|abstract?)\\s+(String|void|int|boolean|char|long|byte|float|double|)\\s+(\\w*)\\s*(\\()";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }

        regex = "\\s*static\\s*void\\s*main\\s*\\(\\s*String\\s*\\[\\]\\s*[^\\)]*\\)";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }

        else {
            return false;
        }
    }

    /**
     * Checks the constructor if it complies to Java
     * conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean constructorRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(public)\\s+([A-Z]|[a-z])*\\s*(\\()";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks the method calling statement if it complies to
     * Java conventions or not .
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean methodCallRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(_*)(|[A-Z]+|[a-z]+|[1-9]*)(_*)\\s*(\\(\\s*\\))\\s*\\;";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the method is named conventionally or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean methodLowerCaseRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(_*)([a-z]+|[1-9]*)(_*)\\s*(\\(\\s*\\))\\s*\\;";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the class is named conventionally or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean classRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(public?|private?)(\\s+)(class{1})\\s+(\\w+|\\d*)";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Checks if the interface is named conventionally or not.
     * 
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean interfaceRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(public?|private?)(\\s+)(interface{1})\\s+(\\w+|\\d*)";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the constant is named conventionally or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean constantRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(final)\\s+(String|int|boolean|char|long|byte|float|double|\\w)\\s+(\\w*)\\s*(=)\\s+(\\w*|\\d*|.)";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the {@code if} statement is constructed according to
     * conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean ifRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(if)\\s*(\\()";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the {@code else if} statement is constructed
     * according to conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean elseifRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(else if)\\s*(\\()";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the {@code else} statement is constructed
     * according to conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean elseRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(else)\\s*";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the {@code switch} statement is constructed
     * according to conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean switchRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(switch)\\s*(\\()";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the {@code for} statement is constructed
     * according to conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean forRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(for)\\s*(\\()";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the {@code while} statement is constructed
     * according to conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean whileRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(while)\\s*(\\()";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the {@code do} statement is constructed
     * according to conventions or not.
     *
     * @param str The string that is going to be checked.
     * @return {@code true} if the style of the statement complies to
     * the Java conventions, {@code false} otherwise.
     */
    public static boolean doRegexMatcher( String str ) {
        String regex;
        Pattern pattern;
        Matcher patternMatcher;
        regex = "(do)\\s*(\\()";
        pattern = Pattern.compile( regex );
        patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }
}