package com.yabushan.common.utils.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;

public class ZipFileUtils {
    public static void main(String[] args) {
        String zipFilePath = "path/to/zip/file.zip";
        String destDirectory = "path/to/unzip/directory";
        try {
            unzip(zipFilePath, destDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File dir = new File("C:\\oa\\logs");
        List<String> strings = listFiles(dir);
        System.out.println(strings.toString());
    }

    
    
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                extractFile(zipIn, filePath);
            } else {
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    /**
     * 
     * @param dir
     * @return
     */
    public static List<String> listFiles(File dir) {
        ArrayList<String> strings = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归处理子文件夹  
                    List<String> strings1 = listFiles(file);
                    strings.addAll(strings1);
                } else {
                    strings.add(file.getAbsolutePath());
                }
            }
        }
        return strings;
    }
}
