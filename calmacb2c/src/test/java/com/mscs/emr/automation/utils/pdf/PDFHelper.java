package com.mscs.emr.automation.utils.pdf;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.TextPosition;

import com.mscs.emr.automation.functional.DriverManager;
import com.mscs.emr.automation.utils.http.HttpClient;

public class PDFHelper {

    private static PDFTextStripperHelper pdfTextStripperHelper;

    private synchronized static void fetchPDFTextStripperHelper(String urlStr) {
        HttpClient httpClient = new HttpClient(urlStr).fetchHttpGet();
        BasicCookieStore cookieStore = DriverManager.getDriverCookieStore();

        try (CloseableHttpResponse response = httpClient.setSSLContext().setCookieStore(cookieStore).execute().getCloseableHttpResponse()) {
            HttpEntity entity = Objects.requireNonNull(response).getEntity();

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                pdfTextStripperHelper = new PDFTextStripperHelper(entity.getContent());
            } else {
                throw new HttpException("Error: " + response.getStatusLine());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public synchronized static String getPDFContent(String urlStr) {
        fetchPDFTextStripperHelper(urlStr);
        return Objects.requireNonNull(pdfTextStripperHelper).getText();
    }

    public synchronized static List<List<TextPosition>> getPDFCharactersByArticle() {
        return Objects.requireNonNull(pdfTextStripperHelper).getCharactersByArticle();
    }

    public synchronized static  List<RenderedImage> getPDFImages(String urlStr) {
        fetchPDFTextStripperHelper(urlStr);
        return getImagesFromPDF(Objects.requireNonNull(pdfTextStripperHelper).getPdDocument());
    }

    private synchronized static List<RenderedImage> getImagesFromPDF(PDDocument document) {
        List<RenderedImage> images = new ArrayList<>();
        for (PDPage page : document.getPages()) {
            images.addAll(getImagesFromResources(page.getResources()));
        }
        return images;
    }

    private synchronized static  List<RenderedImage> getImagesFromResources(PDResources resources) {
        List<RenderedImage> images = new ArrayList<>();
        for (COSName xObjectName : resources.getXObjectNames()) {
            PDXObject xObject = null;
            try {
                xObject = resources.getXObject(xObjectName);
                if (xObject instanceof PDFormXObject) {
                    images.addAll(getImagesFromResources(((PDFormXObject) xObject).getResources()));
                } else if (xObject instanceof PDImageXObject) {
                    images.add(((PDImageXObject) xObject).getImage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return images;
    }
}
