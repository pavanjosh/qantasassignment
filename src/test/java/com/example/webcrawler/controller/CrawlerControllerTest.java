package com.example.webcrawler.controller;

import com.example.webcrawler.exception.ErrorResponse;
import com.example.webcrawler.exception.InvalidURLException;
import com.example.webcrawler.model.LinkDetailsDto;
import com.example.webcrawler.model.LinkNodeDto;
import com.example.webcrawler.service.CrawlerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = CrawlerController.class)
public class CrawlerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String htmlPayload;

    @Autowired private ObjectMapper objectMapper;


    @MockBean
    private CrawlerService crawlerService;

    private LinkNodeDto linkNodeDto;
    private LinkDetailsDto linkDetailsDto;


    @Before
    public void setUp() throws Exception {
        linkNodeDto = new LinkNodeDto();

    }

  @Test
  public void shouldThrowExceededException() throws Exception{
    this.mockMvc
        .perform(
            get(CrawlerController.LINK_URL)
                .param("url", "www.gmail.com")
                .param("depth", "11")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .json(objectMapper.writeValueAsString(new ErrorResponse(CrawlerControllerAdvice.MAXIMUM_DEPTH_EXCEEDED,
                        "Sorry: The maximum depth allowed for test purpose is 10, For more depth lets run on a powerful machine"))));
  }

  @Test
  public void shouldThrowInvalidUrlException() throws Exception {

        when(crawlerService.crawl(anyString(), anyInt(),any()))
              .thenThrow(new InvalidURLException("Invalid url specified, Sample url should be http://xyz.com or https://example.com"));
    this.mockMvc
        .perform(
            get(CrawlerController.LINK_URL)
                .param("url", "www.gmail.com")
                .param("depth", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .json(
                        objectMapper.writeValueAsString(
                        new ErrorResponse(
                            CrawlerControllerAdvice.INVALID_URL_ERROR_CODE,
                            "Invalid url specified, Sample url should be http://xyz.com or https://example.com"))));
  }

    @Test
    public void shouldReturnSuccess() throws Exception {

        when(crawlerService.crawl(anyString(), anyInt(),any()))
                .thenReturn(new LinkNodeDto());

        this.mockMvc
                .perform(
                        get(CrawlerController.LINK_URL)
                                .param("url", "www.gmail.com")
                                .param("depth", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .json(
                                        objectMapper.writeValueAsString(
                                                new LinkNodeDto())));
    }
}
