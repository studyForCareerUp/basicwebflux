package com.example.webflux.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableR2dbcRepositories //r2dbc 사용을 위해서 추가
@EnableR2dbcAuditing //자동으로 created_at, updated_at 값 추가
public class R2dbcConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final DatabaseClient databaseClient;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // reactor: publisher, subscriber
        databaseClient.sql("select 1").fetch().one()
                .subscribe(
                        success -> {
                            log.info("Initialize r2dbc database connection");
                        },
                        error -> {
                            log.error("Fail to initialize r2dbc database connection");
                            SpringApplication.exit(event.getApplicationContext(), () -> -110); // 애플리케이션 자체를 종료
                        }
                );
    }
}
