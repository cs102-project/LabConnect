package me.labconnect.webapp.stylechecker;

import java.util.ArrayList;

public class LoopCurlyBracketsChecker extends StyleChecker {

    @Override
    protected ArrayList<String> checkFile( ArrayList<String> codeFile ) {
        ArrayList<String> errorList = new ArrayList<>();

        for ( int i = 0; i < codeFile.size(); i++ ) {
            if ( RegexHelper.whileRegexMatcher( codeFile.get( i ) ) ) {
                if ( !codeFile.get( i ).contains("{") && !codeFile.get( i + 1 ).contains("{") ) {
                    errorList.add( codeFile.get( i ) );
                }
            }
            else if ( RegexHelper.forRegexMatcher( codeFile.get( i ) ) ) {
                if ( !codeFile.get( i ).contains("{") && !codeFile.get( i + 1 ).contains("{") ) {
                    errorList.add( codeFile.get( i ) );
                }
            }
            else if ( RegexHelper.doRegexMatcher( codeFile.get( i ) ) ) {
                if ( !codeFile.get( i ).contains("{") && !codeFile.get( i + 1 ).contains("{") ) {
                    errorList.add( codeFile.get( i ) );
                }
            }
        }

        return errorList;
    }
}
