package br.com.poc.producer;

import br.com.poc.config.queue.OneQueueConfig;
import br.com.poc.config.queue.TwoQueueConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by maz_dcosta on 07/04/17.
 */
@Component
public class Producer {

//    private @Autowired RabbitTemplate rabbitTemplate;
//    private @Autowired TopicExchange exchange;
//
//    public void produceToQueueOne(String msg) {
//        rabbitTemplate.convertAndSend(exchange.getName(), OneQueueConfig.QUEUE_ONE, msg);
//    }
//
//    public void produceToQueueTwo(String msg) {
//        rabbitTemplate.convertAndSend(exchange.getName(), TwoQueueConfig.QUEUE_TWO, msg);
//    }

}
