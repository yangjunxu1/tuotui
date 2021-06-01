package com.zycw.tuotui.iface.login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * @author Inspiry
 * Created on 2021/6/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ILoginServiceTest {

    @Autowired
    private ILoginService loginService;

    @Test
    public void testLogin() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("auUserMobile", "184101792561");
        map.put("auUserPassword", "123456");
//        map.put("code", "123456");
        map.put("auUserUseruuid", "741852");
        String login = loginService.login(map);
        System.out.println(login);
    }


}