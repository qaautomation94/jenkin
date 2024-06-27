package com.mscs.emr.automation.utils.fileprocessor;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import com.mscs.emr.automation.functional.DriverManager;

public class DownloadedFileFolder {
    
    private String folderPath;
    private String filePattern;
    private String ext;
    
    DownloadedFileFolder(String folderPath, String filePattern, String ext) {
        this.folderPath = folderPath;
        this.filePattern = filePattern;
        this.ext = ext;
    }
    
    public String getFolderPath() {
        return folderPath;
    }
    
    public String getFilePattern() {
        return filePattern;
    }
    
    public String getExt() {
        return ext;
    }
    
    public List<DownloadedFile> getFiles() {
        return getFiles(folderPath, filePattern, ext);
    }

    private List<DownloadedFile> getFiles(String folderPath, String filePattern, String ext) {

        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[] { String.format("%s*%s", filePattern, ext) });
        scanner.setBasedir(folderPath);
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();

        List<DownloadedFile> dlFiles = new ArrayList<DownloadedFile>();

        for (String file : files) {
            dlFiles.add(new DownloadedFile(folderPath, file));
        }

        return dlFiles;
    }
    
    /**
     * Wait for file to appear.  This assumes configured filename is a filename and not a pattern.
     */
    public final void waitForFileToBeDownloaded() {
        waitForFileToBeDownloaded(folderPath, filePattern + ext);
    }
    
    public static final void waitForFileToBeDownloaded(String parentPath, String filename) {
        
        File file = new File(parentPath, filename);
       
//        System.out.println("waitForFileToBeDownloaded: entered, file=" + file.getAbsolutePath());
        
        FluentWait<WebDriver> wait = new FluentWait<>(DriverManager.getDriver()).withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1));
        wait.until((WebDriver wd) -> file.exists());
        
//        System.out.println("waitForFileToBeDownloaded: " + file.getAbsolutePath() + " " + file.exists());
    }

    public static final DownloadedFileFolder parseDownloadedFileFolderConfig(String config) {
        
        String[] atoms = config.split("\\|");
        
        if ((atoms == null) || (atoms.length != 3)) {
            throw new IllegalStateException("DownloadedFileFolder config string not formatted correctly: " + config);
        }
        
        return new DownloadedFileFolder(atoms[0], atoms[1], atoms[2]);
    }

}
