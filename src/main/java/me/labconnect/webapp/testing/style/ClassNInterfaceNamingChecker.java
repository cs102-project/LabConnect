package me.labconnect.webapp.testing.style;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks if the name of the class obeys the conventions
 * 
 * @author Borga Haktan Bilen
 * @version 22.04.2021
 */
public class ClassNInterfaceNamingChecker extends StyleChecker {

    /**
     * Checks the file for conventiınal class or interface naming
     * 
     * @param codeFile The file, line by line list
     * @return The lines that are voiding the convention
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();
        String name;
        String regex;
        Pattern pattern;
        Matcher matcher;

        // Checks for correct conventional naming
        regex = "^[A-Z][a-zA-Z0-9].*";
        pattern = Pattern.compile(regex);
        for (int index = 0; index < codeFile.size(); index++) {
            String currentLine = codeFile.get(index);

            if ((RegexHelper.classRegexMatcher(currentLine) || RegexHelper.interfaceRegexMatcher(currentLine))) {
                name = nameExtractor(currentLine);
                matcher = pattern.matcher(name);

                if (!matcher.find() || name.contains(" ")) {
                    errorList.add(currentLine);
                }
            }
        }

        return errorList;
    }

    /**
     * Exctracts the name of the class/interface for checking
     * 
     * @param line The line containing the class/interface name
     * @return The name of the class/interface
     */
    private String nameExtractor(String line) {
        String name;
        int startingPosition;
        int endIndex;

        startingPosition = 0;
        endIndex = 0;
        if (line.contains("class")) {
            startingPosition = line.indexOf("class") + 6;
        } else if (line.contains("interface")) {
            startingPosition = line.indexOf("interface") + 10;
        }

        if (line.contains("extends")) {
            endIndex = line.indexOf("extends") - 1;
        } else if (line.contains("implements")) {
            endIndex = line.indexOf("implements") - 1;
        } else {
            endIndex = line.indexOf("{") - 1;
        }

        name = line.substring(startingPosition, endIndex);
        return name;
    }

    @Override
    public String getName() {
        return "Class and Interface naming checker";
    }
}