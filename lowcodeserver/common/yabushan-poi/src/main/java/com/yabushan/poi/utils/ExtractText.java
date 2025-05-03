package com.yabushan.poi.utils;

import com.spire.doc.Document;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.yabushan.common.calculatesimilar.Result;
import com.yabushan.common.calculatesimilar.main;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtractText {

    public static void main(String[] args) throws IOException {
        //testDoc();
      //  test();
        getPdfText("D:\\temp\\1.pdf","D:\\temp\\1.txt");
    }

    public static  void testDoc() throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day=new Date(); System.out.println(df.format(day));
        //获取文档中的文本保存为String
        //  String text=getDocxText("D:\\test\\Flink 学习文档1.docx");
        String text1=getDocxText("D:\\test\\嵌入式廉洁风险防控板块--试点单位建设技术规范2.docx","D:\\test\\嵌入式廉洁风险防控板块--试点单位建设技术规范2.txt");
        // String text =getDocxText("D:\\test\\嵌入式廉洁风险防控板块--试点单位建设技术规范1.docx","D:\\test\\嵌入式廉洁风险防控板块--试点单位建设技术规范1.txt");
        // String text1=getPdfText("D:\\test\\unix.pdf","D:\\temp\\unix.txt");
        String text =getDocxText("D:\\test\\嵌入式廉洁风险防控板块--试点单位建设技术规范2.docx","D:\\test\\嵌入式廉洁风险防控板块--试点单位建设技术规范1.txt");
        //比对相似度
        Result analysisResultInfo = main.getAnalysisResultInfo(text1, text);
        StringBuffer sb = new StringBuffer();
        List<String> text2 = analysisResultInfo.getText();
        for (String txt:text2
        ) {
            sb.append(txt).append("\n\t");
        }
        writeStringToTxt(sb.toString(),"D:\\test\\result.txt");
        Date day1=new Date();
        System.out.println(df.format(day1));
    }

    /**
     * 读取doc文件内容，支持doc、docx
     * @param FilePath
     * @return  如果读取成功，返回存储的txt文件目录
     */
    public static String getDocxText(String FilePath,String txtFilePath) throws IOException {
        //加载Word文档
        Document document = new Document();
        document.loadFromFile(FilePath);

        //获取文档中的文本保存为String
        String text=document.getText();

        //将String写入Txt文件
         writeStringToTxt(text,txtFilePath);
       return txtFilePath;
    }

    /**
     * 获取PDF文件内容
     * @param filePath
     * @param txtFilePath
     * @return 如果写入成功，返回txt写入文件的路径
     * @throws IOException
     */
    public static String getPdfText(String filePath,String txtFilePath) throws IOException {
        //创建PdfDocument实例
        PdfDocument doc = new PdfDocument();
        //加载PDF文件
        doc.loadFromFile(filePath);
        //创建StringBuilder实例
        StringBuilder sb = new StringBuilder();

        PdfPageBase page;
        //遍历PDF页面，获取每个页面的文本并添加到StringBuilder对象
        for(int i= 0;i<doc.getPages().getCount();i++){
            page = doc.getPages().get(i);
            String text = page.extractText(true);
            sb.append(text);
        }
        writeStringToTxt(sb.toString(),txtFilePath);
        return txtFilePath;
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

    /**
     * 判断文本是否相等
     */
    public static void test(){
        String s = "用一个二维数组记录每个字符串是否相同，如果相同记为0，不相同记为1，每行每列相同个数累加";
       // String s1 = "每行每列相同个数累加，不相同记为1，如果相同记为0，用一个二维数组记录每个字符串是否相同";
        String s1 = "相同个数累加，不相同记为1，如果相同记为0，记录每个字符串是否相同";
        float similarityRatio = getSimilarityRatio(s, s1);
        double similarityRatio2 = getSimilarityRatio2(s, s1);

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


    /***
     * 分析比较 相似度
     * @param proName1
     * @param proName2
     * @return
     */
    public static double getSimilarityRatio2(String proName1, String proName2) {
        int Length1=proName1.length();
        int Length2=proName2.length();

        int Distance=0;
        if (Length1==0) {
            Distance=Length2;
        }
        if(Length2==0)
        {
            Distance=Length1;
        }
        if(Length1!=0&&Length2!=0){
            int[][] Distance_Matrix=new int[Length1+1][Length2+1];
            //编号
            int Bianhao=0;
            for (int i = 0; i <= Length1; i++) {
                Distance_Matrix[i][0]=Bianhao;
                Bianhao++;
            }
            Bianhao=0;
            for (int i = 0; i <=Length2; i++) {
                Distance_Matrix[0][i]=Bianhao;
                Bianhao++;
            }
            char[] Str_1_CharArray=proName1.toCharArray();
            char[] Str_2_CharArray=proName2.toCharArray();
            for (int i = 1; i <= Length1; i++) {
                for(int j=1;j<=Length2;j++){
                    if(Str_1_CharArray[i-1]==Str_2_CharArray[j-1]){
                        Distance=0;
                    }
                    else{
                        Distance=1;
                    }
                    int Temp1=Distance_Matrix[i-1][j]+1;
                    int Temp2=Distance_Matrix[i][j-1]+1;
                    int Temp3=Distance_Matrix[i-1][j-1]+Distance;
                    Distance_Matrix[i][j]=Temp1>Temp2?Temp2:Temp1;
                    Distance_Matrix[i][j]=Distance_Matrix[i][j]>Temp3?Temp3:Distance_Matrix[i][j];
                }
            }
            Distance=Distance_Matrix[Length1][Length2];
        }
        double Aerfas=1-1.0*Distance/(Length1>Length2?Length1:Length2);
        BigDecimal b = new BigDecimal(Aerfas);
        double Aerfa = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return  Aerfa;
    }
}
