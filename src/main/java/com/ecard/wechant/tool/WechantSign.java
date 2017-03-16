package com.ecard.wechant.tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * @功能：微信支付取得签名
 * @作者：dinghongxing
 * @文件名：WxSign.java 
 * @包名：com.wxpay.util 
 * @项目名：zhuanquanquan
 * @部门：伏守科技项目开发部
 * @日期：2015年12月28日 下午1:38:43 
 * @版本：V1.0
 */
public class WechantSign {
	/**
	 * 
	 * @描述：生成微信支付签名
	 * @作者:丁洪星 
	 * @部门：伏守科技项目开发部
	 * @日期： 2015年11月4日 下午1:41:39 
	 * @版本： V1.0 
	 * @param map
	 * @param key
	 */
	public static String getpaySign(Map<String,Object> map, String key){
		ArrayList<String> list = new ArrayList<String>();
		
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=""){
            	list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String [] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i ++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + key;
        System.out.println(result);
        result = MD5.MD5Encode(result,"").toUpperCase();
        System.out.println(result);
        return result;
    }
	
	/**
	 * 
	 * @功能：微信授权签名
	 * @作者：dinghongxing
	 * @文件名：WechantSign.java 
	 * @包名：com.wechant.sign 
	 * @项目名：tongkeapi
	 * @部门：伏守科技项目开发部
	 * @日期：2016年4月16日 下午4:03:06 
	 * @版本：V1.0 
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
    public static Map<String, String> getauthorizeSign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
}
