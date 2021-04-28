package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * This class is to check whether different kinds of loops in a file have the
 * required brackets or not.
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

        for (int i = 0; i < codeFile.size(); i++) {
            if (RegexHelper.whileRegexMatcher(codeFile.get(i))) {
                if (!codeFile.get(i).contains("{") && !codeFile.get(i + 1).contains("{")) {
                    errorList.add(codeFile.get(i));
                }
            } else if (RegexHelper.forRegexMatcher(codeFile.get(i))) {
                if (!codeFile.get(i).contains("{") && !codeFile.get(i + 1).contains("{")) {
                    errorList.add(codeFile.get(i));
                }
            } else if (RegexHelper.doRegexMatcher(codeFile.get(i))) {
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
        return "Loop curly bracket checker";
    }
}
