package me.labconnect.webapp.models.testing.style;

import me.labconnect.webapp.models.testing.Tests;

import java.util.ArrayList;

import static me.labconnect.webapp.models.testing.Tests.FOR_LOOP_SEMICOLON;

/**
 * This class is for checking if there is a space after the semicolons in for loops.
 *
 * @author Berk Çakar
 * @author Alp Ertan
 * @version 01.05.2021
 */
public class ForLoopSemicolonChecker extends StyleChecker {

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "For loop semicolon checker";
    }

    /**
     * This method checks if there is a space after the semicolons in for loops.
     *
     * @param codeFile is the code file to be checked.
     * @return An ArrayList which contains the lines that failed the test.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < codeFile.size(); lineIndex++) {
            if (isNotAComment(codeFile.get(lineIndex))) {
                if (RegexHelper.forRegexMatcher(codeFile.get(lineIndex))) {
                    for (int j = 0; j < codeFile.get(lineIndex).length() - 1; j++) {
                        if (codeFile.get(lineIndex).charAt(j) == ';'
                                && codeFile.get(lineIndex).charAt(j + 1) != ' ') {
                            errorList.add(codeFile.get(lineIndex));
                        }
                    }
                }
            }
        }

        return errorList;
    }

    @Override
    public Tests getTestType() {
        return FOR_LOOP_SEMICOLON;
    }
}
