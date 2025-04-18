package com.myan.myanai.service;

import com.myan.myanai.dao.ContentMapper;
import com.myan.myanai.entity.ContentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
public class ContentService {

    @Autowired
    private ContentMapper contentMapper;

    @Transactional
    public void insertResultDate(String content, String type, String contentId) {
        ContentDO contentDO = new ContentDO();
        contentDO.setContentId(contentId);
        contentDO.setContent(content);
        contentDO.setContentType(type);
        contentMapper.insert(contentDO);
    }

}
