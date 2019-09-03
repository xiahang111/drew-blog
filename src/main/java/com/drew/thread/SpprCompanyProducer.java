package com.drew.thread;

import com.drew.config.GlobalConfig;
import com.drew.item.pojo.SpprCompany;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpprCompanyProducer implements Runnable {

    private BlockingDeque<SpprCompany> queue;
    private BlockingDeque<String> urlQueue;

    private String ip;

    private int port;

    public SpprCompanyProducer(BlockingDeque<SpprCompany> queue, BlockingDeque<String> urlQueue, String ip, int port) {
        this.urlQueue = urlQueue;
        this.queue = queue;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        Random r = new Random();
        String[] ua = {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36 OPR/37.0.2178.32",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0)",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.277.400 QQBrowser/9.4.7658.400",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 UBrowser/5.6.12150.8 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7",
                "Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/60.0"};
        int a = r.nextInt(14);

        int id = GlobalConfig.spprCompanyCount++;

        while (id < 2999999) {

            String url = "";
            try {
                url = urlQueue.take();

                //爬取的目标网站，url记得换下。。。！！！
                Document document = Jsoup.connect(url)
                        .proxy(ip, port)
                        .ignoreContentType(true)
                        .userAgent(ua[a])
                        .header("referer", url)//这个来源记得换..
                        .get();

                //获取标题
                String companyName = document.select("div.shopName").select("h3").text();

                System.out.println("页面标题：" + companyName);

                //获取主营业务
                String major = document.select("div.shopName").select("p").text();

                System.out.println("主营业务：" + major);

                //获取联系人
                String information = document.select("div.contact").select("p").text();

                information.replaceAll(" ", "");
                information.replaceAll(" ", "");

                String person = "";
                if (information.contains("联系人")) {
                    int i = information.indexOf("联系人");

                    person = information.substring(i, i + 7).replace("联系人：", "");

                    System.out.println("联系人：" + person);


                }

                String postcode = "";

                String address = "";
                if (information.contains("邮政编码")) {

                    int k = information.indexOf("邮政编码");

                    postcode = information.substring(k, information.length()).replace("邮政编码：", "");

                    Pattern p = Pattern.compile("[0-9\\.]+");
                    Matcher m = p.matcher(postcode);
                    while (m.find()) {
                        postcode = m.group();
                    }

                    System.out.println("邮政编码：" + postcode);

                    address = "未知";

                }

                //获取手机号码
                String phoneNumberText = document.select("a[onclick]").attr("onclick");

                String phoneNumber = phoneNumberText.split("'")[1];

                System.out.println("手机号码：" + phoneNumber);

                SpprCompany spprCompany = new SpprCompany(companyName, major, person, postcode, phoneNumber, address);

                queue.offer(spprCompany);

//                Thread.sleep(1000l);
//                System.out.println("睡眠0.8秒");

            } catch (Exception e) {
                System.out.println("获取信息失败，继续 url:" + url + "原因：" + e.getMessage());

                if (e.getMessage().contains("refused") || e.getMessage().contains("timed out")){
                    try {
                        System.out.println("拒绝连接，等待睡眠2分钟");
                        Thread.sleep(120000l);

                    } catch (Exception e1) {
                        e1.getMessage();
                    }
                }else {
//                    try {
//                        Thread.sleep(1000l);
//                        System.out.println("睡眠1秒");
//                    } catch (Exception e1) {
//                        e1.getMessage();
//                    }
                }




            }
        }

    }
}
