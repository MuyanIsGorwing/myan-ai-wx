package com.myan.myanai.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.myan.myanai.common.R;
import com.myan.myanai.config.ApiConfig;
import com.myan.myanai.constant.DsApiConstant;
import com.myan.myanai.dto.req.DsAiReq;
import com.myan.myanai.dto.resq.DsAiResq;
import com.myan.myanai.entity.DialogueRecordDO;
import com.myan.myanai.entity.Message;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SendMsgToAiUtil {

    private static final String systemType = "你是myanAi的一个人工智能体，为用户解答各种疑惑";


    private static final Request.Builder dsRequest;
    private final ApiConfig apiConfig;

    public SendMsgToAiUtil(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    static {
        dsRequest = new Request.Builder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    public Response send(String context) {
        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS).build();
        Request request = createRequest(context);
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }


    private  Request createRequest(String context) {
        MediaType mediaType = MediaType.parse("application/json");
        Message system = new Message();
        system.setContent(systemType);
        system.setRole("system");
        Message user = new Message();
        user.setContent(context);
        user.setRole("user");

        ArrayList<Message> messages = new ArrayList<>();
        messages.add(system);
        messages.add(user);

        DsAiReq dsAiReq = new DsAiReq();
        dsAiReq.setMessages(messages);
        dsAiReq.setModel("deepseek-chat");
        String key = apiConfig.getKey();
        String url = apiConfig.getUrl();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(dsAiReq));
        Request request = dsRequest
                .method("POST", body)
                .url(url)
                .header("Authorization", "Bearer "+key)
                .build();
        return request;
    }
}
