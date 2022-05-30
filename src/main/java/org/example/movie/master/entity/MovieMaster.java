package org.example.movie.master.entity;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "movie_master", schema = "movie_booking", catalog = "")
public class MovieMaster {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "movie_id", nullable = false)
  private Integer movieId;

  @Basic
  @Column(name = "movie_unique_key", nullable = false, length = 255)
  private String movieUniqueKey;

  @Basic
  @Column(name = "movie_name", nullable = false, length = 255)
  private String movieName;

  @Basic
  @Column(name = "movie_run_time", nullable = false, length = 20)
  private String movieRunTime;

  @Basic
  @Column(name = "movie_release_date", nullable = true)
  private String movieReleaseDate;

  @Basic
  @Column(name = "movie_certification_type", nullable = false, length = 20)
  private String movieCertificationType;

  @Basic
  @Column(name = "language", nullable = false, length = 20)
  private String language;

  @Basic
  @Column(name = "genre", nullable = false, length = 20)
  private String genre;
}
