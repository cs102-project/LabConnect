package me.labconnect.webapp.testing.style;

import java.util.ArrayList;

/**
 * Check if the source code is indented correctly
 * 
 * @author Berk Çakar
 * @author Vedat Eren Arıcan
 * @version 23.04.2021
 */
public class IndentationChecker extends StyleChecker {

    private int countLeadingSpaces( String line ) {
        int spaceCount = 0;

        if ( line.length() > 0 ) {
            for ( int c = 0; ( line.charAt( c ) == ' ' ) && ( c < line.length() - 1 ); c++ ) {
                spaceCount++;
            }
        }
        else {
            return 0;
        }

        return spaceCount;
    }

    @Override
    protected ArrayList<String> checkFile( ArrayList<String> codeFile ) {
        ArrayList<String> errorList = new ArrayList<>();
        int properIndent = 0;

        for (int ln = 0; ln < codeFile.size(); ln++) {
           int foundIndent = countLeadingSpaces( codeFile.get( ln ) );

            // Before checking the current line for indentation, check to see if it should be indented less.
            // If it closes a body of some kind, it should be indented less.
            if (codeFile.get( ln ).contains( "}" ) && !codeFile.get( ln ).contains( "{" ) ) {
                properIndent -= 4;
            }

            // Otherwise, check if the indentation is the proper number of spaces.
            else if ( foundIndent != properIndent && codeFile.get( ln ).trim().length() > 0 && codeFile.get( ln ).trim().startsWith( "" ) ) {
                errorList.add( codeFile.get( ln ) );
            }

            // After checking the current line, check to see if the next line should be indented more.
            // If it opens a body of some kind, it should be indented more.
            if ( codeFile.get( ln ).contains( "{" ) && !codeFile.get( ln ).contains( "}" ) ) {
                properIndent += 4;
            }
        }

        return errorList;
    }

    @Override
    public String getName() {
        return "Indentation checker";
    }
}
