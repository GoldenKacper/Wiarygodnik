package org.wiarygodnik.credabilityservice.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wiarygodnik.credabilityservice.application.UrlService;

@Service
@Slf4j
public class RabbitConsumer {
    private final UrlService urlService;

    @Autowired
    public RabbitConsumer(UrlService urlService) {
        this.urlService = urlService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_CONTENT)
    public void receiveContent(UrlContentDTO urlContentDTO) {
        urlService.handleUrlContent(new org.wiarygodnik.credabilityservice.application.UrlContent(urlContentDTO.reportId(), urlContentDTO.url(), urlContentDTO.content(), urlContentDTO.keywords()));
        log.info("URL content received");
    }
}
