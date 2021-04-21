package me.labconnect.webapp.stylechecker;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks the file for forbidden statements that are determined by the course
 * guidelines.
 * <p>
 * Every element of forbidden statement Array List should be written in fully
 * expected form. For instance: {@code "break;", "System.exit(0);"}
 * 
 * @author Borga Haktan Bilen
 * @version 21/04/2021
 */
public class ForbiddenStatementChecker extends StyleChecker {

    ArrayList<String> forbiddenStatements;

    /**
     * Initializes the error line array and passes the forbidden statements
     * 
     * @param forbiddenStatements ArrayList containing forbidden statements
     */
    public ForbiddenStatementChecker(ArrayList<String> forbiddenStatements) {
        this.forbiddenStatements = forbiddenStatements;
    }

    /**
     * Default constructor. Initializes nothing
     * @apiNote Added in case of ommiting constructor injection
     */
    public ForbiddenStatementChecker() {}

    /**
     * Checkes the file for forbidden statements. Returns the violated lines.
     * 
     * @param fileInput Line by line file which is going to be checked
     * @return The ArrayList of lines that are containing the violated lines
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> fileInput) {
        ArrayList<String> errorList;
        Pattern temp;
        Matcher matc;

        errorList = new ArrayList<>();
        for (String str : fileInput) {

            for (String forbidden : forbiddenStatements) {
                temp = Pattern.compile(Pattern.quote(forbidden));
                matc = temp.matcher(str);

                if (matc.find()) {
                    errorList.add(str);
                }
            }
        }

        return errorList;
    }

    /**
     * Sets the forbidden statement array list
     * 
     * @param forbiddenStatements The Array List containing forbidden statements
     */
    public void setForbiddenStatements(ArrayList<String> forbiddenStatements) {
        this.forbiddenStatements = forbiddenStatements;
    }
}
