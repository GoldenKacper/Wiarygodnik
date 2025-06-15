package org.wiarygodnik.credabilityservice.presentation;

import java.util.List;

public record UrlContentDTO(int reportId, String url, String content, List<String> keywords) {
}
