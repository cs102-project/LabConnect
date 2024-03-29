package me.labconnect.webapp.models.testing.style;

import me.labconnect.webapp.models.testing.TestResult;
import me.labconnect.webapp.models.testing.Tester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A style check performed for all source files in the submission
 *
 * @author Berk Çakar
 * @author Berkan Şahin
 * @author Alp Ertan
 * @version 30.04.2021
 */
public abstract class StyleChecker implements Tester {

    /**
     * Check the given source file for a particular convention
     *
     * @param codeFile The contents of the current source code file
     * @return An arraylist containing the lines that violate the convention
     */
    protected abstract ArrayList<String> checkFile(ArrayList<String> codeFile);

    /**
     * Perform a style check on all source code files on the given path
     *
     * @param submission The submission path
     * @return A TestResult instance
     * @throws IOException if processing any of the files fails
     */
    @Override
    public TestResult runTest(Path submission) throws IOException {
        List<Path> codeFiles;
        ArrayList<String> offendingLines;

        codeFiles = Files.walk(submission).filter(p -> p.toString().endsWith(".java"))
                .collect(Collectors.toList());
        offendingLines = new ArrayList<>();

        for (Path codeFile : codeFiles) {
            ArrayList<String> currentFileLines;
            ArrayList<String> currentOffendingLines;
            Scanner scan;

            scan = new Scanner(codeFile);
            currentFileLines = new ArrayList<>();

            while (scan.hasNextLine()) {
                currentFileLines.add(scan.nextLine());
            }

            scan.close();
            currentOffendingLines = new ArrayList<>();

            try {
                currentOffendingLines = checkFile(currentFileLines);
            } catch (RuntimeException ex) {
                currentOffendingLines.add("Test failed");

            } finally {
                if (!currentOffendingLines.isEmpty()) {
                    offendingLines.add("In file " + submission.relativize(codeFile) + ":");
                    offendingLines.addAll(currentOffendingLines);
                }

            }
        }

        return new TestResult(this, submission, offendingLines);
    }

    /**
     * Checks if the specified index exist or not in a list
     *
     * @param list  The list which is going to be checked
     * @param index The index which is going to be checked
     * @return {@code true} if index exists, {@code false} otherwise
     */
    public boolean indexExists(final ArrayList<String> list, final int index) {
        return index >= 0 && index < list.size();
    }

    public boolean isNotAComment(String line) {
        return !line.trim().startsWith("//") && !line.trim().startsWith("/*") && !line.trim().startsWith("*");
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}