package com.example.webcrawler.service;

import com.example.webcrawler.exception.InvalidURLException;
import com.example.webcrawler.model.LinkNodeDto;
import com.example.webcrawler.util.CannedData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)

@SpringBootTest(
        classes = CrawlerService.class,
        properties = {"crawler.depth=10"})
public class CrawlerServiceTest {
    @Autowired
    private CrawlerService crawlerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test(expected = InvalidURLException.class)
    public void shouldThrowInvalidUrlException() throws IOException {
        crawlerService.crawl("www.test.com",10,null);
    }
    @Test
    public void shouldRetunrNullforDepthLessThan0() throws IOException{
        LinkNodeDto crawl = crawlerService.crawl("http://www.test.com", -1, null);
        Assert.assertEquals(null,crawl);
    }
    @Test
    public void shouldRetunLinkNodeDto() throws IOException{
        LinkNodeDto crawl = crawlerService.crawl("http://www.google.com", 2, null);
        Assert.assertEquals(objectMapper.writeValueAsString(crawl), CannedData.getCannedDataForSuccessGoogle());

    }
}
