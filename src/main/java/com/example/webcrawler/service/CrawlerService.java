package com.example.webcrawler.service;

import com.example.webcrawler.exception.InvalidURLException;
import com.example.webcrawler.model.LinkDetailsDto;
import com.example.webcrawler.model.LinkNodeDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.UrlValidator;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CrawlerService implements CrawlerServiceIF {

  @Override
  @Cacheable("url")
  public LinkNodeDto crawl(String url, int depth, List<String> linksProcessed)
      throws IOException, InvalidURLException {
    validateUrl(url);
    if (depth < 0) {
      return null;
    } else {
      List<String> newLinksProcessed =
          Optional.ofNullable(linksProcessed).orElse(new ArrayList<>());
      if (!newLinksProcessed.contains(url)) {
        newLinksProcessed.add(url);
        LinkNodeDto linkNodeDto = new LinkNodeDto();
        linkNodeDto.setUrl(url);
        Optional<LinkDetailsDto> linkDetailsDto = crawlForALink(url);

        if (linkDetailsDto.isPresent()) {
          LinkDetailsDto liDto = linkDetailsDto.get();
          log.info("links present {}", liDto.getLinks().size());
          linkNodeDto.setTitle(liDto.getTitle());

          int count = 0;
          for (Element ele : liDto.getLinks()) {
            count++;
            LinkNodeDto crawledLinkDetailes =
                crawl(ele.attr("abs:href"), depth - 1, newLinksProcessed);
            log.debug("after crawl {}", crawledLinkDetailes);
            if (crawledLinkDetailes != null) linkNodeDto.getNodes().add(crawledLinkDetailes);
            if (count >= depth) break;
          }
        }

        log.info("returning dto {}", linkNodeDto);
        return linkNodeDto;
      } else {
        return null;
      }
    }
  }

  private Optional<LinkDetailsDto> crawlForALink(final String url) throws IOException {

    log.info("Fetching contents for url: {}", url);

    final Document doc = Jsoup.connect(url).timeout(500000).followRedirects(true).get();

    /** .select returns a list of links here * */
    final Elements links = doc.select("a[href]");
    final String title = doc.title();
    return Optional.of(new LinkDetailsDto(title, url, links));
  }

  private String validateUrl(String url) {
    UrlValidator urlValidator = new UrlValidator();
    if (!urlValidator.isValid(url)) {
      log.error(
          "Invalid url specified, Sample url should be http://xyz.com or https://example.com {}",url);
      throw new InvalidURLException(
          "Invalid url specified, Sample url should be http://xyz.com or https://example.com "+ url);

    } else {
      return url;
    }
  }
}
