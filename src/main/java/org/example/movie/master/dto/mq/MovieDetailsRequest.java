package org.example.movie.master.dto.mq;

import lombok.Data;
import lombok.experimental.Accessors;

@Data(staticConstructor = "of")
@Accessors(chain = true)
public class MovieDetailsRequest {
  private String movieUniqueId;
}
