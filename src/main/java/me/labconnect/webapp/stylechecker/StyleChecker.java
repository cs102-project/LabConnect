package me.labconnect.webapp.stylechecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class StyleChecker {
   private static final Pattern METHOD_DECLARATION_PATTERN = Pattern.compile( "[\\w *]+[\\w<>\\[\\]]+\\s(\\w+) *\\([^)]*\\) *(\\{?|[^;])" );
   private static final Pattern CLASS_DECLARATION_PATTERN  = Pattern.compile( "[\\w\\s+]+class\\s+(\\w+)" );

   private int fileHeaderLength;
   private int errorCount;

   private String fileName;
   private String fileLines;

   private String[] fileHeader;
   private String[] fileContents;
   private String[] fileLinesArray;

   /**
    * Default constructor that initializes style checker.
    * @param fileName is the name of the file.
    */
   StyleChecker( String fileName ) {
      this.fileName = fileName;

      // Reads into the class variable the entirety of the file contents so we don't have to do it again.
      try {
         // Search for file's javaDoc comment (header).
         fileLines = new Scanner( new File( fileName ) ).useDelimiter( "\\Z" ).next();
         fileLinesArray = fileLines.split( "\\Q/*\\E|\\Q*/\\E" );

         // Assign the file header.
         this.fileHeader = fileLinesArray[ 1 ].split( "\\n" );
         // Set javaDoc comment's length.
         this.fileHeaderLength = this.fileHeader.length + 2;

         // Rest of the fileLinesArray will be the code itself, so set it accordingly.
         this.fileContents = fileLinesArray[ 2 ].split( "\\n" );

      } catch ( ArrayIndexOutOfBoundsException e ) {
         System.out.println( this.fileName + " does not have a proper header." );
      } catch ( FileNotFoundException e ) {
         System.out.println( this.fileName + " not found." );
      }
   }
}