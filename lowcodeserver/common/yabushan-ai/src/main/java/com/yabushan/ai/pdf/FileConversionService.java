package com.yabushan.ai.pdf;


import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.file.FileUploadUtils;
import com.yabushan.framework.config.ServerConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
public class FileConversionService {

    @Autowired
    private ServerConfig serverConfig;

    public List<String> convertToImages(String fileUrl) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        String fileExtension = getFileExtension(fileUrl);

        if ("pdf".equalsIgnoreCase(fileExtension)) {
            // Convert PDF to images
            imagePaths.addAll(convertPdfToImages(fileUrl));
        } else if ("zip".equalsIgnoreCase(fileExtension)) {
            // Process ZIP file
            imagePaths.addAll(processZipFile(fileUrl));
        }

        return imagePaths;
    }

    private List<String> convertPdfToImages(String pdfUrl) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        // 上传文件路径
        String filePath = RuoYiConfig.getProfile()+"/pdf/";

        try (PDDocument document = PDDocument.load(new URL(pdfUrl).openStream())) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300); // Render at 300 DPI
                // 上传并返回新文件名称
                String imagePath = "image-" + SecurityUtils.getUsername()+"_" +System.currentTimeMillis() +"_"+ page + ".png";
                String absolutePath = filePath + imagePath;
                ImageIO.write(bim, "PNG", new File(absolutePath));
                String url = serverConfig.getUrl() +"/profile/pdf/"+ imagePath;

                imagePaths.add(url);
            }
        }
        return imagePaths;
    }

    private List<String> processZipFile(String zipUrl) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        try (ZipFile zipFile = new ZipFile(new URL(zipUrl).getFile())) {
            Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();
            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".pdf")) {
                    // Extract PDF and convert to images
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        File tempFile = File.createTempFile("temp-", ".pdf");
                        tempFile.deleteOnExit();
                        // Save the PDF to a temporary file
                        Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        imagePaths.addAll(convertPdfToImages(tempFile.toURI().toString()));
                    }
                }
            }
        }
        return imagePaths;
    }

    private String getFileExtension(String url) {
        return url.substring(url.lastIndexOf('.') + 1);
    }
}
