package com.example.chessvolgograd;

import com.example.chessvolgograd.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
public class ChessVolgogradApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChessVolgogradApplication.class, args);
  }

}
