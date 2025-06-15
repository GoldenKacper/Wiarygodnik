package org.wiarygodnik.credabilityservice.presentation;

import java.util.List;

public record UrlsListDTO (int reportId, List<String> urls) {
}
