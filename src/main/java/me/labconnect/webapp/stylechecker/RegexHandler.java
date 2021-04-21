package me.labconnect.webapp.stylechecker;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHandler {

    String regex;
    Pattern pattern;
    Matcher patternMatcher;

    public boolean methodRegexMatcher( String str ) {
        this.regex = "(public|private|protected)\\s+(static?|abstract?)\\s+(String|void|int|boolean|char|long|byte|float|double|)\\s+(\\w*)\\s*(\\()";
        this.pattern = Pattern.compile( regex );
        this.patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }

        this.regex = "\\s*static\\s*void\\s*main\\s*\\(\\s*String\\s*\\[\\]\\s*[^\\)]*\\)";
        this.pattern = Pattern.compile( regex );
        this.patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }

        else {
            return false;
        }
    }

    public boolean constructorRegexMatcher( String str ) {
        this.regex = "(public)\\s+([A-Z]|[a-z])*\\s*(\\()";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean methodCallRegexMatcher( String str ) {
        this.regex = "(_*)(|[A-Z]+|[a-z]+|[1-9]*)(_*)\\s*(\\(\\s*\\))\\s*\\;";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean methodLowerCaseRegexMatcher( String str ) {
        this.regex = "(_*)([a-z]+|[1-9]*)(_*)\\s*(\\(\\s*\\))\\s*\\;";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean classRegexMatcher( String str ) {
        this.regex = "(public?|private?)(\\s+)(class{1})\\s+(\\w+|\\d*)";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean constantRegexMatcher( String str ) {
        this.regex = "(final)\\s+(String|int|boolean|char|long|byte|float|double|\\w)\\s+(\\w*)\\s*(=)\\s+(\\w*|\\d*|.)";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean ifRegexMatcher( String str ) {
        this.regex = "(if)\\s*(\\()";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean elseifRegexMatcher( String str ) {
        this.regex = "(else if)\\s*(\\()";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean elseRegexMatcher( String str ) {
        this.regex = "(else)\\s*";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean switchRegexMatcher( String str ) {
        this.regex = "(switch)\\s*(\\()";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean forRegexMatcher( String str ) {
        this.regex = "(for)\\s*(\\()";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean whileRegexMatcher( String str ) {
        this.regex = "(while)\\s*(\\()";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean doRegexMatcher( String str ) {
        this.regex = "(do)\\s*(\\()";
        this.pattern = Pattern.compile( this.regex );
        Matcher patternMatcher = pattern.matcher( str );

        if( patternMatcher.find() ){
            return true;
        }
        else {
            return false;
        }
    }
}