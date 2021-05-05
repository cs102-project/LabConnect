package me.labconnect.webapp.models.testing.style;

import me.labconnect.webapp.models.testing.Tests;

import java.util.ArrayList;

import static me.labconnect.webapp.models.testing.Tests.OPERATORS_SPACE;

/**
 * This class is for checking whether every operator character have space right before and after
 * them or not.
 *
 * @author Berk Çakar
 * @author Borga Haktan Bilen
 * @version 28.04.2021
 */
public class OperatorsSpaceChecker extends StyleChecker {

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Space between operators checker";
    }

    /**
     * Checks whether every operator character have space right before and after them or not.
     *
     * @param codeFile is the file. List of every line.
     * @return The lines that are failed the check.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < codeFile.size(); lineIndex++) {
            if (RegexHelper.operatorsSpaceRegexMatcher(codeFile.get(lineIndex))) {
                for (int charIndex = 1; charIndex < codeFile.get(lineIndex).length() - 1; charIndex++) {
                    if (charIndex != codeFile.get(lineIndex).length() - 1) {
                        if (codeFile.get(lineIndex).charAt(charIndex) == '+'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')
                                || !(codeFile.get(lineIndex).charAt(charIndex + 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == '-'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')
                                || !(codeFile.get(lineIndex).charAt(charIndex + 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == '*'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')
                                || !(codeFile.get(lineIndex).charAt(charIndex + 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == '/'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')
                                || !(codeFile.get(lineIndex).charAt(charIndex + 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == '%'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')
                                || !(codeFile.get(lineIndex).charAt(charIndex + 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        }
                    } else {
                        if (codeFile.get(lineIndex).charAt(charIndex) == '+'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == '-'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == '*'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == '/'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        } else if (codeFile.get(lineIndex).charAt(charIndex) == '%'
                                && !(codeFile.get(lineIndex).charAt(charIndex - 1) == ' ')) {
                            errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                        }
                    }
                }
            }
        }

        return errorList;
    }

    @Override
    public Tests getTestType() {
        return OPERATORS_SPACE;
    }
}
