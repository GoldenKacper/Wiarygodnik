package pl.edu.p.lodz.wiarygodnik.rgs.amqp

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {

    companion object {
        const val EXCHANGE_NAME: String = "wiarygodnik.exchange"
        const val ANALYSIS_RESULTS_QUEUE: String = "content-analysis-service.results.queue"
        const val ANALYSIS_ROUTING_KEY: String = "analysis.key"
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    fun contentAnalysisServiceResultsQueue(): Queue {
        return Queue(ANALYSIS_RESULTS_QUEUE)
    }

    @Bean
    fun reportCreatedBinding(): Binding {
        return BindingBuilder.bind(contentAnalysisServiceResultsQueue()).to(exchange()).with(ANALYSIS_ROUTING_KEY)
    }

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = jsonMessageConverter()
        return template
    }

}