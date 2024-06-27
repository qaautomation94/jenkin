package com.mscs.emr.automation.functional;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {

    protected static final Logger LOGGER = LogManager.getLogger(SuiteListener.class);

    @Override
    public void onFinish(ISuite suite) {
        LOGGER.info("End " + suite.getName() + " execution");
        try {
            File file = new File("rpscreenshots");
            if (file.exists())
                FileUtils.cleanDirectory(file);
        } catch (IOException e) {
            LOGGER.info("Unable to clean directory 'rpscreenshots'!" + "\n" + e.getMessage());
        }
    }
    
    
    @Override
    public void onStart(ISuite suite) {
        LOGGER.info("Begin " + suite.getName() + " execution");
    }
}
