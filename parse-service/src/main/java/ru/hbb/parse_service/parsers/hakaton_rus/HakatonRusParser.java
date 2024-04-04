package ru.hbb.parse_service.parsers.hakaton_rus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.hbb.parse_service.HakatonInfo;
import ru.hbb.parse_service.WebParser;
import ru.hbb.parse_service.parsers.AbstractParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HakatonRusParser extends AbstractParser implements WebParser {

    private static final String patternURL = "/tpost/";

    public HakatonRusParser() {
        super("https://www.xn--80aa3anexr8c.xn--p1acf/", "ru", 1000);
    }

    // TODO: 25.03.2022 добавить регулярное выражение для ссылок
    @Override
    public List<String> getAllUrls() {
        WebDriver driver = new ChromeDriver();
        driver.get(this.URL);
        try {
            Thread.sleep(this.TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Document doc = Jsoup.parse(driver.getPageSource());

        Elements links = doc.select("a");

        List<String> result = new ArrayList<>();
        for (Element link : links) {
            String href = link.attr("href");
            if (href.contains(patternURL)) {
                result.add(href);
            }
        }

        driver.close();

        return result;
    }

    /**
     * Получение информации о хакатоне с сайта
     * @param url полная ссылка на страницу
     * @return информация о хакатоне
     */
    @Override
    public HakatonInfo getHakatonInfo(String url) throws IOException {
        if (url == null) {
            return null;
        }

        WebDriver driver = new ChromeDriver();
        driver.get(url);
        try {
            Thread.sleep(this.TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Document doc = Jsoup.parse(driver.getPageSource());

        Elements titles = doc.select("h1");
        String title = titles.getFirst().text();
        Elements descriptions = doc.select("div.t-redactor__text");
        String description = descriptions.text();
        Element hakatonUrlDiv = doc.selectFirst("div.t-redactor__embedcode");
        String hakatonUrl = hakatonUrlDiv.select("a").attr("href");

        driver.close();

        return new HakatonInfo(title, url, hakatonUrl, description, 0, 0, 0, 0);
    }
}
