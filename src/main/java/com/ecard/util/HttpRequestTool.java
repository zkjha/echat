package com.ecard.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

public class HttpRequestTool {
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String requestUrl, String params) {
    	
        try {
        	 URL url = new URL(requestUrl);  
             HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
             connection.setDoOutput(true);  
             connection.setDoInput(true);  
             connection.setUseCaches(false);  
             connection.setRequestMethod("POST");  
             if (null != params) {  
                 OutputStream outputStream = connection.getOutputStream();  
                 outputStream.write(params.getBytes("UTF-8"));  
                 outputStream.close();  
             }  
             // 从输入流读取返回内容  
             InputStream inputStream = connection.getInputStream();  
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
             String str = null;  
             StringBuffer buffer = new StringBuffer();  
             while ((str = bufferedReader.readLine()) != null) {  
                 buffer.append(str);  
             }  
             bufferedReader.close();  
             inputStreamReader.close();  
             inputStream.close();  
             inputStream = null;  
             connection.disconnect();  
             return buffer.toString();  
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        return "";
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
        	
            String urlNameString =null;
            
            if(param==null||"".equals(param)){
            	urlNameString=url;
            }else{
            	urlNameString= url + "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    //发送post请求
    public static String sendPost(CloseableHttpClient httpclient,String path, String params) throws Exception{
		 HttpPost httpPost = new HttpPost();
		 URI uri = new URI(path);
		 httpPost.setURI(uri);
		 StringEntity requestEntity = new StringEntity(params, "UTF-8");
		 requestEntity.setContentType("application/x-www-form-urlencoded");
		 httpPost.setEntity(requestEntity);
		 CloseableHttpResponse  closeableHttpResponse =	 httpclient.execute(httpPost);
		 
		 HttpEntity entity = closeableHttpResponse.getEntity();
		 System.out.println(entity.getContentType());
		 System.out.println(entity.getContentLength());
		 // 取出服务器返回的数据流
		 InputStream stream = entity.getContent();
		 BufferedReader in = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
		 StringBuffer content = new StringBuffer();
		 String tempStr = "";
		 while((tempStr=in.readLine())!=null){
			 content.append(tempStr);
		 }
		 System.out.println("编码前："+content.toString());
		 return content.toString();
	}
}