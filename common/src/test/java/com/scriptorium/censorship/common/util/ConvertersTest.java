package com.scriptorium.censorship.common.util;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;

public class ConvertersTest extends TestCase {

    public void testExtractTargetUrlFromHtml() throws IOException {
        String testHtml = "http://comin.kmu.gov.ua/control/publish/article/system?art_id=141661&cat_id=141660";
        String keyWord = "РЕЄСТР";
        String url = Converters.extractTargetFromUrl(testHtml, keyWord);
        Assert.assertNotNull(url);
        Assert.assertTrue(url.contains("Goods"));
    }
}