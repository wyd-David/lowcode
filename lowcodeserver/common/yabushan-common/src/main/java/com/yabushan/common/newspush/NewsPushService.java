package com.yabushan.common.newspush;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.http.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 姜森焱 on 2021/9/14 17:38
 * 企业微信消息推送配置
 */
@Service("newsPushService")
public class NewsPushService {
    /**
     * 企业ID
     */
    private static String corpid;

    @Value("${enterpriseWx.corpid}")
    public void setcorpid(String corpid) {
        NewsPushService.corpid = corpid;
    }

    /**
     * 应用的凭证密钥
     */
    @Value("${enterpriseWx.corpsecret}")
    private static String corpsecret;

    @Value("${enterpriseWx.corpsecret}")
    public void setcorpsecret(String corpsecret) {
        NewsPushService.corpsecret = corpsecret;
    }

    /**
     * 获取access_token
     * 请求方式： GET（HTTPS）
     * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET
     */

    private static String gettokenUrl;
    @Value("${enterpriseWx.gettokenUrl}")
    public void setgettokenUrl(String gettokenUrl) {
        NewsPushService.gettokenUrl = gettokenUrl;
    }

    /**
     * 发送消息
     * 请求方式：POST（HTTPS）
     * 请求地址： https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN
     */
    private static String sendMessageUrl;

    @Value("${enterpriseWx.sendMessageUrl}")
    public void setsendMessageUrl(String sendMessageUrl) {
        NewsPushService.sendMessageUrl = sendMessageUrl;
    }

    /**
     * 企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
     */
    private static String agentid;
    @Value("${enterpriseWx.agentid}")
    public void setagentid(String agentid) {
        NewsPushService.agentid = agentid;
    }

    /**
     * 获取access_token
     *
     * @return
     */
    public static String getAccessToken() {
        //添加请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("corpid", corpid);
        params.put("corpsecret", corpsecret);
        String result = HttpClientUtil.doGet(gettokenUrl, params);
        if (StringUtils.isNotNull(result) && !result.equals("")) {
            JSONObject tokenJson = JSONUtil.parseObj(result);
            String errcode = tokenJson.get("errcode").toString();
            if (errcode.equals("0")) {
                return tokenJson.get("access_token").toString();
            }
        }
        return "";
    }

    /**
     * 发送文本消息
     * touser 指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
     * 特殊情况：指定为”@all”，则向该企业应用的全部成员发送
     * <p>
     * totag
     */
    public static boolean sendMessage(String touser, String content) {
        //添加请求参数
        Map<String, Object> params = new HashMap<>();
        //access_token
//        params.put("access_token", getAccessToken());
        //指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
        //特殊情况：指定为”@all”，则向该企业应用的全部成员发送
        params.put("touser", touser);
        //指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
        //当touser为”@all”时忽略本参数
        params.put("toparty", "");
        //指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
        //当touser为”@all”时忽略本参数
        params.put("totag", "");
        //消息类型，此时固定为：text
        params.put("msgtype", "text");
        //企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
        params.put("agentid", agentid);
        //消息内容，最长不超过2048个字节，超过将截断（支持id转译）
        Map<String, Object> textmap = new HashMap<>();
        textmap.put("content", content);
        params.put("text", textmap);
        //表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
        params.put("safe", "0");
        //表示是否开启id转译，0表示否，1表示是，默认0。仅第三方应用需要用到，企业自建应用可以忽略
        params.put("enable_id_trans", "0");
        //表示是否开启重复消息检查，0表示否，1表示是，默认0
        params.put("enable_duplicate_check", "0");
        //表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
        params.put("duplicate_check_interval", "1800");
        JSONObject jsonObj = new JSONObject(params);
        String result = HttpClientUtil.httpPostWithjson(sendMessageUrl + getAccessToken(), jsonObj.toString());
        if (StringUtils.isNotNull(result) && !result.equals("")) {
            JSONObject tokenJson = JSONUtil.parseObj(result);
            String errcode = tokenJson.get("errcode").toString();
            if (errcode.equals("0")) {
                return true;
            }
        }
        return false;
    }
    /**
     * 发送文本卡片消息
     * touser 指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
     * 特殊情况：指定为”@all”，则向该企业应用的全部成员发送
     * <p>
     * totag
     */
    public static boolean sendTextCardMessage(String touser, String content) {
        //添加请求参数
        Map<String, Object> params = new HashMap<>();
        //access_token
//        params.put("access_token", getAccessToken());
        //指定接收消息的成员，成员ID列表（多个接收者用‘|’分隔，最多支持1000个）。
        //特殊情况：指定为”@all”，则向该企业应用的全部成员发送
        params.put("touser", touser);
        //指定接收消息的部门，部门ID列表，多个接收者用‘|’分隔，最多支持100个。
        //当touser为”@all”时忽略本参数
        params.put("toparty", "");
        //指定接收消息的标签，标签ID列表，多个接收者用‘|’分隔，最多支持100个。
        //当touser为”@all”时忽略本参数
        params.put("totag", "");
        //消息类型，此时固定为：text
        params.put("msgtype", "textcard");
        //企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
        params.put("agentid", agentid);
        //消息内容，最长不超过2048个字节，超过将截断（支持id转译）
        Map<String, Object> textmap = new HashMap<>();
        textmap.put("content", content);
        params.put("text", textmap);
        //表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，默认为0
        params.put("safe", "0");
        //表示是否开启id转译，0表示否，1表示是，默认0。仅第三方应用需要用到，企业自建应用可以忽略
        params.put("enable_id_trans", "0");
        //表示是否开启重复消息检查，0表示否，1表示是，默认0
        params.put("enable_duplicate_check", "0");
        //表示是否重复消息检查的时间间隔，默认1800s，最大不超过4小时
        params.put("duplicate_check_interval", "1800");
        JSONObject jsonObj = new JSONObject(params);
        String result = HttpClientUtil.httpPostWithjson(sendMessageUrl + getAccessToken(), jsonObj.toString());
        if (StringUtils.isNotNull(result) && !result.equals("")) {
            JSONObject tokenJson = JSONUtil.parseObj(result);
            String errcode = tokenJson.get("errcode").toString();
            if (errcode.equals("0")) {
                return true;
            }
        }
        return false;
    }
}
