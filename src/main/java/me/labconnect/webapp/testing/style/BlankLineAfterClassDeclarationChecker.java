package me.labconnect.webapp.testing.style;

import java.util.ArrayList;

/**
 * This class is for checking if there is a blank line after the class or interface declaration.
 *
 * @author Berk Çakar
 * @version 27.04.2021
 */
public class BlankLineAfterClassDeclarationChecker extends StyleChecker {

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Blank line after class declaration checker";
    }

    /**
     * This method checks if there is a blank line after the class or interface declaration.
     *
     * @param codeFile is the code file to be checked.
     * @return An ArrayList which contains the lines that failed the test.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for ( int i = 0; i < codeFile.size(); i++ ) {
            if((RegexHelper.classRegexMatcher(codeFile.get(i)) || RegexHelper.interfaceRegexMatcher(codeFile.get(i)))) {
                if ( codeFile.get(i + 1).trim().charAt(0) == '{' && !codeFile.get(i+2).isEmpty()) {
                    errorList.add(codeFile.get(i+2));
                }

                else if ( codeFile.get(i).trim().charAt( codeFile.get(i).trim().length() - 1 ) == '{' && !codeFile.get( i+1 ).isEmpty()  ) {
                    errorList.add(codeFile.get(i+1));
                }
            }
        }

        return errorList;
    }
}