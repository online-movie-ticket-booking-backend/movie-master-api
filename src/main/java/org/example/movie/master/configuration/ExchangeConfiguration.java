package org.example.movie.master.configuration;

import lombok.Data;

@Data
public class ExchangeConfiguration {
  private String exchange;
  private String routingKey;
  private String queue;
}
