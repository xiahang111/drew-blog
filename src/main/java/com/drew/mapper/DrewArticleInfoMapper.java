package com.drew.mapper;

import com.drew.item.pojo.DrewArticleInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DrewArticleInfoMapper extends Mapper<DrewArticleInfo> {

    @Select("SELECT * FROM drew_article_info WHERE id = #{id}")
    DrewArticleInfo selectByArticleId(@Param("id") Long id);

    @Select("select id,article_headline,article_date,article_visitor,article_tag,article_category_id,article_author,article_img_url,create_time,update_time" +
            " from drew_article_info WHERE article_category_id = #{categoryId}")
    List<DrewArticleInfo> selectByCategoryId(@Param("categoryId") Integer categoryId);

    @Select("select id,article_headline,article_date,article_visitor,article_tag,article_category_id,article_author,article_img_url,create_time,update_time" +
            " from drew_article_info order by article_date desc")
    List<DrewArticleInfo> selectAllOrderBydate();

    @Select({
            "<script>",
            "select",
            "id,article_headline,article_date,article_visitor,article_tag,article_category_id,article_author,article_img_url,create_time,update_time",
            "from drew_article_info",
            "where article_category_id in",
            "<foreach collection='categoryIds' item='categoryId' open='(' separator=',' close=')'>",
            "#{categoryId}",
            "</foreach>",
            "</script>"
    })
    List<DrewArticleInfo> selectByCategoryIds(@Param("categoryIds") List<String> categoryIds);




}
