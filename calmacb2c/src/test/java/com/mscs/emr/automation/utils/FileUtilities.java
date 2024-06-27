package com.mscs.emr.automation.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.XML;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class FileUtilities {
    
    private static final String TMP_FILE_PREFIX = "tmp.";
    
//    public static File getFileInResourcesFolder(String filename) {
//        return new File(Config.getResourcesFolder(), filename);
//    }
    
//    public static File createUniqueFilenameAndCopyFromExistingFileInResourcesFolder(String filename) {
//        return createUniqueFilenameAndCopyFromExistingFile(getFileInResourcesFolder(filename));
//    }
    
    public static File createUniqueFilenameAndCopyFromExistingFile(File srcFile) {
        
        if (!srcFile.exists()) {
            throw new IllegalArgumentException("File " + srcFile.getAbsolutePath() + " does not exist");
        } else if (!srcFile.isFile()) {
            throw new IllegalArgumentException("File " + srcFile.getAbsolutePath() + " is not a regular file");
        }
        
        String filename = srcFile.getName();
        String ext = ".tmp";
        
        // If we can preserve the suffix, let's do it
        int lastDotIndex = filename.lastIndexOf('.');
        int length = filename.length();
        if ((lastDotIndex > 0) && (lastDotIndex < (length-1))) {
            ext = filename.substring(lastDotIndex+1);
        }
        
        try {
            File tmpFile = File.createTempFile(TMP_FILE_PREFIX, ext);
            
            org.apache.commons.io.FileUtils.copyFile(srcFile, tmpFile);
            
            return tmpFile;
            
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create temp file", e);
        }
    }

    //=============================================================================================================

    public static void uploadFileWithRobot(String fileName) {
        String filePath = Config.getResourcesFolderPath() + fileName;
        Robot robot;
        try {
            robot = new Robot();
            Utils.copyPasteRobotActions(robot, filePath);
            Utils.robotPressEnter(robot);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public static List<String[]> readCsvFile(String filePath) throws IOException{
        CSVReader reader = new CSVReader(new FileReader(filePath), ',');
        List<String[]> csvBody = reader.readAll();
        reader.close();
        return csvBody;
    }
    public static void writeCsvFile(String filePath, List<String[]> fileToWrite ) throws IOException{
        CSVWriter writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
        writer.writeAll(fileToWrite);
        writer.flush();
        writer.close();
    }    
    
    public static void createCsvFile(String filename, String[] columns, List<String[]> rows ) throws IOException {
        String filePath = Config.getG2TestResourcesFolder() + filename + "G2TestValues.CSV_FORMAT";
        CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);   
        List<String[]> data = new LinkedList<String[]>();
        data.add(columns);
        for(int i = 0; i < rows.size(); i++) {
            data.add(rows.get(i));               
        }
        csvWriter.writeAll(data); 
        csvWriter.close();        
    }
    
    public static void deleteAFileFromResourcesFolder(String filename) throws IOException{             
        Files.deleteIfExists(Paths.get(Config.getG2TestResourcesFolder() + "\\" + filename)); 
    }

    public static JSONObject convertXMLToJson(String filePath) throws IOException
    {
        return XML.toJSONObject(FileUtils.readFileToString(new File(Config.getResourcesFolderPath() + filePath)));
    }

    public static String loadXMLFile(String filePath)
    {
        String XmlFileData=null;
        try {
            XmlFileData =new String(FileUtils.readFileToString(new File(Config.getResourcesFolderPath() + filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return XmlFileData;
    }
}
