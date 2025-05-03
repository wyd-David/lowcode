package com.yabushan.web.controller.tool;

import java.io.IOException;
import java.util.Calendar;

public class TextToSpeech {

    private static final String ESPEAK_COMMAND = "espeak";
    private static final String OUTPUT_FILE_PATH = "output.wav";

    public static void main(String[] args) {
        String text = "Hello, world!";
        convertTextToSpeech(text,"");
    }

    public static String convertTextToSpeech(String text,String absolutePah) {
        String fileName =  Calendar.getInstance().getTimeInMillis() + ".wav";
        absolutePah=absolutePah+fileName;
        try {
            // 使用eSpeak命令行工具将文字转换为语音文件
            Process process = new ProcessBuilder(ESPEAK_COMMAND,"-v","zh", "-w", absolutePah, text).start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Text to speech conversion completed.");
            } else {
                System.err.println("Text to speech conversion failed.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error during text to speech conversion: " + e.getMessage());
        }
        return fileName;
    }
}
