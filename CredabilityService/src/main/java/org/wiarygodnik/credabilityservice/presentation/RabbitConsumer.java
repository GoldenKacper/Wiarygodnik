package org.wiarygodnik.credabilityservice.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wiarygodnik.credabilityservice.application.UrlService;

import java.util.List;

@Service
@Slf4j
public class RabbitConsumer {
    private final UrlService urlService;

    @Autowired
    public RabbitConsumer(UrlService urlService) {
        this.urlService = urlService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_CONTENT)
    public void receiveContent(List<UrlContentDTO> urlContentDTO) {
        var dto = urlContentDTO.getFirst();
        urlService.handleUrlContent(new org.wiarygodnik.credabilityservice.application.UrlContent(dto.reportId(), dto.url(), null, List.of("test")));
        log.info("URL content received");
    }
}
