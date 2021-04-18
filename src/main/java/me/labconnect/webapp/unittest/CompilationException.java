package me.labconnect.webapp.unittest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An Exception thrown when compilation of user code fails
 * 
 * This exception contains a copy of the compiler output, which should
 * be provided as a {@link Path} object during instantiation.
 * @author Berkan Şahin
 * @version 18.04.2021
 */
public class CompilationException extends Exception {
    private static final long serialVersionUID = 1L;

    ArrayList<String> compilerOutput;

    /**
     * Create a new CompilerException from the given compiler output
     * @param output The file containing the compiler's output
     * @throws IOException If reading the output file fails
     */
    public CompilationException(Path output) throws IOException{
        Scanner scan = new Scanner(output);
        while (scan.hasNextLine())
            compilerOutput.add(scan.nextLine());

        scan.close();
    }

    public ArrayList<String> getCompilerOutput() {
        return compilerOutput;
    }

    
    
}
