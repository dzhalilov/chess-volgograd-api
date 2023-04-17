package com.example.chessvolgograd;

import com.example.chessvolgograd.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
@EnableSwagger2
public class ChessVolgogradApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChessVolgogradApplication.class, args);
  }

}
