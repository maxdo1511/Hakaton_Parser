package ru.hbb.parse_service;

import java.io.IOException;
import java.util.List;

public interface WebParser {

    List<String> getAllUrls();
    HakatonInfo getHakatonInfo(String url) throws IOException;

}
