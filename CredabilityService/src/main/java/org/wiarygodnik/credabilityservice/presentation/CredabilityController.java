package org.wiarygodnik.credabilityservice.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wiarygodnik.credabilityservice.application.UrlContent;
import org.wiarygodnik.credabilityservice.application.UrlService;

import java.util.Arrays;
import java.util.List;

//----------------------
// FOR TESTING ONLY
//----------------------
@RestController
public class CredabilityController {
    private UrlService urlService;

    @Autowired
    public CredabilityController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/findUrls")
    public ResponseEntity<String> newEmployee(@RequestBody String keywords) {
        List<String> keywordsList = Arrays.asList(keywords.split("-"));
        List<String> urls = urlService.handleUrlContent(new UrlContent(0, "test.url", "test", keywordsList));
        return ResponseEntity.ok(String.join(" ", urls));
    }
}
