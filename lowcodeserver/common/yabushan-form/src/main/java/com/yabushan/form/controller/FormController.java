package com.yabushan.form.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.form.domain.LowFormFiledInfo;
import com.yabushan.form.domain.LowFormInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FormController {

    public static void main(String[] args) {

        String str ="{\n" +
                "  \"widgetList\": [\n" +
                "    {\n" +
                "      \"type\": \"select\",\n" +
                "      \"icon\": \"select-field\",\n" +
                "      \"formItemFlag\": true,\n" +
                "      \"options\": {\n" +
                "        \"name\": \"citySelect\",\n" +
                "        \"label\": \"地区\",\n" +
                "        \"labelAlign\": \"\",\n" +
                "        \"defaultValue\": \"\",\n" +
                "        \"placeholder\": \"\",\n" +
                "        \"columnWidth\": \"200px\",\n" +
                "        \"size\": \"\",\n" +
                "        \"labelWidth\": null,\n" +
                "        \"labelHidden\": false,\n" +
                "        \"disabled\": false,\n" +
                "        \"hidden\": false,\n" +
                "        \"clearable\": true,\n" +
                "        \"filterable\": false,\n" +
                "        \"allowCreate\": false,\n" +
                "        \"remote\": false,\n" +
                "        \"automaticDropdown\": false,\n" +
                "        \"multiple\": false,\n" +
                "        \"multipleLimit\": 0,\n" +
                "        \"optionItems\": [],\n" +
                "        \"required\": false,\n" +
                "        \"validation\": \"\",\n" +
                "        \"validationHint\": \"\",\n" +
                "        \"customClass\": [],\n" +
                "        \"labelIconClass\": null,\n" +
                "        \"labelIconPosition\": \"rear\",\n" +
                "        \"labelTooltip\": null,\n" +
                "        \"onCreated\": \"\",\n" +
                "        \"onMounted\": \"\",\n" +
                "        \"onRemoteQuery\": \"\",\n" +
                "        \"onChange\": \"var addressAPI = 'https://www.fastmock.site/mock/2de212e0dc4b8e0885fea44ab9f2e1d0/vform/getAddress?cityId='\\nvar addressSelectR = this.getWidgetRef('addressSelect')\\n\\naxios.get(addressAPI + value).then(function(res) {\\n  addressSelectR.loadOptions(res.data.data)\\n  \\n  //if (!value) {\\n  //  addressSelectR.setValue(null)\\n  //}\\n}).catch(function(error) {\\n  console.error(error)\\n})\\n\\naddressSelectR.setValue(null)\\n\",\n" +
                "        \"onFocus\": \"\",\n" +
                "        \"onBlur\": \"\",\n" +
                "        \"onValidate\": \"\"\n" +
                "      },\n" +
                "      \"id\": \"select78413\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"select\",\n" +
                "      \"icon\": \"select-field\",\n" +
                "      \"formItemFlag\": true,\n" +
                "      \"options\": {\n" +
                "        \"name\": \"addressSelect\",\n" +
                "        \"label\": \"安装地址\",\n" +
                "        \"labelAlign\": \"\",\n" +
                "        \"defaultValue\": \"\",\n" +
                "        \"placeholder\": \"\",\n" +
                "        \"columnWidth\": \"200px\",\n" +
                "        \"size\": \"\",\n" +
                "        \"labelWidth\": null,\n" +
                "        \"labelHidden\": false,\n" +
                "        \"disabled\": false,\n" +
                "        \"hidden\": false,\n" +
                "        \"clearable\": true,\n" +
                "        \"filterable\": false,\n" +
                "        \"allowCreate\": false,\n" +
                "        \"remote\": false,\n" +
                "        \"automaticDropdown\": false,\n" +
                "        \"multiple\": false,\n" +
                "        \"multipleLimit\": 0,\n" +
                "        \"optionItems\": [],\n" +
                "        \"required\": false,\n" +
                "        \"validation\": \"\",\n" +
                "        \"validationHint\": \"\",\n" +
                "        \"customClass\": \"\",\n" +
                "        \"labelIconClass\": null,\n" +
                "        \"labelIconPosition\": \"rear\",\n" +
                "        \"labelTooltip\": null,\n" +
                "        \"onCreated\": \"\",\n" +
                "        \"onMounted\": \"\",\n" +
                "        \"onRemoteQuery\": \"\",\n" +
                "        \"onChange\": \"\",\n" +
                "        \"onFocus\": \"\",\n" +
                "        \"onBlur\": \"\",\n" +
                "        \"onValidate\": \"\"\n" +
                "      },\n" +
                "      \"id\": \"select90599\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"formConfig\": {\n" +
                "    \"modelName\": \"formData\",\n" +
                "    \"refName\": \"vForm\",\n" +
                "    \"rulesName\": \"rules\",\n" +
                "    \"labelWidth\": 80,\n" +
                "    \"labelPosition\": \"left\",\n" +
                "    \"size\": \"\",\n" +
                "    \"labelAlign\": \"label-left-align\",\n" +
                "    \"cssCode\": \"\",\n" +
                "    \"customClass\": \"\",\n" +
                "    \"functions\": \"\",\n" +
                "    \"layoutType\": \"PC\",\n" +
                "    \"jsonVersion\": 3,\n" +
                "    \"onFormCreated\": \"\",\n" +
                "    \"onFormMounted\": \"var citySelectR = this.getWidgetRef('citySelect')\\n\\naxios.get('https://www.fastmock.site/mock/2de212e0dc4b8e0885fea44ab9f2e1d0/vform/getCity').then(function(res) {\\n  citySelectR.loadOptions(res.data.data)\\n}).catch(function(error) {\\n  console.error(error)\\n})\",\n" +
                "    \"onFormDataChange\": \"\"\n" +
                "  }\n" +
                "}";


        //封装字段
        String formUuid = StringUtils.getUUID();
        List<LowFormFiledInfo> formFiledInfoList = new ArrayList<>();
        LowFormFiledInfo lowFormFiledInfo = null;
        Map mapType = JSON.parseObject(str,Map.class);
       /* for (Object obj : mapType.keySet()){
            System.out.println("key为："+obj+"值为："+mapType.get(obj));
        }*/
        String widgetList = mapType.get("widgetList").toString();
        List<Map<String, Object>> maps = JSON.parseObject(widgetList, new TypeReference<List<Map<String, Object>>>() {});
        for(int i=0;i<maps.size();i++){
            lowFormFiledInfo = new LowFormFiledInfo();

            Map result = maps.get(i);
            String icon  = result.get("icon").toString();
            String type  = result.get("type").toString();
            String formItemFlag  = result.get("formItemFlag").toString();

            //获取到字段名称
            String fileds = result.get("options").toString();
            Map filedMap = JSON.parseObject(fileds,Map.class);
            String label = filedMap.get("label").toString();//获取标签
            String name = filedMap.get("name").toString();//获取名字

            lowFormFiledInfo.setFiledId(StringUtils.getUUID());
            lowFormFiledInfo.setFormUuid(formUuid);
            lowFormFiledInfo.setFiledName(name);
            lowFormFiledInfo.setFiledLabel(label);
            //设置字段类型，默认全部为字符串
            lowFormFiledInfo.setFiledType("VARCHAR(255)");
            lowFormFiledInfo.setFiledIcon(icon);
            lowFormFiledInfo.setFormItemFlag(formItemFlag);

            lowFormFiledInfo.setEntityName(name);
            lowFormFiledInfo.setEntityType("VARCHAR(255)");
            lowFormFiledInfo.setCreatedTime(new Date());
            lowFormFiledInfo.setCreatedBy(SecurityUtils.getUsername());









            /*for (Object obj : filedMap.keySet()){
                System.out.println("key为："+obj+"值为："+filedMap.get(obj));

            }*/
        }


    }
}
