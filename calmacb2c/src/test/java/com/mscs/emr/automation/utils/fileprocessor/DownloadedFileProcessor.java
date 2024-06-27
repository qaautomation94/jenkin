package com.mscs.emr.automation.utils.fileprocessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DownloadedFileProcessor {

    public abstract boolean doProcess(DownloadedFile dlFile);
    
    public final Map<DownloadedFile, Boolean> process(DownloadedFileFolder dlFolder) {
        return process(dlFolder, true);
    }

    private final Map<DownloadedFile, Boolean> process(DownloadedFileFolder dlFolder, boolean doWait) {
        
        if (doWait) {
            dlFolder.waitForFileToBeDownloaded();
            sleepForUserAction("Wait for files to be free to rename");
        }
        
        List<DownloadedFile> dlFiles = dlFolder.getFiles();
        
//        System.out.println("DownloadedFileProcessor.process(): count=" + dlFiles.size());

        Map<DownloadedFile, Boolean> results = new HashMap<DownloadedFile, Boolean>();

        for (DownloadedFile dlFile : dlFiles) {
            results.put(dlFile, process(dlFile));
        }
        
//        System.out.println("DownloadedFileProcessor.process(): done");

        return results;
    }
    
    public final boolean processWithSingleResult(DownloadedFileFolder dlFolder) {
        
        dlFolder.waitForFileToBeDownloaded();
        sleepForUserAction("Wait for files to be free to rename");
        
        Map<DownloadedFile, Boolean> map = process(dlFolder, false);
        return !map.values().contains(Boolean.FALSE);
    }

    public final boolean process(DownloadedFile dlFile) {
        
//        System.out.println("DownloadedFileProcessor.process() single: file=" + dlFile.getPath());

        preprocess(dlFile);
        return doProcess(dlFile);
    }

    private void preprocess(DownloadedFile dlFile) {

        if (!dlFile.isMoved()) {
            dlFile.moveToTempFile();
        }

    }
    
    final static void sleepForUserAction(String msg) {
        
//        System.out.println("Sleeping 20 seconds for user action: "+ msg);
        
        try {
            Thread.sleep(20000);
        } catch (Exception e) {
        }
        
    }

}
