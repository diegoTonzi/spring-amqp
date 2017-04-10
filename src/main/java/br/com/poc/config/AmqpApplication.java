package br.com.poc.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "br.com.poc")
@EnableRabbit
public class AmqpApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(AmqpApplication.class, args);
//        context.getBean(Producer.class).produceToQueueOne("send to extchange one");
//        context.getBean(Producer.class).produceToQueueTwo("send to extchange two");
    }

}
