package uz.pdp.appaviauz;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "App Avia Sales",
                description = DemoApplication.DESCRIPTION
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    public static final String DESCRIPTION = """
             <p><b> Bearer token is used for authentication </b> <br></p>
             <a href="https://gitlab.com/my-portfolio4054036/app-aviasales">
                 <b> Gitlab link of this site </b> </a><br>
             <a href="https://github.com/sayfiddinn/app-aviasales">
                 <b> GitHub link of this site </b> </a>
             <p><b>Contact me to get a other role email and password </b>
             <a href="https://t.me/sayfiddin_a">
                 <b> Sayfiddin </b> </a></p>
            """;
}
