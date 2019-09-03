package com.drew.schedule;

import com.drew.config.GlobalConfig;
import com.drew.item.pojo.DrewArticleInfo;
import com.drew.mapper.DrewArticleInfoMapper;
import com.drew.mapper.DrewPageVisitMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.util.List;

@Component
@EnableScheduling
public class VisitCountScheduler {


    @Autowired
    private DrewArticleInfoMapper drewArticleInfoMapper;
    @Autowired
    private DrewPageVisitMapper drewPageVisitMapper;

    @Scheduled(cron = "0 0 0/1 * * ?" )
    private void visitCount(){
        List<DrewArticleInfo> DrewArticleInfos = drewArticleInfoMapper.selectAll();

        for (DrewArticleInfo info:DrewArticleInfos) {

            Long id = info.getId();

            int oldCount = info.getArticleVisitor();

            String pageName = GlobalConfig.PRE_ARTICLE_COUNT+id;

            int count = drewPageVisitMapper.selectCountByPageName(pageName);

            if (oldCount != count){
                drewArticleInfoMapper.updateArticleVisitorByCount(count,id);
            }

        }
    }

    @Scheduled(cron = "0 0/2 * * * ?" )
    private void getSuccess(){

        String url = "http://localhost:8080/sppr-company";

        HttpGet httpGet = new HttpGet(url);

        HttpClient client = HttpClients.createDefault();

        try {
            HttpResponse response = client.execute(httpGet);
            System.out.println(response);
        }catch (Exception e){

        }

    }
}
