package com.rolldata.web.system.util;

import com.alibaba.fastjson2.JSON;
import com.rolldata.core.util.JsonUtil;
import com.rolldata.core.util.StringUtil;
import com.rolldata.core.util.SysPropertiesUtil;
import com.rolldata.web.system.util.webChat.MyX509TrustManager;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.Map.Entry;

/**
 * API接口https请求
 *
 * @Title: HttpRequestUtil
 * @Description: HttpRequestUtil
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-05-21
 * @version: V1.0
 */
public class HttpRequestUtil extends HttpBase<HttpRequestUtil> {

    static private final Logger log = LogManager.getLogger(HttpRequestUtil.class);

    /**
     * 请求类型
     */
    private RequestMethodType requestMethodType;

    /**
     * 请求网址
     */
    private String url;

    private static int timeout = 60000;

    /**
     * 获取官网请求路径
     * @return
     */
    public static String getSysUrl (){
        String sysUrl = SysPropertiesUtil.getConfig("update.server.url");
        if(StringUtil.isEmpty(sysUrl)){
            sysUrl = "http://www.wrenchdata.com/";
        }
        return sysUrl;
    }

    /**application/json
     * 提交的数据
     */
    private String submitData;

    public HttpRequestUtil(String url) {
        this.url = url;

        // 默认
        header("Content-Type", ContentType.JSON.getValue());
    }

    /**
     * 请求类型枚举
     */
    static public enum RequestMethodType {
        GET,
        POST
    }

    /**
     * 提交数据的类型
     */
    static public enum ContentType {

        FORM_URLENCODED("application/x-www-form-urlencoded"),

        MULTIPART("multipart/form-data"),

        JSON("application/json"),

        XML("application/xml");

        private final String value;

        ContentType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return getValue();
        }

