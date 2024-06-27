package com.mscs.emr.automation.utils.fileprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class TextSearchPDFProcessor extends DownloadedFileProcessor {

    private String[] searchStrings;

    public TextSearchPDFProcessor(String... searchStrings) {
        this.searchStrings = searchStrings;
    }

    @Override
    public boolean doProcess(DownloadedFile pdfFile) {
        
//        System.out.println("TextSearchPDFProcessor.doProcess(): file=" + pdfFile.getPath());


        try {
            File outputFile = new File(pdfFile.getParentPath(), pdfFile.getNameWithoutExtension() + "-converted.txt");

            PdfReader reader = new PdfReader(pdfFile.getPath());
            PrintWriter out = new PrintWriter(new FileOutputStream(outputFile.getAbsolutePath()));
            Rectangle rect = new Rectangle(0, 0, 1000, 1000);
            RenderFilter filter = new RegionTextRenderFilter(rect);
            
//            System.out.println("TextSearchPDFProcessor.doProcess(): processing");


            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); ++i) {
                strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
                out.println(PdfTextExtractor.getTextFromPage(reader, i, strategy));
            }

            out.flush();
            out.close();
            
//            System.out.println("TextSearchPDFProcessor.doProcess(): done processing");


            return verifyPDFContainsAllStrings(outputFile);

        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }

    }

    private boolean verifyPDFContainsAllStrings(File file) {
        
//        System.out.println("TextSearchPDFProcessor.verifyPDFContainsAllStrings(): processing");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            
            StringBuilder stringData = new StringBuilder();

            String normalizedReportString;
            String line;

            while ((line = br.readLine()) != null) {
                stringData.append(line);
            }

            normalizedReportString = StringUtils.deleteWhitespace(stringData.toString());

            for (String aSearchString : searchStrings) {
                String searchString = StringUtils.deleteWhitespace(aSearchString);
                if (!StringUtils.containsIgnoreCase(normalizedReportString, searchString)) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        return true;

    }

}
