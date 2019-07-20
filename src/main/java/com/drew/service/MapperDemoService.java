package com.drew.service;

import com.drew.item.pojo.MapperDemo;
import com.drew.mapper.MapperDemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapperDemoService {

    @Autowired
    private MapperDemoMapper mapperDemoMapper;

    //添加方法
    public void add(MapperDemo mapperDemo){
        mapperDemoMapper.insert(mapperDemo);
    }

    //删除方法
    public void delete(String id){

        MapperDemo demo = new MapperDemo();
        demo.setId(Long.parseLong(id));

        mapperDemoMapper.delete(demo);
    }

    //更新方法
    public void update(MapperDemo mapperDemo){

        mapperDemoMapper.updateByPrimaryKey(mapperDemo);

    }

    //查询全部
    public List<MapperDemo> getAll(){
        return mapperDemoMapper.selectAll();
    }
}
