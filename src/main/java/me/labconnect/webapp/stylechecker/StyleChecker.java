package me.labconnect.webapp.stylechecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import me.labconnect.webapp.models.Tester;

public abstract class StyleChecker implements Tester {
    private Path submission;
    private ArrayList<String> fileLines;
    private ArrayList<String> errorList;

    /**
    * Default constructor that initializes style checker.
    * @param submission is the lab submisson file.
    */
    StyleChecker( Path submission ) {
        this.submission = submission;
        Scanner submissionScanner;

        try {
            submissionScanner = new Scanner(submission).useDelimiter("\\Z");
            while ( submissionScanner.hasNext() ) {
            fileLines.add( submissionScanner.next() );
        }
        } catch ( FileNotFoundException e ) {
            System.out.println( submission.getFileName() + " not found." );
        } catch( IOException e ) {
            System.out.println( "Error reading " + submission.getFileName() );
        } finally {
        }
    }

    public ArrayList<String> getFileLines() {
        return fileLines;
    }

    private boolean hasNoErrors() {
        if( errorList.isEmpty() ) {
            return true;
        }
        else {
            return false;
        }
    }
}