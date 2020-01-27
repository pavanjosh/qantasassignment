package com.example.webcrawler.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CannedData {

    private ObjectMapper objectMapper;

    public static String getCannedDataForSuccessGoogle() throws JsonProcessingException {
    String response =
        "{\"url\":\"http://www.google.com\",\"title\":\"Google\",\"nodes\":[{\"url\":\"https://about.google/?fg=1&utm_source=google-AU&utm_medium=referral&utm_campaign=hp-header\",\"title\":\"About - Google\",\"nodes\":[{\"url\":\"https://about.google/\",\"title\":\"About - Google\",\"nodes\":[]}]},{\"url\":\"https://store.google.com/AU?utm_source=hp_header&utm_medium=google_ooo&utm_campaign=GS100042&hl=en-AU\",\"title\":\"Google Store for Google Made Devices & Accessories\",\"nodes\":[{\"url\":\"https://store.google.com/collection/offers\",\"title\":\"Bundles and special offers on Google devices – Google Store\",\"nodes\":[]}]}]}";
        return response;

    }
}
