package com.rescene.recard.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI sceneSwapOpenApi() {
        return new OpenAPI().info(new Info()
                .title("SceneSwap API")
                .description("리센느 포토카드 1:1 교환 API. 본인 확인이 필요한 API는 X-User-Id 헤더를 사용합니다.")
                .version("v1"));
    }
}
