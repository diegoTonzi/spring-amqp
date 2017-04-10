package br.com.poc.config.queue;

import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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

    public static final String BROADCAST_ONE_QUEUE = "rabbit.one.queue";
    public static final String BROADCAST_ONE_QUEUE_DLQ = "rabbit.one.queue.dlq";

    private @Autowired ConnectionFactory connectionFactory;
    private @Autowired RabbitTemplate rabbitTemplate;
    private @Autowired FanoutExchange fanoutExchange;

    @Bean
    public Queue oneQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", BROADCAST_ONE_QUEUE_DLQ);
        Queue queue = new Queue(BROADCAST_ONE_QUEUE, true, false, false, arguments);
        return queue;
    }

    @Bean
    public Binding oneBinding() {
        return BindingBuilder.bind(oneQueue()).to(fanoutExchange);
    }

    @Bean
    Queue deadLetterQueueOne() {
        return new Queue(BROADCAST_ONE_QUEUE_DLQ, true);
    }

    @Bean
    public Binding deadLetterOneBinding() {
        return BindingBuilder.bind(deadLetterQueueOne()).to(fanoutExchange);
    }

    @Bean
    @Qualifier("oneContainerFactory")
    public SimpleRabbitListenerContainerFactory oneContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        Advice[] adviceChain = { oneInterceptor() };
        factory.setAdviceChain(adviceChain);
        return factory;
    }

    @Bean
    RetryOperationsInterceptor oneInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .backOffOptions(3000L, 3L, 10000L)
                .maxAttempts(5)
                .recoverer(new RepublishMessageRecoverer(rabbitTemplate, deadLetterQueueOne().getName(), deadLetterQueueOne().getName()))
                .build();
    }

}
