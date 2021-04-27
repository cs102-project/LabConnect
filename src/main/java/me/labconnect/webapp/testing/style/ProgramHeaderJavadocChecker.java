package me.labconnect.webapp.testing.style;

import java.util.ArrayList;

/**
 * Check if the header javadoc comment is written correctly.
 * 
 * @author Borga Haktan Bilen
 * @version 27.04.2021
 */
public class ProgramHeaderJavadocChecker extends StyleChecker {

    /**
     * Checks if the header javadoc comment is written correctly.
     * 
     * @param codeFile The file. List of every line.
     * @return List of warning messages about the header.
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        String headerBlockSingleLine = "";
        ArrayList<String> headerBlock;
        ArrayList<String> errorList = new ArrayList<>();
        int commentIndices[];

        commentIndices = indexFinder(codeFile);
        headerBlock = new ArrayList<>(codeFile.subList(commentIndices[0], commentIndices[1] + 1));

        for (String line : headerBlock) {
            headerBlockSingleLine = headerBlockSingleLine + line;
        }
        if (RegexHelper.javadocCommentBlockRegexMatcher(headerBlockSingleLine)) {
            if (!headerBlockSingleLine.contains("@author")) {
                errorList.add("Missing or incorrect author (@author) information in the header");
            }
            if (!headerBlockSingleLine.contains("@version")) {
                errorList.add("Missing or incorrect version (@version) information in the header");
            }
        } else {
            errorList.add("Header javadoc comment block is missing or incorrect in the beginning of the program");
        }
        return errorList;
    }

    /**
     * Finds the indices of the header javadoc comment block.
     * 
     * @param codeFile The file. List of every line.
     * @return The indices of the header javadoc comment block.
     */
    private static int[] indexFinder(ArrayList<String> codeFile) {
        int startIndex = 0;
        int endIndex = 0;

        for (int classIndex = 0; !RegexHelper.classRegexMatcher(codeFile.get(classIndex))
                && !RegexHelper.interfaceRegexMatcher(codeFile.get(classIndex)); classIndex++) {
            if (codeFile.get(classIndex).contains("/**")) {
                startIndex = classIndex;
            } else if (codeFile.get(classIndex).contains("*/")) {
                endIndex = classIndex;
            }
        }
        int indices[] = { startIndex, endIndex };
        return indices;
    }

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Program header javadoc checker";
    }
}
