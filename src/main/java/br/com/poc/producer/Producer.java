package br.com.poc.producer;

import br.com.poc.config.queue.OneQueueConfig;
import br.com.poc.config.queue.TwoQueueConfig;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by maz_dcosta on 07/04/17.
 */
@Component
public class Producer {

    private @Autowired RabbitTemplate rabbitTemplate;
    private @Autowired FanoutExchange fanoutExchange;

    public void produceToQueueOne(String msg) {
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), OneQueueConfig.BROADCAST_ONE_QUEUE, msg);
    }

    public void produceToQueueTwo(String msg) {
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), TwoQueueConfig.BROADCAST_TWO_QUEUE, msg);
    }

}
