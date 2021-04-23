package me.labconnect.webapp.testing.style;

import java.util.ArrayList;

/**
 * @author Berk Çakar
 * @author Vedat Eren Arıcan
 * @version 22/04/2021
 */
public class MethodNamingChecker extends StyleChecker {

    String[] identifiers = { "void", "int", "Integer", "double", "Double", "float", "Float", "long", "Long", "short",
            "Short", "boolean", "Boolean", "char", "Character", "byte", "Byte" };

    /**
    * Checks whether method names are conventional or not.
    *
    * @param codeFile The file. List of every line.
    * @return The lines that are voiding the convention.
    */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int i = 0; i < codeFile.size(); i++) {
            if (RegexHelper.methodRegexMatcher(codeFile.get(i))) {
                if (checkCasing(extractMethodName(codeFile.get(i))) == false) {
                    errorList.add(codeFile.get(i));
                }
            }
        }

        return errorList;
    }

    /**
     * This method extracts the method name from the given line.
     * @param line is the line to be processed.
     * @return Name of the method.
     */
    public String extractMethodName(String line) {
        int returnTypeStartIndex = 0;
        Character currentChar;
        int returnTypeEndIndex = 0;
        int methodNameStartIndex = 0;
        String sB = "";

        for (int j = 0; j < line.length(); j++) {
            if (line.indexOf(identifiers[j]) == 0) {
                continue;
            }

            else if (line.indexOf(identifiers[j]) > 0) {
                returnTypeStartIndex = line.indexOf(identifiers[j]);
                break;
            }
        }

        for (int i = returnTypeStartIndex; i < line.length(); i++) {
            currentChar = line.charAt(i);
            if (!currentChar.equals(' ')) {
                continue;
            }

            else {
                returnTypeEndIndex = i;
                break;
            }
        }

        for (int k = returnTypeEndIndex; k < line.length(); k++) {
            currentChar = line.charAt(k);

            if (currentChar == ' ') {
                continue;
            }

            else {
                methodNameStartIndex = k;
                break;
            }
        }

        for (int l = methodNameStartIndex; l < line.length(); l++) {
            currentChar = line.charAt(l);

            if (currentChar.equals(' ') || currentChar.equals('(')) {
                break;
            }

            else {
                sB += currentChar;
            }
        }

        return sB;
    }

    /**
     * This method checks whether the method name has a valid casing style or not.
     * @param methodName is the methodName to be checked.
     * @return {@code true} if the naming is valid, otherwise {@code false}
     */
    private boolean checkCasing(String methodName) {
        if (Character.isUpperCase(methodName.charAt(0))) {
            return false;
        }

        for (int i = 1; i < methodName.length(); i++) {
            if (!Character.isLowerCase(methodName.charAt(i))) {
                return false;
            }
        }
        return true;
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
