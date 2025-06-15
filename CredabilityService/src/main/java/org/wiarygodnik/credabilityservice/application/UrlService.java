package org.wiarygodnik.credabilityservice.application;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wiarygodnik.credabilityservice.presentation.RabbitProducer;
import org.wiarygodnik.credabilityservice.storage.UrlsRepository;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UrlService {
    private final RabbitProducer rabbitProducer;
    private final UrlsRepository urlsRepository;

    @Autowired
    public UrlService(RabbitProducer rabbitProducer, UrlsRepository urlsRepository) {
        this.rabbitProducer = rabbitProducer;
        this.urlsRepository = urlsRepository;
    }

    public List<String> handleUrlContent(UrlContent urlContent) {
        List<String> urls = getUrlsBasedOnKeywords(urlContent.keywords());
        urlsRepository.save(urlContent.url(), urls);
        rabbitProducer.sendUrls(urls, urlContent.reportId());

        return urls;
    }

    private List<String> getUrlsBasedOnKeywords(List<String> keywords) {
        try {
            String url = "https://html.duckduckgo.com/html/?q=" + String.join("+", keywords);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            List<String> urlsBasedOnKeywords = new ArrayList<>();

            for (Element result : doc.select("a.result__a")) {
                String href = result.attr("href");

                if (href.contains("uddg=")) {
                    String encodedUrl = href.substring(href.indexOf("uddg=") + 5);
                    String decodedUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);

                    urlsBasedOnKeywords.add(decodedUrl);
                }

                if (urlsBasedOnKeywords.size() >= 6) break;
            }

            return urlsBasedOnKeywords;
        } catch (IOException e) {
            log.error("Error getting URLs based on keywords!", e);
            return Collections.emptyList();
        }
    }
}
