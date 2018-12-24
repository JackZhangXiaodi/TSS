package com.baizhi.dao;

import com.baizhi.entity.Poetry;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PoetryDAO {
//    查询所有的诗句
    List<Poetry> queryAll();
}
