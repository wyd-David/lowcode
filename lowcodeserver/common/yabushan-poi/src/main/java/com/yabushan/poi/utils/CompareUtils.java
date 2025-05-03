package com.yabushan.poi.utils;

import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.poi.vo.FileComparePoint;
import com.yabushan.poi.vo.FileCompareResult;
import com.yabushan.poi.vo.PaperContentVo;

import java.io.*;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CompareUtils {

    private static final    String SPIRE_WORD="Evaluation Warning: The document was created with Spire.Doc for JAVA.";

    /**
     *
     * @param sourceFilePath   源文件地址url
     * @param sourceFileType  文件类型：doc、txt、pdf
     * @param targetFilePath  目标文件地址url
     * @param targetFilePathType 文件类型：doc、txt、pdf
     * @param similarityRatio  段落相似度为多少时，才命中：1~100
     * @param allPoint  段落相似度达到整个论文的百分之多少时，重复文件命中：1~100
     * @return
     * @throws IOException
     */
    public static  FileCompareResult createTxtByFile(String sourceFilePath, String sourceFileType, String  targetFilePath, String targetFilePathType, Integer similarityRatio, Integer allPoint) throws IOException {
        FileCompareResult result = new FileCompareResult();
        String txtFile = createTxtFile(sourceFilePath, sourceFileType);
        String txtFile2 = createTxtFile(targetFilePath, targetFilePathType);
        FileComparePoint compare = compare(txtFile, txtFile2, similarityRatio, allPoint);
        result.setSourceFilePath(sourceFilePath);
        result.setTargetFilePath(targetFilePath);
        result.setFileComparePoint(compare);
        result.setSimilarityRatio(compare.getPoint());
        return result;

    }

    /**
     * 传入两个txt文件地址，和需要比对的相似度达到多少后才需要返回
     * @param filePath1
     * @param filePath2
     * @param similarityRatio 单行字符串相似度
     * @param allPoint  总相似度
     */
    public static FileComparePoint compare(String filePath1, String filePath2, Integer similarityRatio, Integer allPoint) {
        FileComparePoint point= new FileComparePoint();
        List<PaperContentVo> result = new ArrayList<>();
        List<String> file1List1 = new ArrayList<>();
        List<String> file1List2 = new ArrayList<>();
        try {
            //读取文件1
            File file = new File(filePath1);
            FileInputStream readIn = new FileInputStream(file);
            InputStreamReader read = new InputStreamReader(readIn, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String oneLine= null;
            while((oneLine= bufferedReader.readLine()) != null){
                if(!SPIRE_WORD.equals(oneLine) && oneLine.trim()!=null && oneLine.trim()!="" && oneLine.trim().length()>0){
                    file1List1.add(oneLine);
                }

            }
            bufferedReader.close();
            readIn.close();
            read.close();
            file.delete();
            //读取文件2
            File file2 = new File(filePath2);
            FileInputStream readIn2 = new FileInputStream(file2);
            InputStreamReader read2 = new InputStreamReader(readIn2, "utf-8");
            BufferedReader bufferedReader2 = new BufferedReader(read2);
            String oneLine2= null;
            while((oneLine2= bufferedReader2.readLine()) != null){
                if(!SPIRE_WORD.equals(oneLine2) && oneLine2.trim()!=null && oneLine2.trim()!="" && oneLine2.trim().length()>0) {
                    file1List2.add(oneLine2);
                }
            }
            bufferedReader2.close();
            readIn2.close();
            read2.close();
            file2.delete();

            //比较两个文件，如果字符串总长度大于传入的比例，则返回list。否则返回空
            for ( String txt1: file1List1
                 ) {
                for (String txt2:file1List2
                     ) {
                    float similarityRatio1 = getSimilarityRatio(txt1, txt2);
                    PaperContentVo vo = null;
                    if(similarityRatio1>=similarityRatio){
                        vo = new PaperContentVo();
                        vo.setPoint(similarityRatio1+"%");
                        vo.setSourceText(txt1);
                        vo.setTargetText(txt2);
                        result.add(vo);
                        //result.add("相似度："+similarityRatio1+"%....."+txt1+"【====疑相似内容====】"+txt2+"\n\t");
                        break;
                    }
                }
            }

            String descPercentStrDown = getDescPercentStrDown(result.size(), file1List1.size(), 4);
            point.setPoint(descPercentStrDown);//文章相似度

            if((result.size()  / file1List1.size() )>= (allPoint/100)){
                point.setResult(result);
            }

        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return point;
    }


    /**
     *传入源文件，生成txt文件，返回txt文件url。文件类类型
     * @param filePath
     * @param fileType: pdf  、txt、doc/docx
     * @throws IOException
     */
    public static String createTxtFile(String filePath,String fileType) throws IOException {
        //创建一个新的txt文件
        String rootPath=filePath;
        if(RuoYiConfig.getProfile()==null){
            rootPath=tempPath();
        }
        String txtFile1 = rootPath + StringUtils.getUUID() + ".txt";
        if("pdf".equals(fileType)){
            //pdf文件
            ExtractText.getPdfText(filePath, txtFile1);
        }else if("doc".equals(fileType) || "docx".equals(fileType)){
            ExtractText.getDocxText(filePath,txtFile1);
        }else if("txt".equals(fileType)){
            txtFile1=filePath;
        }
        return  txtFile1;
    }

    /**
     * 比较两个字符串的相识度
     * 核心算法：用一个二维数组记录每个字符串是否相同，如果相同记为0，不相同记为1，每行每列相同个数累加
     * 则数组最后一个数为不相同的总数，从而判断这两个字符的相识度
     * @param str
     * @param target
     * @return
     */
    public static float getSimilarityRatio(String str, String target) {

        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0 || m == 0) {
            return 100.0F;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + temp);
            }
        }
        return (1 - (float) d[n][m] / Math.max(str.length(), target.length())) * 100F;
    }

    /**
     * 获取完成情况的百分比，以字符串的形式返回
     * 传入完成数量和总数量以及保留的小数位
     * 不四舍五入
     * @param doCount
     * @param count
     * @param num
     * @return 百分比的字符串
     */
    public static String getDescPercentStrDown(int doCount,int count,int num){
        // 创建一个数值格式化对象
        if(count == 0){
            return "0%";
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后num位
        numberFormat.setMaximumFractionDigits(num);
        numberFormat.setRoundingMode(RoundingMode.DOWN);
        String result = numberFormat.format((float) doCount / (float) count * 100);
        return result + "%";
    }

    public static void writeStringToTxt(String content, String txtFileName) throws IOException {

        FileWriter fWriter= new FileWriter(txtFileName,true);
        try {
            fWriter.write(content);
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            try{
                fWriter.flush();
                fWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getSystemTempName(){
        String operationName ="";
        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            operationName="windows";
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            operationName="linux";
        } else { //其它操作系统
            operationName="other";
        }
        return operationName;

    }

    public static String tempPath(){
        String systemTempName = getSystemTempName();
        if(systemTempName.equals("windows")){
            return "D:\\test\\";
        }
        return "/temp/";
    }

}
