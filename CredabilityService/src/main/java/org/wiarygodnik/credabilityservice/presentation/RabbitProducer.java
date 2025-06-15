package org.wiarygodnik.credabilityservice.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RabbitProducer {
    private final AmqpTemplate amqpTemplate;

    @Autowired
    public RabbitProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendUrls(List<String> urls, int reportId) {
        amqpTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_RESULTS,
                new UrlsListDTO(reportId, urls)
        );
        log.info("Links sent for reportId: {}", reportId);
    }
}
