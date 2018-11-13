package com.scriptorium.censorship.frontend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by taras on 2018-11-13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MainApplicationTest {

    @Value("${censorship.log-dir}")
    private String urlCensorship;

    @Test
    public void contextLoaded() {
        System.out.println("Environment: " + urlCensorship);
    }
}