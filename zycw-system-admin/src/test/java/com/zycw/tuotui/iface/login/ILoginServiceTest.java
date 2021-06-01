package com.zycw.tuotui.iface.login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        String login = loginService.login("184101792561", "123456", "123456", "741852");
        System.out.println(login);
    }


}