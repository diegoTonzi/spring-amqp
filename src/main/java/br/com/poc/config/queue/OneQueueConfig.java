package br.com.poc.config.queue;

import br.com.poc.config.AmqpConfig;
import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OneQueueConfig {

    private static final String HEADER_FROM = "from";
    private static final String HEADER_TO = "to";
    public static final String QUEUE_ONE = "rabbit.one.queue";
    public static final String QUEUE_ONE_DLQ = "rabbit.one.queue.dlq";

    private @Autowired ConnectionFactory connectionFactory;
    private @Autowired RabbitTemplate rabbitTemplate;
    private @Autowired DirectExchange directExchange;
    private @Autowired HeadersExchange headersExchange;

    @Bean
    public Queue queueOne() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", QUEUE_ONE_DLQ);
        return new Queue(QUEUE_ONE, true, false, false, arguments);
    }

    @Bean
    Binding bindingQueueOne() {
        return BindingBuilder.bind(queueOne()).to(headersExchange).whereAll(HEADER_FROM, HEADER_TO).exist();
    }

    @Bean
    public Queue queueOneDlq() {
        return new Queue(QUEUE_ONE_DLQ, true);
    }

    @Bean
    Binding bindingDlq() {
        return BindingBuilder.bind(queueOneDlq()).to(directExchange).withQueueName();
    }

    @Bean
    @Qualifier("oneContainerFactory")
    public SimpleRabbitListenerContainerFactory oneContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        Advice[] adviceChain = { oneInterceptor() };
        factory.setAdviceChain(adviceChain);
        return factory;
    }

    @Bean
    RetryOperationsInterceptor oneInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .backOffOptions(3000L, 3L, 10000L)
                .maxAttempts(5)
                .recoverer(new RepublishMessageRecoverer(rabbitTemplate, directExchange.getName(), queueOneDlq().getName()))
                .build();
    }

}
