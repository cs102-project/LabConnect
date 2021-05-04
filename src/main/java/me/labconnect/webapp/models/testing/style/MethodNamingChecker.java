package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks if method names in a given file obey the conventions
 *
 * @author Berk Çakar
 * @author Borga Haktan Bilen
 * @author Vedat Eren Arıcan
 * @author Alp Ertan
 * @version 22.04.2021
 */
public class MethodNamingChecker extends StyleChecker {

    String[] identifiers = {"void", "int", "Integer", "double", "Double", "float", "Float", "long",
            "Long", "short",
            "Short", "boolean", "Boolean", "char", "Character", "byte", "Byte", "String"};

    /**
     * Checks whether method names are conventional or not.
     *
     * @param codeFile The file. List of every line.
     * @return The lines that are voiding the convention.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < codeFile.size(); lineIndex++) {
            if (RegexHelper.methodRegexMatcher(codeFile.get(lineIndex))
                    && !RegexHelper.constructorRegexMatcher(codeFile.get(lineIndex))) {
                if (!checkCasing(extractMethodName(codeFile.get(lineIndex)))) {
                    errorList.add(codeFile.get(lineIndex) + " [at line: " + lineIndex + 1 + "]");
                }
            }
        }

        return errorList;
    }

    /**
     * This method extracts the method name from the given line.
     *
     * @param line is the line to be processed.
     * @return Name of the method.
     */
    public String extractMethodName(String line) {
        int returnTypeStartIndex = 0;
        Character currentChar;
        int returnTypeEndIndex = 0;
        int methodNameStartIndex = 0;
        String sB = "";

        for (int charIndex = 0; charIndex < line.length(); charIndex++) {
            if (line.indexOf(identifiers[charIndex]) == 0) {
                continue;
            } else if (line.indexOf(identifiers[charIndex]) > 0) {
                returnTypeStartIndex = line.indexOf(identifiers[charIndex]);
                break;
            }
        }

        for (int charIndex = returnTypeStartIndex; charIndex < line.length(); charIndex++) {
            currentChar = line.charAt(charIndex);
            if (!currentChar.equals(' ')) {
                continue;
            } else {
                returnTypeEndIndex = charIndex;
                break;
            }
        }

        for (int charIndex = returnTypeEndIndex; charIndex < line.length(); charIndex++) {
            currentChar = line.charAt(charIndex);

            if (currentChar == ' ') {
                continue;
            } else {
                methodNameStartIndex = charIndex;
                break;
            }
        }

        for (int charIndex = methodNameStartIndex; charIndex < line.length(); charIndex++) {
            currentChar = line.charAt(charIndex);

            if (currentChar.equals(' ') || currentChar.equals('(')) {
                break;
            } else {
                sB += currentChar;
            }
        }

        return sB;
    }

    /**
     * This method checks whether the method name has a valid casing style or not.
     *
     * @param methodName is the methodName to be checked.
     * @return {@code true} if the naming is valid, otherwise {@code false}
     */
    private boolean checkCasing(String methodName) {
        Pattern methodNamingPattern;
        Matcher methodNamingMatcher;
        methodNamingPattern = Pattern.compile("^[a-z][a-zA-Z0-9].*");
        methodNamingMatcher = methodNamingPattern.matcher(methodName);
        if (methodName.contains("|") || methodName.contains("&") || methodName.contains("+")
                || methodName.contains("-")
                || methodName.contains("*") || methodName.charAt(0) == '_' || methodName.charAt(0) == '$') {
            return false;
        } else {
            return methodNamingMatcher.find();
        }
    }

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Method naming checker";
    }
}
