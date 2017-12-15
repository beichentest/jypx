package com.geekcattle.mq.producer;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.geekcattle.model.console.Log;
import com.geekcattle.util.DateUtil;

@Component  
public class LogSender implements RabbitTemplate.ConfirmCallback {  
    private static final Logger LOGGER = LoggerFactory.getLogger(LogSender.class);  
  
    @Value("${spring.rabbitmq.exchange-log}")  
    private String exchagnge;  
    @Value("${spring.rabbitmq.routingkey-log}")  
    private String routingKey;  
    @Value("${spring.rabbitmq.queuename-log}")  
    private String queuen;
    private RabbitTemplate rabbitTemplate;  
    @Bean
    public DirectExchange logExchange() {  
        /** 
         * DirectExchange:按照routingkey分发到指定队列 
         * TopicExchange:多关键字匹配 
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
         * HeadersExchange ：通过添加属性key-value匹配 
         */  
        return new DirectExchange(exchagnge);  
    }  
    @Bean
    public Queue logQueue() {  
        return new Queue(queuen);  
    }  
  
    @Bean  
    public Binding binding() {  
        /** 将队列绑定到交换机 */  
        return BindingBuilder.bind(logQueue()).to(logExchange()).with(routingKey);  
    }
  
    @Autowired  
    public LogSender(RabbitTemplate rabbitTemplate) {  
        this.rabbitTemplate = rabbitTemplate;  
        this.rabbitTemplate.setConfirmCallback(this);  
    }  
    public void send(String username,String ip,String action) {  
    	try {
			Thread.currentThread().sleep(9000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Log  log = new Log();
        log.setLogUser(username);
        log.setLogTime(DateUtil.getSysTime());
        log.setLogIp(ip);
        log.setLogAction(action);    	
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());  
        LOGGER.info("send: " + correlationData.getId());  
        this.rabbitTemplate.convertAndSend(exchagnge, routingKey,log, correlationData);  
    }  
  
    /** 回调方法 */  
    @Override  
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {  
    	if (ack) {  
    		LOGGER.info("confirm: " + correlationData.getId()+"，消息成功消费");     		
        } else {  
        	LOGGER.error("confirm: " + correlationData.getId()+"，消息消费失败");         	
        }          
    }  
} 