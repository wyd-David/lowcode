package com.yabushan.activiti.service;


import org.activiti.bpmn.model.BpmnModel;
import org.activiti.image.ProcessDiagramGenerator;

import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
/**
 * @Author yabushan
 * @Date 2021/6/22 23:27
 * @Version 1.0
 */
/**
 * 重写ProcessDiagramGenerator接口中的generateDiagram方法
 */
public interface CustomProcessDiagramGeneratorService extends ProcessDiagramGenerator {

    InputStream generateDiagram(BpmnModel bpmnModel,
                                String imageType,
                                List<String> highLightedActivities,
                                List<String> highLightedFlows,
                                String activityFontName,
                                String labelFontName,
                                String annotationFontName,
                                ClassLoader customClassLoader,
                                double scaleFactor,
                                Color[] colors,
                                Set<String> currIds);
}
