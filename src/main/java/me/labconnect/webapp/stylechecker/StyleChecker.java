package me.labconnect.webapp.stylechecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.labconnect.webapp.models.Tester;
import me.labconnect.webapp.unittest.TestResult;

/**
 * A style check performed for all source files in the submission
 * 
 * @author Berk Çakar
 * @author Berkan Şahin
 * @version 21.04.2021
 */
public abstract class StyleChecker implements Tester {

   /**
    * Check the given source file for a particular convention
    * 
    * @param codeFile The source code file
    * @return An arraylist containing the lines that violate the convention
    * @throws IOException If reading the file fails
    */
   protected abstract ArrayList<String> checkFile(Path codeFile) throws IOException;

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

      codeFiles = Files.walk(submission).filter(p -> p.endsWith(".java")).collect(Collectors.toList());
      offendingLines = new ArrayList<>();

      for (Path codeFile : codeFiles) {
         offendingLines.addAll(checkFile(codeFile));
      }

      return new TestResult(this, submission, offendingLines);
   }
}