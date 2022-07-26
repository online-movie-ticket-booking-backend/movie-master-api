package org.example.movie.master.adapter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.movie.core.common.schedule.MovieDetails;
import org.example.movie.core.common.schedule.MovieDetailsListRequest;
import org.example.movie.core.common.schedule.MovieDetailsListResponse;
import org.example.movie.master.service.MovieMasterService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class KafkaProducerAdapter {

    private final MovieMasterService movieMasterService;

    @KafkaListener(topics = "${kafka.movieBookingApi.movieDetails.topic.request}",
            containerFactory = "movieDetailsRequestListenerContainerFactory",
            groupId = "${kafka.movieBookingApi.groupName}"
    )
    @SendTo()
    public MovieDetailsListResponse receive(
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String uniqueId,
            MovieDetailsListRequest request) throws ExecutionException, InterruptedException, TimeoutException {
        log.info("Received Message with : {}",uniqueId);
        return MovieDetailsListResponse.of()
                        .setMovieDetailsMap(
        movieMasterService.fetchMovieDetails(request)
                .stream()
                .collect(Collectors.toMap(MovieDetails::getMovieUniqueKey,movieDetails -> movieDetails)));
    }
}

