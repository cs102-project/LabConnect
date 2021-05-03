package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * This class is for checking whether different kinds of method or constructor calls in a file have
 * the required spaces before and after their parenthesis or not.
 *
 * @author Berk Çakar
 * @version 29.04.2021
 */
public class MethodParenthesisSpaceChecker extends StyleChecker {

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Method parenthesis space checker";
    }

    /**
     * Checks whether different kinds of method or constructor calls in a file have the required
     * spaces before and after their parenthesis or not.
     *
     * @param codeFile The file. List of every line.
     * @return The lines that are failed the check.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < codeFile.size(); lineIndex++) {
            if (RegexHelper.methodCallRegexMatcher(codeFile.get(lineIndex))) {
                for (int charIndex = 0; charIndex < codeFile.get(lineIndex).length(); charIndex++) {
                    if (charIndex != codeFile.get(lineIndex).length() - 1) {
                        if (codeFile.get(lineIndex).charAt(charIndex) == '('
                                && !(codeFile.get(lineIndex).charAt(charIndex + 1) == ')')) {
                            if (codeFile.get(lineIndex).charAt(charIndex + 1) != ' ') {
                                errorList.add(codeFile.get(lineIndex));
                            }
                        }
                    }
                }
            }
        }

        return errorList;
    }
}
