package com.yabushan.capacity.utils;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;
public class WordToMP3 {
    public static void main(String[] args) {
     //   readStr("你好啊！");

        // textToSpeechIO("jacob不支持linux；jacob是“JAVA-COM Bridge”的缩写，是一个中间件，能够提供自动化访问MS系统下COM组件和“Win32 libraries”的功能，并且jacob只支持windows，不支持linux");

      //  String text=  textToStr("D:/temp/新建文本文档.txt");
        textToSpeechIO("你好啊！","D:\\");
    }

    /**
     * 字符串文本阅读
     * @param str 要读的文字字符串
     */
    public static void readStr(String str){
        ActiveXComponent ax = new ActiveXComponent("Sapi.SpVoice");
        //运行时输出语音内容
        Dispatch spVoice = ax.getObject();
        //设置音量 0 ~ 100
        ax.setProperty("Volume",new Variant(100));
        //设置朗读速度 -10 ~ +10
        ax.setProperty("Rate",new Variant(1));
        //执行朗读
        Dispatch.call(spVoice,"Speak",new Variant(str));
    }

    /**
     * 字符串文本转 wav格式 语音文件
     * @param text 要读的文字字符串
     */
    public static String textToSpeechIO(String text,String absolutePah){
        ActiveXComponent ax = null;
        Dispatch spFileStream = null;
        Dispatch spAudioFormat = null;
        Dispatch spVoice = null;
        
        //随机生成一个名字
        String fileName =  Calendar.getInstance().getTimeInMillis() + ".mp3";
        String absolutePahAndName =absolutePah+fileName;
      //  System.out.println("absolutePahAndName="+absolutePahAndName);
        try{
            
            ax = new ActiveXComponent("Sapi.SpFileStream");
          //  System.out.println("ax="+ax.toString());
            spFileStream = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            spAudioFormat = ax.getObject();
           // System.out.println("1");
            spVoice = new ActiveXComponent("Sapi.SpVoice").getObject();
            // 设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            //System.out.println("2");
            // 设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
           // System.out.println("3");
            // 调用输出 文件流打开方法，创建一个.wav文件
           // Dispatch.call(spFileStream, "Open", new Variant("D:/temp/test.mp3"), new Variant(3), new Variant(true));
            Dispatch.call(spFileStream, "Open", new Variant(absolutePahAndName), new Variant(3), new Variant(true));
            // 设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            // 设置音量  0 ~ 100
           // System.out.println("4");
            Dispatch.put(spVoice, "Volume", new Variant(100));
            // 设置朗读速度  -10 ~ +10
         //   System.out.println("5");
            Dispatch.put(spVoice, "Rate", new Variant(1));
            

            Dispatch.call(spVoice, "Speak", new Variant(text));
          //  System.out.println("fileName="+fileName);
           return fileName;
        }catch(Exception e){
            System.out.println("发生异常Exception="+e.getMessage());
            e.printStackTrace();
        }finally {

            // 关闭输出文件
           // System.out.println("关闭输出文件");
            Dispatch.call(Objects.requireNonNull(spFileStream), "Close");
          //  System.out.println("关闭输出文件1");
            Dispatch.putRef(Objects.requireNonNull(spVoice), "AudioOutputStream", null);
           // System.out.println("关闭输出文件2");
            Objects.requireNonNull(spAudioFormat).safeRelease();
          //  System.out.println("关闭输出文件3");
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();
        }
        return  null;
    }

    /**
     * txt文件转字符串
     * @param fileName txt文件所在位置
     * @return txt文件中的字符串
     */
    public static String textToStr(String fileName){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line=reader.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }finally {
            try {
                Objects.requireNonNull(reader).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
