package com.myan.myanai.dao;

import com.myan.myanai.entity.DialogueRecordDO;

public interface DialogueRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DialogueRecordDO record);

    int insertSelective(DialogueRecordDO record);

    DialogueRecordDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DialogueRecordDO record);

    int updateByPrimaryKey(DialogueRecordDO record);
}