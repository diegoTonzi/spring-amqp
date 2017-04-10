package br.com.poc.config.queue;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TwoQueueConfig {

//    public static final String QUEUE_TWO = "rabbit.two.queue";
//    public static final String QUEUE_TWO_DLQ = "rabbit.two.queue.dlq";
//
//    private @Autowired ConnectionFactory connectionFactory;
//    private @Autowired RabbitTemplate rabbitTemplate;
//    private @Autowired TopicExchange exchange;
//
//    @Bean
//    public Queue twoQueue() {
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-dead-letter-exchange", QUEUE_TWO_DLQ);
//        Queue queue = new Queue(QUEUE_TWO, true, false, false, arguments);
//        return queue;
//    }
//
//    @Bean
//    public Binding twoBinding() {
//        return BindingBuilder.bind(twoQueue()).to(exchange).with(twoQueue().getName());
//    }
//
//    @Bean
//    public Queue deadLetterTwoQueue() {
//        return new Queue(QUEUE_TWO_DLQ, true);
//    }
//
//    @Bean
//    public Binding deadLetterOneBinding() {
//        return BindingBuilder
//                .bind(deadLetterTwoQueue())
//                .to(new TopicExchange(deadLetterTwoQueue().getName()))
//                .with(deadLetterTwoQueue().getName());
//    }
//
//    @Bean
//    @Qualifier("twoContainerFactory")
//    public SimpleRabbitListenerContainerFactory twoContainerFactory() {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setConcurrentConsumers(3);
//        factory.setMaxConcurrentConsumers(10);
//        Advice[] adviceChain = { twoInterceptor() };
//        factory.setAdviceChain(adviceChain);
//        return factory;
//    }
//
//    @Bean
//    RetryOperationsInterceptor twoInterceptor() {
//        return RetryInterceptorBuilder.stateless()
//                .backOffOptions(3000L, 3L, 10000L)
//                .maxAttempts(5)
//                .recoverer(new RepublishMessageRecoverer(rabbitTemplate, deadLetterTwoQueue().getName(), deadLetterTwoQueue().getName()))
//                .build();
//    }

}
