package me.labconnect.webapp.testing.style;

import java.util.ArrayList;

public class ConstantNamingChecker extends StyleChecker {

    @Override
    protected ArrayList<String> checkFile( ArrayList<String> codeFile ) {
        ArrayList<String> errorList = new ArrayList<>();

        for ( int i = 0; i < codeFile.size(); i++ ) {
            if ( RegexHelper.constantRegexMatcher( codeFile.get( i ) ) ) {
                if ( isAllCaps( extractConstant( codeFile.get( i ) ) ) == false ) {
                    errorList.add( codeFile.get( i ) );
                }
            }
        }

        return errorList;
    }

    // seems unnecessary
    // private int getStartPos( String line ) {
    //     int startPos = 0;
    //     String[] identifiers = { "int", "double", "float", "long", "String", "Character", "char", "Integer",
    //                              "Double", "Float", "Long", "Boolean", "boolean", "Byte", "byte", "Short", "short" };

    //     for ( int i = 0; i < identifiers.length; i++ ) {
    //         if(line.contains(identifiers[i])){
    //             startPos = line.indexOf(identifiers[i]);
    //         }
    //     }

    //     return startPos;
    // }

    private int getEndPos( String line ) {
        int endPos = 0;
        String[] identifiers = { "int", "double", "float", "long", "String", "Character", "char", "Integer",
                                 "Double", "Float", "Long", "Boolean", "boolean", "Byte", "byte", "Short", "short" };


        for ( int i = 0; i < identifiers.length; i++ ) {
            if( line.contains( identifiers[ i ] ) ) {
                endPos = line.indexOf( identifiers[ i ] ) + identifiers[ i ].length();
            }
        }

        return endPos;
    }

    private String extractConstant( String line ) {
        String constant = "";
        for ( int i = getEndPos( line ); i < line.length(); i++ ) {
                constant += line.charAt( i );
        }

        return constant;
    }

    private boolean isAllCaps( String constant ){
        if( !constant.equals( constant.toUpperCase() ) ) {
            return false;
        }
        else {
            return true;
        }
    }
}