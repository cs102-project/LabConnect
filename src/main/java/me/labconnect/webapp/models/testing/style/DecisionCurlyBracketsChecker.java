package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * This class is to check whether different kinds of decision statements in a file have the required
 * brackets or not.
 *
 * @author Berk Çakar
 * @version 28.04.2021
 */
public class DecisionCurlyBracketsChecker extends StyleChecker {

    /**
     * Checks whether every decision statement have curly brackets or not.
     *
     * @param codeFile The file. List of every line.
     * @return The lines that are failed the check.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < codeFile.size(); lineIndex++) {
            if (indexExists(codeFile, lineIndex + 1)) {
                if (RegexHelper.ifRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{") && !codeFile.get(lineIndex + 1)
                            .contains("{")) {
                        errorList.add(codeFile.get(lineIndex));
                    }
                } else if (RegexHelper.elseifRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{") && !codeFile.get(lineIndex + 1)
                            .contains("{")) {
                        errorList.add(codeFile.get(lineIndex));
                    }
                } else if (RegexHelper.elseRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{") && !codeFile.get(lineIndex + 1)
                            .contains("{")) {
                        errorList.add(codeFile.get(lineIndex));
                    }
                } else if (RegexHelper.switchRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{") && !codeFile.get(lineIndex + 1)
                            .contains("{")) {
                        errorList.add(codeFile.get(lineIndex));
                    }
                }
            } else {
                if (RegexHelper.ifRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{")) {
                        errorList.add(codeFile.get(lineIndex));
                    }
                } else if (RegexHelper.elseifRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{")) {
                        errorList.add(codeFile.get(lineIndex));
                    }
                } else if (RegexHelper.elseRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{")) {
                        errorList.add(codeFile.get(lineIndex));
                    }
                } else if (RegexHelper.switchRegexMatcher(codeFile.get(lineIndex))) {
                    if (!codeFile.get(lineIndex).contains("{")) {
                        errorList.add(codeFile.get(lineIndex));
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
        return "Decision curly bracket checker";
    }

}
