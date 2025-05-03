package com.yabushan.common.bpm.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.bpm.model.AmsConstants;
import com.yabushan.common.bpm.model.FlowResult;
import com.yabushan.common.bpm.model.PieFlowTDto;
import com.yabushan.common.bpm.model.UserDto;
import com.yabushan.common.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ToolUtil {
    private final static Logger log = LogManager.getLogger(ToolUtil.class);

    /**
     * @Title: dealWithReturn
     * @Description: 处理BPM返回参数
     * @param
     * @author : yl
     * @date : 2019年7月17日 上午10:05:45
     * @throws
     * @return FlowResult    返回类型
     */
    public static FlowResult dealWithReturn(String result){
        FlowResult flowResult = JSON.parseObject(result, FlowResult.class);
        if(flowResult != null && flowResult.getHttpCode()==200){
            return flowResult;
        }else{
            log.error("BPM异常返回："+result);
            return flowResult;
        }
    }
    /**
     * @Title: dealWithFlowOwner
     * @Description: TODO(处理当前用户格式 	Name|id,Name|id 转为 id,id)
     * @param
     * @author : yl
     * @date : 2019年4月22日 上午9:48:40
     * @throws
     * @return String    返回类型用户ID
     */
    public static String dealWithFlowOwner(String owners){
        StringBuffer v = new StringBuffer();
        if(StringUtils.isNotBlank(owners)){
            if(owners.contains("|")){
                String[] users = owners.split(",");
                for (String user : users) {
                    if(v.length()==0){
                        v.append(user.split("\\|")[1]);
                    }else{
                        v.append(","+user.split("\\|")[1]);
                    }
                }
            }else{
                v.append(owners);
            }
        }else{
            v.append("-1");
        }
        return v.toString();
    }

    /**
     * @Title: dealNextActivities
     * @Description: TODO(获取默认选择的节点)
     * @param
     * @author : yl
     * @date : 2019年7月16日 下午2:57:38
     * @throws
     * @return String    返回类型
     */
    public static String dealNextActivities(JSONArray jsonArray) {
        String nextID = "";
        if(jsonArray != null && jsonArray.size()>0){
            for(int i=0;i<jsonArray.size();i++){
                JSONObject job = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                boolean defaultV = job.getBooleanValue("default");
                if(defaultV){//只处理一个
                    nextID += job.getString("id");
                    break;
                }
            }
            if(StringUtils.isBlank(nextID)){
                JSONObject job = jsonArray.getJSONObject(0);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                nextID = job.getString("id");
            }
        }
        return nextID;
    }

    /***
     * @Description:字符串去重
     * @Author: lanjin
     * @Date: 2019/8/27
     * @param str 1
     * @return : java.lang.String
     */

    public static  String distinctStringWithDot(String str) {
        String newStr="";
        if(StringUtils.isNotBlank(str)){
            String[]array=str.split(",");
            List<String> list = new ArrayList<String>();
            for(int i=0;i<array.length;i++){
                for(int j=i+1;j<array.length;j++){
                    if(array[i].equals(array[j])){
                        j = ++i;
                    }
                }
                list.add(array[i]);
            }
            for(String s:list){
                newStr=newStr+s+",";
            }
        }
        return newStr;
    }

    /**
     * @Title: getCurrentUser
     * @Description: 获取当前登录的用法
     * @author : yl
     * @date : 2019年1月30日 下午1:22:47
     * @return UserDto    返回类型
     * @throws
     */
    public static UserDto getCurrentUser(HttpServletRequest request){
        UserDto attribute = (UserDto) request.getSession().getAttribute(
                AmsConstants.LOGIN_USER);
        return attribute;
    }


    /**
     *@MethodName getBpmReader
     *@Desc 获取BMP的阅知人员
     *@Author YvonneLee
     *@Param [docId, cur, nextv]
     *@return java.lang.String
     *@Date 2021/1/5 15:50
     **/
    public static String getBpmReader(String docId,String cur,String nextv){
        String userIds = "";
        try {
        //    userIds = amopFlowNodeService.getBpmReader(docId, cur, nextv);
        }catch (Exception e){
            log.error("获取BMP的阅知人员异常：",e);
        }
        return userIds;
    }
    public static PieFlowTDto returnDocId(PieFlowTDto dto){
        PieFlowTDto d = new PieFlowTDto();
        if(dto != null){
            d.setDocId(dto.getDocId());
        }
        return d;
    }
}
