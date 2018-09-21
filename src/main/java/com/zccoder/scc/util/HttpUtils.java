package com.zccoder.scc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
* http请求工具类
* @create ZhangCheng by 2017-06-23
*
*/
public class HttpUtils {
	
	/** 
     * 向指定 URL 发送POST方法的请求 
     *  
     * @param url    发送请求的 URL 
     * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式。 
     * @return 所代表远程资源的响应结果 
     */
    public static String sendPost(String url, String param) {  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);        
            HttpURLConnection  conn = (HttpURLConnection)realUrl.openConnection();  // 打开和URL之间的连接  
              
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
  
            out = new PrintWriter(conn.getOutputStream());  // 获取URLConnection对象对应的输出流  
            out.print(param);    // 发送请求参数  
            out.flush();        // flush输出流的缓冲  
              
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  // 定义BufferedReader输入流来读取URL的响应  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("发送 POST 请求出现异常！"+e);  
            e.printStackTrace();  
        }  
        //使用finally块来关闭输出流、输入流  
        finally{  
            try{  
                if(out!=null){  
                    out.close();  
                }  
                if(in!=null){  
                    in.close();  
                }  
            }  
            catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }
}
