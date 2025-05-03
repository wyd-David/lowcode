
package com.yabushan.web.controller.common;

import com.yabushan.common.annotation.Log;
import com.yabushan.common.config.RuoYiConfig;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.poi.service.ExtractTextImpl;
import com.yabushan.poi.utils.JavaDownloadFileFromURL;
import com.yabushan.poi.vo.FileCompareResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * 论文比对算法
 */

@RestController
@Api(tags = "论文比对模块")
@RequestMapping("/lunwen")
@Slf4j
public class PaperController {


    /**
     * similarityRatio – 段落相似度为多少时，才命中：1~100
     */
    @Value("${compare.similarityRatio}")
    private Integer similarityRatio;


    /**
     * allPoint – 段落相似度达到整个论文的百分之多少时，重复文件命中：1~100
     */
    @Value("${compare.allPoint}")
    private Integer allPoint;   




    @PostMapping("/paper/compareFileByFileUrl")
    @ApiOperation(value = "传入网络url地址进行比对2个文章地址信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "sourceFileUrl", value = "源文件url", dataType = "String" ,required = true),
            @ApiImplicitParam(name = "sourceFileType", value = "源文件类型：txt、doc、pdf", dataType = "String",required = true),
            @ApiImplicitParam(name = "targetFileUrl", value = "比对目标文件url", dataType = "String" ,required = true),
            @ApiImplicitParam(name = "targetFileType", value = "比对目标文件url", dataType = "String" ,required = true)
    })
    public FileCompareResult compareFileByFileId(String sourceFileUrl, String sourceFileType, String  targetFileUrl,
                                                 String targetFileType) throws IOException {
        FileCompareResult txtByFile=null;

        //获取远程文件的信息
        if(sourceFileType==null) {
            sourceFileType ="doc";
        };
        if(targetFileType==null) {
            targetFileType ="doc";
        }
    
        //创建一个新的txt文件
        String rootPath=RuoYiConfig.getProfile();
        String sourcFilePath = rootPath+"/"+StringUtils.getUUID()+"_paper."+sourceFileType;
        String targetFilePath = rootPath+"/"+StringUtils.getUUID()+"_paper."+sourceFileType;

        //下载文件到本地临时目录
        JavaDownloadFileFromURL.downloadUsingNIO(sourceFileUrl,sourcFilePath);
        JavaDownloadFileFromURL.downloadUsingNIO(targetFileUrl,targetFilePath);

        //文件比对
        txtByFile  = ExtractTextImpl.compareFile(sourcFilePath, sourceFileType, targetFilePath, targetFileType, similarityRatio, allPoint);

        return  txtByFile;
    }
    





   
/**
     *文件比对 根据文件地址
     * @param sourceFilePath  需要比对的源论文本地文件绝对路径（论文A）
     * @param sourceFileType  文件类型：doc、txt、pdf
     * @param targetFilePath 需要比对的目标论文本地文件绝对路径（论文B）
     * @param targetFilePathType 文件类型：doc、txt、pdf
     * @return FileCompareResult

     */

   
    @PostMapping("/paper/compareFileByFile")
    @ApiOperation(value = "传入本地路径地址进行比对")
    @ApiImplicitParams({@ApiImplicitParam(name = "sourceFilePath", value = "源文件url", dataType = "String"),
            @ApiImplicitParam(name = "sourceFileType", value = "源文件类型：txt、doc、pdf、docx", dataType = "String"),
            @ApiImplicitParam(name = "targetFilePath", value = "比对目标文件url", dataType = "String"),
            @ApiImplicitParam(name = "targetFilePathType", value = "目标文件类型：txt、doc、pdf、docx", dataType = "String")
    })
    public FileCompareResult compareFile(String sourceFilePath, String sourceFileType, String  targetFilePath, String targetFilePathType) throws IOException {
        FileCompareResult txtByFile=null;
        txtByFile  = ExtractTextImpl.compareFile(sourceFilePath, sourceFileType, targetFilePath, targetFilePathType, similarityRatio, allPoint);
        return  txtByFile;
    }






}

