package com.example.chessvolgograd.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "service")
@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppConfig {

  private String urlPathClassic;
  private String urlPathRapid;
  private String urlPathBlitz;
  private String region;

  private String username;
  private String password;
}
