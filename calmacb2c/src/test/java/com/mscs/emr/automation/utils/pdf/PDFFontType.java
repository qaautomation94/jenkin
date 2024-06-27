package com.mscs.emr.automation.utils.pdf;

public enum PDFFontType {

    ARIAL_MT("ArialMT");

    private String fontName;

    PDFFontType(String fontName) {
        this.fontName = fontName;
    }
    
    public String getFontTypeName() {
        return fontName;
    }
}
