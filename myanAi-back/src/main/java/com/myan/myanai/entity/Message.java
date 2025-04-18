package com.myan.myanai.entity;

import lombok.Data;

@Data
public class Message {

    /**
     * 提问/指令 内容
     */
    private String content;
    /**
     * 角色
     */
    private String role;

}
