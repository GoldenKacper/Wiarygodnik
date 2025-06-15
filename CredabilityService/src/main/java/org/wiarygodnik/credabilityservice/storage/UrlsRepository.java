package org.wiarygodnik.credabilityservice.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class UrlsRepository {
    private final UrlsPostgresJpaRepository jpaRepository;

    @Autowired
    public UrlsRepository(UrlsPostgresJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public List<String> getUrlsByOriginalUrl(String originalUrl) {
        return jpaRepository.getUrlsByOriginalUrl(originalUrl).map(UrlsRecord::getRelatedUrls).orElse(null);
    }

    public void save(String originalUrl, List<String> relatedUrls) {
        jpaRepository.save(new UrlsRecord(originalUrl, relatedUrls));
        log.info("Saved related links for {}", originalUrl);
    }
}
