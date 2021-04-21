package me.labconnect.webapp.stylechecker;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

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