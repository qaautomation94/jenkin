package com.mscs.emr.automation.utils.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.TextPosition;

public class TextVerifyPDFProcessor {

    private String[] searchStrings;

    public TextVerifyPDFProcessor(String... searchStrings) {
        this.searchStrings = searchStrings;
    }

    public boolean doProcess(String pdfContext) {
        try {
            return verifyPDFContainsAllStrings(pdfContext);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }

    public boolean doProcess(PDFFontType font, String pdfContext, List<List<TextPosition>> pdfCharactersByArticle) {
        try {
            return verifyPDFFontForStrings(font, pdfContext, pdfCharactersByArticle);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }

    public boolean doProcess(int size, String pdfContext, List<List<TextPosition>> pdfCharactersByArticle) {
        try {
            return verifyPDFContentSizeForStrings(size, pdfContext, pdfCharactersByArticle);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }

    private boolean verifyPDFContainsAllStrings(String pdfContext) {
        String normalizedReportString = StringUtils.deleteWhitespace(pdfContext);

        for (String aSearchString : searchStrings) {
            String searchString = StringUtils.deleteWhitespace(aSearchString);
            if (!StringUtils.containsIgnoreCase(normalizedReportString, searchString)) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyPDFFontForStrings(PDFFontType font, String pdfContext, List<List<TextPosition>> pdfCharactersByArticle) {
        String pdfContentWithoutSpecialSymbols = getCleanPDFContent(pdfContext);
        List<TextPosition> pdfTextPositionList = getTextPositions(pdfCharactersByArticle);
        for (String searchString : searchStrings) {
            String validateTExt = StringUtils.deleteWhitespace(searchString);
            int validationPdfTextLength = validateTExt.length();
            int startTextIndex = pdfContentWithoutSpecialSymbols.indexOf(validateTExt);

            for (int i = startTextIndex; i < startTextIndex + validationPdfTextLength; ++i) {
                try {
                    if (!((PDType1Font) pdfTextPositionList.get(i).getFont()).getFontBoxFont().getName().equals(font.getFontTypeName())) {
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private boolean verifyPDFContentSizeForStrings(int size, String pdfContext, List<List<TextPosition>> pdfCharactersByArticle) {
        String pdfContentWithoutSpecialSymbols = getCleanPDFContent(pdfContext);
        List<TextPosition> pdfTextPositionList = getTextPositions(pdfCharactersByArticle);
        for (String searchString : searchStrings) {
            String validateTExt = StringUtils.deleteWhitespace(searchString);
            int validationPdfTextLength = validateTExt.length();
            int startTextIndex = pdfContentWithoutSpecialSymbols.indexOf(validateTExt);

            for (int i = startTextIndex; i < startTextIndex + validationPdfTextLength; ++i) {
                if (pdfTextPositionList.get(i).getFontSizeInPt() != size) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<TextPosition> getTextPositions(List<List<TextPosition>> pdfCharactersByArticle) {
        List<TextPosition> pdfTextPositionList = new ArrayList<>();
        for (List<TextPosition> aSearchString : pdfCharactersByArticle) {
            aSearchString.removeIf(pdfChar -> pdfChar.getUnicode().hashCode() == 32);
            pdfTextPositionList.addAll(aSearchString);
        }
        return pdfTextPositionList;
    }

    private String getCleanPDFContent(String pdfContext) {
        return StringUtils.deleteWhitespace(pdfContext.replaceAll("[\\r\\n]", ""));
    }
}
