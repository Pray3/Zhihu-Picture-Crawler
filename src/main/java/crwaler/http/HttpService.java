package crwaler.http;

import com.alibaba.fastjson.JSONObject;
import crwaler.MyCrawler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;


/**
 * 处理有关http请求的部分
 */
public class HttpService {
    static Logger log = Logger.getLogger(MyCrawler.class.getName());
    /**
     * 请求url并返回解析成json对象
     * @param url 请求url
     * @param name 请求头名称
     * @param value 请求头值
     * @return
     */
    public static JSONObject httpGet(String url, String name, String value) {
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            request.setHeader(name, value);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String strResult = EntityUtils.toString(response.getEntity());
                jsonResult = JSONObject.parseObject(strResult);
            }
        } catch (IOException e) {
            log.error("请求地址失败："+url+" 错误信息："+e.getMessage());
        }
        return jsonResult;
    }

    /**
     * 请求图片地址并下载
     * @param flag
     * @param url
     * @param name
     * @param value
     */
    public static void downloadEachPic(String flag,String url, String name, String value) {
        String path = MyCrawler.base + "/"+MyCrawler.questionTitle+"/" +flag +"/" +url.substring(23);//url中的图片名称命名
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            request.setHeader(name, value);
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                byte[] data = EntityUtils.toByteArray(entity);
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(data);
                fos.close();
                log.debug(Thread.currentThread().getName()+"下载文件:"+path+"成功");
            }
        } catch (IOException e) {
            log.error("下载图片失败："+url+" 错误信息："+e.getMessage());
        }
    }
}
