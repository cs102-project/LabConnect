package me.labconnect.webapp.unittest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;

/**
 * A model of a single unit test, with one tester class and a correct output
 * 
 * The test is done by comparing the output of the tester with the output
 * generated by the example implementation
 * 
 * @author Berkan Şahin
 * @version 21.04.2021
 */
public class UnitTest {

    // Variables
    @Id
    public String id;

    ArrayList<String> correctOutput;
    Path testerClass;
    Long timeLimitInMS;

    // Constructors

    /**
     * Creates a new unit test from the given tester and generates the correct
     * output from the example implementation
     * 
     * @param testerClass          The tester class, which is a singular java file
     *                             with a main method
     * @param exampleImpl          The example implementation supplied by the
     *                             instructor
     * @param timeLimitMiliseconds The maximum time for the unit test in
     *                             miliseconds. A value of 0 denotes no time limit
     * @throws IOException         If an I/O problem is encountered while processing
     *                             the implementation or tester files
     * @throws BadExampleException If the example implementation fails the unit test
     */
    public UnitTest(Path testerClass, Path exampleImpl, long timeLimitMiliseconds)
            throws IOException, BadExampleException {
        TestResult firstResult;
        this.testerClass = testerClass;
        this.timeLimitInMS = timeLimitMiliseconds;

        firstResult = runTest(exampleImpl);
        if (!firstResult.isSuccessful()) {
            throw new BadExampleException(firstResult);
        } else {
            correctOutput = firstResult.getOutput();
        }
    }

    /**
     * Creates a new unit test from the given tester and generates the correct
     * output from the example implementation
     * 
     * @param testerClass The tester class, which is a singular java file with a
     *                    main method
     * @param exampleImpl The example implementation supplied by the instructor
     */
    public UnitTest(Path testerClass, Path exampleImpl) throws IOException, BadExampleException {
        this(testerClass, exampleImpl, 0);
    }

    // Methods

    /**
     * Compiles the tester together with the given source code submission
     * 
     * @param source The directory containing the submission code
     * @return The directory containing the resulting bytecode
     * @throws IOException          If the Path in the parameter is not a directory,
     *                              or if I/O operations on the source directiory
     *                              fail
     * @throws CompilationException If compilation of the provided source code fails
     */
    private Path compileSubmission(Path source) throws IOException, CompilationException {
        Path compilerOutput;
        Path bytecodeDirectory;
        List<String> sourceFiles;
        ArrayList<String> compilerArgs;
        ProcessBuilder compilerBuilder;
        Process compilerProcess;

        if (!Files.isDirectory(source)) {
            throw new IOException();
        }

        Files.copy(testerClass, source.resolve(testerClass));

        // Scan the source path for .java files
        sourceFiles = Files.walk(source).filter(p -> p.getFileName().endsWith(".java")).map(Path::toString)
                .collect(Collectors.toList());

        bytecodeDirectory = Files.createTempDirectory(null);
        compilerOutput = Files.createTempFile("javac-", ".out");

        // Generate the arguments
        compilerArgs = new ArrayList<>();

        compilerArgs.add("javac");

        // Specify output directory (source: javac manpage)
        compilerArgs.add("-d");
        compilerArgs.add(bytecodeDirectory.toString());

        compilerArgs.addAll(sourceFiles);

        compilerBuilder = new ProcessBuilder(compilerArgs);
        compilerBuilder.redirectOutput(compilerOutput.toFile());

        compilerProcess = compilerBuilder.start();

        // Wait until the compiler exits then check its return code
        try {
            if (compilerProcess.waitFor() != 0) {
                throw new CompilationException(compilerOutput);
            }
        } catch (InterruptedException e) {
            throw new CompilationException(compilerOutput); // TODO handle it in a better way?
        } finally {
            Files.deleteIfExists(source.resolve(testerClass));
        }

        return bytecodeDirectory;
    }

    /**
     * Compile and test the given submission against the tester class
     * 
     * @param submission The submission to test
     * @return A TestResult instance
     * @throws IOException If an I/O error occurs while processing the submission
     */
    public TestResult runTest(Path submission) throws IOException {
        Path bytecodeDir;
        Path programOutput;
        ProcessBuilder runtimeBuilder;
        Process runtimeProcess;
        ArrayList<String> runtimeArgs;
        String testerFileName;
        String testerClassName;
        TestState endState;
        Scanner currentOutputScanner;
        boolean finishedInTime;

        // Try to compile the submission
        try {
            bytecodeDir = compileSubmission(submission);
        } catch (CompilationException e) {
            return new TestResult(this, submission, e);
        }

        // Extract class name from tester filename
        testerFileName = testerClass.getFileName().toString();
        testerClassName = testerFileName.substring(0, testerFileName.length() - 5);

        programOutput = Files.createTempFile("runtime-", ".out");

        // Specify the runtime command
        runtimeArgs = new ArrayList<>();
        runtimeArgs.add("java");

        // Add the bytecode dir as a classpath
        runtimeArgs.add("-cp");
        runtimeArgs.add(bytecodeDir.toString());

        runtimeArgs.add(testerClassName);

        runtimeBuilder = new ProcessBuilder(runtimeArgs);
        runtimeBuilder.redirectOutput(programOutput.toFile());

        runtimeProcess = runtimeBuilder.start();

        // Wait until specified time limit or until the process exits
        try {
            if (timeLimitInMS > 0) {
                finishedInTime = runtimeProcess.waitFor(timeLimitInMS, TimeUnit.MILLISECONDS);
            } else {
                runtimeProcess.waitFor();
                finishedInTime = true;
            }
        } catch (InterruptedException e) {
            return new TestResult(this, submission, programOutput, TestState.RUNTIME_ERROR);
        }

        // Determine end state
        if (!finishedInTime) {
            endState = TestState.TIMEOUT;
        } else if (runtimeProcess.exitValue() != 0) {
            endState = TestState.RUNTIME_ERROR;
        } else if (correctOutput == null) { // Check if this is the example run
            endState = TestState.SUCCESS;
        } else {

            // Assume success, change on mismatch
            endState = TestState.SUCCESS;

            currentOutputScanner = new Scanner(programOutput);

            for (String correctLine : correctOutput) {
                if (!currentOutputScanner.hasNextLine()) {
                    endState = TestState.OUTPUT_MISMATCH;
                    break;
                } else if (!correctLine.equals(currentOutputScanner.nextLine())) {
                    endState = TestState.OUTPUT_MISMATCH;
                    break;
                }
            }

            // If both files don't reach EOF at the same time, they are different
            if (currentOutputScanner.hasNextLine()) {
                endState = TestState.OUTPUT_MISMATCH;
            }

            currentOutputScanner.close();
        }

        return new TestResult(this, submission, programOutput, endState);

    }

}
