package com.example.webcrawler.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LinkNodeDto implements Serializable {

    @JsonProperty("url")
    private String url;

    @JsonProperty("title")
    private String title;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNodes(List<LinkNodeDto> nodes) {
        this.nodes = nodes;
    }

    @JsonProperty("nodes")
    private List<LinkNodeDto> nodes = new ArrayList<>();

    public List<LinkNodeDto> getNodes() {
        return nodes;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("class PageTreeInfo {\n");

        sb.append("    url: ").append((url)).append("\n");
        sb.append("    title: ").append((title)).append("\n");
        sb.append("    nodes: ").append((nodes)).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
