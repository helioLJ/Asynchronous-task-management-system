package com.helio.taskManagement.config;

import com.helio.taskManagement.messaging.CustomMessageConverter;
import com.helio.taskManagement.messaging.TaskAssignmentConsumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    static final String topicExchangeName = "task-assignment-exchange";

    static final String queueName = "task-assignment-queue";
    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new CustomMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("#");
    }
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    @Bean
    MessageListenerAdapter listenerAdapter(TaskAssignmentConsumer receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
