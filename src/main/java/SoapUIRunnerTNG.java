import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SoapUIRunnerTNG {

    // Path to testrunner.bat
    private static final String TESTRUNNER = "C:\\Program Files\\SmartBear\\SoapUI-5.6.1\\bin\\testrunner.bat";

    // Path to SoapUI project file
    private static final String PROJECT_FILE = "C:\\Users\\MohamedBABAOUI\\TNR_BF\\ws_tnr_bf_06_24.xml";

    // Base output directory
    private static final String OUTPUT_DIR_BASE = "C:\\Users\\MohamedBABAOUI\\Reports";

    // Path to the file containing test suite names
    private static final String TEST_SUITE_NAMES_FILE = "C:\\Users\\MohamedBABAOUI\\TestSuitesNames\\TSN.txt";

    private static Map<Integer, String> testSuitesMap = new HashMap<>();

@BeforeTest
    public void setUp() throws Exception {
        String extractSuiteName = "ExtractTS";
        System.out.println("Test suites are loading...");

        if (runTestSuite(extractSuiteName, true)) {
            System.out.println("Test suites loaded successfully.");

            // Load and print the test suite names
            loadTestSuites();

            if (testSuitesMap.isEmpty()) {
                System.out.println("No test suites found. Exiting.");
                return;  // Exit if no test suites are loaded
            }

            printTestSuites();
        }else{
            System.out.println("Failed to run test suite: " + extractSuiteName);
        }
    }


    @Test
    public void runSoapUITests() throws IOException, InterruptedException {
        // Get the choice from system properties
        String choiceProp = System.getProperty("choice");
        int choice = (choiceProp != null) ? Integer.parseInt(choiceProp) : 0;

        // Execute actions based on system property choice
        switch (choice) {
            case 1:
                String suiteName = System.getProperty("suiteName");
                if (suiteName != null && testSuitesMap.containsValue(suiteName)) {
                    runInitTestSuite();  // Run INIT before the selected test suite
                    runSingleSuite(suiteName);
                } else {
                    System.out.println("Invalid or missing test suite name.");
                }
                break;

            case 2:
                String suiteNames = System.getProperty("suiteNames");
                if (suiteNames != null) {
                    String[] suiteArray = suiteNames.split(",");
                    for (String name : suiteArray) {
                        String trimmedName = name.trim();
                        if (testSuitesMap.containsValue(trimmedName)) {
                            runInitTestSuite();  // Run INIT before executing each test suite
                            runSingleSuite(trimmedName);
                        } else {
                            System.out.println("Invalid test suite name: " + trimmedName);
                        }
                    }
                } else {
                    System.out.println("Missing test suite names.");
                }
                break;


            case 3:
                // Execute all test suites
                runInitTestSuite();  // Run INIT before executing all test suites
                for (String testSuite : testSuitesMap.values()) {
                    runInitTestSuite();
                    runSingleSuite(testSuite);
                }
                break;

            default:
                System.out.println("Invalid choice. Please provide a valid choice through system properties.");
                break;
        }

    }
@AfterTest
    public void tearDown() {
        // Perform any necessary cleanup here
    System.out.println("Tear down");
    }

    /*public static void main(String[] args) {
        try {
            // Run the extract test suite to update TSN.txt
            String extractSuiteName = "ExtractTS";
            System.out.println("Test suites are loading...");

            if (runTestSuite(extractSuiteName, true)) {
                System.out.println("Test suites loaded successfully.");

                // Load and print the test suite names
                loadTestSuites();

                if (testSuitesMap.isEmpty()) {
                    System.out.println("No test suites found. Exiting.");
                    return;  // Exit if no test suites are loaded
                }

                printTestSuites();

                // Get the choice from system properties
                String choiceProp = System.getProperty("choice");
                int choice = (choiceProp != null) ? Integer.parseInt(choiceProp) : 0;

                // Execute actions based on system property choice
                switch (choice) {
                    case 1:
                        String suiteName = System.getProperty("suiteName");
                        if (suiteName != null && testSuitesMap.containsValue(suiteName)) {
                            runInitTestSuite();  // Run INIT before the selected test suite
                            runSingleSuite(suiteName);
                        } else {
                            System.out.println("Invalid or missing test suite name.");
                        }
                        break;

                    case 2:
                        String suiteNames = System.getProperty("suiteNames");
                        if (suiteNames != null) {
                            String[] suiteArray = suiteNames.split(",");
                            runInitTestSuite();  // Run INIT before executing the set of test suites
                            for (String name : suiteArray) {
                                String trimmedName = name.trim();
                                if (testSuitesMap.containsValue(trimmedName)) {
                                    runSingleSuite(trimmedName);
                                } else {
                                    System.out.println("Invalid test suite name: " + trimmedName);
                                }
                            }
                        } else {
                            System.out.println("Missing test suite names.");
                        }
                        break;

                    case 3:
                        // Execute all test suites
                        runInitTestSuite();  // Run INIT before executing all test suites
                        for (String testSuite : testSuitesMap.values()) {
                            runSingleSuite(testSuite);
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Please provide a valid choice through system properties.");
                        break;
                }
            } else {
                System.out.println("Failed to run test suite: " + extractSuiteName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Execution complete.");
        }
    }
*/

    // Method to run the INIT test suite
    private static void runInitTestSuite() throws IOException, InterruptedException {
        String initSuiteName = "INIT";

        System.out.println("Running the INIT test suite: " + initSuiteName);
        if (runTestSuite(initSuiteName, false)) {
            System.out.println("Test suite " + initSuiteName + " executed successfully.");
        } else {
            System.out.println("Failed to run test suite: " + initSuiteName);
        }
    }

    // Method to load test suite names from file into a map, excluding "INIT"
    private static void loadTestSuites() throws IOException {
        testSuitesMap.clear();  // Clear any existing entries
        int index = 1;
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_SUITE_NAMES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String name = parts[1].trim();

                    // Check if the name starts and ends with quotes
                    if (name.startsWith("\"") && name.endsWith("\"")) {
                        // Remove the quotes around the name
                        name = name.substring(1, name.length() - 1);
                    }

                    // Exclude INIT test suite
                    if (!name.equalsIgnoreCase("INIT")) {
                        testSuitesMap.put(index, name);
                        index++;
                    }
                }
            }
        }
    }

    // Method to print test suite names to the console
    private static void printTestSuites() {
        System.out.println("Available Test Suites:");
        for (Map.Entry<Integer, String> entry : testSuitesMap.entrySet()) {
            System.out.println("Test Suite " + entry.getKey() + ": " + entry.getValue());
        }
    }

    // Method to run a test suite and optionally suppress detailed output
    private static boolean runTestSuite(String suiteName, boolean suppressOutput) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "\"" + TESTRUNNER + "\"",
                "-s" + suiteName,
                "-r", "-a", "-j",
                "-f" + OUTPUT_DIR_BASE,
                PROJECT_FILE
        );
        Process process = builder.start();

        if (!suppressOutput) {
            // If not suppressing output, log the output and error streams
            logProcessOutput(process);
        } else {
            // If suppressing output, only log errors if there are any
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            boolean hasError = false;
            while ((line = errorReader.readLine()) != null) {
                hasError = true;
                System.err.println(line);
            }
            if (hasError) {
                return false;  // Return false if there were errors
            }
        }

        return process.waitFor() == 0;  // Return true if the process finished successfully
    }


    // Method to run a single test suite as a subroutine
    // Global error handling method
    private static void handleError(Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        e.printStackTrace();
    }

    // Method to run the test suite with error handling
    private static void runSingleSuite(String suiteName) {
        try {
            System.out.println("Running single test suite: " + suiteName);

            // Quote the suiteName if it contains spaces
            String quotedSuiteName = suiteName.contains(" ") ? "\"" + suiteName + "\"" : suiteName;

            // Construct the ProcessBuilder command
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "\"" + TESTRUNNER + "\"",
                    "-s" + quotedSuiteName,  // Use the quoted suiteName
                    "-r", "-a", "-j",
                    "-f" + "C:\\Users\\MohamedBABAOUI\\Reports\\" + suiteName,
                    PROJECT_FILE
            );
            builder.redirectErrorStream(true);  // Redirect error stream to the output stream
            Process process = builder.start();

            // Capture and log output and errors
            logProcessOutput(process);

            // Wait for the process to complete
            int exitCode = process.waitFor();

            // Log the result of the test suite execution
            if (exitCode == 0) {
                System.out.println("Test suite " + suiteName + " executed successfully.");
            } else {
                System.out.println("Test suite " + suiteName + " failed with errors.");
            }

            // After running the test suite, open the latest HTML report
            openLatestReport(suiteName);

        } catch (Exception e) {
            // Handle any exception without blocking
            handleError(e);
        }
    }

    // Method to open the latest HTML report after the suite execution
    private static void openLatestReport(String suiteName) {
        try {
            // Directory where reports are stored
            Path reportsDir = Paths.get(OUTPUT_DIR_BASE);

            // Find the latest HTML report file that starts with the test suite name
            Optional<Path> latestReport = Files.list(reportsDir)
                    .filter(path -> path.toString().endsWith(".html") && path.getFileName().toString().startsWith(suiteName))  // Only consider .html files starting with suiteName
                    .max(Comparator.comparingLong(path -> path.toFile().lastModified()));

            if (latestReport.isPresent()) {
                File htmlFile = latestReport.get().toFile();
                System.out.println("Opening report: " + htmlFile.getAbsolutePath());

                // Command to open the file using Chrome
                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "start chrome \"" + htmlFile.getAbsolutePath() + "\"");
                builder.start();
            } else {
                System.out.println("No HTML report found for the test suite " + suiteName);
            }
        } catch (Exception e) {
            handleError(e);  // Handle any exception without blocking
        }
    }

    // Utility method to log process output and error streams
    private static void logProcessOutput(Process process) throws IOException {
        // Use try-with-resources to auto-close streams
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            String line;
            // Log output stream
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Log error stream
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
        }
    }
}

