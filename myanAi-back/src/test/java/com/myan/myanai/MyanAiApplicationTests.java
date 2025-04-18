package com.myan.myanai;

import com.myan.myanai.config.ApiConfig;
import com.myan.myanai.dto.resq.DsAiResq;
import com.myan.myanai.service.DeepseekAiService;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableConfigurationProperties({ApiConfig.class})
public class MyanAiApplicationTests {
	@Autowired
	private DeepseekAiService deepseekAiService;

	@Test
	public void contextLoads() {

//		deepseekAiService.sendCompletions("111");
//		System.out.println(dsAiResq);
	}

}
