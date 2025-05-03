package com.yabushan.common.utils.file;

import java.io.File;

public class DeleteDirectory {

    //删除文件和目录
    public static void clearFiles(String workspaceRootPath){
        File file = new File(workspaceRootPath);
        if(file.exists()){
            deleteFile(file);
        }
    }
    public static void deleteFile(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0; i<files.length; i++){
                deleteFile(files[i]);
            }
        }
        boolean  flag= file.delete();
        if(flag){
            System.out.println("文件删除成功");
        }
    }
}
