package org.wiarygodnik.credabilityservice.presentation;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "wiarygodnik.exchange";
    public static final String QUEUE_CONTENT = "credibility.service.content";
    public static final String ROUTING_CONTENT = "routing.content";
    public static final String QUEUE_RESULTS = "credibility.service.results";
    public static final String ROUTING_RESULTS = "routing.results";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue keywordsQueue() {
        return new Queue(QUEUE_CONTENT, false);
    }

    @Bean
    public Queue resultsQueue() {
        return new Queue(QUEUE_RESULTS, false);
    }

    @Bean
    public Binding contentBinding(@Qualifier("keywordsQueue") Queue urlQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(urlQueue)
                .to(exchange)
                .with(ROUTING_CONTENT);
    }

    @Bean
    public Binding resultsBinding(@Qualifier("resultsQueue") Queue resultsQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(resultsQueue)
                .to(exchange)
                .with(ROUTING_RESULTS);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}