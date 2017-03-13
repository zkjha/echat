//package com.ecard.controller;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Receiver {
//
//	@RabbitListener(queues = "hello")
//	@Async("messageQuneExecutor")
//    public void process(String hello) {
//		 System.out.println("Receiver : " +Thread.currentThread().getName()+"  "+ hello);
//    }
//	
//	@RabbitListener(queues = "hello1")
//    public void process1(String hello)  throws Exception{
//		
//	    
//		 System.out.println("Receiver : " +Thread.currentThread().getName()+"  "+ hello);
//	      
//       
//    }
//}
