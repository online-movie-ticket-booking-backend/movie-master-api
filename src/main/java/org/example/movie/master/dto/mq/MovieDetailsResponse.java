package org.example.movie.master.dto.mq;

import lombok.Data;
import lombok.experimental.Accessors;

@Data(staticConstructor = "of")
@Accessors(chain = true)
public class MovieDetailsResponse {
  private String movieUniqueKey;
  private String movieName;
  private String movieRunTime;
  private String movieReleaseDate;
  private String movieCertificationType;
  private String language;
  private String genre;
}
