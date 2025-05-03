package com.yabushan.common.bpm.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yabushan.common.bpm.model.AmsConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class HttpToolUtil {
    private final static Logger log = LogManager.getLogger(HttpToolUtil.class);
    /**
     * get请求org.apache.http.client.HttpClient
     *
     * @return
     */
    public static String doGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String strResult = "";
        try {
            // 发送get请求
            HttpGet request = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(22000).build();
            request.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(request);
            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /** 读取服务器返回过来的json字符串数据 **/
                strResult = EntityUtils.toString(response.getEntity());
                return strResult;
            }
            response.close();
        } catch (IOException e) {
// 			e.printStackTrace();
            log.error(e);
            strResult = "{\"httpCode\":500,\"msg\":\"获取信息异常！\"}";
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    public static String post(Map<String, Object> prodMap, String url, Map<String, Object> header) throws ClientProtocolException, IOException {
        String content = "";
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse httpResponse = null;
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(22000).build();
            for (Iterator iter = header.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String value = String.valueOf(prodMap.get(name));
                httpPost.addHeader(name, value);
            }
            httpPost.setConfig(requestConfig);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Iterator iter = prodMap.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String value = String.valueOf(prodMap.get(name));
                params.add(new BasicNameValuePair(name, value));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
            httpPost.setEntity(entity);
            httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                content = EntityUtils.toString(responseEntity, "UTF-8");
            }

            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (Exception e) {
            log.error(e);
            content = "{\"httpCode\":500,\"msg\":\"获取信息异常！\"}";
        }
        return content;
    }

    public static String postRawJson(String prodMap, String url) throws ClientProtocolException, IOException {
        String content = "";
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse httpResponse = null;
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(22000).build();
            httpPost.setConfig(requestConfig);
            StringEntity postingString = new StringEntity(prodMap);// json传递
            httpPost.setEntity(postingString);
            httpPost.setHeader("Content-type", "application/json");
            httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                content = EntityUtils.toString(responseEntity, "UTF-8");
            }

            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (Exception e) {
            content = "{\"httpCode\":500,\"msg\":\"获取信息异常！\"}";
            log.error(e);
        }
        return content;
    }

    public static String post(Map<String, Object> prodMap, String url) throws ClientProtocolException, IOException {
        String content = "";
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse httpResponse = null;
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(22000).build();
            httpPost.setConfig(requestConfig);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Iterator iter = prodMap.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String value = String.valueOf(prodMap.get(name));
                params.add(new BasicNameValuePair(name, value));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
            httpPost.setEntity(entity);
            httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                content = EntityUtils.toString(responseEntity, "UTF-8");
            }
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (Exception e) {
            content = "{\"httpCode\":500,\"msg\":\"获取信息异常！\"}";
            log.error(e);
        }
        return content;
    }


    public static String daGuangGet(String url, String accessToken) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String strResult = "";
        try {
            // 发送get请求
            HttpGet request = new HttpGet(url);
            request.addHeader("Authorization", "Bearer " + accessToken);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setSocketTimeout(22000).build();
            request.setConfig(requestConfig);
            CloseableHttpResponse response = httpclient.execute(request);
            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /** 读取服务器返回过来的json字符串数据 **/
                strResult = EntityUtils.toString(response.getEntity());
                return strResult;
            }
            response.close();
        } catch (IOException e) {
// 			e.printStackTrace();
            strResult = "{\"httpCode\":500,\"msg\":\"获取信息异常！\"}";
            log.error(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    //达观POST 传cookies 获取他们的accesstoken
    public static String daGuangGet2(String url, String callBack, String token) {
        HttpGet request = new HttpGet(url);
        BasicCookieStore cookieStore = new BasicCookieStore();
        dealCookieForGet(cookieStore, request, token);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        String strResult = "";
        try {
            // 发送get请求
            request.addHeader("Content-Type", "application/json;charset=UTF-8");
            CloseableHttpResponse response = httpclient.execute(request);
            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /** 读取服务器返回过来的json字符串数据 **/
                strResult = EntityUtils.toString(response.getEntity());
                return strResult;
            } else {
                strResult = "{\"httpCode\":-1,\"msg\":\"获取信息异常！\"}";
            }
            response.close();
        } catch (IOException e) {
//			e.printStackTrace();
            strResult = "{\"httpCode\":-1,\"msg\":\"获取信息异常！\"}";
            log.error(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }


    /**
     * 发送GET请求下载公司发文带cookie
     *
     * @param
     * @param
     * @return
     */
    public static String doGet(String url, String callBack, String token) {
        HttpGet request = new HttpGet(url);
        BasicCookieStore cookieStore = new BasicCookieStore();
        dealCookieForGet(cookieStore, request, token);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        try {
            // 发送get请求
            request.addHeader("Content-Type", "application/json;charset=UTF-8");

            CloseableHttpResponse response = httpclient.execute(request);
            /** 请求发送成功，并得到响应 **/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /** 读取服务器返回过来的json字符串数据 **/
                String strResult = EntityUtils.toString(response.getEntity());
                if (StringUtils.isNotBlank(callBack)) {
                    String allMsg = "[]";
                    callBack += "(";
                    int i = strResult.indexOf(callBack);
                    if (i >= 0) {
                        int start = callBack.length();
                        int end = strResult.lastIndexOf(")");
                        allMsg = strResult.substring(start, end);
                    }
                    return allMsg;
                } else {
                    return strResult;
                }
            }
            response.close();
        } catch (IOException e) {
            log.error(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
        return "[]";
    }


    /**
     * 发送Post请求下载公司发文带cookie
     *
     * @param
     * @param
     * @return
     */
    public static String doPost(String url, Map<String, Object> prodMap, String token) {
        BasicCookieStore cookieStore = new BasicCookieStore();
        String content = "";
        try {
            CloseableHttpResponse httpResponse = null;
            HttpPost httpPost = new HttpPost(url);
            dealCookieForPost(cookieStore, httpPost, token);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .build();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Iterator iter = prodMap.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String value = String.valueOf(prodMap.get(name));
                params.add(new BasicNameValuePair(name, value));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Charset.forName("UTF-8"));
            httpPost.setEntity(entity);
            httpResponse = httpClient.execute(httpPost);
            HttpEntity responseEntity = httpResponse.getEntity();
            if (responseEntity != null) {
                content = EntityUtils.toString(responseEntity, "UTF-8");
            }
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (Exception e) {
            content = "{\"httpCode\":500,\"msg\":\"获取信息异常！\"}";
            log.error(e.getLocalizedMessage());
        }
        return content;
    }


    public static void inputStreamResponse(InputStream is, OutputStream os) {
        try {
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException ex) {
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
            } catch (IOException ex) {
            } finally {
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }

    private static void dealCookieForGet(BasicCookieStore cookieStore, HttpGet request, String token) {
        HashMap<String, String> cookies = new HashMap<String, String>();
        if (token == null || token.length() < 1) {
//			log.info(" XX 当前请求中 cookie为空，或无法获取cookie内容  XX ");
        } else {
            token = token.replaceAll("\\=", "等.号");
            String[] cookies_array = token.split(";");
            String[] cookie_set = null;
            String singleCookie = null;
            String cv = null;
            for (int i = 0; i < cookies_array.length; i++) {
                cv = "";
                singleCookie = cookies_array[i].trim();
                singleCookie = singleCookie.replaceFirst("等.号", "分.隔.符");
                singleCookie = singleCookie.replaceAll("等.号", "=");
                cookie_set = singleCookie.split("分.隔.符");
                if (cookie_set.length > 1) {
                    if (cookie_set[0] != null && cookie_set[0].length() > 0 && cookie_set[1] != null) {
                        cv = cookie_set[1].replaceAll("\"", "");
                        cookies.put(cookie_set[0], cv);
                    }
                }
            }
        }
        Iterator<?> iter = cookies.entrySet().iterator();
        String host = request.getURI().getHost();
        while (iter.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>) iter.next();
            BasicClientCookie cookie = new BasicClientCookie(entry.getKey(), entry.getValue());
            cookie.setDomain(host);
//	    	  cookie.setPath("/coa/oapage.nsf/agentGetJsonDataWeb");
            cookieStore.addCookie(cookie);
        }
    }

    private static void dealCookieForPost(BasicCookieStore cookieStore, HttpPost request, String token) {
        HashMap<String, String> cookies = new HashMap<String, String>();
        if (token == null || token.length() < 1) {
//			log.info(" XX 当前请求中 cookie为空，或无法获取cookie内容  XX ");
        } else {
            token = token.replaceAll("\\=", "等.号");
            String[] cookies_array = token.split(";");
            String[] cookie_set = null;
            String singleCookie = null;
            String cv = null;
            for (int i = 0; i < cookies_array.length; i++) {
                cv = "";
                singleCookie = cookies_array[i].trim();
                singleCookie = singleCookie.replaceFirst("等.号", "分.隔.符");
                singleCookie = singleCookie.replaceAll("等.号", "=");
                cookie_set = singleCookie.split("分.隔.符");
                if (cookie_set.length > 1) {
                    if (cookie_set[0] != null && cookie_set[0].length() > 0 && cookie_set[1] != null) {
                        cv = cookie_set[1].replaceAll("\"", "");
                        cookies.put(cookie_set[0], cv);
                    }
                }
            }
        }
        Iterator<?> iter = cookies.entrySet().iterator();
        String host = request.getURI().getHost();
        while (iter.hasNext()) {
            Entry<String, String> entry = (Entry<String, String>) iter.next();
            BasicClientCookie cookie = new BasicClientCookie(entry.getKey(), entry.getValue());
            cookie.setDomain(host);
//	    	  cookie.setPath("/coa/oapage.nsf/agentGetJsonDataWeb");
            cookieStore.addCookie(cookie);
        }
    }


    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();

        Map<String, Object> paramsS = new HashMap<>();
        paramsS.put("type", "user");
        paramsS.put("users", "lizhiyong");
        paramsS.put("secretary", "");
        System.err.println(JSON.toJSONString(paramsS));
        params.put("documentId", "docid:12F5D63699EA4E86894CEA62FF6E1BBB");//文档编号
        params.put("currentUser", "gongmaoxing");//当前用户
        params.put("nextActivityId", "2");//下一环节ID
        params.put("nextUserInfo", JSON.toJSONString(paramsS));//下一处理人
        params.put("approveResult", "");//处理结果
        params.put("approveNote", "");//处理意见
        params.put("appId", "");//应用ID
        params.put("chain", "[]");//审批链
        params.put("delaySerialNum", "false");//是否延迟创建文号
        Object o = JSONObject.toJSON(params);
        System.out.println(o.toString());
        Map json = (Map) JSONObject.parse(o.toString());
        System.out.println("这个是用JSONObject类的parse方法来解析JSON字符串!!!");
        for (Object map : json.entrySet()) {
            System.out.println(((Entry) map).getKey() + "  " + ((Entry) map).getValue());
        }
        try {
            String result = post(params, "http://127.0.0.1:8080" + AmsConstants.PBMURL_Start);
            System.err.println(result);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            String strResult = "额外热污染我惹(ewe werwer)我额外";
            Matcher mat = Pattern.compile("(?<=\\()[^\\)]+").matcher(strResult);
            String allMsg = "";
            while (mat.find()) {
                allMsg = mat.group();
            }

        }

    }
}
