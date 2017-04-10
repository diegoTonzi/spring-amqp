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
public class TwoQueueConfig {

    public static final String BROADCAST_TWO_QUEUE = "rabbit.two.queue";
    public static final String BROADCAST_TWO_DLQ = "rabbit.two.queue.dlq";

    private @Autowired ConnectionFactory connectionFactory;
    private @Autowired RabbitTemplate rabbitTemplate;
    private @Autowired FanoutExchange fanoutExchange;

    @Bean
    public Queue twoQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", BROADCAST_TWO_DLQ);
        Queue queue = new Queue(BROADCAST_TWO_QUEUE, true, false, false, arguments);
        return queue;
    }

    @Bean
    public Binding twoBinding() {
        return BindingBuilder.bind(twoQueue()).to(fanoutExchange);
    }

    @Bean
    public Queue deadLetterTwoQueue() {
        return new Queue(BROADCAST_TWO_DLQ, true);
    }

    @Bean
    public Binding deadLetterTwoBinding() {
        return BindingBuilder.bind(deadLetterTwoQueue()).to(fanoutExchange);
    }

    @Bean
    @Qualifier("twoContainerFactory")
    public SimpleRabbitListenerContainerFactory twoContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        Advice[] adviceChain = { twoInterceptor() };
        factory.setAdviceChain(adviceChain);
        return factory;
    }

    @Bean
    RetryOperationsInterceptor twoInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .backOffOptions(3000L, 3L, 10000L)
                .maxAttempts(5)
                .recoverer(new RepublishMessageRecoverer(rabbitTemplate, deadLetterTwoQueue().getName(), deadLetterTwoQueue().getName()))
                .build();
    }

}
