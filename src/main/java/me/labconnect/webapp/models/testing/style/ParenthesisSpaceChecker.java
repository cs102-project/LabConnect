package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * LoopParanthesisSpaceChecker
 */
public class ParenthesisSpaceChecker extends StyleChecker {

    @Override
    public String getName() {
        return "Parenthesis space checker";
    }

    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();

        for (int i = 0; i < codeFile.size(); i++) {
            if (RegexHelper.forRegexMatcher(codeFile.get(i)) || RegexHelper.ifRegexMatcher(codeFile.get(i)) ||
                RegexHelper.elseifRegexMatcher(codeFile.get(i)) || RegexHelper.whileRegexMatcher(codeFile.get(i)) ) {
                for (int j = 0; j < codeFile.get(i).length(); j++) {
                    if (codeFile.get(i).charAt(j) == '(' && codeFile.get(i).charAt(j-1) != ' ' && codeFile.get(i).charAt(j+1) != ' ') {
                        errorList.add(codeFile.get(i));
                    } else if (codeFile.get(i).charAt(j) == ')' && codeFile.get(i).charAt(j-1) != ' ') {
                        errorList.add(codeFile.get(i));
                    }
                }
            }
        }
        return errorList;
    }
}