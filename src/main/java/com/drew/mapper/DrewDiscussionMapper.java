package com.drew.mapper;

import com.drew.item.pojo.DrewDiscussion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DrewDiscussionMapper extends Mapper<DrewDiscussion> {


    @Select("select * from drew_discussion order by create_time desc limit 5")
    public List<DrewDiscussion> getDissionOrderByTime();

    @Select("select * from drew_discussion where parent_id = #{parentId} order by create_time desc")
    public  List<DrewDiscussion> getByParentId(@Param("parentId") Long parentId);

}
