package com.aa.example.springinteg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel inputChannel() {
        return new QueueChannel();
    }

    @Bean
    public QueueChannel outputChannel() {
        return new QueueChannel();
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.setThreadNamePrefix("async-task-");
        taskExecutor.initialize();
        return taskExecutor;
    }
    
    @Bean
    public IntegrationFlow myFlow() {
        return IntegrationFlow.from("inputChannel")
                .channel(c -> c.executor(taskExecutor()))
                .handle("myService", "process")
                .channel("outputChannel")
                .get();
            }

//    @Bean
//    @ServiceActivator(inputChannel = "outputChannel")
//    public MessageHandler outputHandler() {
//        return message -> System.out.println("Response: " + message.getPayload());
//    }
    
}