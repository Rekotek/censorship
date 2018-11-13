package com.scriptorium.censorship.frontend.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by taras on 2018-11-13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppSettingsServiceTest {
    private static final long LAST_SIZE = 11385856;

    @Autowired
    private AppSettingsService settingsService;

    @Test
    public void isNewDatabase() {
        boolean newDatabase = settingsService.isNewDatabase();
        Assert.assertTrue(newDatabase);
    }
}