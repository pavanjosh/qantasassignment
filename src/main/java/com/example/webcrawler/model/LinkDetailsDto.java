package com.example.webcrawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import org.jsoup.select.Elements;

@Data
@AllArgsConstructor
public class LinkDetailsDto implements Serializable {

    private String title;

    private String url;

    private Elements links;
}
