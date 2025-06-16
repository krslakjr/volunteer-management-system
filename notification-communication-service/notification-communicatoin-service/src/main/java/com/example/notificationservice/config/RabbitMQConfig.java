package com.example.notificationservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "participation.exchange";
    public static final String ROUTING_KEY = "participation.created";
    public static final String QUEUE = "participation.created.queue";

    public static final String FEEDBACK_EXCHANGE = "feedback.exchange";
    public static final String FEEDBACK_ROUTING_KEY = "feedback.created";
    public static final String FEEDBACK_QUEUE = "feedback.created.queue";

    public static final String USER_EXCHANGE = "user.exchange";
    public static final String USER_ROUTING_KEY = "user.created";
    public static final String USER_QUEUE = "user.created.queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Queue feedbackQueue() {
        return new Queue(FEEDBACK_QUEUE);
    }

    @Bean
    public TopicExchange feedbackExchange() {
        return new TopicExchange(FEEDBACK_EXCHANGE);
    }

    @Bean
    public Binding feedbackBinding(Queue feedbackQueue, TopicExchange feedbackExchange) {
        return BindingBuilder.bind(feedbackQueue).to(feedbackExchange).with(FEEDBACK_ROUTING_KEY);
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(USER_EXCHANGE);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(USER_QUEUE);
    }

    @Bean
    public Binding userBinding(Queue userQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with(USER_ROUTING_KEY);
    }

    @Bean
    public Queue participationCreatedQueue() {
        return QueueBuilder.durable("participation-created-queue")
                .withArgument("x-dead-letter-exchange", "") // default exchange
                .withArgument("x-dead-letter-routing-key", "participation-created-dlq")
                .build();
    }

    @Bean
    public Queue participationCreatedDlq() {
        return QueueBuilder.durable("participation-created-dlq").build();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        // Retry: pokušaj do 3 puta, pa odbaci bez vraćanja u queue
        RetryOperationsInterceptor retryInterceptor = RetryInterceptorBuilder.stateless()
                .maxAttempts(3)
                .recoverer(new RejectAndDontRequeueRecoverer()) // odbacuje poruku nakon 3 neuspeha
                .build();

        factory.setAdviceChain(retryInterceptor);
        factory.setDefaultRequeueRejected(false); // važno da ne vraća poruku u queue ako baci izuzetak

        factory.setMessageConverter(jackson2JsonMessageConverter());

        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

        // Type mapping
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID); // Prioritizuj header __TypeId__

        // Mapiraj ime klase iz poruke na lokalnu klasu
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.example.participationservice.event.ParticipationCreatedEvent",
                com.example.notificationservice.event.ParticipationCreatedEvent.class);

        idClassMapping.put("com.example.feedbackservice.event.FeedbackCreatedEvent",
                com.example.notificationservice.event.FeedbackCreatedEvent.class);

        idClassMapping.put("com.example.userservice.event.UserCreatedEvent",
                com.example.notificationservice.event.UserCreatedEvent.class);

        typeMapper.setIdClassMapping(idClassMapping);
        converter.setJavaTypeMapper(typeMapper);

        return converter;
    }
}
