package me.labconnect.webapp.stylechecker;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Forbiden statement checker.
 * <p>
 * Checks the file for forbidden statements that are
 * determined by the course guidelines
 * 
 * @author Borga Haktan Bilen
 * @version 21/04/2021
 */
public class ForbiddenStatementCheck extends StyleChecker {

    ArrayList<String> errorLines;
    ArrayList<String> forbiddenStatements;

    /**
     * Initializes the error line array and passes the forbidden 
     * statements
     * @param forbiddenStatements ArrayList containing forbidden statements
     */
    public ForbiddenStatementCheck( ArrayList<String> forbiddenStatements ) {
        this.forbiddenStatements = forbiddenStatements;
        errorLines = new ArrayList<>();
    }

    /**
     * Checkes the file for forbidden statements. Returns the violated
     * lines.
     * @param fileInput Line by line file which is going to be checked
     * @return The ArrayList of lines that are containing the violated
     * lines
     */
    @Override
    protected ArrayList<String> checkFile( ArrayList<String> fileInput ) {
        Pattern temp;
        Matcher matc;

        for ( String str : fileInput ) {
            
            for ( String forbidden : forbiddenStatements ) {
                temp = Pattern.compile( forbidden );
                matc = temp.matcher( str );

                if ( matc.find() ) {
                    errorLines.add( str );
                }
            }
        }

        return errorLines;
    }
}
