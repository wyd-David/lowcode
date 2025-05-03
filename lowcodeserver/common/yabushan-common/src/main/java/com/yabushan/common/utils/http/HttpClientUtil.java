package com.yabushan.common.utils.http;

import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 姜森焱 on 2021/8/17 14:49
 * http、https 请求工具类
 */
@Slf4j
public class HttpClientUtil {

    /**
     * 客户端
     */
    private static final String USER_AGENT = "Mozilla/5.0";
    /***
     * http请求状态
     */
    private static final int HTTP_REQUEST_STATE_SUCCESS = 200;

    /**
     * doget请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String doGet(String url, Map<String, Object> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key).toString());
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
        return resultString;
    }

    /**
     * httpPOST  JSON
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String httpPostWithjson(String url, String json) {
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            BasicResponseHandler handler = new BasicResponseHandler();
            StringEntity entity = new StringEntity(json, "utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            result = httpClient.execute(httpPost, handler);
            return result;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取网络图片转成字节流
     *
     * @param strUrl 完整图片地址
     * @return 图片资源数组
     */
    public static byte[] getNetImgByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            log.info("图片显示地址：" + strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            // 通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            // 得到图片的二进制数据
            byte[] btImg = readInputStream(inStream);
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 从输入流中获取字节流数据
     *
     * @param inStream 输入流
     * @return 图片流
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 设置每次读取缓存区大小
        byte[] buffer = new byte[1024 * 10];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }


    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * doPost请求
     *
     * @param url   请求地址
     * @param param 请求参数map
     * @return
     */
    public static String doPost(String url, Map<String, Object> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key).toString()));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
                // if(response != null){
                //     response.addHeader("Content-type","application/json; charset=utf-8");
                //     response.setHeader("Accept", "application/json");
                //  }

            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return resultString;
    }

    /**
     * POST请求
     *
     * @param url    请求url
     * @param params 请求参数
     * @return 请求结果字符串
     * @throws Exception 异常
     */
    public static String sendPost(String url, Map<String, Object> params) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        request.addHeader("USER_AGENT", USER_AGENT);
        request.addHeader("charset", "UTF-8");
        if (!CollectionUtils.isEmpty(params)) {
            List<NameValuePair> urlParameters = Lists.newArrayList();
            for (String key : params.keySet()) {
                urlParameters.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
            request.setEntity(new UrlEncodedFormEntity(urlParameters, Charsets.UTF_8));
        }
        HttpResponse response = httpClient.execute(request);
        return doResponse(response);
    }

    private static String doResponse(HttpResponse response) throws Exception {
        if (response.getStatusLine().getStatusCode() != HTTP_REQUEST_STATE_SUCCESS) {
            throw new Exception("请求异常");
        }

        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, Charsets.UTF_8);

        return responseContent;
    }

    /**
     * doPost请求带有请求头的Header
     *
     * @param url     请求地址
     * @param param   请求参数map
     * @param headers 请求头参数集合
     * @return
     */
    public static String doPostHeaders(String url, Map<String, Object> param, Header[] headers) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key).toString()));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
                httpPost.setHeaders(headers);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * doPost请求
     *
     * @param url  请求地址
     * @param json 请求参数，JSON格式参数
     * @return
     */
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }

        return resultString;
    }

    public static JSONObject Post(String url, JSONObject jsonObj) throws ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(jsonObj.toString(), "utf-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        //输出调用结果
        if (response != null && response.getStatusLine().getStatusCode() == 200) {
            // 生成 JSON 对象
            JSONObject obj = JSONObject.parseObject(result);
            return obj;
        }
        return null;
    }

    /**
     * doPost请求
     *
     * @param url  请求地址
     * @param json 请求参数，JSON格式参数
     * @return
     */
    public static String doPostJsonHeader(String url, String json, Header[] headers) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            httpPost.setHeaders(headers);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }

        return resultString;
    }

    /**
     * doget请求
     *
     * @param url
     * @param param
     * @return
     */
    public static String doGetHeader(String url, Map<String, Object> param,Header[] headers) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key).toString());
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeaders(headers);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
        return resultString;
    }

    /**
     * post请求（用于请求json格式的参数）
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, String params,Map headers) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);

        //设置请求头参数
        if(!StringUtils.isEmpty(headers)){
            for (Iterator iter = headers.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String value = String.valueOf(headers.get(name));
                httpPost.setHeader(name,value);
            }
        }
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity,"UTF-8");
                return jsonString;
            }
            else{
                //logger.error("请求返回:"+state+"("+url+")");
            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
