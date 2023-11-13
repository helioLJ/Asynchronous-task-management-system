package com.helio.taskManagement.messaging;

import com.helio.taskManagement.dto.TaskAssignmentMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

public class CustomMessageConverter implements MessageConverter {

    private final SimpleMessageConverter delegate = new SimpleMessageConverter();

    @Override
    public Message toMessage(Object object, org.springframework.amqp.core.MessageProperties messageProperties) throws MessageConversionException {
        // Implement your custom conversion logic here
        // Convert TaskAssignmentMessage to byte[] or String

        // Example (assuming TaskAssignmentMessage has a toString() method):
        String payload = object.toString();
        return delegate.toMessage(payload, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        // Implement your custom conversion logic here
        // Convert byte[] or String to TaskAssignmentMessage

        // Example:
        String payload = delegate.fromMessage(message).toString();
        // Parse the payload and create a TaskAssignmentMessage object

        return new TaskAssignmentMessage(/* Set properties based on the payload */);
    }
}
