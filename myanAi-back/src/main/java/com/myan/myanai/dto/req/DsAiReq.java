package com.myan.myanai.dto.req;

import com.myan.myanai.entity.Message;
import lombok.Data;

import java.util.List;

@Data
public class DsAiReq {

    /**
     * 对话的消息列表
     */
    private List<Message> messages;

    private String model;
//
//    private Integer frequency_penalty;
//
    private Integer max_tokens;
//
//    private Integer presence_penalty;
//
//    private ResponseFormat response_format;
//
//    private Object stop;
//
//    private Boolean stream;
//
//    private Object stream_options;


}
