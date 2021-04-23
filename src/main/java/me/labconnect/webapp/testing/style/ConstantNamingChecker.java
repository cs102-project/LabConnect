package me.labconnect.webapp.testing.style;

import java.util.ArrayList;

/**
 * Check if the constants in the source code are named correctly
 * 
 * @author Berk Çakar
 * @author Vedat Eren Arıcıan
 * @version 22.04.2021
 */
public class ConstantNamingChecker extends StyleChecker {

    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int i = 0; i < codeFile.size(); i++) {
            if (RegexHelper.constantRegexMatcher(codeFile.get(i))) {
                if (isAllCaps(extractConstant(codeFile.get(i))) == false) {
                    errorList.add(codeFile.get(i));
                }
            }
        }

        return errorList;
    }

    private int getEndPosition(String line) {
        int endPos = 0;
        String[] identifiers = { "int", "double", "float", "long", "String", "Character", "char", "Integer", "Double",
                "Float", "Long", "Boolean", "boolean", "Byte", "byte", "Short", "short" };

        for (int i = 0; i < identifiers.length; i++) {
            if (line.contains(identifiers[i])) {
                endPos = line.indexOf(identifiers[i]) + identifiers[i].length();
            }
        }

        return endPos;
    }

    private String extractConstant(String line) {
        String constant = "";
        for (int i = getEndPosition(line); i < line.length(); i++) {
            constant += line.charAt(i);
        }

        return constant;
    }

    private boolean isAllCaps(String constant) {
        if (!constant.equals(constant.toUpperCase())) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String getName() {
        return "Constant naming checker";
    }
}