package org.example.movie.master.configuration;

import org.example.movie.core.common.schedule.MovieDetailsListResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaTemplateConfiguration {

    @Bean
    public KafkaTemplate<String, MovieDetailsListResponse> kafkaMovieDetailsReplyTemplate(ProducerFactory<String,
            MovieDetailsListResponse> producerFactoryMovieDetailsResponse) {
        return new KafkaTemplate<>(producerFactoryMovieDetailsResponse);
    }
}