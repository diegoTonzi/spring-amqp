package br.com.poc.consumer;

import br.com.poc.config.queue.TwoQueueConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TwoConsumer {

//	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//
//	@RabbitListener(containerFactory = "twoContainerFactory", queues = {TwoQueueConfig.QUEUE_TWO})
//	public void twoConsumer(Message message) throws Exception {
//		System.out.println("Msg received on 'twoConsumer' at " + dateFormat.format(new Date()));
//		throw  new Exception("some consumer exception");
//	}

}
