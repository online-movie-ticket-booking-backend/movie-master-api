package org.example.movie.master.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Value("${kafka.movieBookingApi.movieDetails.topic.response}")
    private String movieDetailsTopicName;

    @Bean
    public NewTopic topicMovieDetailsTopicName() {
        return TopicBuilder.name(movieDetailsTopicName).build();
    }


}