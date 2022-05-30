package org.example.movie.master.repository;

import org.example.movie.master.entity.MovieMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieMasterRepository extends JpaRepository<MovieMaster, Integer> {

  Optional<MovieMaster> findByMovieUniqueKey(String movieUniqueKey);
}
