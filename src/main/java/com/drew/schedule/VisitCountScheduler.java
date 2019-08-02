package com.drew.schedule;

import com.drew.config.GlobalConfig;
import com.drew.item.pojo.DrewArticleInfo;
import com.drew.mapper.DrewArticleInfoMapper;
import com.drew.mapper.DrewPageVisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
}
