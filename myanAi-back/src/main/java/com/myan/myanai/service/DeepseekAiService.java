package com.myan.myanai.service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.myan.myanai.common.R;
import com.myan.myanai.config.ApiConfig;
import com.myan.myanai.constant.DsApiConstant;
import com.myan.myanai.dao.ContentMapper;
import com.myan.myanai.dao.DialogueRecordMapper;
import com.myan.myanai.dto.req.DsAiReq;
import com.myan.myanai.dto.req.UserIssueReq;
import com.myan.myanai.dto.resq.DsAiResq;
import com.myan.myanai.entity.Choice;
import com.myan.myanai.entity.ContentDO;
import com.myan.myanai.entity.DialogueRecordDO;
import com.myan.myanai.entity.Message;
import com.myan.myanai.utils.SendMsgToAiUtil;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

@Service
public class DeepseekAiService {

    @Autowired
    private DialogueRecordMapper dialogueRecordMapper;

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private ContentService contentService;

    @Autowired
    private DialogueRecoredService dialogueRecoredService;

    @Autowired
    private ApiConfig apiConfig;

    private final Gson gson;

    public DeepseekAiService(Gson gson) {
        this.gson = gson;
    }

    @Transactional
    public R sendCompletions(UserIssueReq userIssueReq) {
        Date issueTime = new Date();
        SendMsgToAiUtil sendMsgToAiUtil = new SendMsgToAiUtil(apiConfig);
        Response response = sendMsgToAiUtil.send(userIssueReq.getContent());
        R r = encaResult(response);
        Date date = new Date();
        String issue = createContentId("issue");
        contentService.insertResultDate(userIssueReq.getContent(),"1",issue);
        if (r.getCode() == 200){
            DsAiResq data = (DsAiResq) r.getData();
            Choice choice = data.getChoices().get(0);
            String reply = createContentId("reply");
            contentService.insertResultDate(choice.getMessage().getContent(),"2",reply);
            dialogueRecoredService.insertData(issue,reply,issueTime,date);

        }else {
            String error = createContentId("error");
            contentService.insertResultDate(r.getMsg(),"3",error);
            dialogueRecoredService.insertData(issue,error,issueTime,date);
        }
        return r;
    }

    private R encaResult(Response response){
        if (response.code() != 200){
            switch (response.code()) {
                case 400:
                    return new R().fail(400, DsApiConstant.DS_API_ERROR_400);
                case 401:
                    return new R().fail(401, DsApiConstant.DS_API_ERROR_401);
                case 402:
                    return new R().fail(402, DsApiConstant.DS_API_ERROR_402);
                case 422:
                    return new R().fail(422, DsApiConstant.DS_API_ERROR_422);
                case 429:
                    return new R().fail(429, DsApiConstant.DS_API_ERROR_429);
                case 500:
                    return new R().fail(500, DsApiConstant.DS_API_ERROR_500);
                case 503:
                    return new R().fail(503, DsApiConstant.DS_API_ERROR_503);
                default:
                    return new R().fail(9999, "错误");
            }
        }
        DsAiResq dsAiResq = null;
        try {
            dsAiResq = gson.fromJson(response.body().string(), DsAiResq.class);
        } catch (IOException e) {
            if (e instanceof SocketTimeoutException) {
                // 处理超时
                return new R().fail(9999, "网络请求超时，请稍后再试");
            }
            throw new RuntimeException(e);
        }
        return new R().ok(dsAiResq);
    }

    private String createContentId(String issueOrReply){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String contentId = issueOrReply + LocalDateTime.now().format(dtf);
        return contentId;
    }

}
