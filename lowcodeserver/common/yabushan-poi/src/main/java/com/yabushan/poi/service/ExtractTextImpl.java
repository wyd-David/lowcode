package com.yabushan.poi.service;

import com.yabushan.poi.utils.CompareUtils;
import com.yabushan.poi.vo.FileCompareResult;
import com.yabushan.poi.vo.PaperContentVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class ExtractTextImpl {

    @Value("${compare.similarityRatio}")
    private Integer similarityRatio;
    @Value("${compare.allPoint}")
    private Integer allPoint;

    /**
     *文件比对方式
     * @param sourceFilePath  需要比对的源论文本地文件绝对路径（论文A）
     * @param sourceFileType  文件类型：doc、txt、pdf
     * @param targetFilePath 需要比对的目标论文本地文件绝对路径（论文B）
     * @param targetFilePathType 文件类型：doc、txt、pdf
     * @return FileCompareResult
     * @throws IOException
     */
     public  FileCompareResult compareFileByFileId(String sourceFilePath, String sourceFileType, String  targetFilePath, String targetFilePathType) throws IOException {
         FileCompareResult txtByFile = CompareUtils.createTxtByFile(sourceFilePath, sourceFileType, targetFilePath, targetFilePathType, similarityRatio, allPoint);
        return  txtByFile;

     }


    /**
     *文件比对方式
     * @param sourceFilePath  需要比对的源论文本地文件绝对路径（论文A）
     * @param sourceFileType  文件类型：doc、txt、pdf
     * @param targetFilePath 需要比对的目标论文本地文件绝对路径（论文B）
     * @param targetFilePathType 文件类型：doc、txt、pdf
     * @return FileCompareResult
     * @throws IOException
     */
    public  FileCompareResult compareFile(String sourceFilePath, String sourceFileType, String  targetFilePath, String targetFilePathType) throws IOException {
        FileCompareResult txtByFile = CompareUtils.createTxtByFile(sourceFilePath, sourceFileType, targetFilePath, targetFilePathType, similarityRatio, allPoint);
        return  txtByFile;

    }



    /**
     *
     sourceFilePath – 源文件地址url
     sourceFileType – 文件类型：doc、txt、pdf
     targetFilePath – 目标文件地址url
     targetFilePathType – 文件类型：doc、txt、pdf
     similarityRatio – 段落相似度为多少时，才命中：1~100
     allPoint – 段落相似度达到整个论文的百分之多少时，重复文件命中：1~100
     * @param sourceFilePath
     * @param sourceFileType
     * @param targetFilePath
     * @param targetFilePathType
     * @param similarityRatio
     * @param allPoint
     * @return
     * @throws IOException
     */
    public static FileCompareResult compareFile(String sourceFilePath, String sourceFileType, String  targetFilePath, String targetFilePathType, Integer similarityRatio, Integer allPoint) throws IOException {
        FileCompareResult txtByFile = CompareUtils.createTxtByFile(sourceFilePath, sourceFileType, targetFilePath, targetFilePathType, similarityRatio, allPoint);
        return  txtByFile;

    }

    public static void main(String[] args) throws IOException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day=new Date(); 
        //System.out.println(df.format(day));

       // FileCompareResult result = compareFile("D:\\test\\Flink 学习文档.doc", "doc", "D:\\test\\周楷涛个人简历——java.txt", "txt", 50, 50);
        FileCompareResult result = compareFile("D:\\test\\1.docx", "docx", "D:\\test\\2.docx", "docx", 0, 50);
        StringBuffer sb = new StringBuffer();
        Date day1=new Date(); System.out.println(df.format(day1));
        sb.append(df.format(day)).append("----").append(df.format(day1)).append("\n\t");
        sb.append(result.getFileComparePoint().getPoint());
        sb.append(result.getSourceFilePath()).append("\n\t");
        sb.append(result.getTargetFilePath()).append("\n\t");
        sb.append("相似内容如下：");
        List<PaperContentVo> result1 = result.getFileComparePoint().getResult();
        if(result1!=null && result1.size()>0){
            for (PaperContentVo txt:result1
            ) {
                sb.append(txt.toString()).append("\n\t");
            }
        }

        CompareUtils.writeStringToTxt(sb.toString(),"D:\\test\\result.txt");

    }



}
