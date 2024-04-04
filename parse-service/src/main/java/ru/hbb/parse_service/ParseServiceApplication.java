package ru.hbb.parse_service;

import ru.hbb.parse_service.parsers.hakaton_rus.HakatonRusParser;

import java.io.IOException;

public class ParseServiceApplication {
    public static void main(String[] args) {
        WebParser webParser = new HakatonRusParser();
        for (String url : webParser.getAllUrls()) {
            try {
                System.out.println(webParser.getHakatonInfo(url).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //String[] urls = webParser.getAllUrls();
        //for (String url : urls) {
        //    System.out.println(webParser.getHakatonInfo(url).toString());
        //}
    }
}