package com.example.webcrawler.service;

import com.example.webcrawler.model.LinkNodeDto;

import java.io.IOException;
import java.util.List;

public interface CrawlerServiceIF {

    LinkNodeDto crawl(final String url,int depth,List<String> linksProcessed) throws IOException;
}
