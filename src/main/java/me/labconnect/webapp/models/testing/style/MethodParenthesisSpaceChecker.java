package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

public class MethodParenthesisSpaceChecker extends StyleChecker {

    @Override
    public String getName() {
        return "Method parenthesis space checker";
    }

    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int i = 0; i < codeFile.size(); i++) {
            if (RegexHelper.methodCallRegexMatcher(codeFile.get(i))) {
                for (int j = 0; j < codeFile.size(); j++) {
                    if (j != codeFile.size() - 1) {
                        if ( codeFile.get(i).charAt(j) == '(' && !(codeFile.get(i).charAt(j+1) == ')' ) ) {
                            if ( codeFile.get(i).charAt(j+1) != ' ' ) {
                                errorList.add(codeFile.get(i));
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
