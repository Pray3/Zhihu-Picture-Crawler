package crwaler;


import crwaler.bean.AnswerContent;
import crwaler.bean.ZhihuUrl;
import crwaler.download.JsonAnalysis;
import org.apache.log4j.Logger;

import java.util.List;

public class MyCrawler {
    public static String  base = "E:/zhihu";//保存图片地址
    public static String questionTitle;//问题标题
    public static String name = "authorization";
    public static String value  = "oauth c3cef7c66a1843f8b3a9e6a1e3160e20";
    static Integer totals = 50;
    static Logger log = Logger.getLogger(MyCrawler.class.getName());

    public static void main(String[] args) throws CloneNotSupportedException {
        ZhihuUrl url = new ZhihuUrl("31785374",20,0);
        startCrawler(url);
    }


    public static void startCrawler(ZhihuUrl url){
        totals = JsonAnalysis.getCount(url.getAddress());//获取任务总数，也可以使用默认50条回答
        questionTitle = JsonAnalysis.getTitle(url.getAddress());
        log.info("任务总数："+totals+"每次读取"+url.getLimit());
        Integer limit = url.getLimit();
        for (int i= 0;i*limit<totals;i++){//遍历
            url.setOffset(i*limit);
            List<AnswerContent> jsonResult = JsonAnalysis.getContent(url.getAddress());
            JsonAnalysis.downloadContent(jsonResult);
        }
        JsonAnalysis.closeTask();
    }



}
