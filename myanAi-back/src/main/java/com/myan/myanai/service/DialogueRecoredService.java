package com.myan.myanai.service;

import com.myan.myanai.dao.ContentMapper;
import com.myan.myanai.dao.DialogueRecordMapper;
import com.myan.myanai.entity.DialogueRecordDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DialogueRecoredService {

    @Autowired
    private DialogueRecordMapper dialogueRecordMapper;

    @Autowired
    private ContentMapper contentMapper;

    public void insertData(String issue, String reply, Date issueTime, Date replyTime) {
        DialogueRecordDO dialogueRecordDO = new DialogueRecordDO();
        dialogueRecordDO.setIssue(issue);
        dialogueRecordDO.setReply(reply);
        dialogueRecordDO.setIssueTime(issueTime);
        dialogueRecordDO.setReplyTime(replyTime);
        dialogueRecordMapper.insert(dialogueRecordDO);
    }

}
