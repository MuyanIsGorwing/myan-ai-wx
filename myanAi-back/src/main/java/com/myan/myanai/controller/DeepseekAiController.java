package com.myan.myanai.controller;

import com.myan.myanai.common.R;
import com.myan.myanai.dto.req.UserIssueReq;
import com.myan.myanai.dto.resq.DsAiResq;
import com.myan.myanai.service.DeepseekAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ds")
public class DeepseekAiController {

    @Autowired
    private DeepseekAiService deepseekAiService;

    @RequestMapping(value = "/simple-questions",method = RequestMethod.GET)
    public R simpleQuestions(UserIssueReq userIssueReq){
        return deepseekAiService.sendCompletions(userIssueReq);
    }

}
