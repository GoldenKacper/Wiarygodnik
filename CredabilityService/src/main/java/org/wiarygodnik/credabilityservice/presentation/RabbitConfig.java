package org.wiarygodnik.credabilityservice.presentation;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "wiarygodnik.exchange";
    public static final String QUEUE_CONTENT = "content.fetch.result";
    public static final String ROUTING_CONTENT = "routing.content";
    public static final String QUEUE_RESULTS = "credibility.service.results.queue";
    public static final String ROUTING_RESULTS = "routing.results";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue keywordsQueue() {
        return new Queue(QUEUE_CONTENT);
    }

    @Bean
    public Queue resultsQueue() {
        return new Queue(QUEUE_RESULTS);
    }

    @Bean
    public Binding contentBinding(@Qualifier("keywordsQueue") Queue urlQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(urlQueue)
                .to(exchange)
                .with(ROUTING_CONTENT);
    }

    @Bean
    public Binding resultsBinding(@Qualifier("resultsQueue") Queue resultsQueue, TopicExchange exchange) {
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