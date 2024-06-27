package com.mscs.emr.automation.utils.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class PDFTextStripperHelper extends PDFTextStripper {

    public PDFTextStripperHelper(InputStream pdfInputStream) throws IOException {
        super.document = getPDDocument(pdfInputStream);
    }

    public List<List<TextPosition>> getCharactersByArticle() {
        return super.charactersByArticle;
    }

    public PDDocument getPdDocument() {
        return super.document;
    }

    public String getText() {
        try {
            return super.getText(Objects.requireNonNull(super.document));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private synchronized PDDocument getPDDocument(InputStream pdfInputStream) {
        try {
            PDFParser pdfParser = new PDFParser(new RandomAccessBufferedFileInputStream(pdfInputStream));
            pdfParser.parse();
            COSDocument cosDoc = pdfParser.getDocument();
            return new PDDocument(cosDoc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
