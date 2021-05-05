package me.labconnect.webapp.models.testing.style;

import me.labconnect.webapp.models.testing.Tests;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.labconnect.webapp.models.testing.Tests.CLASS_INTERFACE_NAMING;

/**
 * Checks if the name of the class obeys the conventions
 *
 * @author Borga Haktan Bilen
 * @author Alp Ertan
 * @version 22.04.2021
 */
public class ClassNInterfaceNamingChecker extends StyleChecker {

    /**
     * Checks the file for conventional class or interface naming
     *
     * @param codeFile The file. List of every line.
     * @return The lines that are voiding the convention
     */
    @Override
    protected ArrayList<String> checkFile(ArrayList<String> codeFile) {
        ArrayList<String> errorList = new ArrayList<>();
        String topLevelName;
        String classNameRegexp;
        Pattern compiledPattern;
        Matcher matcher;

        // Checks for correct conventional naming
        classNameRegexp = "^[A-Z][a-zA-Z0-9].*";
        compiledPattern = Pattern.compile(classNameRegexp);
        for (int index = 0; index < codeFile.size(); index++) {
            String currentLine = codeFile.get(index);

            if ((RegexHelper.classRegexMatcher(currentLine) || RegexHelper
                    .interfaceRegexMatcher(currentLine))) {
                topLevelName = nameExtractor(currentLine);
                matcher = compiledPattern.matcher(topLevelName);

                if (!matcher.find() || topLevelName.contains(" ")) {
                    errorList.add(currentLine);
                }
            }
        }

        return errorList;
    }

    /**
     * Extracts the name of the class/interface for checking
     *
     * @param line The line containing the class/interface name
     * @return The name of the class/interface
     */
    private String nameExtractor(String line) {
        String name;
        int startingPosition;
        int endIndex;

        startingPosition = 0;
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

    /**
     * Gets the name of the checker
     *
     * @return Name of the style checker
     */
    @Override
    public String getName() {
        return "Class and Interface naming checker";
    }

    @Override
    public Tests getTestType() {
        return CLASS_INTERFACE_NAMING;
    }
}
