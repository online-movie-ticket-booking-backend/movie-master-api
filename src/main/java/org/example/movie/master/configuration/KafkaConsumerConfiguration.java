package org.example.movie.master.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.movie.core.common.schedule.MovieDetailsListRequest;
import org.example.movie.core.common.schedule.MovieDetailsListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.movieBookingApi.groupName}")
    private String groupName;
   
    @Value("${kafka.movieBookingApi.movieDetails.topic.serialization-class}")
    private String movieDetailsSerializationClass;

    @Value("${kafka.movieBookingApi.movieDetails.topic.request}")
    private String movieDetailsRequestTopic;

    @Bean
    public ConsumerFactory<String, MovieDetailsListRequest> consumerFactoryMovieDetailsRequest() {
        return new DefaultKafkaConsumerFactory<>(
                getConfigurationMapForListener(movieDetailsSerializationClass));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MovieDetailsListRequest> movieDetailsRequestListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MovieDetailsListRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryMovieDetailsRequest());
        return factory;
    }

    @Bean
    public ConcurrentMessageListenerContainer<String, MovieDetailsListRequest> movieDetailsRequestListenerContainer(
            ConcurrentKafkaListenerContainerFactory<String, MovieDetailsListRequest> movieDetailsRequestListenerContainerFactory) {
        ConcurrentMessageListenerContainer<String, MovieDetailsListRequest> repliesContainer =
                movieDetailsRequestListenerContainerFactory.createContainer(movieDetailsRequestTopic);
        repliesContainer.getContainerProperties().setGroupId(groupName);
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MovieDetailsListRequest>>
    movieDetailsRequestListenerContainerFactory(KafkaTemplate<String, MovieDetailsListResponse> kafkaMovieDetailsReplyTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, MovieDetailsListRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryMovieDetailsRequest());
        factory.setReplyTemplate(kafkaMovieDetailsReplyTemplate);
        return factory;
    }   

    private Map<String, Object> getConfigurationMapForListener(String defaultValueType) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        configProps.put(JsonDeserializer.KEY_DEFAULT_TYPE, "java.lang.String");
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, defaultValueType);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "org.example.movie.core.common.schedule");
        return configProps;
    }
}