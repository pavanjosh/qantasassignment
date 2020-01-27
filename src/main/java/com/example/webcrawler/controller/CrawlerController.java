package com.example.webcrawler.controller;

import com.example.webcrawler.exception.MaximumDepthExceededException;
import com.example.webcrawler.model.LinkNodeDto;
import com.example.webcrawler.service.CrawlerServiceIF;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;


@RestController
@Slf4j
public class CrawlerController {

    @Value("${crawler.depth}")
    private Integer defaultDepth;

    @Autowired
    private CrawlerServiceIF crawlerService;


    public static final String LINK_URL = "/link";
    @GetMapping(LINK_URL)
    public ResponseEntity<LinkNodeDto> crawl(@NotNull @RequestParam(value = "url", required = true) final String url,
                                             @RequestParam(value = "depth", required = false) final Integer depth)
            throws IOException, MaximumDepthExceededException
    {
        // This line is to make sure that the maximum depth reached is 10.
        // If depth is given as a request parameter, then take the minimum of the given or default depth (10)
        // if depth is not given then take 10 as the default depth
        if(Optional.ofNullable(depth).isPresent()){
            if(depth>defaultDepth){
                throw new MaximumDepthExceededException("Sorry: The maximum depth allowed for test purpose is 10, " +
                        "For more depth lets run on a powerful machine");
            }
        }
        final int finalDepth = Integer.min(Optional.ofNullable(depth).orElse(defaultDepth),defaultDepth);
        log.info("The link to deep crawl: {}, with depth: {}", url, finalDepth);
        return ResponseEntity.status(HttpStatus.OK).body(crawlerService.crawl(url,finalDepth,null));
    }
}
