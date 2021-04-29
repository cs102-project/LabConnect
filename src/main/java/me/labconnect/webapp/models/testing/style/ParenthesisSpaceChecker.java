package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * This class is for checking whether different kinds of decision and loop statements in a
 * file have the required spaces before and after their parenthesis or not.
 *
 * @author Berk Çakar
 * @version 29.04.2021
 */
public class ParenthesisSpaceChecker extends StyleChecker {

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Parenthesis space checker";
    }

    /**
     * Checks whether different kinds of decision and loop statements in a
     * file have the required spaces before and after their parenthesis or not.
     *
     * @param codeFile The file. List of every line.
     * @return The lines that are failed the check.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int i = 0; i < codeFile.size(); i++) {
            if (RegexHelper.forRegexMatcher(codeFile.get(i)) || RegexHelper.ifRegexMatcher(codeFile.get(i)) ||
                RegexHelper.elseifRegexMatcher(codeFile.get(i)) || RegexHelper.whileRegexMatcher(codeFile.get(i)) ) {
                for (int j = 0; j < codeFile.get(i).length(); j++) {
                    if (j != codeFile.get(i).length() - 1) {
                        if (codeFile.get(i).charAt(j) == '(' && codeFile.get(i).charAt(j-1) != ' ' && codeFile.get(i).charAt(j+1) != ' ') {
                            errorList.add(codeFile.get(i));
                        } else if (codeFile.get(i).charAt(j) == ')' && codeFile.get(i).charAt(j-1) != ' ') {
                            errorList.add(codeFile.get(i));
                        }
                    }

                    else {
                        if (codeFile.get(i).charAt(j) == '(' && codeFile.get(i).charAt(j-1) != ' ') {
                            errorList.add(codeFile.get(i));
                        } else if (codeFile.get(i).charAt(j) == ')' && codeFile.get(i).charAt(j-1) != ' ') {
                            errorList.add(codeFile.get(i));
                        }
                    }
                }
            }
        }

        return errorList;
    }
}