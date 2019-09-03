package com.drew.service;

import com.drew.item.pojo.DrewPageVisit;
import com.drew.item.pojo.SpprCompany;
import com.drew.single.PageVisitSingleQueue;
import com.drew.single.SpprCompanySingleQueue;
import com.drew.single.SpprUrlSingleQueue;
import com.drew.thread.SpprCompanyProducer;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SpprPageService {

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

    BlockingDeque<SpprCompany> queue = SpprCompanySingleQueue.getInstance();
    BlockingDeque<String> urlQueue = SpprUrlSingleQueue.getInstance();

    private final static String GET_IP_URL = "http://piping.mogumiao.com/proxy/api/get_ip_bs?appKey=0d8d01225e4f4c44a4340489229956a0&count=20&expiryDate=0&format=1&newLine=2";

    public static void main(String[] args) {


    }


    public void saveUrl() {

        for (int i = 1103114; i < 2000000; i++) {

            String url = "http://shop.jc001.cn/" + i + "/contact.html";

            urlQueue.offer(url);

        }

        Map<String, Integer> ipmap = new HashMap<String, Integer>();
        ExecutorService exe = Executors.newFixedThreadPool(20);

        Document doc = null;
        try {
            doc = Jsoup.connect(GET_IP_URL).get();
        } catch (Exception e) {
            System.out.println("获取端口失败，");
            return;
        }
        System.out.println(doc.text());
        JSONObject jsonObject = JSONObject.fromObject(doc.text());
        List<Map<String, Object>> list = (List<Map<String, Object>>) jsonObject.get("msg");

        int i = 0;

        for (Map<String, Object> map : list) {


            String ip = (String) map.get("ip");
            String port = (String) map.get("port");
            ipmap.put(ip, Integer.valueOf(port));
            exe.execute(new SpprCompanyProducer(queue, urlQueue, ip, Integer.valueOf(port)));
            i++;


        }


    }

    private void analyzeUrl(String url, int id) {

        try {
            int j = new Random().nextInt(14);

            Document document = Jsoup.connect(url)
                    .timeout(5000)
                    .ignoreContentType(true)
                    .userAgent(ua[j])
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


        } catch (Exception e) {
            System.out.println("获取信息失败，继续 url:" + url);
        }

    }

    private String getAddressByPostCode(String postCode) {

        try {

            URL url = new URL("http://opendata.baidu.com/post/s?wd=" + postCode);
            URLConnection hul = url.openConnection();
            InputStream is = hul.getInputStream();
            byte[] by = new byte[1024];
            int len = 0;
            String address = null;
            while ((len = is.read(by)) != -1) {
                address += new String(by, 0, len, "GBK");
            }
            try {
                return address.substring(address.indexOf("</em>：") + 6, address.indexOf("</h3>"));
            } catch (Exception e) {
                System.out.println("对不起，您的输入有误");
                return "未知";
            }


        } catch (Exception e) {
            return "未知";
        }

    }

}
