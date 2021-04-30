package me.labconnect.webapp.models.testing.style;

import java.util.ArrayList;

/**
 * Check if the source code is indented correctly
 *
 * @author Berk Çakar
 * @author Vedat Eren Arıcan
 * @version 23.04.2021
 */
public class IndentationChecker extends StyleChecker {

    /**
     * Counts the leading spaces of a line
     *
     * @param line A line containing a statement
     * @return Number of leading spaces
     */
    private int countLeadingSpaces(String line) {
        int spaceCount = 0;

        if (line.length() > 0) {
            for (int leadingIndex = 0; (line.charAt(leadingIndex) == ' ')
                    && (leadingIndex < line.length() - 1); leadingIndex++) {
                spaceCount++;
            }
        } else {
            return 0;
        }

        return spaceCount;
    }

    /**
     * Checks every line in a file for indentation errors.
     *
     * @param codeFile The file. List of every line.
     * @return List of lines that indentation violation occured
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();
        int properIndent = 0;

        for (int line = 0; line < codeFile.size(); line++) {
                int foundIndent = countLeadingSpaces(codeFile.get(line));

                // Before checking the current line for indentation, check to see if it should
                // be indented less.
                // If it closes a body of some kind, it should be indented less.
                if (codeFile.get(line).contains("}") && !codeFile.get(line).contains("{")) {
                    properIndent -= 4;
                }

                // Otherwise, check if the indentation is the proper number of spaces.
                else if (foundIndent != properIndent && codeFile.get(line).trim().length() > 0
                        && codeFile.get(line).trim().startsWith("")) {
                    errorList.add(codeFile.get(line));
                }

                // After checking the current line, check to see if the next line should be
                // indented more.
                // If it opens a body of some kind, it should be indented more.
                if (codeFile.get(line).contains("{") && !codeFile.get(line).contains("}")) {
                    properIndent += 4;
                }
            }
        return errorList;
    }

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Indentation checker";
    }
}
