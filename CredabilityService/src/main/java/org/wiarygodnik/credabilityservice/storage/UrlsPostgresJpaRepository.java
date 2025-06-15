package org.wiarygodnik.credabilityservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlsPostgresJpaRepository extends JpaRepository<UrlsRecord, Long> {
    @Query("SELECT u FROM UrlsRecord u WHERE u.originalUrl = :url")
    Optional<UrlsRecord> getUrlsByOriginalUrl(@Param("url") String url);
}
