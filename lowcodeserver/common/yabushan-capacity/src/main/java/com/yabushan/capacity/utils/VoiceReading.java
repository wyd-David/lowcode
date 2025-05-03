package com.yabushan.capacity.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.util.Objects;
import java.util.UUID;

public class VoiceReading {
    public static void main(String[] args) {
        //这里是你要朗读的文字
        String text = "你好";
        //输出音频文件地址
        textToSpeechIO(text,"D:\\temp\\test\\");
    }

    /**
     *
     * @param text 阅读文字
     * @param path 文件输出路径
     */
    public static void textToSpeechIO(String text, String path) {

        ActiveXComponent ax = null;
        Dispatch spFileStream = null;
        Dispatch spAudioFormat = null;
        Dispatch spVoice = null;
        try {
            ax = new ActiveXComponent("Sapi.SpFileStream");
            spFileStream = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            spAudioFormat = ax.getObject();

            spVoice = new ActiveXComponent("Sapi.SpVoice").getObject();
            // 设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));
            // 设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            //随机uuid
            String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");

            String filePath = path + uuid + ".mp3";

            // 调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant(filePath), new Variant(3), new Variant(true));
            // 设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            // 设置音量  0 ~ 100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            // 设置朗读速度  -10 ~ +10
            Dispatch.put(spVoice, "Rate", new Variant(1));



            Dispatch.call(spVoice, "Speak", new Variant(text));

            System.out.println("输出语音文件成功！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭输出文件
            Dispatch.call(Objects.requireNonNull(spFileStream), "Close");
            Dispatch.putRef(Objects.requireNonNull(spVoice), "AudioOutputStream", null);

            Objects.requireNonNull(spAudioFormat).safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();
        }
    }


}