        /**
         * 判断提交数据的类型
         *
         * @param body
         * @return
         */
        public static ContentType get(String body) {
            ContentType contentType = null;
            if (StringUtil.isNotEmpty(body)) {
                char firstChar = body.charAt(0);
                switch (firstChar) {
                    case '{':
                    case '[':

                        // JSON请求体
                        contentType = JSON;
                        break;
                    case '<':

                        // XML请求体
                        contentType = XML;
                        break;

                    default:
                        break;
                }
            }
            return contentType;
        }
    }

    public static HttpRequestUtil httpsGet(String url) {

        return new HttpRequestUtil(url).method(RequestMethodType.GET);
    }

    public static HttpRequestUtil httpsPost(String url) {

        return new HttpRequestUtil(url).method(RequestMethodType.POST);
    }

    /**
     * 请求要提交的数据
     *
     * @param submitData 提交的数据
     * @return
     */
    public HttpRequestUtil body(String submitData) {

        this.submitData = submitData;
        return this;
    }

    // TODO: 待整理

    /**
     * 测试内存暴增到5G左右,测试转json问题
     * 此方法JSONObject的json转换性能低,占内存很大
     *
     * @return
     */
    public JSONObject exec() throws Exception {

        JSONObject jsonObject = new JSONObject(true);
        try {
            jsonObject = getJsonObject();
        } catch (ConnectException ce) {
            log.error("{} 连接超时：{}", Thread.currentThread().getName(), ce.getMessage(), ce);
            throw ce;
        } catch (Exception e) {
            log.error("{} https 请求异常：{}", Thread.currentThread().getName(), e.getMessage(), e);
            throw e;
        }
        //log.info("执行完成：----{}", this.url);
        //log.info("用时：----------{} ms.", System.currentTimeMillis() - startTime);
        return jsonObject;
    }

    private JSONObject getJsonObject() throws Exception {

        JSONObject jsonObject;

        // 创建 SSLContext 对象，并使用我们指定的信任管理器初始化
        TrustManager[] tm = { new MyX509TrustManager() };
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述 SSLContext 对象中得到 SSLSocketFactory 对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        URL url = new URL(this.url);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setSSLSocketFactory(ssf);

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);

        // 设置请求方式（GET/POST）
        conn.setRequestMethod(this.requestMethodType.toString());

        // 设置 MessageHeader
        setRequestProperty(conn);

        // 当 outputStr 不为 null 时，向输出流写数据
        if (null != this.submitData) {
            OutputStream outputStream = conn.getOutputStream();

            // 注意编码格式
            outputStream.write(this.submitData.getBytes("UTF-8"));
            outputStream.close();
        }

        // 从输入流读取返回内容
        InputStream inputStream = conn.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }

        // 释放资源
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        inputStream = null;
        conn.disconnect();
        jsonObject = JSONObject.fromObject(buffer.toString());
        return jsonObject;
    }

    /**
     * 单据不存在或者被删除也报错,打印没有必要,加一个方法先
     *
     * @param isPrint
     * @return
     * @throws Exception
     */
    public JSONObject execOffPrint() throws Exception {

        JSONObject jsonObject = null;
        try {
            jsonObject = getJsonObject();
        } catch (ConnectException ce) {
            log.error("{} 连接超时：{}", Thread.currentThread().getName(), ce.getMessage(), ce);
            throw ce;
        } catch (Exception e) {
            throw e;
        }
        return jsonObject;
    }

    /**
     *
     *
     * @return
     */
    public <T> T exec2(Class<T> clazz) {

        //log.info("即将执行：----{}", this.url);
        long startTime = System.currentTimeMillis();
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建 SSLContext 对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述 SSLContext 对象中得到 SSLSocketFactory 对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(this.url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            conn.setRequestMethod(this.requestMethodType.toString());

            // 设置 MessageHeader
            setRequestProperty(conn);

            // 当 outputStr 不为 null 时，向输出流写数据
            if (null != this.submitData) {
                OutputStream outputStream = conn.getOutputStream();

                // 注意编码格式
                outputStream.write(this.submitData.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            //jsonObject = JSONObject.fromObject(buffer.toString());
            //Object o = JsonUtil.fromJson(buffer.toString(), clazz);
        } catch (ConnectException ce) {
            log.error("{} 连接超时：{}", Thread.currentThread().getName(), ce.getMessage(), ce);
        } catch (Exception e) {
            log.error("{} https 请求异常：{}", Thread.currentThread().getName(), e.getMessage(), e);
        }
        //log.info("执行完成：----{}", this.url);
        //log.info("用时：----------{} ms.", System.currentTimeMillis() - startTime);
        return JsonUtil.fromJson(buffer.toString(), clazz);
    }

    public HttpRequestUtil method(RequestMethodType requestMethodType) {

        this.requestMethodType = requestMethodType;
        return this;
    }

    /**
     * 设置请求属性
     *
     * @param conn
     */
    private void setRequestProperty(HttpsURLConnection conn) {

        if (null == conn) {
            return;
        }
        for (Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    public final String USER_AGENT = "Mozilla/5.0";
    // HTTP POST请求
    public String sendPost() throws Exception {

        URL obj = new URL(this.url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //添加请求头
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");//en-US,en;q=0.5
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("Access-Control-Allow-Headers", "Content-Type, X-TOKEN");
        String urlParameters = "data=" + URLEncoder.encode(this.submitData);

        //发送Post请求
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
        wr.write(urlParameters.getBytes("UTF-8"));
        wr.flush();
        wr.close();
        InputStream input = null;
        int resCode = con.getResponseCode();
        if (resCode != 200 && resCode != 201 && resCode != 202) {
            input = con.getErrorStream();
        } else {
            input = con.getInputStream();
        }
//        int responseCode = con.getResponseCode();
//        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + urlParameters);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(input,"UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //打印结果
//        System.out.println(response.toString());
        return response.toString();
    }

    public static com.alibaba.fastjson2.JSONObject doPost(String url,Map<String, Object> headparams, String param) {
        //定义接收数据
        com.alibaba.fastjson2.JSONObject result = new com.alibaba.fastjson2.JSONObject();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient client = HttpClients.createDefault();
//请求参数转JOSN字符串
        StringEntity entity = new StringEntity(param, "UTF-8");
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("content-type",
                "application/json;charset=utf-8");
        for (Entry<String, Object> entry : headparams.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue()
                    .toString());
        }
        try {
            HttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = JSON.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
            }else {
                result.put("error", "连接错误！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.put("error", "连接错误！");
        }
        return result;
    }

    /**
     * 测试内存暴增到5G左右,测试转json问题
     * 此方法JSONObject的json转换性能低,占内存很大
     * zhaibx新增，待测试大数据量，亨利小数位数问题，改为返回com.alibaba.fastjson2.JSONObject
     * @return String
     */
    public String execToString() throws Exception {

        //log.info("即将执行：----{}", this.url);
        long startTime = System.currentTimeMillis();
        String result = "";
        try {
            // 创建 SSLContext 对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述 SSLContext 对象中得到 SSLSocketFactory 对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(this.url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            conn.setRequestMethod(this.requestMethodType.toString());

            // 设置 MessageHeader
            setRequestProperty(conn);

            // 当 outputStr 不为 null 时，向输出流写数据
            if (null != this.submitData) {
                OutputStream outputStream = conn.getOutputStream();

                // 注意编码格式
                outputStream.write(this.submitData.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            result = buffer.toString();
        } catch (ConnectException ce) {
            log.error("{} 连接超时：{}", Thread.currentThread().getName(), ce.getMessage(), ce);
            throw ce;
        } catch (Exception e) {
            log.error("{} https 请求异常：{}", Thread.currentThread().getName(), e.getMessage(), e);
            throw e;
        }
        //log.info("执行完成：----{}", this.url);
        //log.info("用时：----------{} ms.", System.currentTimeMillis() - startTime);
        return result;
    }

    public com.alibaba.fastjson2.JSONObject execToObject() throws Exception {

        //log.info("即将执行：----{}", this.url);
        long startTime = System.currentTimeMillis();
        com.alibaba.fastjson2.JSONObject jsonObject = new com.alibaba.fastjson2.JSONObject();
        try {
            // 创建 SSLContext 对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述 SSLContext 对象中得到 SSLSocketFactory 对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(this.url);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            conn.setRequestMethod(this.requestMethodType.toString());

            // 设置 MessageHeader
            setRequestProperty(conn);

            // 当 outputStr 不为 null 时，向输出流写数据
            if (null != this.submitData) {
                OutputStream outputStream = conn.getOutputStream();

                // 注意编码格式
                outputStream.write(this.submitData.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = com.alibaba.fastjson2.JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("{} 连接超时：{}", Thread.currentThread().getName(), ce.getMessage(), ce);
            throw ce;
        } catch (Exception e) {
            log.error("{} https 请求异常：{}", Thread.currentThread().getName(), e.getMessage(), e);
            throw e;
        }
        //log.info("执行完成：----{}", this.url);
        //log.info("用时：----------{} ms.", System.currentTimeMillis() - startTime);
        return jsonObject;
    }

    public static String get(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        return get(path, params, headers, timeout);
    }

    public static String get(String path, Map<String, String> params, Map<String, String> headers, int timeout) throws Exception {
        path = getPath(path, params);
        HttpGet method = new HttpGet(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String delete(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        path = getPath(path, params);
        HttpDelete method = new HttpDelete(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String options(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        path = getPath(path, params);
        HttpOptions method = new HttpOptions(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String trace(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        path = getPath(path, params);
        HttpTrace method = new HttpTrace(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String head(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        path = getPath(path, params);
        HttpHead method = new HttpHead(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        method.setConfig(requestConfig);
        return getHead(method, headers);
    }

    public static String put(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        HttpPut method = new HttpPut(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        // 请求的参数信息传递
        List<NameValuePair> pairs = buildPairs(params);
        if (pairs.size() > 0) {
            HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
            method.setEntity(entity);
        }
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }
    public static String patch(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        HttpPatch method = new HttpPatch(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        // 请求的参数信息传递
        List<NameValuePair> pairs = buildPairs(params);
        if (pairs.size() > 0) {
            HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
            method.setEntity(entity);
        }
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String post(String path, Map<String, String> params, Map<String, String> headers, int timeout) throws Exception {
        HttpPost method = new HttpPost(path);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        // 请求的参数信息传递
        List<NameValuePair> pairs = buildPairs(params);
        if (pairs.size() > 0) {
            HttpEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
            method.setEntity(entity);
        }
        method.setConfig(requestConfig);
        return getResponse(method, headers);
    }

    public static String post(String path, Map<String, String> params, Map<String, String> headers) throws Exception {
        return post(path, params, headers, timeout);
    }

    public static String postBody(String url, String body, Map<String, String> headers) throws Exception {
        return postBody(url, body, headers, timeout);
    }


    public static String postBody(String url, String body, Map<String, String> headers, int timeout) throws Exception {
        HttpClient client = buildHttpClient(url);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("charset", "utf-8");

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
        httppost.setConfig(requestConfig);
        buildHeader(headers, httppost);

        BasicHttpEntity requestBody = new BasicHttpEntity();
        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
        requestBody.setContentLength(body.getBytes("UTF-8").length);
        httppost.setEntity(requestBody);
        // 执行客户端请求
        HttpResponse response = client.execute(httppost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }

    /*********************************私有方法***************************************************/
    public static HttpClient buildHttpClient(String url) throws Exception{
        if (url.startsWith("https")){
            SSLContext sslcontext = createIgnoreVerifySSL();
            //创建自定义的httpclient对象
            SSLConnectionSocketFactory fac = new SSLConnectionSocketFactory(sslcontext,
                    new String[]{"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(fac).build();
            return client;
        }
        return HttpClients.createDefault();
    }

    /**
     * 绕过验证
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[] { trustManager }, null);
        return sc;
    }

    private static List<NameValuePair> buildPairs(Map<String, String> params) throws UnsupportedEncodingException {
        List<NameValuePair> pairs = new ArrayList<>();
        if (params != null) {
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                pairs.add(new BasicNameValuePair(key, URLDecoder.decode(params.get(key), "UTF-8")));
//                pairs.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        return pairs;
    }

    private static String getResponse(HttpUriRequest method, Map<String, String> headers)
            throws Exception {
        HttpClient client = buildHttpClient(method.getURI().getScheme());
        method.setHeader("charset", "utf-8");

        if (headers != null) {
            Set<String> keys = headers.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                method.setHeader(key, headers.get(key));
//                method.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                method.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63");
            }
        }

        HttpResponse response = client.execute(method);
        int status = response.getStatusLine().getStatusCode();
        if (status < 200 || status >= 300) {
            throw new ClientProtocolException("Path:" + method.getURI() + " - Unexpected response status: " + status);
        }
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity, "UTF-8");
        return body;
    }

    private static String getHead(HttpUriRequest method, Map<String, String> headers) throws Exception {
        HttpClient client = buildHttpClient(method.getURI().getScheme());
        method.setHeader("charset", "utf-8");
        // 默认超时时间为15s。
        buildHeader(headers, method);
        HttpResponse response = client.execute(method);
        Header[] responseHeaders = response.getAllHeaders();
        StringBuilder sb = new StringBuilder("");
        for (Header h : responseHeaders) {
            sb.append(h.getName() + ":" + h.getValue() + "\r\n");
        }
        return sb.toString();
    }

    private static void buildHeader(Map<String, String> headers, HttpUriRequest request) {
        if (headers != null) {
            Set<String> keys = headers.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                request.setHeader(key, headers.get(key));
            }
        }
    }

    private static String getPath(String path, Map<String, String> params) throws UnsupportedEncodingException {
        if (params != null) {
            if (path.indexOf("?") > -1) {
                path += "&";
            } else {
                path += "?";
            }
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                path += key + "=" + URLEncoder.encode(params.get(key), "UTF-8") + "&";
            }
            if (path.endsWith("&")) {
                path = path.substring(0, path.length() - 1);
            }
        }
        return path;
    }

    // 获取页面代码
    public static InputStream getInputStream(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(timeout);
        conn.setRequestProperty("Accept-Language", "zh-CN");
        conn.setRequestProperty("User-Agent",
                "User-Agent:Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setUseCaches(false);// 不进行缓存
        // 头部必须设置不缓存，否则第二次获取不到sessionID
        conn.setUseCaches(false);
        if (conn.getResponseCode() == 200) {
            return conn.getInputStream();
        }
        return null;
    }

    public com.alibaba.fastjson2.JSONObject httpExecToObject() throws Exception {

        //log.info("即将执行：----{}", this.url);
        long startTime = System.currentTimeMillis();
        com.alibaba.fastjson2.JSONObject jsonObject = new com.alibaba.fastjson2.JSONObject();
        try {
            // 创建 SSLContext 对象，并使用我们指定的信任管理器初始化

            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            conn.setRequestMethod(this.requestMethodType.toString());

            // 设置 MessageHeader
            for (Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            // 当 outputStr 不为 null 时，向输出流写数据
            if (null != this.submitData) {
                OutputStream outputStream = conn.getOutputStream();

                // 注意编码格式
                outputStream.write(this.submitData.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = com.alibaba.fastjson2.JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("{} 连接超时：{}", Thread.currentThread().getName(), ce.getMessage(), ce);
            throw ce;
        } catch (Exception e) {
            log.error("{} https 请求异常：{}", Thread.currentThread().getName(), e.getMessage(), e);
            throw e;
        }
        //log.info("执行完成：----{}", this.url);
        //log.info("用时：----------{} ms.", System.currentTimeMillis() - startTime);
        return jsonObject;
    }
}
