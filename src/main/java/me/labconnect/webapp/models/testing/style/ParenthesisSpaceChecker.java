package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * This class is for checking whether different kinds of decision and loop statements in a file have
 * the required spaces before and after their parenthesis or not.
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
     * Checks whether different kinds of decision and loop statements in a file have the required
     * spaces before and after their parenthesis or not.
     *
     * @param codeFile The file. List of every line.
     * @return The lines that are failed the check.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < codeFile.size(); lineIndex++) {
            if (RegexHelper.forRegexMatcher(codeFile.get(lineIndex))
                    || RegexHelper.ifRegexMatcher(codeFile.get(lineIndex))
                    || RegexHelper.elseifRegexMatcher(codeFile.get(lineIndex))
                    || RegexHelper.whileRegexMatcher(codeFile.get(lineIndex))) {
                for (int charIndex = 0; charIndex < codeFile.get(lineIndex).length(); charIndex++) {
                    if (charIndex != codeFile.get(lineIndex).length() - 1) {
                        if (codeFile.get(lineIndex).charAt(charIndex) == '('
                                && codeFile.get(lineIndex).charAt(charIndex - 1) != ' '
                                && codeFile.get(lineIndex).charAt(charIndex + 1) != ' ') {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == ')'
                                && codeFile.get(lineIndex).charAt(charIndex - 1) != ' ') {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        }
                    } else {
                        if (codeFile.get(lineIndex).charAt(charIndex) == '('
                                && codeFile.get(lineIndex).charAt(charIndex - 1) != ' ') {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == ')'
                                && codeFile.get(lineIndex).charAt(charIndex - 1) != ' ') {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        }
                    }
                }
            }
        }

        return errorList;
    }
}