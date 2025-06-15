package org.wiarygodnik.credabilityservice.application;

import java.util.List;

public record UrlContent(int reportId, String url, String content, List<String> keywords) {
}
