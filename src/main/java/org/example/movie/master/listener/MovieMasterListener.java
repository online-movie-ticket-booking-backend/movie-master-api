package org.example.movie.master.listener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.movie.master.dto.mq.MovieDetailsRequest;
import org.example.movie.master.dto.mq.MovieDetailsResponse;
import org.example.movie.master.service.MovieMasterService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MovieMasterListener {

  private final MovieMasterService movieMasterService;

  @RabbitListener(queues = "movieDetailsQueue")
  public MovieDetailsResponse receiveMovieDetailsMessage(MovieDetailsRequest movieDetailsRequest) {
    return movieMasterService.fetchMovieDetails(movieDetailsRequest);
  }
}
