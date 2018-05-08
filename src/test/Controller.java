package test;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * 爬虫控制器
 */
public class Controller {
    public static void main(String[] args) throws Exception {
        //定义爬虫存储的位置
        String crawStorageFoler="d:/crawler";
        //定义爬虫线程7个
        int numberOfCrawlers=1;
        //定义爬虫配置
        CrawlConfig config=new CrawlConfig();
        //设置爬虫文件存储位置
        config.setCrawlStorageFolder(crawStorageFoler);
        //实例化页面获取器
        PageFetcher pageFetcher=new PageFetcher(config);
        //实例化爬虫机器人配置,比如可以设置user-agent
        RobotstxtConfig robotstxtconfig=new RobotstxtConfig();
        //实例化爬虫机器人对目标服务器的配置，每个网站都有一个robots.txt文件
        //规定了该网站哪些页面可以爬，哪些页面禁止爬，该类是对robots.txt规范的实现
        RobotstxtServer robotstxtServer=new RobotstxtServer(robotstxtconfig,pageFetcher);
        //实例化爬虫控制器
        CrawlController controller=new CrawlController(config,pageFetcher,robotstxtServer);
        //配置爬取种子页面，就是规定从哪里开始爬，可以配置多个种子页面
        controller.addSeed("http://www.bjsxt.com");


        //启动爬虫，爬虫从此刻开始执行爬虫任务
        controller.start(MyCrawler.class,numberOfCrawlers);
    }

}
