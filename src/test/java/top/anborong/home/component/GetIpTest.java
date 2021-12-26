package top.anborong.home.component;

import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.anborong.home.component.GetIp;


@SpringBootTest
class GetIpTest {



    @Autowired
    public GetIp getIp;

    @Test
    void contextLoads() {
        getIp.ip();
    }
}