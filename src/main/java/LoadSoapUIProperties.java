import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

import org.testng.annotations.*;

public class LoadSoapUIProperties {
    private static final String PROJECT_FILE = "C:\\Users\\MohamedBABAOUI\\TNR_BF\\ws_tnr_bf_06_24.xml";
    private static final String INIT_TS = "INIT";
    private static final String INITTC1 = "INITJAVA";
    private static final String INITTC2 = "VALIDATEEP";

    private static final String TESTRUNNER = "C:\\Program Files\\SmartBear\\SoapUI-5.6.1\\bin\\testrunner.bat";


    public String setURL(String URL_Choice)  {

            switch (URL_Choice) {
                case "A5":
                    return"https://esb-ws-a5polo.pvcp.intra:8443/polo-ws/services/WsService?wsdl";

                case "G3":
                    return"http://frccepljbt32.pvcp.intra:8080/polo-ws/services/WsService?wsdl";

                case "T4":
                    return"https://euccepljbt04.pvcp.intra:8443/polo-ws/services/WsService?wsdl";
            }
                    return "Invalid URL choice";

    }



    @BeforeTest
    public void setUp() throws IOException {
        String dBegin = System.getProperty("dBegin");
        String smart14 = System.getProperty("smart14");
      /*  String smart7 = System.getProperty("smart7");
        String smart2 = System.getProperty("smart2");
        String mydate = System.getProperty("mydate");
        String SMART30 = System.getProperty("SMART30");
        String BOOKING_DATE_SMART30 = System.getProperty("BOOKING_DATE_SMART30");
        String SMART14 = System.getProperty("SMART14");
        String BOOKING_DATE_SMART14 = System.getProperty("BOOKING_DATE_SMART14");
        String SMART7 = System.getProperty("SMART7");
        String BOOKING_DATE_SMART7 = System.getProperty("BOOKING_DATE_SMART7");
        String SMART2 = System.getProperty("SMART2");
        String BOOKING_DATE_SMART2 = System.getProperty("BOOKING_DATE_SMART2");
        String BASIC = System.getProperty("BASIC");
        String BOOKING_DATE_BASIC = System.getProperty("BOOKING_DATE_BASIC");
        String Date_NBF = System.getProperty("Date_NBF");
        String BOOKING_SITE_SMART30 = System.getProperty("BOOKING_SITE_SMART30");
        String BOOKING_SERVICE_SMART30 = System.getProperty("BOOKING_SERVICE_SMART30");
        String BOOKING_SERVICE02_SMART30 = System.getProperty("BOOKING_SERVICE02_SMART30");
        String BOOKING_SITE_SMART14 = System.getProperty("BOOKING_SITE_SMART14");
        String BOOKING_SERVICE_SMART14 = System.getProperty("BOOKING_SERVICE_SMART14");
        String BOOKING_SERVICE02_SMART14 = System.getProperty("BOOKING_SERVICE02_SMART14");
        String BOOKING_SITE_SMART07 = System.getProperty("BOOKING_SITE_SMART07");
        String BOOKING_SERVICE_SMART07 = System.getProperty("BOOKING_SERVICE_SMART07");
        String BOOKING_SERVICE02_SMART07 = System.getProperty("BOOKING_SERVICE02_SMART07");
        String BOOKING_SITE_SMART2 = System.getProperty("BOOKING_SITE_SMART2");
        String BOOKING_SERVICE_SMART2 = System.getProperty("BOOKING_SERVICE_SMART2");
        String BOOKING_SERVICE02_SMART2 = System.getProperty("BOOKING_SERVICE02_SMART2");
        String BOOKING_SITE_BASIC = System.getProperty("BOOKING_SITE_BASIC");
        String BOOKING_SERVICE_BASIC = System.getProperty("BOOKING_SERVICE_BASIC");
        String BOOKING_SERVICE02_BASIC = System.getProperty("BOOKING_SERVICE02_BASIC");
        String SITE_NBF = System.getProperty("SITE_NBF");
        String SERVICE_NBF = System.getProperty("SERVICE_NBF");
        String DateSmart = System.getProperty("DateSmart");*/
        String URL = System.getProperty("URL");

        String URL_Link= setURL(URL);
        Properties properties = new Properties();
        properties.setProperty("dBegin", dBegin);
        properties.setProperty("smart14", smart14);
   /*     properties.setProperty("smart7", smart7);
        properties.setProperty("smart2", smart2);
        properties.setProperty("mydate", mydate);
        properties.setProperty("SMART30", SMART30);
        properties.setProperty("BOOKING_DATE_SMART30", BOOKING_DATE_SMART30);
        properties.setProperty("SMART14", SMART14);
        properties.setProperty("BOOKING_DATE_SMART14", BOOKING_DATE_SMART14);
        properties.setProperty("SMART7", SMART7);
        properties.setProperty("BOOKING_DATE_SMART7", BOOKING_DATE_SMART7);
        properties.setProperty("SMART2", SMART2);
        properties.setProperty("BOOKING_DATE_SMART2", BOOKING_DATE_SMART2);
        properties.setProperty("BASIC", BASIC);
        properties.setProperty("BOOKING_DATE_BASIC", BOOKING_DATE_BASIC);
        properties.setProperty("Date_NBF", Date_NBF);
        properties.setProperty("BOOKING_SITE_SMART30", BOOKING_SITE_SMART30);
        properties.setProperty("BOOKING_SERVICE_SMART30", BOOKING_SERVICE_SMART30);
        properties.setProperty("BOOKING_SERVICE02_SMART30", BOOKING_SERVICE02_SMART30);
        properties.setProperty("BOOKING_SITE_SMART14", BOOKING_SITE_SMART14);
        properties.setProperty("BOOKING_SERVICE_SMART14", BOOKING_SERVICE_SMART14);
        properties.setProperty("BOOKING_SERVICE02_SMART14", BOOKING_SERVICE02_SMART14);
        properties.setProperty("BOOKING_SITE_SMART07", BOOKING_SITE_SMART07);
        properties.setProperty("BOOKING_SERVICE_SMART07", BOOKING_SERVICE_SMART07);
        properties.setProperty("BOOKING_SERVICE02_SMART07", BOOKING_SERVICE02_SMART07);
        properties.setProperty("BOOKING_SITE_SMART2", BOOKING_SITE_SMART2);
        properties.setProperty("BOOKING_SERVICE_SMART2", BOOKING_SERVICE_SMART2);
        properties.setProperty("BOOKING_SERVICE02_SMART2", BOOKING_SERVICE02_SMART2);
        properties.setProperty("BOOKING_SITE_BASIC", BOOKING_SITE_BASIC);
        properties.setProperty("BOOKING_SERVICE_BASIC", BOOKING_SERVICE_BASIC);
        properties.setProperty("BOOKING_SERVICE02_BASIC", BOOKING_SERVICE02_BASIC);
        properties.setProperty("SITE_NBF", SITE_NBF);
        properties.setProperty("SERVICE_NBF", SERVICE_NBF);
        properties.setProperty("DateSmart", DateSmart);*/
        properties.setProperty("URL", URL_Link);

        try (OutputStream output = new FileOutputStream("C:\\Users\\MohamedBABAOUI\\TNR_BF\\soapui.ini")) {
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Test
    public void initialization() throws InterruptedException  {
        Thread.sleep(3000);
        runsuite(INIT_TS,INITTC1);
    }

    private static void runsuite(String suiteName,String testcaseName) throws InterruptedException {
        try {
            System.out.println("Running single test suite: " + suiteName);

            // Construct the ProcessBuilder command
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "\"" + TESTRUNNER + "\"",
                    "-s" + suiteName,
                    "-c" + testcaseName,
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

        } catch (Exception e) {
            // Handle any exception without blocking
            handleError(e);
        }
    }

    private static void logProcessOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Log the output
            }
        } catch (IOException e) {
            handleError(e);  // Redirect error handling
        }
    }

    private static void handleError(Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        e.printStackTrace();
    }

    @Test(dependsOnMethods = {"initialization"})
    public void test() throws InterruptedException {
        Thread.sleep(5000);
        runsuite(INIT_TS,INITTC2);

    }
    @AfterTest
    public void tearDown() {
        System.out.println("DONE TESTING");
    }
}
