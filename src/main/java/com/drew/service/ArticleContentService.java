package com.drew.service;

import com.drew.config.GlobalConfig;
import com.drew.item.dto.ArticleBlogDTO;
import com.drew.item.pojo.DrewArticleComment;
import com.drew.item.pojo.DrewArticleContent;
import com.drew.item.pojo.DrewArticleInfo;
import com.drew.mapper.*;
import com.drew.utils.DateUtils;
import com.drew.utils.RedisUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticleContentService {

    @Autowired
    private DrewArticleInfoMapper drewArticleInfoMapper;

    @Autowired
    private DrewArticleContentMapper drewArticleContentMapper;

    @Autowired
    private DrewArticleCommentMapper drewArticleCommentMapper;

    @Autowired
    private DrewCategoryMapper drewCategoryMapper;

    @Autowired
    private DrewTagMapper drewTagMapper;

    @Autowired
    private RedisUtil redisUtil;

    public ArticleBlogDTO getArticleByArticleId(String articleId) {

        DrewArticleInfo drewArticleInfo = drewArticleInfoMapper.selectByArticleId(Long.parseLong(articleId));

        DrewArticleContent drewArticleContent = drewArticleContentMapper.selectByArticleId(Long.parseLong(articleId));

        List<DrewArticleComment> drewArticleComments = drewArticleCommentMapper.selectByArticleInfoId(Long.parseLong(articleId));

        ArticleBlogDTO articleBlogDTO = new ArticleBlogDTO(drewArticleInfo, drewArticleContent, drewArticleComments);

        articleBlogDTO.setArticleCategoryName(drewCategoryMapper.getNameById((long) drewArticleInfo.getArticleCategoryId()));

        String articleTag = drewArticleInfo.getArticleTag();

        if (!StringUtils.isEmpty(articleTag)){
            List<String> articleTagNames = new ArrayList<>();
            List<String> articleTags = Arrays.asList(articleTag.split(","));

            articleTagNames = drewTagMapper.getTagNameByIds(articleTags);
            articleBlogDTO.setArticleTags(articleTagNames);
        }

        return articleBlogDTO;

    }

    public List<ArticleBlogDTO> getArticles(String categoryId, String sort) {

        //先读缓存
        if (redisUtil.hasKey(GlobalConfig.ALL_ARTICLES_KEY+"-"+categoryId)){

            return new Gson().fromJson(redisUtil.get(GlobalConfig.ALL_ARTICLES_KEY), new TypeToken<List<ArticleBlogDTO>>(){

            }.getType());

        }

        List<ArticleBlogDTO> articleBlogDTOS = new ArrayList<>();

        List<DrewArticleInfo> drewArticleInfos;

        long t1 = System.currentTimeMillis();

        if (StringUtils.isEmpty(categoryId)) {

            switch (sort) {
                case "visitor" : drewArticleInfos = drewArticleInfoMapper.selectAllOrderByVisitor();break;

                default:drewArticleInfos = drewArticleInfoMapper.selectAllOrderBydate();
            }

        } else {
            //获取分类下的所有子分类
            List<String> categoryIds = drewCategoryMapper.getIdsByParentId(Long.parseLong(categoryId));
            categoryIds.add(categoryId);
            drewArticleInfos = drewArticleInfoMapper.selectByCategoryIds(categoryIds);
        }

        long t3 = System.currentTimeMillis();
        System.out.println("获取全部文章信息查询花费总时间："+ (t3 - t1));

        List<DrewArticleContent> drewArticleContents = drewArticleContentMapper.selectAllIndexArticle();


        long t4 = System.currentTimeMillis();
        System.out.println("获取全部文章内容查询花费总时间："+ (t4 - t3));

        List<DrewArticleComment> drewArticleComments = drewArticleCommentMapper.selectAll();

        if (drewArticleComments == null) {
            drewArticleComments = new ArrayList<>();
        }

        long t5 = System.currentTimeMillis();
        System.out.println("获取全部文章评论查询花费总时间："+ (t5 - t4));

        long t2 = System.currentTimeMillis();
        System.out.println("查询花费总时间："+ (t2 - t1));

        Map<Long, List<DrewArticleContent>> contentMap = drewArticleContents.stream().collect(Collectors.groupingBy(DrewArticleContent::getArticleInfoId));

        Map<Long, List<DrewArticleComment>> commentMap = drewArticleComments.stream().collect(Collectors.groupingBy(DrewArticleComment::getArticleInfoId));

        for (DrewArticleInfo drewArticleInfo : drewArticleInfos) {

            ArticleBlogDTO articleBlogDTO = new ArticleBlogDTO();

            articleBlogDTO.setArticleInfoId(drewArticleInfo.getId());
            articleBlogDTO.setArticleHeadline(drewArticleInfo.getArticleHeadline());
            articleBlogDTO.setArticleVisitor(drewArticleInfo.getArticleVisitor());
            articleBlogDTO.setArticleDate(DateUtils.dateToString(drewArticleInfo.getArticleDate(), DateUtils.DATE_FORMAT));
            articleBlogDTO.setArticleTags(new ArrayList<String>());
            articleBlogDTO.setArticleCategoryId(drewArticleInfo.getArticleCategoryId());
            articleBlogDTO.setArticleCategoryName(drewCategoryMapper.getNameById((long) drewArticleInfo.getArticleCategoryId()));
            articleBlogDTO.setArticleAuthor(drewArticleInfo.getArticleAuthor());
            articleBlogDTO.setArticleImgUrl(drewArticleInfo.getArticleImgUrl());
            articleBlogDTO.setArticleDescription("");


            DrewArticleContent content = contentMap.get(drewArticleInfo.getId()).get(0);
            articleBlogDTO.setArticleContentId(content.getId());
            articleBlogDTO.setArticleContent(content.getArticleContent());

            List<DrewArticleComment> articleComments = commentMap.get(drewArticleInfo.getId());

            if (null == articleComments) {
                articleComments = new ArrayList<>();
            }
            List<String> comments = new ArrayList<>();
            for (DrewArticleComment drewArticleComment : articleComments) {

                comments.add(drewArticleComment.getComment());
            }
            articleBlogDTO.setArticleComments(comments);
            articleBlogDTO.setArticleCommentsNum(comments.size());

            articleBlogDTOS.add(articleBlogDTO);

        }

        long t6 = System.currentTimeMillis();
        System.out.println("封装花费时间："+ (t6 - t2));

//        switch (sort) {
//
//            //将结果通过时间排序
//            case "time":
//                Collections.sort(articleBlogDTOS, new Comparator<ArticleBlogDTO>() {
//                    @Override
//                    public int compare(ArticleBlogDTO o1, ArticleBlogDTO o2) {
//                        Date a = DateUtils.stringToDate(o1.getArticleDate());
//                        Date b = DateUtils.stringToDate(o2.getArticleDate());
//
//                        if (a.after(b)) {
//                            return -1;
//                        } else {
//                            return 1;
//                        }
//                    }
//                });
//
//                break;
//
//            case "visitor":
//                Collections.sort(articleBlogDTOS, new Comparator<ArticleBlogDTO>() {
//                    @Override
//                    public int compare(ArticleBlogDTO o1, ArticleBlogDTO o2) {
//                        return o1.getArticleVisitor() > o2.getArticleVisitor() ? 1 : -1;
//                    }
//                });
//
//                break;
//            default:
//                break;
//        }

        //放入缓存

        redisUtil.setEx(GlobalConfig.ALL_ARTICLES_KEY,new Gson().toJson(articleBlogDTOS,new TypeToken<List<ArticleBlogDTO>>(){

        }.getType()),1, TimeUnit.MINUTES);

        return articleBlogDTOS;

    }


    public List<ArticleBlogDTO> getHotArticles(String articleId){

        List<ArticleBlogDTO> articleBlogDTOS = getArticles("","visitor");

        articleBlogDTOS = articleBlogDTOS.size() > 5 ? articleBlogDTOS.subList(0,4): articleBlogDTOS;

        if (!StringUtils.isEmpty(articleId)){

            articleBlogDTOS = articleBlogDTOS.stream().filter(new Predicate<ArticleBlogDTO>() {
                @Override
                public boolean test(ArticleBlogDTO articleBlogDTO) {
                    String articleInfoId = articleBlogDTO.getArticleInfoId()+"";
                    return !articleInfoId.equals(articleId);
                }
            }).collect(Collectors.toList());
        }

        return articleBlogDTOS;

    }

}
