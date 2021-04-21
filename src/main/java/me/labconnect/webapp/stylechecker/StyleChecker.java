package me.labconnect.webapp.stylechecker;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StyleChecker {
   private static final Pattern METHOD_DECLARATION_PATTERN = Pattern.compile( "[\\w *]+[\\w<>\\[\\]]+\\s(\\w+) *\\([^)]*\\) *(\\{?|[^;])" );
   private static final Pattern CLASS_DECLARATION_PATTERN  = Pattern.compile( "[\\w\\s+]+class\\s+(\\w+)" );

   private int fileHeaderLength;
   private int errorCount;

   private Path submission;
   private String fileLines;

   private String[] fileHeader;
   private String[] fileContents;
   private String[] fileLinesArray;

   /**
    * Default constructor that initializes style checker.
    * @param fileName is the name of the file.
    */
   StyleChecker( Path submission ) {
      Scanner submissionScanner;
      this.submission = submission;

      // Reads into the class variable the entirety of the file contents so we don't have to do it again.
      try {

         submissionScanner = new Scanner(submission).useDelimiter("\\Z");

         // Search for file's javaDoc comment (header).
         fileLines = submissionScanner.next();
         fileLinesArray = fileLines.split( "\\Q/*\\E|\\Q*/\\E" );

         // Assign the file header.
         this.fileHeader = fileLinesArray[ 1 ].split( "\\n" );
         // Set javaDoc comment's length.
         this.fileHeaderLength = this.fileHeader.length + 2;

         // Rest of the fileLinesArray will be the code itself, so set it accordingly.
         this.fileContents = fileLinesArray[ 2 ].split( "\\n" );

         submissionScanner.close();

      } catch ( ArrayIndexOutOfBoundsException e ) {
         System.out.println( submission.getFileName() + " does not have a proper header." );
      } catch ( IOException e ) {
         System.out.println( submission.getFileName() + " not found." );
      } finally {
      }
   }
}