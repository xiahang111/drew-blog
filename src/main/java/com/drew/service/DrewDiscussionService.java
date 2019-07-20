package com.drew.service;

import com.drew.item.d_enum.AnonymityUserEnum;
import com.drew.item.d_enum.PlantUserEnum;
import com.drew.item.dto.ArticleDiscussionDTO;
import com.drew.item.dto.ArticleReplyDTO;
import com.drew.item.pojo.DrewDiscussion;
import com.drew.mapper.DrewDiscussionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrewDiscussionService {

    @Autowired
    private DrewDiscussionMapper drewDiscussionMapper;

    //匿名用户的方法
    public void addDiscussion(String discussion, String parentId) {

        AnonymityUserEnum anonymityUserEnum = AnonymityUserEnum.getRandomAnonymityUser();
        PlantUserEnum plantUserEnum = PlantUserEnum.getRandomPlant();

        DrewDiscussion drewDiscussion = new DrewDiscussion(anonymityUserEnum, plantUserEnum);
        drewDiscussion.setDiscussion(discussion);
        if (!StringUtils.isEmpty(parentId)) {
            drewDiscussion.setParentId(Long.parseLong(parentId));
        }

        drewDiscussionMapper.insert(drewDiscussion);

    }

    public void addReply(String reply,String discussionId){

        AnonymityUserEnum anonymityUserEnum = AnonymityUserEnum.getRandomAnonymityUser();
        PlantUserEnum plantUserEnum = PlantUserEnum.getRandomPlant();

        DrewDiscussion drewDiscussion = new DrewDiscussion(anonymityUserEnum, plantUserEnum);
        drewDiscussion.setParentId(Long.parseLong(discussionId));
        drewDiscussion.setDiscussion(reply);

        drewDiscussionMapper.insert(drewDiscussion);

    }

    public List<ArticleDiscussionDTO> getAllDiscussion(String sort,String parentId) {

        List<ArticleDiscussionDTO> articleDiscussionDTOS = new ArrayList<>();

        if (StringUtils.isEmpty(parentId)){
            parentId = "0";
        }

        List<DrewDiscussion> drewDiscussions = drewDiscussionMapper.getByParentId(Long.parseLong(parentId));

        int floor = 1;
        for (DrewDiscussion drewDiscussion : drewDiscussions) {

            ArticleDiscussionDTO articleDiscussionDTO = new ArticleDiscussionDTO(drewDiscussion);

            List<DrewDiscussion> replys = drewDiscussionMapper.getByParentId(drewDiscussion.getId());

            if (replys.size() > 0){
                articleDiscussionDTO.setArticleReplyDTOS(revert(replys));
            }

            articleDiscussionDTO.setFloor(floor);
            floor++;
            articleDiscussionDTOS.add(articleDiscussionDTO);

        }

        return articleDiscussionDTOS;
    }

    public List<ArticleReplyDTO> revert(List<DrewDiscussion> replys){

        List<ArticleReplyDTO> result = new ArrayList<>();

        for (DrewDiscussion reply :replys) {

            result.add(new ArticleReplyDTO(reply));

        }

        return result;

    }

    //todo 留给非匿名用户的方法
    public void addDiscussion(String discussion) {

    }

    public List<ArticleDiscussionDTO> getHotDiscussion() {

        List<ArticleDiscussionDTO> articleDiscussionDTOS = new ArrayList<>();

        List<DrewDiscussion> drewDiscussions = drewDiscussionMapper.getDissionOrderByTime();


        for (DrewDiscussion drewDiscussion : drewDiscussions) {

            ArticleDiscussionDTO articleDiscussionDTO = new ArticleDiscussionDTO(drewDiscussion);
            articleDiscussionDTOS.add(articleDiscussionDTO);
        }

        return articleDiscussionDTOS;
    }

}
