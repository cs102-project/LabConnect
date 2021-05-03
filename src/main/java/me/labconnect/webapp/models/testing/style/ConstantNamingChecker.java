package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * Check if the constants in the source code are named correctly
 *
 * @author Berk Çakar
 * @author Vedat Eren Arıcıan
 * @version 22.04.2021
 */
public class ConstantNamingChecker extends StyleChecker {

    /**
     * Checks whether constant names are conventional or not.
     *
     * @param codeFile The file. List of every line.
     * @return The lines that are voiding the convention.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < codeFile.size(); lineIndex++) {
            if (RegexHelper.constantRegexMatcher(codeFile.get(lineIndex))) {
                if (!isAllCaps(extractConstant(codeFile.get(lineIndex)))) {
                    errorList.add(codeFile.get(lineIndex));
                }
            }
        }

        return errorList;
    }

    /**
     * This method gets the end position of the constant expression.
     *
     * @param line is the line which includes the constant expression.
     * @return End position of the constant expression.
     */
    private int getEndPosition(String line) {
        int endPos = 0;
        String[] identifiers = {"int", "double", "float", "long", "String", "Character", "char",
                "Integer", "Double",
                "Float", "Long", "Boolean", "boolean", "Byte", "byte", "Short", "short"};

        for (int typeIndex = 0; typeIndex < identifiers.length; typeIndex++) {
            if (line.contains(identifiers[typeIndex])) {
                endPos = line.indexOf(identifiers[typeIndex]) + identifiers[typeIndex].length();
            }
        }

        return endPos;
    }

    /**
     * This method extracts the constant name from the given line.
     *
     * @param line is the line to be processed.
     * @return Name of the constant.
     */
    private String extractConstant(String line) {
        String constant = "";
        for (int charIndex = getEndPosition(line); charIndex < line.length(); charIndex++) {
            constant += line.charAt(charIndex);
        }

        return constant;
    }

    /**
     * This method checks whether the constant name is all caps or not.
     *
     * @param constant is the constant to be checked.
     * @return {@code true} if the constant name is all caps, otherwise {@code false}.
     */
    private boolean isAllCaps(String constant) {
        return constant.equals(constant.toUpperCase());
    }

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Constant naming checker";
    }
}