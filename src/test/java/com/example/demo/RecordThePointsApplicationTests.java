package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.service.impl.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecordThePointsApplicationTests {

    @Test
    public void contextLoads() {
    }

    /**
     * Spring RestTemplate的便利替代。你可以获取一个普通的或发送基本HTTP认证（使用用户名和密）的模板
     * 这里不使用
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    TaskService taskService;


    @Test
    public void test() {
        User user=new User();
        user.setId(Long.valueOf(8));
        Long l=taskService.score(user);
        System.out.println("score:" + l);
        System.out.println("-----测试完毕-------");

    }
}
