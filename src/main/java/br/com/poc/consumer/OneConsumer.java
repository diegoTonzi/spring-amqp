package br.com.poc.consumer;

import br.com.poc.config.queue.OneQueueConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OneConsumer {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@RabbitListener(containerFactory = "oneContainerFactory", queues = {OneQueueConfig.BROADCAST_ONE_QUEUE})
	public void oneConsumer(Message message) throws Exception {
		System.out.println("Msg received on 'oneConsumer' at " + dateFormat.format(new Date()));
		throw  new Exception("some consumer exception");
	}

}
