package org.example.movie.master.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.movie.core.common.schedule.MovieDetails;
import org.example.movie.core.common.schedule.MovieDetailsListRequest;
import org.example.movie.master.repository.MovieMasterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@Service
@AllArgsConstructor
public class MovieMasterService {

  private final MovieMasterRepository movieMasterRepository;

  public List<MovieDetails> fetchMovieDetails(MovieDetailsListRequest movieDetailsRequest) {
    return
            movieMasterRepository.findAllByMovieUniqueKeyIn(movieDetailsRequest.getMovieUniqueIdList())
            .stream()
            .map(
            movieMaster ->
                MovieDetails.of()
                    .setMovieName(movieMaster.getMovieName())
                    .setMovieRunTime(movieMaster.getMovieRunTime())
                    .setMovieUniqueKey(movieMaster.getMovieUniqueKey())
                    .setGenre(movieMaster.getGenre())
                    .setMovieCertificationType(movieMaster.getMovieCertificationType())
                    .setLanguage(movieMaster.getLanguage())
                    .setMovieReleaseDate(movieMaster.getMovieReleaseDate()))
            .collect(Collectors.toList());
  }
}