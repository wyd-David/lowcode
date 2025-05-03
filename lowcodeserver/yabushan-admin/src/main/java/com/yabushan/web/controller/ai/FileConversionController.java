package com.yabushan.web.controller.ai;


import com.spire.ms.System.Collections.ArrayList;
import com.yabushan.ai.pdf.FileConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/fp")
public class FileConversionController {

    @Autowired
    private FileConversionService fileConversionService;

    @GetMapping("/convert")
    public List<String> convertFile(@RequestParam String url) {
        try {
            return fileConversionService.convertToImages(url);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }


    @GetMapping("/convertOne")
    public String convertFileOne(@RequestParam String url) {
        try {
            return fileConversionService.convertToImages(url).get(0);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
