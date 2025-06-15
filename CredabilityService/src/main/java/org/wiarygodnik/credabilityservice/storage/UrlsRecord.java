package org.wiarygodnik.credabilityservice.storage;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
public class UrlsRecord {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String originalUrl;

    @Setter
    @Getter
    @ElementCollection
    @CollectionTable(name = "related_urls", joinColumns = @JoinColumn(name = "record_id"))
    @Column(name = "url")
    private List<String> relatedUrls;

    public UrlsRecord() {}

    public UrlsRecord(String originalUrl, List<String> relatedUrls) {
        this.originalUrl = originalUrl;
        this.relatedUrls = relatedUrls;
    }
}