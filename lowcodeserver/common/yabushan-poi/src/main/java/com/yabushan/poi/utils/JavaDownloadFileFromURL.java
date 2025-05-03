package com.yabushan.poi.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * downloadUsingStream：在这个从URL下载java文件的方法中，使用URL openStream方法来创建输入流。然后使用文件输出流从输入流中读取数据并写入文件。
 * downloadUsingNIO：在这个URL方法的下载文件中，从URL流数据创建字节通道。然后使用文件输出流将其写入文件。
 */

public class JavaDownloadFileFromURL {

    public static void main(String[] args) {
        String url = "https://www.yiibai.com/index.html";

        try {
            downloadUsingNIO(url, "D:/test/sitemap.xml");

            downloadUsingStream(url, "D:/test/sitemap_stream.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    public static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

}
