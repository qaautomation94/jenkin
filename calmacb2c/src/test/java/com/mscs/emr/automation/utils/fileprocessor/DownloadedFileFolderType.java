package com.mscs.emr.automation.utils.fileprocessor;

public enum DownloadedFileFolderType {
    
    REPORT_SERVLET_PDF("reportservlet", "pdf"),
    FILE_DOWNLOAD_PDF("filedownload", "pdf"),
    CLINICAL_NOTE_PDF("clinicalnote", "pdf"),
    EXCEL_FILE_DOWNLOAD_XLS("excelfiledownload", "xls"),
    FAX_PDF("faxdocument", "pdf");

    private String typeName;
    private String ext;
    
    private DownloadedFileFolderType(String typeName, String ext) {
        this.typeName = typeName;
        this.ext = ext;
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public String getExt() {
        return ext;
    }
}
