package com.vorsa.voting.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic",
        in = SecuritySchemeIn.HEADER
)
@OpenAPIDefinition(
        info = @Info(
                title = "REST API documentation",
                version = "1.0",
                description = """
                        <h3>Restaurants voting application</h3>
                        <p>This is a voting system for deciding where to have lunch.<br>
                        2 types of users: admin and regular users.<br>
                        Admin can input a restaurant and it's lunch menu of the day (just a dish name and price).<br>
                        Admin can create new menus, new meals, update meals.<br>
                        Users can vote for a restaurant they want to have lunch at today.<br>
                        Only one vote counted per user.<br>
                        If user votes again the same day:<br>
                           - if it is before 11:00 we assume that he changed his mind.<br>
                           - if it is after 11:00 then it is too late, vote can't be changed.<br>
                        Supposed each restaurant provides a new menu each day. So restaurants that have not been
                        provided a today lunch menu, don't take part in the voting today</p>
                        <p><b>Test credentials:</b><br>
                         - user@yandex.ru / password<br>
                         - admin@gmail.com / admin<br>
                         - guest@gmail.com / guest</p>
                        """,
                contact = @Contact(name = "Sergey Vorontsov", email = "vorontsov.s@mail.ru")
        ),
        security = @SecurityRequirement(name = "basicAuth")
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("REST API")
                .pathsToMatch("/api/**")
                .build();
    }
}
