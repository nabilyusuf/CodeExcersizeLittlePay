package org.littlepay.coding.exercise;

import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainAppTest {
    private static Logger logger = LogManager.getLogger();
    private static String filePath ="";
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        filePath = System.getProperty("filePath");
        logger.info("Reading config file : " + filePath);
    }
    /**
     * Sample Test to test tap to trips logic
     */
    @Test
    public void testSampledata() {
        MainApp.tappingtoTripsProcessor(filePath,filePath);
    }
}
