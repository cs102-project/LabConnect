package me.labconnect.webapp.models.testing.style;

import me.labconnect.webapp.models.testing.Tests;

import java.util.ArrayList;

import static me.labconnect.webapp.models.testing.Tests.LOOP_CURLY_BRACKETS;

/**
 * This class is to check whether different kinds of loops in a file have the required brackets or
 * not.
 *
 * @author Berk Çakar
 * @author Alp Ertan
 * @version 22.04.2021
 */
public class LoopCurlyBracketsChecker extends StyleChecker {

    /**
     * Checks whether every loop statement have curly brackets or not.
     *
     * @param codeFile The file. List of every line.
     * @return The lines that are failed the check.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < codeFile.size(); lineIndex++) {
            if (indexExists(codeFile, lineIndex + 1)) {
                if (RegexHelper.whileRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{") && !codeFile.get(lineIndex + 1)
                            .contains("{")) {
                        errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]" );
                    }
                } else if (RegexHelper.forRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{") && !codeFile.get(lineIndex + 1)
                            .contains("{")) {
                        errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                    }
                } else if (RegexHelper.doRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{") && !codeFile.get(lineIndex + 1)
                            .contains("{")) {
                        errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                    }
                }
            } else {
                if (RegexHelper.whileRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{")) {
                        errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                    }
                } else if (RegexHelper.forRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{")) {
                        errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                    }
                } else if (RegexHelper.doRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{")) {
                        errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                    }
                }
            }
        }

        return errorList;
    }

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Loop curly bracket checker";
    }

    @Override
    public Tests getTestType() {
        return LOOP_CURLY_BRACKETS;
    }
}
