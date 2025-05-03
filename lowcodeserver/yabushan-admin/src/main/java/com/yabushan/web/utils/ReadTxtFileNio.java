package com.yabushan.web.utils;


import com.yabushan.common.utils.DateUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

public class ReadTxtFileNio {

    public static void main(String[] args) throws Exception {
        String s = AESUtil.aesEncrypt("sxf2025sxf-sxf05sxfsxf-30sxf");
        System.out.println(s);
        System.out.println("'>>>>>>>>>>>>>>>>>>>'");
       System.out.println(AESUtil.aesDecrypt("vaLzlajD2FraPQUHMNExp4+xz/L2tDTw2XIbg+mMTrU="));
    }


    public static String readSecret() {
        //获取认证密钥
        // 指定文件路径（包括目录和文件名）
        String filePath = "D:\\project\\ngs\\key.txt";
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {

        } else if (osName.contains("mac")) {
            filePath ="/home/lowCode/lowCodeForm/key.txt";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            filePath ="/home/lowCode/lowCodeForm/key.txt";
        } else {
            filePath ="/home/lowCode/lowCodeForm/key.txt";
        }

        // 创建Path对象
        Path path = Paths.get(filePath);

        // 读取文件内容到List中
        try {
            List<String> lines = Files.readAllLines(path);
            if(lines.size()>0){
                return lines.get(0);
            }
            /*// 打印每一行内容
            for (String line : lines) {
                System.out.println(line);
            }*/
        } catch (IOException e) {
            return null;
        }
        return filePath;
    }


    public static boolean isKey() throws Exception {
        String s = readSecret();
        String s1 = AESUtil.aesDecrypt(s);
        s1=s1.replace("sxf","");
        Date date = DateUtils.parseDate(s1);
        if(date.after(new Date())){
            return true;
        }
        return false;
    }


}
