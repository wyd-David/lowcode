package com.yabushan.common.utils.file;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipOutputStream;

/**
 * 文件处理工具类
 *
 * @author ruoyi
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 输出指定流的byte数组
     *
     * @param os 输出流
     * @return
     */
    public static void writeBuffBytes(BufferedInputStream bufferedInputStream, OutputStream os, byte[] bytes) throws IOException {
//        FileInputStream fis = null;
//        try {
////            File file = new File(filePath);
////            if (!file.exists())
////            {
////                throw new FileNotFoundException(filePath);
////            }
////            fis = new FileInputStream(file);
//            if (bytes == null) {
//                byte[] b = new byte[bufferedInputStream.available()];
//                int length;
//                while ((length = bufferedInputStream.read(b)) > 0) {
//                    os.write(b, 0, length);
//                }
//            } else {
//                int length;
//                while ((length = bufferedInputStream.read(bytes)) > 0) {
//                    os.write(bytes, 0, length);
//                }
//            }
//
//        } catch (IOException e) {
//            throw e;
//        } finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }else {
//                System.out.println(os);
//            }
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }else {
//                System.out.println(fis);
//            }
//        }
    }

    /**
     * 输出指定流的byte数组
     *
     * @param os 输出流
     * @return
     */
    public static void writeBuffBytes(BufferedInputStream bufferedInputStream, OutputStream os) throws IOException {
        try {
            byte[] b = new byte[bufferedInputStream.available()];
            int length;
            while ((length = bufferedInputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

            os.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            if(null != os){
                os.close();
            }
        }
    }

    /**
     * InputStream转Byte[]
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static byte[] InputStreamToBytes(InputStream inputStream,int bufferSize) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[bufferSize];
        int count = -1;
        while((count = inputStream.read(data,0,bufferSize))!= -1){
            outputStream.write(data,0,count);
            data = null;
        }

        return outputStream.toByteArray();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            boolean delete = file.delete();
            if(!delete){
                System.out.println(delete);
            }
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     *
     * @param request  请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

    // 文件打包下载
   /* public static HttpServletResponse downLoadFiles(HttpServletResponse response) {
        try {
            *//**
             * 这个集合就是你想要打包的所有文件， 这里假设已经准备好了所要打包的文件
             *//*
            //List<File> files = new ArrayList<File>();

            *//**
             * 创建一个临时压缩文件， 我们会把文件流全部注入到这个文件中 这里的文件你可以自定义是.rar还是.zip
             *//*
            File file = new File("D:/certpics.zip");
            if (!file.exists()) {
                file.createNewFile();
            }
            response.reset();
            // response.getWriter()
            // 创建文件输出流
            FileOutputStream fous =null;
            ZipOutputStream zipOut=null;
            try{
                fous =  new FileOutputStream(file);
                *//**
                 * 打包的方法我们会用到ZipOutputStream这样一个输出流, 所以这里我们把输出流转换一下
                 *//*
                 zipOut = new ZipOutputStream(fous);
                *//**
                 * 这个方法接受的就是一个所要打包文件的集合， 还有一个ZipOutputStream
                 *//*
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                fous.close();
                zipOut.close();
            }

//            zipOut.close();

            return downloadZip(file, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        *//**
         * 直到文件的打包已经成功了， 文件的打包过程被我封装在FileUtil.zipFile这个静态方法中，
         * 稍后会呈现出来，接下来的就是往客户端写数据了
         *//*

        return response;
    }*/

    public static HttpServletResponse downloadZip(File file,
                                                  HttpServletResponse response) throws IOException {

        try(OutputStream toClient=new BufferedOutputStream(
                response.getOutputStream());InputStream fis = new BufferedInputStream(new FileInputStream(
                file.getPath()))) {
            // 以流的形式下载文件。
            byte[] buffer = new byte[fis.available()];
            int read = fis.read(buffer);

            // 清空response
            response.reset();
            response.setContentType("application/octet-stream");

            // 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     * <p>
     * .apache.tools.zip.ZipOutputStream
     */
    public static void zipFile(BufferedInputStream bufferedInputStream, ZipOutputStream ouputStream) {
        try {


            // 向压缩文件中输出数据
            int nNumber;
            byte[] buffer = new byte[512];
            while ((nNumber = bufferedInputStream.read(buffer)) != -1) {
                ouputStream.write(buffer, 0, nNumber);
            }
            // 关闭创建的流对象
//            bins.close();
//            bufferedInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 检查文件是否可下载
     *
     * @param resource 需要下载的文件
     * @return true 正常 false 非法
     */
    public static boolean checkAllowDownload(String resource)
    {
        // 禁止目录上跳级别
        if (StringUtils.contains(resource, ".."))
        {
            return false;
        }

        // 检查允许下载的文件规则
        if (ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(resource)))
        {
            return true;
        }

        // 不在允许下载的文件规则
        return false;
    }

    /**
     * 获取文件名称 /profile/upload/2022/04/16/ruoyi.png -- ruoyi.png
     *
     * @param fileName 路径名称
     * @return 没有文件路径的名称
     */
    public static String getName(String fileName)
    {
        if (fileName == null)
        {
            return null;
        }
        int lastUnixPos = fileName.lastIndexOf('/');
        int lastWindowsPos = fileName.lastIndexOf('\\');
        int index = Math.max(lastUnixPos, lastWindowsPos);
        return fileName.substring(index + 1);
    }

    /**
     * 下载文件名重新编码
     *
     * @param response 响应对象
     * @param realFileName 真实文件名
     * @return
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException
    {
        String percentEncodedFileName = percentEncode(realFileName);

        StringBuilder contentDispositionValue = new StringBuilder();
        contentDispositionValue.append("attachment; filename=")
                .append(percentEncodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(percentEncodedFileName);

        response.setHeader("Content-disposition", contentDispositionValue.toString());
    }
    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException
    {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }

}
