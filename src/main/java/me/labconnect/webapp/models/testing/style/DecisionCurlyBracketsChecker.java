package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * This class is to check whether different kinds of decision statements in a
 * file have the required brackets or not.
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

        for (int i = 0; i < codeFile.size(); i++) {
            if (RegexHelper.ifRegexMatcher(codeFile.get(i))) {
                if (!codeFile.get(i).contains("{") && !codeFile.get(i + 1).contains("{")) {
                    errorList.add(codeFile.get(i));
                }
            } else if (RegexHelper.elseifRegexMatcher(codeFile.get(i))) {
                if (!codeFile.get(i).contains("{") && !codeFile.get(i + 1).contains("{")) {
                    errorList.add(codeFile.get(i));
                }
            } else if (RegexHelper.elseRegexMatcher(codeFile.get(i))) {
                if (!codeFile.get(i).contains("{") && !codeFile.get(i + 1).contains("{")) {
                    errorList.add(codeFile.get(i));
                }
            } else if (RegexHelper.switchRegexMatcher(codeFile.get(i))) {
                if (!codeFile.get(i).contains("{") && !codeFile.get(i + 1).contains("{")) {
                    errorList.add(codeFile.get(i));
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
