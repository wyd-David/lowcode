package com.yabushan.web.utils.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片转换PDF类
 */
public class ImgPDF {
    public static void main(String[] args) {
        String[] fileList = FileUtil.getFileList("D:\\temp\\tt");
        String target = "D:\\temp\\tt\\tt.pdf";
        ImgPDF.ImgChangePDF(fileList,target);
        System.out.println("下载完成");
    }

    /**
     * 将图片转换成PDF
     * @param source        文件路径的集合 可以调用 FileUtil.getFileList() 方法
     * @param target        PDF的名字和位置
     */
    public static void ImgChangePDF(String []source, String target) {
        //创建一个文档对象
        Document doc = new Document();
        try {
            //定义输出文件的位置
            PdfWriter.getInstance(doc, new FileOutputStream(target));
            //开启文档
            doc.open();

            // 创建一个一行两列的表格
            PdfPTable table = new PdfPTable(2);
            for (int i = 0; i < source.length; i++) {
                if (source[i] == null) {
                    break;
                }
                Image img = Image.getInstance(source[i]);
                Float h = img.getHeight();
                Float w = img.getWidth();
                Integer percent = getPercent(h, w);
                img.setAlignment(Image.MIDDLE);
                img.scalePercent(percent);
                PdfPCell cell = new PdfPCell(img); // 将图片放置在单元格中
                cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 设置单元格中的内容居中对齐
                cell.setBorder(Rectangle.NO_BORDER); // 去掉单元格的边框
                cell.setFixedHeight(200f); // 设置单元格的固定高度
                cell.setBorderWidth(300f); // 设置单元格的固定宽度
                table.addCell(cell); // 将单元格添加到表格中
            }
            doc.add(table); // 将表格添加到文档中

            // 循环获取图片文件夹内的图片（一行一列）
            /*for (int i = 0; i < source.length; i++) {
                if(source[i] == null){      //前面的方法默认了数组长度是1024，所以这里就让它提前退出循环
                    break;
                }
                //路径
                Image  img = Image.getInstance(source[i]);
                //获得宽高
                Float h = img.getHeight();
                Float w = img.getWidth();
                //统一压缩
                Integer percent = getPercent(h, w);
                //图片居中
                img.setAlignment(Image.MIDDLE);
                //百分比显示图
                img.scalePercent(percent);
                //设置高和宽的比例
                doc.add(img);
            }*/
            // 关闭文档
            if(doc != null){
                doc.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    /**
     * 压缩
     * @param
     */
    public static Integer getPercent(Float h,Float w)
    {
        Integer g=0;
        Float g2=0.0f;
        g2=480/w*40;
        g=Math.round(g2);
        return g;
    }
}

