package org.example.movie.master.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.movie.master.dto.mq.MovieDetailsRequest;
import org.example.movie.master.dto.mq.MovieDetailsResponse;
import org.example.movie.master.repository.MovieMasterRepository;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
@AllArgsConstructor
public class MovieMasterService {

  private final MovieMasterRepository movieMasterRepository;

  public MovieDetailsResponse fetchMovieDetails(MovieDetailsRequest movieDetailsRequest) {
    return movieMasterRepository
        .findByMovieUniqueKey(movieDetailsRequest.getMovieUniqueId())
        .map(
            movieMaster ->
                MovieDetailsResponse.of()
                    .setMovieName(movieMaster.getMovieName())
                    .setMovieRunTime(movieMaster.getMovieRunTime())
                    .setMovieUniqueKey(movieMaster.getMovieUniqueKey())
                    .setGenre(movieMaster.getGenre())
                    .setMovieCertificationType(movieMaster.getMovieCertificationType())
                    .setLanguage(movieMaster.getLanguage())
                    .setMovieReleaseDate(movieMaster.getMovieReleaseDate()))
        .orElseGet(MovieDetailsResponse::of);
  }
}
