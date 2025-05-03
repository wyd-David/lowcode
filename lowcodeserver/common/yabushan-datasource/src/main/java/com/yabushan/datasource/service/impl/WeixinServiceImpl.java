package com.yabushan.datasource.service.impl;

import com.yabushan.datasource.mapper.SqldxecuteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class WeixinServiceImpl {


    @Autowired
    private SqldxecuteMapper sqldExecuteMapper;

    public  String SaveFile(String filePath,String tableName,String fileColoumName,String filePathColoumName,String fileType){
        File file = new File(filePath);
        return listFilesAndDirectories(file,tableName,fileColoumName,filePathColoumName, fileType);

    }

    public   String listFilesAndDirectories(File dir,String tableName,String fileColoumName,String filePathColoumName,String fileType) {
        if (dir.isDirectory()) {
            File[] filesList = dir.listFiles();
            if (filesList != null) {
                for (File file : filesList) {
                    if (file.isDirectory()) {
                      //  System.out.println("Directory: " + file.getPath());
                        // 递归调用
                        listFilesAndDirectories(file,tableName,fileColoumName,filePathColoumName, fileType);
                    } else {
                        //检查是否存在同名的文件
                        String validateSql = "select created_time from "+tableName +" where "+filePathColoumName +"='"+file.getPath().replace("\\","/")+"'";
                        List<Map<String, Object>> maps = sqldExecuteMapper.selectPublicItemList(validateSql);
                        if(maps.size()>0){continue;}
                        //写入
                        String sql = "insert into "+tableName +"("+fileColoumName+","+filePathColoumName+",created_time) values('"+file.getName()+"','"+file.getPath().replace("\\","/")+"','"+getFileLastUpdateTime(file.getPath())+"')";
                        sqldExecuteMapper.execute(sql);

                    }
                }
            } else {
                 return "The directory is empty or an I/O error occurred for directory: " + dir.getPath();
            }
        } else {
            return "The specified path is not a directory: " + dir.getPath();
        }
        return null;

    }

    public static void main(String[] args) {
        File rootDirectory = new File("D:\\软件\\WeChat\\文件存储\\WeChat Files\\shenxuefa\\FileStorage\\File");
        //listFilesAndDirectories(rootDirectory,null,null,null,"txt");
    }


    public static String getFileLastUpdateTime(String filePath){
        Path path = Paths.get(filePath);
        try {
            FileTime fileTime = Files.getLastModifiedTime(path);
            Instant instant = fileTime.toInstant();
            java.time.ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = zonedDateTime.format(formatter);
            return  formattedDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    //获取待推送的待办
    public List<Map<String, Object>> getSendTodoInfo(){
        String sql="SELECT A.openid,A.content,A.title,IFNULL(a.`dateTimeStr`,DATE_FORMAT(a.`date_time`, '%Y-%m-%d %H:%i:%s')) AS dateTimeStr ,a.id FROM ai_todo_info A     WHERE A.is_send=0  AND a.date_time > NOW() - INTERVAL 5 MINUTE   AND  a.date_time < NOW() + INTERVAL 1 MINUTE  ";
        List<Map<String, Object>> maps = sqldExecuteMapper.selectPublicItemList(sql);
        return maps;

    }

    public void updateTodoInfo(int id,String error){
        String sql="update ai_todo_info set send_time=now(),  is_send="+1+ "  where id="+id;
        if(error!=null){
            sql =sql+ " and send_error="+error.substring(0,200);
        }

        sqldExecuteMapper.execute(sql);
    }
    public void udpateQiandaoCishu( String openId){
        String sql="UPDATE AI_TODO_QIANDAO u SET u.qiandao_number=u.qiandao_number-1 WHERE u.openid='"+openId+"'";
        sqldExecuteMapper.execute(sql);
    }




}
