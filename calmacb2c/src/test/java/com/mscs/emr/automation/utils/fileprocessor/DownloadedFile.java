package com.mscs.emr.automation.utils.fileprocessor;

import java.io.File;

import com.mscs.emr.automation.testData.MockDataUtils;

public class DownloadedFile {

    private File file;
    private boolean movedToTempFile;

    DownloadedFile(String filePath) {
        file = new File(filePath);
    }
    
    DownloadedFile(String parentFolderPath, String fileName) {
        file = new File(parentFolderPath, fileName);
    }
    
    String getPath() {
        return file.getAbsolutePath();
    }
    
    String getParentPath() {
        return file.getParentFile().getAbsolutePath();
    }
    
    String getName() {
        return file.getName();
    }
    
    String getNameWithoutExtension() {
        return file.getName().substring(0, file.getName().indexOf('.'));
    }
    
    boolean isMoved() {
        return movedToTempFile;
    }
    
    void moveToTempFile() {
        
        if  (movedToTempFile) {
            throw new IllegalStateException("File already moved to temp file");
        }
        
        String originalName = file.getAbsolutePath();
        
        String path = file.getParentFile().getAbsolutePath();
        String name = file.getName();
        String ext = name.substring(name.indexOf('.'));
        
        String tmpString = MockDataUtils.createUniqueNumber(8);
        String newName = "processedFile-" + tmpString + ext;
        
        File newFile = new File(path, newName);
        
        if (!file.renameTo(newFile)) {
            throw new IllegalStateException("Cannot rename file " + originalName + " to " + newFile.getAbsolutePath());
        }
        
        file = newFile;
        movedToTempFile = true;
    }

}
