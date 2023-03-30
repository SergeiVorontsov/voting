package com.vorsa.voting.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.vorsa.voting.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;
import java.text.SimpleDateFormat;


@Configuration
@Slf4j
@EnableCaching
public class AppConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile("!test")
    Server h2Server() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @Bean
    public void storeObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        Hibernate6Module hibernate6Module = new Hibernate6Module();
        mapper.registerModule(hibernate6Module);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        JsonUtil.setMapper(mapper);
    }
}