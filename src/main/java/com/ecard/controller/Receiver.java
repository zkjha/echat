/*package com.ecard.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	@RabbitListener(queues = "hello")
    public void process(String hello) {
        System.out.println("Receiver : " + hello);
    }
	
	@RabbitListener(queues = "hello1")
    public void process1(String hello) {
        System.out.println("Receiver : " + hello);
    }
}
*/