package com.myan.myanai.dao;

import com.myan.myanai.entity.ContentDO;

public interface ContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ContentDO record);

    int insertSelective(ContentDO record);

    ContentDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContentDO record);

    int updateByPrimaryKeyWithBLOBs(ContentDO record);

    int updateByPrimaryKey(ContentDO record);
}