package com.yabushan.common.utils.file;

import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class MultipartFileToFile {
    /**
     * MultipartFile 转 File
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile( @RequestParam MultipartFile file ) throws Exception {
        File toFile = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }
    /**
     * File 转 MultipartFile
     * @param file
     * @throws Exception
     */
    public static void fileToMultipartFile( File file ) throws Exception {
        FileInputStream fileInput = new FileInputStream(file);
        MultipartFile toMultipartFile = new MockMultipartFile("file",file.getName(),"text/plain", IOUtils.toByteArray(fileInput));
        toMultipartFile.getInputStream();
    }
    /**
     * InputStream 转 File
     * @param ins
     * @param file
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        OutputStream os =null;
        try {
             os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(null!=os){
                    os.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
