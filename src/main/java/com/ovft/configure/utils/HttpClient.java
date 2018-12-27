package com.ovft.configure.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by looyer on 2017/9/13.
 */
public class HttpClient {

    private static Logger logger = LoggerFactory.getLogger(HttpClient.class);    //日志记录
     

    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static String httpGet(String url){
        //get请求返回结果
//        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                return EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
//                jsonResult = JSONObject.fromObject(strResult);
//                url = URLDecoder.decode(url, "UTF-8");
            } else {
                logger.error("get请求提交失败:" + url);
                return "";
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return "";
    }


    public static void main(String args[]){
//        JSONObject json = HttpClient.httpGet("https://api.weixin.qq.com/sns/jscode2session?appid=wx6bc897bf94e62e6e&secret=5b23af2a31036ea92dc84dfe021f6a66&js_code=003sIDBJ01uaO52H9BzJ0vxOBJ0sIDB9&grant_type=authorization_code");
//        System.out.println(json.toString());
        //{"errcode":40029,"errmsg":"invalid code, hints: [ req_id: ojzvVA0406th44 ]"}
        //{"session_key":"qghlmn9ADh8YJryAjk/w4g==","openid":"oh5WV5FD5arUCih_t-eyu1f64Zeg"}
    }

}
